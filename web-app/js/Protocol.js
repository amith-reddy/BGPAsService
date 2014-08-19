$(function() {
	//Form the protocol rule based on checkbox values
    $("#protocolform").submit(function() {
    	var protocolrule = "";

        $("input.Checkbox[type=checkbox]:checked").each(function() {
        	if(protocolrule != "")
        		protocolrule = protocolrule.concat(" ");
        	
    		protocolrule = protocolrule.concat($(this).val());
        });
		
		document.getElementById("protocolRule").value = protocolrule;

    });
});