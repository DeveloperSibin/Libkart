<?php 
error_reporting(0);
require "init.php";

$name = $_POST["name"];

//$name = "gineerin";

//$sql = "SELECT `bookname`,`author`,`qty` FROM `book` WHERE `bookname`='".$name."' OR `author`='".$name."'";

$sql = "SELECT `bookid`,`bookname`,`author`,`qty` FROM `book` WHERE `bookname`LIKE '%".$name."%' OR `author`LIKE '%".$name."%'";

$result = mysqli_query($con, $sql);

$response = array();

while($row = mysqli_fetch_assoc($result)){
//	$response = array("bookid"=>$row[0],"bookname"=>$row[1],"author"=>$row[2],"qty"=>$row[3]);	
$response[] = $row;
}

echo json_encode(array("user_data"=>$response));

?>