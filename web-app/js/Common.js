function popupclose()
{
	if(window.opener!=null && window.opener.location != null)
	{
		window.opener.location.reload(true);
		window.close();
	}
}

function pagereload()
{
	if(window != null)
	{
		window.reload(true);
	
	}
}