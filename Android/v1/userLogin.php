<?php
require_once '../includes/DbOperations.php';
$response = array();


if($_SERVER['REQUEST_METHOD'] =='POST'){

    if(isset($_POST['email']) and isset($_POST['password'])){
    	$db = new DbOperations();
    	if($db->userLogin($_POST['email'], $_POST['password'])){
    		$user = $db->getUserByUsername($_POST['email']);
    		$response ['error'] = false;
    		$response['userId'] = $user['userId'];
    		$response['email'] = $user['email'];
        $response['userName'] = $user['userName'];


    	}else{

    		 $response['error'] = true;
             $response['message'] = "Invalid Username or Passwrd";
    	}

    }else{

         $response['error'] = true;
         $response['message'] = "Required fields are missing ";


    }
}
echo json_encode($response);
