<?php
error_reporting(E_ALL);

//$cartID = $_GET['CartID'];
//$productID = $_GET['ProductID'];
//$count = $_GET['Count'];
////
$cartID  = $_POST['CartID'];
$productID = $_POST['ProductID'];
$count = $_POST['Count'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try {

    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt2 = $conn->prepare("SELECT * FROM Cart WHERE CartID = '$cartID'");
    $stmt2->execute();
    $cart = $stmt2->fetchAll(PDO::FETCH_ASSOC);
    if (count($cart) == 0) {
        echo "Wrong Details";
    } else {
        $stmt3 = $conn->prepare("SELECT * FROM Product WHERE ProductID = '$productID'");
        $stmt3->execute();
        $product = $stmt3->fetchAll(PDO::FETCH_ASSOC);
        if (count($product) == 0) {
            echo "Wrong Product Details";
        } else {
            $stmt6 = $conn->prepare("SELECT * FROM Cart_Product_Count WHERE CartID = '$cartID' AND ProductID = '$productID'");
            $stmt6->execute();
            $existing = $stmt6->fetchAll(PDO::FETCH_ASSOC);
            if (count($existing) == 0) {
                $productPrice = $product[0]['price'];
                $totalPrice = $cart[0]['totalPrice'];
                $newPrice = $totalPrice + ($productPrice * $count);
                $stmt4 = $conn->prepare("UPDATE Cart SET totalPrice = '$newPrice' WHERE CartID = '$cartID'");
                $stmt4->execute();
                echo($count);
                $stmt5 = $conn->prepare("INSERT INTO Cart_Product_Count (CartID, ProductID, Count) 
            VALUES ('$cartID', '$productID', '$count')");
                $stmt5->execute();
            } else {
                $curCount = $existing[0]['Count'];
                $productPrice = $product[0]['price'];
                $totalPrice = $cart[0]['totalPrice'];
                $curCount =  $curCount + $count;
                $newPrice = $totalPrice + ($productPrice * $count);
                $stmt7 = $conn->prepare("UPDATE Cart_Product_Count SET Count = '$curCount' WHERE 
                                                            CartID = '$cartID' AND ProductID = '$productID'");
                $stmt7->execute();
                $stmt8 = $conn->prepare("UPDATE Cart SET totalPrice = '$newPrice' WHERE CartID = '$cartID'");
                $stmt8->execute();

            }
        }
    }
} catch (PDOException$e) {
    echo "Error: " . $e->getMessage();
}

?>