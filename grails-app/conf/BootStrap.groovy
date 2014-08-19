import bgpasservice.Exaaction
import bgpasservice.Flow
import bgpasservice.Neighbor
import bgpasservice.Port
import bgpasservice.Protocol
import grails.util.Environment;
import common.ScheduleFlowHelper;

class BootStrap {

    def init = { servletContext ->
		ScheduleFlowHelper.getInstance().startup()
		def port_1 = new Port(
			portRule:"any")
			port_1.save()
			if(port_1.hasErrors()){
				println port_1.errors
			}
			
//		switch(Environment.getCurrent()){
//			case "DEVELOPMENT":
//				//ScheduleFlowHelper.getInstance().startup()
//				
//				

//				def port_2 = new Port(
//						portRule:">=1024&<=1026")
//				port_2.save()
//				if(port_2.hasErrors()){
//					println port_2.errors
//				}
//
//				def port_3 = new Port(
//						portRule:">=8079&<=8089")
//				port_3.save()
//				if(port_3.hasErrors()){
//					println port_3.errors
//				}
//
//				def port_4 = new Port(
//						portRule:"=80 =8080"
//						)
//				port_4.save()
//				if(port_4.hasErrors()){
//					println port_4.errors
//				}
//				def port_5 = new Port(
//                                                portRule:"=5001"
//                                                )
//                                port_5.save()
//                                if(port_5.hasErrors()){
//                                        println port_5.errors
//                                }
//
//				def action_1 = new Exaaction(
//						actionName:"discard",
//						actionParameter:"null"
//						)
//				action_1.save()
//				if(action_1.hasErrors()){
//					println action_1.errors
//				}
//				
//
//				def action_2 = new Exaaction(
//						actionName:"rate-limit",
//						actionParameter:"10000000"
//						)
//				action_2.save()
//				if(action_2.hasErrors()){
//					println action_2.errors
//				}
//
//				def action_3 = new Exaaction(
//						actionName:"redirect",
//						actionParameter:"65500:12345"
//						)
//				action_3.save()
//				if(action_3.hasErrors()){
//					println action_3.errors
//				}
//
//				def neighborVar = new Neighbor(
//						neighborIp:"173.199.62.2",
//						neighborDescription:"edge1.sjc-lab"
//						)
//				neighborVar.save()
//				if(neighborVar.hasErrors()){
//					println neighborVar.errors
//				}
//
//				def neighbor2 = new Neighbor(
//						neighborIp:"173.199.62.4",
//						neighborDescription:"edge1.las-lab"
//						)
//				neighbor2.save()
//				if(neighbor2.hasErrors()){
//					println neighbor2.errors
//				}
//				
//				def protocol_1 = new Protocol(
//					protocolRule:"tcp"
//					)
//			protocol_1.save()
//			if(protocol_1.hasErrors()){
//				println protocol_1.errors
//			}
//				
//				def flow1 = new Flow(
//					neighbors:neighborVar.id,
//					sourceSubnet:"208.209.211.0/24",
//					destinationSubnet:"10.17.6.0/24",
//					protocol:protocol_1.id,
//					exaaction:action_1.id,
//					sourceport:port_1.id,
//					destinationport:port_1.id,
//					dateCreated:new Date(),
//					status:"new"
//					)
//				flow1.save()
//				if(flow1.hasErrors()){
//					println flow1.errors
//				}
//				
//				def flow2 = new Flow(
//					neighbors:neighbor2.id,
//					sourceSubnet:"21.21.21.21/32",
//					destinationSubnet:"216.115.219.0/24",
//					protocol:protocol_1.id,
//					exaaction:action_2.id,
//					sourceport:port_1.id,
//					destinationport:port_3.id,
//					dateCreated:new Date(),
//					status:"new"
//					)
//				flow2.save()
//				if(flow2.hasErrors()){
//					println flow2.errors
//				}
//				
//				def flow3 = new Flow(
//					neighbors:neighborVar.id,
//					sourceSubnet:"99.99.99.0/24",
//					destinationSubnet:"100.100.100.0/24",
//					protocol:protocol_1.id,
//					exaaction:action_1.id,
//					sourceport:port_2.id,
//					destinationport:port_1.id,
//					status:"new"
//					)
//				//flow3.save()
//				if(flow3.hasErrors()){
//					println flow3.errors
//				}
//		}
	}
    def destroy = {
		ScheduleFlowHelper.getInstance().shutdown()
    }
}
