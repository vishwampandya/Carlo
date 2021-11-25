<?php
include 'db.php';

$id=uniqid();
$msg=$_POST["msg"];
$uid=$_POST["regID"];
$query = "INSERT INTO `feedback` (`feedbackID`, `message`, `userID`) VALUES ('".$id."','".$msg."','".$uid."');";
if ($sql->query($query) === TRUE) {
 echo "200";
} else {
    echo "Error: <br>" . $sqll=->error;
}

$sql->close();
?>