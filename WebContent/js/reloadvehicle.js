$(window).load(
    function(event) {
    	$(".admin-button").hide();
    	$(".delivery-button").hide();
    	$(".byer-button").hide();
        isLoggedIn();
        
        $.ajax({
    		url: "rest/users/isloggedin",
    		type:"GET",
    		complete: function(data) {
    			if(data.responseText!==""){
    				var user = JSON.parse(data.responseText);
    				if(user.role=="deliverer"){
    					$.ajax({
    			    		url: "rest/vehicles/getAll",
    			    		type:"GET",
    			    		complete: function(data) {
    			    			var vehicles = JSON.parse(data.responseText);
    			    			for(var i = 0; i < vehicles.length; i++){
    			    				if(vehicles[i].registration_number==user.vehicle){
    			    					$("#userBrand").html(vehicles[i].brand);
    			    					$("#userModel").html(vehicles[i].model);
    			    					$("#userReg").html(vehicles[i].registration_number);
    			    					$("#userYear").html(vehicles[i].year);
    			    					$("#userNote").html(vehicles[i].note);
    			    				}
    			    			}
    			    		}
    			    	});
    				}
    			}
    		}
    	});
    }
);