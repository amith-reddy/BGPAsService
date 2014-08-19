
<%@ page import="bgpasservice.Exaaction" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'exaaction.label', default: 'Exaaction')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<script src="${resource(dir:'js' , file:'Common.js')}"></script>
	</head>
	<body>
		<a href="#show-exaaction" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
		<div id="show-exaaction" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			<script> popupclose(); </script>
			</g:if>
			<ol class="property-list exaaction">
			
				<g:if test="${exaactionInstance?.actionName}">
				<li class="fieldcontain">
					<span id="actionName-label" class="property-label"><g:message code="exaaction.actionName.label" default="Action Name" /></span>
					
						<span class="property-value" aria-labelledby="actionName-label"><g:fieldValue bean="${exaactionInstance}" field="actionName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${exaactionInstance?.actionParameter}">
				<li class="fieldcontain">
					<span id="actionParameter-label" class="property-label"><g:message code="exaaction.actionParameter.label" default="Action Parameter" /></span>
					
						<span class="property-value" aria-labelledby="actionParameter-label"><g:fieldValue bean="${exaactionInstance}" field="actionParameter"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${exaactionInstance?.actionFlow}">
				<li class="fieldcontain">
					<span id="actionFlow-label" class="property-label"><g:message code="exaaction.actionFlow.label" default="Action Flow" /></span>
					
						<g:each in="${exaactionInstance.actionFlow}" var="a">
						<span class="property-value" aria-labelledby="actionFlow-label"><g:link controller="flow" action="show" id="${a.id}">${a?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:exaactionInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${exaactionInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
