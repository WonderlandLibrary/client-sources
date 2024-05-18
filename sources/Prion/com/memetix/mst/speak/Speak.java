package com.memetix.mst.speak;

import com.memetix.mst.MicrosoftTranslatorAPI;
import com.memetix.mst.language.SpokenDialect;
import java.net.URL;
import java.net.URLEncoder;


































public final class Speak
  extends MicrosoftTranslatorAPI
{
  private static final String SERVICE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/Speak?";
  
  private Speak() {}
  
  public static String execute(String text, SpokenDialect language)
    throws Exception
  {
    validateServiceState(text);
    URL url = new URL("http://api.microsofttranslator.com/V2/Ajax.svc/Speak?" + (
      apiKey != null ? "appId=" + URLEncoder.encode(apiKey, "UTF-8") : "") + 
      "&language=" + URLEncoder.encode(language.toString(), "UTF-8") + 
      "&text=" + URLEncoder.encode(text, "UTF-8"));
    String response = retrieveString(url);
    return response;
  }
  
  private static void validateServiceState(String text) throws Exception {
    int byteLength = text.getBytes("UTF-8").length;
    if (byteLength > 2000) {
      throw new RuntimeException("TEXT_TOO_LARGE - Microsoft Translator (Speak) can handle up to 2000 bytes per request");
    }
    validateServiceState();
  }
}
