package common

import java.util.Date;
import bgpasservice.*;

//import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import grails.plugin.mail.*;

import java.lang.Runtime;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import static org.quartz.TriggerBuilder.newTrigger;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
//import org.apache.commons.logging.LogFactory;
import groovy.util.logging.Log4j;


@Log4j
 public class FlowSchedule implements Job {
	 	//private static final log = LogFactory.getLog(this)
	    def inputStreamString = ""
		def grailsApplication
		org.quartz.Scheduler scheduler=null;
		public static final int SLEEP_VALUE = 5;
		
		public void readInputStream(grepproc){
					   inputStreamString = "";
					   grepproc.inputStream.eachLine {
						   inputStreamString=inputStreamString+it+"\n"
					   }
		 }
		
		
		public Boolean unscheduleJobs(flowid){
			def triggerId = "Trigger_"+flowid
			def unscheduletriggerId = "Unscheduletrigger_"+flowid
			
			try{
				if(!scheduler)
			    {
				   scheduler = StdSchedulerFactory.getDefaultScheduler();
				   scheduler.start();
			    }
									   
		   		scheduler.unscheduleJob(new TriggerKey(triggerId,"group"))
				scheduler.unscheduleJob(new TriggerKey(unscheduletriggerId,"group"))
				
				return true;
			}
			catch(Exception e)
			{
				log.error "ERROR!!:Exception occured while unscheduling the job with trigger id "+triggerId
				return false;
			}
		}
		
		Boolean killOldBGPProcess(String neighborDescription)
		{
			//return true;
			try
			{
				 def pidcommand = new String[3]
				 pidcommand[0] = "sh"
				 pidcommand[1] = "-c"
				 pidcommand[2] = "ps -o pid,cmd -e| grep neigbhor_"+neighborDescription+".conf|grep -v grep|awk \"{print \\\$1}\""
				 def grepproc = new ProcessBuilder(pidcommand).redirectErrorStream(true).start()

				//log.error "command is :"+pidcommand[2]
				
				grepproc.inputStream.eachLine {
					  String killcommand = "kill -9 "+it
					  //log.error "command is :"+killcommand
					  def killproc = killcommand.execute()
					  killproc.waitFor();
					  def exitVal = killproc.exitValue()
 
					  if(exitVal != 0)
					  {
						  log.error "Couldn't kill the PID:"+it
						  return false;
					  }
				}
				return true;
			}
			catch(Exception e)
			{
				log.error "ERROR!!:Exception occured while executing kill command for neighbor "+neighborDescription
				return false;
			}
		}
		
		boolean executeExabgp(Neighbor neighbor, String exabgpPath)
		{
			//return true;
			try
			{
				def pidcommand = new String[3]
				pidcommand[0] = "sh"
				pidcommand[1] = "-c"
				if(exabgpPath == '')
					pidcommand[2] = "exabgp ./BGP/neigbhor_" + neighbor.neighborDescription + ".conf"
				else
					pidcommand[2] = exabgpPath+" ./BGP/neigbhor_" + neighbor.neighborDescription + ".conf"
					
				def grepproc = new ProcessBuilder(pidcommand).redirectErrorStream(true).start()
				
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
		
					@Override
					public void run() {
						readInputStream(grepproc);
					}
				}, 0);
					
				TimeUnit.SECONDS.sleep(SLEEP_VALUE);
				timer.cancel();
				log.error "inputStreamString:"+inputStreamString
				def connectmatchPattern = "Connected to peer neighbor "+neighbor.neighborIp+" local-ip "+neighbor.localIp+" local-as "+neighbor.localAS+" peer-as "+neighbor.peerAS+" router-id "+ neighbor.routerId +" family-allowed in-open (out)"
				def processmatchPattern = "outgoing peer "+neighbor.neighborIp+" ASN "+neighbor.peerAS+"   processed 1 routes"

				if(inputStreamString.contains(connectmatchPattern) && inputStreamString.contains(processmatchPattern))
				{
						return true;
				}
				else
				{
					log.error "Flow Rules are not successfully processed by ExaBGP Tool!!"
					   return false;
				}
			}
			catch(Exception e)
			{
				log.error "ERROR!!:Exception occured while executing the exabgp command for neighbor "+neighbor.neighborDescription
				return false;
			}
			
		}
		
		Integer BGPProcessExists(String neighborDescription)
		{
			try
			{
				 def pidcommand = new String[3]
				 pidcommand[0] = "sh"
				 pidcommand[1] = "-c"
				 pidcommand[2] = "ps -o pid,cmd -e| grep neigbhor_"+neighborDescription+".conf|grep -v grep|awk \"{print \\\$1}\""
				 def grepproc = new ProcessBuilder(pidcommand).redirectErrorStream(true).start()

				//log.error "command is :"+pidcommand[2]
				
				def ProcOutput = grepproc.inputStream.getText();
				
				if(ProcOutput)
				{
					return 1
				}
				else
				{
					return 0;
				}
			}
			catch(Exception e)
			{
				log.error "ERROR!!:Exception occured while checking if the ExaBGP process exists for neighbor "+neighborDescription
				return -1;
			}
		}
	 
	   public void execute(JobExecutionContext context)
			   throws JobExecutionException {
			   def SendMailControllerObj = new  SendMailController()
			   def JobName = context.jobDetail.key.name
			   def subject = ""
			   
			   String exabgpPath = context.mergedJobDataMap.get('exabgpPath')
			   String operation = context.mergedJobDataMap.get('operation')
			   String schedulerid = context.mergedJobDataMap.get('schedulerid')
			   if(schedulerid == null || schedulerid == "")
			   {
				   log.error "ERROR!!: Scheduler ID received by the Job "+JobName+" is null"
				   return;
			   }
			   
			   def schedulerEntry = Scheduler.findById(schedulerid)
			   if(schedulerEntry == null)
			   {
				   log.error "ERROR!!: Job "+JobName+" could not be executed since scheduler Entry could not be retrieved based on scheduler id "+schedulerid
				   return;
			   }
			   
			   def flowEntry = Flow.findById(schedulerEntry.newFlowsId)
			   if(flowEntry == null)
			   {
				   subject = "Job "+JobName+" could not be executed since Flow Entry based on flow id "+schedulerEntry.newFlowsId+" could not be found"
				   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
				   log.error "ERROR!!: "+subject
				   return;
			   }
 
			   //Delete the flow with status 'new'. No need to check the ExaBGP Process.
			   if(operation == "delete" && flowEntry.status=="new")
			   {
				   schedulerEntry.delete flush:true
				   
				   
				   subject = "This email is to notify Scheduler entry for Flow ID "+flowEntry.id+" is deleted"
				   log.error "Below email is being sent to "+schedulerEntry.email+"\n\n"+subject
				   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)

				   
				   def success = unscheduleJobs(schedulerEntry.newFlowsId)
				   if(!success)
				   {
					   subject = "Scheduler Jobs could not be unscheduled for flow entry "+flowEntry.id+" with triggerId "+triggerId
					   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
					   log.error "ERROR!!: "+subject
				   }
				   
				   return;
			   }
			      
