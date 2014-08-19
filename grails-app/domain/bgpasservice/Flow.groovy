package bgpasservice
import common.Utility

class Flow {

	String sourceSubnet
	String destinationSubnet
	String status
	String comment
	Date dateCreated
	

	String toString(){	
		return "Flow:${sourceSubnet}:${destinationSubnet}"
	}
	
	static belongsTo = [neighbors:Neighbor, sourceport:Port, destinationport:Port, exaaction:Exaaction, protocol:Protocol]
	
	static constraints = {
		neighbors(unique:['sourceSubnet','sourceSubnet','destinationSubnet','protocol','exaaction','sourceport','destinationport'])
		sourceSubnet(blank:false,nullable:false,maxSize:20,validator:{val,obj ->
				if(! Utility.getInstance().validateSubnetMask(val)) {return 'invalid.ipsubnet'}
			})
		destinationSubnet(blank:false,nullable:false,maxSize:20,validator:{val,obj ->
				if(! Utility.getInstance().validateSubnetMask(val)) {return 'invalid.ipsubnet'}
			})
		protocol()
		exaaction()
		sourceport()
		destinationport()
		status(blank:false,nullable:false,inList: ["new","done"])
		comment(blank:true,nullable:true)
		dateCreated()
	}
	
}
