package org.silvertunnel_ng.netlib.adapter.url.impl.net.http;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.StringTokenizer;






































class DigestAuthentication
  extends AuthenticationInfo
{
  private static final long serialVersionUID = 100L;
  protected static final String HTTP_CONNECT = "CONNECT";
  protected static final char DIGEST_AUTH = 'D';
  private String authMethod;
  Parameters params;
  
  static class Parameters
    implements Serializable
  {
    private static final long serialVersionUID = -3584543755194526252L;
    private boolean serverQop;
    private String opaque;
    private String cnonce;
    private String nonce;
    private String algorithm;
    private int NCcount = 0;
    

    private String cachedHA1;
    

    private boolean redoCachedHA1 = true;
    



    private static final int cnonceRepeat = 5;
    


    private static final int cnoncelen = 40;
    


    private static SecureRandom random = new SecureRandom();
    

    Parameters()
    {
      serverQop = false;
      opaque = null;
      algorithm = null;
      cachedHA1 = null;
      nonce = null;
      setNewCnonce();
    }
    
    boolean authQop()
    {
      return serverQop;
    }
    
    synchronized void incrementNC()
    {
      NCcount += 1;
    }
    
    synchronized int getNCCount()
    {
      return NCcount;
    }
    
    int cnonceCount = 0;
    

    synchronized String getCnonce()
    {
      if (cnonceCount >= 5)
      {
        setNewCnonce();
      }
      cnonceCount += 1;
      return cnonce;
    }
    
    synchronized void setNewCnonce()
    {
      byte[] bb = new byte[20];
      char[] cc = new char[40];
      random.nextBytes(bb);
      for (int i = 0; i < 20; i++)
      {
        int x = bb[i] + 128;
        cc[(i * 2)] = ((char)(65 + x / 16));
        cc[(i * 2 + 1)] = ((char)(65 + x % 16));
      }
      cnonce = new String(cc, 0, 40);
      cnonceCount = 0;
      redoCachedHA1 = true;
    }
    
    synchronized void setQop(String qop)
    {
      if (qop != null)
      {
        StringTokenizer st = new StringTokenizer(qop, " ");
        while (st.hasMoreTokens())
        {
          if (st.nextToken().equalsIgnoreCase("auth"))
          {
            serverQop = true;
            return;
          }
        }
      }
      serverQop = false;
    }
    
    synchronized String getOpaque()
    {
      return opaque;
    }
    
    synchronized void setOpaque(String s)
    {
      opaque = s;
    }
    
    synchronized String getNonce()
    {
      return nonce;
    }
    
    synchronized void setNonce(String s)
    {
      if (!s.equals(nonce))
      {
        nonce = s;
        NCcount = 0;
        redoCachedHA1 = true;
      }
    }
    
    synchronized String getCachedHA1()
    {
      if (redoCachedHA1)
      {
        return null;
      }
      

      return cachedHA1;
    }
    

    synchronized void setCachedHA1(String s)
    {
      cachedHA1 = s;
      redoCachedHA1 = false;
    }
    
    synchronized String getAlgorithm()
    {
      return algorithm;
    }
    
    synchronized void setAlgorithm(String s)
    {
      algorithm = s;
    }
  }
  










  public DigestAuthentication(boolean isProxy, URL url, String realm, String authMethod, PasswordAuthentication pw, Parameters params)
  {
    super(isProxy ? 'p' : 's', 'D', url, realm);
    this.authMethod = authMethod;
    this.pw = pw;
    this.params = params;
  }
  






  public DigestAuthentication(boolean isProxy, String host, int port, String realm, String authMethod, PasswordAuthentication pw, Parameters params)
  {
    super(isProxy ? 'p' : 's', 'D', host, port, realm);
    this.authMethod = authMethod;
    this.pw = pw;
    this.params = params;
  }
  




  boolean supportsPreemptiveAuthorization()
  {
    return true;
  }
  




  String getHeaderName()
  {
    if (type == 's')
    {
      return "Authorization";
    }
    

    return "Proxy-Authorization";
  }
  
















  String getHeaderValue(URL url, String method)
  {
    return getHeaderValueImpl(url.getFile(), method);
  }
  















  String getHeaderValue(String requestURI, String method)
  {
    return getHeaderValueImpl(requestURI, method);
  }
  








  boolean isAuthorizationStale(String header)
  {
    HeaderParser p = new HeaderParser(header);
    String s = p.findValue("stale");
    if ((s == null) || (!s.equals("true")))
    {
      return false;
    }
    String newNonce = p.findValue("nonce");
    if ((newNonce == null) || ("".equals(newNonce)))
    {
      return false;
    }
    params.setNonce(newNonce);
    return true;
  }
  












  boolean setHeaders(HttpURLConnection conn, HeaderParser p, String raw)
  {
    params.setNonce(p.findValue("nonce"));
    params.setOpaque(p.findValue("opaque"));
    params.setQop(p.findValue("qop"));
    String method;
    String uri;
    String method;
    if ((type == 'p') && 
      (conn.tunnelState() == HttpURLConnection.TunnelState.SETUP))
    {
      String uri = HttpURLConnection.connectRequestURI(conn.getURL());
      method = "CONNECT";
    }
    else
    {
      uri = conn.getURL().getFile();
      method = conn.getMethod();
    }
    
    if ((params.nonce == null) || (authMethod == null) || (pw == null) || (realm == null))
    {

      return false;
    }
    if (authMethod.length() >= 1)
    {




      authMethod = (Character.toUpperCase(authMethod.charAt(0)) + authMethod.substring(1).toLowerCase());
    }
    String algorithm = p.findValue("algorithm");
    if ((algorithm == null) || ("".equals(algorithm)))
    {
      algorithm = "MD5";
    }
    params.setAlgorithm(algorithm);
    




    if (params.authQop())
    {
      params.setNewCnonce();
    }
    
    String value = getHeaderValueImpl(uri, method);
    if (value != null)
    {
      conn.setAuthenticationProperty(getHeaderName(), value);
      return true;
    }
    

    return false;
  }
  






  private String getHeaderValueImpl(String uri, String method)
  {
    char[] passwd = pw.getPassword();
    boolean qop = params.authQop();
    String opaque = params.getOpaque();
    String cnonce = params.getCnonce();
    String nonce = params.getNonce();
    String algorithm = params.getAlgorithm();
    params.incrementNC();
    int nccount = params.getNCCount();
    String ncstring = null;
    
    if (nccount != -1)
    {
      ncstring = Integer.toHexString(nccount).toLowerCase();
      int len = ncstring.length();
      if (len < 8)
      {
        ncstring = ZEROPAD[len] + ncstring;
      }
    }
    
    try
    {
      response = computeDigest(true, pw.getUserName(), passwd, realm, method, uri, nonce, cnonce, ncstring);
    }
    catch (NoSuchAlgorithmException ex)
    {
      String response;
      return null;
    }
    String response;
    String ncfield = "\"";
    if (qop)
    {
      ncfield = "\", nc=" + ncstring;
    }
    
    String value = authMethod + " username=\"" + pw.getUserName() + "\", realm=\"" + realm + "\", nonce=\"" + nonce + ncfield + ", uri=\"" + uri + "\", response=\"" + response + "\", algorithm=\"" + algorithm;
    


    if (opaque != null)
    {
      value = value + "\", opaque=\"" + opaque;
    }
    if (cnonce != null)
    {
      value = value + "\", cnonce=\"" + cnonce;
    }
    if (qop)
    {
      value = value + "\", qop=\"auth";
    }
    value = value + "\"";
    return value;
  }
  

  public void checkResponse(String header, String method, URL url)
    throws IOException
  {
    String uri = url.getFile();
    char[] passwd = pw.getPassword();
    String username = pw.getUserName();
    boolean qop = params.authQop();
    String opaque = params.getOpaque();
    String cnonce = params.cnonce;
    String nonce = params.getNonce();
    String algorithm = params.getAlgorithm();
    int nccount = params.getNCCount();
    String ncstring = null;
    
    if (header == null)
    {
      throw new ProtocolException("No authentication information in response");
    }
    

    if (nccount != -1)
    {
      ncstring = Integer.toHexString(nccount).toUpperCase();
      int len = ncstring.length();
      if (len < 8)
      {
        ncstring = ZEROPAD[len] + ncstring;
      }
    }
    try
    {
      String expected = computeDigest(false, username, passwd, realm, method, uri, nonce, cnonce, ncstring);
      
      HeaderParser p = new HeaderParser(header);
      String rspauth = p.findValue("rspauth");
      if (rspauth == null)
      {
        throw new ProtocolException("No digest in response");
      }
      if (!rspauth.equals(expected))
      {
        throw new ProtocolException("Response digest invalid");
      }
      
      String nextnonce = p.findValue("nextnonce");
      if ((nextnonce != null) && (!"".equals(nextnonce)))
      {
        params.setNonce(nextnonce);
      }
      
    }
    catch (NoSuchAlgorithmException ex)
    {
      throw new ProtocolException("Unsupported algorithm in response");
    }
  }
  










  private String computeDigest(boolean isRequest, String userName, char[] password, String realm, String connMethod, String requestURI, String nonceString, String cnonce, String ncValue)
    throws NoSuchAlgorithmException
  {
    String algorithm = params.getAlgorithm();
    boolean md5sess = algorithm.equalsIgnoreCase("MD5-sess");
    
    MessageDigest md = MessageDigest.getInstance(md5sess ? "MD5" : algorithm);
    String HashA1;
    if (md5sess) {
      String HashA1;
      if ((HashA1 = params.getCachedHA1()) == null)
      {
        String s = userName + ":" + realm + ":";
        String s1 = encode(s, password, md);
        String A1 = s1 + ":" + nonceString + ":" + cnonce;
        HashA1 = encode(A1, null, md);
        params.setCachedHA1(HashA1);
      }
    }
    else
    {
      String A1 = userName + ":" + realm + ":";
      HashA1 = encode(A1, password, md);
    }
    String A2;
    String A2;
    if (isRequest)
    {
      A2 = connMethod + ":" + requestURI;
    }
    else
    {
      A2 = ":" + requestURI;
    }
    String HashA2 = encode(A2, null, md);
    String combo;
    String combo;
    if (params.authQop())
    {
      combo = HashA1 + ":" + nonceString + ":" + ncValue + ":" + cnonce + ":auth:" + HashA2;

    }
    else
    {

      combo = HashA1 + ":" + nonceString + ":" + HashA2;
    }
    String finalHash = encode(combo, null, md);
    return finalHash;
  }
  
  private static final char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
  

  private static final String[] ZEROPAD = { "00000000", "0000000", "000000", "00000", "0000", "000", "00", "0" };
  


  private String encode(String src, char[] passwd, MessageDigest md)
  {
    try
    {
      md.update(src.getBytes("ISO-8859-1"));
    }
    catch (UnsupportedEncodingException uee)
    {
      if (!$assertionsDisabled) throw new AssertionError();
    }
    if (passwd != null)
    {
      byte[] passwdBytes = new byte[passwd.length];
      for (int i = 0; i < passwd.length; i++)
      {
        passwdBytes[i] = ((byte)passwd[i]);
      }
      md.update(passwdBytes);
      Arrays.fill(passwdBytes, (byte)0);
    }
    byte[] digest = md.digest();
    
    StringBuffer res = new StringBuffer(digest.length * 2);
    for (int i = 0; i < digest.length; i++)
    {
      int hashchar = digest[i] >>> 4 & 0xF;
      res.append(HEX_CHARS[hashchar]);
      hashchar = digest[i] & 0xF;
      res.append(HEX_CHARS[hashchar]);
    }
    return res.toString();
  }
}
