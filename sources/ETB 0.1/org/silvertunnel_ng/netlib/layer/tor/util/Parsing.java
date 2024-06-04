package org.silvertunnel_ng.netlib.layer.tor.util;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

























public class Parsing
{
  private static final Logger LOG = LoggerFactory.getLogger(Parsing.class);
  



  public Parsing() {}
  


  public static String renderFingerprint(byte[] fingerprint, boolean withSpace)
  {
    String hex = "0123456789ABCDEF";
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < fingerprint.length; i++)
    {
      int x = fingerprint[i];
      if (x < 0)
      {
        x = 256 + x;
      }
      sb.append("0123456789ABCDEF".substring(x >> 4, (x >> 4) + 1));
      sb.append("0123456789ABCDEF".substring(x % 16, x % 16 + 1));
      if (((i + 1) % 2 == 0) && (withSpace))
      {
        sb.append(" ");
      }
    }
    
    return sb.toString();
  }
  






  public static Pattern compileRegexPattern(String regex)
  {
    return Pattern.compile(regex, 43);
  }
  















  public static String parseStringByRE(String s, Pattern regexPattern, String defaultValue)
  {
    Matcher m = regexPattern.matcher(s);
    if (m.find())
    {
      return m.group(1);
    }
    return defaultValue;
  }
  







  public static Date parseTimestampLine(String startKeyWord, String documentToSearchIn)
  {
    Pattern pValid = compileRegexPattern("^" + startKeyWord + " (\\S+) (\\S+)");
    
    Matcher mValid = pValid.matcher(documentToSearchIn);
    if (mValid.find())
    {
      String value = null;
      try
      {
        value = mValid.group(1) + " " + mValid.group(2);
        return Util.parseUtcTimestamp(value);
      }
      catch (Exception e)
      {
        LOG.warn("could not parse " + startKeyWord + " from value=");
      }
    }
    

    return null;
  }
}
