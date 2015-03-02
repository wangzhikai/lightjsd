package net.heteroclinic.lightjsd.servers;

import java.io.PrintWriter;
import java.net.InetSocketAddress;
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
			server.createContext("/jshello", new CustomizedHTTPHandlers.JSHelloWorldHandler());
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
}