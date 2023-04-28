# Install MySQL image
FROM mysql:8

#Creating Environment variable for mysql
ENV MYSQL_ROOT_PASSWORD root
ENV MYSQL_DATABASE hoteldb
ENV MYSQL_USER hoteldbuser
ENV MYSQL_PASSWORD tanvi
ENV MYSQL_TCP_PORT 3307

#Adding mysql Dump file to entrypoint
ADD Dump.sql /docker-entrypoint-initdb.d/

#EXPOSE 3307

