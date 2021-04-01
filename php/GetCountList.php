<?php
error_reporting(E_ALL);
$orderID = $_POST['OrderID'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt = $conn->prepare("SELECT * FROM Order_Product_Count WHERE OrderID = '$orderID'");
    $stmt->execute();
    $order = $stmt->fetchAll(PDO::FETCH_ASSOC);
    $countArray = array();
    for ($counter = 0; $counter < count($order); $counter++) {
        // echo "$id \n";
        $c = $order[$counter]['Count'];
        $prodArray[$counter] = $c;
    }
    echo json_encode($countArray);
}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}

?>