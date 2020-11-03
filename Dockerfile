#Dockerfile

FROM jboss/wildfly

MAINTAINER "Paul Ponomarev"

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-full.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

ADD target/stand-app1.war /opt/jboss/wildfly/standalone/deployments/