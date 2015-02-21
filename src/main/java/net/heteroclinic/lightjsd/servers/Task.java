package net.heteroclinic.lightjsd.servers;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Task implements Runnable {
	static protected AtomicLong count = new AtomicLong(0l);
	protected long iD = -1l;
	protected volatile boolean requestedStop = false;
	protected volatile boolean otherStop = false;

	public boolean isRequestedStop() {
		return requestedStop;
	}

	public void setRequestedStop(boolean requestedStop) {
		this.requestedStop = requestedStop;
	}

	public Task() {
		this.iD = count.incrementAndGet();
	}

	@Override
	public void run() {
		System.out.println("Task " + this.iD + " started.");
		try {
			while (!Thread.interrupted() && !requestedStop && !otherStop) {
				try {
					TimeUnit.MILLISECONDS.sleep(200);
				} catch (InterruptedException e) {
					// show or not?
					throw e;
				} catch (Exception e) {
					// show or not?
					//throw e;
					otherStop = true;
				}
			}
		} catch (Exception e) {
			// eat it
		} finally {
			System.out.println("Task " + this.iD + " ended.");
		}
	}

}