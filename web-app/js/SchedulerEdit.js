$(document).ready(function(){

$(":radio[name= 'scheduleOption']").prop('disabled',true);
$("select[name^='scheduleTime_']").prop('disabled',true);


 $('#schedulerform').bind('submit', function() {
        $(":radio[name= 'scheduleOption']").prop('disabled',false);
		 $("select[name^='scheduleTime_']").prop('disabled',false);
    });
    
});