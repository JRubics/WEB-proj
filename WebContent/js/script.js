function login(event) {
	event.preventDefault();
	var $form = $("#login");
	var data = getFormData($form);
	var s = JSON.stringify(data);
	$.ajax({
		url: "rest/users/login",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			$( "#resultLogin" ).html( data.responseText );
			if(data.responseText === ""){
				window.location.href = "index.html";
			}
		}
	});
}

function logout(){
	$.ajax({
		url: "rest/users/logout",
		type:"POST",
		complete: function(data) {
			$(".admin-button").hide();
			isLoggedIn();
			window.location = "index.html";
		}
	});
}

function register(event) {
	event.preventDefault();
	var $form = $("#registration");
	var data = getFormData($form);
	var s = JSON.stringify(data);
	$.ajax({
		url: "rest/users/registration",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			$( "#resultRegistration" ).html( data.responseText );
			if(data.responseText === ""){
				window.location.href = "../WEB-project";
			}
		}
	});
}

function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}

function isLoggedIn(){
	$.ajax({
		url: "rest/users/isloggedin",
		type:"GET",
		complete: function(data) {
			if(data.responseText!==""){
				$(".login-class").hide();
				$(".logout-class").show();
				var user = JSON.parse(data.responseText);
				if(user.role=="admin"){
					$(".admin-button").show();
				}
				if(user.role=="deliverer"){
					$(".delivery-button").show();
				}
				if(user.role=="byer"){
					$(".byer-button").show();
				}
//				if(user.role=="byer"){
//					$(".restaurants-button").show();
//				}
			}else{
				$(".logout-class").hide();
				$(".login-class").show();
				$(".not-logged-in").show();
			}
		}
	});
}

function getname(){ //nece trebati,samo testiram
	$.ajax({
		url: "rest/users/isloggedin",
		type:"GET",
		complete: function(data) {
			if(data.responseText!==""){
				var user = JSON.parse(data.responseText);
				window.alert(user.role);
			}else{
				window.alert("nema");
			}
		}
	});	
}