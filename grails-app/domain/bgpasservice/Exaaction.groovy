package bgpasservice
import common.Utility

class Exaaction {

	String actionName
	String actionParameter
	
	String toString(){
		if("${actionName}" == 'discard'){
			return "${actionName}"
		}
		else{
		return "${actionName}:${actionParameter}"
		}
	}
	
	static hasMany = [actionFlow:Flow]
	static mapping = {
		actionParameter defaultValue: ""
	}
	
    static constraints = {
		actionName(blank:false,nullable:false,inList:["discard","rate-limit","redirect"])
		actionParameter(blank:true,nullable:true,unique:['actionName'],validator:{val,obj ->
						if(obj.actionName == 'discard' && val != 'null'){ return 'invalid.discard.value'}
						else if(obj.actionName == 'rate-limit')
						{
							if(!Utility.getInstance().validateNum(val,0,4294967296))  {return 'invalid.ratelimit.range'}

						}
						else if(obj.actionName == 'redirect')
						{
							if(!(Utility.getInstance().validateIp(val) || Utility.getInstance().validateIpAndPort(val) || Utility.getInstance().validateASAndPort(val)) )  {return 'invalid.redirect.format'}
						}
		})
		
    }
}
