package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.BitSet;






























public class ParseUtil
{
  static BitSet encodedInPath;
  private static final Charset CHARSET_UTF8;
  
  static
  {
    CHARSET_UTF8 = Charset.forName("UTF-8");
    


    encodedInPath = new BitSet(256);
    





    encodedInPath.set(61);
    encodedInPath.set(59);
    encodedInPath.set(63);
    encodedInPath.set(47);
    


    encodedInPath.set(35);
    encodedInPath.set(32);
    encodedInPath.set(60);
    encodedInPath.set(62);
    encodedInPath.set(37);
    encodedInPath.set(34);
    encodedInPath.set(123);
    encodedInPath.set(125);
    encodedInPath.set(124);
    encodedInPath.set(92);
    encodedInPath.set(94);
    encodedInPath.set(91);
    encodedInPath.set(93);
    encodedInPath.set(96);
    

    for (int i = 0; i < 32; i++)
    {
      encodedInPath.set(i);
    }
    encodedInPath.set(127);
  }
  









  public static String encodePath(String path)
  {
    return encodePath(path, true);
  }
  




  public static String encodePath(String path, boolean flag)
  {
    char[] retCC = new char[path.length() * 2 + 16];
    int retLen = 0;
    char[] pathCC = path.toCharArray();
    
    int n = path.length();
    for (int i = 0; i < n; i++)
    {
      char c = pathCC[i];
      if (((!flag) && (c == '/')) || ((flag) && (c == File.separatorChar)))
      {
        retCC[(retLen++)] = '/';


      }
      else if (c <= '')
      {
        if (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')) || ((c >= '0') && (c <= '9')))
        {

          retCC[(retLen++)] = c;
        }
        else if (encodedInPath.get(c))
        {
          retLen = escape(retCC, c, retLen);
        }
        else
        {
          retCC[(retLen++)] = c;
        }
      }
      else if (c > '߿')
      {
        retLen = escape(retCC, (char)(0xE0 | c >> '\f' & 0xF), retLen);
        
        retLen = escape(retCC, (char)(0x80 | c >> '\006' & 0x3F), retLen);
        
        retLen = escape(retCC, (char)(0x80 | c >> '\000' & 0x3F), retLen);

      }
      else
      {
        retLen = escape(retCC, (char)(0xC0 | c >> '\006' & 0x1F), retLen);
        
        retLen = escape(retCC, (char)(0x80 | c >> '\000' & 0x3F), retLen);
      }
      



      if (retLen + 9 > retCC.length)
      {
        int newLen = retCC.length * 2 + 16;
        if (newLen < 0)
        {
          newLen = Integer.MAX_VALUE;
        }
        char[] buf = new char[newLen];
        System.arraycopy(retCC, 0, buf, 0, retLen);
        retCC = buf;
      }
    }
    return new String(retCC, 0, retLen);
  }
  




  private static int escape(char[] cc, char c, int index)
  {
    cc[(index++)] = '%';
    cc[(index++)] = Character.forDigit(c >> '\004' & 0xF, 16);
    cc[(index++)] = Character.forDigit(c & 0xF, 16);
    return index;
  }
  



  private static byte unescape(String s, int i)
  {
    return (byte)Integer.parseInt(s.substring(i + 1, i + 3), 16);
  }
  





  public static String decode(String s)
  {
    int n = s.length();
    if ((n == 0) || (s.indexOf('%') < 0))
    {
      return s;
    }
    
    StringBuilder sb = new StringBuilder(n);
    ByteBuffer bb = ByteBuffer.allocate(n);
    CharBuffer cb = CharBuffer.allocate(n);
    

    CharsetDecoder dec = CHARSET_UTF8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
    
    char c = s.charAt(0);
    for (int i = 0; i < n;)
    {
      assert (c == s.charAt(i));
      if (c != '%')
      {
        sb.append(c);
        i++; if (i >= n) {
          break;
        }
        
        c = s.charAt(i);
      }
      else {
        bb.clear();
        int ui = i;
        for (;;)
        {
          assert (n - i >= 2);
          try
          {
            bb.put(unescape(s, i));
          }
          catch (NumberFormatException e)
          {
            throw new IllegalArgumentException();
          }
          i += 3;
          if (i < n)
          {


            c = s.charAt(i);
            if (c != '%') {
              break;
            }
          }
        }
        bb.flip();
        cb.clear();
        dec.reset();
        CoderResult cr = dec.decode(bb, cb, true);
        if (cr.isError())
        {
          throw new IllegalArgumentException("Error decoding percent encoded characters");
        }
        
        cr = dec.flush(cb);
        if (cr.isError())
        {
          throw new IllegalArgumentException("Error decoding percent encoded characters");
        }
        
        sb.append(cb.flip().toString());
      }
    }
    return sb.toString();
  }
  



  public String canonizeString(String file)
  {
    int i = 0;
    int lim = file.length();
    

    while ((i = file.indexOf("/../")) >= 0)
    {
      if ((lim = file.lastIndexOf('/', i - 1)) >= 0)
      {
        file = file.substring(0, lim) + file.substring(i + 3);
      }
      else
      {
        file = file.substring(i + 3);
      }
    }
    
    while ((i = file.indexOf("/./")) >= 0)
    {
      file = file.substring(0, i) + file.substring(i + 2);
    }
    
    while (file.endsWith("/.."))
    {
      i = file.indexOf("/..");
      if ((lim = file.lastIndexOf('/', i - 1)) >= 0)
      {
        file = file.substring(0, lim + 1);
      }
      else
      {
        file = file.substring(0, i);
      }
    }
    
    if (file.endsWith("/."))
    {
      file = file.substring(0, file.length() - 1);
    }
    
    return file;
  }
  
  public static URL fileToEncodedURL(File file) throws MalformedURLException
  {
    String path = file.getAbsolutePath();
    path = encodePath(path);
    if (!path.startsWith("/"))
    {
      path = "/" + path;
    }
    if ((!path.endsWith("/")) && (file.isDirectory()))
    {
      path = path + "/";
    }
    return new URL("file", "", path);
  }
  
  public static URI toURI(URL url)
  {
    String protocol = url.getProtocol();
    String auth = url.getAuthority();
    String path = url.getPath();
    String query = url.getQuery();
    String ref = url.getRef();
    if ((path != null) && (!path.startsWith("/")))
    {
      path = "/" + path;
    }
    




    if ((auth != null) && (auth.endsWith(":-1")))
    {
      auth = auth.substring(0, auth.length() - 3);
    }
    
    URI uri;
    try
    {
      uri = createURI(protocol, auth, path, query, ref);
    }
    catch (URISyntaxException e) {
      URI uri;
      uri = null;
    }
    return uri;
  }
  










  private static URI createURI(String scheme, String authority, String path, String query, String fragment)
    throws URISyntaxException
  {
    String s = toString(scheme, null, authority, null, null, -1, path, query, fragment);
    
    checkPath(s, scheme, path);
    return new URI(s);
  }
  


  private static String toString(String scheme, String opaquePart, String authority, String userInfo, String host, int port, String path, String query, String fragment)
  {
    StringBuffer sb = new StringBuffer();
    if (scheme != null)
    {
      sb.append(scheme);
      sb.append(':');
    }
    appendSchemeSpecificPart(sb, opaquePart, authority, userInfo, host, port, path, query);
    
    appendFragment(sb, fragment);
    return sb.toString();
  }
  


  private static void appendSchemeSpecificPart(StringBuffer sb, String opaquePart, String authority, String userInfo, String host, int port, String path, String query)
  {
    if (opaquePart != null)
    {




      if (opaquePart.startsWith("//["))
      {
        int end = opaquePart.indexOf(']');
        if ((end != -1) && (opaquePart.indexOf(':') != -1)) { String doquote;
          String dontquote;
          String doquote;
          if (end == opaquePart.length())
          {
            String dontquote = opaquePart;
            doquote = "";
          }
          else
          {
            dontquote = opaquePart.substring(0, end + 1);
            doquote = opaquePart.substring(end + 1);
          }
          sb.append(dontquote);
          sb.append(quote(doquote, L_URIC, H_URIC));
        }
      }
      else
      {
        sb.append(quote(opaquePart, L_URIC, H_URIC));
      }
    }
    else
    {
      appendAuthority(sb, authority, userInfo, host, port);
      if (path != null)
      {
        sb.append(quote(path, L_PATH, H_PATH));
      }
      if (query != null)
      {
        sb.append('?');
        sb.append(quote(query, L_URIC, H_URIC));
      }
    }
  }
  

  private static void appendAuthority(StringBuffer sb, String authority, String userInfo, String host, int port)
  {
    if (host != null)
    {
      sb.append("//");
      if (userInfo != null)
      {
        sb.append(quote(userInfo, L_USERINFO, H_USERINFO));
        sb.append('@');
      }
      
      boolean needBrackets = (host.indexOf(':') >= 0) && (!host.startsWith("[")) && (!host.endsWith("]"));
      if (needBrackets)
      {
        sb.append('[');
      }
      sb.append(host);
      if (needBrackets)
      {
        sb.append(']');
      }
      if (port != -1)
      {
        sb.append(':');
        sb.append(port);
      }
    }
    else if (authority != null)
    {
      sb.append("//");
      if (authority.startsWith("["))
      {
        int end = authority.indexOf(']');
        if ((end != -1) && (authority.indexOf(':') != -1)) { String doquote;
          String dontquote;
          String doquote;
          if (end == authority.length())
          {
            String dontquote = authority;
            doquote = "";
          }
          else
          {
            dontquote = authority.substring(0, end + 1);
            doquote = authority.substring(end + 1);
          }
          sb.append(dontquote);
          sb.append(quote(doquote, L_REG_NAME | L_SERVER, H_REG_NAME | H_SERVER));
        }
        
      }
      else
      {
        sb.append(quote(authority, L_REG_NAME | L_SERVER, H_REG_NAME | H_SERVER));
      }
    }
  }
  

  private static void appendFragment(StringBuffer sb, String fragment)
  {
    if (fragment != null)
    {
      sb.append('#');
      sb.append(quote(fragment, L_URIC, H_URIC));
    }
  }
  



  private static String quote(String s, long lowMask, long highMask)
  {
    int n = s.length();
    StringBuffer sb = null;
    boolean allowNonASCII = (lowMask & 1L) != 0L;
    for (int i = 0; i < s.length(); i++)
    {
      char c = s.charAt(i);
      if (c < '')
      {
        if ((!match(c, lowMask, highMask)) && (!isEscaped(s, i)))
        {
          if (sb == null)
          {
            sb = new StringBuffer();
            sb.append(s.substring(0, i));
          }
          appendEscape(sb, (byte)c);


        }
        else if (sb != null)
        {
          sb.append(c);
        }
        
      }
      else if ((allowNonASCII) && (
        (Character.isSpaceChar(c)) || (Character.isISOControl(c))))
      {
        if (sb == null)
        {
          sb = new StringBuffer();
          sb.append(s.substring(0, i));
        }
        appendEncoded(sb, c);


      }
      else if (sb != null)
      {
        sb.append(c);
      }
    }
    
    return sb == null ? s : sb.toString();
  }
  




  private static boolean isEscaped(String s, int pos)
  {
    if ((s == null) || (s.length() <= pos + 2))
    {
      return false;
    }
    

    return (s.charAt(pos) == '%') && (match(s.charAt(pos + 1), L_HEX, H_HEX)) && (match(s.charAt(pos + 2), L_HEX, H_HEX));
  }
  
  private static void appendEncoded(StringBuffer sb, char c)
  {
    ByteBuffer bb = null;
    try
    {
      bb = CHARSET_UTF8.newEncoder().encode(CharBuffer.wrap("" + c));
    }
    catch (CharacterCodingException x)
    {
      if (!$assertionsDisabled) throw new AssertionError();
    }
    while (bb.hasRemaining())
    {
      int b = bb.get() & 0xFF;
      if (b >= 128)
      {
        appendEscape(sb, (byte)b);
      }
      else
      {
        sb.append((char)b);
      }
    }
  }
  
  private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
  

  private static void appendEscape(StringBuffer sb, byte b)
  {
    sb.append('%');
    sb.append(hexDigits[(b >> 4 & 0xF)]);
    sb.append(hexDigits[(b >> 0 & 0xF)]);
  }
  

  private static boolean match(char c, long lowMask, long highMask)
  {
    if (c < '@')
    {
      return (1L << c & lowMask) != 0L;
    }
    if (c < '')
    {
      return (1L << c - '@' & highMask) != 0L;
    }
    return false;
  }
  


  private static void checkPath(String s, String scheme, String path)
    throws URISyntaxException
  {
    if (scheme != null)
    {
      if ((path != null) && 
        (path.length() > 0) && (path.charAt(0) != '/'))
      {
        throw new URISyntaxException(s, "Relative path in absolute URI");
      }
    }
  }
  




  private static long lowMask(char first, char last)
  {
    long m = 0L;
    int f = Math.max(Math.min(first, 63), 0);
    int l = Math.max(Math.min(last, 63), 0);
    for (int i = f; i <= l; i++)
    {
      m |= 1L << i;
    }
    return m;
  }
  

  private static long lowMask(String chars)
  {
    int n = chars.length();
    long m = 0L;
    for (int i = 0; i < n; i++)
    {
      char c = chars.charAt(i);
      if (c < '@')
      {
        m |= 1L << c;
      }
    }
    return m;
  }
  


  private static long highMask(char first, char last)
  {
    long m = 0L;
    int f = Math.max(Math.min(first, 127), 64) - 64;
    int l = Math.max(Math.min(last, 127), 64) - 64;
    for (int i = f; i <= l; i++)
    {
      m |= 1L << i;
    }
    return m;
  }
  

  private static long highMask(String chars)
  {
    int n = chars.length();
    long m = 0L;
    for (int i = 0; i < n; i++)
    {
      char c = chars.charAt(i);
      if ((c >= '@') && (c < ''))
      {
        m |= 1L << c - '@';
      }
    }
    return m;
  }
  




  private static final long L_DIGIT = lowMask('0', '9');
  

  private static final long H_DIGIT = 0L;
  
  private static final long L_HEX = L_DIGIT;
  private static final long H_HEX = highMask('A', 'F') | highMask('a', 'f');
  

  private static final long L_UPALPHA = 0L;
  

  private static final long H_UPALPHA = highMask('A', 'Z');
  

  private static final long L_LOWALPHA = 0L;
  

  private static final long H_LOWALPHA = highMask('a', 'z');
  
  private static final long L_ALPHA = 0L;
  
  private static final long H_ALPHA = H_LOWALPHA | H_UPALPHA;
  

  private static final long L_ALPHANUM = L_DIGIT | 0L;
  private static final long H_ALPHANUM = 0L | H_ALPHA;
  


  private static final long L_MARK = lowMask("-_.!~*'()");
  private static final long H_MARK = highMask("-_.!~*'()");
  

  private static final long L_UNRESERVED = L_ALPHANUM | L_MARK;
  private static final long H_UNRESERVED = H_ALPHANUM | H_MARK;
  



  private static final long L_RESERVED = lowMask(";/?:@&=+$,[]");
  private static final long H_RESERVED = highMask(";/?:@&=+$,[]");
  

  private static final long L_ESCAPED = 1L;
  

  private static final long H_ESCAPED = 0L;
  
  private static final long L_DASH = lowMask("-");
  private static final long H_DASH = highMask("-");
  

  private static final long L_URIC = L_RESERVED | L_UNRESERVED | 1L;
  private static final long H_URIC = H_RESERVED | H_UNRESERVED | 0L;
  


  private static final long L_PCHAR = L_UNRESERVED | 1L | 
    lowMask(":@&=+$,");
  private static final long H_PCHAR = H_UNRESERVED | 0L | 
    highMask(":@&=+$,");
  

  private static final long L_PATH = L_PCHAR | lowMask(";/");
  private static final long H_PATH = H_PCHAR | highMask(";/");
  


  private static final long L_USERINFO = L_UNRESERVED | 1L | 
    lowMask(";:&=+$,");
  private static final long H_USERINFO = H_UNRESERVED | 0L | 
    highMask(";:&=+$,");
  


  private static final long L_REG_NAME = L_UNRESERVED | 1L | 
    lowMask("$,;:@&=+");
  private static final long H_REG_NAME = H_UNRESERVED | 0L | 
    highMask("$,;:@&=+");
  

  private static final long L_SERVER = L_USERINFO | L_ALPHANUM | L_DASH | 
    lowMask(".:@[]");
  private static final long H_SERVER = H_USERINFO | H_ALPHANUM | H_DASH | 
    highMask(".:@[]");
  
  public ParseUtil() {}
}
