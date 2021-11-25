<?php
include 'db.php';
$id=$_POST["ownID"];
$query="SELECT * FROM `registration` WHERE regID='".$id."' AND role='owner' ";

$result = $sql->query($query);

if ($result->num_rows > 0) {
	echo json_encode($result->fetch_assoc());    
   }
 else {
    echo "User Not Found";
}

$sql->close();
?>