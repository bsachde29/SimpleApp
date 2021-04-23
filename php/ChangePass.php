<?php
error_reporting(E_ALL);
//$buyerID = $_POST['BuyerID'];
//$pass = $_POST['Pswd'];
//$SecAns = $_POST['SecAns'];

$buyerID = $_POST['Email'];
$pass = $_POST['Pswd'];
$SecAns = $_POST['SecAns'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt = $conn->prepare("SELECT SecAns FROM Buyers WHERE Email = '$buyerID'");
    $stmt->execute();
    $answer = $stmt->fetchAll(PDO::FETCH_ASSOC);
    $stmt1 = $conn->prepare("UPDATE Buyers SET Pswd = '$pass' WHERE Email = '$buyerID' ");
    echo "UPDATE Buyers SET Pswd = $pass WHERE Email = $buyerID";
    $stmt1->execute();

}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}

?>