<?php 
error_reporting(0);
require "init.php";

$id = $_POST["id"];
$name = $_POST["name"];

//$id = "14ugit28";

$sql = "SELECT `bookid`,`days`,`rcount`,`status` FROM `circulation` WHERE `userid`='".$id."' AND `bookname`='".$name."'";

$result = mysqli_query($con, $sql);

$response = array();

while($row = mysqli_fetch_array($result)){
	$response = array("bookid"=>$row[0],"days"=>$row[1],"count"=>$row[2],"status"=>$row[3]);
}

echo json_encode(array("user_data"=>$response));

?>