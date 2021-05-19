
  <?php

  class DbOperations{


   private $con;
   function __construct(){

    require_once dirname(__FILE__).'/DbConnect.php';
    $db =  new DbConnect();
    $this->con = $db->connect();

  }

  public function userdetails ($userId, $firstName, $lastName, $phoneNumber , $gender , $dob , $country , $description , $city , $cnic , $province){
    if($this->isNumberExist($phoneNumber)){
      return 0;

    }
    elseif ($this->isUserDetailsExsist($userId)) {
      return 3;
    }else {
        // code...
      $stmt = $this->con->prepare("INSERT INTO `userinfo`(`userId`,`firstName`, `lastName`, `phoneNumber`,`gender`,`dob`,`country`,`description` ,`city` , `cnic` , `province` ) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
      $stmt->bind_param("sssssssssss", $userId, $firstName, $lastName , $phoneNumber , $gender , $dob , $country , $description , $city , $cnic , $province);
      if($stmt->execute()){
       return 1;
     }else {
       return 2;
     }
   }

  }


    // crud
  public function createUser($email , $pass , $userName , $createdAt){
   if($this->isUserExist($email)){
    return 0;

  }else{
   $password = md5($pass);
   $stmt = $this->con->prepare("INSERT INTO `users`(`email`, `password`, `userName`, `createdAt`) VALUES (?,?,?,?);");
   $stmt->bind_param("ssss",$email  ,$password , $userName,$createdAt);
   if($stmt->execute()){
    return 1;
  } else{
   return 2;
  }
  }
  }
  public function userLogin($email, $pass){
   $password = md5($pass);

   $stmt = $this->con->prepare("SELECT userId  FROM users WHERE email = ? AND password = ?");
   $stmt->bind_param("ss",$email,$password);
   $stmt->execute();
   $stmt->store_result();
   return $stmt->num_rows > 0;
  }
  public function getUserByUsername($email){
    $stmt = $this->con->prepare("SELECT * FROM users WHERE email = ?");
    $stmt->bind_param("s",$email);
    $stmt->execute();
    return $stmt->get_result()->fetch_assoc();
  }
  private function isUserDetailsExsist($userId){
   $stmt = $this->con->prepare("SELECT id FROM userinfo WHERE userId = ?");
   $stmt->bind_param("s" , $userId);
   $stmt->execute();
   $stmt->store_result();
   return $stmt->num_rows > 0;
  }


  public function bidDetails($userId ,$bidStatus ,$bidTitle, $bidImage , $bidStartDate , $bidDiscription , $bidMinAmount , $bidDuration ,$bidCategory , $bidEndDate , $bidVerifiedAt)
  {
    $stmt = $this->con->prepare("INSERT INTO `bids`(`userId`,`bidStatus`, `bidTitle`, `bidImage`, `bidStartDate`,
      `bidDiscription`,`bidMinAmount`,`bidDuration`,`bidCategory`,`bidEndDate`,`bidVerifiedAt` ) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
    $stmt->bind_param("sssssssssss", $userId , $bidStatus , $bidTitle , $bidImage ,$bidStartDate ,$bidDiscription ,
      $bidMinAmount , $bidDuration ,$bidCategory , $bidEndDate ,$bidVerifiedAt);
    if($stmt->execute()){
     return 1;
   }else {
     return 2;
   }
  }




  private function isNumberExist($phoneNumber){
    $stmt = $this->con->prepare("SELECT id FROM userinfo WHERE phoneNumber = ?");
    $stmt->bind_param("s" , $phoneNumber);
    $stmt->execute();
    $stmt->store_result();
    return $stmt->num_rows > 0;
  }


  private function isUserExist($email){
   $stmt = $this->con->prepare("SELECT userId FROM users WHERE email = ?");
   $stmt->bind_param("s" , $email);
   $stmt->execute();
   $stmt->store_result();
   return $stmt->num_rows > 0;
  }
  }
