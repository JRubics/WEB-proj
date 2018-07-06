function addNewVehicle(){
	$(".all-articles").hide();
	$(".new-vehicle-page").show();	
}

function showCar(){
	$(".all-articles").hide();
	$(".show-car-page").show();
	
	$.ajax({
		url: "rest/vehicles/getCar",
		type:"GET",
		complete: function(data) {
			var vehicles = JSON.parse(data.responseText);
			var str = "<table id=\"cartable\"><tr>" +
						"<td>Brand</td>" +
						"<td>Model</td>" +
						"<td>Registration number</td>" +
						"<td>Year</td>" +
						"<td>In use</td>" +
						"<td>Note</td>" +
						"<td></td>" +
						"<td></td>" +
						"</tr>";
			
			for (i = 0; i < vehicles.length; i++) {
				if(vehicles[i].active==true){
					var id = vehicles[i].registration_number;
					str += "<tr>" +
					"<td>"+ vehicles[i].brand +"</td>" +
					"<td>"+ vehicles[i].model +"</td>" +
					"<td>"+ id +"</td>" +
					"<td>"+ vehicles[i].year +"</td>";
					if(vehicles[i].usable==true){
						str += "<td>yes</td>";
					}else{
						str += "<td>no</td>";
					}
					str += "<td>"+ vehicles[i].note +"</td>" +
					"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"editVehicle(\'"+id + "\', \'" + vehicles[i].type +"\')\">Edit</button></td>" +
					"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"deleteVehicle(\'"+id + "\', \'" + vehicles[i].type+"\')\">Delete</button></td>" +
					"</tr>";
				}
			}
			str += "</table>";
			var Obj = document.getElementById('cartable'); //any element to be fully replaced
			if(Obj.outerHTML) { //if outerHTML is supported
			    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
			}
		}
	});
}

function showScooter(){
	$(".all-articles").hide();
	$(".show-scooter-page").show();
	
	$.ajax({
		url: "rest/vehicles/getScooter",
		type:"GET",
		complete: function(data) {
			//window.location.href ="admin.html"
			var vehicles = JSON.parse(data.responseText);
			var str = "<table id=\"scootertable\"><tr>" +
						"<td>Brand</td>" +
						"<td>Model</td>" +
						"<td>Registration number</td>" +
						"<td>Year</td>" +
						"<td>In use</td>" +
						"<td>Note</td>" +
						"<td></td>" +
						"<td></td>" +
						"</tr>";
			
			for (i = 0; i < vehicles.length; i++) {
				if(vehicles[i].active==true){
					var id = vehicles[i].registration_number;
					str += "<tr>" +
					"<td>"+ vehicles[i].brand +"</td>" +
					"<td>"+ vehicles[i].model +"</td>" +
					"<td>"+ id +"</td>" +
					"<td>"+ vehicles[i].year +"</td>";
					if(vehicles[i].usable==true){
						str += "<td>yes</td>";
					}else{
						str += "<td>no</td>";
					}
					str += "<td>"+ vehicles[i].note +"</td>" +
					"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"editVehicle(\'"+id + "\', \'" + vehicles[i].type +"\')\">Edit</button></td>" +
					"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"deleteVehicle(\'"+id + "\', \'" + vehicles[i].type+"\')\">Delete</button></td>" +
					"</tr>";
				}
			}
			str += "</table>";
			var Obj = document.getElementById('scootertable'); //any element to be fully replaced
			if(Obj.outerHTML) { //if outerHTML is supported
			    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
			}
		}
	});
}

function showBicycle(){
	$(".all-articles").hide();
	$(".show-bicycle-page").show();
	$.ajax({
		url: "rest/vehicles/getBicycle",
		type:"GET",
		complete: function(data) {
			//window.location.href ="admin.html"
			var vehicles = JSON.parse(data.responseText);
			var str = "<table id=\"bicycletable\"><tr>" +
						"<td>Brand</td>" +
						"<td>Model</td>" +
						"<td>Registration number</td>" +
						"<td>Year</td>" +
						"<td>In use</td>" +
						"<td>Note</td>" +
						"<td></td>" +
						"<td></td>" +
						"</tr>";
			
			for (i = 0; i < vehicles.length; i++) {
				if(vehicles[i].active==true){
					var id = vehicles[i].registration_number;
					str += "<tr>" +
					"<td>"+ vehicles[i].brand +"</td>" +
					"<td>"+ vehicles[i].model +"</td>" +
					"<td>"+ id +"</td>" +
					"<td>"+ vehicles[i].year +"</td>";
					if(vehicles[i].usable==true){
						str += "<td>yes</td>";
					}else{
						str += "<td>no</td>";
					}
					str += "<td>"+ vehicles[i].note +"</td>" +
					"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"editVehicle(\'"+id + "\', \'" + vehicles[i].type +"\')\">Edit</button></td>" +
					"<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"deleteVehicle(\'"+id + "\', \'" + vehicles[i].type+"\')\">Delete</button></td>" +
					"</tr>";
				}
			}
			str += "</table>";
			var Obj = document.getElementById('bicycletable'); //any element to be fully replaced
			if(Obj.outerHTML) { //if outerHTML is supported
			    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
			}
		}
	});
}

function chooseCar(){
	$(".all-articles").hide();
	$(".show-car-page").show();
	
	$.ajax({
		url: "rest/vehicles/getCar",
		type:"GET",
		complete: function(data) {
			var vehicles = JSON.parse(data.responseText);
			var str = "<table id=\"cartable\"><tr>" +
						"<td>Brand</td>" +
						"<td>Model</td>" +
						"<td>Registration number</td>" +
						"<td>Year</td>" +
						"<td>In use</td>" +
						"<td>Note</td>" +
						"<td></td>" +
						"<td></td>" +
						"</tr>";
			
			for (i = 0; i < vehicles.length; i++) {
				if(vehicles[i].active==true){
					var id = vehicles[i].registration_number;
					str += "<tr>" +
					"<td>"+ vehicles[i].brand +"</td>" +
					"<td>"+ vehicles[i].model +"</td>" +
					"<td>"+ id +"</td>" +
					"<td>"+ vehicles[i].year +"</td>";
					if(vehicles[i].usable==true){
						str += "<td>yes</td>";
					}else{
						str += "<td>no</td>";
					}
					str += "<td>"+ vehicles[i].note +"</td>";
					if(vehicles[i].usable==false){
						str += "<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"chooseVehicle(\'"+id + "\', \'" + vehicles[i].type +"\')\">Choose</button></td>";
					}
					
					str += "</tr>";
				}
			}
			str += "</table>";
			var Obj = document.getElementById('cartable'); //any element to be fully replaced
			if(Obj.outerHTML) { //if outerHTML is supported
			    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
			}
		}
	});
}

function chooseScooter(){
	$(".all-articles").hide();
	$(".show-scooter-page").show();
	
	$.ajax({
		url: "rest/vehicles/getScooter",
		type:"GET",
		complete: function(data) {
			//window.location.href ="admin.html"
			var vehicles = JSON.parse(data.responseText);
			var str = "<table id=\"scootertable\"><tr>" +
						"<td>Brand</td>" +
						"<td>Model</td>" +
						"<td>Registration number</td>" +
						"<td>Year</td>" +
						"<td>In use</td>" +
						"<td>Note</td>" +
						"<td></td>" +
						"<td></td>" +
						"</tr>";
			
			for (i = 0; i < vehicles.length; i++) {
				if(vehicles[i].active==true){
					var id = vehicles[i].registration_number;
					str += "<tr>" +
					"<td>"+ vehicles[i].brand +"</td>" +
					"<td>"+ vehicles[i].model +"</td>" +
					"<td>"+ id +"</td>" +
					"<td>"+ vehicles[i].year +"</td>";
					if(vehicles[i].usable==true){
						str += "<td>yes</td>";
					}else{
						str += "<td>no</td>";
					}
					str += "<td>"+ vehicles[i].note +"</td>";
					if(vehicles[i].usable==false){
						str += "<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"chooseVehicle(\'"+id + "\', \'" + vehicles[i].type +"\')\">Choose</button></td>";
					}
					
					str += "</tr>";
				}
			}
			str += "</table>";
			var Obj = document.getElementById('scootertable'); //any element to be fully replaced
			if(Obj.outerHTML) { //if outerHTML is supported
			    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
			}
		}
	});
}

function chooseBicycle(){
	$(".all-articles").hide();
	$(".show-bicycle-page").show();
	$.ajax({
		url: "rest/vehicles/getBicycle",
		type:"GET",
		complete: function(data) {
			//window.location.href ="admin.html"
			var vehicles = JSON.parse(data.responseText);
			var str = "<table id=\"bicycletable\"><tr>" +
						"<td>Brand</td>" +
						"<td>Model</td>" +
						"<td>Registration number</td>" +
						"<td>Year</td>" +
						"<td>In use</td>" +
						"<td>Note</td>" +
						"<td></td>" +
						"<td></td>" +
						"</tr>";
			
			for (i = 0; i < vehicles.length; i++) {
				if(vehicles[i].active==true){
					var id = vehicles[i].registration_number;
					str += "<tr>" +
					"<td>"+ vehicles[i].brand +"</td>" +
					"<td>"+ vehicles[i].model +"</td>" +
					"<td>"+ id +"</td>" +
					"<td>"+ vehicles[i].year +"</td>";
					if(vehicles[i].usable==true){
						str += "<td>yes</td>";
					}else{
						str += "<td>no</td>";
					}
					str += "<td>"+ vehicles[i].note +"</td>";
					if(vehicles[i].usable==false){
						str += "<td><button type=\"button\" class=\"btn btn-primary\" onclick=\"chooseVehicle(\'"+id + "\', \'" + vehicles[i].type +"\')\">Choose</button></td>";
					}
					
					str += "</tr>";
				}
			}
			str += "</table>";
			var Obj = document.getElementById('bicycletable'); //any element to be fully replaced
			if(Obj.outerHTML) { //if outerHTML is supported
			    Obj.outerHTML=str; ///it's simple replacement of whole element with contents of str var
			}
		}
	});
}

function reloadMyVehicle(){
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

function chooseVehicle(id,type){
	var s = JSON.stringify(id);
	$.ajax({
		url: "rest/vehicles/chooseVehicle",
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if(data.responseText == "no user"){
				window.location = "login.html";
			}else{
				$( "#resultVehicle" ).html( data.responseText );
				if(type == "car"){
					chooseCar();
				}else if(type == "bicycle"){
					chooseBicycle();
				}else if(type == "scooter"){
					chooseScooter();
				}
				reloadMyVehicle();
			}
		}
	});
}

function addVehicle(type) {
	var $form = $("#new-vehicle");
	var data = getFormData($form);
	if(data.usable=="usable"){
		data.usable=1;
	}else{
		data.usable=0;
	}
	var s = JSON.stringify(data);
	$.ajax({
		url: "rest/vehicles/addVehicle/"+type,
		type:"POST",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if(data.responseText == "Fill all fields!" || data.responseText == "Registration number taken! Please choose another one."){
				$( "#resultVehicle" ).html( data.responseText );
				return;
			}
			if(type == "car"){
				showCar();
			}else if(type == "bicycle"){
				showBicycle();
			}else if(type == "scooter"){
				showScooter();
			}
		}
	});
}

function deleteVehicle(name,type){
	$.ajax({
		url: "rest/vehicles/delete",
		type:"DELETE",
		data: name,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if(type == "car"){
				showCar();
			}else if(type == "bicycle"){
				showBicycle();
			}else if(type == "scooter"){
				showScooter();
			}
		}
	});
}

function editVehicle(id){
	$(".all-articles").hide();
	$(".edit-page").show();
	$.ajax({
		url: "rest/vehicles/getAll",
		type:"GET",
		complete: function(data) {
			var vehicles = JSON.parse(data.responseText);
			
			for(var i = 0; i < vehicles.length; i++){
				if(vehicles[i].registration_number==id){
					$(".edit-brand").val(vehicles[i].brand);
					$(".edit-model").val(vehicles[i].model);
					$(".edit-brand").val(vehicles[i].brand);
					$(".edit-registration_number").val(vehicles[i].registration_number);
					$(".edit-year").val(vehicles[i].year);
					$(".edit-note").val(vehicles[i].note);
					if(vehicles[i].usable==true){
						$(".edit-usable").prop('checked', true);
					}else{
						$(".edit-usable").prop('checked', false);
					}
					
					var Obj = document.getElementById("confirm-but")
					if(Obj.outerHTML) {
					    Obj.outerHTML="<input type=\"button\" class=\"btn btn-primary\" id=\"confirm-but\" value=\"OK\" onclick=\"confirmEdit(\'"+ vehicles[i].type +"\')\"/> <br />";
					}
				}
			}
		}
	});
}

function confirmEdit(type){	
	var $form = $("#edit-form");
	var data = getFormData($form);
	if(data.usable=="usable"){
		data.usable=1;
	}else{
		data.usable=0;
	}
	var s = JSON.stringify(data);
	$.ajax({
		url: "rest/vehicles/edit",
		type:"PUT",
		data: s,
		contentType:"application/json",
		dataType:"json",
		complete: function(data) {
			if(data.responseText == "Fill all fields!" || data.responseText == "Registration number taken! Please choose another one."){
				$( "#resultEditVehicle" ).html( data.responseText );
				return;
			}
			if(type == "car"){
				showCar();
			}else if(type == "bicycle"){
				showBicycle();
			}else if(type == "scooter"){
				showScooter();
			}
		}
	});
}

$("document").ready(function(){
	$(".all-articles").hide();
	
	$("#add-car-button").click(function(){
		var Obj = document.getElementById("confirm-add-vehicle")
		if(Obj.outerHTML) {
		    Obj.outerHTML="<input type=\"button\" id=\"confirm-add-vehicle\" class=\"btn btn-primary\" value=\"ADD\" onclick=\"addVehicle(\'car\')\"/>";
		}
	});

	$("#add-bicycle-button").click(function(){
		var Obj = document.getElementById("confirm-add-vehicle")
		if(Obj.outerHTML) {
		    Obj.outerHTML="<input type=\"button\" id=\"confirm-add-vehicle\" class=\"btn btn-primary\" value=\"ADD\" onclick=\"addVehicle(\'bicycle\')\"/>";
		}
	});

	$("#add-scooter-button").click(function(){
		var Obj = document.getElementById("confirm-add-vehicle")
		if(Obj.outerHTML) {
		    Obj.outerHTML="<input type=\"button\" id=\"confirm-add-vehicle\" class=\"btn btn-primary\" value=\"ADD\" onclick=\"addVehicle(\'scooter\')\"/>";
		}
	});
});



