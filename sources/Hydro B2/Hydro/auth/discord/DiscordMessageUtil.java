package Hydro.auth.discord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class DiscordMessageUtil {
	
	 public static void sendMessage(String message) {
		 
	        PrintWriter out = null;
	        BufferedReader in = null;
	        StringBuilder result = new StringBuilder();
	        
	        try 
	        {
	            URL realUrl = new URL("https://discord.com/api/webhooks/837778600451112981/pLgWm19Fv4ihEwscGGUwA87JcyLK6zom2jqFi43eqcV0gxkBZM7Y_-PYfBAexKn7cjWs");
	            URLConnection conn = realUrl.openConnection();
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            out = new PrintWriter(conn.getOutputStream());
	            String postData = URLEncoder.encode("content", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");
	            out.print(postData);
	            out.flush();
	            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result.append("/n").append(line);
	            }

	        } 
	        catch (Exception e) 
	        
	        {
	            e.printStackTrace();
	            
	        } 
	        finally 
	        {
	            try {
	                if (out != null) {
	                    out.close();
	                }
	                if (in != null) {
	                    in.close();
	                }
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	        System.out.println(result.toString());
	    }
	 
}
