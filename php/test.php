<?php
error_reporting(E_ALL);
$name  = $_GET['name'];




$servername = "selldb.cqt5tgj7qyws.us-east-2.rds.amazonaws.com";
$username = "simpledb";
$password = "sell1234";
$dbname = "simpledb";
//echo "123";
//fwrite($f, "\npoint 1\n");

try{
    $conn = new PDO("mysql:host=$servername;dbname=$dbname",$username, $password);
    //echo "YAYYYY";

    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $stmt = $conn->prepare("SELECT * From Buyers");
    $stmt->execute();
    //echo "\n\n\n\n";
    //echo $name;
    $value = $stmt->fetchAll(PDO::FETCH_ASSOC);
    //$retVal = $value->qty;
    echo (json_encode($value));
}
catch(PDOException$e) {

    echo "Error: ".$e ->getMessage();
}




//     // Create connection:w
//     $conn = new mysqli($servername, $username, $password, $dbname);
//     // Check connection
//     if ($conn->connect_error) {
//       die("Connection failed: " . $conn->connect_error);
//     }
//
//     $sql = "INSERT INTO orders (Item name)
//     VALUES ('JOHn')";
//
//     if ($conn->query($sql) === TRUE) {
//       echo "New record created successfully";
//     } else {
//       echo "Error: " . $sql . "<br>" . $conn->error;
//     }
?>
