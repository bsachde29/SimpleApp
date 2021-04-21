<?php
error_reporting(E_ALL);
$sellerId = $_POST['SellerID'];
$searchName = $_POST['searchName'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt = $conn->prepare("SELECT * FROM Seller_Product WHERE SellerID = '$sellerId'");
    $stmt->execute();
    $product_temp = $stmt->fetchAll(PDO::FETCH_COLUMN, 1);
    $prodArray = array();
    $counter = 0;
    foreach ($product_temp as $id) {
        // echo "$id \n";
        $stmt2 = $conn->prepare("SELECT * FROM Product WHERE ProductID = '$id' AND (Name like '%{$searchName}%' OR Description like '%{$searchName}%')");
        $stmt2->execute();
        $prodList = $stmt2->fetchAll(PDO::FETCH_ASSOC);
        $prodArray[$counter] = $prodList[0];
        $counter++;
    }
    echo json_encode(($prodArray));

}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}

?>









