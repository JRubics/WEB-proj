$(window).load(
    function(event) {
    	$(".admin-button").hide();
    	$(".delivery-button").hide();
    	$(".byer-button").hide();
        isLoggedIn();
        
        $.ajax({
			url: "rest/orders/getDelivery",
			type:"GET",
			complete: function(data) {
				if(data.responseText == "no user"){
					window.location = "login.html";
				}else{
					try{
						var order = JSON.parse(data.responseText);
						
						var str = `<div class="row all-articles" id="show-delivery-page">
							<div class="col-lg-2 col-md-2"></div>
							<div class="col-lg-8 col-md-8">
								<h4 >Delivery:</h4>
								<div>`;

								str+=`<div class="row">
								<div class="col-lg-1 col-md-1"></div>
								<div class="card col-lg-10 col-md-10">
								  	<div class="card w-75">
									  	<div class="card-body">
									  	<h5 class="card-title">${order.date}</h5>
									  	<table><tr><td>Name</td><td>Price</td><td colspan="2">About</td><td>Quantity(gr/ml)</td><td>Type</td><td>Numer</td><td>Full price</td></tr>`;
								var price = 0;
								for (j = 0; j < order.articles.length; j++) {
									var id = order.articles[j].name;
									str += "<tr>" +
									"<td>"+ id +"</td>" +
									"<td>"+ order.articles[j].price +"</td>" +
									"<td colspan=\"2\">"+ order.articles[j].about +"</td>" +
									"<td>"+ order.articles[j].quantity +"</td>" +
									"<td>"+ order.articles[j].type +"</td>" +
									"<td>"+ order.counter[j] +"</td>" +
									"<td>"+ order.counter[j] * order.articles[j].price +"$</td>" +
									"</tr>";
									price +=order.counter[j] * order.articles[j].price;
								}
								var newPrice=order.price-(order.points * 3/100 * order.price );
								str+= `<tr><td></td><td></td><td></td><td></td><td></td><td></td><td>Full price:</td><td>${order.price}$</td></tr>
								<tr><td></td><td></td><td></td><td></td><td>Points</td><td>${order.points}</td><td>New price:</td><td>${newPrice}$</td></tr></table>`;
								str+= `<label>byer</label>
									 		<input type="text" readonly="readonly" style="font-size: 2em" value=${order.byer}>
									 		<br>
									 		<label>deliverer</label>`;
											if(order.deliverer==null){
												str+=`<input type="text" readonly="readonly" style="font-size: 2em" value="/">`;
											}else{
									 			str+=`<input type="text" readonly="readonly" style="font-size: 2em" value=${order.deliverer}>`;
									 			}
											str+=`<br>
												 		<label>state</label>`;
												 		str+="<input type=\"text\" readonly=\"readonly\" style=\"font-size: 2em\" value=\"" + order.state + "\">";
													 	str+=`<label>note</label>
												 		<input type="text" readonly="readonly" style="font-size: 2em" value=${order.note}>`;
												str+= "<button type=\"button\" class=\"btn btn-info\" onclick=\"deliverOrder(\'" + order.date + "\')\">Deliver order</button>";
											str+=`</div>
													</div>`;
											str+=`	</div>
												<div class="col-lg-1 col-md-1"></div>`
											str+=`</div></div>`;
							var Obj = document.getElementById('show-delivery-page'); //any element to be fully replaced
							if(Obj.outerHTML) { //if outerHTML is supported
							    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
							}
							$("#show-orders-page").hide();
					}catch (e) {
						str=`<div class="row all-articles" id="show-delivery-page">
								<div class="col-lg-2 col-md-2"></div>
								<div class="col-lg-8 col-md-8">
									<h4 >There are no deliveries</h4>
									<div id="showDelivery">
									</div>
							</div>
							</div>`;
						var Obj = document.getElementById('show-delivery-page'); //any element to be fully replaced
						if(Obj.outerHTML) { //if outerHTML is supported
						    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
						}
					}
				}
			}
		});
        getAllArticlesIfNoDelivery();
});

function getAllArticlesIfNoDelivery(){
	$.ajax({
		url: "rest/orders/getAllOrders",
		type:"GET",
		complete: function(data) {
			if(data.responseText == "no user"){
				window.location = "login.html";
			}else{
				try{
				var orders = JSON.parse(data.responseText);
				var str = `<div class="row all-articles" id="show-orders-page">
				<div class="col-lg-2 col-md-2"></div>
				<div class="col-lg-8 col-md-8">
					<h4 >Order history:</h4>
					<div id="showOrderHistory">`;

				for (i = 0; i < orders.length; i++) {
					if(orders[i].active==true){
					str+=`<div class="row">
					<div class="col-lg-1 col-md-1"></div>
					<div class="card col-lg-10 col-md-10">
					  	<div class="card w-75">
						  	<div class="card-body">
						  	<h5 class="card-title">${orders[i].date}</h5>
						  	<table><tr><td>Name</td><td>Price</td><td colspan="2">About</td><td>Quantity(gr/ml)</td><td>Type</td><td>Numer</td><td>Full price</td></tr>`;
					var price = 0;
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
						price +=orders[i].counter[j] * orders[i].articles[j].price;
					}
					var newPrice=orders[i].price-(orders[i].points * 3/100 * orders[i].price );
					str+= `<tr><td></td><td></td><td></td><td></td><td></td><td></td><td>Full price:</td><td>${orders[i].price}$</td></tr>
					<tr><td></td><td></td><td></td><td></td><td>Points</td><td>${orders[i].points}</td><td>New price:</td><td>${newPrice}$</td></tr></table>
					<label>byer</label>
						 		<input type="text" readonly="readonly" style="font-size: 2em" value=${orders[i].byer}>
						 		<br>
						 		<label>deliverer</label>`;
								if(orders[i].deliverer==null || orders[i].deliverer=="/"){
									str+=`<input type="text" readonly="readonly" style="font-size: 2em" value="/">`;
								}else{
						 			str+=`<input type="text" readonly="readonly" style="font-size: 2em" value=${orders[i].deliverer}>`;
						 			}
								str+=`<br>
									 		<label>state</label>`;
									 		str+="<input type=\"text\" readonly=\"readonly\" style=\"font-size: 2em\" value=\"" + orders[i].state + "\">";
										 	str+=`<label>note</label>
									 		<input type="text" readonly="readonly" style="font-size: 2em" value=${orders[i].note}>`;
								if(orders[i].deliverer==null || orders[i].deliverer=="/"){
									str+= "<button type=\"button\" class=\"btn btn-info\" onclick=\"takeOrder(\'" + orders[i].date + "\')\">Take order</button>";
								}
								str+=`</div>
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
				}catch (e) {
					str=`<div class="row all-articles" id="show-orders-page">
			<div class="col-lg-2 col-md-2"></div>
			<div class="col-lg-8 col-md-8">
				<div id="showOrderHistory">
				</div>
		</div>
		</div>`
					var Obj = document.getElementById('show-orders-page'); //any element to be fully replaced
					if(Obj.outerHTML) { //if outerHTML is supported
					    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
					}
				}
			}
		}
	});
}

function deliverOrder(date){
	var s = JSON.stringify(date);
	$.ajax({
		url: "rest/orders/deliverOrder",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			window.location = "delivery.html";
		}
	});
}

function takeOrder(date){
	var s = JSON.stringify(date);
	$.ajax({
		url: "rest/orders/takeOrder",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			window.location = "delivery.html";
		}
	});
};



