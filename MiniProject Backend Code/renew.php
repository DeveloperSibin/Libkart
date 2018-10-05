<?php 
error_reporting(0);
require "init.php";

$userid = $_POST["userid"];
$bookid = $_POST["bookid"];
$days = $_POST["days"];
$rcount = $_POST["rcount"];

/*$userid = "test02";
$bookid = "b1017";
$days = "4";
$rcount = "1";*/

//$sql = "SELECT `bookid`,`days`,`rcount` FROM `circulation` WHERE `userid`='".$id."'";
 	
$int = (int)$rcount;
--$rcount;
$int = (int)$days;
$days += 7;

$sql1 = "UPDATE `circulation` SET `rcount`='".$rcount."',`days`='".$days."' WHERE `bookid`='".$bookid."' AND `userid`='".$userid."' ";

mysqli_query($con, $sql1);

echo "Succesfull";
?>