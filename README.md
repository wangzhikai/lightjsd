# lightjsd
'lightjsd' is a light-weight Java Web server. 

Introduction
----
'lightjsd' is a light-weight Web server to study and experiment basic Web service functionality and to ease writing and testing Javascript programs. It uses Sun/Oracle com.sun.net.httpserver package. We wrap this server so it is more convenient for us to add more tests, more easily to embed Javascripts in service contexts.

License
----
All third party licenses and rights are automatically cascaded. The responsibility of the author(s), Zhikai Wang/www.heteroclinic.net, to the maximum is to remove or modify matters in dispute. You can utilize this project at good-will. The inverse of good-will includes illegal activities that are subject to jurisdiction applicable. Zhikai Wang/www.heteroclinic.net (c) 2015.

QuickStart
----
After you get the source repository, you can use an IDE to open the Maven project. The entry point of the server is in Portal.java. You can study the project by reviewing examples in package net.heteroclinic.lightjsd.examples. Or study the test cases in folder src/test/java. To compile the binary, at the source root directory, execute 'mvn clean package'. To execute/start the binary, run 'java -jar target/lightjsd-*.jar'. Use ctrl-c or 'kill pid' to end the server in protocol (calling shutdown-hook.

