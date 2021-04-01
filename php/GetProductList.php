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
    $prodArray = array();
    for ($counter = 0; $counter < count($order); $counter++) {
        // echo "$id \n";
        $id = $order[$counter]['ProductID'];
        $stmt2 = $conn->prepare("SELECT * FROM Product WHERE ProductID = '$id'");
        $stmt2->execute();
        $prodList = $stmt2->fetchAll(PDO::FETCH_ASSOC);
        $prodArray[$counter] = $prodList;
    }
    echo json_encode($prodArray);
}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}

?>