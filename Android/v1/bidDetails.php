<?php
require_once '../includes/DbOperations.php';
$response = array();


if($_SERVER['REQUEST_METHOD'] =='POST'){
	if(
		isset($_POST['userId']) and
		isset($_POST['bidStatus']) and
		isset($_POST['bidTitle']) and
		isset($_POST['bidImage']) and
		isset($_POST['bidStartDate']) and
		isset($_POST['bidDiscription']) and
		isset($_POST['bidMinAmount']) and
		isset($_POST['bidDuration']) and
		isset($_POST['bidCategory']) and
		isset($_POST['bidEndDate']) and
		isset($_POST['bidVerifiedAt'])

	){
		$db = new DbOperations();

		$result =  $db-> bidDetails(
			$_POST['userId'],
			$_POST['bidStatus'],
			$_POST['bidTitle'],
			$_POST['bidImage'],
			$_POST['bidStartDate'],
			$_POST['bidDiscription'],
			$_POST['bidMinAmount'],
			$_POST['bidDuration'],
			$_POST['bidCategory'],
			$_POST['bidEndDate'],
			$_POST['bidVerifiedAt']
		);

		if($result == 1){
			$response['error'] = false;
			$response['message'] = "Data added successfully";
		}elseif($result == 2){
			$response['error'] = true;
			$response['message'] = "some erroe occured please try again";

		}

	}else{
		$response['error'] = true;
		$response['message'] = "Invalid Request";
	}
	echo json_encode($response);
}
