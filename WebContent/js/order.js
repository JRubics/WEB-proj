$(window).load(
    function(event) {
    	$(".admin-button").hide();
    	$(".delivery-button").hide();
    	$(".byer-button").hide();
        isLoggedIn();
        
        $(".show-article-page").hide();
        
        $.ajax({
			url: "rest/articles/getCart",
			type:"GET",
			complete: function(data) {
				if(data.responseText == "no user"){
					window.location = "login.html";
				}else{
					var myMap = JSON.parse(data.responseText);
					var str = "<table id=\"articletable\"><tr><td>Name</td><td>Price</td><td colspan=\"2\">About</td><td>Quantity</td><td>Type</td><td>Count</td></tr>";
					
					var i = 0;
					var empty = false;
					for (var m in myMap){
						empty = true;
						var article = JSON.parse(m);
						if(article.active==true){
							var id = article.name;
							str += "<tr>" +
							"<td>"+ id +"</td>" +
							"<td>"+ article.price +"</td>" +
							"<td colspan=\"2\">"+ article.about +"</td>" +
							"<td>"+ article.quantity +"</td>" +
							"<td>"+ article.type +"</td>" +
							"<td>  <input type=\"number\" class=\"quantity" + id + "\" name=\"quantity\" style=\"width:2em;\" onchange=\"quantityChanged(" + id + ")\" value=" + myMap[m] +"></td>" +
							"</tr>";
						}
						i = i + 1;
					}
					if(empty == true){
						$(".show-article-page").show();
						$(".show-message").hide();
					}else{
						$(".show-article-page").hide();
						$(".show-message").show();
						return;
					}
					str += "</table>";
					var Obj = document.getElementById('articletable'); //any element to be fully replaced
					if(Obj.outerHTML) { //if outerHTML is supported
					    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
					}
					
					$.ajax({
						url: "rest/users/isloggedin",
						type:"GET",
						complete: function(data) {
							if(data.responseText!==""){
								var user = JSON.parse(data.responseText);
								var str1 = "<input type=\"number\" required=\"required\" min=\"0\" max=\""+ user.points +"\" value=\"0\" id=\"point-id\">";
								var Obj = document.getElementById('point-id'); //any element to be fully replaced
								if(Obj.outerHTML) { //if outerHTML is supported
								    Obj.outerHTML=str1; ///it's simple replacement of whole element with contents of str var
								}
							}
						}
					});
				}
			}
		});
});

function quantityChanged(id){
	var quantity = $(".quantity" + id).val();
	
	var s = [id,quantity];
	s = JSON.stringify(s);
	$.ajax({
		url: "rest/articles/setQuantity",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
		}
	});
}

function confirmOrder(event){
	event.preventDefault();
	var note = $(".note-class").val();
	var points = $("#point-id").val();
	s = JSON.stringify([note,points]);
	alert(s);
	$.ajax({
		url: "rest/orders/confirmOrder",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			window.location = "main.html";
		}
	});
}

function confirmAdminOrder(event){
	event.preventDefault();
	var note = $(".note-class").val();
	s = JSON.stringify(note);
	$.ajax({
		url: "rest/orders/confirmAdminOrder",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			window.location = "editadminorder.html";
		}
	});
}


