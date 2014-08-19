$(document).ready(function (){
		       		
	   if ($('#action_name option:selected').text() == 'discard') {
	       	   $('#actionParameterValue').val('null');
	           $("#action_parameter").hide();
	   }
	   else if($('#action_name option:selected').text() == 'rate-limit') {
	           $('#actionparametertip').text('Bytes per second. Example: 9600');
	   }
	    else if($('#action_name option:selected').text() == 'redirect') {
	           $('#actionparametertip').text('title', 'Enter a Valid IP(10.10.10.10) or ValidIP:Port(10.10.10.10:1234) or ASNumber:LocalPreference(65000:65000)');
	   }
	   
	   $("#action_name").change(function() {
			
	       if ($('#action_name option:selected').text() == 'discard') {
	       	   $('#actionParameterValue').val('null');
	           $("#action_parameter").hide();
	       }
	       else if($('#action_name option:selected').text() == 'rate-limit') {
	       	   $('#actionParameterValue').val('');
	           $("#action_parameter").show();
	           $('#actionparametertip').text('Bytes per second. Example: 9600');
	       } 
	       else if($('#action_name option:selected').text() == 'redirect') {
	       	   $('#actionParameterValue').val('');
	           $("#action_parameter").show();
	           $('#actionparametertip').text('IP(10.10.10.10) or IP:Port(10.10.10.10:1234) or ASNumber:LocalPreference(16815:65000)');
	       } 
	   });
		
});

