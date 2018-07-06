function addFood(event) {
	event.preventDefault();
	var $form = $("#new-food");
	var data = getFormData($form);
	var s = JSON.stringify(data);
	//window.alert(s);
	$.ajax({
		url: "rest/articles/addFood",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if(data.responseText == "Fill all fields!" || data.responseText == "Name taken! Please choose another one."){
				$( "#resultFood" ).html( data.responseText );
				return;
			}
			showFood();
		}
	});
}

function addDrink(event) {
	event.preventDefault();
	var $form = $("#new-drink");
	var data = getFormData($form);
	var s = JSON.stringify(data);
	//window.alert(s);
	$.ajax({
		url: "rest/articles/addDrink",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if(data.responseText == "Fill all fields!" || data.responseText == "Name taken! Please choose another one."){
				$( "#resultDrink" ).html( data.responseText );
				return;
			}
			showDrink();
		}
	});
}

function addNewDrink(){
	$(".all-articles").hide();
	$(".new-drink-page").show();
}

function addNewFood(){
	$(".all-articles").hide();
	$(".new-food-page").show();
}

function showFood(){
	$(".all-articles").hide();
	$(".show-food-page").show();
	
	$.ajax({
		url: "rest/articles/getFood",
		type:"GET",
		complete: function(data) {
			//window.location.href ="admin.html"
			var articles = JSON.parse(data.responseText);
			
			var str = "<table id=\"foodtable\"><tr><td>Name</td><td>Price ($)</td><td colspan=\"2\">About</td><td>Quantity</td><td></td><td></td></tr>";
			
			for (i = 0; i < articles.length; i++) {
				if(articles[i].active==true){
					var id = articles[i].name;
					str += "<tr>" +
					"<td>"+ id +"</td>" +
					"<td>"+ articles[i].price +"</td>" +
					"<td colspan=\"2\">"+ articles[i].about +"</td>" +
					"<td>"+ articles[i].quantity +"</td>" +
					"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"editArticle(\'"+id + "\', \'" + articles[i].type +"\')\">Edit</button></td>" +
					"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"deleteArticle(\'"+id + "\', \'" + articles[i].type+"\')\">Delete</button></td>" +
					"</tr>";
				}
			}
			str += "</table>";
			var Obj = document.getElementById('foodtable'); //any element to be fully replaced
			if(Obj.outerHTML) { //if outerHTML is supported
			    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
			}
		}
	});
}

function showDrink(){
	$(".all-articles").hide();
	$(".show-drink-page").show();
	
	$.ajax({
		url: "rest/articles/getDrinks",
		type:"GET",
		complete: function(data) {
			//window.location.href ="admin.html"
			var articles = JSON.parse(data.responseText);
			
			var str = "<table id=\"drinktable\"><tr><td>Name</td><td>Price ($)</td><td colspan=\"2\">About</td><td>Quantity</td><td></td><td></td></tr>";
			
			for (i = 0; i < articles.length; i++) {
				if(articles[i].active==true){
					var id = articles[i].name;
					str += "<tr>" +
					"<td>"+ id +"</td>" +
					"<td>"+ articles[i].price +"</td>" +
					"<td colspan=\"2\">"+ articles[i].about +"</td>" +
					"<td>"+ articles[i].quantity +"</td>" +
					"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"editArticle(\'"+id + "\', \'" + articles[i].type +"\')\">Edit</button></td>" +
					"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"deleteArticle(\'"+id + "\', \'" + articles[i].type +"\')\">Delete</button></td>" +
					"</tr>";
				}
			}
			str += "</table>";
			var Obj = document.getElementById('drinktable'); //any element to be fully replaced
			if(Obj.outerHTML) { //if outerHTML is supported
			    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
			}
		}
	});
}

function editArticle(name){
	$(".all-articles").hide();
	$(".edit-page").show();
	
	$.ajax({
		url: "rest/articles/getAll",
		type:"GET",
		complete: function(data) {
			//window.location.href ="admin.html"
			var articles = JSON.parse(data.responseText);
			
			for(var i = 0; i < articles.length; i++){
				if(articles[i].name==name){
					$(".edit-name").val(articles[i].name);
					$(".edit-price").val(articles[i].price);
					$(".edit-about").val(articles[i].about);
					$(".edit-quantity").val(articles[i].quantity);
					var Obj = document.getElementById("confirm-but")
					if(Obj.outerHTML) {
					    Obj.outerHTML="<input type=\"button\" class=\"btn btn-primary\" id=\"confirm-but\" value=\"OK\" onclick=\"confirmEdit(\'"+ articles[i].type +"\')\"/> <br />";
					}
				}
			}
		}
	});
}

function confirmEdit(type){	
	var $form = $("#edit-form");
	var data = getFormData($form);
	var s = JSON.stringify(data);
	//window.alert(s);
	$.ajax({
		url: "rest/articles/edit",
		type:"PUT",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if(data.responseText == "Fill all fields!" || data.responseText == "Name taken! Please choose another one."){
				$( "#resultEditArticle" ).html( data.responseText );
				return;
			}
			if(type == "food"){
				showFood();
			}else if(type == "drink"){
				showDrink();
			}
		}
	});
}

function deleteArticle(name,type){
	$.ajax({
		url: "rest/articles/delete",
		type:"DELETE",
		data: name,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if(type == "food"){
				showFood();
			}else if(type == "drink"){
				showDrink();
			}
		}
	});
}

$("document").ready(function(){
	$(".all-articles").hide();
});

