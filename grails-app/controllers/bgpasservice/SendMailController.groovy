package bgpasservice

import org.quartz.Scheduler;

import common.ScheduleFlowHelper;

class SendMailController {
	   def grailsApplication
//	    private static SendMailController mailObject = new SendMailController();
//	    
//		private SendMailController()
//		{
//
//		}
//	 
//	    public static SendMailController  getInstance() {
//			if(!mailObject)
//			{
//				mailObject = new SendMailController();
//			}
//			return mailObject;
//	    }
	
       def sendEmail(String name,String email,String mailsubject)
	   {

		   if(email != null || grailsApplication.config.grails.emailList.size() > 0)
		   {
			   def finalemaillist = grailsApplication.config.grails.emailList.collect()
			   
			   if(email != null)
			   	finalemaillist.add(0,email);
			   
			   try
			   {
				   
				   sendMail {
					   to finalemaillist.toArray()
					   from grailsApplication.config.grails.mail.username
					   subject "ExaBGP Notification"
					   body "Hello "+name+"\n\n"+ mailsubject +" \n\nRegards,\nExaBGP Team"
					}
			   }
			   catch(Exception e)
			   {
				   e.printStackTrace()
				   println "ERROR!!: Unable to send the notification to email "+ finalemaillist.toString()
			   }
		   }
	   }
}
