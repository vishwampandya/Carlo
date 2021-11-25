<?php
include 'db.php';

$id=uniqid();
$name=$_POST["name"];
$phone=$_POST["phone"];
$email=$_POST["email"];
$gender=$_POST["gender"];
$address=$_POST["address"];
$role=$_POST["role"];
$phone=$_POST["phone"];
$password=$_POST["password"];
$upi=$_POST["upi"];
$query = "INSERT INTO `registration` (`regID`, `Name`, `address`, `role`, `ph`, `email`, `password`, `gender`, `timestamp`,`UPIID`) VALUES ('".$id."', '".$name."', '".$address."', '".$role."', '".$phone."', '".$email."', '".$password."', '".$gender."', CURRENT_TIMESTAMP,'".$upi."');";
if ($sql->query($query) === TRUE) {
 echo "200";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

$sql->close();
?>