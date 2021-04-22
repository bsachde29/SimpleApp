<?php
error_reporting(E_ALL);
$buyerID = $_POST['BuyerID'];
$first = $_POST['FirstName'];
$last= $_POST['LastName'];
$email = $_POST['Email'];
$mobile = $_POST['MobileNum'];
$secAns = $_POST['SecAns'];


$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt = $conn->prepare("UPDATE Buyers set FirstName ='$first',LastName ='$last',Email = '$email',MobileNum ='$mobile',SecAns='$secAns'  WHERE BuyerID = '$buyerID'");
    $stmt->execute();
}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}

?>




