package net.heteroclinic.lightjsd.examples;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.heteroclinic.lightjsd.servers.Task;

public class ExampleTaskClass {

	public static void main(String[] args) throws InterruptedException {
		
		PrintWriter pw = new PrintWriter(System.out,true);
		
		// Test 1, terminate by cancel futures
		{
			List<Future<?>> fl = new ArrayList<Future<?>>();
			ExecutorService exec = Executors.newCachedThreadPool();	
			int numberOfThreads = 5;
			List<Task> tl = new ArrayList<Task>();
			for (int i =0; i<numberOfThreads; i++ ) {
				Task t ;
				fl.add(exec.submit(t = new Task(pw,true)));
				tl.add(t);
			}
			
			long sleepTime = 500l;
			TimeUnit.MILLISECONDS.sleep(sleepTime);
			
			for (Future<?> f:fl) {
				f.cancel(true);
			}
			exec.shutdown();
			
			long timeOutInMillis = 1000l;
			exec.awaitTermination(timeOutInMillis, TimeUnit.MILLISECONDS);
			
			for (Task t:tl) {
				pw.println("id:"+t.getId()+"\t isOtherStop():"+t.isOtherStop()+"\t isRequestedStop():"+ t.isRequestedStop());
			}
			
		}
		
		// Test 2, terminate by requesting a stop
		{
			List<Future<?>> fl = new ArrayList<Future<?>>();
			ExecutorService exec = Executors.newCachedThreadPool();	
			int numberOfThreads = 5;
			List<Task> tl = new ArrayList<Task>();
			for (int i =0; i<numberOfThreads; i++ ) {
				Task t ;
				fl.add(exec.submit(t = new Task(pw,true)));
				tl.add(t);
			}
			
			long sleepTime = 500l;
			TimeUnit.MILLISECONDS.sleep(sleepTime);
			
			for (Task t:tl) {
				t.setRequestedStop(true);
			}
			exec.shutdown();
			
			long timeOutInMillis = 1000l;
			exec.awaitTermination(timeOutInMillis, TimeUnit.MILLISECONDS);
			
			for (Task t:tl) {
				pw.println("id:"+t.getId()+"\t isOtherStop():"+t.isOtherStop()+"\t isRequestedStop():"+ t.isRequestedStop());
			}
		}

	}

}
