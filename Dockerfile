# Creating image from tomcat8-jdk8 image from dockerhub
FROM tanvishah280/tomcat8-jdk8

#Copying war file from local to specified directory 
COPY hotelbokingsystem-jpa.war /opt/tomcat/webapps/ROOT.war

#Set CATALINA HOME & Java_OPTS - catalina to path variable, setting JVM arguments 
ENV CATALINA_HOME /opt/tomcat
ENV PATH $PATH:$CATALINA_HOME/bin
ENV JAVA_OPTS="-Xms1024m -Xmx1024m -Xss8192k -XX:PermSize=500m -XX:CMSInitiatingOccupancyFraction=50 -XX:+ExplicitGCInvokesConcurrent -XX:+CMSClassUnloadingEnabled -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:NewRatio=1 -XX:SurvivorRatio=1  -Dorg.apache.cxf.JDKBugHacks.imageIO=false"
ENV DOMAIN db
ENV DB_PORT 3307
ENV DB_USER hoteldbuser
ENV DB_PASSWORD tanvi
ENV DATABASE hoteldb

#EXPOSE port 8080 to host machine to connect to
EXPOSE 8080

#WORKDIR will set the working directory for any RUN,CMD,COPY,ADD, ENTRYPOINT instructions that follow it in the DF
WORKDIR /opt/tomcat

# Launch Tomcat
CMD ["/opt/tomcat/bin/catalina.sh", "run"]