BGPAsService
============
Installation Steps:
------------------
Open your favourite bash terminal and enter the following:

curl -s get.gvmtool.net | bash

Install the grails version 2.4.0 by typing:

$ gvm install grails 2.4.0

After the grails installation is complete, make it default version.

$ gvm default grails 2.4.0

1. Application Installation:
- Copy the BGPAsService directory present at github link(https://github.com/amith-reddy/bgpAsService)

2. Database Installation:

a) Create the database user and schema based on the DataSource.groovy file present in BGPAsService/conf directory. 
$create database db_name;
Ensure user for database is created and privileged or create one (will need root access to mysql)
$CREATE USER 'newuser’@‘hostname' IDENTIFIED BY 'password’;
$GRANT ALL PRIVILEGES ON db_name.* TO 'newuser’@‘hostname';
$FLUSH PRIVILEGES;

b) Create the quartz user based on the details present in BGPAsService/conf/quartz.properties file and create quartz tables by running the below commands.
$create database quartz;
Ensure user for database is created and privileged or create one (will need root access to mysql)
$CREATE USER 'quartz'@‘localhost' IDENTIFIED BY 'quartz’;
$GRANT ALL PRIVILEGES ON quartz.* TO 'quartz’@‘localhost';
$FLUSH PRIVILEGES; 
$source quartz.sql; //to create quartz tables. quartz.sql is present in the /opt/ec/services/BGPAsService directory

3. ExaBGP Tool installation
Installation instructions for ExaBGP tool are provided at https://github.com/Exa-Networks/exabgp

4. Run the BGPAsService application
To run grails-app, use command grails run-app(from within BGPAsService directory)
