<?php
error_reporting(E_ALL);
$email = $_GET['Email'];
$pswd = $_GET['Pswd'];

$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";


try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    $stmt2 = $conn->prepare("SELECT BuyerID from Buyers where Email = '$email'");
    $stmt2->execute();
    $buyer = $stmt2->fetchAll(PDO::FETCH_ASSOC);
    if (count($buyer) == 0) {
        echo "Wrong Details";
    } else {
        $buyerId = $buyer[0]['BuyerID'];
        $passFrom = $buyer[0]['Pswd'];
        if ($passFrom != $pswd) {
            echo "Wrong Details";
        } else {
            echo(json_encode($buyer[0]));
        }
    }


}
catch(PDOException$e) {
    echo "Error: ".$e ->getMessage();

}

?>




