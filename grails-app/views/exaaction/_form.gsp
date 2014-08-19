<%@ page import="bgpasservice.Exaaction" %>



<div id="action_name" class="fieldcontain ${hasErrors(bean: exaactionInstance, field: 'actionName', 'error')} required">
	<label for="actionName">
		<g:message code="exaaction.actionName.label" default="Action Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="actionName" from="${exaactionInstance.constraints.actionName.inList}" required="" value="${exaactionInstance?.actionName}" valueMessagePrefix="exaaction.actionName"/>

</div>

<div id="action_parameter" class="fieldcontain ${hasErrors(bean: exaactionInstance, field: 'actionParameter', 'error')} ">
	<label for="actionParameter">
		<g:message code="exaaction.actionParameter.label" default="Action Parameter" />
		
	</label>
		<g:textField id="actionParameterValue" name="actionParameter" value="${exaactionInstance?.actionParameter}"/><div class = "tip" id="actionparametertip">Bytes per second. Example: 9600</div>
</div>

