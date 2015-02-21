package net.heteroclinic.lightjsd.servers;

import java.io.IOException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class PlainHttpServerTask extends Task {

	static class PlainHttpTaskHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {

		}
	}

	@Override
	public void run() {

	}
}