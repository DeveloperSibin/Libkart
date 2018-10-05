<?php 
error_reporting(0);
require "init.php";

$userid = $_POST["userid"];

//$userid = "test02";

$sql = "SELECT COUNT(*) FROM `circulation` WHERE `userid`='".$userid."'";
 	

$result = mysqli_query($con, $sql);

$response = array();

while($row = mysqli_fetch_array($result)){
	$response = array("count"=>$row[0]);
}
echo json_encode(array("user_data"=>$response));
?>