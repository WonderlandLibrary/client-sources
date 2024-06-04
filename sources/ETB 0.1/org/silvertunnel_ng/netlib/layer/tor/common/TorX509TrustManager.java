package org.silvertunnel_ng.netlib.layer.tor.common;

import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.X509TrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
























public class TorX509TrustManager
  implements X509TrustManager
{
  private static final Logger LOG = LoggerFactory.getLogger(TorX509TrustManager.class);
  
  public static final Pattern cnPattern = Pattern.compile(".*CN=(.*?)(,.*)*", 35);
  


  public TorX509TrustManager() {}
  


  public void checkClientTrusted(X509Certificate[] chain, String authType)
    throws CertificateException
  {}
  


  public void checkServerTrusted(X509Certificate[] chain, String authType)
    throws CertificateException
  {
    if (chain.length != 2)
    {
      throw new CertificateException("Certificate Chain length != 2");
    }
    
    String dnName0 = chain[0].getSubjectDN().getName();
    String dnName1 = chain[1].getSubjectDN().getName();
    
    Matcher dnName0Match = cnPattern.matcher(dnName0);
    Matcher dnName1Match = cnPattern.matcher(dnName1);
    
    if ((!dnName0Match.matches()) || (!dnName1Match.matches()))
    {
      LOG.warn("TorX509TrustManager.checkServerTrusted(): not matched dnName0=" + dnName0 + ", dnName1=" + dnName1);
      
      throw new CertificateException("Name field of Certificate does not have the right format");
    }
    

    dnName0 = dnName0Match.group(1);
    dnName1 = dnName1Match.group(1);
    
    if (dnName1.indexOf(dnName0) > 1)
    {
      throw new CertificateException("Certifier and Certificate owner don't have the same name");
    }
    



    LOG.debug("dnName0 = {}, dnName1 = {}", dnName0.toString(), dnName1.toString());
    if ((dnName1.indexOf("<identity>") != -1) && 
      (dnName1.indexOf("<signing>") != -1))
    {
      throw new CertificateException("Certifier Field does not have the required form");
    }
    

    Date now = new Date();
    if (now.before(chain[0].getNotBefore()))
    {
      throw new CertificateException("Certificate is not valid yet");
    }
    if (now.after(chain[0].getNotAfter()))
    {
      throw new CertificateException("Certificate has expired");
    }
  }
  




  public X509Certificate[] getAcceptedIssuers()
  {
    LOG.debug("X509Certificate[] getAcceptedIssuers()");
    return new X509Certificate[0];
  }
}
