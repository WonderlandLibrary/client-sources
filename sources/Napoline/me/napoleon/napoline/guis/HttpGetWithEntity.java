package me.napoleon.napoline.guis;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public class HttpGetWithEntity extends HttpEntityEnclosingRequestBase {
	 public final static String METHOD_NAME = "GET";

	    @Override
	    public String getMethod() {
	        return METHOD_NAME;
	    }

}
