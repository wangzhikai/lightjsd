package net.heteroclinic.lightjsd.examples;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.heteroclinic.lightjsd.servers.PlainHttpServerTask;
import net.heteroclinic.lightjsd.servers.Task;

public class ExamplePlainHttpServerTask {

	public static void main(String[] args) throws InterruptedException {
		PrintWriter pw = new PrintWriter(System.out,true);
		
		List<Future<?>> fl = new ArrayList<Future<?>>();
		ExecutorService exec = Executors.newCachedThreadPool();	
		int numberOfThreads = 1;
		List<Task> tl = new ArrayList<Task>();
		for (int i =0; i<numberOfThreads; i++ ) {
			Task t ;
			fl.add(exec.submit(t = new PlainHttpServerTask(pw,true)));
			tl.add(t);
		}
		
		long sleepTime = 30000l;
		TimeUnit.MILLISECONDS.sleep(sleepTime);
		
		for (Task t:tl) {
			t.setRequestedStop(true);
		}
		exec.shutdown();
		
		long timeOutInMillis = 200l;
		exec.awaitTermination(timeOutInMillis, TimeUnit.MILLISECONDS);
		
		for (Task t:tl) {
			pw.printf("The task should stop as requested : %s",t.isRequestedStop());
		}		
	}

}
