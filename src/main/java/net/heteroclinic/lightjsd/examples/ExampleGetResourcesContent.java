package net.heteroclinic.lightjsd.examples;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

public class ExampleGetResourcesContent {
	public void readContentFromResource(String resName) {
		URL urlRes1 = new ExampleGetResources().printFindResource(resName);
		InputStream fis = null;
		try {
			//fis = new FileInputStream(urlRes1.g);
			//fis = this.getClass().getResourceAsStream(urlRes1.getFile());
			//fis = this.getClass().getResourceAsStream(urlRes1.getFile());
			fis = urlRes1.openStream();
			
		} catch (Exception e) {
			// eat it
			// e.printStackTrace();
		}
		if (null != fis ) {
			System.out.println(resName +  " loaded.");
		} else {
			System.out.println(resName +  " not loaded.");
			return;
		}
		
	}

	public static void main(String[] args) {
		new ExampleGetResourcesContent().readContentFromResource("res1");

	}

}
