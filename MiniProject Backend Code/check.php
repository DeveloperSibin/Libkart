<?php 
error_reporting(0);
require "init.php";

$userid = $_POST["userid"];
/*$bookid = $_POST["bookid"];
$days = $_POST["days"];
$rcount = $_POST["rcount"];*/

//$userid = "test02";
/*$bookid = "b1017";
$days = "4";
$rcount = "1";*/

$sql = "SELECT COUNT(*) FROM `circulation` WHERE `userid`='".$userid."'";
 	
/*$int = (int)$rcount;
--$rcount;
$int = (int)$days;
$days += 7;

$sql1 = "UPDATE `circulation` SET `rcount`='".$rcount."',`days`='".$days."' WHERE `bookid`='".$bookid."' AND `userid`='".$userid."' ";*/

$result = mysqli_query($con, $sql);

$response = array();

while($row = mysqli_fetch_array($result)){
	$response = array("count"=>$row[0]);
}
echo json_encode(array("user_data"=>$response));
?>