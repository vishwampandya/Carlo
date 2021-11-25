<?php
include 'db.php';
$userID=$_POST["userID"];
$query="SELECT * FROM `booking` WHERE userID='".$userID."' ";

$result = $sql->query($query);

if ($result->num_rows > 0) {
	echo json_encode($result->fetch_all(MYSQLI_ASSOC));     
   }
 else {
    echo "User Not Found";
}

$sql->close();
?>