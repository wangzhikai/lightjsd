package net.heteroclinic.lightjsd.servers;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.sun.net.httpserver.HttpServer;

public class PlainHttpServerTask extends Task {

	public PlainHttpServerTask() {
		super();
	}
	
	public PlainHttpServerTask(PrintWriter pw, boolean printDebug) {
		super(pw,printDebug);
	}
	
	public PlainHttpServerTask(PrintWriter pw, boolean printDebug, int portNumber,int backLog) {
		super(pw,printDebug);
		this.portNumber = portNumber;
		this.backLog = backLog;
	}
	
	protected int portNumber = 8000;
	protected int backLog =0;

	//TODO pass the handler as a functor
	@Override
	public void run() {
		HttpServer server = null; 
		try {
	        
			server = HttpServer.create(new InetSocketAddress(portNumber), backLog);
			server.createContext("/", new CustomizedHTTPHandlers.HelloWorldHandler());
	        server.setExecutor(null);
	        server.start();
	        
			log("Task " + this.id + " started.");
			try {
				while (!Thread.interrupted() && !requestedStop && !otherStop) {
					try {
						// Do concrete job here
						TimeUnit.MILLISECONDS.sleep(200);
					} catch (InterruptedException e) {
						otherStop = true;
						throw e;
					} catch (Exception e) {
						otherStop = true;
						throw e;
					}
				}
			} catch (Exception e) {
				// eat it
				log(e.toString());
			} finally {
				log("Task " + this.id + " ended.");
			}
			
		} catch (Exception e) {
		} finally {
			if (null != server) 
				server.stop(0);
		}
	}
	
	// TODO How to test shutdownhook?
	
	// TODO This main to a class in example folder
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
		
		long sleepTime = 20000l;
		TimeUnit.MILLISECONDS.sleep(sleepTime);
		
		for (Task t:tl) {
			t.setRequestedStop(true);
		}
		exec.shutdown();
		
		long timeOutInMillis = 200l;
		exec.awaitTermination(timeOutInMillis, TimeUnit.MILLISECONDS);
		
		for (Task t:tl) {
			pw.printf("The task should not stop as requested : %s",t.isRequestedStop());
		}		
	}
	
}