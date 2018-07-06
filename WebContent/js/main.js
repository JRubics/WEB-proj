$(window).load(
    function(event) {
    	$(".admin-button").hide();
    	$(".delivery-button").hide();
    	$(".byer-button").hide();
        isLoggedIn();
        
        $.ajax({
			url: "rest/orders/getUserHistory",
			type:"GET",
			complete: function(data) {
				if(data.responseText == "no user"){
					window.location = "login.html";
				}else{
					var orders = JSON.parse(data.responseText);
					
					var str = `<div class="row all-articles" id="show-orders-page">
					<div class="col-lg-2 col-md-2"></div>
					<div class="col-lg-8 col-md-8">
						<h4 >Order history:</h4>
						<div id="showOrderHistory">`;

					for (i = 0; i < orders.length; i++) {
						//alert(orders[i].active)
						if(orders[i].active==true){
							str+=`<div class="row">
							<div class="col-lg-1 col-md-1"></div>
							<div class="card col-lg-10 col-md-10">
							  	<div class="card w-75">
								  	<div class="card-body">
								  	<h5 class="card-title">${orders[i].date}</h5>
								  	<table><tr><td>Name</td><td>Price</td><td colspan="2">About</td><td>Quantity(gr/ml)</td><td>Type</td><td>Numer</td><td>Full price</td></tr>`;
							for (j = 0; j < orders[i].articles.length; j++) {
								var id = orders[i].articles[j].name;
								str += "<tr>" +
								"<td>"+ id +"</td>" +
								"<td>"+ orders[i].articles[j].price +"</td>" +
								"<td colspan=\"2\">"+ orders[i].articles[j].about +"</td>" +
								"<td>"+ orders[i].articles[j].quantity +"</td>" +
								"<td>"+ orders[i].articles[j].type +"</td>" +
								"<td>"+ orders[i].counter[j] +"</td>" +
								"<td>"+ orders[i].counter[j] * orders[i].articles[j].price +"$</td>" +
								"</tr>";
							}
							var newPrice=orders[i].price-(orders[i].points * 3/100 * orders[i].price );
							str+= `<tr><td></td><td></td><td></td><td></td><td></td><td></td><td>Full price:</td><td>${orders[i].price}$</td></tr>
							<tr><td></td><td></td><td></td><td></td><td>Points</td><td>${orders[i].points}</td><td>New price:</td><td>${newPrice}$</td></tr></table>
							<label>byer</label>
								 		<input type="text" readonly="readonly" style="font-size: 2em" value=${orders[i].byer}>
								 		<br>
								 		<label>deliverer</label>`;
										if(orders[i].deliverer==null){
											str+=`<input type="text" readonly="readonly" style="font-size: 2em" value="/">`;
										}else{
								 			str+=`<input type="text" readonly="readonly" style="font-size: 2em" value=${orders[i].deliverer}>`;
								 			}
							str+=`	 		<br>
								 		<label>state</label>
								 		<input type="text" readonly="readonly" style="font-size: 2em" value=${orders[i].state}>
									 	<label>note</label>
								 		<input type="text" readonly="readonly" style="font-size: 2em" value="${orders[i].note}">
									</div>
								</div>
							</div>
							<div class="col-lg-1 col-md-1"></div>
						</div>`;
						}
					}
					str+=`</div>`;
					var Obj = document.getElementById('show-orders-page'); //any element to be fully replaced
					if(Obj.outerHTML) { //if outerHTML is supported
					    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
					}
				}
			}
		});
        $.ajax({
			url: "rest/restaurants/getFavorite",
			type:"GET",
			complete: function(data) {
				if(data.responseText == "no user"){
					window.location = "login.html";
				}else{
					var restaurants = JSON.parse(data.responseText);
					
					var str = `<div class="row all-articles" id="show-favorite-page">
					<div class="col-lg-2 col-md-2"></div>
			<div class="col-lg-8 col-md-8">
				<h4 >Favorite restaurants</h4>
				<div id="showFavorite">
				</div>`;
					str += "<table><tr>" +
					"<td>Name</td>" +
					"<td>Adress</td>" +
					"<td>Type</td>" +
					"</tr>";
		var found = false;
		for (i = 0; i < restaurants.length; i++) {
			if(restaurants[i].active==true){
				found=true;
				var id = restaurants[i].name;
				str += "<tr>" +
				"<td>"+ id +"</td>" +
				"<td>"+ restaurants[i].adress +"</td>" +
				"<td>"+ restaurants[i].type +"</td>" +
				"</tr>";
			}
		}
		str += "</table>";

					str+=`</div></div>`;
					if(found){
						var Obj = document.getElementById('show-favorite-page'); //any element to be fully replaced
						if(Obj.outerHTML) { //if outerHTML is supported
						    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
						}
					}
				}
			}
		});
});



