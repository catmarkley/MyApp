$(document).ready(function() {
	$("#resultSuccess").hide();
	$("#resultError").hide();
	$("#addNewEntry").click(function() {
		// first, validate the entry information
		var elements = ["recipeName", "recipeInstructions", "recipeYield", "entryComments", "entryCategory", ".ingredient"];
		error_free = true;
		elements.forEach(function(element){
			var message = validateElement(element)
			error_free = error_free && message == null;
		})

		if (!error_free){
			$("#resultSuccess").hide();
			var errorElement = document.getElementById('resultError');
			errorElement.innerHTML = "Error creating entry.";
			$("#resultError").show();
			return;
		}
		$("#resultError").hide();

		// next, upload the files
		var fd = new FormData(); 
		$.each($(".photo").children(), function(i, data) {
			var file = data.getElementsByClassName("file")[0].files[0];
			if(file != null){
				fd.append('file', file); 
				console.log("Added file");
			} else {
				console.log("Did not add file");
			}          
		});

		var filepaths = null;
		// post them to the 'upload' endpoint
		$.ajax({
				type : "POST",
				url : 'http://localhost:8080/img',
				data: fd,
				enctype: 'multipart/form-data',
				processData: false,
				contentType: false,
				async: false,
				success : function(data) {
					console.log(data);
					filepaths = data;
				},
				error : function(data) {
					console.log(data);
				}
			});

		// organize the entry information
		var ingredients = [];
		var photos = [];
		var entryModel = {
			name : document.getElementById('recipeName').value,
			instructions : document.getElementById('recipeInstructions').value,
			yield : document.getElementById('recipeYield').value,
			comments : document.getElementById('entryComments').value,
			collection : $("#collection").val(),
			category : document.getElementById('entryCategory').value,
			ingredients : ingredients,
			photos: photos
		};
		$.each($(".ingredient").children(), function(i, data) {
			if(data.id != "") {
				var food = data.getElementsByClassName("food")[0].value;
				var unit = data.getElementsByClassName("unit")[0].value;
				var amount = data.getElementsByClassName("amount")[0].value;
				ingredients.push({'food': food, 'unit': unit, 'amount': amount}); 
			}               
		});

		if(filepaths != null && Array.isArray(filepaths)){
			$.each(filepaths, function(i, data) {
				photos.push({'path': data});
			});
		}

		console.log("Request: " + entryModel);
		var requestJSON = JSON.stringify(entryModel);

		$.ajax({
			type : "POST",
			url : "http://localhost:8080/entry",
			headers : {
				"Content-Type" : "application/json"
			},
			data: requestJSON,
			success : function(id) {
				console.log(id);

				$(location).attr('href', '/home');
			},
			error : function(text) {
				console.log(text);
				
				var form = document.getElementById("new_entry_form");
				form.reset();

				var errorElement = document.getElementById('resultError');
				errorElement.innerHTML = "There was an error creating your recipe. Please make sure all fields are filled out correctly.";
				$('#resultError').show();

				// delete the uploaded files
				if(filepaths != null){
					var requestModel_Delete = {
						"names": filepaths
					}
					var requestJSON_Delete = JSON.stringify(requestModel_Delete);
					$.ajax({
						type : "POST",
						url : 'http://localhost:8080/img/delete',
						headers : {
							"Content-Type" : "application/json"
						},
						data: requestJSON_Delete,
						success : function(data) {
							console.log(data);
						},
						error : function(data) {
							console.log(data);
						}
					});
				}
			}
		});
	});

	$("#recipeName").on("input", function(){
		var input=$(this).val();
		var message = validateRecipeName(input);
		displayErrorChange(this, message, "recipeName");
	});

	$("#recipeInstructions").on("input", function(){
		var input=$(this).val();
		var message = validateInstructions(input);
		displayErrorChange(this, message, "recipeInstructions");
	});

	$("#recipeYield").on("input", function(){
		var input=$(this).val();
		var message = validateYield(input);
		displayErrorChange(this, message, "recipeYield");
	});

	$("#entryComments").on("input", function(){
		var input=$(this).val();
		var message = validateComments(input);
		displayErrorChange(this, message, "entryComments");
	});

	$("#entryCategory").on("input", function(){
		var input=$(this).val();
		var message = validateCategory(input);
		displayErrorChange(this, message, "entryCategory");
	});

	var x = 0;
	var y = 0;

	$(".add_form_field_ingred").click(function() {
		var htmlStr = add_ingredient_field(++x);
		$(".ingredient").append(htmlStr);
	});

	$(".ingredient").on("click", ".delete_form_field_ingred", function(event) {
		var element = event.originalEvent.srcElement.parentElement.parentElement;
		element.remove();
	})

	$(".add_form_field_photo").click(function() {
		var htmlStr = add_photo_field(++y);
		$(".photo").append(htmlStr);
	});

	$(".photo").on("click", ".delete_form_field_photo", function(event) {
		var element = event.originalEvent.srcElement.parentElement.parentElement;
		element.remove();
	})
});

function amountOnInput(element, id){
	var input=$(element).val();
	var message = validateIngredientAmount(input);
	displayErrorChange(element, message, id);
}

function unitOnInput(element, id){
	var input=$(element).val();
	var message = validateIngredientUnit(input);
	displayErrorChange(element, message, id);
}

function foodOnInput(element, id){
	var input=$(element).val();
	var message = validateIngredientFood(input);
	displayErrorChange(element, message, id);
}