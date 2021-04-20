<?php
error_reporting(E_ALL);
//$buyerID = $_POST['BuyerID'];
//$pass = $_POST['Pswd'];
//$SecAns = $_POST['SecAns'];

$buyerID = $_GET['BuyerID'];
$pass = $_GET['Pswd'];
$SecAns = $_GET['SecAns'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt = $conn->prepare("SELECT SecAns FROM Buyers WHERE BuyerID = '$buyerID'");
    $stmt->execute();
    $answer = $stmt->fetchAll(PDO::FETCH_ASSOC);
    echo($answer[0]['SecAns']);
    if ($answer[0]['SecAns'] == $SecAns) {
        $stmt1 = $conn->prepare("UPDATE Buyers SET Pswd = $pass WHERE BuyerID = $buyerID ");
        $stmt1->execute();
    }
}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}

?>