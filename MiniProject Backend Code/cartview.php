<?php 
error_reporting(0);
require "init.php";

$userid = $_POST["userid"];

//$userid = "14ugit10";

//$sql = "SELECT `bookname`,`author`,`qty` FROM `book` WHERE `bookname`='".$name."' OR `author`='".$name."'";

$sql = "SELECT `bookname` FROM `cart` WHERE `userid`= '".$userid."'";

$result = mysqli_query($con, $sql);

$response = array();

while($row = mysqli_fetch_assoc($result)){
	$response[] = $row;

}

echo json_encode(array("user_data"=>$response));

?>