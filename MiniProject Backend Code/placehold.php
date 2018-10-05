<?php 
error_reporting(0);
require "init.php";

$userid = $_POST["userid"];
$bookid = $_POST["bookid"];
$bookname = $_POST["bookname"];
$qty = $_POST["qty"];

/*$userid = "test05";
$bookid = "b1011";
$bookname = "Advanced Engineering Mathematics";
$qty = "3";*/

//$sql = "SELECT `bookid`,`days`,`rcount` FROM `circulation` WHERE `userid`='".$id."'";
 	
$sql = "INSERT INTO `circulation` (`bookid`, `userid`, `days`, `rcount`,`bookname`,`status`) VALUES ('".$bookid."', '".$userid."', '7', '2', '".$bookname."','0')";

$result = mysqli_query($con, $sql);

$int = (int)$qty;
--$qty;

$sql1 = "UPDATE `book` SET `qty`='".$qty."' WHERE `bookid`='".$bookid."'";

mysqli_query($con, $sql1);

/*$response = array();

while($row = mysqli_fetch_array($result)){
	$response = array("bookid"=>$row[0],"days"=>$row[1]);
}

echo json_encode(array("user_data"=>$response));*/

?>