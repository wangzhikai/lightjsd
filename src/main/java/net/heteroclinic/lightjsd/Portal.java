/*
 * Zhikai Wang/www.heteroclinic.net (c) 2015. All third party licenses and rights are automatically cascaded. The responsibility of the author(s), Zhikai Wang/www.heteroclinic.net, to the maximum is to remove or modify matters in dispute. You can utilize this project at good-will. The inverse of good-will includes illegal activities that are subject to jurisdiction applicable. 
 */
package net.heteroclinic.lightjsd;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.heteroclinic.lightjsd.servers.PlainHttpServerTask;
import net.heteroclinic.lightjsd.servers.Task;

/**
 * This class is the entry point of Lightjsd. Also keeps the development log.
 * @author Zhikai Wang / www.heteroclinic.net
 */
/*
 * TODO Beta1.0 20150111~20150228
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
 * - DONE 11. Beta 1.0
 * -- DONE 11.0 Change pom for jar name
 * -- DONE 11.1 Add shutdownhook in Portal.main
 * -- DONE 11.1.1 Test run in IDE
 * -- DONE 11.1.2 Test run the jar with shutdown hook ctrl-c
 * -- DONE 11.1.3 Test run the jar with shutdown hook by sending signal with 'kill pid', Note 'kill -9 pid' will not call shutdown hook. 
 * -- DONE 11.2 Add/complete readme file
 * -- DONE 11.3 Add Beta 1.0 release note
 * -- DONE 11.3.1 Wrap httpServer, client use Java URL
 * -- DONE 11.3.2 Maven project management
 * -- DONE 11.3.3 How to handle resources
 * -- DONE 11.3.4 Experiment explicit thread termination
 * -- DONE 11.3.5 Add unit tests as many as possible
 * -- DONE 11.3.6 Try to documents 3.5 and 3.6 slow down the process of writing code. But some time later, they make it easy to pick up the project again after long in-activity. 
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
	static class GeneralShutdownHook extends Thread {// must be static
		protected PrintWriter pw;
		protected List<Task> tl; 
		protected ExecutorService exec;

		
		public GeneralShutdownHook(List<Task> tl, ExecutorService exec,PrintWriter pw) {
			this.pw = pw;
			this.tl = tl;
			this.exec = exec;
		}

		@Override
		public void run() {
			exec.shutdown();		
			for (Task t : tl) {
				t.setRequestedStop(true);
			}
			long timeOutInMillis = 200l;
			// Protocol join similar exit.
			try {
				exec.awaitTermination(timeOutInMillis, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
			
			}
			
//			for (Task t:tl) {
//				pw.printf("The task should stop as requested : %s\n",t.isRequestedStop());
//			}
			tl.clear();
			pw.println("End with shutdown hook.");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		PrintWriter pw = new PrintWriter(System.out,true);

		List<Future<?>> fl = new ArrayList<Future<?>>();
		ExecutorService exec = Executors.newCachedThreadPool();
		List<Task> tl = new ArrayList<Task>();
		tl = Collections.synchronizedList(tl);
		
	    Runtime.getRuntime().addShutdownHook( new GeneralShutdownHook(tl,exec,pw));
	    
		// The two ways to stop a server.
		// 1. Use console, in IDE e.g. Eclipse, it shields signals like ctrl-c etc. 
		// 2. Use shutdown hook, as a system service run without console, you can "service lightjsd start/stop/restart"
		System.out.println("Type stop or use ctrl-c to terminate the service. ctrl-c not work in Eclipse");
		
		int numberOfThreads = 1;
		
		for (int i =0; i<numberOfThreads; i++ ) {
			Task t ;
			fl.add(exec.submit(t = new PlainHttpServerTask(pw,true)));
			tl.add(t);
		}
		// TODO Need a cyclic barrier etc to indicate the server completes loading.
		pw.println("Server started!");
		
		String condition = "";
		Scanner scanner = new Scanner(System.in);
		while (!condition.equalsIgnoreCase("stop")) {
			try {
				condition = scanner.nextLine();
			} catch (Throwable t) {
				t.printStackTrace();
				condition = "stop";
			} 
		}
		scanner.close();

		exec.shutdown();		
		for (Task t : tl) {
			t.setRequestedStop(true);
		}
		long timeOutInMillis = 200l;
		// Protocol join similar exit.
		exec.awaitTermination(timeOutInMillis, TimeUnit.MILLISECONDS);

		for (Task t:tl) {
			pw.printf("The task should stop as requested : %s\n",t.isRequestedStop());
		}
		//pw.println("If shutdown hook is called this line won't be called.");
		pw.println("End with inputing stop in main.");

	}

}
