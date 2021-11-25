<?php
include 'db.php';

$query="SELECT * FROM `car` where status='available'";

$result = $sql->query($query);

if ($result->num_rows > 0) {
	echo json_encode($result->fetch_all(MYSQLI_ASSOC));    
   }
 else {
    echo "NO Car Found";
}

$sql->close();
?>