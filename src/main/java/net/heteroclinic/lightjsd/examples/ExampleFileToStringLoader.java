package net.heteroclinic.lightjsd.examples;

import java.io.PrintWriter;

import net.heteroclinic.lightjsd.loaders.FileToStringLoader;

public class ExampleFileToStringLoader {
	
	public static void main(String[] args) {
		PrintWriter pw = new PrintWriter(System.out,true);
		String fileToLoad = "Hello.js";
		String result1 = FileToStringLoader.getAFileToString(fileToLoad);
		String result2 = FileToStringLoader.getAFileToString(fileToLoad,pw);
		
		pw.println(result1);
		pw.println(result2);
		
	}
	
}
