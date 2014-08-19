package bgpasservice

class Protocol {

	String protocolRule
	
	static hasMany = [protocolflow:Flow]
	
	String toString(){
		return "${protocolRule}"
	}
	
	def validateProtocolRule(input)
	{
		def protocols = input.split(" ",-1);
		def validMap = ['tcp':0, 'udp':0, 'icmp':0]
		
		if(protocols.length < 1 && protocols.length > 3)
		{
			return false
		}
		else
		{
			for(int i=0;i<protocols.length;i++)
			{
				if(!(protocols[i] == 'tcp' 
					|| protocols[i] == 'udp'
					|| protocols[i] == 'icmp') )
				{
					return false
				}
				else
				{
					if(validMap[protocols[i]] == 0)
						validMap[protocols[i]] = 1
					else
						return false
				}			
			}
			
			return true
		}
	}
	
    static constraints = {
		protocolRule(blank:false,nullable:false, unique:true, maxSize:40, validator:{val,obj ->
			Protocol protocolObj = new Protocol()
			if(!protocolObj.validateProtocolRule(val))  {return false}
			})
    }
}
