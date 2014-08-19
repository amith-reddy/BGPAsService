<%@ page import="bgpasservice.Port" %>


<g:hiddenField type="text" name="portRule" value="" id="portRule"/>
<div id="entry1" class="clonedInput">
<h4 id="reference" name="reference" class="heading-reference">Port Rule #1</h4>
<div id="portradio1" class="fieldcontain required">
	<label id = "label1" for="PortOption1">
		<g:message code="port.end_port.label" default="Port Option" />
		<span class="required-indicator">*</span>
	</label>
	<g:radioGroup  class="fieldcontain" name="PortOption1" labels="['Port Value','Port Range']" values="['value','range']" value="value">
	 ${it.radio}${it.label}&nbsp;
	</g:radioGroup>
</div>

<div id="start_port1" class="fieldcontain required">
	<label for="portStart1">
		<g:message code="port.start_port.label" default="Port Start" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" id="portstartvalue1" name="portStart1" min = "${grailsApplication.config.grails.port.minvalue}" max="${grailsApplication.config.grails.port.maxvalue}" required=""/>

</div>

<div id="end_port1" class="fieldcontain">
	<label for="portEnd1">
		<g:message code="port.end_port.label" default="Port End" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" id="portendvalue1" name="portEnd1" min = "${grailsApplication.config.grails.port.minvalue}" max="${grailsApplication.config.grails.port.maxvalue}" />

</div>
</div>
<div id="addDelButtons" class="buttons">
       <input type="button" class="add" id="btnAdd" value="Add port rule"> <input type="button" class="delete" id="btnDel" value="Delete port rule above">
</div>



