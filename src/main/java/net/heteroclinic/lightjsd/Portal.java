package net.heteroclinic.lightjsd;

/*
* Copyright (c) 2015, Zhikai Wang/www.heteroclinic.net. All rights reserved.
* Science and technology promotion license applied (third party licenses automatically cascaded).
* This is a good-will software. Users' liability always.
* The liability of Zhikai Wang/ www.heteroclinic.net to maximum is to remove or modify matter(s) in dispute.
* This is a good-will software. Given you keep this header, you can use this software at good will. The inverse
* of good-will including illegal activities that are subject to jurisdiction applicable.
*/
/**
 * This class is the entry point of Lightjsd. Also keeps the development log.
 * @author Zhikai Wang / www.heteroclinic.net
 * 
 */
/*
 * TODO Beta1.0 20140111
 * - TO-DO 1. Start a new git repo for Lightjsd/20140111
 * - TO-DO 2. Change Maven pom/20140111
 * - TO-DO 3. test public URL getResource(String name) in Eclipse IDE
 * 		this.getClass().getClassLoader().getResource(name);
 * -- TO-DO 4. test this.getClass().getClassLoader().getResources(name)) in Eclipse IDE; 
 *   See file ExampleResourceFinder
 * - TO-DO 5. Test getResources in jar, add the following in Portal.main
 		try {
			ExampleGetResources.main(new String [] {});
		} catch (IOException e) {
			e.printStackTrace();
		}
 * - TO-DO 6. Add unit test for getRources, including get resource content
 * -- change junit 3.8.1 to 4.11, otherwise `mvn test' can not find org.junit
 * -- `mvn clean package -DskipTests' OK
 * -- unfortunately, `mvn test' can not be used to detect jar resource. 
 * Note TO-DO 5 and TO-DO 6 are not the same thing.
 * - TO-DO 7. Get the content of resources
 * -- DONE ExampleGetResourcesContent in IDE
 * -- DONE Test in jar
 * -- unit test
 * - TO-DO 8. Task Class
 * -- DONE Unit test requestedStop
 * -- DONE Unit test exceptionStop/otherStop
 * - TO-DO 9. PlainHttpServerTask
 * -- DONE 9.0 Finish the ssl certificate experiment code in file ExampleURLReader
 * -- DONE 9.1 Example PlainHttpServerTask
 * -- DONE 9.2 Unit test PlainHttpServerTask
 * - DONE 10. Load a JavaScript file in resource to PlainHttpServer context
 * - TODO 11. Beta 1.0
 * -- TODO 11.0 Change pom for jar name
 * -- TODO 11.1 Add shutdownhook in main
 * -- TODO 11.1.1 Test run in IDE
 * -- TODO 11.1.2 Test run the jar with shutdown hook
 * -- TODO 11.2 Add/complete readme file
 * -- TODO 11.3 Add Beta 1.0 release note
 * -- TODO 11.3.1 Wrap httpServer, client use Java URL
 * -- TODO 11.3.2 Maven project manager
 * -- TODO 11.3.3 How to handle resources
 * -- TODO 11.3.4 Experiment explicit thread termination
 * -- TODO 11.3.5 Add unit tests as many as possible
 * -- TODO 11.3.6  
 * 
 * BACKLOG
 * - TODO Test in Eclipse with Run as Java application
 * 
 * -- TODO 1. Locate a file in Eclipse IDE
 * -- TODO 2. Locate a file as resource as in jar, this has to be done in console ie. java -jar some.jar.
 * -- TODO 3. Locate a file with the path specified in .properties etc
 * -- TODO 4. Can the file specified in .properties be copied to target automatically?
 * - TODO
 * - TODO Integrate Sun httpd
 * - TODO Integrate Sun httpd with ssl/tsl
 * -- TODO Including properly locating the keystore
 * - TODO Test load a java script
 * -- TODO Eclipse IDE
 * -- TODO Jar test
 * - TODO Test Facebook API
 * -- TODO Eclipse IDE
 * -- TODO Jar test
 * - TODO Beta1.0 
 * ********************************
 * TODO CONSIDERING 
 * - TODO Linkedin
 * - TODO Selenium
 * -- TODO Ssl support
 * - TODO Gradle service test
 * -- TODO SSL REST test
 * -- TOxDO 9.1 Fork an process 
 * -- TOxDO 9.2 Send signal to terminate process in 9.1
 * -- TODO Read HttpsURLConnectionLocalCertificateChain.java, HttpsURLConnection, TrustManager etc
 * -- TODO 88 unfortunately, `mvn test' can not be used to detect jar resource.
 */
public class Portal {

	public static void main(String[] args) {
		//System.out.println("Hello!");
		//new ExampleGetResourcesContent().readContentFromResource("res1");
		

		
	}

}
