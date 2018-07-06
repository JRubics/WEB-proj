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

function searchRestaurants(){
	$(".all-articles").hide();
	$(".search-restaurants").show();
}

function searchArticles(){
	$(".all-articles").hide();
	$(".search-articles").show();
}

$("document").ready(function(){
	$(".all-articles").hide();
});

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

function searchRestaurantsOK(){
	$(".all-articles").hide();
	$(".show-restaurants").show();
	
	var $form = $("#search-restaurants-form");
	var data = getFormData($form);
	var s = JSON.stringify(data);
	
	$.ajax({
		url: "rest/restaurants/search",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			try{
				var restaurants = JSON.parse(data.responseText);	
				var str = "<table id=\"restaurants-table\"><tr>" +
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
						"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"showRestaurant(\'"+id + "\', \'" + restaurants[i].type +"\')\">Show menu</button></td>" +
						"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"favoriteRestaurant(\'"+id + "\')\">Add to favorites</button></td>" +
						"</tr>";
					}
				}
				str += "</table>";
				var Obj = document.getElementById("restaurants-table"); //any element to be fully replaced
				if(Obj.outerHTML) { //if outerHTML is supported
				    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
				}
			}catch (e) { //nema artikle
				var str = "<table id=\"restaurants-table\"><tr><th>No articles</th></tr>";
				str += "</table>";
				var Obj = document.getElementById('restaurants-table');
				if(Obj.outerHTML) {
				    Obj.outerHTML=str;
				}
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

function searchArticlesOK(){
	$(".all-articles").hide();
	$(".show-articles").show();
	
	var $form = $("#search-articles-form");
	var data = getFormData($form);
	var s = [data.name, data.type, data.pricemin, data.pricemax, data.restaurant];
	s = JSON.stringify(s);
	$.ajax({
		url: "rest/articles/search",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			try{
				var articles = JSON.parse(data.responseText);
				var str = "<table id=\"articles-table\"><tr><td>Name</td><td>Price</td><td colspan=\"2\">About</td><td>Quantity</td><td>Type</td><td></td></tr>";
				for (i = 0; i < articles.length; i++) {
					if(articles[i].active==true){
						var id = articles[i].name;
						str += "<tr>" +
						"<td>"+ id +"</td>" +
						"<td>"+ articles[i].price +"</td>" +
						"<td colspan=\"2\">"+ articles[i].about +"</td>" +
						"<td>"+ articles[i].quantity +"</td>" +
						"<td>"+ articles[i].type +"</td>" +
						"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"orderArticle(\'"+ id + "\')\">Order</button></td>" +
						"</tr>";
					}
				}
				str += "</table>";
				var Obj = document.getElementById('articles-table');
				if(Obj.outerHTML) {
				    Obj.outerHTML=str;
				}
			}catch (e) { //nema artikle
				var str = "<table id=\"restaurants-table\"><tr><th>No articles</th></tr>";
				str += "</table>";
				var Obj = document.getElementById('articles-table');
				if(Obj.outerHTML) {
				    Obj.outerHTML=str;
				}
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
				var str = "<table id=\"drinktable1\"><tr><td>Name</td><td>Price</td><td colspan=\"2\">About</td><td>Quantity</td><td></td></tr>";
				
				for (i = 0; i < articles.length; i++) {
					if(articles[i].active==true){
						var id = articles[i].name;
						str += "<tr>" +
						"<td>"+ id +"</td>" +
						"<td>"+ articles[i].price +"</td>" +
						"<td colspan=\"2\">"+ articles[i].about +"</td>" +
						"<td>"+ articles[i].quantity +"</td>" +
						"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"orderArticle(\'"+ id + "\')\">Order</button></td>" +
						"</tr>";
					}
				}
				str += "</table>";
				var Obj = document.getElementById('drinktable1');
				if(Obj.outerHTML) {
				    Obj.outerHTML=str;
				}
			}catch (e) { //nema artikle
				var str = "<table id=\"drinktable1\"><tr><td>Name</td><td>Price</td><td colspan=\"2\">About</td><td>Quantity</td><td></td></tr>";
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
				var str = "<table id=\"foodtable1\"><tr><td>Name</td><td>Price</td><td colspan=\"2\">About</td><td>Quantity</td><td></td></tr>";
				
				for (i = 0; i < articles.length; i++) {
					if(articles[i].active==true){
						var id = articles[i].name;
						str += "<tr>" +
						"<td>"+ id +"</td>" +
						"<td>"+ articles[i].price +"</td>" +
						"<td colspan=\"2\">"+ articles[i].about +"</td>" +
						"<td>"+ articles[i].quantity +"</td>" +
						"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"orderArticle(\'"+ id + "\')\">Order</button></td>" +
						"</tr>";
					}
				}
				str += "</table>";
				var Obj = document.getElementById('foodtable1');
				if(Obj.outerHTML) {
				    Obj.outerHTML=str;
				}
			}catch (e) { //nema artikle
				var str = "<table id=\"foodtable1\"><tr><td>Name</td><td>Price</td><td colspan=\"2\">About</td><td>Quantity</td><td></td></tr>";
				str += "</table>";
				var Obj = document.getElementById('foodtable1');
				if(Obj.outerHTML) {
				    Obj.outerHTML=str;
				}
			}
		}
	});
}