<?php
require_once '../includes/DbOperations.php';
$response = array();


if($_SERVER['REQUEST_METHOD'] =='POST'){
	if(
		isset($_POST['email']) and
		isset($_POST['password']) and
		isset($_POST['userName']) and
		isset($_POST['createdAt'])
	){
		$db = new DbOperations();

       $result =  $db->createUser(
		                           $_POST['email'],
		                           $_POST['password'],
		                           $_POST['userName'],
															 $_POST['createdAt']
	                        );

		if($result == 1){
        $response['error'] = false;
        $response['message'] = "User registered successfully";
    }elseif($result == 2){
        $response['error'] = true;
        $response['message'] = "some erroe occured please try again";

    } elseif ($result == 0) {
          $response['error'] = true;
          $response['message'] = "it seems you are already registered, please chooose a diferent email and username";

    }

}else{
   $response['error'] = true;
	$response['message'] = "Invalid Request";
}
echo json_encode($response);
}
