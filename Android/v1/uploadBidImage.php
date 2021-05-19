<?php


define('DB_NAME','fypdatabase');
define('DB_USER','root');
define('DB_PASSWORD','');
define('DB_HOST','Localhost');


$connect = new mysqli(DB_HOST , DB_USER , DB_PASSWORD , DB_NAME);

if (isset($_POST['bidImage']))
{
	$target_dir = "Images/";
	$bidImage = $_POST["bidImage"];
	$imageStore = rand()."_".time().".jpeg";
	$target_dir = $target_dir."/".$imageStore;
	file_put_contents($target_dir, base64_decode($bidImage));
	$userId = $_POST["userId"];
	$stmt = $connect->prepare("UPDATE bids SET bidImage = '$imageStore' WHERE userId = ?");
	$stmt->bind_param("i", $userId);
	$stmt->execute();

	if ($stmt)
	{
		echo json_encode("Image Uploaded");
	}

	else{
		echo json_encode("Error");
	}
}else
{
	echo json_encode("Missing");
}
