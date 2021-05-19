<?php
require_once '../includes/DbOperations.php';
$response = array();


if($_SERVER['REQUEST_METHOD'] =='POST'){
	if(
		isset($_POST['userId']) and
		isset($_POST['firstName']) and
		isset($_POST['lastName']) and
		isset($_POST['phoneNumber']) and
		isset($_POST['gender']) and
		isset($_POST['dob']) and
		isset($_POST['country']) and
		isset($_POST['description']) and
		isset($_POST['city']) and
		isset($_POST['cnic']) and
		isset($_POST['province']) 
	){
		$db = new DbOperations();

		$result =  $db-> userdetails(
			$_POST['userId'],
			$_POST['firstName'],
			$_POST['lastName'],
			$_POST['phoneNumber'],
			$_POST['gender'],
			$_POST['dob'],
			$_POST['country'],
			$_POST['description'],
			$_POST['city'],
			$_POST['cnic'],
			$_POST['province']

		);

		if($result == 1){
			$response['error'] = false;
			$response['message'] = "Data added successfully";
		}elseif($result == 2){
			$response['error'] = true;
			$response['message'] = "some erroe occured please try again";

		} elseif ($result == 0) {
			$response['error'] = true;
			$response['message'] = "it seems your number is already register, please chooose a diferent phone number";
		}	elseif ($result == 3) {
			$response['error'] = true;
			$response['message'] = "it seems your details is already register";
		}

	}else{
		$response['error'] = true;
		$response['message'] = "Invalid Request";
	}
	echo json_encode($response);
}
