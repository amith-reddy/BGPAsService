
<%@ page import="bgpasservice.Neighbor" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'neighbor.label', default: 'Neighbor')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<script src="${resource(dir:'js' , file:'Common.js')}"></script>
	</head>
	<body>
		<a href="#show-neighbor" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
		<div id="show-neighbor" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			<script> popupclose(); </script>
			</g:if>
			<ol class="property-list neighbor">
			
				<g:if test="${neighborInstance?.neighborDescription}">
				<li class="fieldcontain">
					<span id="neighborDescription-label" class="property-label"><g:message code="neighbor.neighborDescription.label" default="Neighbor Description" /></span>
					
						<span class="property-value" aria-labelledby="neighborDescription-label"><g:fieldValue bean="${neighborInstance}" field="neighborDescription"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${neighborInstance?.neighborIp}">
				<li class="fieldcontain">
					<span id="neighborIp-label" class="property-label"><g:message code="neighbor.neighborIp.label" default="Neighbor Ip" /></span>
					
						<span class="property-value" aria-labelledby="neighborIp-label"><g:fieldValue bean="${neighborInstance}" field="neighborIp"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${neighborInstance?.routerId}">
				<li class="fieldcontain">
					<span id="routerId-label" class="property-label"><g:message code="neighbor.routerId.label" default="Router Id" /></span>
					
						<span class="property-value" aria-labelledby="routerId-label"><g:fieldValue bean="${neighborInstance}" field="routerId"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${neighborInstance?.localIp}">
				<li class="fieldcontain">
					<span id="localIp-label" class="property-label"><g:message code="neighbor.localIp.label" default="Local Ip" /></span>
					
						<span class="property-value" aria-labelledby="localIp-label"><g:fieldValue bean="${neighborInstance}" field="localIp"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${neighborInstance?.localAS}">
				<li class="fieldcontain">
					<span id="localAS-label" class="property-label"><g:message code="neighbor.localAS.label" default="Local AS" /></span>
					
						<span class="property-value" aria-labelledby="localAS-label">${neighborInstance?.localAS}</span>
					
				</li>
				</g:if>
			
				<g:if test="${neighborInstance?.peerAS}">
				<li class="fieldcontain">
					<span id="peerAS-label" class="property-label"><g:message code="neighbor.peerAS.label" default="Peer AS" /></span>
					
						<span class="property-value" aria-labelledby="peerAS-label">${neighborInstance?.peerAS}</span>
					
				</li>
				</g:if>
			
				<g:if test="${neighborInstance?.gracefulRestart}">
				<li class="fieldcontain">
					<span id="gracefulRestart-label" class="property-label"><g:message code="neighbor.gracefulRestart.label" default="Graceful Restart" /></span>
					
						<span class="property-value" aria-labelledby="gracefulRestart-label"><g:fieldValue bean="${neighborInstance}" field="gracefulRestart"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${neighborInstance?.md5}">
				<li class="fieldcontain">
					<span id="md5-label" class="property-label"><g:message code="neighbor.md5.label" default="Md5" /></span>
					
						<span class="property-value" aria-labelledby="md5-label"><g:fieldValue bean="${neighborInstance}" field="md5"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${neighborInstance?.neighborflow}">
				<li class="fieldcontain">
					<span id="neighborflow-label" class="property-label"><g:message code="neighbor.neighborflow.label" default="Neighborflow" /></span>
					
						<g:each in="${neighborInstance.neighborflow}" var="n">
						<span class="property-value" aria-labelledby="neighborflow-label"><g:link controller="flow" action="show" id="${n.id}">${n?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:neighborInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${neighborInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
