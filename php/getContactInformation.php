<?php
error_reporting(E_ALL);
$sellerId = $_POST['SellerID'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt = $conn->prepare("SELECT * FROM Sellers WHERE SellerID = '$sellerId'");
    $stmt->execute();
    $seller = $stmt->fetchAll(PDO::FETCH_ASSOC);
    if (count($seller) != 0) {
        $retarray = array();
        $counter = 0;
        $emailID = $seller[0]['Email'];
        $facebook = $seller[0]['FbHandle'];
        $instagram = $seller[0]['InstaHandle'];
        $mobileNum = $seller[0]['MobileNum'];
        $retarray[0] = $emailID;
        $retarray[1] = $facebook;
        $retarray[2] = $instagram;
        $retarray[3] = $mobileNum;
        echo json_encode(($retarray));
    }
    else {
        echo "\nNo Seller";
    }
}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}