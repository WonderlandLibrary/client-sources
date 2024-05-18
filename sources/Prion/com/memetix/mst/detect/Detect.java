package com.memetix.mst.detect;

import com.memetix.mst.MicrosoftTranslatorAPI;
import com.memetix.mst.language.Language;
import java.net.URL;
import java.net.URLEncoder;
































public final class Detect
  extends MicrosoftTranslatorAPI
{
  private static final String SERVICE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/Detect?";
  private static final String ARRAY_SERVICE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/DetectArray?";
  
  private Detect() {}
  
  public static Language execute(String text)
    throws Exception
  {
    validateServiceState(text);
    URL url = new URL("http://api.microsofttranslator.com/V2/Ajax.svc/Detect?" + (
      apiKey != null ? "appId=" + URLEncoder.encode(apiKey, "UTF-8") : "") + 
      "&text=" + URLEncoder.encode(text, "UTF-8"));
    
    String response = retrieveString(url);
    return Language.fromString(response);
  }
  






  public static String[] execute(String[] texts)
    throws Exception
  {
    validateServiceState(texts);
    String textArr = buildStringArrayParam(texts);
    URL url = new URL("http://api.microsofttranslator.com/V2/Ajax.svc/DetectArray?" + (
      apiKey != null ? "appId=" + URLEncoder.encode(apiKey, "UTF-8") : "") + 
      "&texts=" + URLEncoder.encode(textArr, "UTF-8"));
    String[] response = retrieveStringArr(url);
    return response;
  }
  
  private static void validateServiceState(String text) throws Exception {
    int byteLength = text.getBytes("UTF-8").length;
    if (byteLength > 10240) {
      throw new RuntimeException("TEXT_TOO_LARGE - Microsoft Translator (Detect) can handle up to 10,240 bytes per request");
    }
    validateServiceState();
  }
  
  private static void validateServiceState(String[] texts) throws Exception {
    int length = 0;
    String[] arrayOfString = texts;int j = texts.length; for (int i = 0; i < j; i++) { String text = arrayOfString[i];
      length += text.getBytes("UTF-8").length;
    }
    if (length > 10240) {
      throw new RuntimeException("TEXT_TOO_LARGE - Microsoft Translator (Detect) can handle up to 10,240 bytes per request");
    }
    validateServiceState();
  }
}
