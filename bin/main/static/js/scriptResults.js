$(document).ready(function() {
	$.ajax({
			type : "GET",
			url : "http://localhost:8080/entry",
			headers : {
				"Content-Type" : "application/json"
			},
			success : function(dataTotal) {
				$.each(dataTotal, function (i, data) {
					// set up
					var entry = '';
					entry += '<div id="entry:'+data["id"]+'" class="entry"><div class="box">';

					// images
					var photos = data["photos"]
					$.each(photos, function (j, item) {
						entry += '<div class="recipe image fit"><img align-center src="/img/' + photos[j]["url"] + '" alt="" /></div>';
					});
					
					// recipe title
					entry += '<div class="content">';
					entry += '<header class="align-center"><h2>'+data["recipe"]["name"]+'</h2></header>';
					entry += '<hr />';

					// content
					entry += '<p>Entry ID: ' + data["id"] + '</p>';
					entry += '<p>Entry Comments: ' + data["comments"] + '</p>';
					entry += '<p>Entry Category: ' + data["category"] + '</p>';
					entry += '<p>Recipe Yield: ' + data["recipe"]["yield"] + '</p>';
					entry += 'Recipe Instructions: <blockquote>' + data["recipe"]["instructions"] + '</blockquote>';

					// ingredients
					entry += '<div><p>Ingredients:</p><ul>';
					var ingredients = data["recipe"]["ingredients"];
					$.each(ingredients, function (j, item) {
						entry += '<li>' + ingredients[j]["amount"] + ' ' + ingredients[j]["unit"] + ' ' + ingredients[j]["food"];
					});
					entry += '</ul></div>';

					// go to recipe button
					entry += '<a href="/recipe/'+data["id"]+'" class="go_to_recipe button">Go To Recipe</a>';

					// delete button
					entry += '<a class="delete_entry button">Delete this entry</a>';

					// close tags
					entry += '</div></div></div>';

					$('#entry').append(entry);
				});
			},
			error : function(data) {
				console.log(data);
			}
		});
});