
<%@ page import="bgpasservice.Scheduler" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'scheduler.label', default: 'Scheduler')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'jqCron.css')}"/>
		<script src="${resource(dir: 'js', file: 'jqCron.js')}"></script>
		<script src="${resource(dir: 'js', file: 'jqCron.en.js')}"></script>
		<script src="${resource(dir: 'js', file: 'Scheduler.js')}"></script>
	</head>
	<body>
		<a href="#show-scheduler" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
		<div id="show-scheduler" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list scheduler">
			
				<g:if test="${schedulerInstance?.newFlows}">
				<li class="fieldcontain">
					<span id="newFlows-label" class="property-label"><g:message code="scheduler.newFlows.label" default="New Flows" /></span>
					
						<span class="property-value" aria-labelledby="newFlows-label"><g:link controller="flow" action="show" id="${schedulerInstance?.newFlows?.id}">${schedulerInstance?.newFlows?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${schedulerInstance?.scheduleOption}">
				<li class="fieldcontain">
					<span id="scheduleOption-label" class="property-label"><g:message code="scheduler.scheduleOption.label" default="Schedule Option" /></span>
					
						<span class="property-value" aria-labelledby="scheduleOption-label"><g:fieldValue bean="${schedulerInstance}" field="scheduleOption"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${schedulerInstance?.scheduleOption == 'Schedule Once'}">
					<g:if test="${schedulerInstance?.scheduleTime}">
					<li class="fieldcontain">
						<span id="scheduleTime-label" class="property-label"><g:message code="scheduler.scheduleTime.label" default="Schedule Time" /></span>
						
							<span class="property-value" aria-labelledby="scheduleTime-label"><g:formatDate date="${schedulerInstance?.scheduleTime}" /></span>
						
					</li>
					</g:if>
				</g:if>
			
				<g:if test="${schedulerInstance?.scheduleOption == 'Schedule Periodically'}">
					<g:if test="${schedulerInstance?.activationSchedule}">
					<li class="fieldcontain">
						<span id="activationSchedule-label" class="property-label"><g:message code="scheduler.activationSchedule.label" default="Activation Schedule" /></span>
						
							<span class="property-value" aria-labelledby="activationSchedule-label"><g:hiddenField name="activationSchedule" class="cron" value="${schedulerInstance?.activationSchedule}" /></span>
						
					</li>
					</g:if>
				
					<g:if test="${schedulerInstance?.deactivationSchedule}">
					<li class="fieldcontain">
						<span id="deactivationSchedule-label" class="property-label"><g:message code="scheduler.deactivationSchedule.label" default="Deactivation Schedule" /></span>
	
							<span class="property-value" aria-labelledby="deactivationSchedule-label"><g:hiddenField name="deactivationSchedule" class="cron" value="${schedulerInstance?.deactivationSchedule}" /></span>
						
					</li>
					</g:if>
				</g:if>
			
				<g:if test="${schedulerInstance?.deleteScheduleOption}">
				<li class="fieldcontain">
					<span id="deleteScheduleOption-label" class="property-label"><g:message code="scheduler.deleteScheduleOption.label" default="Delete Option" /></span>
					
						<span class="property-value" aria-labelledby="deleteScheduleOption-label"><g:fieldValue bean="${schedulerInstance}" field="deleteScheduleOption"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${schedulerInstance?.deleteScheduleOption == 'Delete Later'}">
					<g:if test="${schedulerInstance?.deleteScheduleTime}">
					<li class="fieldcontain">
						<span id="deleteScheduleTime-label" class="property-label"><g:message code="scheduler.deleteScheduleTime.label" default="Deletion Time" /></span>
						
							<span class="property-value" aria-labelledby="deleteScheduleTime-label"><g:formatDate date="${schedulerInstance?.deleteScheduleTime}" /></span>
						
					</li>
					</g:if>
				</g:if>
				
				<g:if test="${schedulerInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="scheduler.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${schedulerInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${schedulerInstance?.email}">
				<li class="fieldcontain">
					<span id="email-label" class="property-label"><g:message code="scheduler.email.label" default="Email" /></span>
					
						<span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${schedulerInstance}" field="email"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:schedulerInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${schedulerInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
