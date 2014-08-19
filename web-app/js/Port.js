function setFormParameter(subportrule, i)
{
   		var tmpPortRule = subportrule.split('&amp;');
		var tmpPortOption = "",tmpPortStart = "", tmpPortEnd = "";

		if(tmpPortRule.length == 1)
		{
			tmpPortOption = 'value';
			var tmpPortStartArr = tmpPortRule[0].split('=');
			tmpPortStart = tmpPortStartArr[1];
			tmpPortEnd = ""
		}
		else if(tmpPortRule.length == 2)
		{
			tmpPortOption = 'range';
			var tmpPortStartArr = tmpPortRule[0].split('&gt;=');
			tmpPortStart = tmpPortStartArr[1];
			var tmpPortEndArr = tmpPortRule[1].split('&lt;=');
			tmpPortEnd = tmpPortEndArr[1];
		}
		
		if (tmpPortOption == 'value') {
           	   $("#end_port"+i).hide();
           	   $("#portendvalue"+i).prop('required',false);
        }
        else
        {
        	  $("#end_port"+i).show();
           	   $("#portendvalue"+i).prop('required',true);
        }
        
		$("input:radio[name=PortOption"+i+"][value="+tmpPortOption+"]").prop('checked', true);
		$("#portstartvalue"+i).val(tmpPortStart);
		$("#portendvalue"+i).val(tmpPortEnd);
}
		   
   $(document).ready(function(){
			
 if ($(":radio[name= 'PortOption1']:checked").val() == 'value') {
       	   $("#end_port1").hide();
       	   $("#portendvalue1").val('');
       	   $("#portendvalue1").prop('required',false);
}

 $('input[name^=PortOption]').on('change',function(e) {
 		var vname = this.name
 		var i = parseInt(/PortOption(\d+)/.exec(vname)[1], 10);

   		if ($(":radio[name= '"+vname+"']:checked").val() == 'value') {
	       $("#end_port"+i).hide();
	        $("#portendvalue1").val('');
	       $("#portendvalue"+i).prop('required',false);
   		}
   		else
   		{
       		$("#end_port"+i).show();
      		 $("#portendvalue"+i).prop('required',true);
   		}  
});
	           
	                                
$('#portform').submit( function(event) {

	var portrule = "";
	var num     = $('.clonedInput').length;
	for(var i=1;i<=num;i++)
	{
		var vname = "PortOption"+i, vportStart = "portStart"+i, vportEnd = "portEnd"+i;

		if(portrule != "")
			portrule = portrule.concat(' ');
	
    	if ($(":radio[name= '"+vname+"']:checked").val() == 'value'){
    		portrule = portrule.concat('=');
    		portrule = portrule.concat($("input[name= '"+vportStart+"']").val());

    	}
    	else
    	{
    		portrule = portrule.concat('>=');
    		portrule = portrule.concat($("input[name= '"+vportStart+"']").val());
    		portrule = portrule.concat('&<=');
    		portrule = portrule.concat($("input[name= '"+vportEnd+"']").val());
    	}
    }
	document.getElementById("portRule").value = portrule;
	//alert(document.getElementById("portRule").value);
	
});
			    

$('#btnAdd').click(function () {

    var num     = $('.clonedInput').length, // Checks to see how many "duplicatable" input fields we currently have
    newNum  = new Number(num + 1),      // The numeric ID of the new input field being added, increasing by 1 each time
    newElem = $('#entry' + num).clone(true).attr('id', 'entry' + newNum); // create the new element via clone(), and manipulate it's ID using newNum value

    newElem.find('.heading-reference').attr('id', 'ID' + newNum + '_reference').attr('name', 'ID' + newNum + '_reference').html('Port Rule #' + newNum);

    newElem.find('#portradio'+num).attr('id', 'portradio'+ newNum);
    newElem.find("input[name='PortOption"+num+"']").attr('name', 'PortOption'+ newNum);
    newElem.find('#label'+num).attr('id', 'label'+ newNum).attr('for', 'PortOption'+ newNum);
    
    newElem.find('#start_port'+num).attr('id', 'start_port'+ newNum);
    newElem.find('#portstartvalue'+num).attr('id', 'portstartvalue'+ newNum).attr('name', 'portStart'+ newNum).val('');
    
    newElem.find('#end_port'+num).attr('id', 'end_port'+ newNum);
    newElem.find('#portendvalue'+num).attr('id', 'portendvalue'+ newNum).attr('name', 'portEnd'+ newNum).val('');
   
	 // Insert the new element after the last "duplicatable" input field
    $('#entry' + num).after(newElem);
    $("input:radio[name=PortOption"+newNum+"][value='value']").prop('checked', true);
	$("#end_port"+newNum).hide();
    $("#portendvalue"+newNum).prop('required',false);

	// Enable the "remove" button. This only shows once you have a duplicated section.
    $('#btnDel').attr('disabled', false);

	if (newNum == 5)
    $('#btnAdd').attr('disabled', true).prop('value', "Add port rule"); // value here updates the text in the 'add' button when the limit is reached 
});

$('#btnDel').click(function () {
    
        if (confirm("Are you sure?"))
        {
                var num = $('.clonedInput').length;
                
                $('#entry' + num).slideUp('slow', function () {
                		$(this).remove();

		                if (num - 1 == 1)
		                $('#btnDel').attr('disabled', true);

                		$('#btnAdd').attr('disabled', false).prop('value', "Add port rule");    
                });
                
                 // Enable the "add" button
			    $('#btnAdd').attr('disabled', false);
			    // Disable the "remove" button
			    $('#btnDel').attr('disabled', true);	 
        }
  
        return false; // Removes the last section you added

});
 
});
 