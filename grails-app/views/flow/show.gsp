
<%@ page import="bgpasservice.Flow" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'flow.label', default: 'Flow')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-flow" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
		<div id="show-flow" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list flow">
			
				<g:if test="${flowInstance?.neighbors}">
				<li class="fieldcontain">
					<span id="neighbors-label" class="property-label"><g:message code="flow.neighbors.label" default="Neighbors" /></span>
					
						<span class="property-value" aria-labelledby="neighbors-label"><g:link controller="neighbor" action="show" id="${flowInstance?.neighbors?.id}">${flowInstance?.neighbors?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${flowInstance?.sourceSubnet}">
				<li class="fieldcontain">
					<span id="sourceSubnet-label" class="property-label"><g:message code="flow.sourceSubnet.label" default="Source Subnet" /></span>
					
						<span class="property-value" aria-labelledby="sourceSubnet-label"><g:fieldValue bean="${flowInstance}" field="sourceSubnet"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${flowInstance?.destinationSubnet}">
				<li class="fieldcontain">
					<span id="destinationSubnet-label" class="property-label"><g:message code="flow.destinationSubnet.label" default="Destination Subnet" /></span>
					
						<span class="property-value" aria-labelledby="destinationSubnet-label"><g:fieldValue bean="${flowInstance}" field="destinationSubnet"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${flowInstance?.protocol}">
				<li class="fieldcontain">
					<span id="protocol-label" class="property-label"><g:message code="flow.protocol.label" default="Protocol" /></span>
					
						<span class="property-value" aria-labelledby="protocol-label"><g:link controller="protocol" action="show" id="${flowInstance?.protocol?.id}">${flowInstance?.protocol?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${flowInstance?.exaaction}">
				<li class="fieldcontain">
					<span id="exaaction-label" class="property-label"><g:message code="flow.exaaction.label" default="Exaaction" /></span>
					
						<span class="property-value" aria-labelledby="exaaction-label"><g:link controller="exaaction" action="show" id="${flowInstance?.exaaction?.id}">${flowInstance?.exaaction?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${flowInstance?.sourceport}">
				<li class="fieldcontain">
					<span id="sourceport-label" class="property-label"><g:message code="flow.sourceport.label" default="Sourceport" /></span>
					
						<span class="property-value" aria-labelledby="sourceport-label"><g:link controller="port" action="show" id="${flowInstance?.sourceport?.id}">${flowInstance?.sourceport?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${flowInstance?.destinationport}">
				<li class="fieldcontain">
					<span id="destinationport-label" class="property-label"><g:message code="flow.destinationport.label" default="Destinationport" /></span>
					
						<span class="property-value" aria-labelledby="destinationport-label"><g:link controller="port" action="show" id="${flowInstance?.destinationport?.id}">${flowInstance?.destinationport?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${flowInstance?.status}">
				<li class="fieldcontain">
					<span id="status-label" class="property-label"><g:message code="flow.status.label" default="Status" /></span>
					
						<span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${flowInstance}" field="status"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${flowInstance?.comment}">
				<li class="fieldcontain">
					<span id="comment-label" class="property-label"><g:message code="flow.comment.label" default="Comment" /></span>
					
						<span class="property-value" aria-labelledby="comment-label"><g:fieldValue bean="${flowInstance}" field="comment"/></span>
					
				</li>
				</g:if>
				
				<g:if test="${flowInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="flow.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${flowInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:flowInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:if test="${flowInstance?.status != 'done'}">
						<g:link class="edit" action="edit" resource="${flowInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
						<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.newflow.confirm.message', default: 'Scheduler Entry configured for this flow will also be deleted. Are you sure?')}');" />
					</g:if>
					<g:else>
						<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
					</g:else>
					<g:link class="add" action="copy" resource="${flowInstance}"><g:message code="default.button.copy.label" default="Copy" /></g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
