<?php
error_reporting(E_ALL);
$buyerID  = $_POST['BuyerID'];
$street = $_POST['StreetAddress'];
$apt = $_POST['Apt'];
$city = $_POST['City'];
$state = $_POST['State'];
$postal = $_POST['PostalCode'];

//$buyerID  = $_GET['BuyerID'];
//$street = $_GET['StreetAddress'];
//$apt = $_GET['Apt'];
//$city = $_GET['City'];
//$state = $_GET['State'];
//$postal = $_GET['PostalCode'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt1 = $conn->prepare("Insert into AddressBook (BuyerID, StreetAddress, Apt, City, State, PostalCode) 
             VALUES('$buyerID', '$street', '$apt','$city','$state',$postal)");
    $stmt1->execute();

}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}

?>




