package bgpasservice
import common.Utility

import org.apache.log4j.*

class Port {
	
	String portRule
	
	static mappedBy = [sport:'sourceport', dport:'destinationport']
	static hasMany = [sport:Flow,dport:Flow]
	
	String toString(){
		def portString = ""
		if("${portRule}" == '' || "${portRule}" == null)
		{
			return "${portRule}"
		}
		
		if("${portRule}" == 'any')
		{
			return "${portRule}"
		}
		def portRules = "${portRule}".split(" ")

		for (int i=1;i<=portRules.length;i++)
		{
		
			   def tmpPortRule = portRules[i-1].split('&');
			def tmpPortOption = "",tmpPortStart = "", tmpPortEnd = "";
	
			if(tmpPortRule.length == 1)
			{
				def tmpPortStartArr = tmpPortRule[0].split('=');
				if(tmpPortStartArr.length == 2)
				{
					if(portString != "")
						portString = portString.concat(' OR ');
						
					portString = portString.concat(tmpPortStartArr[1]);
				}
			}
			else if(tmpPortRule.length == 2)
			{
				tmpPortOption = 'range';
				def tmpPortStartArr = tmpPortRule[0].split('>=');
				if(tmpPortStartArr.length == 2)
				{
					tmpPortStart = tmpPortStartArr[1];
					def tmpPortEndArr = tmpPortRule[1].split('<=');
					if(tmpPortEndArr.length == 2)
					{
						tmpPortEnd = tmpPortEndArr[1];
					
						if(portString != "")
							portString = portString.concat(' OR ');
							
						portString = portString.concat("Between ").concat(tmpPortStart).concat(" and ").concat(tmpPortEnd);
					}
				}
			}
		}
		return portString;
		//return "${portRule}"
	}
	
    static constraints = {
		portRule(blank:false, nullable:false,unique:true, maxSize:80,validator:{val,obj ->
					if(val == "any") {return true}
					def portrules = val.split(" ",-1)
					def map = [:]
					
					if(portrules.length > 5) {return false}
					else{
						for(int i=0;i<portrules.length;i++)
						{
							if(portrules[i] == null || portrules[i] == "") {return false}
							if(map.containsKey((portrules[i]))) {return false}
							else {
								map.put(portrules[i],1)
							}
							def subportrules = portrules[i].split("&",-1)
							
							if(subportrules.length > 2) 
							{
								return false
							}
							else if(subportrules.length == 1)
							{
								if(!Utility.getInstance().validateoperatorAndPort(subportrules[0], "="))
								{ return false}
							}
							else if(subportrules.length == 2){
								def port1 = Utility.getInstance().validateoperatorAndPort(subportrules[0], ">=")
								def port2 = Utility.getInstance().validateoperatorAndPort(subportrules[1], "<=")
								if( (port1 == 0	|| port2 == 0 || (port1.toInteger() > port2.toInteger())) )
								{ return false}
							}
						}
					}
			})
    }
}
