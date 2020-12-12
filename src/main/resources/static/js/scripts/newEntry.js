$(document).ready(function() {
	$("#addNewEntry").click(function() {
		// first, upload the files
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
			ingredients.push({'food': food, 'unit': unit, 'amount': amount});                
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

				var error_div = $('#add_new_error');
				error_div.append('<p>There was an error creating your recipe. Please make sure all fields are filled out correctly.</p>');

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
	})

	$(add_button_photo).click(function() {
		y++;
		$(wrapper_photo).append('<div id="photo'+y+'" class="row"><div class="column-3"><input type="file" name="file" id="file" class="file"></div><div class="column"><a class="delete_form_field_photo button default">Delete Photo</a></div></div>');
	});

	$(wrapper_photo).on("click", ".delete_form_field_photo", function(event) {
		var element = event.originalEvent.srcElement.parentElement.parentElement;
		element.remove();
	})
});