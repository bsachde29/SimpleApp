<?php
error_reporting(E_ALL);
$buyerID  = $_POST['BuyerID'];
$orderState = 0;
$orderAccept = 1;
$firstName = $_POST['FirstName'];
$lastName =  $_POST['LastName'];
$email = $_POST['Email'];
$mobileNum = $_POST['MobileNum'];
$totalPrice = 0;
$discountCode = $_POST['DiscountCode'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $disCheck = $conn->prepare("SELECT DiscountID from Discount where DiscountCode = '$discountCode'");
    //TODO discount code invalid
    $stmt = "INSERT into Orders (BuyerID, totalPrice, OrderState, FirstName, LastName, Email, 
    MobileNum, OrderAccept, DiscountID) VALUES ('$buyerID', '$totalPrice', '$orderState', '$firstName', '$lastName', 
    '$email', '$mobileNum', '$orderAccept', '$discountCode')";
    if ($conn->query($stmt) === TRUE) {
        $orderID = $conn->lastInsertId();
        echo json_encode($orderID);
    }

}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}

?>




