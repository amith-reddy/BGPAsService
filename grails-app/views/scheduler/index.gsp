
<%@ page import="bgpasservice.Scheduler" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'scheduler.label', default: 'Scheduler')}" />
		<link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'jqCron.css')}"/>
		<script src="${resource(dir: 'js', file: 'jqCron.js')}"></script>
		<script src="${resource(dir: 'js', file: 'jqCron.en.js')}"></script>
		<script src="${resource(dir: 'js', file: 'Scheduler.js')}"></script>
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-scheduler" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-scheduler" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<th><g:message code="scheduler.newFlows.label" default="New Flows" /></th>
					
						<g:sortableColumn property="scheduleOption" title="${message(code: 'scheduler.scheduleOption.label', default: 'Schedule Option')}" />
					
						<g:sortableColumn property="scheduleTime" title="${message(code: 'scheduler.scheduleTime.label', default: 'Schedule Time')}" />
					
						<th><g:message code="scheduler.newFlows.label" default="Activation Schedule" /></th>
						
						<th><g:message code="scheduler.newFlows.label" default="Deactivation Schedule" /></th>
											
						<g:sortableColumn property="deleteScheduleOption" title="${message(code: 'scheduler.deleteScheduleOption.label', default: 'Delete Option')}" />
					
						<g:sortableColumn property="deleteScheduleTime" title="${message(code: 'scheduler.deleteScheduleTime.label', default: 'Delete Time')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${schedulerInstanceList}" status="i" var="schedulerInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${schedulerInstance.id}">${fieldValue(bean: schedulerInstance, field: "newFlows")}</g:link></td>
					
						<td>${fieldValue(bean: schedulerInstance, field: "scheduleOption")}</td>
						
						<g:if test="${schedulerInstance.scheduleOption == 'Schedule Once'}">
							<td><g:formatDate date="${schedulerInstance.scheduleTime}" /></td>
						</g:if>
						<g:else>
							<td></td>
						</g:else>
						
						<g:if test="${schedulerInstance.activationSchedule == null}">
							<td><g:hiddenField name="activationSchedule" value="${schedulerInstance?.activationSchedule}" /></td>
					
							<td><g:hiddenField name="deactivationSchedule" value="${schedulerInstance?.deactivationSchedule}" /></td>
						</g:if>
						<g:else>
							<td><g:hiddenField name="activationSchedule" class="cron" value="${schedulerInstance?.activationSchedule}" /></td>
					
							<td ><g:hiddenField name="deactivationSchedule" class="cron" value="${schedulerInstance?.deactivationSchedule}" /></td>
						</g:else>
						
						<td>${fieldValue(bean: schedulerInstance, field: "deleteScheduleOption")}</td>
					
						
						<g:if test="${schedulerInstance.deleteScheduleOption == 'Delete Later'}">
							<td><g:formatDate date="${schedulerInstance.deleteScheduleTime}" /></td>
						</g:if>
						<g:else>
							<td></td>
						</g:else>
					
				
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${schedulerInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
