//Check if jQuery included
if (typeof jQuery == 'undefined') {
    alert("ERROR: jQuery library loading failed!");
}

//Document ready method
$( document ).ready(function() {


	//Click Listener for Block Button
     $("#block").click( 
	function()
	{
		var input = $("#input").val();
		
		//If no input provided
		if(input == "")
		{
			//Don't send the AJAX call to server
			return;
		}
		
		//Get the selection
		var selection = $("#selection option:selected").text();
		
		console.log("Block "+ selection + ": " + input);
		
		var blockURL;
		
		//Select the URL based on user selection
		if(selection == "Protocols")
		{
			blockURL = "/learningswitch/northbound/block/Protocol/" + input;
		}
		else if(selection == "Ports")
		{
			blockURL = "/learningswitch/northbound/block/Port/" + input;
		}
		else if(selection == "IP Addresses")
		{
			blockURL = "/learningswitch/northbound/block/IP/" + input;
		}
		else if(selection == "MAC Addresses")
		{
			blockURL = "/learningswitch/northbound/block/MAC/" + input;
		}

		//AJAX Call for Block
		$.ajax({
            type: 'PUT',
            url: blockURL
          });
          
          //Refresh Blocked List
          $("#refreshList").click();
     });
     
     
	
	//Click Listener for Unblock Button
     $("#unblock").click( 
	function()
	{
		var input = $("#input").val();
		
		//If no input provided
		if(input == "")
		{
			//Don't send the AJAX call to server
			return;
		}
		
		//Get the selection
		var selection = $("#selection option:selected").text();
		
		console.log("Block "+ selection + ": " + input);
		
		var unblockURL;
		
		//Select the URL based on user selection
		if(selection == "Protocols")
		{
			unblockURL = "/learningswitch/northbound/unblock/Protocol/" + input;
		}
		else if(selection == "Ports")
		{
			unblockURL = "/learningswitch/northbound/unblock/Port/" + input;
		}
		else if(selection == "IP Addresses")
		{
			unblockURL = "/learningswitch/northbound/unblock/IP/" + input;
		}
		else if(selection == "MAC Addresses")
		{
			unblockURL = "/learningswitch/northbound/unblock/MAC/" + input;
		}

		//AJAX Call for Unblock
		$.ajax({
            type: 'DELETE',
            url: unblockURL
          });
          
          //Refresh Blocked List
          $("#refreshList").click();
	});  
	
	
	
	//Click Listener for Refresh List Button
     $("#refreshList").click( 
	function()
	{
		//Get the selection
		var selection = $("#selection option:selected").text();
		
		console.log("Refresh "+ selection + " Blocked List");
		
		var getDataURL;
		
		//Select the URL based on user selection
		if(selection == "Protocols")
		{
			getDataURL = "/learningswitch/northbound/get/Protocols";
		}
		else if(selection == "Ports")
		{
			getDataURL = "/learningswitch/northbound/get/Ports";
		}
		else if(selection == "IP Addresses")
		{
			getDataURL = "/learningswitch/northbound/get/IPs";
		}
		else if(selection == "MAC Addresses")
		{
			getDataURL = "/learningswitch/northbound/get/MACs";
		}

		//AJAX Call for Unblock
		$.ajax({
			type: 'GET',
			url: getDataURL,
			//dataType: 'text/html',
          	success: function(response)
		     {
		     	console.log("Response: " + response);
		     	//Update the Blocked List
		     	$("#blockedList").html(response);
		     },
		     error: function(object, reason)
		     {
		     	console.log("Error in AJAX call: " + reason);
		     }
		}); 
	});
});
