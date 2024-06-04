package org.silvertunnel_ng.netlib.util;

public abstract interface StringStorage
{
  public abstract void put(String paramString1, String paramString2)
    throws IllegalArgumentException;
  
  public abstract String get(String paramString);
}
