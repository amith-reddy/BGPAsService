$(document).ready(function(){

$('.cron').jqCron({
					enabled_minute: true,
			        multiple_dom: true,
			        multiple_month: true,
			        multiple_mins: true,
			        multiple_dow: true,
			        multiple_time_hours: true,
			        multiple_time_minutes: true,
			        default_period: 'week',
			        lang: 'en'		
 } );

if ($(":radio[name= 'scheduleOption']:checked").val() == 'Execute Now') {
       	   $("#scheduleTimeId").hide();
       	   $("#scheduleTimeId").prop('required',false);
       	   $("#scheduletimedateid").val('');
		   
		   $("#activationCronDivId").hide();
   	   $("#activationCronDivId").prop('required',false);
   	   $('#activationSchedule').val('');
   	    $('#activationSchedule').prop('disabled',true);
   	   $("#deactivationCronDivId").hide();
   	   $("#deactivationCronDivId").prop('required',false);
   	   $('#deactivationSchedule').val('');
   	   $('#deactivationSchedule').prop('disabled',true);

}
else if ($(":radio[name= 'scheduleOption']:checked").val() == 'Schedule Once') {
	   $("#scheduleTimeId").show();
       $("#scheduleTimeId").prop('required',true);
       
       $("#activationCronDivId").hide();
   	   $("#activationCronDivId").prop('required',false);
   	   $('#activationSchedule').val('');
   	    $('#activationSchedule').prop('disabled',true);
   	   $("#deactivationCronDivId").hide();
   	   $("#deactivationCronDivId").prop('required',false);
   	   $('#deactivationSchedule').val('');
   	   $('#deactivationSchedule').prop('disabled',true);

}  
else if ($(":radio[name= 'scheduleOption']:checked").val() == 'Schedule Periodically') {
	 $("#scheduleTimeId").hide();
     $("#scheduleTimeId").prop('required',false);
     
     $('#activationSchedule').prop('disabled',false);
	 $('#deactivationSchedule').prop('disabled',false);
     $("#activationCronDivId").show();
     $("#activationCronDivId").prop('required',true);
     $("#deactivationCronDivId").show();
     $("#deactivationCronDivId").prop('required',true);
}


if ($(":radio[name= 'deleteScheduleOption']:checked").val() == 'Dont Delete'
		|| $(":radio[name= 'deleteScheduleOption']:checked").val() == 'Delete Now') {
       	    $("#deleteScheduleTimeId").hide();
     		$("#deleteScheduleTimeId").prop('required',false);

}
 
 $('input[name^=deleteScheduleOption]').on('change',function(e) {

   		if ($(":radio[name= 'deleteScheduleOption']:checked").val() == 'Dont Delete'
		|| $(":radio[name= 'deleteScheduleOption']:checked").val() == 'Delete Now') {
       	    $("#deleteScheduleTimeId").hide();
     		$("#deleteScheduleTimeId").prop('required',false);

		}
		else if ($(":radio[name= 'deleteScheduleOption']:checked").val() == 'Delete Later') {
       	    $("#deleteScheduleTimeId").show();
     		$("#deleteScheduleTimeId").prop('required',true);

		}
});

 $('input[name^=scheduleOption]').on('change',function(e) {

   		if ($(":radio[name= 'scheduleOption']:checked").val() == 'Execute Now') {
	       	   $("#scheduleTimeId").hide();
	       	   $("#scheduleTimeId").prop('required',false);
	       	   $("#scheduletimedateid").val('');
			   
			   $("#activationCronDivId").hide();
		   	   $("#activationCronDivId").prop('required',false);
		   	   $('#activationSchedule').val('');
		   	    $('#activationSchedule').prop('disabled',true);
		   	   $("#deactivationCronDivId").hide();
		   	   $("#deactivationCronDivId").prop('required',false);
		   	   $('#deactivationSchedule').val('');
		   	   $('#deactivationSchedule').prop('disabled',true);
		
		}
		else if ($(":radio[name= 'scheduleOption']:checked").val() == 'Schedule Once') {
			   $("#scheduleTimeId").show();
		       $("#scheduleTimeId").prop('required',true);
		       
		  
		       $("#activationCronDivId").hide();
		   	   $("#activationCronDivId").prop('required',false);
		   	   $('#activationSchedule').val('');
		   	    
		   	   $("#deactivationCronDivId").hide();
		   	   $("#deactivationCronDivId").prop('required',false);
		   	   $('#deactivationSchedule').val('');
		   	   $('#activationSchedule').prop('disabled',true);
		       $('#deactivationSchedule').prop('disabled',true);
		
		}  
		else if ($(":radio[name= 'scheduleOption']:checked").val() == 'Schedule Periodically') {
			 $("#scheduleTimeId").hide();
		     $("#scheduleTimeId").prop('required',false);
		     
		     $('#activationSchedule').prop('disabled',false);
			 $('#deactivationSchedule').prop('disabled',false);
		     $("#activationCronDivId").show();
		     $("#activationCronDivId").prop('required',true);
		     $('#activationSchedule').val('0 * * ? * *');
		     $("#deactivationCronDivId").show();
		     $("#deactivationCronDivId").prop('required',true);
		     $('#deactivationSchedule').val('0 * * ? * *');
		}
});
});

