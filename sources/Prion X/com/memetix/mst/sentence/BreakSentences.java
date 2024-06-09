package com.memetix.mst.sentence;

import com.memetix.mst.MicrosoftTranslatorAPI;
import com.memetix.mst.language.Language;
import java.net.URL;
import java.net.URLEncoder;





































public final class BreakSentences
  extends MicrosoftTranslatorAPI
{
  private static final String SERVICE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/BreakSentences?";
  
  private BreakSentences() {}
  
  public static Integer[] execute(String text, Language fromLang)
    throws Exception
  {
    validateServiceState(text, fromLang);
    URL url = new URL("http://api.microsofttranslator.com/V2/Ajax.svc/BreakSentences?" + (
      apiKey != null ? "appId=" + URLEncoder.encode(apiKey, "UTF-8") : "") + 
      "&language=" + URLEncoder.encode(fromLang.toString(), "UTF-8") + 
      "&text=" + URLEncoder.encode(text, "UTF-8"));
    
    Integer[] response = retrieveIntArray(url);
    return response;
  }
  
  private static void validateServiceState(String text, Language fromLang) throws Exception {
    int byteLength = text.getBytes("UTF-8").length;
    if (byteLength > 10240) {
      throw new RuntimeException("TEXT_TOO_LARGE - Microsoft Translator (BreakSentences) can handle up to 10,240 bytes per request");
    }
    if (Language.AUTO_DETECT.equals(fromLang)) {
      throw new RuntimeException("BreakSentences does not support AUTO_DETECT Langauge. Please specify the origin language");
    }
    validateServiceState();
  }
}
