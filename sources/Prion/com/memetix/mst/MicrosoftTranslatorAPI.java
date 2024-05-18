package com.memetix.mst;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;































public abstract class MicrosoftTranslatorAPI
{
  protected static final String ENCODING = "UTF-8";
  protected static String apiKey;
  private static String DatamarketAccessUri = "https://datamarket.accesscontrol.windows.net/v2/OAuth2-13";
  private static String referrer;
  private static String clientId;
  private static String clientSecret;
  private static String token;
  private static long tokenExpiration = 0L;
  private static String contentType = "text/plain";
  
  protected static final String PARAM_APP_ID = "appId=";
  
  protected static final String PARAM_TO_LANG = "&to=";
  
  protected static final String PARAM_FROM_LANG = "&from=";
  
  protected static final String PARAM_TEXT_SINGLE = "&text=";
  
  protected static final String PARAM_TEXT_ARRAY = "&texts=";
  protected static final String PARAM_SPOKEN_LANGUAGE = "&language=";
  protected static final String PARAM_SENTENCES_LANGUAGE = "&language=";
  protected static final String PARAM_LOCALE = "&locale=";
  protected static final String PARAM_LANGUAGE_CODES = "&languageCodes=";
  
  public MicrosoftTranslatorAPI() {}
  
  public static void setKey(String pKey)
  {
    apiKey = pKey;
  }
  






  public static void setContentType(String pKey)
  {
    contentType = pKey;
  }
  





  public static void setClientId(String pClientId)
  {
    clientId = pClientId;
  }
  





  public static void setClientSecret(String pClientSecret)
  {
    clientSecret = pClientSecret;
  }
  



  public static void setHttpReferrer(String pReferrer)
  {
    referrer = pReferrer;
  }
  


  public static String getToken(String clientId, String clientSecret)
    throws Exception
  {
    String params = "grant_type=client_credentials&scope=http://api.microsofttranslator.com&client_id=" + 
      URLEncoder.encode(clientId, "UTF-8") + 
      "&client_secret=" + URLEncoder.encode(clientSecret, "UTF-8");
    
    URL url = new URL(DatamarketAccessUri);
    HttpURLConnection uc = (HttpURLConnection)url.openConnection();
    if (referrer != null)
      uc.setRequestProperty("referer", referrer);
    uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    uc.setRequestProperty("Accept-Charset", "UTF-8");
    uc.setRequestMethod("POST");
    uc.setDoOutput(true);
    
    OutputStreamWriter wr = new OutputStreamWriter(uc.getOutputStream());
    wr.write(params);
    wr.flush();
    try
    {
      int responseCode = uc.getResponseCode();
      String result = inputStreamToString(uc.getInputStream());
      if (responseCode != 200) {
        throw new Exception("Error from Microsoft Translator API: " + result);
      }
      return result;
    } finally {
      if (uc != null) {
        uc.disconnect();
      }
    }
  }
  





  private static String retrieveResponse(URL url)
    throws Exception
  {
    if ((clientId != null) && (clientSecret != null) && (System.currentTimeMillis() > tokenExpiration)) {
      String tokenJson = getToken(clientId, clientSecret);
      Integer expiresIn = Integer.valueOf(Integer.parseInt((String)((JSONObject)JSONValue.parse(tokenJson)).get("expires_in")));
      tokenExpiration = System.currentTimeMillis() + (expiresIn.intValue() * 1000 - 1);
      token = "Bearer " + (String)((JSONObject)JSONValue.parse(tokenJson)).get("access_token");
    }
    HttpURLConnection uc = (HttpURLConnection)url.openConnection();
    if (referrer != null)
      uc.setRequestProperty("referer", referrer);
    uc.setRequestProperty("Content-Type", contentType + "; charset=" + "UTF-8");
    uc.setRequestProperty("Accept-Charset", "UTF-8");
    if (token != null) {
      uc.setRequestProperty("Authorization", token);
    }
    uc.setRequestMethod("GET");
    uc.setDoOutput(true);
    try
    {
      int responseCode = uc.getResponseCode();
      String result = inputStreamToString(uc.getInputStream());
      if (responseCode != 200) {
        throw new Exception("Error from Microsoft Translator API: " + result);
      }
      return result;
    } finally {
      if (uc != null) {
        uc.disconnect();
      }
    }
  }
  




