
<%@ page import="bgpasservice.Protocol" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'protocol.label', default: 'Protocol')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<script src="${resource(dir:'js' , file:'Common.js')}"></script>
	</head>
	<body>
		<a href="#show-protocol" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div id='cssmenu'>
		<ul>
		<li class="controller"><a href="${createLink(uri: '/')}" >Home</a></li>
		<g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
			<!--  <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li> -->
			<g:if test = "${c.logicalPropertyName != 'assets' && c.logicalPropertyName != 'dbdoc' && c.logicalPropertyName != 'sendMail' && c.logicalPropertyName != 'login'&& c.logicalPropertyName != 'logout'}">
				<li class="${controllerName == c.logicalPropertyName ? 'active' : ''}"><g:link controller="${c.logicalPropertyName}">${c.logicalPropertyName[0].toUpperCase().concat(c.logicalPropertyName.substring(1))}</g:link></li>
			</g:if>
		</g:each>
		</ul>
		</div>
		<div class="nav" role="navigation">
			<ul>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-protocol" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			<script> popupclose(); </script>
			</g:if>
			<ol class="property-list protocol">
			
				<g:if test="${protocolInstance?.protocolRule}">
				<li class="fieldcontain">
					<span id="protocolRule-label" class="property-label"><g:message code="protocol.protocolRule.label" default="Protocol Rule" /></span>
					
						<span class="property-value" aria-labelledby="protocolRule-label"><g:fieldValue bean="${protocolInstance}" field="protocolRule"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${protocolInstance?.protocolflow}">
				<li class="fieldcontain">
					<span id="protocolflow-label" class="property-label"><g:message code="protocol.protocolflow.label" default="Protocolflow" /></span>
					
						<g:each in="${protocolInstance.protocolflow}" var="p">
						<span class="property-value" aria-labelledby="protocolflow-label"><g:link controller="flow" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:protocolInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${protocolInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
