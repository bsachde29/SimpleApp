<?php
error_reporting(E_ALL);
$buyerID  = $_POST['buyerID'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt2 = $conn->prepare("SELECT * FROM Cart WHERE BuyerID = '$buyerID'");
    $stmt2->execute();
    $cart = $stmt2->fetchAll(PDO::FETCH_ASSOC);
    if (count($cart) == 0) {
        echo "Wrong Details";
    } else {
        $cartID = $cart[0]['CartID'];
        echo (json_encode($cartID));
    }
}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();
}

?>