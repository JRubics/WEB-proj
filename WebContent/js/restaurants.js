function addRestaurant(type) {
	var $form = $("#new-restaurant");
	var data = getFormData($form);
	var s = JSON.stringify(data);
	$.ajax({
		url: "rest/restaurants/addRestaurant/"+type,
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if(data.responseText == "Fill all fields!" || data.responseText == "Name taken! Please choose another one."){
				$( "#resultRestaurant" ).html( data.responseText );
				return;
			}
			if(type == "local"){
				showLocal();
			}else if(type == "grill"){
				showGrill();
			}else if(type == "indian"){
				showIndian();
			}
			else if(type == "chinese"){
				showChinese();
			}
			else if(type == "sweets"){
				showSweets();
			}
			else if(type == "italian"){
				showItalian();
			}
		}
	});
}

function favoriteRestaurant(id){
	var s = JSON.stringify(id);
	$.ajax({
		url: "rest/restaurants/favorite",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if(data.responseText=="no user"){
				window.location = "login.html";
			}
		}
	});
}

function orderArticle(id){
	var s = JSON.stringify(id);
	$.ajax({
		url: "rest/articles/order",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if(data.responseText=="no user"){
				window.location = "login.html";
			}
		}
	});
}

function showLocal(){
	show("local");
}

function showGrill(){
	show("grill");
}

function showChinese(){
	show("chinese");
}

function showIndian(){
	show("indian");
}

function showSweets(){
	show("sweets");
}

function showItalian(){
	show("italian");
}

