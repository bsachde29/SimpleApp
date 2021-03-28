<?php
error_reporting(E_ALL);
$orderID  = $_POST['OrderID'];
$productID = $_POST['ProductID'];
$count = $_POST['Count'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt = $conn->prepare("insert into Order_Product_Count (OrderID, ProductID, Count) VALUES ('$orderID', 
    '$productID', '$count')");
    $stmt->execute();

}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();
}

?>