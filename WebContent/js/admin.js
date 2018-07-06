$(window).load(function(){
	$(".admin-button").hide();
	$(".delivery-button").hide();
	$(".byer-button").hide();
    isLoggedIn();
    
	$.ajax({
			url: "rest/users/admin",
			type:"GET",
			complete: function(data) {
				var users = JSON.parse(data.responseText);
				
				var str = "<table id=\"admintable\"><tr><td>Username</td><td>Role</td></tr>"
				
				for (i = 0; i < users.length; i++) {
					var username = users[i].username;
					str += "<tr><td>"+ username +"</td><td id=\'id"+ username +"\'>"+ users[i].role +"</td>" +
							"<td>" +
							"<div class=\"btn-group\" role=\"group\" aria-label=\"Basic example\">" +
							"<button onclick=\"setadmin(\'"+username+"\')\" type=\"button\" class=\"btn btn-secondary\">Admin</button>" +
							"<button onclick=\"setbyer(\'"+username+"\')\" type=\"button\" class=\"btn btn-secondary\">Byer</button>" +
							"<button onclick=\"setdeliverer(\'"+username+"\')\" type=\"button\" class=\"btn btn-secondary\">Deliverer</button>" +
							"</div>" +
							"</td>" +
							"</tr>";
				}
				str += "</table>";
				var Obj = document.getElementById('admintable'); //any element to be fully replaced
				if(Obj.outerHTML) { //if outerHTML is supported
				    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
				}
			}
		});
	}
);



function setadmin(username) {
	var a = [username,"admin"];
	var s = JSON.stringify(a);
	
	$.ajax({
		url: "rest/users/setrole",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			$("#id"+username).text("admin");
		}
	});
}

function setbyer(username) {
	var a = [username,"byer"];
	var s = JSON.stringify(a);
	
	$.ajax({
		url: "rest/users/setrole",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			$("#id"+username).text("byer");
		}
	});
}

function setdeliverer(username) {
	var a = [username,"deliverer"];
	var s = JSON.stringify(a);
	
	$.ajax({
		url: "rest/users/setrole",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			$("#id"+username).text("deliverer");
		}
	});
}







