<?php
error_reporting(E_ALL);
$buyerID = $_POST['BuyerID'];
$addressID = $_POST['AddressID'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt = $conn->prepare("DELETE FROM AddressBook WHERE BuyerID = '$buyerID' AND AddressID = '$addressID'");
    $stmt->execute();

}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}

?>