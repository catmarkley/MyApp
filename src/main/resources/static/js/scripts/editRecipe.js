$(document).ready(function() {
	$("#resultSuccess").hide();
	$("#resultError").hide();

	var id = $("#model").text();
	
	var photos_to_delete = new Set();
	
	$.ajax({
		type : "GET",
		url : "/entry/"+id,
		headers : {
			"Content-Type" : "application/json"
		},
		success : function(data) {
			console.log(data);

			// fill name
			var name = data["recipe"]["name"];
			$("#recipeName").val(name);

			// fill ingredients
			var ingredients = data["recipe"]["ingredients"];
			var ingred_list = $("#recipeIngredient");
			console.log(ingred_list);

			var iters = ingredients.length;
			var j;
			for (j = 0; j < iters; j++) { 
				$(".add_form_field_ingred").click();
			}

			$.each(ingredients, function (j, ingred) {
				var amount = $("#ingredient"+(j+1)+"amount")[0].children[1];
				var unit = $("#ingredient"+(j+1)+"unit")[0].children[1];
				var food = $("#ingredient"+(j+1)+"food")[0].children[1];
				amount.value = ingred["amount"];
				unit.value = ingred["unit"];
				food.value = ingred["food"];
			});

			// fill instructions
			var instructions = data["recipe"]["instructions"];
			$("#recipeInstructions").val(instructions);

			// fill yield
			var yield = data["recipe"]["yield"];
			$("#recipeYield").val(yield);

			// fill comments
			var comments = data["comments"];
			$("#entryComments").val(comments);
			
			// fill category
			var category = data["category"];
			$("#entryCategory").val(category);

			// fill collection
			var collection = data["type"];
			$("#collection").val(collection);

			// fill pictures
			var pictures = data["photos"];
			var pic_list = $("#old_entry_photos");
			$.each(pictures, function (i, photo) {
				pic_list.append('<div class="col-c"><div class="img-container"><img id="picture-'+photo["id"]+'" src="/img/' + photo["url"] + '"></div></div><div class="col-c"><a id="delete_old_photo" class="button default">Delete Photo</a></div>');
			});

			// fill time
		},
		error : function(data) {
			console.log(data);
		}
	});

	$("#old_entry_photos").click(function(event) {
		// if a delete button is clicked, find the click event
		// find the picture ID associated with that click event
		// add the ID to the array of picture IDs to delete
		// delete the JS

		if(event.originalEvent.srcElement.id != "delete_old_photo"){
			return;
		}

		var element_id = event.originalEvent.target.parentElement.previousSibling.firstChild.firstChild.id;
		console.log("Element ID: " + element_id);

		var photo_id = element_id.split("-")[1];

		if(photo_id != null){
			photos_to_delete.add(photo_id);
		}				

		console.log("Clicked delete photo, picture ID set: " + photos_to_delete);

		event.originalEvent.target.parentElement.previousSibling.remove();
		event.originalEvent.target.parentElement.remove();
	});

	$("#saveChanges").click(function() {

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

		// next, delete the previous photos and upload the new ones

		// delete old ones
		// use the set 'photos_to_delete' and post it to the photo data controller, which will call the method to delete photo by ID in the database
		// also have to make sure you delete the photo from the local storage
		var request_photos_to_delete = {
			IDs: Array.from(photos_to_delete)
		}

		var request_photos_to_delete_JSON = JSON.stringify(request_photos_to_delete);

		$.ajax({
			type : "POST",
			url : "http://localhost:8080/photo/delete",
			headers : {
				"Content-Type" : "application/json"
			},
			data: request_photos_to_delete_JSON,
			success : function(text) {
				console.log(text);
				photos_to_delete = new Set();
			},
			error : function(text) {
				console.log(text)
				photos_to_delete = new Set();
			}
		});

		// upload the new files
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
		
		// update the rest of the entry information

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

		var requestJSON = JSON.stringify(entryModel);

		console.log('request data:\n'); 
		console.log(requestJSON);

		$.ajax({
			type : "PUT",
			url : "http://localhost:8080/entry/"+id,
			headers : {
				"Content-Type" : "application/json"
			},
			data: requestJSON,
			success : function(data) {
				console.log(data);

				$(location).attr('href', '/recipe/'+id);
			},
			error : function(data) {
				console.log(data);
		
				var errorElement = document.getElementById('resultError');
				errorElement.innerHTML = "There was an error updating your recipe. Please make sure all fields are filled out correctly";
				$('#resultError').show();

				// if there is an error, delete the uploaded files
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