<?php
error_reporting(E_ALL);
$cartID = $_POST['CartID'];

//$cartID = $_POST['cartID'];

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
    $countArray = array();
    for ($counter = 0; $counter < count($cart); $counter++) {
        // echo "$id \n";
        $c = $cart[$counter]['Count'];
        $countArray[$counter] = $c;
    }
    echo json_encode($countArray);
}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}

?>




