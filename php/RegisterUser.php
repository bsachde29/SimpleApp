<?php
error_reporting(E_ALL);
$firstName  = $_POST['FirstName'];
$lastName =  $_POST['LastName'];
$email = $_POST['Email'];
$mobileNum = $_POST['MobileNum'];
$pswd = $_POST['Pswd'];
$sellerId = $_POST['SellerID'];
$SecAns = $_POST['SecAns'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt = $conn->prepare("insert into Buyers (FirstName, LastName, Email, MobileNum, Pswd, SellerID, SecAns) VALUES ('$firstName', '$lastName', '$email', '$mobileNum', '$pswd', '$sellerId','$SecAns')");

    $stmt->execute();
    $stmt2 = $conn->prepare("SELECT BuyerID from Buyers where Email = '$email'");

    $stmt2->execute();
    $buyer = $stmt2->fetchAll(PDO::FETCH_ASSOC);
    $buyerId = $buyer[0]['BuyerID'];
    $stmt3 = $conn->prepare("insert into Seller_Buyer (SellerID, BuyerID) VALUES ('$sellerId', '$buyerId')");

    $stmt3->execute();


}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}

?>




