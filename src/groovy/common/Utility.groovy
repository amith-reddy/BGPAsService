package common
import org.codehaus.groovy.grails.validation.routines.InetAddressValidator

class Utility {
	private static Utility utilObject = new Utility();
	private Utility()
	{
		
	}
 
	public static Utility  getInstance() {
		return utilObject;
	}
	
	def validateIp(input)
	{
		if(input == null) {return false}
		
		return (InetAddressValidator.getInstance().isValidInet4Address(input))
	}
	
	def validateIpAndPort(input)
	{
		if(input == null) {return false}
		
		def parts = input.split(":",-1);
		if(parts.length == 2)
		{
			return validateNum(parts[1], 0, 65535) &&
				(InetAddressValidator.getInstance().isValidInet4Address(parts[0]))
		}
		else
		{
			return false
		}
	}
	
	def validateASAndPort(input)
	{
		if(input == null) {return false}
		
		def parts = input.split(":",-1);
		if(parts.length == 2)
		{
			return validateNum(parts[1], 0, 65535) &&
				validateNum(parts[0], 0, 65535)
		}
		else
		{
			return false
		}
	}
	
	def validateoperatorAndPort(input, operator)
	{
		if(input == null || operator == null) {return false}
		if(input == "" || operator == "") {return 0}
		
		def finalportvalue = input.split(operator)
		if(finalportvalue.length == 2)
		{
			if((finalportvalue[0] == null || finalportvalue[0] == "") && validateNum(finalportvalue[1],0,65535))
				{return finalportvalue[1]}
			else
				{return 0}
		}
		else
		{
			return 0
		}
	}
	
	def validateSubnetMask(input)
	{
		if(input == null || input == "") {return false}
				
		def subnet = input.split("/",-1)
		if(subnet.length == 2)
		{
			return InetAddressValidator.getInstance().isValidInet4Address(subnet[0]) && 
					validateNum(subnet[1], 0, 32) 
				
		}
		else
		{
			return false
		}
	}
	
	def validateNum(input, min,max)
	{
		if(input == null) {return false}
		
		if(input?.isBigInteger())
		{
			if(input.toBigInteger() >= min &&  input.toBigInteger() <= max)
			{return true}
		}
		else
		{
			return false
		}
	}
	
	String buildNeighborJson(String neighborIP, String neighborDescription, String routerID, String localIP, long localAS, long peerAS, int gracefulRestart, String md5) {
		String neighborJson = "neighbor " + neighborIP + " {\n" +
				"\tdescription \"" + neighborDescription + "\";\n" +
				"\trouter-id " + routerID + ";\n" +
				"\tlocal-address " + localIP + ";\n" +
				"\tlocal-as " + localAS + ";\n" +
				"\tpeer-as " + peerAS + ";\n" +
				"\tgraceful-restart " + gracefulRestart + ";\n" +
				"\tmd5 \"" + md5 + "\";\n\n"+
				"\tflow {\n"
		return neighborJson
	}

	String buildFlowJson(String sourceSubnet, String destinationSubnet, String sourcePort, String destinationPort, String protocol, String exaAction, String exaActionParam = null) {
		def routeID = new Date()
		String flowJson = "\t\troute " +  routeID.getTime().toString() + " {\n" +
				"\t\t\tmatch {\n" +
				"\t\t\t\tsource " + sourceSubnet + ";\n" +
				"\t\t\t\tdestination " + destinationSubnet + ";\n";

		if (!sourcePort.equalsIgnoreCase("any")) {
			flowJson = flowJson + "\t\t\t\tsource-port " + sourcePort + ";\n"
		}
		else if (!destinationPort.equalsIgnoreCase("any")) {
			flowJson = flowJson + "\t\t\t\tdestination-port " + destinationPort + ";\n"
		}
		
		def protocollist = protocol.split(" ",-1)
		if(protocollist.length > 1)
		{
			flowJson = flowJson + "\t\t\t\tprotocol  [ " + protocol + " ];\n"
		}
		else
		{
			flowJson = flowJson + "\t\t\t\tprotocol  " + protocol + ";\n"
		}

		flowJson = flowJson + "\t\t\t}\n\t\t\tthen {\n"

		if (exaActionParam == null || exaActionParam == 'null') {
			flowJson = flowJson + "\t\t\t\t" + exaAction + ";\n"
		}
		else {
			flowJson = flowJson + "\t\t\t\t" + exaAction + " " + exaActionParam + ";\n"
		}
		flowJson = flowJson + "\t\t\t}\n\t\t}\n"
		
		return flowJson
	}
	
	
}
