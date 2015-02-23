package net.heteroclinic.lightjsd.servers;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CustomizedHTTPHandlers {

	static class HelloWorldHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			StringBuilder sb = new StringBuilder("");
			sb.append("<h1>Hello!</h1>");
			String echo = sb.toString();
			t.sendResponseHeaders(200, echo.length());
			OutputStream os = t.getResponseBody();
			os.write(echo.getBytes());
			os.close();
		}
	}
	
}
