<%@ page import="bgpasservice.Neighbor" %>



<div class="fieldcontain ${hasErrors(bean: neighborInstance, field: 'neighborDescription', 'error')} required">
	<label for="neighborDescription">
		<g:message code="neighbor.neighborDescription.label" default="Neighbor Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="neighborDescription" maxlength="40" required="" value="${neighborInstance?.neighborDescription}"/>
	<div class="tip" id="neighborDescriptionTip">Enter any Neighbor Description. Example:edge1.sjc-lab</div>

</div>

<div class="fieldcontain ${hasErrors(bean: neighborInstance, field: 'neighborIp', 'error')} required">
	<label for="neighborIp">
		<g:message code="neighbor.neighborIp.label" default="Neighbor Ip" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="neighborIp" maxlength="15" required="" value="${neighborInstance?.neighborIp}"/>
	<div class="tip" id="neighborIpTip">Enter a Valid IP. Example:173.199.62.2</div>
</div>

<div class="fieldcontain ${hasErrors(bean: neighborInstance, field: 'routerId', 'error')} required">
	<label for="routerId">
		<g:message code="neighbor.routerId.label" default="Router Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="routerId" maxlength="15" required="" value="${neighborInstance?.routerId}"/>
	<div class="tip" id="routerIdTip">Enter a Valid IP. Example:10.22.6.12</div>

</div>

<div class="fieldcontain ${hasErrors(bean: neighborInstance, field: 'localIp', 'error')} required">
	<label for="localIp">
		<g:message code="neighbor.localIp.label" default="Local Ip" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="localIp" maxlength="15" required="" value="${neighborInstance?.localIp}"/>
	<div class="tip" id="localIpTip">Enter a Valid IP. Example:10.22.6.12</div>

</div>

<div class="fieldcontain ${hasErrors(bean: neighborInstance, field: 'localAS', 'error')} required">
	<label for="localAS">
		<g:message code="neighbor.localAS.label" default="Local AS" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="localAS" title="Enter a Valid AS between 0 to 65535. Example:16815" type="number" min = "${grailsApplication.config.grails.asnumber.minvalue}" max="${grailsApplication.config.grails.asnumber.maxvalue}" value="${neighborInstance.localAS}" required=""/>
	<div class="tip" id="localASTip">Enter a Valid AS between 0 to 65535. Example:16815</div>

</div>

<div class="fieldcontain ${hasErrors(bean: neighborInstance, field: 'peerAS', 'error')} required">
	<label for="peerAS">
		<g:message code="neighbor.peerAS.label" default="Peer AS" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="peerAS" title="Enter a Valid AS between 0 to 65535. Example:16815" type="number" min = "${grailsApplication.config.grails.asnumber.minvalue}" max="${grailsApplication.config.grails.asnumber.maxvalue}" value="${neighborInstance.peerAS}" required=""/>
	<div class="tip" id="peerASTip">Enter a Valid AS between 0 to 65535. Example:16815</div>

</div>

<div class="fieldcontain ${hasErrors(bean: neighborInstance, field: 'gracefulRestart', 'error')} required">
	<label for="gracefulRestart">
		<g:message code="neighbor.gracefulRestart.label" default="Graceful Restart" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="gracefulRestart" title="Restart time in seconds. Example:5" type="number" min = "${grailsApplication.config.grails.gracefulRestart.minvalue}" max="${grailsApplication.config.grails.gracefulRestart.maxvalue}" value="${neighborInstance.gracefulRestart}" required=""/>
	<div class="tip" id="gracefulRestartip">Restart time in seconds. Example:5</div>

</div>

<div class="fieldcontain ${hasErrors(bean: neighborInstance, field: 'md5', 'error')} required">
	<label for="md5">
		<g:message code="neighbor.md5.label" default="Md5" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="md5" maxlength="40" required="" value="${neighborInstance?.md5}"/>
	<div class="tip" id="md5Tip">Enter any string. Example:flowspec</div>
</div>