  protected static String retrieveString(URL url)
    throws Exception
  {
    try
    {
      String response = retrieveResponse(url);
      return jsonToString(response);
    } catch (Exception ex) {
      throw new Exception("[microsoft-translator-api] Error retrieving translation : " + ex.getMessage(), ex);
    }
  }
  






  protected static String[] retrieveStringArr(URL url, String jsonProperty)
    throws Exception
  {
    try
    {
      String response = retrieveResponse(url);
      return jsonToStringArr(response, jsonProperty);
    } catch (Exception ex) {
      throw new Exception("[microsoft-translator-api] Error retrieving translation.", ex);
    }
  }
  








  protected static String[] retrieveStringArr(URL url)
    throws Exception
  {
    return retrieveStringArr(url, null);
  }
  




  protected static Integer[] retrieveIntArray(URL url)
    throws Exception
  {
    try
    {
      String response = retrieveResponse(url);
      return jsonToIntArr(response);
    } catch (Exception ex) {
      throw new Exception("[microsoft-translator-api] Error retrieving translation : " + ex.getMessage(), ex);
    }
  }
  
  private static Integer[] jsonToIntArr(String inputString) throws Exception {
    JSONArray jsonArr = (JSONArray)JSONValue.parse(inputString);
    Integer[] intArr = new Integer[jsonArr.size()];
    int i = 0;
    for (Object obj : jsonArr) {
      intArr[i] = Integer.valueOf(((Long)obj).intValue());
      i++;
    }
    return intArr;
  }
  
  private static String jsonToString(String inputString) throws Exception {
    String json = (String)JSONValue.parse(inputString);
    return json.toString();
  }
  
  private static String[] jsonToStringArr(String inputString, String propertyName)
    throws Exception
  {
    JSONArray jsonArr = (JSONArray)JSONValue.parse(inputString);
    String[] values = new String[jsonArr.size()];
    
    int i = 0;
    for (Object obj : jsonArr) {
      if ((propertyName != null) && (propertyName.length() != 0)) {
        JSONObject json = (JSONObject)obj;
        if (json.containsKey(propertyName)) {
          values[i] = json.get(propertyName).toString();
        }
      } else {
        values[i] = obj.toString();
      }
      i++;
    }
    return values;
  }
  





  private static String inputStreamToString(InputStream inputStream)
    throws Exception
  {
    StringBuilder outputBuilder = new StringBuilder();
    
    try
    {
      if (inputStream != null) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String string; while ((string = reader.readLine()) != null)
        {
          String string;
          outputBuilder.append(string.replaceAll("﻿", ""));
        }
      }
    } catch (Exception ex) {
      throw new Exception("[microsoft-translator-api] Error reading translation stream.", ex);
    }
    
    return outputBuilder.toString();
  }
  
  protected static void validateServiceState() throws Exception
  {
    if ((apiKey != null) && (apiKey.length() < 16))
      throw new RuntimeException("INVALID_API_KEY - Please set the API Key with your Bing Developer's Key");
    if ((apiKey == null) && ((clientId == null) || (clientSecret == null))) {
      throw new RuntimeException("Must provide a Windows Azure Marketplace Client Id and Client Secret - Please see http://msdn.microsoft.com/en-us/library/hh454950.aspx for further documentation");
    }
  }
  
  protected static String buildStringArrayParam(Object[] values) {
    StringBuilder targetString = new StringBuilder("[\"");
    
    Object[] arrayOfObject = values;int j = values.length; for (int i = 0; i < j; i++) { Object obj = arrayOfObject[i];
      if (obj != null) {
        String value = obj.toString();
        if (value.length() != 0) {
          if (targetString.length() > 2)
            targetString.append(",\"");
          targetString.append(value);
          targetString.append("\"");
        }
      }
    }
    targetString.append("]");
    return targetString.toString();
  }
}
