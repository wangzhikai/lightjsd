/*
 * Zhikai Wang/www.heteroclinic.net (c) 2015. All third party licenses and rights are automatically cascaded. The responsibility of the author(s), Zhikai Wang/www.heteroclinic.net, to the maximum is to remove or modify matters in dispute. You can utilize this project at good-will. The inverse of good-will includes illegal activities that are subject to jurisdiction applicable. 
 */
package net.heteroclinic.lightjsd.examples;

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
