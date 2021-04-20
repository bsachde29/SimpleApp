<?php
error_reporting(E_ALL);
$searchName = $_POST['searchName'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt = $conn->prepare("SELECT * FROM Product WHERE Name like '%{$searchName}%' OR Description like '%{$searchName}%'");
    $stmt->execute();
    $order = $stmt->fetchAll(PDO::FETCH_ASSOC);
    echo json_encode($order);
}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}

?>