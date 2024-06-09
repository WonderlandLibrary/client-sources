package me.hexxed.mercury.util;

import com.memetix.mst.language.Language;

public class Translator
{
  private static Translator translator;
  
  public Translator() {}
  
  public static Translator getTranslator() {
    return translator;
  }
  
  public void setup() {
    com.memetix.mst.translate.Translate.setClientId("sCheese-MCClient");
    com.memetix.mst.translate.Translate.setClientSecret("4UaoXOyXaPaogjj3L0VBglXny+BxQpXYsq1DMYBtSuU=");
  }
  
  public String getTranslation(String input, Language from, Language to) {
    try {
      return com.memetix.mst.translate.Translate.execute(input, from, to);
    } catch (Exception e) {
      e.printStackTrace(); }
    return "Failed to translate";
  }
  
  public String getTranslation(String input, Language to)
  {
    try {
      return com.memetix.mst.translate.Translate.execute(input, to);
    } catch (Exception e) {
      e.printStackTrace(); }
    return "Failed to translate";
  }
  
  public Language getLanguage(String input)
  {
    try {
      return com.memetix.mst.detect.Detect.execute(input);
    } catch (Exception e) {
      e.printStackTrace(); }
    return Language.ENGLISH;
  }
}
