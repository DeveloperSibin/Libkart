<?php 
error_reporting(0);
require "init.php";

$userid = $_POST["userid"];
$bookid = $_POST["bookid"];

$userid = "test05";
$bookid = "b1011";

//$sql = "SELECT `bookid`,`days`,`rcount` FROM `circulation` WHERE `userid`='".$id."'";

$sql = "SELECT `bookname` FROM `book` WHERE `bookid`='".$bookid."'";
$result1 = mysqli_query($con, $sql);
while($row = mysqli_fetch_array($result)){
	$response = array("bookid"=>$row[0]);
}
 $sql = "INSERT INTO `cart` (`bookid`, `userid`, `bookname`) VALUES ('".$bookid."', '".$userid."','$response')";
//$sql = "INSERT INTO `cart` (`bookid`, `userid`) VALUES ('".$bookid."', '".$userid."')";

$result = mysqli_query($con, $sql);

/*$response = array();

while($row = mysqli_fetch_array($result)){
	$response = array("bookid"=>$row[0],"days"=>$row[1]);
}

echo json_encode(array("user_data"=>$response));*/

?>

//$bookid = $row[0]
user_data =[{"id":"a"}
	,{"id":"b"}];