<?php
error_reporting(E_ALL);
$sellerID = $_POST['BuyerID'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt = $conn->prepare("SELECT OrderID FROM Notification  WHERE BuyerID = '$sellerID' order by NotificationID DESC");
    $stmt->execute();
    $seller = $stmt->fetchAll(PDO::FETCH_ASSOC);
    echo json_encode($seller);
}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}

?>




