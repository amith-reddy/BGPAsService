
<%@ page import="bgpasservice.Flow" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'flow.label', default: 'Flow')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<asset:javascript src="fp.js"/>
		<asset:stylesheet src="fp.css"/>
	</head>
	<body>
		<a href="#list-flow" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
		<div id="list-flow" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			
			<filterpane:currentCriteria domainBean="Flow" 
              removeImgDir="images" removeImgFile="bullet_remove.png" fullAssociationPathFieldNames="false"
              filterPropertyValues="${[createdDate: [years: 2099..2000, precision: 'minute']]}"/>
                              
			<filterpane:filterButton class="filterbtn" text="Search" />
			<table>
			<thead>
					<tr>
					
						<th><g:message code="flow.neighbors.label" default="Neighbors" /></th>
					
						<g:sortableColumn property="sourceSubnet" title="${message(code: 'flow.sourceSubnet.label', default: 'Source Subnet')}" params="${filterParams}"/>
					
						<g:sortableColumn property="destinationSubnet" title="${message(code: 'flow.destinationSubnet.label', default: 'Destination Subnet')}" params="${filterParams}"/>
						
						<th><g:message code="flow.protocol.label" default="Protocol" /></th>
					
						<th><g:message code="flow.exaaction.label" default="Exaaction" /></th>
					
						<th><g:message code="flow.sourceport.label" default="Sourceport" /></th>
						
						<th><g:message code="flow.destinationport.label" default="Destinationport" /></th>
						
						<g:sortableColumn property="status" title="${message(code: 'flow.status.label', default: 'Status')}" params="${filterParams}"/>
						
						<g:sortableColumn property="dateCreated" title="${message(code: 'flow.dateCreated.label', default: 'Date Created')}" params="${filterParams}"/>
					
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${flowList}" status="i" var="flowInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${flowInstance.id}">${fieldValue(bean: flowInstance, field: "neighbors")}</g:link></td>
					
						<td>${fieldValue(bean: flowInstance, field: "sourceSubnet")}</td>
					
						<td>${fieldValue(bean: flowInstance, field: "destinationSubnet")}</td>
					
						<td>${fieldValue(bean: flowInstance, field: "protocol")}</td>
					
						<td>${fieldValue(bean: flowInstance, field: "exaaction")}</td>
					
						<td>${fieldValue(bean: flowInstance, field: "sourceport")}</td>
						
						<td>${fieldValue(bean: flowInstance, field: "destinationport")}</td>
						
						<td>${fieldValue(bean: flowInstance, field: "status")}</td>
						
						<td><g:formatDate date="${flowInstance.dateCreated}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${flowCount == null ? Flow.count(): flowCount}" params="${filterParams}" />
			</div>
			<filterpane:filterPane domain="bgpasservice.Flow" 
						 excludeProperties="comment"
						 additionalProperties="identifier"
                         associatedProperties="neighbors.neighborDescription, protocol.protocolRule,exaaction.actionName, exaaction.actionParameter, sourceport.portRule, destinationport.portRule"
                         filterPropertyValues="${[createdDate: [years: 2099..2000, precision: 'minute']]}"
                         titleKey="fp.tag.filterPane.titleText"
                         dialog="true"
                         visible="n"
                         showSortPanel="n"
                         showTitle="y"
                         fullAssociationPathFieldNames="false"/>
		</div>
	</body>
</html>
