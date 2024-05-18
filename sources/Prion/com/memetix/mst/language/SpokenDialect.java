package com.memetix.mst.language;

import com.memetix.mst.MicrosoftTranslatorAPI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
























public enum SpokenDialect
{
  CATALAN_SPAIN("ca-es"), 
  DANISH_DENMARK("da-dk"), 
  GERMAN_GERMANY("de-de"), 
  ENGLISH_AUSTRALIA("en-au"), 
  ENGLISH_CANADA("en-ca"), 
  ENGLISH_UNITED_KINGDOM("en-gb"), 
  ENGLISH_INDIA("en-in"), 
  ENGLISH_UNITED_STATES("en-us"), 
  SPANISH_SPAIN("es-es"), 
  SPANISH_MEXICO("es-mx"), 
  FINNISH_FINLAND("fi-fi"), 
  FRENCH_CANADA("fr-ca"), 
  FRENCH_FRANCE("fr-fr"), 
  ITALIAN_ITALY("it-it"), 
  JAPANESE_JAPAN("ja-jp"), 
  KOREAN_KOREA("ko-kr"), 
  NORWEGIAN_NORWAY("nb-no"), 
  DUTCH_NETHERLANDS("nl-nl"), 
  POLISH_POLAND("pl-pl"), 
  PORTUGUESE_BRAZIL("pt-br"), 
  PORTUGUESE_PORTUGAL("pt-pt"), 
  RUSSIAN_RUSSIA("ru-ru"), 
  SWEDISH_SWEDEN("sv-se"), 
  CHINESE_SIMPLIFIED_PEOPLES_REPUBLIC_OF_CHINA("zh-cn"), 
  CHINESE_TRADITIONAL_HONG_KONG_SAR("zh-hk"), 
  CHINESE_TRADITIONAL_TAIWAN("zh-tw");
  




  private final String language;
  


  private Map<Language, String> localizedCache = new ConcurrentHashMap();
  



  private SpokenDialect(String pLanguage)
  {
    language = pLanguage;
  }
  
  public static SpokenDialect fromString(String pLanguage) {
    for (SpokenDialect l : ) {
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
    SpokenDialectService.setKey(pKey);
  }
  
  public static void setClientId(String pId) {
    SpokenDialectService.setClientId(pId);
  }
  
  public static void setClientSecret(String pSecret) {
    SpokenDialectService.setClientSecret(pSecret);
  }
  













  public String getName(Language locale)
    throws Exception
  {
    String localizedName = null;
    if (localizedCache.containsKey(locale)) {
      localizedName = (String)localizedCache.get(locale);
    }
    else
    {
      String[] names = SpokenDialectService.execute(values(), locale);
      int i = 0;
      for (SpokenDialect lang : values()) {
        localizedCache.put(locale, names[i]);
        i++;
      }
      localizedName = (String)localizedCache.get(locale);
    }
    return localizedName;
  }
  
  private void flushCache()
  {
    localizedCache.clear();
  }
  
  public static void flushNameCache()
  {
    for (SpokenDialect lang : ) {
      lang.flushCache();
    }
  }
  

  private static final class SpokenDialectService
    extends MicrosoftTranslatorAPI
  {
    private static final String SERVICE_URL = "http://api.microsofttranslator.com/V2/Ajax.svc/GetLanguageNames?";
    

    private SpokenDialectService() {}
    
    public static String[] execute(SpokenDialect[] targets, Language locale)
      throws Exception
    {
      validateServiceState();
      String[] localizedNames = new String[0];
      if (locale == Language.AUTO_DETECT) {
        return localizedNames;
      }
      
      String targetString = buildStringArrayParam(SpokenDialect.values());
      
      URL url = new URL("http://api.microsofttranslator.com/V2/Ajax.svc/GetLanguageNames?" + (
        apiKey != null ? "appId=" + URLEncoder.encode(apiKey, "UTF-8") : "") + 
        "&locale=" + URLEncoder.encode(locale.toString(), "UTF-8") + 
        "&languageCodes=" + URLEncoder.encode(targetString, "UTF-8"));
      localizedNames = retrieveStringArr(url);
      return localizedNames;
    }
  }
}
