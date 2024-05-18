package com.darkcart.xdolf.util;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.FileUtils;

import com.darkcart.xdolf.Client;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.gui.XuluUpdateGui;

public class XdolfUpdater {

	public static String getRemoteVersion() throws IOException {
		TrustManager[] trustAllCerts = new TrustManager[] { 
			    new X509TrustManager() {     
			        public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
			            return new X509Certificate[0];
			        } 
			        public void checkClientTrusted( 
			            java.security.cert.X509Certificate[] certs, String authType) {
			            } 
			        public void checkServerTrusted( 
			            java.security.cert.X509Certificate[] certs, String authType) {
			        }
			    } 
			}; 

			// Install the all-trusting trust manager
			try {
			    SSLContext sc = SSLContext.getInstance("SSL"); 
			    sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
			    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			} catch (GeneralSecurityException e) {
			} 
			// Now you can access an https URL without having the certificate in the truststore
			try { 
			    URL url = new URL(""); 
			    BufferedReader bufferedReader = new BufferedReader(
			            new InputStreamReader(url.openStream()));

			    StringBuilder stringBuilder = new StringBuilder();

			    String inputLine;
			    while ((inputLine = bufferedReader.readLine()) != null)
			    {
			        stringBuilder.append(inputLine);
			        stringBuilder.append(System.lineSeparator());
			    }

			    bufferedReader.close();
			    return stringBuilder.toString().trim();
			} catch (MalformedURLException e) {
			}
			return Client.CLIENT_VERSION; 
	}

	public static void downloadFile() {
		TrustManager[] trustAllCerts = new TrustManager[] { 
				new X509TrustManager() {     
					public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
						return new X509Certificate[0];
			        } 
			        public void checkClientTrusted( 
			            java.security.cert.X509Certificate[] certs, String authType) {
			            } 
			        public void checkServerTrusted( 
			            java.security.cert.X509Certificate[] certs, String authType) {
			        }
			    } 
			}; 

			// Install the all-trusting trust manager
			try {
			    SSLContext sc = SSLContext.getInstance("SSL"); 
			    sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
			    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			} catch (GeneralSecurityException e) {} 
			try {
				FileUtils.copyURLToFile(new URL("https://darkcart.co/xdolf/xdolf.zip"),
						new File(Wrapper.getAppDir("minecraft") + File.separator + "versions" + File.separator + "xdolf.zip"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			XuluUpdateGui.downloadedUpdate = true;
			try {
				Desktop.getDesktop().open(new File(Wrapper.getAppDir("minecraft") + File.separator + "versions"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
