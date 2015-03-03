/*
 * Zhikai Wang/www.heteroclinic.net (c) 2015. All third party licenses and rights are automatically cascaded. The responsibility of the author(s), Zhikai Wang/www.heteroclinic.net, to the maximum is to remove or modify matters in dispute. You can utilize this project at good-will. The inverse of good-will includes illegal activities that are subject to jurisdiction applicable. 
 */
package net.heteroclinic.lightjsd.servers;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Task implements Runnable {
	static protected AtomicLong count = new AtomicLong(0l);
	protected long id = -1l;
	
	public long getId() {
		return id;
	}

	public boolean isOtherStop() {
		return otherStop;
	}

	@SuppressWarnings("unused")
	private void setOtherStop(boolean otherStop) {
		this.otherStop = otherStop;
	}

	protected volatile boolean requestedStop = false;
	protected volatile boolean otherStop = false;
	
	protected PrintWriter pw; 
	protected boolean printDebug = false;

	public boolean isRequestedStop() {
		return requestedStop;
	}

	public void setRequestedStop(boolean requestedStop) {
		this.requestedStop = requestedStop;
	}

	public Task() {
		this.id = count.incrementAndGet();
	}
	
	public Task(PrintWriter pw, boolean printDebug) {
		this.id = count.incrementAndGet();
		this.pw=pw;
		this.printDebug = printDebug;
	}
	
	public void log(String msg) {
		if (printDebug && null != pw) {
			pw.println(msg);
		}
	}

	@Override
	public void run() {
		
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
	}

}