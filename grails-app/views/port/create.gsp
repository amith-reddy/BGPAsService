<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'port.label', default: 'Port')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
		<link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'style.css')}"/>
		<script src="${resource(dir: 'js', file: 'Port.js')}"></script>
		<script>
	
		   $(document).ready(function(){
				
				var num = $('.clonedInput').length;

                if (num == 1)
                $('#btnDel').attr('disabled', true);
		                
		   		var portRules = "${portInstance?.portRule}".split(" ")
		   		var count = 1;
				if(portRules.length >= 1)
				{
					for (var i=1;i<=portRules.length;i++)
					{
						if(i != 1)
						{
							setTimeout(function() {
								count = count + 1;
						        $('#btnAdd').click();
						        setFormParameter(portRules[count-1],count); 
						    },10);
		
						}
						else
						{
							setFormParameter(portRules[0],i);
						}
					}
				}
			});
 
		</script>
	</head>
	<body>
		<a href="#create-port" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
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
			</ul>
		</div>
		<div id="create-port" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${portInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${portInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form id="portform" url="[resource:portInstance, action:'save']" >
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
