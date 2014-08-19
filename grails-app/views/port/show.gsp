
<%@ page import="bgpasservice.Port" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'port.label', default: 'Port')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<script src="${resource(dir:'js' , file:'Common.js')}"></script>
	</head>
	<body>
		<a href="#show-port" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
		<div id="show-port" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			<script> popupclose(); </script>
			</g:if>
			<ol class="property-list port">
			
				<g:if test="${portInstance?.portRule}">
				<li class="fieldcontain">
					<span id="portRule-label" class="property-label"><g:message code="port.portRule.label" default="Port Rule" /></span>
					
						<span class="property-value" aria-labelledby="portRule-label">${portInstance?.toString()}</span>
					
				</li>
				</g:if>
			
				<g:if test="${portInstance?.dport}">
				<li class="fieldcontain">
					<span id="dport-label" class="property-label"><g:message code="port.dport.label" default="Dport" /></span>
					
						<g:each in="${portInstance.dport}" var="d">
						<span class="property-value" aria-labelledby="dport-label"><g:link controller="flow" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${portInstance?.sport}">
				<li class="fieldcontain">
					<span id="sport-label" class="property-label"><g:message code="port.sport.label" default="Sport" /></span>
					
						<g:each in="${portInstance.sport}" var="s">
						<span class="property-value" aria-labelledby="sport-label"><g:link controller="flow" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:portInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${portInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
