/*
 * Zhikai Wang/www.heteroclinic.net (c) 2015. All third party licenses and rights are automatically cascaded. The responsibility of the author(s), Zhikai Wang/www.heteroclinic.net, to the maximum is to remove or modify matters in dispute. You can utilize this project at good-will. The inverse of good-will includes illegal activities that are subject to jurisdiction applicable. 
 */
package net.heteroclinic.lightjsd.servers;

import java.io.IOException;
import java.io.OutputStream;

import net.heteroclinic.lightjsd.loaders.FileToStringLoader;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CustomizedHTTPHandlers {

	static class HelloWorldHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			StringBuilder sb = new StringBuilder("");
			String helloString = "<h1>Hello!</h1>";
			sb.append(helloString);
			String echo = sb.toString();
			t.sendResponseHeaders(200, echo.length());
			OutputStream os = t.getResponseBody();
			os.write(echo.getBytes());
			os.close();
		}
	}
	
	static class JSHelloWorldHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {
			StringBuilder sb = new StringBuilder("");
			String fileToLoad = "Hello.js";
			String helloScriptString = FileToStringLoader.getAFileToString(fileToLoad);
			//String helloString = "<h1>Hello!</h1>";
			sb.append(helloScriptString);
			String echo = sb.toString();
			t.sendResponseHeaders(200, echo.length());
			OutputStream os = t.getResponseBody();
			os.write(echo.getBytes());
			os.close();
		}
	}
	
}
