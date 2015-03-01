package net.heteroclinic.lightjsd.loaders;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import net.heteroclinic.lightjsd.examples.ExampleGetResources;

public class FileToStringLoader {
	
	public static String getAFileToString(String resName) {
		return getAFileToString(resName, null);
	}
	
	public static String getAFileToString(String resName, PrintWriter pw) {
		StringBuilder result = new StringBuilder("");
		URL urlRes1 = new ExampleGetResources().printFindResource(resName);
		InputStream fis = null;
		String oneline  =null;
		try {
			fis = urlRes1.openStream();
			
			BufferedReader br = 
					new BufferedReader(
							new InputStreamReader(fis));
			
			while ( (oneline = br.readLine()) != null) {
				result.append(oneline+"\n");
				if (pw!=null) {
					pw.println(oneline);
				}
			}
			
		} catch (Exception e) {
			// eat it
			// e.printStackTrace();
		}
		if (null != fis ) {
			if (null != pw)
				pw.println(resName +  " loaded.");
		} else {
			if (null != pw)
				pw.println("WARN: " +resName +  " not loaded.");
			
		}
		return result.toString();
	}
	

	public static void main(String[] args) {
		PrintWriter pw = new PrintWriter(System.out,true);
		
		String resName = "Hello.js";
		URL urlRes1 = new ExampleGetResources().printFindResource(resName);
		InputStream fis = null;
		String oneline  =null;
		try {
			fis = urlRes1.openStream();
			
			BufferedReader br = 
					new BufferedReader(
							new InputStreamReader(fis));
			
			while ( (oneline = br.readLine()) != null) {
				pw.println(oneline);
			}
			
		} catch (Exception e) {
			// eat it
			// e.printStackTrace();
		}
		if (null != fis ) {
			pw.println(resName +  " loaded.");
		} else {
			pw.println(resName +  " not loaded.");
			return;
		}
	}
	
}
