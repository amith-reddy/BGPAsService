
<%@ page import="bgpasservice.Exaaction" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'exaaction.label', default: 'Exaaction')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-exaaction" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
		<div id="list-exaaction" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="actionName" title="${message(code: 'exaaction.actionName.label', default: 'Action Name')}" />
					
						<g:sortableColumn property="actionParameter" title="${message(code: 'exaaction.actionParameter.label', default: 'Action Parameter')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${exaactionInstanceList}" status="i" var="exaactionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${exaactionInstance.id}">${fieldValue(bean: exaactionInstance, field: "actionName")}</g:link></td>
					
						<td>${fieldValue(bean: exaactionInstance, field: "actionParameter")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${exaactionInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
