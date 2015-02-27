package net.heteroclinic.lightjsd.examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLSession;
import javax.security.cert.CertificateException;
import javax.swing.text.html.HTMLDocument.Iterator;

public class ExampleURLReader {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException, java.security.cert.CertificateException {

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
		
		// List trusted certificates
		//http://stackoverflow.com/questions/3508050/how-can-i-get-a-list-of-trusted-root-certificates-in-java
		{
		     try {
		            // Load the JDK's cacerts keystore file
		            String filename = System.getProperty("java.home") + "/lib/security/cacerts".replace('/', File.separatorChar);
		            FileInputStream is = new FileInputStream(filename);
		            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		            String password = "changeit";
		            keystore.load(is, password.toCharArray());

		            // This class retrieves the most-trusted CAs from the keystore
		            PKIXParameters params = new PKIXParameters(keystore);

		            // Get the set of trust anchors, which contain the most-trusted CA certificates
		            java.util.Iterator<TrustAnchor> it = params.getTrustAnchors().iterator();
		            int certsCount = 0;
		            while( it.hasNext() ) {
		            	certsCount++;
		            	System.out.println("-----------------------");
		                TrustAnchor ta = (TrustAnchor)it.next();
		                // null ? System.out.println("ta.getCAName() = "+ ta.getCAName());
		                
		                // Get certificate
		                //X509CertImpl
		                X509Certificate cert = ta.getTrustedCert();
		                String certString = cert.toString();
		                String result = "";
		                //System.out.println(cert);
		                Pattern pattern = Pattern.compile("(?s)Subject[^\\n]*\\n");
		                Matcher matcher = pattern.matcher(certString);
		                if (matcher.find()) {
		                	result += certString.substring(matcher.start(), matcher.end());
		                }
		                
		                pattern = Pattern.compile("(?s)Issuer[^\\n]*\\n");
		                matcher = pattern.matcher(certString);
		                if (matcher.find()) {
		                	result += certString.substring(matcher.start(), matcher.end());
		                }
		                
		                System.out.println(result);
		            }
		            System.out.println("certsCount = "+certsCount ); //certsCount = 78 20150227
		        } catch (KeyStoreException e) {
		        } catch (NoSuchAlgorithmException e) {
		        } catch (InvalidAlgorithmParameterException e) {
		        } catch (IOException e) {
		        } 
	           

		     /* Default hotspot jdk8 installed certificates
-----------------------
Subject: OU=Security Communication RootCA1, O=SECOM Trust.net, C=JP
Issuer: OU=Security Communication RootCA1, O=SECOM Trust.net, C=JP

-----------------------
Subject: CN=Entrust Root Certification Authority, OU="(c) 2006 Entrust, Inc.", OU=www.entrust.net/CPS is incorporated by reference, O="Entrust, Inc.", C=US
Issuer: CN=Entrust Root Certification Authority, OU="(c) 2006 Entrust, Inc.", OU=www.entrust.net/CPS is incorporated by reference, O="Entrust, Inc.", C=US

-----------------------
Subject: CN=VeriSign Class 3 Public Primary Certification Authority - G5, OU="(c) 2006 VeriSign, Inc. - For authorized use only", OU=VeriSign Trust Network, O="VeriSign, Inc.", C=US
Issuer: CN=VeriSign Class 3 Public Primary Certification Authority - G5, OU="(c) 2006 VeriSign, Inc. - For authorized use only", OU=VeriSign Trust Network, O="VeriSign, Inc.", C=US

-----------------------
Subject: CN=Equifax Secure eBusiness CA-1, O=Equifax Secure Inc., C=US
Issuer: CN=Equifax Secure eBusiness CA-1, O=Equifax Secure Inc., C=US

-----------------------
Subject: EMAILADDRESS=personal-freemail@thawte.com, CN=Thawte Personal Freemail CA, OU=Certification Services Division, O=Thawte Consulting, L=Cape Town, ST=Western Cape, C=ZA
Issuer: EMAILADDRESS=personal-freemail@thawte.com, CN=Thawte Personal Freemail CA, OU=Certification Services Division, O=Thawte Consulting, L=Cape Town, ST=Western Cape, C=ZA

-----------------------
Subject: CN=Baltimore CyberTrust Code Signing Root, OU=CyberTrust, O=Baltimore, C=IE
Issuer: CN=Baltimore CyberTrust Code Signing Root, OU=CyberTrust, O=Baltimore, C=IE

-----------------------
Subject: CN=QuoVadis Root Certification Authority, OU=Root Certification Authority, O=QuoVadis Limited, C=BM
Issuer: CN=QuoVadis Root Certification Authority, OU=Root Certification Authority, O=QuoVadis Limited, C=BM

-----------------------
Subject: CN=DigiCert Assured ID Root CA, OU=www.digicert.com, O=DigiCert Inc, C=US
Issuer: CN=DigiCert Assured ID Root CA, OU=www.digicert.com, O=DigiCert Inc, C=US

-----------------------
Subject: CN=Sonera Class2 CA, O=Sonera, C=FI
Issuer: CN=Sonera Class2 CA, O=Sonera, C=FI

-----------------------
Subject: CN=Entrust.net Certification Authority (2048), OU=(c) 1999 Entrust.net Limited, OU=www.entrust.net/CPS_2048 incorp. by ref. (limits liab.), O=Entrust.net
Issuer: CN=Entrust.net Certification Authority (2048), OU=(c) 1999 Entrust.net Limited, OU=www.entrust.net/CPS_2048 incorp. by ref. (limits liab.), O=Entrust.net

-----------------------
Subject: CN=VeriSign Class 3 Public Primary Certification Authority - G4, OU="(c) 2007 VeriSign, Inc. - For authorized use only", OU=VeriSign Trust Network, O="VeriSign, Inc.", C=US
Issuer: CN=VeriSign Class 3 Public Primary Certification Authority - G4, OU="(c) 2007 VeriSign, Inc. - For authorized use only", OU=VeriSign Trust Network, O="VeriSign, Inc.", C=US

-----------------------
Subject: OU=Class 3 Public Primary Certification Authority, O="VeriSign, Inc.", C=US
Issuer: OU=Class 3 Public Primary Certification Authority, O="VeriSign, Inc.", C=US

-----------------------
Subject: CN=Thawte Timestamping CA, OU=Thawte Certification, O=Thawte, L=Durbanville, ST=Western Cape, C=ZA
Issuer: CN=Thawte Timestamping CA, OU=Thawte Certification, O=Thawte, L=Durbanville, ST=Western Cape, C=ZA

-----------------------
Subject: EMAILADDRESS=server-certs@thawte.com, CN=Thawte Server CA, OU=Certification Services Division, O=Thawte Consulting cc, L=Cape Town, ST=Western Cape, C=ZA
Issuer: EMAILADDRESS=server-certs@thawte.com, CN=Thawte Server CA, OU=Certification Services Division, O=Thawte Consulting cc, L=Cape Town, ST=Western Cape, C=ZA

-----------------------
Subject: CN=America Online Root Certification Authority 2, O=America Online Inc., C=US
Issuer: CN=America Online Root Certification Authority 2, O=America Online Inc., C=US

-----------------------
Subject: CN=Deutsche Telekom Root CA 2, OU=T-TeleSec Trust Center, O=Deutsche Telekom AG, C=DE
Issuer: CN=Deutsche Telekom Root CA 2, OU=T-TeleSec Trust Center, O=Deutsche Telekom AG, C=DE

-----------------------
Subject: CN=GlobalSign, O=GlobalSign, OU=GlobalSign Root CA - R3
Issuer: CN=GlobalSign, O=GlobalSign, OU=GlobalSign Root CA - R3

-----------------------
Subject: CN=UTN-USERFirst-Client Authentication and Email, OU=http://www.usertrust.com, O=The USERTRUST Network, L=Salt Lake City, ST=UT, C=US
Issuer: CN=UTN-USERFirst-Client Authentication and Email, OU=http://www.usertrust.com, O=The USERTRUST Network, L=Salt Lake City, ST=UT, C=US

-----------------------
Subject: OU=Starfield Class 2 Certification Authority, O="Starfield Technologies, Inc.", C=US
Issuer: OU=Starfield Class 2 Certification Authority, O="Starfield Technologies, Inc.", C=US

-----------------------
Subject: CN=AddTrust External CA Root, OU=AddTrust External TTP Network, O=AddTrust AB, C=SE
Issuer: CN=AddTrust External CA Root, OU=AddTrust External TTP Network, O=AddTrust AB, C=SE

-----------------------
Subject: CN=QuoVadis Root CA 3, O=QuoVadis Limited, C=BM
Issuer: CN=QuoVadis Root CA 3, O=QuoVadis Limited, C=BM

-----------------------
Subject: CN=TC TrustCenter Class 4 CA II, OU=TC TrustCenter Class 4 CA, O=TC TrustCenter GmbH, C=DE
Issuer: CN=TC TrustCenter Class 4 CA II, OU=TC TrustCenter Class 4 CA, O=TC TrustCenter GmbH, C=DE

-----------------------
Subject: CN=DigiCert Global Root CA, OU=www.digicert.com, O=DigiCert Inc, C=US
Issuer: CN=DigiCert Global Root CA, OU=www.digicert.com, O=DigiCert Inc, C=US

-----------------------
Subject: CN=T-TeleSec GlobalRoot Class 2, OU=T-Systems Trust Center, O=T-Systems Enterprise Services GmbH, C=DE
Issuer: CN=T-TeleSec GlobalRoot Class 2, OU=T-Systems Trust Center, O=T-Systems Enterprise Services GmbH, C=DE

-----------------------
Subject: CN=GTE CyberTrust Global Root, OU="GTE CyberTrust Solutions, Inc.", O=GTE Corporation, C=US
Issuer: CN=GTE CyberTrust Global Root, OU="GTE CyberTrust Solutions, Inc.", O=GTE Corporation, C=US

-----------------------
Subject: CN=UTN-USERFirst-Hardware, OU=http://www.usertrust.com, O=The USERTRUST Network, L=Salt Lake City, ST=UT, C=US
Issuer: CN=UTN-USERFirst-Hardware, OU=http://www.usertrust.com, O=The USERTRUST Network, L=Salt Lake City, ST=UT, C=US

-----------------------
Subject: CN=GeoTrust Global CA, O=GeoTrust Inc., C=US
Issuer: CN=GeoTrust Global CA, O=GeoTrust Inc., C=US

-----------------------
Subject: CN=SwissSign Gold CA - G2, O=SwissSign AG, C=CH
Issuer: CN=SwissSign Gold CA - G2, O=SwissSign AG, C=CH

-----------------------
Subject: CN=thawte Primary Root CA - G2, OU="(c) 2007 thawte, Inc. - For authorized use only", O="thawte, Inc.", C=US
Issuer: CN=thawte Primary Root CA - G2, OU="(c) 2007 thawte, Inc. - For authorized use only", O="thawte, Inc.", C=US

-----------------------
Subject: CN=KEYNECTIS ROOT CA, OU=ROOT, O=KEYNECTIS, C=FR
Issuer: CN=KEYNECTIS ROOT CA, OU=ROOT, O=KEYNECTIS, C=FR

-----------------------
Subject: CN=Equifax Secure Global eBusiness CA-1, O=Equifax Secure Inc., C=US
Issuer: CN=Equifax Secure Global eBusiness CA-1, O=Equifax Secure Inc., C=US

-----------------------
Subject: OU=VeriSign Trust Network, OU="(c) 1998 VeriSign, Inc. - For authorized use only", OU=Class 1 Public Primary Certification Authority - G2, O="VeriSign, Inc.", C=US
Issuer: OU=VeriSign Trust Network, OU="(c) 1998 VeriSign, Inc. - For authorized use only", OU=Class 1 Public Primary Certification Authority - G2, O="VeriSign, Inc.", C=US

-----------------------
Subject: CN=Baltimore CyberTrust Root, OU=CyberTrust, O=Baltimore, C=IE
Issuer: CN=Baltimore CyberTrust Root, OU=CyberTrust, O=Baltimore, C=IE

-----------------------
Subject: CN=VeriSign Universal Root Certification Authority, OU="(c) 2008 VeriSign, Inc. - For authorized use only", OU=VeriSign Trust Network, O="VeriSign, Inc.", C=US
Issuer: CN=VeriSign Universal Root Certification Authority, OU="(c) 2008 VeriSign, Inc. - For authorized use only", OU=VeriSign Trust Network, O="VeriSign, Inc.", C=US

-----------------------
Subject: CN=Class 2 Primary CA, O=Certplus, C=FR
Issuer: CN=Class 2 Primary CA, O=Certplus, C=FR

-----------------------
Subject: CN=AddTrust Class 1 CA Root, OU=AddTrust TTP Network, O=AddTrust AB, C=SE
Issuer: CN=AddTrust Class 1 CA Root, OU=AddTrust TTP Network, O=AddTrust AB, C=SE

-----------------------
Subject: CN=Class 3P Primary CA, O=Certplus, C=FR
Issuer: CN=Class 3P Primary CA, O=Certplus, C=FR

-----------------------
Subject: OU=VeriSign Trust Network, OU="(c) 1998 VeriSign, Inc. - For authorized use only", OU=Class 2 Public Primary Certification Authority - G2, O="VeriSign, Inc.", C=US
Issuer: OU=VeriSign Trust Network, OU="(c) 1998 VeriSign, Inc. - For authorized use only", OU=Class 2 Public Primary Certification Authority - G2, O="VeriSign, Inc.", C=US

-----------------------
Subject: OU=Equifax Secure Certificate Authority, O=Equifax, C=US
Issuer: OU=Equifax Secure Certificate Authority, O=Equifax, C=US

-----------------------
Subject: CN=TC TrustCenter Class 2 CA II, OU=TC TrustCenter Class 2 CA, O=TC TrustCenter GmbH, C=DE
Issuer: CN=TC TrustCenter Class 2 CA II, OU=TC TrustCenter Class 2 CA, O=TC TrustCenter GmbH, C=DE

-----------------------
Subject: OU=Class 1 Public Primary Certification Authority, O="VeriSign, Inc.", C=US
Issuer: OU=Class 1 Public Primary Certification Authority, O="VeriSign, Inc.", C=US

-----------------------
Subject: CN=thawte Primary Root CA, OU="(c) 2006 thawte, Inc. - For authorized use only", OU=Certification Services Division, O="thawte, Inc.", C=US
Issuer: CN=thawte Primary Root CA, OU="(c) 2006 thawte, Inc. - For authorized use only", OU=Certification Services Division, O="thawte, Inc.", C=US

-----------------------
Subject: CN=UTN - DATACorp SGC, OU=http://www.usertrust.com, O=The USERTRUST Network, L=Salt Lake City, ST=UT, C=US
Issuer: CN=UTN - DATACorp SGC, OU=http://www.usertrust.com, O=The USERTRUST Network, L=Salt Lake City, ST=UT, C=US

-----------------------
Subject: CN=Chambers of Commerce Root, OU=http://www.chambersign.org, O=AC Camerfirma SA CIF A82743287, C=EU
Issuer: CN=Chambers of Commerce Root, OU=http://www.chambersign.org, O=AC Camerfirma SA CIF A82743287, C=EU

-----------------------
Subject: OU=Go Daddy Class 2 Certification Authority, O="The Go Daddy Group, Inc.", C=US
Issuer: OU=Go Daddy Class 2 Certification Authority, O="The Go Daddy Group, Inc.", C=US

-----------------------
Subject: CN=DigiCert High Assurance EV Root CA, OU=www.digicert.com, O=DigiCert Inc, C=US
Issuer: CN=DigiCert High Assurance EV Root CA, OU=www.digicert.com, O=DigiCert Inc, C=US

-----------------------
Subject: CN=Global Chambersign Root - 2008, O=AC Camerfirma S.A., SERIALNUMBER=A82743287, L=Madrid (see current address at www.camerfirma.com/address), C=EU
Issuer: CN=Global Chambersign Root - 2008, O=AC Camerfirma S.A., SERIALNUMBER=A82743287, L=Madrid (see current address at www.camerfirma.com/address), C=EU

-----------------------
Subject: CN=Entrust.net Secure Server Certification Authority, OU=(c) 1999 Entrust.net Limited, OU=www.entrust.net/CPS incorp. by ref. (limits liab.), O=Entrust.net, C=US
Issuer: CN=Entrust.net Secure Server Certification Authority, OU=(c) 1999 Entrust.net Limited, OU=www.entrust.net/CPS incorp. by ref. (limits liab.), O=Entrust.net, C=US

-----------------------
Subject: CN=QuoVadis Root CA 2, O=QuoVadis Limited, C=BM
Issuer: CN=QuoVadis Root CA 2, O=QuoVadis Limited, C=BM

-----------------------
Subject: CN=GlobalSign, O=GlobalSign, OU=GlobalSign Root CA - R2
Issuer: CN=GlobalSign, O=GlobalSign, OU=GlobalSign Root CA - R2

-----------------------
Subject: CN=GeoTrust Primary Certification Authority, O=GeoTrust Inc., C=US
Issuer: CN=GeoTrust Primary Certification Authority, O=GeoTrust Inc., C=US

-----------------------
Subject: CN=VeriSign Class 2 Public Primary Certification Authority - G3, OU="(c) 1999 VeriSign, Inc. - For authorized use only", OU=VeriSign Trust Network, O="VeriSign, Inc.", C=US
Issuer: CN=VeriSign Class 2 Public Primary Certification Authority - G3, OU="(c) 1999 VeriSign, Inc. - For authorized use only", OU=VeriSign Trust Network, O="VeriSign, Inc.", C=US

-----------------------
Subject: OU=Security Communication RootCA2, O="SECOM Trust Systems CO.,LTD.", C=JP
Issuer: OU=Security Communication RootCA2, O="SECOM Trust Systems CO.,LTD.", C=JP

-----------------------
Subject: CN=America Online Root Certification Authority 1, O=America Online Inc., C=US
Issuer: CN=America Online Root Certification Authority 1, O=America Online Inc., C=US

-----------------------
Subject: OU=VeriSign Trust Network, OU="(c) 1998 VeriSign, Inc. - For authorized use only", OU=Class 3 Public Primary Certification Authority - G2, O="VeriSign, Inc.", C=US
Issuer: OU=VeriSign Trust Network, OU="(c) 1998 VeriSign, Inc. - For authorized use only", OU=Class 3 Public Primary Certification Authority - G2, O="VeriSign, Inc.", C=US

-----------------------
Subject: CN=Chambers of Commerce Root - 2008, O=AC Camerfirma S.A., SERIALNUMBER=A82743287, L=Madrid (see current address at www.camerfirma.com/address), C=EU
Issuer: CN=Chambers of Commerce Root - 2008, O=AC Camerfirma S.A., SERIALNUMBER=A82743287, L=Madrid (see current address at www.camerfirma.com/address), C=EU

-----------------------
Subject: CN=GeoTrust Primary Certification Authority - G2, OU=(c) 2007 GeoTrust Inc. - For authorized use only, O=GeoTrust Inc., C=US
Issuer: CN=GeoTrust Primary Certification Authority - G2, OU=(c) 2007 GeoTrust Inc. - For authorized use only, O=GeoTrust Inc., C=US

-----------------------
Subject: CN=GlobalSign Root CA, OU=Root CA, O=GlobalSign nv-sa, C=BE
Issuer: CN=GlobalSign Root CA, OU=Root CA, O=GlobalSign nv-sa, C=BE

-----------------------
Subject: CN=VeriSign Class 3 Public Primary Certification Authority - G3, OU="(c) 1999 VeriSign, Inc. - For authorized use only", OU=VeriSign Trust Network, O="VeriSign, Inc.", C=US
Issuer: CN=VeriSign Class 3 Public Primary Certification Authority - G3, OU="(c) 1999 VeriSign, Inc. - For authorized use only", OU=VeriSign Trust Network, O="VeriSign, Inc.", C=US

-----------------------
Subject: CN=TC TrustCenter Universal CA I, OU=TC TrustCenter Universal CA, O=TC TrustCenter GmbH, C=DE
Issuer: CN=TC TrustCenter Universal CA I, OU=TC TrustCenter Universal CA, O=TC TrustCenter GmbH, C=DE

-----------------------
Subject: CN=AAA Certificate Services, O=Comodo CA Limited, L=Salford, ST=Greater Manchester, C=GB
Issuer: CN=AAA Certificate Services, O=Comodo CA Limited, L=Salford, ST=Greater Manchester, C=GB

-----------------------
Subject: CN=UTN-USERFirst-Object, OU=http://www.usertrust.com, O=The USERTRUST Network, L=Salt Lake City, ST=UT, C=US
Issuer: CN=UTN-USERFirst-Object, OU=http://www.usertrust.com, O=The USERTRUST Network, L=Salt Lake City, ST=UT, C=US

-----------------------
Subject: OU=Security Communication EV RootCA1, O="SECOM Trust Systems CO.,LTD.", C=JP
Issuer: OU=Security Communication EV RootCA1, O="SECOM Trust Systems CO.,LTD.", C=JP

-----------------------
Subject: EMAILADDRESS=info@valicert.com, CN=http://www.valicert.com/, OU=ValiCert Class 2 Policy Validation Authority, O="ValiCert, Inc.", L=ValiCert Validation Network
Issuer: EMAILADDRESS=info@valicert.com, CN=http://www.valicert.com/, OU=ValiCert Class 2 Policy Validation Authority, O="ValiCert, Inc.", L=ValiCert Validation Network

-----------------------
Subject: CN=Certum Trusted Network CA, OU=Certum Certification Authority, O=Unizeto Technologies S.A., C=PL
Issuer: CN=Certum Trusted Network CA, OU=Certum Certification Authority, O=Unizeto Technologies S.A., C=PL

-----------------------
Subject: CN=GeoTrust Primary Certification Authority - G3, OU=(c) 2008 GeoTrust Inc. - For authorized use only, O=GeoTrust Inc., C=US
Issuer: CN=GeoTrust Primary Certification Authority - G3, OU=(c) 2008 GeoTrust Inc. - For authorized use only, O=GeoTrust Inc., C=US

-----------------------
Subject: CN=Entrust Root Certification Authority - G2, OU="(c) 2009 Entrust, Inc. - for authorized use only", OU=See www.entrust.net/legal-terms, O="Entrust, Inc.", C=US
Issuer: CN=Entrust Root Certification Authority - G2, OU="(c) 2009 Entrust, Inc. - for authorized use only", OU=See www.entrust.net/legal-terms, O="Entrust, Inc.", C=US

-----------------------
Subject: CN=thawte Primary Root CA - G3, OU="(c) 2008 thawte, Inc. - For authorized use only", OU=Certification Services Division, O="thawte, Inc.", C=US
Issuer: CN=thawte Primary Root CA - G3, OU="(c) 2008 thawte, Inc. - For authorized use only", OU=Certification Services Division, O="thawte, Inc.", C=US

-----------------------
Subject: CN=T-TeleSec GlobalRoot Class 3, OU=T-Systems Trust Center, O=T-Systems Enterprise Services GmbH, C=DE
Issuer: CN=T-TeleSec GlobalRoot Class 3, OU=T-Systems Trust Center, O=T-Systems Enterprise Services GmbH, C=DE

-----------------------
Subject: EMAILADDRESS=info@valicert.com, CN=http://www.valicert.com/, OU=ValiCert Class 1 Policy Validation Authority, O="ValiCert, Inc.", L=ValiCert Validation Network
Issuer: EMAILADDRESS=info@valicert.com, CN=http://www.valicert.com/, OU=ValiCert Class 1 Policy Validation Authority, O="ValiCert, Inc.", L=ValiCert Validation Network

-----------------------
Subject: CN=AddTrust Qualified CA Root, OU=AddTrust TTP Network, O=AddTrust AB, C=SE
Issuer: CN=AddTrust Qualified CA Root, OU=AddTrust TTP Network, O=AddTrust AB, C=SE

-----------------------
Subject: CN=SwissSign Silver CA - G2, O=SwissSign AG, C=CH
Issuer: CN=SwissSign Silver CA - G2, O=SwissSign AG, C=CH

-----------------------
Subject: EMAILADDRESS=premium-server@thawte.com, CN=Thawte Premium Server CA, OU=Certification Services Division, O=Thawte Consulting cc, L=Cape Town, ST=Western Cape, C=ZA
Issuer: EMAILADDRESS=premium-server@thawte.com, CN=Thawte Premium Server CA, OU=Certification Services Division, O=Thawte Consulting cc, L=Cape Town, ST=Western Cape, C=ZA

-----------------------
Subject: CN=Certum CA, O=Unizeto Sp. z o.o., C=PL
Issuer: CN=Certum CA, O=Unizeto Sp. z o.o., C=PL

-----------------------
Subject: CN=VeriSign Class 1 Public Primary Certification Authority - G3, OU="(c) 1999 VeriSign, Inc. - For authorized use only", OU=VeriSign Trust Network, O="VeriSign, Inc.", C=US
Issuer: CN=VeriSign Class 1 Public Primary Certification Authority - G3, OU="(c) 1999 VeriSign, Inc. - For authorized use only", OU=VeriSign Trust Network, O="VeriSign, Inc.", C=US

-----------------------
Subject: CN=SwissSign Platinum CA - G2, O=SwissSign AG, C=CH
Issuer: CN=SwissSign Platinum CA - G2, O=SwissSign AG, C=CH

-----------------------
Subject: CN=GeoTrust Universal CA, O=GeoTrust Inc., C=US
Issuer: CN=GeoTrust Universal CA, O=GeoTrust Inc., C=US

-----------------------
Subject: CN=Sonera Class1 CA, O=Sonera, C=FI
Issuer: CN=Sonera Class1 CA, O=Sonera, C=FI		      
		      */
	/* An example cert 	     
		     -----------------------
		     [
		     [
		       Version: V3
		       Subject: CN=GeoTrust Universal CA, O=GeoTrust Inc., C=US
		       Signature Algorithm: SHA1withRSA, OID = 1.2.840.113549.1.1.5

		       Key:  Sun RSA public key, 4096 bits
		       modulus: 677560903942283765615584502265054051892306912804012504139331854259010939583809653407117304304429097658109015700763089202433927852851427952305165981140037497611414487536945532499834078431088510182946270897576453298268646478465239662230924074941367276004230628658118092426650417353277959644361516072023209905005447336943923390517330609803446751512450587932674623877181587108132308562873565066291767923874601112612119097573260830778270169224532831913232479794329448362665253956651261281100912255435882110509277481911648330207228808471025165894412989781642837694422209515603741300337920596519925472365347159439404671564185614962643462824793309176204436399699580146883614693095903132392708010759498523046829278676035251297846097454270047757274467270854536762355772101142611270309479944360934727872251481186722447556739929411378079109593314520912722058590371847177544142826242428322486336162925357406815702930150512498822580434130443762664513127178657749598464331319443588139308377774365697212898381711005505731884412943555973327472303136124183678809017386359796967979411688165541750158834574592725198037569491343761628758409828931173285476098974770940376020434375505560678940632802907651613212606699229451451494077424062169693195892671867
		       public exponent: 65537
		       Validity: [From: Thu Mar 04 00:00:00 EST 2004,
		                    To: Sun Mar 04 00:00:00 EST 2029]
		       Issuer: CN=GeoTrust Universal CA, O=GeoTrust Inc., C=US
		       SerialNumber: [    01]

		     Certificate Extensions: 4
		     [1]: ObjectId: 2.5.29.35 Criticality=false
		     AuthorityKeyIdentifier [
		     KeyIdentifier [
		     0000: DA BB 2E AA B0 0C B8 88   26 51 74 5C 6D 03 D3 C0  ........&Qt\m...
		     0010: D8 8F 7A D6                                        ..z.
		     ]
		     ]

		     [2]: ObjectId: 2.5.29.19 Criticality=true
		     BasicConstraints:[
		       CA:true
		       PathLen:2147483647
		     ]

		     [3]: ObjectId: 2.5.29.15 Criticality=true
		     KeyUsage [
		       DigitalSignature
		       Key_CertSign
		       Crl_Sign
		     ]

		     [4]: ObjectId: 2.5.29.14 Criticality=false
		     SubjectKeyIdentifier [
		     KeyIdentifier [
		     0000: DA BB 2E AA B0 0C B8 88   26 51 74 5C 6D 03 D3 C0  ........&Qt\m...
		     0010: D8 8F 7A D6                                        ..z.
		     ]
		     ]

		     ]
		       Algorithm: [SHA1withRSA]
		       Signature:
		     0000: 31 78 E6 C7 B5 DF B8 94   40 C9 71 C4 A8 35 EC 46  1x......@.q..5.F
		     0010: 1D C2 85 F3 28 58 86 B0   0B FC 8E B2 39 8F 44 55  ....(X......9.DU
		     0020: AB 64 84 5C 69 A9 D0 9A   38 3C FA E5 1F 35 E5 44  .d.\i...8<...5.D
		     0030: E3 80 79 94 68 A4 BB C4   9F 3D E1 34 CD 30 46 8B  ..y.h....=.4.0F.
		     0040: 54 2B 95 A5 EF F7 3F 99   84 FD 35 E6 CF 31 C6 DC  T+....?...5..1..
		     0050: 6A BF A7 D7 23 08 E1 98   5E C3 5A 08 76 A9 A6 AF  j...#...^.Z.v...
		     0060: 77 2F B7 60 BD 44 46 6A   EF 97 FF 73 95 C1 8E E8  w/.`.DFj...s....
		     0070: 93 FB FD 31 B7 EC 57 11   11 45 9B 30 F1 1A 88 39  ...1..W..E.0...9
		     0080: C1 4F 3C A7 00 D5 C7 FC   AB 6D 80 22 70 A5 0C E0  .O<......m."p...
		     0090: 5D 04 29 02 FB CB A0 91   D1 7C D6 C3 7E 50 D5 9D  ].)..........P..
		     00A0: 58 BE 41 38 EB B9 75 3C   15 D9 9B C9 4A 83 59 C0  X.A8..u<....J.Y.
		     00B0: DA 53 FD 33 BB 36 18 9B   85 0F 15 DD EE 2D AC 76  .S.3.6.......-.v
		     00C0: 93 B9 D9 01 8D 48 10 A8   FB F5 38 86 F1 DB 0A C6  .....H....8.....
		     00D0: BD 84 A3 23 41 DE D6 77   6F 85 D4 85 1C 50 E0 AE  ...#A..wo....P..
		     00E0: 51 8A BA 8D 3E 76 E2 B9   CA 27 F2 5F 9F EF 6E 59  Q...>v...'._..nY
		     00F0: 0D 06 D8 2B 17 A4 D2 7C   6B BB 5F 14 1A 48 8F 1A  ...+....k._..H..
		     0100: 4C E7 B3 47 1C 8E 4C 45   2B 20 EE 48 DF E7 DD 09  L..G..LE+ .H....
		     0110: 8E 18 A8 DA 40 8D 92 26   11 53 61 73 5D EB BD E7  ....@..&.Sas]...
		     0120: C4 4D 29 37 61 EB AC 39   2D 67 2E 16 D6 F5 00 83  .M)7a..9-g......
		     0130: 85 A1 CC 7F 76 C4 7D E4   B7 4B 66 EF 03 45 60 69  ....v....Kf..E`i
		     0140: B6 0C 52 96 92 84 5E A6   A3 B5 A4 3E 2B D9 CC D8  ..R...^....>+...
		     0150: 1B 47 AA F2 44 DA 4F F9   03 E8 F0 14 CB 3F F3 83  .G..D.O......?..
		     0160: DE D0 C1 54 E3 B7 E8 0A   37 4D 8B 20 59 03 30 19  ...T....7M. Y.0.
		     0170: A1 2C C8 BD 11 1F DF AE   C9 4A C5 F3 27 66 66 86  .,.......J..'ff.
		     0180: AC 68 91 FF D9 E6 53 1C   0F 8B 5C 69 65 0A 26 C8  .h....S...\ie.&.
		     0190: 1E 34 C3 5D 51 7B D7 A9   9C 06 A1 36 DD D5 89 94  .4.]Q......6....
		     01A0: BC D9 E4 2D 0C 5E 09 6C   08 97 7C A3 3D 7C 93 FF  ...-.^.l....=...
		     01B0: 3F A1 14 A7 CF B5 5D EB   DB DB 1C C4 76 DF 88 B9  ?.....].....v...
		     01C0: BD 45 05 95 1B AE FC 46   6A 4C AF 48 E3 CE AE 0F  .E.....FjL.H....
		     01D0: D2 7E EB E6 6C 9C 4F 81   6A 7A 64 AC BB 3E D5 E7  ....l.O.jzd..>..
		     01E0: CB 76 2E C5 A7 48 C1 5C   90 0F CB C8 3F FA E6 32  .v...H.\....?..2
		     01F0: E1 8D 1B 6F A4 E6 8E D8   F9 29 48 8A CE 73 FE 2C  ...o.....)H..s.,

		     ]
		     -----------------------
		     */
		     
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
//	        // Create all-trusting host name verifier
//	        HostnameVerifier allHostsValid = new HostnameVerifier() {
//	            public boolean verify(String hostname, SSLSession session) {
//	                return true;
//	            }
//	        };
			
			
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
