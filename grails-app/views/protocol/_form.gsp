<%@ page import="bgpasservice.Protocol" %>


<div class="fieldcontain  required">
		<label for="protocolRule">
			<g:message code="protocol.protocolrule.label" default="Protocol Rule" />
			<span class="required-indicator">*</span>
		</label>
		<g:each var="prot" status="i" in="${grailsApplication.config.grails.protocollist}">
			<p id="alignright"><g:checkBox id="check${i}" name="protocol" class="Checkbox" value="${prot}" checked="${protocolInstance?.protocolRule?.contains(prot)}" />&nbsp;${prot}</p>
		</g:each>
		<g:hiddenField type="text" name="protocolRule" value="" id="protocolRule"/>
</div>



