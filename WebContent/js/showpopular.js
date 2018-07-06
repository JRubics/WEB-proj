$(window).load(
    function(event) {
    	$(".admin-button").hide();
    	$(".delivery-button").hide();
    	$(".byer-button").hide();
        isLoggedIn();
        
        $.ajax({
    		url: "rest/articles/getPopular",
    		type:"GET",
    		complete: function(data) {
//    			alert(data.responseText);
    			try{
    				var articles = JSON.parse(data.responseText);
    				var str = "<table id=\"populartable\"><tr><td></td><td>Name</td><td>Price</td><td colspan=\"2\">About</td><td>Quantity</td><td>Type</td><td></td></tr>";
    				
    				var n;
    				if(articles.length > 10){
    					n = 10;
    				}else{
    					n = articles.length;
    				}
    				for (i = 0; i < n; i++) {
    					if(articles[i].active==true){
    						var id = articles[i].name;
    						var j = i + 1;
    						str += "<tr>" +
    						"<td>"+ j +".</td>" +
    						"<td>"+ id +"</td>" +
    						"<td>"+ articles[i].price +"</td>" +
    						"<td colspan=\"2\">"+ articles[i].about +"</td>" +
    						"<td>"+ articles[i].quantity +"</td>" +
    						"<td>"+ articles[i].type +"</td>" +
    						"<td><button data-toggle=\"modal\" data-target=\"#myModal\" type=\"button\" class=\"btn btn-primary\" onclick=\"orderArticle(\'"+ id + "\')\">Order</button></td>" +
    						"</tr>";
    					}
    				}
    				str += "</table>";
    				var Obj = document.getElementById('populartable');
    				if(Obj.outerHTML) {
    				    Obj.outerHTML=str;
    				}
    			}catch (e) { //nema artikle
    				var str = "<table id=\"populartable\"><tr><td>Name</td><td>Price</td><td colspan=\"2\">About</td><td>Quantity</td><td></td></tr>";
    				str += "</table>";
    				var Obj = document.getElementById('populartable');
    				if(Obj.outerHTML) {
    				    Obj.outerHTML=str;
    				}
    			}
    		}
    	});
    }
);