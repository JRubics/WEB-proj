$(window).load(
    function(event) {
    	$(".admin-button").hide();
    	$(".delivery-button").hide();
    	$(".byer-button").hide();
        isLoggedIn();
        
        $.ajax({
			url: "rest/orders/getOrder",
			type:"GET",
			complete: function(data) {
				if(data.responseText == "no user"){
					window.location = "login.html";
				}else{
					var order = JSON.parse(data.responseText);
					var str = "<table id=\"articletable\"><tr><td>Name</td><td>Price</td><td colspan=\"2\">About</td><td>Quantity</td><td>Type</td><td>Count</td></tr>";
					
					for (var i = 0; i < order.articles.length; i++){
						var article = order.articles[i];
						if(article.active==true){						
							var id = article.name;
							str += "<tr>" +
							"<td>"+ id +"</td>" +
							"<td>"+ article.price +"</td>" +
							"<td colspan=\"2\">"+ article.about +"</td>" +
							"<td>"+ article.quantity +"</td>" +
							"<td>"+ article.type +"</td>" +
							"<td>  <input type=\"number\" class=\"quantity" + id + "\" min=\"1\" name=\"quantity" + id + "\" style=\"width:2em;\" value=" + order.counter[i] +"></td>" +
							"</tr>";
						}
					}
					str += "</table>";
					var Obj = document.getElementById('articletable'); //any element to be fully replaced
					if(Obj.outerHTML) { //if outerHTML is supported
					    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
					}
					$(".note-class").val(order.note);
					$(".order-date").html(order.date);
					
					
					$.ajax({
						url: "rest/users/getByers",
						type:"GET",
						complete: function(data) {
							if(data.responseText == "no user"){
								window.location = "login.html";
							}else{
								var byers = JSON.parse(data.responseText);
								var str = `<select class="form-control select-byer" name="byer" id="byer-select-id">`;
								str += `<option>${order.byer}</option>`;
								for(var i = 0; i < byers.length; i++){
									if(order.byer !== byers[i].username){
										str += `<option>${byers[i].username}</option>`;
									}
								}
								str +=`</select>`;
								var Obj = document.getElementById('byer-select-id'); //any element to be fully replaced
								if(Obj.outerHTML) { //if outerHTML is supported
								    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
								}
							}
						}
			        });
			        $.ajax({
						url: "rest/users/getDeliverers",
						type:"GET",
						complete: function(data) {
							if(data.responseText == "no user"){
								window.location = "login.html";
							}else{
								var byers = JSON.parse(data.responseText);
								var str = `<select class="form-control select-byer" name="deliverer" id="deliverer-select-id">`;
								if(order.deliverer !== null){
									str += `<option>${order.deliverer}</option>`;
								}else{
									str += `<option>/</option>`;
								}
								
								for(var i = 0; i < byers.length; i++){
									if(order.deliverer !== byers[i].username){
										str += `<option>${byers[i].username}</option>`;
									}
								}
								str +=`</select>`;
								var Obj = document.getElementById('deliverer-select-id'); //any element to be fully replaced
								if(Obj.outerHTML) { //if outerHTML is supported
								    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
								}
							}
						}
			        });
			        var states = ['Ordered','','Delivered','Canceled'];
			        var str = `<select class="form-control select-state" name="state" id="state-select-id">`;
			        str+=`<option>${order.state}</option>`;
			        
			        if(order.state=='Ordered'){
			        	 str+=`<option>Delivery in progress</option>
			        	 <option>Delivered</option>
			        	 <option>Canceled</option>`;
			        }
			        if(order.state=='Delivery in progress'){
			        	 str+=`<option>Ordered</option>
			        	 <option>Delivered</option>
			        	 <option>Canceled</option>`;
			        }
			        if(order.state=='Delivered'){
			        	 str+=`<option>Ordered</option>
			        	 <option>Delivery in progress</option>
			        	 <option>Canceled</option>`;
			        }
			        if(order.state=='Canceled'){
			        	 str+=`<option>Ordered</option>
			        	 <option>Delivery in progress</option>
			        	 <option>Delivered</option>`;
			        }
			        str += `</select>`;
			        var Obj = document.getElementById('state-select-id'); //any element to be fully replaced
					if(Obj.outerHTML) { //if outerHTML is supported
					    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
					}
				}
			}
		});
});

function confirmEditOrder(event){
	event.preventDefault();
	var $form = $("#edit-order");
	var data = getFormData($form);
	var s = JSON.stringify(data);
	$.ajax({
		url: "rest/orders/edit",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			window.location = "editadminorder.html";
		}
	});
}


//function quantityChanged(id){
//	var quantity = $(".quantity" + id).val();
//	
//	var s = [id,quantity];
//	s = JSON.stringify(s);
//	$.ajax({
//		url: "rest/articles/setQuantity",
//		type:"POST",
//		data: s,
//		contentType:"application/json",
//		dataType:"json",
//		complete: function(data) {
//		}
//	});
//}