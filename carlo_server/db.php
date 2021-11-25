<?php
$sql = new mysqli("localhost","root","mysql","carlo");
// Check connection
if ($mysqli -> connect_errno) {
  echo "Failed to connect to MySQL: " . $mysqli -> connect_error;
  exit();
}


?>
