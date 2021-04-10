$(document).ready(function() {
	$("#resultSuccess").hide();
	$("#resultError").hide();
	$("#create_user").click(function() {
		var elements = ["first_name", "last_name", "username", "password", "passwordConfirm"];
		error_free = true;
		elements.forEach(function(element){
			var message = validateElement(element)
			error_free = error_free && message == null;
		})

		if (!error_free){
			$("#resultSuccess").hide();
			$("#resultError").show();
			return;
		}
		$("#resultError").hide();

		var usermodel = {
			first_name : document.getElementById('first_name').value,
			last_name : document.getElementById('last_name').value,
			username : document.getElementById('username').value,
			password : document.getElementById('password').value
		};
		var requestJSON = JSON.stringify(usermodel);
		$.ajax({
			type : "POST",
			url : "http://localhost:8080/user/create",
			headers : {
				"Content-Type" : "application/json"
			},
			data : requestJSON,
			success : function(data) {
				$("#resultSuccess").show();
				$("#resultError").hide();
				console.log(data);
			},
			error : function(data) {
				$("#resultError").show();
				$("#resultSuccess").hide();
				console.log(data);
			}
		});
	});

	$("#first_name").on("input", function(){
		var input=$(this).val();
		var message = validateFirstName(input);
		displayErrorChange(this, message, "first_name");
	});

	$("#last_name").on("input", function(){
		var input=$(this).val();
		var message = validateLastName(input);
		displayErrorChange(this, message, "last_name");
	});

	$("#username").on("input", function(){
		var input=$(this).val();
		var message = validateUsername(input);
		displayErrorChange(this, message, "username");
	});

	$("#password").on("input", function(){
		var input=$(this).val();
		var message = validatePassword(input);
		displayErrorChange(this, message, "password");
	});

	$("#passwordConfirm").on("input", function(){
		var input=$(this).val();
		var message = validateConfirmPassword(input, $("#password").val());
		displayErrorChange(this, message, "passwordConfirm");
	});
});