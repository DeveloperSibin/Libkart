<?php 
error_reporting(0);
require "init.php";

$id = $_POST["id"];
$name = $_POST["name"];
$author = $_POST["author"];

//$id = "b100";
//$name = "Comp";
//$author = "Hear";


//$sql = "SELECT `bookname`,`author`,`qty` FROM `book` WHERE `bookname`='".$name."' OR `author`='".$name."'";

$sql = "SELECT `bookname`,`author`,`qty` FROM `book` WHERE `bookname`LIKE '%".$name."%' OR `author`LIKE '%".$author."%' OR `bookid`LIKE '".$id."'";

$result = mysqli_query($con, $sql);

$response = array();

while($row = mysqli_fetch_array($result)){
	$response = array("bookname"=>$row[0],"author"=>$row[1],"qty"=>$row[2]);
}

echo json_encode(array("user_data"=>$response));

?>