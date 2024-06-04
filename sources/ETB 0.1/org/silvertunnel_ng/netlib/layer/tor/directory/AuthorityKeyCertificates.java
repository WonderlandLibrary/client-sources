package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




















public class AuthorityKeyCertificates
{
  private static final Logger LOG = LoggerFactory.getLogger(AuthorityKeyCertificates.class);
  



  private final Map<Fingerprint, AuthorityKeyCertificate> authorityKeyCertificates = new HashMap();
  




  private static Pattern pattern;
  



  private static Collection<Fingerprint> authorizedAuthorityKeyIdentityKeys = new ArrayList();
  


  static
  {
    try
    {
      pattern = Pattern.compile("^(dir-key-certificate-version 3\n.*?dir-key-certification\n-----BEGIN SIGNATURE-----.*?-----END SIGNATURE-----)", 43);
      







      authorizedAuthorityKeyIdentityKeys = AuthorityServers.getAuthorityDirIdentityKeyDigests();
    }
    catch (Exception e) {
      LOG.error("could not initialze class AuthorityKeyCertificates", e);
    }
  }
  








  public AuthorityKeyCertificates(String authorityKeyCertificatesStr, Date minValidUntil)
    throws TorException
  {
    this(authorityKeyCertificatesStr, minValidUntil, authorizedAuthorityKeyIdentityKeys);
  }
  












  public AuthorityKeyCertificates(String authorityKeyCertificatesStr, Date minValidUntil, Collection<Fingerprint> allowedAuthorityKeyIdentFingerprints)
    throws TorException
  {
    Matcher m = pattern.matcher(authorityKeyCertificatesStr);
    while (m.find())
    {
      String oneCertStr = m.group(1);
      try {
        AuthorityKeyCertificate oneCert = new AuthorityKeyCertificate(oneCertStr);
        


        if (!oneCert.getDirKeyExpires().after(minValidUntil)) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("skip authorityKeyCertificate because expired with fingerprint=" + oneCert
              .getDirIdentityKeyDigest() + ", dirKeyExpires=" + oneCert
              .getDirKeyExpires());
          }
          
        }
        else if (!allowedAuthorityKeyIdentFingerprints.contains(oneCert
          .getDirIdentityKeyDigest())) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("skip authorityKeyCertificate because unauthorized fingerprint=" + oneCert
              .getDirIdentityKeyDigest());
          }
          

        }
        else {
          authorityKeyCertificates.put(oneCert.getDirIdentityKeyDigest(), oneCert);
        }
      }
      catch (Exception e) {
        LOG.info("skip authorityKeyCertificate because of error while parsing oneCertStr=" + oneCertStr, e);
      }
    }
  }
  









  public boolean isValid(Date minValidUntil)
  {
    int MAX_CERT_OUTDATED_COUNT = 1;
    int MIN_CERT_VALID_COUNT = 5;
    

    int certOutdatedCount = 0;
    int certValidCount = 0;
    for (AuthorityKeyCertificate oneCert : authorityKeyCertificates
      .values())
    {
      if (!oneCert.getDirKeyExpires().after(minValidUntil))
      {
        certOutdatedCount++;
      } else {
        certValidCount++;
      }
    }
    

    boolean result = (certValidCount >= 5) && (certOutdatedCount <= 1);
    if (LOG.isDebugEnabled()) {
      LOG.debug("isValid(): result=" + result + ", certValidCount=" + certValidCount + ", certOutdatedCount=" + certOutdatedCount);
    }
    
    return result;
  }
  









  public AuthorityKeyCertificate getCertByFingerprints(Fingerprint identityKeyFingerprint, Fingerprint signingKeyFingerprint)
  {
    AuthorityKeyCertificate result = (AuthorityKeyCertificate)authorityKeyCertificates.get(identityKeyFingerprint);
    



    if ((signingKeyFingerprint == null) || (result == null) || 
    
      (!signingKeyFingerprint.equals(result
      .getDirSigningKeyDigest()))) {
      return null;
    }
    
    return result;
  }
  



  public String toString()
  {
    return "AuthorityKeyCertificates(" + authorityKeyCertificates + ")";
  }
  



  public Map<Fingerprint, AuthorityKeyCertificate> getAuthorityKeyCertificates()
  {
    return authorityKeyCertificates;
  }
}
