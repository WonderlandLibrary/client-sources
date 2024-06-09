package com.memetix.mst.translate;

import com.memetix.mst.MicrosoftTranslatorAPI;
import com.memetix.mst.language.Language;
import java.net.URL;
import java.net.URLEncoder;


































public final class Translate
  extends MicrosoftTranslatorAPI
{
  private static final String SERVICE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/Translate?";
  private static final String ARRAY_SERVICE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/TranslateArray?";
  private static final String ARRAY_JSON_OBJECT_PROPERTY = "TranslatedText";
  
  private Translate() {}
  
  public static String execute(String text, Language from, Language to)
    throws Exception
  {
    validateServiceState(text);
    String params = 
      (apiKey != null ? "appId=" + URLEncoder.encode(apiKey, "UTF-8") : "") + 
      "&from=" + URLEncoder.encode(from.toString(), "UTF-8") + 
      "&to=" + URLEncoder.encode(to.toString(), "UTF-8") + 
      "&text=" + URLEncoder.encode(text, "UTF-8");
    
    URL url = new URL("http://api.microsofttranslator.com/V2/Ajax.svc/Translate?" + params);
    String response = retrieveString(url);
    return response;
  }
  








  public static String execute(String text, Language to)
    throws Exception
  {
    return execute(text, Language.AUTO_DETECT, to);
  }
  











  public static String[] execute(String[] texts, Language from, Language to)
    throws Exception
  {
    validateServiceState(texts);
    String params = 
      (apiKey != null ? "appId=" + URLEncoder.encode(apiKey, "UTF-8") : "") + 
      "&from=" + URLEncoder.encode(from.toString(), "UTF-8") + 
      "&to=" + URLEncoder.encode(to.toString(), "UTF-8") + 
      "&texts=" + URLEncoder.encode(buildStringArrayParam(texts), "UTF-8");
    
    URL url = new URL("http://api.microsofttranslator.com/V2/Ajax.svc/TranslateArray?" + params);
    String[] response = retrieveStringArr(url, "TranslatedText");
    return response;
  }
  













  public static String[] execute(String[] texts, Language to)
    throws Exception
  {
    return execute(texts, Language.AUTO_DETECT, to);
  }
  
  private static void validateServiceState(String[] texts) throws Exception {
    int length = 0;
    String[] arrayOfString = texts;int j = texts.length; for (int i = 0; i < j; i++) { String text = arrayOfString[i];
      length += text.getBytes("UTF-8").length;
    }
    if (length > 10240) {
      throw new RuntimeException("TEXT_TOO_LARGE - Microsoft Translator (Translate) can handle up to 10,240 bytes per request");
    }
    validateServiceState();
  }
  
  private static void validateServiceState(String text) throws Exception
  {
    int byteLength = text.getBytes("UTF-8").length;
    if (byteLength > 10240) {
      throw new RuntimeException("TEXT_TOO_LARGE - Microsoft Translator (Translate) can handle up to 10,240 bytes per request");
    }
    validateServiceState();
  }
}
