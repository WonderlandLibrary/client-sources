package com.darkcart.xdolf.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.darkcart.xdolf.Client;

import net.minecraft.util.Util;

public class Splashes {

	public static String[] getSplashes() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (GeneralSecurityException e) {
		}
		// Now you can access an https URL without having the certificate in the
		// truststore
		try {
			URL url = new URL("https://darkcart.co/xdolf/splashes.txt");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

			StringBuilder stringBuilder = new StringBuilder();

			String inputLine;
			while ((inputLine = bufferedReader.readLine()) != null) {
				stringBuilder.append(inputLine);
				stringBuilder.append(System.lineSeparator());
			}

			bufferedReader.close();
			
			Util.EnumOS util$enumos = Util.getOSType();
			if (util$enumos != Util.EnumOS.WINDOWS)
				return stringBuilder.toString().substring(0, stringBuilder.toString().length()).split("\n");
			
			return stringBuilder.toString().substring(0, stringBuilder.toString().length()).split("\r\n");
		} catch (Exception e) {
		}
		return new String[] { "missingno" };
	}
}
