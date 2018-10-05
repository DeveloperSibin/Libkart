<?php 
error_reporting(0);
require "init.php";

$id = $_POST["id"];
$password = $_POST["password"];


//$id = "14ugit28";

//$password ="a123456#";


$sql = "SELECT * FROM `student` WHERE `userid`='".$id."' AND `password`='".$password."'";

$result = mysqli_query($con, $sql);

$response = array();

while($row = mysqli_fetch_array($result)){
	$response = array("id"=>$row[0],"name"=>$row[1],"password"=>$row[2]);
}

echo json_encode(array("user_data"=>$response));

?>