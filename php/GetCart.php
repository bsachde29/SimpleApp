<?php
error_reporting(E_ALL);
$cartID = $_POST['CartID'];

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
    //echo $cart[0];
    $cartObj = $cart;
//    $prodArray = array();
//    $counter = 0;
//    foreach ($cart[$counter]['ProductID'] as $id) {
//        // echo "$id \n";
//        $stmt2 = $conn->prepare("SELECT * FROM Product WHERE ProductID = '$id' AND isSubProduct = 0");
//        $stmt2->execute();
//        $prodList = $stmt2->fetchAll(PDO::FETCH_ASSOC);
//        $prodArray[$counter] = $prodList;
//        $counter++;
//    }
    echo json_encode($cartObj);

}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}

?>




