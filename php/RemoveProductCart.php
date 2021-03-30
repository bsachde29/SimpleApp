<?php
error_reporting(E_ALL);
$cartID  = $_POST['CartID'];
$productID = $_POST['ProductID'];
$count = $_POST['Count'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
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
            $stmt6 = $conn->prepare("SELECT * FROM Cart_Product_Count WHERE ProductID = '$productID' 
                                       AND CartID = '$cartID'");
            $stmt6->execute();
            $countCheck = $stmt6->fetchAll(PDO::FETCH_ASSOC);
            if ($countCheck[0]['Count'] < $count) {
                echo "Trying to delete too many products";
            } else {
                if ($countCheck[0]['Count'] > $count) {
                    $productPrice = $product[0]['price'];
                    $totalPrice = $cart[0]['totalPrice'];
                    $newCount = $countCheck[0]['Count'] - $count;
                    $newPrice = $totalPrice - ($productPrice * $count);
                    $stmt4 = $conn->prepare("UPDATE Cart SET totalPrice = '$newPrice' WHERE CartID = '$cartID'");
                    $stmt4->execute();
                    $stmt5 = $conn->prepare("UPDATE Cart_Product_Count SET Count = '$newCount' WHERE
                                                    CartID = '$cartID' AND ProductID = '$productID'");
                    $stmt5->execute();
                } else {
                    $productPrice = $product[0]['price'];
                    $totalPrice = $cart[0]['totalPrice'];
                    $newPrice = $totalPrice - ($productPrice * $count);
                    $stmt4 = $conn->prepare("UPDATE Cart SET totalPrice = '$newPrice' WHERE CartID = '$cartID'");
                    $stmt4->execute();
                    $stmt5 = $conn->prepare("DELETE FROM Cart_Product_Count WHERE ProductID = '$productID' 
                                 AND CartID = '$cartID'");
                    $stmt5->execute();
                }
            }
        }
    }
}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();
}

?>