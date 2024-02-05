steps for start the project:

- maven clean install
- docker-compose up -d
- open http://localhost:8080/help-service/v1/support


build of url:
http://localhost:${port}/${war}${from_web.xml}

    port: the port you specified in the docker-compose.yml
    war: name of your war file, you could specified this in the pom.xml -> 
    <configuration>
    <warName>help-service</warName>
    </configuration>
    from_web.xml: you could specified this in the web.xml ->  
    <url-pattern>/v1/support</url-pattern>

Use cases:
for GET request: http://localhost:8080/help-service/v1/support
for POST request: http://localhost:8080/help-service/v1/support?message=Example