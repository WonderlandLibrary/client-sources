package org.silvertunnel_ng.netlib.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;























public enum JavaVersion
{
  JAVA_1_5("JAVA_1_5"),  JAVA_1_6("JAVA_1_6"),  JAVA_1_7("JAVA_1_7"),  JAVA_1_8("JAVA_1_8"),  ANDROID("ANDROID"),  UNKNOWN("UNKNOWN");
  


  private final String title;
  
  private static final Logger LOG = LoggerFactory.getLogger(JavaVersion.class);
  private static JavaVersion javaVersion;
  
  private JavaVersion(String title) {
    this.title = title;
  }
  
  public String toString()
  {
    return title;
  }
  


  public static JavaVersion getJavaVersion()
  {
    if (javaVersion == null)
    {
      String jv = System.getProperty("java.specification.version");
      LOG.debug("system prop jv={}", jv);
      if ("1.5".equals(jv)) {
        javaVersion = JAVA_1_5;
      } else if ("1.6".equals(jv)) {
        javaVersion = JAVA_1_6;
      } else if ("1.7".equals(jv)) {
        javaVersion = JAVA_1_7;
      } else if ("1.8".equals(jv)) {
        javaVersion = JAVA_1_8;
      } else {
        String vendor = System.getProperty("java.vendor");
        if (vendor.toUpperCase().contains("ANDROID")) {
          javaVersion = ANDROID;
        } else {
          javaVersion = UNKNOWN;
        }
      }
      
      LOG.debug("determined Java Version: {}", javaVersion);
    }
    
    return javaVersion;
  }
}
