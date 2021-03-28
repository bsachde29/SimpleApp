<?php
error_reporting(E_ALL);
$buyerID  = $_POST['BuyerID'];
$cartID = $_POST['CartID'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt1 = $conn->prepare("SELECT * FROM Cart WHERE CartID = '$cartID'");
    $stmt1->execute();
    $cart = $stmt1->fetchAll(PDO::FETCH_ASSOC);
    if (count($cart) == 0) {
        echo "Cart does not exist";
    } else {
        $totalPrice = $cart[0]['totalPrice'];
        if ($totalPrice == 0) {
            echo "Nothing in cart";
        } else {
            $orderState = 0;
            $firstName = $cart[0]['FirstName'];
            $lastName = $cart[0]['LastName'];
            $email = $cart[0]['Email'];
            $mobileNum = $cart[0]['MobileNum'];
            $orderAccept = 1;
            $discountCode = $cart[0]['DiscountCode'];
            $stmt2 = $conn->prepare("INSERT INTO Orders (BuyerID, totalPrice, OrderState, FirstName, LastName, Email, 
            MobileNum, OrderAccept, DiscountID) VALUES ('$buyerID', '$totalPrice', '$orderState', '$firstName', '$lastName', 
            '$email', '$mobileNum', '$orderAccept', '$discountCode')");
            $stmt2->execute();
            $orderID = $conn->lastInsertId();
            $stmt3 = $conn->prepare("SELECT * FROM Cart_Product_Count WHERE CartID = '$cartID'");
            $stmt3->execute();
            $result = $stmt3->fetchAll(PDO::FETCH_ASSOC);
            for ($i = 0; $i < count($result); $i++) {
                $productID = $result[$i]["ProductID"];
                $count = $result[$i]["Count"];
                $stmt4 = $conn->prepare("INSERT INTO Order_Product_Count (OrderID, ProductID, Count) 
                VALUES ('$orderID', '$productID', '$count')");
                $stmt4->execute();
            }
            $stmt5 = $conn->prepare("DELETE FROM Cart_Product_Count WHERE CartID = '$cartID'");
            $stmt5->execute();
            $stmt6 = $conn->prepare("DELETE FROM Cart WHERE CartID = '$cartID'");
            $stmt6->execute();
            $stmt7 = $conn->prepare("INSERT INTO Cart (BuyerID, FirstName, LastName, Email, MobileNum) 
            VALUES ('$buyerID', '$firstName', '$lastName', '$email', '$mobileNum'");
            $stmt7->execute();
        }
    }

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




