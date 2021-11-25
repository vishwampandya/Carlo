<?php
include 'db.php';
$id=$_POST["regID"];
$name=$_POST["name"];
$ph=$_POST["phone"];
$email=$_POST["email"];
$add=$_POST["add"];
$upi=$_POST["upi"];
$gender=$_POST["gender"];
$query="UPDATE `registration` SET `Name`='".$name."',`address`='".$add."',`ph`='".$ph."',`email`='".$email."',`gender`='".$gender."',`UPIID`='".$upi."' WHERE regID='".$id."'";

$result = $sql->query($query);

if ($result===true) {
	
$query="SELECT `regID`, `Name`, `address`, `role`, `ph`, `email`, `gender`,`UPIID`, `timestamp` FROM `registration` WHERE regID='".$id."'";
 $result = $sql->query($query);

	echo json_encode($result->fetch_assoc());    
   
   }
 else {
    echo "404";
}

$sql->close();
?>
