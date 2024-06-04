package org.spongycastle.i18n.filter;



public class SQLFilter
  implements Filter
{
  public SQLFilter() {}
  


  public String doFilter(String input)
  {
    StringBuffer buf = new StringBuffer(input);
    int i = 0;
    while (i < buf.length())
    {
      char ch = buf.charAt(i);
      switch (ch)
      {
      case '\'': 
        buf.replace(i, i + 1, "\\'");
        i++;
        break;
      case '"': 
        buf.replace(i, i + 1, "\\\"");
        i++;
        break;
      case '=': 
        buf.replace(i, i + 1, "\\=");
        i++;
        break;
      case '-': 
        buf.replace(i, i + 1, "\\-");
        i++;
        break;
      case '/': 
        buf.replace(i, i + 1, "\\/");
        i++;
        break;
      case '\\': 
        buf.replace(i, i + 1, "\\\\");
        i++;
        break;
      case ';': 
        buf.replace(i, i + 1, "\\;");
        i++;
        break;
      case '\r': 
        buf.replace(i, i + 1, "\\r");
        i++;
        break;
      case '\n': 
        buf.replace(i, i + 1, "\\n");
        i++;
        break;
      }
      
      i++;
    }
    return buf.toString();
  }
  
  public String doFilterUrl(String input)
  {
    return doFilter(input);
  }
}
