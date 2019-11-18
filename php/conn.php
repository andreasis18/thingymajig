<?php 
	header("Access-Control-Allow-Origin: *");
	// Create connection
	$conn = new mysqli("localhost", "penir_c1", "penir", "penir_c1");
	// Check connection
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	} 
?>