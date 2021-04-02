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
    $stmt = $conn->prepare("SELECT * FROM Cart WHERE CartID = '$cartID'");
    $stmt->execute();
    $cart = $stmt->fetchAll(PDO::FETCH_ASSOC);
    //echo $cart[0];
    $cartObj = $cart[0];

    echo json_encode($cartObj);

}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}

?>




