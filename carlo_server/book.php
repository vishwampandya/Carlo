<?php
include 'db.php';

$id=uniqid();
$carID=$_POST["carID"];
$userID=$_POST["userID"];
$ownerID=$_POST["ownerID"];
$price=$_POST["price"];
$pickup=strtotime($_POST["pickup"]);
$drop=strtotime($_POST["drop"]);

$query="SELECT * FROM `car` WHERE carID='".$carID."' AND status='available'";

$result = $sql->query($query);

if ($result->num_rows > 0) {

$query="INSERT INTO `booking`(`bookID`, `carID`, `userID`, `ownerID`, `price`, `pickUP`, `drop_car`) VALUES ('".$id."','".$carID."','".$userID."','".$ownerID."','".$price."','".$pickup."','".$drop."')";

if ($sql->query($query) === TRUE) {
 echo "200";
} else {
    echo "Error: ";
}

   }
 else {
    echo "Car Not Found";
}

$sql->close();
?>