//			   log.error "neighborid:"+flowEntry.neighborsId
			   def neighbor = Neighbor.findById(flowEntry.neighborsId)
			   if(neighbor == null)
			   {
				   subject = "Job "+JobName+" could not be executed since Neighbor Entry based on neighbor id "+flowEntry.neighborsId+" could not be found"
				   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
				   log.error "ERROR!!: "+subject
				   return;
			   }
			   
			   def newFlow = false
			   def flowJson = "";
			   def fileWrite;
			   
			   def neighborJson = Utility.getInstance().buildNeighborJson(neighbor.neighborIp,neighbor.neighborDescription,neighbor.routerId,neighbor.localIp,neighbor.localAS,neighbor.peerAS,neighbor.gracefulRestart,neighbor.md5)
					   
			   def individualFlowsLists = Flow.findAllByNeighbors(neighbor)
			   
			   if (individualFlowsLists) 
			   {
				   individualFlowsLists.each () {
					   def flow = Flow.findById(it.id)
					   if(flow == null)
					   {
						   subject = "Job "+JobName+" could not be executed since Flow Entry based on flow id "+it.id+" could not be found"
						   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
						   log.error "ERROR!!: "+subject
						   return;
					   }
					   def spt = Port.findById(flow.sourceportId)
					   if(spt == null)
					   {
						   subject = "Job "+JobName+" could not be executed since source port based on port id "+flow.sourceportId+" could not be found"
						   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
						   log.error "ERROR!!: "+subject
						   return;
					   }
					   
					   def dpt = Port.findById(flow.destinationportId)
					   if(dpt == null)
					   {
						   subject = "Job "+JobName+" could not be executed since destination port based on port id "+flow.destinationportId+" could not be found"
						   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
						   log.error "ERROR!!: "+subject
						   return;
					   }
					   
					   def prot = Protocol.findById(flow.protocolId)
					   if(prot == null)
					   {
						   subject = "Job "+JobName+" could not be executed since protocol based on protocol id "+flow.protocolId+" could not be found"
						   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
						   log.error "ERROR!!: "+subject
						   return;
					   }
					   
					   def exaAction = Exaaction.findById(flow.exaactionId)
					   if(exaAction == null)
					   {
						   subject = "Job "+JobName+" could not be executed since Action based on Exa Action id "+flow.exaactionId+" could not be found"
						   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
						   log.error "ERROR!!: "+subject
						   return;
					   }
					   
					   if(!((operation == "delete" || operation == "unschedule") && it.id == schedulerEntry.newFlowsId))
					   {
						   if(flow.status.equalsIgnoreCase("done") )
						   {
							   flowJson = flowJson + Utility.getInstance().buildFlowJson(flow.sourceSubnet, flow.destinationSubnet, spt.portRule, dpt.portRule, prot.protocolRule, exaAction.actionName, exaAction.actionParameter)
						   }
						   else
						   {
							   def schedule = Scheduler.findByNewFlows(flow)
							   if(schedule != null && ((schedule.scheduleOption == 'Execute Now') || (schedule.scheduleOption == 'Schedule Once' && schedule.scheduleTime <= (new Date())) ||(it.id == schedulerEntry.newFlowsId && schedule.scheduleOption == 'Schedule Periodically')))
							   {
								   flowJson = flowJson + Utility.getInstance().buildFlowJson(flow.sourceSubnet, flow.destinationSubnet, spt.portRule, dpt.portRule, prot.protocolRule, exaAction.actionName, exaAction.actionParameter)
								   newFlow = true
								   flow.status = "done"
							   }
						   }
					   }
				   };
			   }
			   
			   if(operation != "delete" && operation!= "unschedule" && !newFlow)
			   {
				   Integer status =  BGPProcessExists(neighbor.neighborDescription)
				   if(status == -1)
				   {
					   subject = "Job "+JobName+" could not be executed since ExaBGP Process status command failed for neigbhor " + neighbor.neighborDescription
					   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
					   log.error "ERROR!!: "+subject
					   return;
				   }
				   else if(status == 0)
				   {
					   log.error "Process do not exist"
					   newFlow = true
				   }
			   }
			   
			   if(flowJson == "" && (operation == "delete" || operation == "unschedule"))
			   {
				   def successKillOldBGPProcess  = killOldBGPProcess(neighbor.neighborDescription)
				   if(successKillOldBGPProcess)
				   {
					   try{
						   
						   //delete the scheduler entry
						   if(operation == "delete")
						   {
							   subject = "Scheduler entry "+schedulerEntry.id+" could not be deleted"
							   schedulerEntry.delete flush:true
						   }
						   subject = "Flow entry "+flowEntry.id+" status could not be updated to new"
						   //update the flow from done to new
						   flowEntry.status = 'new'
						   flowEntry.save flush:true
					   }
					   catch(Exception e)
					   {
						   
						   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
						   log.error "ERROR!!: "+subject
						   return;
					   }
					   
					   try{
						   
						   //delete the schedule and unschedule jobs for this flow
						   if(operation == "delete")
						   {
								   def success = unscheduleJobs(schedulerEntry.newFlowsId)
								   if(!success)
								   {
									   subject = "Scheduler Jobs could not be unscheduled for flow entry "+flowEntry.id+" with triggerId "+triggerId
									   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
									   log.error "ERROR!!: "+subject
									   return;
								   }

						   }
					   }
					   catch(Exception e)
					   {
						   log.error "ERROR occured while sending notification regarding flow rule deletion"
						   return;
					   }
					   
					   try
					   {
						   subject="This email is to notify that exabgp process for neighbor "+ neighbor.neighborDescription +" is killed since no other flows exist for the neighbor"
						   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
					   }
					   catch(Exception e)
					   {
						   log.error "ERROR occured while sending notification regarding flow rule deletion"
						   return;
					   }
				   }
				   else
				   {
					   log.error "Below email is being sent to "+schedulerEntry.email+"\n\n"+subject
					   subject = "Job "+JobName+" could not be executed since kill command failed for neigbhor " + neighbor.neighborDescription
					   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
					   log.error "ERROR!!: "+subject
					   return;
				   }
			   }
			   else if (newFlow || operation == "delete" || operation == "unschedule") 
			   {
				   fileWrite = new File("./BGP/" + "neigbhor_" + neighbor.neighborDescription + ".conf")
				   def isFileCreated = fileWrite.createNewFile()
				   if (isFileCreated) {
					   fileWrite.append(neighborJson + flowJson + "\t}\n}\n")
				   }
				   else {
					   fileWrite.delete()
					   fileWrite = new File("./BGP/" + "neigbhor_" + neighbor.neighborDescription + ".conf")
					   fileWrite.append(neighborJson + flowJson + "\t}\n}\n")
				   }
				   
				   def successKillOldBGPProcess = killOldBGPProcess(neighbor.neighborDescription)
				   if(successKillOldBGPProcess) 
				   {
					   def successExecuteExabgp = executeExabgp(neighbor,exabgpPath)
					   
					   if(successExecuteExabgp)
					   {					   
						   try{
							  
							   //delete the scheduler entry
							   if(operation == "delete")
							   {
								   schedulerEntry.delete flush:true
								   //update the flow from done to new
								   flowEntry.status = 'new'
								   flowEntry.save flush:true
							   }
							   else if(operation == "unschedule")
							   {
								   //update the flow from done to new
								   flowEntry.status = 'new'
								   flowEntry.save flush:true
							   }
							   else
							   {
								   //update the flow from new to done
								   flowEntry.status = 'done'
								   flowEntry.save flush:true
							   }
						   }
						   catch(Exception e)
						   {
							   subject = "Job "+JobName+" could not be executed since Scheduler entry "+schedulerEntry.id+" or flow entry "+flowEntry.id+" could not be deleted"
							   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
							   log.error "ERROR!!: "+subject
							   return;
						   }
						   
						  try{
							   //delete the schedule and unschedule jobs for this flow
							   if(operation == "delete")
							   {
									   def success = unscheduleJobs(schedulerEntry.newFlowsId)
									   if(!success)
									   {
										   subject = "Scheduler Jobs could not be unscheduled for flow entry "+flowEntry.id+" with triggerId "+triggerId
										   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
										   log.error "ERROR!!: "+subject
										   return;
									   }
	
							   }
						   }
						   catch(Exception e)
						   {
							   log.error "ERROR occured while sending notification regarding flow rule deletion"
							   return;
						   }
						   
						   try
						   {
							   log.error "Below email is being sent to "+schedulerEntry.email+"\n\n"+subject
							   subject = "This email is to notify that scheduled Flow rules are executed through ExaBGP\nFind the configuration file pushed to the router below\n\n"+neighborJson + flowJson + "\t}\n}\n"
							   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
						   }
						   catch(Exception e)
						   {
							   log.error "ERROR occured while sending notification regarding flow rule addition/update"
							   return;
						   }
					   }
					   else
					   {
						   subject = "Job "+JobName+" could not be executed since exabgp command failed for neigbhor " + neighbor.neighborDescription
						   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
						   log.error "ERROR!!: "+subject
						   return;
					   }
				   }
				   else
				   {
					   subject = "Job "+JobName+" could not be executed since kill command failed for neigbhor " + neighbor.neighborDescription
					   SendMailControllerObj.sendEmail(schedulerEntry.name,schedulerEntry.email, subject)
					   log.error "ERROR!!: "+subject
					   return;
				   }
			   }
		   }
			
}