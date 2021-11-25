<?php
include 'db.php';
$email=$_POST["email"];
$password=$_POST["password"];
$query="SELECT `regID`, `Name`, `address`, `role`, `ph`, `email`, `gender`,`UPIID`, `timestamp` FROM `registration` WHERE email LIKE '".$email."' AND password LIKE '".$password."'";
$result = $sql->query($query);

if ($result->num_rows > 0) {
	echo json_encode($result->fetch_assoc());    
   }
 else {
    echo "User Not Found";
}

$sql->close();
?>