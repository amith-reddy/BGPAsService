package bgpasservice
import common.Utility

class Neighbor {
	
	String neighborIp
	String neighborDescription
	String routerId = "10.22.6.12"
	String localIp  = "10.22.6.12"
	Long localAS	= 16815;
	Long peerAS	= 16815;
	Integer gracefulRestart = 5;
	String md5 = "flowspec";
	
	String toString(){
		return "${neighborDescription}"
	}
	
	static mapping = {
		routerId defaultValue: "'10.22.6.12'"
		localIp defaultValue: "'10.22.6.12'"
		localAS defaultValue: 16815
		peerAS defaultValue: 16815
		gracefulRestart defaultValue: 5
		md5 defaultValue: "'flowspec'"
	}
	
	static hasMany = [neighborflow:Flow]
	
    static constraints = {
		neighborDescription(blank:false,nullable:false,unique:true,maxSize:40)
		neighborIp(blank:false,nullable:false, unique:true, maxSize:15, validator:{val, obj ->
					 if(!(Utility.getInstance().validateIp(val))) {return 'invalid.ip'}
					})
		routerId(blank:false,nullable:false, maxSize:15, validator:{val, obj ->
					 if(!(Utility.getInstance().validateIp(val))) {return 'invalid.ip'}
					})
		localIp(blank:false,nullable:false, maxSize:15, validator:{val, obj ->
					 if(!(Utility.getInstance().validateIp(val))) {return 'invalid.ip'}
					})
		localAS(blank:false,nullable:false,min:1L, max:4294967296L)
		peerAS(blank:false,nullable:false,min:1L, max:4294967296L)
		gracefulRestart(blank:false,nullable:false,min:1, max:3600)
		md5(blank:false,nullable:false,maxSize:40)
    }
}
