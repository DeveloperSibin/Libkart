<?php 
error_reporting(0);
require "init.php";

$id = $_POST["id"];

//$id = "test01";

$sql = "SELECT `bookid`,`bookname` FROM `circulation` WHERE `userid`='".$id."'";

$result = mysqli_query($con, $sql);

$response = array();

while($row = mysqli_fetch_assoc($result)){
	$response[] = $row;
}

echo json_encode(array("user_data"=>$response));

?>