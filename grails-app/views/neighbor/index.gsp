
<%@ page import="bgpasservice.Neighbor" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'neighbor.label', default: 'Neighbor')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-neighbor" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
		<div id="list-neighbor" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="neighborDescription" title="${message(code: 'neighbor.neighborDescription.label', default: 'Neighbor Description')}" />
					
						<g:sortableColumn property="neighborIp" title="${message(code: 'neighbor.neighborIp.label', default: 'Neighbor Ip')}" />
					
						<g:sortableColumn property="routerId" title="${message(code: 'neighbor.routerId.label', default: 'Router Id')}" />
					
						<g:sortableColumn property="localIp" title="${message(code: 'neighbor.localIp.label', default: 'Local Ip')}" />
					
						<g:sortableColumn property="localAS" title="${message(code: 'neighbor.localAS.label', default: 'Local AS')}" />
					
						<g:sortableColumn property="peerAS" title="${message(code: 'neighbor.peerAS.label', default: 'Peer AS')}" />
						
						<g:sortableColumn property="gracefulRestart" title="${message(code: 'neighbor.gracefulRestart.label', default: 'Graceful Restart')}" />
					
						<g:sortableColumn property="MD5" title="${message(code: 'neighbor.md5.label', default: 'MD5')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${neighborInstanceList}" status="i" var="neighborInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${neighborInstance.id}">${fieldValue(bean: neighborInstance, field: "neighborDescription")}</g:link></td>
					
						<td>${fieldValue(bean: neighborInstance, field: "neighborIp")}</td>
					
						<td>${fieldValue(bean: neighborInstance, field: "routerId")}</td>
					
						<td>${fieldValue(bean: neighborInstance, field: "localIp")}</td>
					
						<td>${neighborInstance?.localAS}</td>
					
						<td>${neighborInstance?.peerAS}</td>
						
						<td>${fieldValue(bean: neighborInstance, field: "gracefulRestart")}</td>
					
						<td>${neighborInstance?.md5}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${neighborInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
