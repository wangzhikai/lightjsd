/*
 * Zhikai Wang/www.heteroclinic.net (c) 2015. All third party licenses and rights are automatically cascaded. The responsibility of the author(s), Zhikai Wang/www.heteroclinic.net, to the maximum is to remove or modify matters in dispute. You can utilize this project at good-will. The inverse of good-will includes illegal activities that are subject to jurisdiction applicable. 
 */
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
