var hasWhiteSpace = function(s) {
	const whitespaceCharacters = [' ', '  ', '\b', '\t', '\n', '\v', '\f', '\r', `\"`, `\'`, `\\`,
	'\u0008', '\u0009', '\u000A', '\u000B', '\u000C', '\u000D', '\u0020','\u0022', '\u0027', '\u005C',
	'\u00A0', '\u2028', '\u2029', '\uFEFF'];
	return whitespaceCharacters.some(char => s.includes(char));
}

var nameHasBadCharacters = function(s) {
	const regex = /[A-Za-z]+/g;
	const found = s.match(regex);
	if (found != null){
		if (found.length != 1 || found[0].length != s.length){return true;}
	} else {
		if (s.length > 0) {return true;}
	}
	return false;
}

var hasBadCharacters = function(s) {
	const regex = /[A-Za-z0-9!@#$%^&*]+/g;
	const found = s.match(regex);
	if (found.length != 1 || found[0].length != s.length){return true;}
	return false;
}

var validateFirstName = function(input){
	// not empty (because it is required)
	// no whitespace
	// only characters a-zA-Z
	// max 20 characters
	if (input == null || input == "") {return "Field must not be empty."}
	if (input.length > 20) {return "Max length is 20 characters."} 
	if (hasWhiteSpace(input)) {return "Field must not contain whitespace."}
	if (nameHasBadCharacters(input)) {return "Input has bad characters. It must contain only letters."}
	return null;
}

var validateLastName = function(input){
	// no whitespace
	// only characters a-zA-Z
	// max 20 characters
	if (input.length > 20) {return "Max length is 20 characters."} 
	if (hasWhiteSpace(input)) {return "Field must not contain whitespace."}
	if (nameHasBadCharacters(input)) {return "Input has bad characters. It must contain only letters."}
	return null;
}

var validateUsername = function(input){
	// not empty (because it is required)
	// no whitespace
	// only characters a-zA-Z0-9!@#$%^&*
	// min 6 characters, max 20 characters
	if (input == null || input == "") {return "Field must not be empty."}
	if (input.length > 20) {return "Max length is 20 characters."} 
	if (input.length < 6) {return "Min length is 6 characters."}
	if (hasWhiteSpace(input)) {return "Field must not contain whitespace."}
	if (hasBadCharacters(input)) {return "Input has bad characters. It must contain only letters, numbers, or !@#$%^&*."}
	return null;
}

var validatePassword = function(input){
	// not empty (because it is required)
	// no whitespace
	// only characters a-zA-Z0-9!@#$%^&*
	// must be at least 6 characters and max 20 characters
	if (input == null || input == "") {return "Field must not be empty."}
	if (input.length > 20) {return "Max length is 20 characters."} 
	if (input.length < 6) {return "Min length is 6 characters."}
	if (hasWhiteSpace(input)) {return "Field must not contain whitespace."}
	if (hasBadCharacters(input)) {return "Input has bad characters. It must contain only letters, numbers, or !@#$%^&*."}
	return null;
}

var validateConfirmPassword = function(input, password){
	// must equal the first password
	if (input != password) {return "The passwords must be equal."}
	return null;
}

var validateElement = function(element){
	var value = document.getElementById(element).value;
	switch(element){
		case "first_name":
		case "firstname":
			return validateFirstName(value)
		case "last_name":
		case "lastname":
			return validateLastName(value)
		case "username":
			return validateUsername(value)
		case "password":
		case "passwordPassword":
			return validatePassword(value)
		case "passwordConfirm":
			return validateConfirmPassword(value, document.getElementById("password").value)
		case "passwordConfirmPassword":
			return validateConfirmPassword(value, document.getElementById("passwordPassword").value)
		default:
			return "Unexpected Error.";
	}
}

var displayErrorChange = function(currentObj, message, id){
	var error_element = document.getElementById(id).parentElement.nextElementSibling.childNodes[1];
	
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