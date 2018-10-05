<?php 
error_reporting(0);
require "init.php";

$name = $_POST["name"];

//$name = "Sibin Blessen";

$sql = "SELECT `username`,`userid`,`branch`,`semester`,`email`,`phone` FROM `student` WHERE `username`='".$name."'";

$result = mysqli_query($con, $sql);

$response = array();

while($row = mysqli_fetch_array($result)){
	$response = array("name"=>$row[0],"id"=>$row[1],"branch"=>$row[2],"semester"=>$row[3],"email"=>$row[4],"phone"=>$row[5]);
}

echo json_encode(array("user_data"=>$response));

?>