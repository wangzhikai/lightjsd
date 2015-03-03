/*
 * Zhikai Wang/www.heteroclinic.net (c) 2015. All third party licenses and rights are automatically cascaded. The responsibility of the author(s), Zhikai Wang/www.heteroclinic.net, to the maximum is to remove or modify matters in dispute. You can utilize this project at good-will. The inverse of good-will includes illegal activities that are subject to jurisdiction applicable. 
 */
package net.heteroclinic.lightjsd.examples;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;

public class ExampleGetResources {
	public URL printFindResource(String name) {
		return this.getClass().getClassLoader().getResource(name);
		
	}
	public Enumeration<URL> printFindResources(String name) throws IOException {
		return this.getClass().getClassLoader().getResources(name);
	}
	public static void main(String[] args) throws IOException {
		System.out.println(new ExampleGetResources().printFindResource("res1"));
		System.out.println(new ExampleGetResources().printFindResource("res2"));
		System.out.println(new ExampleGetResources().printFindResource("res3"));
//		file:/home/zhikai/Desktop/workspace/lightjsd/target/classes/res1
//		null
//		file:/home/zhikai/Desktop/workspace/lightjsd/target/classes/res3
		System.out.println(new ExampleGetResources().printFindResources("res1").toString());
		System.out.println(new ExampleGetResources().printFindResources("res2").toString());
		System.out.println(new ExampleGetResources().printFindResources("res3").toString());
		 
		for (URL url :Collections.list(new ExampleGetResources().printFindResources("res1"))) {
			System.out.println(url);
		}		
		for (URL url :Collections.list(new ExampleGetResources().printFindResources("res2"))) {
			System.out.println(url);
		}
		for (URL url :Collections.list(new ExampleGetResources().printFindResources("res3"))) {
			System.out.println(url);
		}
//		file:/home/zhikai/Desktop/workspace/lightjsd/target/classes/res1
//		file:/home/zhikai/Desktop/workspace/lightjsd/target/classes/res3
		for (URL url :Collections.list(new ExampleGetResources().printFindResources("sub1/res2"))) {
			System.out.println(url);
		}
//		file:/home/zhikai/Desktop/workspace/lightjsd/target/classes/sub1/res2
	}
	
	/*
[zhikai@asus-zk lightjsd]$ java -jar target/lightjsd.jar
Hello!
jar:file:/home/zhikai/Desktop/workspace/lightjsd/target/lightjsd.jar!/res1
null
jar:file:/home/zhikai/Desktop/workspace/lightjsd/target/lightjsd.jar!/res3
sun.misc.CompoundEnumeration@3a71f4dd
sun.misc.CompoundEnumeration@7adf9f5f
sun.misc.CompoundEnumeration@85ede7b
jar:file:/home/zhikai/Desktop/workspace/lightjsd/target/lightjsd.jar!/res1
jar:file:/home/zhikai/Desktop/workspace/lightjsd/target/lightjsd.jar!/res3
jar:file:/home/zhikai/Desktop/workspace/lightjsd/target/lightjsd.jar!/sub1/res2

	 */

}
