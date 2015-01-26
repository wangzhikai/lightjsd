package net.heteroclinic.lightjsd.test;

import java.net.URL;

import net.heteroclinic.lightjsd.examples.ExampleGetResources;

import org.junit.Test;

import junit.framework.TestCase;

public class TestGetResources extends TestCase {
	@Test
	public void testGetResourceURL () {
		String fileRes1 = "res1";
		String fileRes2 = "res2";
		String fileRes3 = "res3";
		URL urlRes1 = new ExampleGetResources().printFindResource(fileRes1);
		URL urlRes2 = new ExampleGetResources().printFindResource(fileRes2);
		URL urlRes3 = new ExampleGetResources().printFindResource(fileRes3);
		
		
		assertNotNull(urlRes1);
		assertNull(urlRes2);
		assertNotNull(urlRes3);
		System.out.println(urlRes1.toString());
		System.out.println(urlRes2==null?urlRes2:urlRes2.toString());
		System.out.println(urlRes3.toString());
		/* In Eclipse IDE/class run as Junit test
file:/home/zhikai/Desktop/workspace/lightjsd/target/classes/res1
null
file:/home/zhikai/Desktop/workspace/lightjsd/target/classes/res3
		 */
		/* In Eclipse IDE/class run as Maven test
file:/home/zhikai/Desktop/workspace/lightjsd/target/classes/res1
null
file:/home/zhikai/Desktop/workspace/lightjsd/target/classes/res3
		 */
	}
	@Test 
	public void testCallMainGetResourceURL () {
		
	}

}
