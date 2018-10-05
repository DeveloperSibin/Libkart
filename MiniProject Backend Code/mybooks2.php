<?php 
error_reporting(0);
require "init.php";

$id = $_POST["id"];

//$id = "b1017";

//$sql = "SELECT `bookname`,`author`,`qty` FROM `book` WHERE `bookname`='".$name."' OR `author`='".$name."'";

$sql = "SELECT `bookname`,`author` FROM `book` WHERE `bookid`='".$id."'";

$result = mysqli_query($con, $sql);

$response = array();

while($row = mysqli_fetch_array($result)){
	$response = array("bookname"=>$row[0],"author"=>$row[1]);
}

echo json_encode(array("user_data"=>$response));

?>