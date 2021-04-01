<?php
error_reporting(E_ALL);
$buyerID = $_POST['BuyerID'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt = $conn->prepare("SELECT * FROM Orders WHERE BuyerID = '$buyerID'");
    $stmt->execute();
    $orders = $stmt->fetchAll(PDO::FETCH_ASSOC);
    $orderArray = array();
    for ($counter = 0; $counter < count($orders); $counter++) {
        // echo "$id \n";
        $orderArray[$counter] = $orders[$counter];
    }
    echo json_encode($orderArray);
}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}

?>




