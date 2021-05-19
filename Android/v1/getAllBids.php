<?php


define('DB_NAME','fypdatabase');
define('DB_USER','root');
define('DB_PASSWORD','');
define('DB_HOST','Localhost');

$conn = new mysqli(DB_HOST ,DB_USER ,DB_PASSWORD , DB_NAME);

if (mysqli_connect_errno())
{
    die('Unable to connect with database ' .mysqli_connect_errno() ) ;
}
    $sql = "SELECT * FROM bids ORDER BY bidCreatedAt desc";
    $stmt = $conn->prepare($sql);
    $stmt->execute();
    $result = $stmt->get_result()->fetch_all(MYSQLI_ASSOC);
    echo json_encode($result);
