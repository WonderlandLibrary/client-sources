package org.spongycastle.asn1.eac;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import org.spongycastle.util.Arrays;






public class PackedDate
{
  private byte[] time;
  
  public PackedDate(String time)
  {
    this.time = convert(time);
  }
  






  public PackedDate(Date time)
  {
    SimpleDateFormat dateF = new SimpleDateFormat("yyMMdd'Z'");
    
    dateF.setTimeZone(new SimpleTimeZone(0, "Z"));
    
    this.time = convert(dateF.format(time));
  }
  









  public PackedDate(Date time, Locale locale)
  {
    SimpleDateFormat dateF = new SimpleDateFormat("yyMMdd'Z'", locale);
    
    dateF.setTimeZone(new SimpleTimeZone(0, "Z"));
    
    this.time = convert(dateF.format(time));
  }
  
  private byte[] convert(String sTime)
  {
    char[] digs = sTime.toCharArray();
    byte[] date = new byte[6];
    
    for (int i = 0; i != 6; i++)
    {
      date[i] = ((byte)(digs[i] - '0'));
    }
    
    return date;
  }
  

  PackedDate(byte[] bytes)
  {
    time = bytes;
  }
  







  public Date getDate()
    throws ParseException
  {
    SimpleDateFormat dateF = new SimpleDateFormat("yyyyMMdd");
    
    return dateF.parse("20" + toString());
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(time);
  }
  
  public boolean equals(Object o)
  {
    if (!(o instanceof PackedDate))
    {
      return false;
    }
    
    PackedDate other = (PackedDate)o;
    
    return Arrays.areEqual(time, time);
  }
  
  public String toString()
  {
    char[] dateC = new char[time.length];
    
    for (int i = 0; i != dateC.length; i++)
    {
      dateC[i] = ((char)((time[i] & 0xFF) + 48));
    }
    
    return new String(dateC);
  }
  
  public byte[] getEncoding()
  {
    return Arrays.clone(time);
  }
}
