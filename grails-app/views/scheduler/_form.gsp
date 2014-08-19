<%@ page import="bgpasservice.Scheduler" %>


<g:hiddenField type="text" name="operation" id="operation" value="${params.operation}"/>

<div class="fieldcontain ${hasErrors(bean: schedulerInstance, field: 'newFlows', 'error')} required">
	<label for="newFlows">
		<g:message code="scheduler.newFlows.label" default="New Flows" />
		<span class="required-indicator">*</span>
	</label>
	<g:if test="${params.operation == 'delete' || params.operation == 'edit'}">
		<g:select id="newFlows" name="newFlows.id" from="${schedulerInstance.newFlows}" optionKey="id" required="" readonly="readonly" value="${schedulerInstance.newFlows?.id}" class="many-to-one"/>
	</g:if>
	<g:else>
	<g:select id="newFlows" name="newFlows.id" from="${bgpasservice.Flow.findAll()}" optionKey="id" required="" value="${schedulerInstance.newFlows?.id}" class="many-to-one"/>
    </g:else>
</div>

<div id="scheduleOptionid" class="fieldcontain required">
	<label  for="scheduleOption">
		<g:message code="scheduler.scheduleOption.label" default="Scheduler Option" />
		<span class="required-indicator">*</span>
	</label>
	<g:radioGroup  class="fieldcontain" name="scheduleOption" labels="['Execute Now','Schedule Once','Schedule Periodically']" values="['Execute Now','Schedule Once','Schedule Periodically']" value="${schedulerInstance.scheduleOption}">
	 ${it.radio}${it.label}&nbsp;
	</g:radioGroup>
</div>

<div id = "scheduleTimeId" class="fieldcontain ${hasErrors(bean: schedulerInstance, field: 'scheduleTime', 'error')} required">
	<label for="scheduleTime">
		<g:message code="scheduler.scheduleTime.label" default="Schedule Time" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker class="datepickerclass" id="scheduletimedateid" name="scheduleTime" precision="minute"  value="${schedulerInstance?.scheduleTime}"  />

</div>

<div id = "activationCronDivId" class="fieldcontain ${hasErrors(bean: schedulerInstance, field: 'activationSchedule', 'error')} required">
	<label for="activationCron">
		<g:message code="scheduler.activationSchedule.label" default="Activation Schedule" />
		<span class="required-indicator">*</span>
	</label>
<g:hiddenField name="activationSchedule" class="cron" value="${schedulerInstance?.activationSchedule}" />
</div>

<div id = "deactivationCronDivId" class="fieldcontain ${hasErrors(bean: schedulerInstance, field: 'deactivationSchedule', 'error')} required">
	<label for="deactivationSchedule">
		<g:message code="scheduler.deactivationSchedule.label" default="Deactivation Schedule" />
		<span class="required-indicator">*</span>
	</label>
<g:hiddenField name="deactivationSchedule" class="cron" value="${schedulerInstance?.deactivationSchedule}" />
</div>
<!--<g:hiddenField class="example2" type="text" name="cron" id="cron" value="* * * * *"/>-->

<div id="deleteScheduleOptionid" class="fieldcontain required">
	<label  for="deleteScheduleOption">
		<g:message code="scheduler.deleteScheduleOption.label" default="Delete Scheduler Option" />
		<span class="required-indicator">*</span>
	</label>
	<g:radioGroup  class="fieldcontain" name="deleteScheduleOption" labels="['Dont Delete','Delete Now','Delete Later']" values="['Dont Delete','Delete Now','Delete Later']" value="${schedulerInstance.deleteScheduleOption}">
	 ${it.radio}${it.label}&nbsp;
	</g:radioGroup>
</div>

<div id = "deleteScheduleTimeId" class="fieldcontain ${hasErrors(bean: schedulerInstance, field: 'deleteScheduleTime', 'error')} required">
	<label for="deleteScheduleTime">
		<g:message code="scheduler.deleteScheduleTime.label" default="Delete Schedule Time" />
		<span class="required-indicator">*</span>
	</label>
	
	<g:datePicker class="datepickerclass" id="deletescheduletimedateid" name="deleteScheduleTime" precision="minute"  value="${schedulerInstance?.deleteScheduleTime}" />

</div>


<div class="fieldcontain ${hasErrors(bean: schedulerInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="scheduler.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${schedulerInstance?.name}"/> <div class="tip" id="nameTip">Enter a name. Example:George</div> 

</div>

<div class="fieldcontain ${hasErrors(bean: schedulerInstance, field: 'email', 'error')} ">
	<label for="email">
		<g:message code="scheduler.email.label" default="Email" />
		
	</label>
	<g:textField name="email" value="${schedulerInstance?.email}"/>	 <div class="tip" id="emailTip">To notify messages regarding flow addition/deletion in router. Example:george.clooney@citrix.com</div>

</div>

