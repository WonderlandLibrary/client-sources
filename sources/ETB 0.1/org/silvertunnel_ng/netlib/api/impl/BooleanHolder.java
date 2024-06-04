package org.silvertunnel_ng.netlib.api.impl;









public class BooleanHolder
{
  public volatile boolean value;
  







  public BooleanHolder() {}
  







  public String toString()
  {
    return value ? "true" : "false";
  }
}
