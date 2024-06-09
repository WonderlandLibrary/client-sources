package com.memetix.mst.language;

import com.memetix.mst.MicrosoftTranslatorAPI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;





















public enum Language
{
  AUTO_DETECT(""), 
  ARABIC("ar"), 
  BULGARIAN("bg"), 
  CATALAN("ca"), 
  CHINESE_SIMPLIFIED("zh-CHS"), 
  CHINESE_TRADITIONAL("zh-CHT"), 
  CZECH("cs"), 
  DANISH("da"), 
  DUTCH("nl"), 
  ENGLISH("en"), 
  ESTONIAN("et"), 
  FINNISH("fi"), 
  FRENCH("fr"), 
  GERMAN("de"), 
  GREEK("el"), 
  HAITIAN_CREOLE("ht"), 
  HEBREW("he"), 
  HINDI("hi"), 
  HMONG_DAW("mww"), 
  HUNGARIAN("hu"), 
  INDONESIAN("id"), 
  ITALIAN("it"), 
  JAPANESE("ja"), 
  KOREAN("ko"), 
  LATVIAN("lv"), 
  LITHUANIAN("lt"), 
  MALAY("ms"), 
  NORWEGIAN("no"), 
  PERSIAN("fa"), 
  POLISH("pl"), 
  PORTUGUESE("pt"), 
  ROMANIAN("ro"), 
  RUSSIAN("ru"), 
  SLOVAK("sk"), 
  SLOVENIAN("sl"), 
  SPANISH("es"), 
  SWEDISH("sv"), 
  THAI("th"), 
  TURKISH("tr"), 
  UKRAINIAN("uk"), 
  URDU("ur"), 
  VIETNAMESE("vi");
  




  private final String language;
  


  private Map<Language, String> localizedCache = new ConcurrentHashMap();
  



  private Language(String pLanguage)
  {
    language = pLanguage;
  }
  
  public static Language fromString(String pLanguage) {
    for (Language l : ) {
      if (l.toString().equals(pLanguage)) {
        return l;
      }
    }
    return null;
  }
  




  public String toString()
  {
    return language;
  }
  
  public static void setKey(String pKey) {
    LanguageService.setKey(pKey);
  }
  
  public static void setClientId(String pId) {
    LanguageService.setClientId(pId);
  }
  
  public static void setClientSecret(String pSecret) { LanguageService.setClientSecret(pSecret); }
  














  public String getName(Language locale)
    throws Exception
  {
    String localizedName = null;
    if (localizedCache.containsKey(locale)) {
      localizedName = (String)localizedCache.get(locale);
    }
    else if ((this == AUTO_DETECT) || (locale == AUTO_DETECT)) {
      localizedName = "Auto Detect";
    }
    else {
      String[] names = LanguageService.execute(values(), locale);
      int i = 0;
      for (Language lang : values()) {
        if (lang != AUTO_DETECT) {
          localizedCache.put(locale, names[i]);
          i++;
        }
      }
      localizedName = (String)localizedCache.get(locale);
    }
    
    return localizedName;
  }
  
  public static List<String> getLanguageCodesForTranslation() throws Exception {
    String[] codes = GetLanguagesForTranslateService.execute();
    return Arrays.asList(codes);
  }
  












  public static Map<String, Language> values(Language locale)
    throws Exception
  {
    Map<String, Language> localizedMap = new TreeMap();
    for (Language lang : values()) {
      if (lang == AUTO_DETECT) {
        localizedMap.put(AUTO_DETECT.name(), lang);
      } else
        localizedMap.put(lang.getName(locale), lang);
    }
    return localizedMap;
  }
  
  private void flushCache()
  {
    localizedCache.clear();
  }
  
  public static void flushNameCache()
  {
    for (Language lang : ) {
      lang.flushCache();
    }
  }
  

  private static final class LanguageService
    extends MicrosoftTranslatorAPI
  {
    private static final String SERVICE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/GetLanguageNames?";
    
    private LanguageService() {}
    
    public static String[] execute(Language[] targets, Language locale)
      throws Exception
    {
      validateServiceState();
      String[] localizedNames = new String[0];
      if (locale == Language.AUTO_DETECT) {
        return localizedNames;
      }
      
      String targetString = buildStringArrayParam(Language.values());
      
      URL url = new URL("http://api.microsofttranslator.com/V2/Ajax.svc/GetLanguageNames?" + (
        apiKey != null ? "appId=" + URLEncoder.encode(apiKey, "UTF-8") : "") + 
        "&locale=" + URLEncoder.encode(locale.toString(), "UTF-8") + 
        "&languageCodes=" + URLEncoder.encode(targetString, "UTF-8"));
      localizedNames = retrieveStringArr(url);
      return localizedNames;
    }
  }
  

  private static final class GetLanguagesForTranslateService
    extends MicrosoftTranslatorAPI
  {
    private static final String SERVICE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/GetLanguagesForTranslate?";
    

    private GetLanguagesForTranslateService() {}
    

    public static String[] execute()
      throws Exception
    {
      validateServiceState();
      String[] codes = new String[0];
      
      URL url = new URL("http://api.microsofttranslator.com/V2/Ajax.svc/GetLanguagesForTranslate?" + (apiKey != null ? "appId=" + URLEncoder.encode(apiKey, "UTF-8") : ""));
      codes = retrieveStringArr(url);
      return codes;
    }
  }
}
