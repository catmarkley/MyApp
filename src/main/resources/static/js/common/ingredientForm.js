var add_ingredient_field = function(x){
	var htmlStr = '<div id="ingredient'+x+'" class="row">';
	htmlStr += '<div class="column" id="ingredient'+x+'amount">';
	htmlStr += '<label>Amount:</label>';
	htmlStr += '<input type="text" class="amount" oninput="amountOnInput(this, \'ingredient'+x+'amount\')">';
	htmlStr += '</div>';
	htmlStr += '<div class="column" id="ingredient'+x+'unit">';
	htmlStr += '<label>Unit:</label>';
	htmlStr += '<input type="text" class="unit" oninput="unitOnInput(this, \'ingredient'+x+'unit\')">';
	htmlStr += '</div>';
	htmlStr += '<div class="column" id="ingredient'+x+'food">';
	htmlStr += '<label>Food:</label>';
	htmlStr += '<input type="text" class="food" oninput="foodOnInput(this, \'ingredient'+x+'food\')">';
	htmlStr += '</div>';
	htmlStr += '<div class="column">';
	htmlStr += '<label><br></label>';
	htmlStr += '<a class="delete_form_field_ingred button default">Delete Ingredient</a>';
	htmlStr += '</div></div>';
	htmlStr += '<div class="row"><span class="error"></span></div>';
	return htmlStr;
}

var add_photo_field = function(y){
	var htmlStr = '<div id="photo'+y+'" class="row">';
	htmlStr += '<div class="column-3">';
	htmlStr += '<input type="file" name="file" id="file" class="file">';
	htmlStr += '</div>';
	htmlStr += '<div class="column">';
	htmlStr += '<a class="delete_form_field_photo button default">Delete Photo</a>';
	htmlStr += '</div></div>';
	return htmlStr;
}