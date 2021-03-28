<?php
error_reporting(E_ALL);
$cartID  = $_POST['CartID'];
$productID = $_POST['ProductID'];
$count = $_POST['Count'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt = $conn->prepare("insert into Cart_Product_Count (CartID, ProductID, Count) VALUES ('$cartID', 
    '$productID', '$count')");
    $stmt->execute();
    $stmt2 = $conn->prepare("SELECT * FROM Cart WHERE CartID = '$cartID'");
    $stmt2->execute();
    $cart = $stmt2->fetchAll(PDO::FETCH_ASSOC);
    if (count($cart) == 0) {
        echo "Wrong Details";
    } else {
        $stmt3 = $conn->prepare("SELECT * FROM Product WHERE ProductID = '$productID'");
        $stmt3->execute();
        $product = $stmt3->fetchAll(PDO::FETCH_ASSOC);
        if (count($product) == 0) {
            echo "Wrong Product Details";
        } else {
            $productPrice = $product[0]['price'];
            $totalPrice = $cart[0]['totalPrice'];
            $totalPrice = $totalPrice + ($productPrice * $count);
            $stmt4 = $conn->prepare("UPDATE Cart SET totalPrice = '$totalPrice' WHERE CartID = '$cartID'");
            $stmt4->execute();
        }
    }
}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();
}

?>