function show(type){
	$(".all-articles").hide();
	$(".show-"+type+"-page").show();
	$.ajax({
		url: "rest/restaurants/get/"+type,
		type:"GET",
		complete: function(data) {
			var restaurants = JSON.parse(data.responseText);
			
			var str = "<table id=\""+type+"table\"><tr>" +
						"<td>Name</td>" +
						"<td>Adress</td>" +
						"<td></td>" +
						"<td></td>" +
						"</tr>";
			
			for (i = 0; i < restaurants.length; i++) {
				if(restaurants[i].active==true){
					var id = restaurants[i].name;
					str += "<tr>" +
					"<td>"+ id +"</td>" +
					"<td>"+ restaurants[i].adress +"</td>" +
					"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"editRestaurant(\'"+id + "\', \'" + restaurants[i].type +"\')\">Edit</button></td>" +
					"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"deleteRestaurant(\'"+id + "\', \'" + restaurants[i].type+"\')\">Delete</button></td>" +
					"</tr>";
				}
			}
			str += "</table>";
			var Obj = document.getElementById(type+'table'); //any element to be fully replaced
			if(Obj.outerHTML) { //if outerHTML is supported
			    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
			}
		}
	});
}

function showLocalMainPage(){
	showMainPage("local");
}

function showGrillMainPage(){
	showMainPage("grill");
}

function showChineseMainPage(){
	showMainPage("chinese");
}

function showIndianMainPage(){
	showMainPage("indian");
}

function showSweetsMainPage(){
	showMainPage("sweets");
}

function showItalianMainPage(){
	showMainPage("italian");
}

function showMainPage(type){
	$(".all-articles").hide();
	$(".show-"+type+"-page").show();
	$.ajax({
		url: "rest/restaurants/get/"+type,
		type:"GET",
		complete: function(data) {

			var restaurants = JSON.parse(data.responseText);
			
			var str = "<table id=\""+type+"table\"><tr>" +
						"<td>Name</td>" +
						"<td>Adress</td>" +
						"<td></td>" +
						"<td></td>" +
						"</tr>";
			
			for (i = 0; i < restaurants.length; i++) {
				if(restaurants[i].active==true){
					var id = restaurants[i].name;
					str += "<tr>" +
					"<td>"+ id +"</td>" +
					"<td colspan=\"2\">"+ restaurants[i].adress +"</td>" +
					"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"showRestaurant(\'"+id + "\', \'" + restaurants[i].type +"\')\">Show menu</button></td>" +
					"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"favoriteRestaurant(\'"+id + "\')\">Add to favorites</button></td>" +
					"</tr>";
				}
			}
			str += "</table>";
			var Obj = document.getElementById(type+'table'); //any element to be fully replaced
			if(Obj.outerHTML) { //if outerHTML is supported
			    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
			}
		}
	});
}

function showRestaurant(id){
	$(".all-articles").hide();
	$(".menu-page").show();
	$.ajax({
		url: "rest/restaurants/getAll",
		type:"GET",
		complete: function(data) {
			//window.location.href ="admin.html"
			var restaurants = JSON.parse(data.responseText);
			
			for(var i = 0; i < restaurants.length; i++){
				if(restaurants[i].name==id){
					$(".edit-name").val(restaurants[i].name);
					$(".edit-adress").val(restaurants[i].adress);
					
					showDrinkTable(id);
					
					showFoodTable(id);
				}
			}
		}
	});
}

$("document").ready(function(){
	$(".all-articles").hide();
	
	$("#add-local-button").click(function(){
		var Obj = document.getElementById("confirm-add-restaurant")
		if(Obj.outerHTML) {
		    Obj.outerHTML="<input type=\"button\" id=\"confirm-add-restaurant\" class=\"btn btn-primary\" value=\"ADD\" onclick=\"addRestaurant(\'local\')\"/>";
		}
	});
	
	$("#add-grill-button").click(function(){
		var Obj = document.getElementById("confirm-add-restaurant")
		if(Obj.outerHTML) {
		    Obj.outerHTML="<input type=\"button\" id=\"confirm-add-restaurant\" class=\"btn btn-primary\" value=\"ADD\" onclick=\"addRestaurant(\'grill\')\"/>";
		}
	});
	
	$("#add-chinese-button").click(function(){
		var Obj = document.getElementById("confirm-add-restaurant")
		if(Obj.outerHTML) {
		    Obj.outerHTML="<input type=\"button\" id=\"confirm-add-restaurant\" class=\"btn btn-primary\" value=\"ADD\" onclick=\"addRestaurant(\'chinese\')\"/>";
		}
	});
	
	$("#add-indian-button").click(function(){
		var Obj = document.getElementById("confirm-add-restaurant")
		if(Obj.outerHTML) {
		    Obj.outerHTML="<input type=\"button\" id=\"confirm-add-restaurant\" class=\"btn btn-primary\" value=\"ADD\" onclick=\"addRestaurant(\'indian\')\"/>";
		}
	});
	
	$("#add-sweets-button").click(function(){
		var Obj = document.getElementById("confirm-add-restaurant")
		if(Obj.outerHTML) {
		    Obj.outerHTML="<input type=\"button\" id=\"confirm-add-restaurant\" class=\"btn btn-primary\" value=\"ADD\" onclick=\"addRestaurant(\'sweets\')\"/>";
		}
	});
	
	$("#add-italian-button").click(function(){
		var Obj = document.getElementById("confirm-add-restaurant")
		if(Obj.outerHTML) {
		    Obj.outerHTML="<input type=\"button\" id=\"confirm-add-restaurant\" class=\"btn btn-primary\" value=\"ADD\" onclick=\"addRestaurant(\'italian\')\"/>";
		}
	});
});

function deleteRestaurant(name,type){
	$.ajax({
		url: "rest/restaurants/delete",
		type:"DELETE",
		data: name,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if(type == "local"){
				showLocal();
			}else if(type == "grill"){
				showGrill();
			}else if(type == "indian"){
				showIndian();
			}
			else if(type == "chinese"){
				showChinese();
			}
			else if(type == "sweets"){
				showSweets();
			}
			else if(type == "italian"){
				showItalian();
			}
		}
	});
}

function editRestaurant(id){
	$(".all-articles").hide();
	$(".edit-page").show();
	$.ajax({
		url: "rest/restaurants/getAll",
		type:"GET",
		complete: function(data) {
			//window.location.href ="admin.html"
			var restaurants = JSON.parse(data.responseText);
			
			for(var i = 0; i < restaurants.length; i++){
				if(restaurants[i].name==id){
					$(".edit-name").val(restaurants[i].name);
					$(".edit-adress").val(restaurants[i].adress);
					
					addDrinkTable(id);
					addDrink1Table(id);
					
					addFoodTable(id);
					addFood1Table(id);
					var Obj = document.getElementById("confirm-but")
					if(Obj.outerHTML) {
					    Obj.outerHTML="<input type=\"button\" class=\"btn btn-primary\" id=\"confirm-but\" value=\"OK\" onclick=\"confirmEdit(\'"+ restaurants[i].type +"\')\"/> <br />";
					}
				}
			}
		}
	});
}

function addDrinkTable(restaurant){
	$.ajax({
		url: "rest/articles/getDrinks",
		type:"GET",
		complete: function(data) {
			//window.location.href ="admin.html"
			var articles = JSON.parse(data.responseText);
			
			var str = "<table id=\"drinktable\"><tr><td>Name</td><td>Price ($)</td><td colspan=\"2\">About</td><td>Quantity</td><td></td></tr>";
			
			for (i = 0; i < articles.length; i++) {
				if(articles[i].active==true){
					var id = articles[i].name;
					str += "<tr>" +
					"<td>"+ id +"</td>" +
					"<td>"+ articles[i].price +"</td>" +
					"<td colspan=\"2\">"+ articles[i].about +"</td>" +
					"<td>"+ articles[i].quantity +"</td>" +
					"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"addDrink(\'"+ restaurant + "\',\'"+ id + "\')\">Add</button></td>" +
					"</tr>";
				}
			}
			str += "</table>";
			var Obj = document.getElementById('drinktable');
			if(Obj.outerHTML) {
			    Obj.outerHTML=str;
			}
		}
	});
}

function addFoodTable(restaurant){
	$.ajax({
		url: "rest/articles/getFood",
		type:"GET",
		complete: function(data) {
			//window.location.href ="admin.html"
			var articles = JSON.parse(data.responseText);
			
			var str = "<table id=\"foodtable\"><tr><td>Name</td><td>Price</td><td colspan=\"2\">About</td><td>Quantity</td><td></td></tr>";
			
			for (i = 0; i < articles.length; i++) {
				if(articles[i].active==true){
					var id = articles[i].name;
					str += "<tr>" +
					"<td>"+ id +"</td>" +
					"<td>"+ articles[i].price +"</td>" +
					"<td colspan=\"2\">"+ articles[i].about +"</td>" +
					"<td>"+ articles[i].quantity +"</td>" +
					"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"addFood(\'"+ restaurant + "\',\'"+ id + "\')\">Add</button></td>" +
					"</tr>";
				}
			}
			str += "</table>";
			var Obj = document.getElementById('foodtable');
			if(Obj.outerHTML) {
			    Obj.outerHTML=str;
			}
		}
	});
}

function addDrink1Table(restaurant){//iz restorana
	var data = restaurant;
	var s = JSON.stringify(data);
	$.ajax({
		url: "rest/restaurants/getDrinks",
		type:"POST",
		data:s,
		complete: function(data) {
			try{
				var articles = JSON.parse(data.responseText);
				var str = "<table id=\"drinktable1\"><tr><td>Name</td><td>Price ($)</td><td colspan=\"2\">About</td><td>Quantity</td><td></td></tr>";
				
				for (i = 0; i < articles.length; i++) {
					if(articles[i].active==true){
						var id = articles[i].name;
						str += "<tr>" +
						"<td>"+ id +"</td>" +
						"<td>"+ articles[i].price +"</td>" +
						"<td colspan=\"2\">"+ articles[i].about +"</td>" +
						"<td>"+ articles[i].quantity +"</td>" +
						"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"removeDrink(\'"+ restaurant + "\',\'"+ id + "\')\">Remove</button></td>" +
						"</tr>";
					}
				}
				str += "</table>";
				var Obj = document.getElementById('drinktable1');
				if(Obj.outerHTML) {
				    Obj.outerHTML=str;
				}
			}catch (e) { //nema artikle
				var str = "<table id=\"drinktable1\"><tr><td>Name</td><td>Price ($)</td><td colspan=\"2\">About</td><td>Quantity</td><td></td></tr>";
				str += "</table>";
				var Obj = document.getElementById('drinktable1');
				if(Obj.outerHTML) {
				    Obj.outerHTML=str;
				}
			}
		}
	});
}

function showDrinkTable(restaurant){//iz restorana
	var data = restaurant;
	var s = JSON.stringify(data);
	$.ajax({
		url: "rest/restaurants/getDrinks",
		type:"POST",
		data:s,
		complete: function(data) {
			try{
				var articles = JSON.parse(data.responseText);
				var str = "<table id=\"drinktable1\"><tr><td>Name</td><td>Price ($)</td><td colspan=\"2\">About</td><td>Quantity</td><td></td></tr>";
				
				for (i = 0; i < articles.length; i++) {
					if(articles[i].active==true){
						var id = articles[i].name;
						str += "<tr>" +
						"<td>"+ id +"</td>" +
						"<td>"+ articles[i].price +"</td>" +
						"<td colspan=\"2\">"+ articles[i].about +"</td>" +
						"<td>"+ articles[i].quantity +"</td>" +
						"<td><button data-toggle=\"modal\" data-target=\"#myModal\" type=\"button\" class=\"btn btn-primary\" onclick=\"orderArticle(\'"+ id + "\')\">Order</button></td>" +
						"</tr>";
					}
				}
				str += "</table>";
				var Obj = document.getElementById('drinktable1');
				if(Obj.outerHTML) {
				    Obj.outerHTML=str;
				}
			}catch (e) { //nema artikle
				var str = "<table id=\"drinktable1\"><tr><td>Name</td><td>Price ($)</td><td colspan=\"2\">About</td><td>Quantity</td><td></td></tr>";
				str += "</table>";
				var Obj = document.getElementById('drinktable1');
				if(Obj.outerHTML) {
				    Obj.outerHTML=str;
				}
			}
		}
	});
}

function showFoodTable(restaurant){//iz restorana
	var data = restaurant;
	var s = JSON.stringify(data);
	$.ajax({
		url: "rest/restaurants/getFood",
		type:"POST",
		data:s,
		complete: function(data) {
			try{
				var articles = JSON.parse(data.responseText);
				var str = "<table id=\"foodtable1\"><tr><td>Name</td><td>Price ($)</td><td colspan=\"2\">About</td><td>Quantity</td><td></td></tr>";
				
				for (i = 0; i < articles.length; i++) {
					if(articles[i].active==true){
						var id = articles[i].name;
						str += "<tr>" +
						"<td>"+ id +"</td>" +
						"<td>"+ articles[i].price +"</td>" +
						"<td colspan=\"2\">"+ articles[i].about +"</td>" +
						"<td>"+ articles[i].quantity +"</td>" +
						"<td><button data-toggle=\"modal\" data-target=\"#myModal\" type=\"button\" class=\"btn btn-primary\" onclick=\"orderArticle(\'"+ id + "\')\">Order</button></td>" +
						"</tr>";
					}
				}
				str += "</table>";
				var Obj = document.getElementById('foodtable1');
				if(Obj.outerHTML) {
				    Obj.outerHTML=str;
				}
			}catch (e) { //nema artikle
				var str = "<table id=\"foodtable1\"><tr><td>Name</td><td>Price ($)</td><td colspan=\"2\">About</td><td>Quantity</td><td></td></tr>";
				str += "</table>";
				var Obj = document.getElementById('foodtable1');
				if(Obj.outerHTML) {
				    Obj.outerHTML=str;
				}
			}
		}
	});
}

function addFood1Table(restaurant){//iz restorana
	var data = restaurant;
	var s = JSON.stringify(data);
	$.ajax({
		url: "rest/restaurants/getFood",
		type:"POST",
		data:s,
		complete: function(data) {
			try{
				var articles = JSON.parse(data.responseText);
				var str = "<table id=\"foodtable1\"><tr><td>Name</td><td>Price ($)</td><td colspan=\"2\">About</td><td>Quantity</td><td></td></tr>";
				
				for (i = 0; i < articles.length; i++) {
					if(articles[i].active==true){
						var id = articles[i].name;
						str += "<tr>" +
						"<td>"+ id +"</td>" +
						"<td>"+ articles[i].price +"</td>" +
						"<td colspan=\"2\">"+ articles[i].about +"</td>" +
						"<td>"+ articles[i].quantity +"</td>" +
						"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"removeFood(\'"+ restaurant + "\',\'"+ id + "\')\">Remove</button></td>" +
						"</tr>";
					}
				}
				str += "</table>";
				var Obj = document.getElementById('foodtable1');
				if(Obj.outerHTML) {
				    Obj.outerHTML=str;
				}
			}catch (e) { //nema artikle
				var str = "<table id=\"foodtable1\"><tr><td>Name</td><td>Price ($)</td><td colspan=\"2\">About</td><td>Quantity</td><td></td></tr>";
				str += "</table>";
				var Obj = document.getElementById('foodtable1');
				if(Obj.outerHTML) {
				    Obj.outerHTML=str;
				}
			}
		}
	});
}

function addDrink(restaurant,article){
	var data = [restaurant, article];
	var s = JSON.stringify(data);
	$.ajax({
		url: "rest/restaurants/addDrink",
		type:"PUT",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			addDrinkTable(restaurant);
			addDrink1Table(restaurant);
		}
	});
}

function removeDrink(restaurant,article){
	var data = [restaurant, article];
	var s = JSON.stringify(data);
	$.ajax({
		url: "rest/restaurants/removeDrink",
		type:"PUT",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			addDrinkTable(restaurant);
			addDrink1Table(restaurant);
		}
	});
}

function addFood(restaurant,article){
	var data = [restaurant, article];
	var s = JSON.stringify(data);
	$.ajax({
		url: "rest/restaurants/addFood",
		type:"PUT",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			addFoodTable(restaurant);
			addFood1Table(restaurant);
		}
	});
}

function removeFood(restaurant,article){
	var data = [restaurant, article];
	var s = JSON.stringify(data);
	$.ajax({
		url: "rest/restaurants/removeFood",
		type:"PUT",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			addFoodTable(restaurant);
			addFood1Table(restaurant);
		}
	});
}

function confirmEdit(type){	
	var $form = $("#edit-form");
	var data = getFormData($form);
	var s = JSON.stringify(data);
	$.ajax({
		url: "rest/restaurants/edit",
		type:"PUT",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if(data.responseText == "Fill all fields!" || data.responseText == "Name taken! Please choose another one."){
				$( "#resultEditRestaurant" ).html( data.responseText );
				return;
			}
			if(type == "local"){
				showLocal();
			}else if(type == "grill"){
				showGrill();
			}else if(type == "indian"){
				showIndian();
			}
			else if(type == "chinese"){
				showChinese();
			}
			else if(type == "sweets"){
				showSweets();
			}
			else if(type == "italian"){
				showItalian();
			}
		}
	});
}

function addNewRestaurant(){
	$(".all-articles").hide();
	$(".new-restaurant-page").show();	
}