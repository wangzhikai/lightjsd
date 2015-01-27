package net.heteroclinic.lightjsd.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import net.heteroclinic.lightjsd.examples.ExampleGetResources;

import org.junit.Test;

import junit.framework.TestCase;

public class TestGetResources extends TestCase {
	private static final String fileRes1 = "res1";
	private static final String fileRes2 = "res2";
	private static final String fileRes3 = "res3";
	private static final String fileRes4 = "sub1/res2";
	private static final String expectedContent1 = "res1";
	private static final String expectedContent2 = "res2";
	private static final String expectedContent3 = "res3";
	private static final String expectedContent4 = "res2";
	@Test
	public void testGetResourceURL() {

		URL urlRes1 = new ExampleGetResources().printFindResource(fileRes1);
		URL urlRes2 = new ExampleGetResources().printFindResource(fileRes2);
		URL urlRes3 = new ExampleGetResources().printFindResource(fileRes3);

		assertNotNull(urlRes1);
		assertNull(urlRes2);
		assertNotNull(urlRes3);
		System.out.println(urlRes1.toString());
		System.out.println(urlRes2 == null ? urlRes2 : urlRes2.toString());
		System.out.println(urlRes3.toString());
		/*
		 * In Eclipse IDE/class run as Junit test
		 * file:/home/zhikai/Desktop/workspace/lightjsd/target/classes/res1 null
		 * file:/home/zhikai/Desktop/workspace/lightjsd/target/classes/res3
		 */
		/*
		 * In Eclipse IDE/class run as Maven test
		 * file:/home/zhikai/Desktop/workspace/lightjsd/target/classes/res1 null
		 * file:/home/zhikai/Desktop/workspace/lightjsd/target/classes/res3
		 */
	}

	// TODO 20150127 @Test, so far did not find a way to do this
	public void testCallMainGetResourceURL() {

	}

	protected void readAssertContent(String resName, String expectedContent) {
		URL urlRes1 = new ExampleGetResources().printFindResource(resName);
		InputStream fis = null;
		String oneline  =null;
		try {
			fis = urlRes1.openStream();
			
			BufferedReader br = 
					new BufferedReader(
							new InputStreamReader(fis));
			oneline = br.readLine();
			
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
		assertNotNull (oneline);
		assertEquals(oneline,expectedContent);
	}

	@Test
	public void testReadResourcesContent() {
		 readAssertContent(fileRes1, expectedContent1);
		 //readAssertContent(fileRes2, expectedContent1);
		 readAssertContent(fileRes3, expectedContent3);
		 readAssertContent(fileRes4, expectedContent4);

	}

}
