package common

import bgpasservice.*;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.CronScheduleBuilder.*;


import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import groovy.util.logging.Log4j;
import net.redhogs.cronparser.*;

@Log4j
public class ScheduleFlowHelper  {
 
   private static ScheduleFlowHelper JOB_SCHEDULER = new ScheduleFlowHelper();
    private Scheduler scheduler=null;
	
	ScheduleFlowHelper()
	{
		//scheduler = StdSchedulerFactory.getDefaultScheduler();
		//scheduler.start();
	}
 
    public static ScheduleFlowHelper  getInstance() {
		if(JOB_SCHEDULER == null)
		{
			JOB_SCHEDULER = new ScheduleFlowHelper();
		}
		return JOB_SCHEDULER;
    }
 
	public void startup() {
		try {
			// and start it off
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
		} 
		catch (SchedulerException se) {
		se.printStackTrace();
		}
    }
	
     public void scheduleflow(bgpasservice.Scheduler schedulerEntry, String operation,flowid,scheduleFlag,deleteFlag,exabgpPath)
	 {
		 def SendMailControllerObj = new  SendMailController()
		 
			 if(!scheduler)
			 {
				 scheduler = StdSchedulerFactory.getDefaultScheduler();
				 scheduler.start(); 
			 }
			 
		 	def subject = ""
			if(schedulerEntry == null)
			{
				 log.error "ERROR!!:schedulerEntry is Null. Cannot schedule the job for this flow"
				 return;
			}
			
			if(flowid == "" || flowid == null)
			{
				subject = "Job with scheduler Id "+schedulerEntry.id+" could not be scheduled since flow Id is NULL"
				SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
				log.error "ERROR!!: "+subject
				return;
			}
			
			def triggerId = "Trigger_"+flowid
			def unscheduletriggerId = "Unscheduletrigger_"+flowid
			def deletetriggerId = "Deletetrigger_"+flowid
			def jobId = "Job_"+flowid
			def unschedulejobId = "Unschedulejob_"+flowid
			def deletejobId = "Deletejob_"+flowid
			Trigger trigger,unscheduleTrigger, deleteTrigger;
			JobDetail job, unscheduleJob, deleteJob;
			
			def flowEntry = Flow.findById(flowid)
			if(flowEntry == null)
			{
				subject = "Job with scheduler Id "+schedulerEntry.id+" could not be scheduled since flow Entry is not found for flow ID "+flowid
				SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
				log.error "ERROR!!: "+subject
				return;
			}
			
			def neighbor = Neighbor.findById(flowEntry.neighborsId)
			if(neighbor == null)
			{
				subject = "Job with scheduler Id "+schedulerEntry.id+" could not be scheduled since Neighbor Entry is not found for neighbor id "+flowEntry.neighborsId
				SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
				log.error "ERROR!!: "+subject
				return;
			}
			
			def spt = Port.findById(flowEntry.sourceportId)
			if(spt == null)
			{
				subject = "Job with scheduler Id "+schedulerEntry.id+" could not be scheduled since source port is not found for port id "+flowEntry.sourceportId
				SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
				log.error "ERROR!!: "+subject
				return;
			}
			
			def dpt = Port.findById(flowEntry.destinationportId)
			if(dpt == null)
			{
				subject = "Job with scheduler Id "+schedulerEntry.id+" could not be scheduled since destination port is not found for port id "+flowEntry.destinationportId
				SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
				log.error "ERROR!!: "+subject
				return;
			}
			
			def prot = Protocol.findById(flowEntry.protocolId)
			if(prot == null)
			{
				subject = "Job with scheduler Id "+schedulerEntry.id+" could not be scheduled since protocol is not found for protocol id "+flowEntry.protocolId
				SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
				log.error "ERROR!!: "+subject
				return;
			}
			
			def exaAction = Exaaction.findById(flowEntry.exaactionId)
			if(exaAction == null)
			{
				subject = "Job with scheduler Id "+schedulerEntry.id+" could not be scheduled since Action is not found for Exa Action id "+flowEntry.exaactionId
				SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
				log.error "ERROR!!: "+subject
				return;
			}

			def flowJson = Utility.getInstance().buildFlowJson(flowEntry.sourceSubnet, flowEntry.destinationSubnet, spt.portRule, dpt.portRule, prot.protocolRule, exaAction.actionName, exaAction.actionParameter)
			
			if(scheduleFlag)
			{
					try
					{
						job = newJob(FlowSchedule.class)
									.withIdentity(jobId, "group")
									.build();
						
						if(schedulerEntry.scheduleOption == 'Schedule Periodically')
						{
							unscheduleJob = newJob(FlowSchedule.class)
							.withIdentity(unschedulejobId, "group")
							.build();
						}
										
						if(schedulerEntry.scheduleOption == 'Schedule Once')
						{			
							trigger = newTrigger()
										.withIdentity(triggerId,"group")
										.startAt(schedulerEntry.scheduleTime)
										.build();
						}
						else if(schedulerEntry.scheduleOption == 'Schedule Periodically')
						{
							trigger = newTrigger()
										.withIdentity(triggerId,"group")
										.withSchedule(cronSchedule(schedulerEntry.activationSchedule))
										.build();
							unscheduleTrigger = newTrigger()
										.withIdentity(unscheduletriggerId,"group")
										.withSchedule(cronSchedule(schedulerEntry.deactivationSchedule))
										.build();
						}
						else
						{
							trigger = newTrigger()
							.withIdentity(triggerId,"group")
							.startNow()
							.build();
						}
					
						trigger.jobDataMap.putAll ([schedulerid:schedulerEntry.id, operation:operation,exabgpPath:exabgpPath])
						
						if(schedulerEntry.scheduleOption == 'Schedule Periodically')
						{
							unscheduleTrigger.jobDataMap.putAll ([schedulerid:schedulerEntry.id, operation:'unschedule',exabgpPath:exabgpPath])
						}
					}
					catch(Exception e)
					{
						subject = "Job with scheduler Id "+schedulerEntry.id+" could not be scheduled since exception occured while building job and trigger"
						SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
						log.error "ERROR!!: "+subject
						return;
					}

					if(schedulerEntry.scheduleOption == 'Schedule Once')
					{
						subject="This email is to notify that below flow for neighbor "+ neighbor.neighborDescription +" will be added/updated at "+ schedulerEntry.scheduleTime +"\n\n"+flowJson
					}
					else if(schedulerEntry.scheduleOption == 'Schedule Periodically')
					{
						subject="This email is to notify that below flow for neighbor "+ neighbor.neighborDescription +" will be scheduled at "+ CronExpressionDescriptor.getDescription(schedulerEntry.activationSchedule) +" and unscheduled at "+CronExpressionDescriptor.getDescription(schedulerEntry.deactivationSchedule)+"\n\n"+flowJson
					}
					
					try
					{
						scheduler.scheduleJob(job,trigger)
		
						if(schedulerEntry.scheduleOption == 'Schedule Once')
						{
							 log.error "Below email is being sent to "+schedulerEntry.email+"\n\n"+subject
						     SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
						}
						else if(schedulerEntry.scheduleOption == 'Schedule Periodically')
						{
							scheduler.scheduleJob(unscheduleJob,unscheduleTrigger)
							//scheduler.start()
							log.error "Below email is being sent to "+schedulerEntry.email+"\n\n"+subject
							SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
						}
					}
					catch(Exception	ObjectAlreadyExistsException)
					{
						scheduler.unscheduleJob(new TriggerKey(triggerId,"group"))
						scheduler.unscheduleJob(new TriggerKey(unscheduletriggerId,"group"))
						scheduler.scheduleJob(job,trigger)
						//scheduler.start()
						
						if(schedulerEntry.scheduleOption == 'Schedule Once')
						{
							log.error "Below email is being sent to "+schedulerEntry.email+"\n\n"+subject
						    SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
						}
						else if(schedulerEntry.scheduleOption == 'Schedule Periodically')
						{
							scheduler.scheduleJob(unscheduleJob,unscheduleTrigger)
							//scheduler.start()
							log.error "Below email is being sent to "+schedulerEntry.email+"\n\n"+subject
							SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
						}
					}
					catch(Exception e)
					{
						subject = "Job with scheduler Id "+schedulerEntry.id+" could not be scheduled since exception occured while scheduling job"
						SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
						log.error "ERROR!!: "+subject
						return;
					}
			}
			
			if(deleteFlag)
			{
				try
				{
					if(schedulerEntry.deleteScheduleOption == 'Delete Now' || schedulerEntry.deleteScheduleOption == 'Delete Later')
					{
						deleteJob = newJob(FlowSchedule.class)
						.withIdentity(deletejobId, "group")
						.build();
					
						if(schedulerEntry.deleteScheduleOption == 'Delete Now')
						{
							deleteTrigger = newTrigger()
							.withIdentity(deletetriggerId,"group")
							.startNow()
							.build();
						}
						if(schedulerEntry.deleteScheduleOption == 'Delete Later')
						{
							deleteTrigger = newTrigger()
							.withIdentity(deletetriggerId,"group")
							.startAt(schedulerEntry.deleteScheduleTime)
							.build();
						}
						
						deleteTrigger.jobDataMap.putAll ([schedulerid:schedulerEntry.id, operation:'delete',exabgpPath:exabgpPath])
						
						scheduler.scheduleJob(deleteJob,deleteTrigger)
						//scheduler.start()
						if(schedulerEntry.deleteScheduleOption == 'Delete Later')
						{
							subject="This email is to notify that below flow for neighbor "+ neighbor.neighborDescription +" will be deleted at "+ schedulerEntry.deleteScheduleTime +"\n\n"+flowJson
							log.error "Below email is being sent to "+schedulerEntry.email+"\n\n"+subject
							SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
						}
						
					}
					else if(schedulerEntry.deleteScheduleOption == 'Dont Delete' && operation == 'edit')
					{
						scheduler.unscheduleJob(new TriggerKey(deletetriggerId,"group"))
					}
				}
				catch(Exception	ObjectAlreadyExistsException)
				{
					scheduler.unscheduleJob(new TriggerKey(deletetriggerId,"group"))
					scheduler.scheduleJob(deleteJob,deleteTrigger)
					if(schedulerEntry.deleteScheduleOption == 'Delete Later')
					{
						 subject="This email is to notify that below flow for neighbor "+ neighbor.neighborDescription +" will be deleted at "+ schedulerEntry.deleteScheduleTime +"\n\n"+flowJson
						 log.error "Below email is being sent to "+schedulerEntry.email+"\n\n"+subject
						 SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
					}
				}
				catch(Exception e)
				{
					subject = "Job with scheduler Id "+schedulerEntry.id+" and Job ID "+deletejobId+" could not be scheduled since exception occured while scheduling job"
					SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
					log.error "ERROR!!: "+subject
					return;
				}
			}
			
    }
	
	public void shutdown() {
		try {
			scheduler.shutdown();
		} catch (SchedulerException se) {
			log.error "ERROR:!!"+se.printStackTrace()
		}
	}
	
}