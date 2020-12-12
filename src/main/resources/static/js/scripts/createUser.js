$(document).ready(function() {
	$("#resultSuccess").hide();
	$("#resultError").hide();
	$("#create_user").click(function() {
		var elements = ["#first_name", "username", "password", "passwordConfirm"];
		error_free = true;
		for (var selector in elements){
			var element = $(selector);
			var valid=element.hasClass("valid");
			var error_element=$("span", element.parent());
			console.log("error element: ", error_element);
			if (!valid){error_element.removeClass("error").addClass("error_show"); error_free=false;}
			else {error_element.removeClass("error_show").addClass("error");}
		}

		if (!error_free){
			console.log("not valid");
			return;
		}
		
		console.log("valid");

		var usermodel = {
			first_name : document.getElementById('first_name').value,
			last_name : document.getElementById('last_name').value,
			username : document.getElementById('username').value,
			password : document.getElementById('password').value
		};
		var requestJSON = JSON.stringify(usermodel);
		/*$.ajax({
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
		});*/
	});
	$("#first_name").on("input", function(){
		var input=$(this);
		var is_name=input.val().trim();
		if(is_name){input.removeClass("invalid").addClass("valid");}
		else{input.removeClass("valid").addClass("invalid");}
	});
	$("#username").on("input", function(){
		var input=$(this);
		var is_name=input.val().trim();
		if(is_name){input.removeClass("invalid").addClass("valid");}
		else{input.removeClass("valid").addClass("invalid");}
	});
	$("#password").on("input", function(){
		var input=$(this);
		var is_name=input.val();
		if(is_name){input.removeClass("invalid").addClass("valid");}
		else{input.removeClass("valid").addClass("invalid");}
	});
	$("#passwordConfirm").on("input", function(){
		var input=$(this);
		var is_name=input.val();
		var password=$("#password").val();
		if(is_name == password){input.removeClass("invalid").addClass("valid");}
		else{input.removeClass("valid").addClass("invalid");}
	});
});