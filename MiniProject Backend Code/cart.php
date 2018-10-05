<?php 
error_reporting(0);
require "init.php";

$userid = $_POST["userid"];
$bookid = $_POST["bookid"];
$bookname = $_POST["bookname"];

//$userid = "test05";
//$bookid = "b1011";
//$bookname = "Advanced Engineering Mathematics";

//$sql = "SELECT `bookid`,`days`,`rcount` FROM `circulation` WHERE `userid`='".$id."'";

$sql = "INSERT INTO `cart` (`bookid`, `userid`, `bookname`) VALUES ('".$bookid."', '".$userid."', '".$bookname."')";

$result = mysqli_query($con, $sql);

/*$response = array();

while($row = mysqli_fetch_array($result)){
	$response = array("bookid"=>$row[0],"days"=>$row[1]);
}

echo json_encode(array("user_data"=>$response));*/

?>