/*
 * Zhikai Wang/www.heteroclinic.net (c) 2015. All third party licenses and rights are automatically cascaded. The responsibility of the author(s), Zhikai Wang/www.heteroclinic.net, to the maximum is to remove or modify matters in dispute. You can utilize this project at good-will. The inverse of good-will includes illegal activities that are subject to jurisdiction applicable. 
 */
package net.heteroclinic.lightjsd.examples;

public class ExampleResourceFinder {
	public boolean findAFile (String fn) {
		String keyfn = ClassLoader.getSystemResource(fn)
				.getFile();
		this.getClass().getResourceAsStream("/"+fn);	

		// if found : return /home/zhikai/Desktop/workspace/lightjsd/target/classes/resource.txt
		// else null
		return null == keyfn ? true:false;
		
		//TODO refer to ClassLoader
		//public URL getResource(String name) and getResources
		
/*
		InputStream fis = null;

		try {
			fis = new FileInputStream(keyfn);
		} catch (Exception e) {
			// eat it
			//e.printStackTrace();
		}
		if (fis == null) {
			fis = this.getClass().getResourceAsStream("/"+fn);	
			if (fis == null) {
				System.out.println("this.getClass().getResourceAsStream(fn) == null");
			} else {
				System.out.println("this.getClass().getResourceAsStream(fn)");
			}
		}		
		else {
			System.out.println("Get resource file by ClassLoader.getSystemResource(fn)");
			//ks.load(fis, password);
		}
		return null == fis ? true:false;
		
*/		
	}

	public static void main(String[] args) {
		String fn = "resource.txt";
		ExampleResourceFinder rf = new ExampleResourceFinder();
		rf.findAFile(fn);
		
		String fn2 = "not_exist.txt";
		rf.findAFile(fn2);
		
//		String keyfn = ClassLoader.getSystemResource("resource.txt")
//				.getFile();
//		FileInputStream fis = null;
//
//		try {
//			fis = new FileInputStream(keyfn);
//		} catch (Exception e) {
//			// eat it
//			// e.printStackTrace();
//		}
//		if (fis == null)
//			fis = this.getClass().getResourceAsStream("/resource.txt");
//		else {
//			ks.load(fis, password);
//		}
//
	}

}
