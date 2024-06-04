package org.silvertunnel_ng.netlib.layer.tor.directory;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.silvertunnel_ng.netlib.layer.tor.api.Fingerprint;
import org.silvertunnel_ng.netlib.layer.tor.util.Encoding;
import org.silvertunnel_ng.netlib.layer.tor.util.Encryption;
import org.silvertunnel_ng.netlib.layer.tor.util.Parsing;
import org.silvertunnel_ng.netlib.layer.tor.util.TorException;
import org.silvertunnel_ng.netlib.layer.tor.util.Util;
import org.silvertunnel_ng.netlib.util.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



















public class DirectoryConsensus
{
  public static final Logger LOG = LoggerFactory.getLogger(DirectoryConsensus.class);
  
  private Date validAfter;
  
  private Date freshUntil;
  private Date validUntil;
  private Map<Fingerprint, RouterStatusDescription> fingerprintsNetworkStatusDescriptors = new HashMap();
  
  private static final Pattern VERSION_PATTERN = Parsing.compileRegexPattern("^network-status-version (\\d+)");
  private static final Pattern SIGNEDDATA_PATTERN = Parsing.compileRegexPattern("^(network-status-version.*?directory-signature )");
  












  public DirectoryConsensus(String consensusStr, AuthorityKeyCertificates authorityKeyCertificates, Date currentDate)
    throws TorException, ParseException
  {
    String version = Parsing.parseStringByRE(consensusStr, VERSION_PATTERN, "");
    if (!version.equals("3")) {
      throw new TorException("wrong network status version");
    }
    

    setValidAfter(Parsing.parseTimestampLine("valid-after", consensusStr));
    setFreshUntil(Parsing.parseTimestampLine("fresh-until", consensusStr));
    setValidUntil(Parsing.parseTimestampLine("valid-until", consensusStr));
    if (LOG.isDebugEnabled()) {
      LOG.debug("Directory.parseDirV3NetworkStatus: Consensus document validAfter=" + 
        getValidAfter() + ", freshUntil=" + 
        
        getFreshUntil() + ", validUntil=" + 
        getValidUntil());
    }
    if (!isValidDate(currentDate))
    {

      throw new TorException("invalid validAfter=" + getValidAfter() + ", freshUntil=" + getFreshUntil() + " or and validUntil=" + getValidUntil() + " for currentDate=" + currentDate);
    }
    
    byte[] signedData = Parsing.parseStringByRE(consensusStr, SIGNEDDATA_PATTERN, "").getBytes();
    LOG.debug("consensus: extracted signed data (length)={}", Integer.valueOf(signedData.length));
    


    Pattern pSignature = Pattern.compile("^directory-signature (\\S+) (\\S+)\\s*\n-----BEGIN SIGNATURE-----\n(.*?)-----END SIGNATURE", 43);
    


    Matcher mSig = pSignature.matcher(consensusStr);
    Set<Fingerprint> dirIdentityKeyDigestOfMatchingSignatures = new HashSet();
    while (mSig.find()) {
      byte[] identityKeyDigest = DatatypeConverter.parseHexBinary(mSig.group(1));
      byte[] signingKeyDigest = DatatypeConverter.parseHexBinary(mSig.group(2));
      String sigBase64 = mSig.group(3);
      while (sigBase64.length() % 4 != 0) {
        sigBase64 = sigBase64 + "=";
      }
      byte[] signature = DatatypeConverter.parseBase64Binary(sigBase64);
      if (LOG.isDebugEnabled()) {
        LOG.debug("Directory.parseDirV3NetworkStatus: Extracted identityKeyDigest(hex)=" + 
          Encoding.toHexString(identityKeyDigest));
        LOG.debug("Directory.parseDirV3NetworkStatus: Extracted signingKeyDigest(hex)=" + 
          Encoding.toHexString(signingKeyDigest));
        LOG.debug("Directory.parseDirV3NetworkStatus: Found signature(base64)=" + 
          DatatypeConverter.printBase64Binary(signature));
      }
      


      AuthorityKeyCertificate authorityKeyCertificate = authorityKeyCertificates.getCertByFingerprints(new FingerprintImpl(identityKeyDigest), new FingerprintImpl(signingKeyDigest));
      
      if (authorityKeyCertificate == null) {
        LOG.debug("No authorityKeyCertificate found");
      }
      else {
        if (LOG.isDebugEnabled()) {
          LOG.debug("authorityKeyCertificate signingKeyDigest(hex)=" + 
            Encoding.toHexString(authorityKeyCertificate
            .getDirSigningKeyDigest().getBytes()));
        }
        if (signature.length < 1) {
          LOG.debug("No signature found in network status");

        }
        else if (!Encryption.verifySignature(signature, authorityKeyCertificate.getDirSigningKey(), signedData)) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("Directory signature verification failed for identityKeyDigest(hex)=" + 
              Encoding.toHexString(identityKeyDigest));
          }
        }
        else
        {
          dirIdentityKeyDigestOfMatchingSignatures.add(authorityKeyCertificate.getDirIdentityKeyDigest());
          if (LOG.isDebugEnabled())
            LOG.debug("single signature verification ok for identityKeyDigest(hex)=" + 
              Encoding.toHexString(identityKeyDigest));
        }
      } }
    int CONSENSUS_MIN_VALID_SIGNATURES = 4;
    int sigNum = dirIdentityKeyDigestOfMatchingSignatures.size();
    if (sigNum < 4) {
      throw new TorException("Directory signature verification failed: only " + sigNum + " (different) signatures found");
    }
    

    LOG.debug("signature verification accepted");
    


    Pattern pRouter = Pattern.compile("^r (\\S+) (\\S+) (\\S+) (\\S+) (\\S+) (\\S+) (\\d+) (\\d+)\\s*\ns ([a-z0-9 ]+)?", 43);
    
    Matcher m = pRouter.matcher(consensusStr);
    
    while (m.find()) {
      RouterStatusDescription sinfo = new RouterStatusDescription();
      sinfo.setNickname(m.group(1));
      sinfo.setFingerprint(m.group(2));
      sinfo.setDigestDescriptor(m.group(3));
      sinfo.setLastPublication(Util.parseUtcTimestampAsLong(m.group(4) + " " + m.group(5)).longValue());
      sinfo.setIp(m.group(6));
      sinfo.setOrPort(Integer.parseInt(m.group(7)));
      sinfo.setDirPort(Integer.parseInt(m.group(8)));
      sinfo.setRouterFlags(m.group(9));
      if (sinfo.getRouterFlags().isRunning()) {
        getFingerprintsNetworkStatusDescriptors().put(sinfo.getFingerprint(), sinfo);
      }
    }
  }
  







  public boolean isValid(Date now)
  {
    if (!isValidDate(now)) {
      return false;
    }
    

    if (fingerprintsNetworkStatusDescriptors.size() < 50)
    {
      LOG.warn("too few number of routers=" + fingerprintsNetworkStatusDescriptors
        .size());
      return false;
    }
    

    return true;
  }
  








  private boolean isValidDate(Date now)
  {
    if ((validAfter == null) || (validAfter.after(now)))
    {
      LOG.warn("validAfter=" + validAfter + " is too new  for currentDate=" + now + " - this should never occur with consistent data");
      

      return false;
    }
    if (freshUntil == null) {
      LOG.info("freshUntil=" + freshUntil + " is invalid for currentDate=" + now);
    }
    
    if ((validUntil == null) || (validUntil.before(now)))
    {
      LOG.info("validUntil=" + validUntil + " is too old for currentDate=" + now);
      
      return false;
    }
    

    return true;
  }
  



  public boolean needsToBeRefreshed(Date now)
  {
    if (validUntil.before(now))
    {
      LOG.warn("must be refrehed - but it is actually to late; validUntil=" + validUntil);
      
      return true;
    }
    


    if (freshUntil.before(now))
    {

      LOG.debug("should be refreshed soon");
    }
    

    return !isValid(now);
  }
  



  public Date getValidAfter()
  {
    return validAfter;
  }
  
  public void setValidAfter(Date validAfter) {
    this.validAfter = validAfter;
  }
  
  public Date getFreshUntil() {
    return freshUntil;
  }
  
  public void setFreshUntil(Date freshUntil) {
    this.freshUntil = freshUntil;
  }
  
  public Date getValidUntil() {
    return validUntil;
  }
  
  public void setValidUntil(Date validUntil) {
    this.validUntil = validUntil;
  }
  
  public Map<Fingerprint, RouterStatusDescription> getFingerprintsNetworkStatusDescriptors() {
    return fingerprintsNetworkStatusDescriptors;
  }
  
  public void setFingerprintsNetworkStatusDescriptors(Map<Fingerprint, RouterStatusDescription> fingerprintsNetworkStatusDescriptors)
  {
    this.fingerprintsNetworkStatusDescriptors = fingerprintsNetworkStatusDescriptors;
  }
}
