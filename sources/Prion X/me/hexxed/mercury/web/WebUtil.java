package me.hexxed.mercury.web;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class WebUtil
{
  private static final String webURL = "http://serenityclient.grn.cc/";
  
  public WebUtil() {}
  
  private static ArrayList<WebData> parseDataSetsFromLatestList()
  {
    try
    {
      String url = "http://serenityclient.grn.cc/getrecentinfo.php";
      HttpURLConnection urlConnection = (HttpURLConnection)new URL(url).openConnection();
      urlConnection.setRequestMethod("GET");
      java.io.InputStream is = urlConnection.getInputStream();
      BufferedReader rd = new BufferedReader(new java.io.InputStreamReader(is));
      ArrayList<WebData> tempList = new ArrayList();
      String line;
      while ((line = rd.readLine()) != null) { String line;
        tempList.add(parseData(line));
      }
      
      return tempList;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
  
  private static WebData parseData(String raw) {
    String[] dataset = raw.split(";");
    Date date = new Date(Long.parseLong(dataset[0]));
    String username = dataset[1];
    
    int x = Integer.parseInt(dataset[2]);
    int y = Integer.parseInt(dataset[3]);
    int z = Integer.parseInt(dataset[4]);
    String server = null;
    if (dataset.length > 5) {
      server = dataset[5];
    }
    return new WebData(date, username, x, y, z, server);
  }
  
  public static ArrayList<WebData> getLatestDataForUsers() {
    ArrayList<WebData> dataSet = parseDataSetsFromLatestList();
    if (dataSet != null) {
      HashMap<String, WebData> usernameToData = new HashMap();
      for (WebData data : dataSet)
      {
        usernameToData.put(data.getUsername(), data);
      }
      return new ArrayList(usernameToData.values());
    }
    return null;
  }
  
  public static void sendWebData(WebData data) {
    try {
      String url = "http://serenityclient.grn.cc/addinfo.php";
      StringBuilder urlBuilder = new StringBuilder(url);
      urlBuilder.append("?user=");
      urlBuilder.append(data.getUsername());
      urlBuilder.append("&data=");
      urlBuilder.append(data.getX());
      urlBuilder.append(";");
      urlBuilder.append(data.getY());
      urlBuilder.append(";");
      urlBuilder.append(data.getZ());
      if (data.getServer() != null) {
        urlBuilder.append(";");
        urlBuilder.append(data.getServer());
      }
      HttpURLConnection urlConnection = (HttpURLConnection)new URL(urlBuilder.toString()).openConnection();
      urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
      urlConnection.setConnectTimeout(15000);
      urlConnection.connect();
      System.out.println("Request made to " + urlBuilder.toString());
      System.out.println("Response code - " + urlConnection.getResponseCode());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static void wipeCurrentUser() {
    try {
      HttpURLConnection urlConnection = (HttpURLConnection)new URL("http://serenityclient.grn.cc/clearinfo.php?user=" + net.minecraft.client.Minecraft.getMinecraft().getSession().getUsername()).openConnection();
      urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
      urlConnection.setConnectTimeout(15000);
      urlConnection.getResponseCode();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
