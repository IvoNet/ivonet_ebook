#!/usr/bin/env bash

#First build the app
mvn clean package
#create the temp deploy folder if it does not exist
#ssh -t ivonet@www.ivonet.nl "mkdir ~/deploy"
#Upload it to a non monitored location (avoids stacktraces in GlassFish because of slow uploads)
scp target/ebook.war ivonet@www.ivonet.nl:~/deploy
#undeploy the currently installed version of the service. This for the currect status updates for Glassfish
ssh -t ivonet@www.ivonet.nl "rm -f /opt/glassfish4/glassfish/domains/domain1/autodeploy/ebook.war"
#Move the war (on remote location) to the autodeploy folder of GlassFish (on remote server)
#Make sure that you have given the autodeploy folder the rights to the uploader to write there.
#e.g. (remote server) $ sudo 777 /opt/glassfish4/glassfish/domains/domain1/autodeploy
ssh -t ivonet@www.ivonet.nl "mv -v ./deploy/ebook.war /opt/glassfish4/glassfish/domains/domain1/autodeploy"
