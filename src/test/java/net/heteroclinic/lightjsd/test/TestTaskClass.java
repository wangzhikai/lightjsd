package net.heteroclinic.lightjsd.test;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.heteroclinic.lightjsd.servers.Task;

import org.junit.Test;

public class TestTaskClass {

	PrintWriter pw = new PrintWriter(System.out,true);

	@Test
	public void testStopTasksByCancelFutures() throws InterruptedException {
		List<Future<?>> fl = new ArrayList<Future<?>>();
		ExecutorService exec = Executors.newCachedThreadPool();	
		int numberOfThreads = 5;
		List<Task> tl = new ArrayList<Task>();
		for (int i =0; i<numberOfThreads; i++ ) {
			Task t ;
			fl.add(exec.submit(t = new Task(pw,false)));
			tl.add(t);
		}
		
		long sleepTime = 200l;
		TimeUnit.MILLISECONDS.sleep(sleepTime);
		
		for (Future<?> f:fl) {
			f.cancel(true);
		}
		exec.shutdown();
		
		long timeOutInMillis = 1000l;
		exec.awaitTermination(timeOutInMillis, TimeUnit.MILLISECONDS);
		
		for (Task t:tl) {
			assertEquals("The task should not stop as requested : ",t.isOtherStop(),true);
		}
	}
	
	@Test
	public void testStopTasksByNiceRequest() throws InterruptedException {
		List<Future<?>> fl = new ArrayList<Future<?>>();
		ExecutorService exec = Executors.newCachedThreadPool();	
		int numberOfThreads = 5;
		List<Task> tl = new ArrayList<Task>();
		for (int i =0; i<numberOfThreads; i++ ) {
			Task t ;
			fl.add(exec.submit(t = new Task(pw,false)));
			tl.add(t);
		}
		
		long sleepTime = 200l;
		TimeUnit.MILLISECONDS.sleep(sleepTime);
		
		for (Task t:tl) {
			t.setRequestedStop(true);
		}
		exec.shutdown();
		
		long timeOutInMillis = 1000l;
		exec.awaitTermination(timeOutInMillis, TimeUnit.MILLISECONDS);
		
		for (Task t:tl) {
			assertEquals("The task should not stop as requested : ",t.isRequestedStop(),true);
		}
	}
}
