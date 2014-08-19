package bgpasservice

class Scheduler {
	Flow newFlows
	String scheduleOption = "Execute Now"
	Date scheduleTime 
	String activationSchedule
	String deactivationSchedule
	String deleteScheduleOption = "Dont Delete"
	Date deleteScheduleTime
	String name
	String email
	
//	static mappings = {
//		scheduleOption:"execute"
//		deleteScheduleOption: "dontdelete"
//	}
	
    static constraints = {
		newFlows(blank:false,nullable:false,unique:true)
		scheduleOption(blank:false,nullable:false,inList:['Execute Now','Schedule Once','Schedule Periodically'])
		scheduleTime(blank:true,nullable:true,validator:{val,obj ->
			if(obj.scheduleOption == 'Schedule Once' && !obj.id)
			{
					if (val <= new Date())
					{ return 'invalid.scheduledate' }
			}
			
		})
		activationSchedule(blank:true,nullable:true,validator:{val,obj ->
			if(obj.scheduleOption == 'Schedule Periodically')
			{
				if(val == '' || val == null)
				{
					return 'invalid.blank'
				}
				else if(!(org.quartz.CronExpression.isValidExpression(val)))
				{
					return 'invalid.cronExpression'
				}
			}
			else if((obj.scheduleOption == 'Execute Now' || obj.scheduleOption == 'Schedule Once')&& (val != null))
			{
				return 'invalid.notblank'
			}
		})
		deactivationSchedule(blank:true,nullable:true,validator:{val,obj ->
			if(obj.scheduleOption == 'Schedule Periodically')
			{
				if(val == '' || val == null)
				{
					return 'invalid.blank'
				}
				else if(!(org.quartz.CronExpression.isValidExpression(val)))
				{
					return 'invalid.cronExpression'
				}
			}
			else if((obj.scheduleOption == 'Execute Now' || obj.scheduleOption == 'Schedule Once')&& (val != null))
			{
				return 'invalid.notblank'
			}
		})
		deleteScheduleOption(blank:false,nullable:false,inList:['Dont Delete','Delete Now','Delete Later'])
		deleteScheduleTime(blank:true,nullable:true,validator:{val,obj ->
			if(obj.deleteScheduleOption == 'Delete Later')
			{
				if (val <= new Date())
				{	  return 'invalid.deletescheduledate' }
			}
			
		})
		name(blank:true,nullable:true,maxSize:40)
		email(blank:true,nullable:true,maxSize:256,email:true)
    }
}
