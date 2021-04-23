<?php
error_reporting(E_ALL);
$cartID  = $_POST['CartID'];
$discountCode = $_POST['DiscountCode'];

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
        $cartPrice = $cart[0]['totalPrice'];
        $stmt3 = $conn->prepare("SELECT * FROM Discount WHERE DiscountCode = '$discountCode'");
        $stmt3->execute();
        $discount = $stmt3->fetchAll(PDO::FETCH_ASSOC);
        if (count($discount) == 0) {
            echo "Wrong Discount Details";
        } else {
            $discountID = $discount[0]['DiscountID'];
            $discountValue = $discount[0]['DiscountAmount'];
            if ($discount[0]['FlatPercentage'] == 0) {
                $newAmount = (1-($discountValue / 100)) * ($cartPrice);
                $stmt4 = $conn->prepare("UPDATE Cart SET DiscountID = '$discountID',totalPrice = '$newAmount' WHERE CartID = '$cartID'");
                $stmt4->execute();
                echo "Cart Updated\n";
            } else {
                if (($cartPrice - $discountValue) < 0) {
                    echo "Wrong Discount Details";
                }else{
                    $newAmount = ($cartPrice) - $discountValue;
                    $stmt4 = $conn->prepare("UPDATE Cart SET DiscountID = '$discountID',totalPrice = '$newAmount' WHERE CartID = '$cartID'");
                    $stmt4->execute();
                    echo "Cart Updated\n";
                }

            }

        }
    }
}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();
}

?>