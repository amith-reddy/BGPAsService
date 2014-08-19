<%@ page import="bgpasservice.Flow" %>



<div class="fieldcontain ${hasErrors(bean: flowInstance, field: 'neighbors', 'error')} required">
	<label for="neighbors">
		<g:message code="flow.neighbors.label" default="Neighbors" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="neighbors" name="neighbors.id" from="${bgpasservice.Neighbor.list()}" optionKey="id" required="" value="${flowInstance?.neighbors?.id}" class="many-to-one"/>
	<a href="javascript:void(0)" onClick="createPopup('Neighbor',420,800); return true" title="Create New Neighbor">Create New Neighbor</a>
</div>

<div class="fieldcontain ${hasErrors(bean: flowInstance, field: 'sourceSubnet', 'error')} required">
	<label for="sourceSubnet">
		<g:message code="flow.sourceSubnet.label" default="Source Subnet" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="sourceSubnet" maxlength="20" required="" value="${flowInstance?.sourceSubnet}"/><div class="tip" id="sourceSubnettip">Enter valid IP/Subnet Mask. Example:173.199.62.10/24</div>

</div>

<div class="fieldcontain ${hasErrors(bean: flowInstance, field: 'destinationSubnet', 'error')} required">
	<label for="destinationSubnet">
		<g:message code="flow.destinationSubnet.label" default="Destination Subnet" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="destinationSubnet" maxlength="20" required="" value="${flowInstance?.destinationSubnet}"/><div class="tip" id="destinationSubnettip">Enter valid IP/Subnet Mask. Example:173.199.62.10/24</div>

</div>

<div class="fieldcontain ${hasErrors(bean: flowInstance, field: 'protocol', 'error')} required">
	<label for="protocol">
		<g:message code="flow.protocol.label" default="Protocol" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="protocol" name="protocol.id" from="${bgpasservice.Protocol.list()}" optionKey="id" required="" value="${flowInstance?.protocol?.id}" class="many-to-one"/>
	<a href="javascript:void(0)" onClick="createPopup('Protocol',420,450); return true" title="Create New Protocol">Create New Protocol</a>
</div>

<div class="fieldcontain ${hasErrors(bean: flowInstance, field: 'exaaction', 'error')} required">
	<label for="exaaction">
		<g:message code="flow.exaaction.label" default="Exaaction" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="exaaction" name="exaaction.id" from="${bgpasservice.Exaaction.list()}" optionKey="id" required="" value="${flowInstance?.exaaction?.id}" class="many-to-one"/>
	<!-- <modalbox:createLink controller="Exaaction" action="create"  id="test" title="Create New ExaBGP Action" width="500">Create New ExaBGP Action</modalbox:createLink>--> 
	<a href="javascript:void(0)"  onClick="createPopup('Exaaction',420,450); return true" title="Create New ExaBGP Action">Create New ExaBGP Action</a> 
</div>


<div class="fieldcontain ${hasErrors(bean: flowInstance, field: 'sourceport', 'error')} required">
	<label for="sourceport">
		<g:message code="flow.sourceport.label" default="Sourceport" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="sourceport" name="sourceport.id" from="${bgpasservice.Port.list()}" optionKey="id" required="" value="${flowInstance?.sourceport?.id}" class="many-to-one"/>
	<a href="javascript:void(0)" onClick="createPopup('Port',550,500); return true" title="Create New Source Port">Create New Source Port</a>
</div>

<div class="fieldcontain ${hasErrors(bean: flowInstance, field: 'destinationport', 'error')} required">
	<label for="destinationport">
		<g:message code="flow.destinationport.label" default="Destinationport" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="destinationport" name="destinationport.id" from="${bgpasservice.Port.list()}" optionKey="id"  required="" value="${flowInstance?.destinationport?.id}" class="many-to-one"/>
	<a href="javascript:void(0)" onClick="createPopup('Port',550,500); return true" title="Create New Destination Port">Create New Destination Port</a>
</div>

<div class="fieldcontain ${hasErrors(bean: flowInstance, field: 'status', 'error')} required">
	<label for="status">
		<g:message code="flow.status.label" default="Status" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="status" from="${flowInstance.constraints.status.inList}" required="" value="${flowInstance?.status}" valueMessagePrefix="flow.status"/>

</div>

<div class="fieldcontain ${hasErrors(bean: flowInstance, field: 'comment', 'error')} ">
	<label for="comment">
		<g:message code="flow.comment.label" default="Comment" />
	</label>
	<g:textField name="comment" maxlength="100" value="${flowInstance?.comment}"/><div class="tip" id="commenttip">Enter the reason for the creation of flow</div>

</div>

