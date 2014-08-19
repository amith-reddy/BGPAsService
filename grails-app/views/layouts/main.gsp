<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="ExaBGP Portal"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="214x214" href="${assetPath(src: 'apple-touch-icon-retina.png')}">
  		<asset:stylesheet src="application.css"/>
		<asset:javascript src="application.js"/>
		<g:layoutHead/>
	</head>
	<body>
		<div id="grailsLogo" class="ryl-glass-green-light" role="banner" style="height:70px"><a href="https://github.com/Exa-Networks/exabgp/wiki"><asset:image id="logo_img" src="BGP_400x400.jpeg" alt="Grails"/></a>
		<h1 id="welcome">Welcome to ExaBGP Portal</h1>
		
		<sec:ifLoggedIn><a href="/BGPAsService/logout" style="text-decoration:none"><asset:image id="logout" src="logout.png" alt="logout"/><span id = "logout_text" >sign off</span></a></sec:ifLoggedIn>
		<sec:ifLoggedIn><span id ="welcome_text">Welcome, ${sec.loggedInUserInfo(field: 'fullname')}!</span></sec:ifLoggedIn>
		</div>
		<g:layoutBody/>
		<div id="ftr" class="footer" role="contentinfo"></div>
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
	</body>
</html>
