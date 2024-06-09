package lunadevs.luna.generator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class AltGen {
	
	  public static String username;
	  public static String password;

	public static void generate(){
		try
	    {
	      URL url = new URL("http://lunaclient.pw/api/alt/alt.php");
	      URLConnection connection = url.openConnection();
	      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	      String line;
	      while ((line = reader.readLine()) != null)
	      {
	    	  String[] split = line.split(":");
	    	  username = split[0];
	    	  password = split[1];
	      }
	      reader.close();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	  }

}
