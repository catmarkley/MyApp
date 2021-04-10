$(document).ready(function() {
	$.get("http://localhost:8080/user", function(data, status){
		var row = '';
		$.each(data, function (i, item) {
			row += '<tr><td>' + data[i]['id'] + '</td>';
			row += '<td>' + data[i]['username'] + '</td>';
			row += '<td>' + data[i]['enabled'] + '</td>';
			row += '<td>' + data[i]['role'] + '</td>';
			row += '<td>' + data[i]['firstName'] + '</td>';
			row += '<td>' + data[i]['lastName'] + '</td></tr>';
		});
		$('#userData').append(row);
	});
	$('.message').hide();

	// ----- Request Functions -----

	$("#disable").click(function() {
		var usermodel = {
			username : document.getElementById('disableUsername').value
		};
		var requestJSON = JSON.stringify(usermodel);
		makeRequest("disable", requestJSON);
	});
	$("#enable").click(function() {
		var usermodel = {
			username : document.getElementById('enableUsername').value
		};
		var requestJSON = JSON.stringify(usermodel);
		makeRequest("enable", requestJSON);
	});
	$("#changePassword").click(function() {
		if (validateElement("passwordPassword") != null || validateElement("passwordConfirmPassword") != null){
			$("#passwordResultSuccess").hide();
			$("#passwordResultError").show();
			return;
		}

		var usermodel = {
			username : document.getElementById('passwordUsername').value,
			password : document.getElementById('passwordPassword').value
		};
		var requestJSON = JSON.stringify(usermodel);
		makeRequest("password", requestJSON);
	});
	$("#changeFirstName").click(function() {
		if (validateElement("firstname") != null){
			$("#firstNameResultSuccess").hide();
			$("#firstNameResultError").show();
			return;
		}

		var usermodel = {
			username : document.getElementById('firstnameUsername').value,
			firstName : document.getElementById('firstname').value
		};
		var requestJSON = JSON.stringify(usermodel);
		makeRequest("firstName", requestJSON);
	});
	$("#changeLastName").click(function() {
		if (validateElement("lastname") != null){
			$("#lastNameResultSuccess").hide();
			$("#lastNameResultError").show();
			return;
		}

		var usermodel = {
			username : document.getElementById('lastnameUsername').value,
			lastName : document.getElementById('lastname').value
		};
		var requestJSON = JSON.stringify(usermodel);
		makeRequest("lastName", requestJSON);
	});
	$("#changeRole").click(function() {
		var authmodel = {
			username : document.getElementById('roleUsername').value,
			role : document.getElementById('role').value
		};
		var requestJSON = JSON.stringify(authmodel);
		makeRequest("role", requestJSON);
	});

	// ----- Validation Functions For Input -----

	$("#passwordPassword").on("input", function(){
		var input=$(this).val();
		var message = validatePassword(input);
		displayErrorChange(this, message, "passwordPassword");
	});
	$("#passwordConfirmPassword").on("input", function(){
		var input=$(this).val();
		var message = validateConfirmPassword(input, $("#passwordPassword").val());
		displayErrorChange(this, message, "passwordConfirmPassword");
	});
	$("#firstname").on("input", function(){
		var input=$(this).val();
		var message = validateFirstName(input);
		displayErrorChange(this, message, "firstname");
	});
	$("#lastname").on("input", function(){
		var input=$(this).val();
		var message = validateLastName(input);
		displayErrorChange(this, message, "lastname");
	});
});

var makeRequest = function(item, requestData){
	$.ajax({
		type : "POST",
		url : "http://localhost:8080/user/" + item,
		headers : {
			"Content-Type" : "application/json"
		},
		data : requestData,
		success : function(data) {
			$("#" + item + "ResultSuccess").show();
			$("#" + item + "ResultError").hide();
			console.log(data);
		},
		error : function(data) {
			$("#" + item + "ResultError").show();
			$("#" + item + "ResultSuccess").hide();
			console.log(data);
		}
	});
}