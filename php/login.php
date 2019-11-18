<?php 
	include "conn.php";

	$name = $_POST['name'];
	$pass = $_POST['pass'];

	$sql = "SELECT * FROM user WHERE username= '$name' AND password = '$pass'";
	$result = $conn->query($sql);
	$user = array();
	if ($result->num_rows > 0) {
		$i = 0;
		while ($obj = $result->fetch_assoc()) {
			$user[$i]['username'] = addslashes(htmlentities($obj['username']));
			$i++;
	    }	
	} else {
		echo "error";
		die();
	}
	$conn->close();
	echo json_encode($user);
?>