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

$sql = "SELECT `bookid`,`bookname`,`author`,`qty` FROM `book` WHERE `bookname`LIKE '%".$name."%' OR `author`LIKE '%".$author."%' OR `bookid`LIKE '".$id."'";

//$sql = "SELECT `bookid`,`bookname`,`author`,`qty` FROM `book` WHERE `bookname`LIKE '%".$name."%'";

$result = mysqli_query($con, $sql);

$response = array();

while($row = mysqli_fetch_assoc($result)){
//	$response = array("bookid"=>$row[0],"bookname"=>$row[1],"author"=>$row[2],"qty"=>$row[3]);	
	$response[] = $row;
}

echo json_encode(array("user_data"=>$response));

?>