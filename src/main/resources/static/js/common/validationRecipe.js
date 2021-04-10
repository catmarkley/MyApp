var hasBadCharacters = function(s) {
	const regex = /[A-Za-z0-9!@#$&%)(\]\[*\\-\\+=} {\\;:"/?<>.,`~]+/g;
	const found = s.match(regex);
	if (found != null && (found.length != 1 || found[0].length != s.length)){return true;}
	else if (found == null && s.length > 0) {return true;}
	return false;
}

var hasBadCharactersYield = function(s){
	const regex = /[A-Za-z0-9#%&( )+\\-\\=/\\"':;,?~]+/g;
	const found = s.match(regex);
	if (found != null && (found.length != 1 || found[0].length != s.length)){return true;}
	else if (found == null && s.length > 0) {return true;}
	return false;
}

var hasBadCharactersCategory = function(s){
	const regex = /[A-Za-z0-9'&+, /\\-]+/g;
	const found = s.match(regex);
	if (found != null && (found.length != 1 || found[0].length != s.length)){return true;}
	else if (found == null && s.length > 0) {return true;}
	return false;
}

var hasBadCharactersAmount = function(s){
	const regex = /[A-Za-z0-9#$%+=\\-\\/\\"' <>:,.~]+/g;
	const found = s.match(regex);
	if (found != null && (found.length != 1 || found[0].length != s.length)){return true;}
	else if (found == null && s.length > 0) {return true;}
	return false;
}

var hasBadCharactersUnit= function(s){
	const regex = /[A-Za-z ]+/g;
	const found = s.match(regex);
	if (found != null && (found.length != 1 || found[0].length != s.length)){return true;}
	else if (found == null && s.length > 0) {return true;}
	return false;
}

var hasBadCharactersFood = function(s){
	const regex = /[A-Za-z!@#$%&^ /\\"',\\-\\+:;]+/g;
	const found = s.match(regex);
	if (found != null && (found.length != 1 || found[0].length != s.length)){return true;}
	else if (found == null && s.length > 0) {return true;}
	return false;
}

// All of these are allowed to contain whitespace

var validateRecipeName = function(input){
	// must not be blank
	// must contain only letters, numbers, or !@#%$&()[]*-+={}\;:"'/?<>.,`~
	// maximum 100 characters
	if (input == null || input == "") {return "Field must not be empty."}
	if (input.length > 100) {return "Max length is 100 characters."} 
	if (hasBadCharacters(input)) {return "Input has bad characters. It must contain only letters, numbers, or !@#%$&()[]*-+={}\\;:\"'/?<>.,`~"};
}

var validateInstructions = function(input){
	// must only contain letters, numbers, or !@#%$&()[]*-+={}\;:"'/?<>.,`~
	// It is allowed to be blank
	// maximum 3000 characters
	if (input.length > 3000) {return "Max length is 3000 characters."} 
	if (hasBadCharacters(input)) {return "Input has bad characters. It must contain only letters, numbers, or !@#%$&()[]*-+={}\\;:\"'/?<>.,`~"};
}

var validateYield = function(input){
	// It must contain letters, numbers, or #%&()+-=/\"':;,?~
	// It is allowed to be blank
	// maximum 100 characters
	if (input.length > 100) {return "Max length is 100 characters."} 
	if (hasBadCharactersYield(input)) {return "Input has bad characters. It must contain only letters, numbers, or #%&()+-=/\"':;,?~"};
}

var validateComments = function(input){
	// must only contain letters, numbers, or !@#%$&()[]*-+={}\;:"'/?<>.,`~
	// Is allowed to be blank
	// maximum 1000 characters
	if (input.length > 1000) {return "Max length is 1000 characters."} 
	if (hasBadCharacters(input)) {return "Input has bad characters. It must contain only letters, numbers, or !@#%$&()[]*-+={}\\;:\"'/?<>.,`~"};
}

var validateCategory = function(input){
	// must only contain letters, numbers, or '&+,/-
	// It is allowed to be blank
	// maximum 100 characters
	if (input.length > 100) {return "Max length is 100 characters."} 
	if (hasBadCharactersCategory(input)) {return "Input has bad characters. It must contain only letters, numbers, or '&+,/-"};
}

var validateIngredientAmount = function(input){
	// must only contain letters, numbers, or ~#$%/\<>+=-,.:"'
	// It is allowed to be blank
	// maximum 20 characters
	if (input.length > 20) {return "Amount field max length is 20 characters."} 
	if (hasBadCharactersAmount(input)) {return "Amount input has bad characters. It must contain only letters, numbers, or ~#$%/\\<>+=-,.:\"'"};
}

var validateIngredientUnit = function(input){
	// must contain only letters
	// It is allowed to be blank
	// maximum 50 characters
	if (input.length > 50) {return "Unit field max length is 50 characters."} 
	if (hasBadCharactersUnit(input)) {return "Unit input has bad characters. It must contain only letters."};
}

var validateIngredientFood = function(input){
	// must not be blank
	// maximum 100 characters
	// must contain only letters, or !@#$%&^/\"',-+:;
	if (input == null || input == "") {return "Food field must not be empty."}
	if (input.length > 100) {return "Food field max length is 100 characters."} 
	if (hasBadCharactersFood(input)) {return "Food input has bad characters. It must contain only letters or !@#$%&^/\\\"',-+:;"};
}

var validateIngredients = function(){
	var result = "";
	$.each($(".ingredient").children(), function(i, data) {
		if(data.id != ""){
			if(data.getElementsByClassName("amount").length > 0){
				var result1 = validateIngredientAmount(data.getElementsByClassName("amount")[0].value);
				result = result1 == null ? result : result + result1;
			}
			if(data.getElementsByClassName("unit").length > 0){
				var result2 = validateIngredientUnit(data.getElementsByClassName("unit")[0].value);
				result = result2 == null ? result : result + result2;
			}
			if(data.getElementsByClassName("food").length > 0){
				var result3 = validateIngredientFood(data.getElementsByClassName("food")[0].value);
				result = result3 == null ? result : result + result3;
			}
		}
	});
	return result == "" ? null : result;
}

var validateElement = function(element){
	var value = null;
	if (element != ".ingredient"){
		value = document.getElementById(element).value;
	}
	switch(element){
		case "recipeName":
			return validateRecipeName(value)
		case "recipeInstructions":
			return validateInstructions(value)
		case "recipeYield":
			return validateYield(value)
		case "entryComments":
			return validateComments(value)
		case "entryCategory":
			return validateCategory(value)
		case ".ingredient":
			return validateIngredients();
		default:
			return "Unexpected Error.";
	}
}

var displayErrorChange = function(currentObj, message, id){
	var error_elements = document.getElementById(id).parentElement.nextElementSibling.childNodes;
	var error_element;
	if (error_elements.item(0).nodeName.includes("SPAN")){
		error_element = error_elements[0];
	}
	else {
		error_element = error_elements[1];
	}
	
	if (message != null){
		$(currentObj).removeClass("valid").addClass("invalid");
		error_element.innerHTML = message;
		$(error_element).removeClass("error").addClass("error_show");
	}
	else {
		$(currentObj).removeClass("invalid").addClass("valid");
		error_element.innerHTML = null;
		$(error_element).removeClass("error_show").addClass("error");
	}
}