$(document).ready(function() {
	var id = $("#model").text();
	document.getElementById("edit_recipe_button").href = "/edit/"+id;
	
	$.ajax({
		type : "GET",
		url : "/entry/"+id,
		headers : {
			"Content-Type" : "application/json"
		},
		success : function(data) {
			console.log(data);

			// set up name
			var name = data["recipe"]["name"];
			console.log('recipe name: ' + name);
			$("#recipe_name").text(name);

			// set up ingredients
			var ingredients = data["recipe"]["ingredients"];
			console.log('ingredient 1: ' + ingredients[0]);
			var ingred_list = $("#recipe_ingredients");
			$.each(ingredients, function (i, ingred) {
				ingred_list.append('<li>'+ingred["amount"] + ' ' + ingred["unit"] + ' ' + ingred["food"] + '</li>');
			});

			// set up instructions
			var instructions = data["recipe"]["instructions"];
			console.log("instructions: " + instructions);
			$("#recipe_instructions").text(instructions);

			// set up comments
			var comments = data["comments"];
			console.log("comments: " + comments);
			$("#recipe_comments").text(comments);

			// set up pictures
			var pictures = data["photos"];
			console.log("photos: " + pictures);
			var pic_list = $("#recipe_photos");
			$.each(pictures, function (i, photo) {
				pic_list.append('<div class="col-c"><div class="img-container"><img src="/img/' + photo["url"] + '"></div></div>');
			});

			// set up category, yield, and time
		},
		error : function(data) {
			console.log(data);
		}
	});

	var delete_recipe = function(){
		// create pop-up to confirm
		var confirmation = confirm("Are you sure you want to delete this entry?");
		if (confirmation == false) {
			return;
		}

		var id = $("#model").text();

		$.ajax({
				type : "DELETE",
				url : 'http://localhost:8080/entry/'+id,
				headers : {
					"Content-Type" : "application/json"
				},
				success : function(data) {
					console.log(data);
				},
				error : function(data) {
					console.log(data);
				}
			});
			
		$(location).attr('href', '/home');
	};
});