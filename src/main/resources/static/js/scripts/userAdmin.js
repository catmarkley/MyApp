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
	$("#disable").click(function() {
		var usermodel = {
			username : document.getElementById('disableUsername').value
		};
		var requestJSON = JSON.stringify(usermodel);
		$.ajax({
			type : "POST",
			url : "http://localhost:8080/user/disable",
			headers : {
				"Content-Type" : "application/json"
			},
			data : requestJSON,
			success : function(data) {
				$("#resultDisableSuccess").show();
				$("#resultDisableError").hide();
				console.log(data);
			},
			error : function(data) {
				$("#resultDisableError").show();
				$("#resultDisableSuccess").hide();
				console.log(data);
			}
		});
	});
	$("#enable").click(function() {
		var usermodel = {
			username : document.getElementById('enableUsername').value
		};
		var requestJSON = JSON.stringify(usermodel);
		$.ajax({
			type : "POST",
			url : "http://localhost:8080/user/enable",
			headers : {
				"Content-Type" : "application/json"
			},
			data : requestJSON,
			success : function(data) {
				$("#resultEnableSuccess").show();
				$("#resultEnableError").hide();
				console.log(data);
			},
			error : function(data) {
				$("#resultEnableError").show();
				$("#resultEnableSuccess").hide();
				console.log(data);
			}
		});
	});
	$("#changePassword").click(function() {
		var usermodel = {
			username : document.getElementById('passwordUsername').value,
			password : document.getElementById('passwordPassword').value
		};
		var requestJSON = JSON.stringify(usermodel);
		$.ajax({
			type : "POST",
			url : "http://localhost:8080/user/changePassword",
			headers : {
				"Content-Type" : "application/json"
			},
			data : requestJSON,
			success : function(data) {
				$("#resultPasswordSuccess").show();
				$("#resultPasswordError").hide();
				console.log(data);
			},
			error : function(data) {
				$("#resultPasswordError").show();
				$("#resultPasswordSuccess").hide();
				console.log(data);
			}
		});
	});
	$("#changeFirstName").click(function() {
		var usermodel = {
			username : document.getElementById('firstnameUsername').value,
			firstName : document.getElementById('firstName').value
		};
		var requestJSON = JSON.stringify(usermodel);
		$.ajax({
			type : "POST",
			url : "http://localhost:8080/user/changeFirstName",
			headers : {
				"Content-Type" : "application/json"
			},
			data : requestJSON,
			success : function(data) {
				$("#resultFirstNameSuccess").show();
				$("#resultFirstNameError").hide();
				console.log(data);
			},
			error : function(data) {
				$("#resultFirstNameError").show();
				$("#resultFirstNameSuccess").hide();
				console.log(data);
			}
		});
	});
	$("#changeLastName").click(function() {
		var usermodel = {
			username : document.getElementById('lastnameUsername').value,
			lastName : document.getElementById('lastName').value
		};
		var requestJSON = JSON.stringify(usermodel);
		$.ajax({
			type : "POST",
			url : "http://localhost:8080/user/changeLastName",
			headers : {
				"Content-Type" : "application/json"
			},
			data : requestJSON,
			success : function(data) {
				$("#resultLastNameSuccess").show();
				$("#resultLastNameError").hide();
				console.log(data);
			},
			error : function(data) {
				$("#resultLastNameError").show();
				$("#resultLastNameSuccess").hide();
				console.log(data);
			}
		});
	});
	$("#changeRole").click(function() {
		var authmodel = {
			username : document.getElementById('roleUsername').value,
			role : document.getElementById('role').value
		};
		var requestJSON = JSON.stringify(authmodel);
		$.ajax({
			type : "POST",
			url : "http://localhost:8080/user/changeRole",
			headers : {
				"Content-Type" : "application/json"
			},
			data : requestJSON,
			success : function(data) {
				$("#resultRoleSuccess").show();
				$("#resultRoleError").hide();
				console.log(data);
			},
			error : function(data) {
				$("#resultRoleError").show();
				$("#resultRoleSuccess").hide();
				console.log(data);
			}
		});
	});
});