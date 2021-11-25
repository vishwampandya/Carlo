<?php

include 'db.php';

$json = json_decode(file_get_contents('php://input'),true);
    
    
   
    $name = $json["name"]; //within square bracket should be same as Utils.imageName & Utils.image
	$id=uniqid();
    $image = $json["image"];
    $comp = $json["comp"];
    $model = $json["model"];
    $price = $json["price"];
	$ownID= $json["ownID"];
	$desc=$json["desc"];

	


 
    $response = array();
 
    $decodedImage = base64_decode("$image");
 
    $return = file_put_contents("uploadedFiles/".$name.".JPG", $decodedImage);
	
 
    if($return !== false){
		$query = "INSERT INTO `car`(`carID`, `car_model`, `car_company`, `ownerID`, `photoparth`, `perkmprice`,`description`) VALUES ('".$id."','".$model."','".$comp."','".$ownID."','".$name."','".$price."','".$desc."')";
		if ($sql->query($query) === TRUE) {
		 $response['success'] = 1;
         $response['message'] = "Image Uploaded Successfully";
		
		} else {
		   $response['success'] = 0;
        $response['message'] = "some thing went wrong";
	}

		$sql->close();
		
       
    }else{
        $response['success'] = 0;
        $response['message'] = "Image Uploaded Failed";
    }
 
    echo json_encode($response);
?>