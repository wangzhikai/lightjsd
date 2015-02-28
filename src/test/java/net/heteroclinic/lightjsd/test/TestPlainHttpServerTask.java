package net.heteroclinic.lightjsd.test;

import static org.junit.Assert.assertTrue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.heteroclinic.lightjsd.servers.PlainHttpServerTask;
import net.heteroclinic.lightjsd.servers.Task;

import org.junit.Test;

public class TestPlainHttpServerTask {
	
	@Test
	public void testReadPlainHttpServerTask() throws InterruptedException, IOException {
		PrintWriter pw = new PrintWriter(System.out,true);
		
		final int serverPort = 8123;
		// don't quite understand this var's purpose.
		final int backLog = 0;
		
		List<Future<?>> fl = new ArrayList<Future<?>>();
		ExecutorService exec = Executors.newCachedThreadPool();	
		int numberOfThreads = 1;
		List<Task> tl = new ArrayList<Task>();
		for (int i =0; i<numberOfThreads; i++ ) {
			Task t ;
			fl.add(exec.submit(t = new PlainHttpServerTask(pw,true,serverPort,backLog)));
			tl.add(t);
		}
		
		// TODO need a semaphore or barrier here, or the client will request so early that the server is not ready.
		long sleepTime = 200l;
		TimeUnit.MILLISECONDS.sleep(sleepTime);
		
        URL url = new URL("http://localhost:"+serverPort+"/");
        BufferedReader in = new BufferedReader(
        new InputStreamReader(url.openStream()));

        int lineCount = 0;
        //defined in CustomizedHTTPHandler
        String keyString = "Hello";
        boolean stringFound = false;
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
        	lineCount++;
        	if (inputLine.toLowerCase().contains(keyString.toLowerCase())) {
        		stringFound = true;
        	}
            //System.out.println(inputLine);
        }
        in.close();
		
		for (Task t:tl) {
			t.setRequestedStop(true);
		}
		exec.shutdown();
		
		long timeOutInMillis = 200l;
		exec.awaitTermination(timeOutInMillis, TimeUnit.MILLISECONDS);
		
		for (Task t:tl) {
			pw.printf("The task should stop as requested : %s",t.isRequestedStop());
		}			
		
		//assert line count > 0
		assertTrue("No http text found",lineCount>0 );
		//assert hello found
		assertTrue(keyString+ " not found",stringFound );
	}
	
}
