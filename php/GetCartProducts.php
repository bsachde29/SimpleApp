<?php
error_reporting(E_ALL);
$cartID = $_POST['cartID'];

//$cartID = $_GET['cartID'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt = $conn->prepare("SELECT * FROM Cart_Product_Count WHERE CartID = '$cartID'");
    $stmt->execute();
    $cart = $stmt->fetchAll(PDO::FETCH_ASSOC);
    $prodArray = array();
    for ($counter = 0; $counter < count($cart); $counter++) {
        // echo "$id \n";
        $id = $cart[$counter]['ProductID'];
        $stmt2 = $conn->prepare("SELECT * FROM Product WHERE ProductID = '$id'");
        $stmt2->execute();
        $prodList = $stmt2->fetchAll(PDO::FETCH_ASSOC);
        $prodArray[$counter] = $prodList[0];
    }
    echo json_encode($prodArray);
}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}

?>




