$(document).ready(function() {
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
				var amount = $("#ingredient"+(j+1)+"amount");
				var unit = $("#ingredient"+(j+1)+"unit");
				var food = $("#ingredient"+(j+1)+"food");
				amount.val(ingred["amount"]);
				unit.val(ingred["unit"]);
				food.val(ingred["food"]);
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
		// first, delete the previous photos and upload the new ones

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
			var food = data.getElementsByClassName("food")[0].value;
			var unit = data.getElementsByClassName("unit")[0].value;
			var amount = data.getElementsByClassName("amount")[0].value;
			if(food != null){
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
		
				var error_div = $('#save_error');
				error_div.append('<p>There was an error updating your recipe. Please make sure all fields are filled out correctly.</p>');

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

	var wrapper_ingred = $(".ingredient");
	var add_button_ingred = $(".add_form_field_ingred");
	var wrapper_photo = $(".photo");
	var add_button_photo = $(".add_form_field_photo");

	var x = 0;
	var y = 0;

	$(add_button_ingred).click(function() {
		x++;
		$(wrapper_ingred).append('<div id="ingredient'+x+'" class="row"><div class="column"><label>Amount:</label><input type="number" id="ingredient'+x+'amount" class="amount"></div><div class="column"><label>Unit:</label><input type="text" id="ingredient'+x+'unit" class="unit"></div><div class="column"><label>Food:</label><input type="text" id="ingredient'+x+'food" class="food"></div><div class="column"><label><br></label><a class="delete_form_field_ingred button default">Delete Ingredient</a></div></div>');
	});

	$(wrapper_ingred).on("click", ".delete_form_field_ingred", function(event) {
		var element = event.originalEvent.srcElement.parentElement.parentElement;
		element.remove();
	});

	$(add_button_photo).click(function() {
		y++;
		$(wrapper_photo).append('<div id="photo'+y+'" class="row"><div class="column-3"><input type="file" name="file" id="file" class="file"></div><div class="column"><a class="delete_form_field_photo button default">Delete Photo</a></div></div>');
	});

	$(wrapper_photo).on("click", ".delete_form_field_photo", function(event) {
		var element = event.originalEvent.srcElement.parentElement.parentElement;
		element.remove();
	});

});