function createPopup(containername,w,h){ 
var link = "/BGPAsService/"+containername+"/create"
var left = (screen.width/2)-(w/2);
var top = (screen.height/2)-(h/2);
littleWindow = window.open(link, null, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width='+w+', height='+h+', top='+top+', left='+left);
} 
