package org.spongycastle.pqc.jcajce.provider.xmss;

import java.io.IOException;
import java.security.PublicKey;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.asn1.XMSSMTKeyParams;
import org.spongycastle.pqc.asn1.XMSSPublicKey;
import org.spongycastle.pqc.crypto.xmss.XMSSMTParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSMTPublicKeyParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSMTPublicKeyParameters.Builder;
import org.spongycastle.pqc.jcajce.interfaces.XMSSMTKey;
import org.spongycastle.util.Arrays;

public class BCXMSSMTPublicKey
  implements PublicKey, XMSSMTKey
{
  private final ASN1ObjectIdentifier treeDigest;
  private final XMSSMTPublicKeyParameters keyParams;
  
  public BCXMSSMTPublicKey(ASN1ObjectIdentifier treeDigest, XMSSMTPublicKeyParameters keyParams)
  {
    this.treeDigest = treeDigest;
    this.keyParams = keyParams;
  }
  
  public BCXMSSMTPublicKey(SubjectPublicKeyInfo keyInfo)
    throws IOException
  {
    XMSSMTKeyParams keyParams = XMSSMTKeyParams.getInstance(keyInfo.getAlgorithm().getParameters());
    treeDigest = keyParams.getTreeDigest().getAlgorithm();
    
    XMSSPublicKey xmssMtPublicKey = XMSSPublicKey.getInstance(keyInfo.parsePublicKey());
    
    this.keyParams = new XMSSMTPublicKeyParameters.Builder(new XMSSMTParameters(keyParams
      .getHeight(), keyParams.getLayers(), DigestUtil.getDigest(treeDigest)))
      .withPublicSeed(xmssMtPublicKey.getPublicSeed())
      .withRoot(xmssMtPublicKey.getRoot()).build();
  }
  
  public boolean equals(Object o)
  {
    if (o == this)
    {
      return true;
    }
    
    if ((o instanceof BCXMSSMTPublicKey))
    {
      BCXMSSMTPublicKey otherKey = (BCXMSSMTPublicKey)o;
      
      return (treeDigest.equals(treeDigest)) && (Arrays.areEqual(keyParams.toByteArray(), keyParams.toByteArray()));
    }
    
    return false;
  }
  
  public int hashCode()
  {
    return treeDigest.hashCode() + 37 * Arrays.hashCode(keyParams.toByteArray());
  }
  



  public final String getAlgorithm()
  {
    return "XMSSMT";
  }
  

  public byte[] getEncoded()
  {
    try
    {
      AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PQCObjectIdentifiers.xmss_mt, new XMSSMTKeyParams(keyParams.getParameters().getHeight(), keyParams.getParameters().getLayers(), new AlgorithmIdentifier(treeDigest)));
      SubjectPublicKeyInfo pki = new SubjectPublicKeyInfo(algorithmIdentifier, new XMSSPublicKey(keyParams.getPublicSeed(), keyParams.getRoot()));
      
      return pki.getEncoded();
    }
    catch (IOException e) {}
    
    return null;
  }
  

  public String getFormat()
  {
    return "X.509";
  }
  
  CipherParameters getKeyParams()
  {
    return keyParams;
  }
  
  public int getHeight()
  {
    return keyParams.getParameters().getHeight();
  }
  
  public int getLayers()
  {
    return keyParams.getParameters().getLayers();
  }
  
  public String getTreeDigest()
  {
    return DigestUtil.getXMSSDigestName(treeDigest);
  }
}
