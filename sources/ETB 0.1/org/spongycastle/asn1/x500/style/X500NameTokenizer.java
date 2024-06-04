package org.spongycastle.asn1.x500.style;



public class X500NameTokenizer
{
  private String value;
  

  private int index;
  
  private char separator;
  
  private StringBuffer buf = new StringBuffer();
  

  public X500NameTokenizer(String oid)
  {
    this(oid, ',');
  }
  


  public X500NameTokenizer(String oid, char separator)
  {
    value = oid;
    index = -1;
    this.separator = separator;
  }
  
  public boolean hasMoreTokens()
  {
    return index != value.length();
  }
  
  public String nextToken()
  {
    if (index == value.length())
    {
      return null;
    }
    
    int end = index + 1;
    boolean quoted = false;
    boolean escaped = false;
    
    buf.setLength(0);
    
    while (end != value.length())
    {
      char c = value.charAt(end);
      
      if (c == '"')
      {
        if (!escaped)
        {
          quoted = !quoted;
        }
        buf.append(c);
        escaped = false;


      }
      else if ((escaped) || (quoted))
      {
        buf.append(c);
        escaped = false;
      }
      else if (c == '\\')
      {
        buf.append(c);
        escaped = true;
      } else {
        if (c == separator) {
          break;
        }
        


        buf.append(c);
      }
      
      end++;
    }
    
    index = end;
    
    return buf.toString();
  }
}
