package net.heteroclinic.lightjsd.examples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLSession;

public class ExampleURLReader {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException {

		//Refer to http://docs.oracle.com/javase/tutorial/networking/urls/readingURL.html
		{
			//Good
	        URL oracle = new URL("http://www.oracle.com/");
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(oracle.openStream()));
	
	        String inputLine;
	        while ((inputLine = in.readLine()) != null)
	            System.out.println(inputLine);
	        in.close();
		}
		{
			//Good
	        URL oracle = new URL("https://www.oracle.com/");
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(oracle.openStream()));
	
	        String inputLine;
	        while ((inputLine = in.readLine()) != null)
	            System.out.println(inputLine);
	        in.close();
		}
		
		// TODO Case ignoring server certificates. You can configure an https server youself or comment/bypass this block.
		{
			// TODO refer to http://stackoverflow.com/questions/13022717/java-and-https-url-connection-without-downloading-certificate
	        // Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs,
						String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs,
						String authType) {
				}
			} };
			
	        // Install the all-trusting trust manager
	        final SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };
			
			
			// "https://c2rose/g/?p=PizzaShop;a=summary" is a server of mine using a certificate not from a official CA root
			// Refer to openjdkNotes.txt to get openjdk source; Use "openjdk8/jdk/src/" to attach source
	        URL oracle = new URL("https://c2rose/g/?p=PizzaShop;a=summary");
	        // The exception happens here
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(oracle.openStream()));
	
	        String inputLine;
	        while ((inputLine = in.readLine()) != null)
	            System.out.println(inputLine);
	        in.close();
			/* TODO handle this
			Exception in thread "main" javax.net.ssl.SSLHandshakeException: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
			at sun.security.ssl.Alerts.getSSLException(Alerts.java:192)
			at sun.security.ssl.SSLSocketImpl.fatal(SSLSocketImpl.java:1904)
			at sun.security.ssl.Handshaker.fatalSE(Handshaker.java:279)
			at sun.security.ssl.Handshaker.fatalSE(Handshaker.java:273)
			at sun.security.ssl.ClientHandshaker.serverCertificate(ClientHandshaker.java:1446)
			at sun.security.ssl.ClientHandshaker.processMessage(ClientHandshaker.java:209)
			at sun.security.ssl.Handshaker.processLoop(Handshaker.java:901)
			at sun.security.ssl.Handshaker.process_record(Handshaker.java:837)
			at sun.security.ssl.SSLSocketImpl.readRecord(SSLSocketImpl.java:1023)
			at sun.security.ssl.SSLSocketImpl.performInitialHandshake(SSLSocketImpl.java:1332)
			at sun.security.ssl.SSLSocketImpl.startHandshake(SSLSocketImpl.java:1359)
			at sun.security.ssl.SSLSocketImpl.startHandshake(SSLSocketImpl.java:1343)
			at sun.net.www.protocol.https.HttpsClient.afterConnect(HttpsClient.java:563)
			at sun.net.www.protocol.https.AbstractDelegateHttpsURLConnection.connect(AbstractDelegateHttpsURLConnection.java:185)
			at sun.net.www.protocol.http.HttpURLConnection.getInputStream(HttpURLConnection.java:1301)
			at sun.net.www.protocol.https.HttpsURLConnectionImpl.getInputStream(HttpsURLConnectionImpl.java:254)
			at java.net.URL.openStream(URL.java:1037)
			at net.heteroclinic.lightjsd.examples.ExampleURLReader.main(ExampleURLReader.java:15)
		Caused by: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
			at sun.security.validator.PKIXValidator.doBuild(PKIXValidator.java:385)
			at sun.security.validator.PKIXValidator.engineValidate(PKIXValidator.java:292)
			at sun.security.validator.Validator.validate(Validator.java:260)
			at sun.security.ssl.X509TrustManagerImpl.validate(X509TrustManagerImpl.java:326)
			at sun.security.ssl.X509TrustManagerImpl.checkTrusted(X509TrustManagerImpl.java:231)
			at sun.security.ssl.X509TrustManagerImpl.checkServerTrusted(X509TrustManagerImpl.java:126)
			at sun.security.ssl.ClientHandshaker.serverCertificate(ClientHandshaker.java:1428)
			... 13 more
		Caused by: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
			at sun.security.provider.certpath.SunCertPathBuilder.engineBuild(SunCertPathBuilder.java:196)
			at java.security.cert.CertPathBuilder.build(CertPathBuilder.java:268)
			at sun.security.validator.PKIXValidator.doBuild(PKIXValidator.java:380)
			... 19 more
			*/
		}
		// TODO Case load our own keystore and install our own trust certificate. 

	}

}
