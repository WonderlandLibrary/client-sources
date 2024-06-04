package org.spongycastle.pqc.jcajce.provider.xmss;

import java.io.IOException;
import java.security.PrivateKey;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.asn1.XMSSMTKeyParams;
import org.spongycastle.pqc.asn1.XMSSMTPrivateKey;
import org.spongycastle.pqc.asn1.XMSSPrivateKey;
import org.spongycastle.pqc.crypto.xmss.BDSStateMap;
import org.spongycastle.pqc.crypto.xmss.XMSSMTParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSMTPrivateKeyParameters;
import org.spongycastle.pqc.crypto.xmss.XMSSMTPrivateKeyParameters.Builder;
import org.spongycastle.pqc.crypto.xmss.XMSSUtil;
import org.spongycastle.pqc.jcajce.interfaces.XMSSMTKey;
import org.spongycastle.util.Arrays;



public class BCXMSSMTPrivateKey
  implements PrivateKey, XMSSMTKey
{
  private final ASN1ObjectIdentifier treeDigest;
  private final XMSSMTPrivateKeyParameters keyParams;
  
  public BCXMSSMTPrivateKey(ASN1ObjectIdentifier treeDigest, XMSSMTPrivateKeyParameters keyParams)
  {
    this.treeDigest = treeDigest;
    this.keyParams = keyParams;
  }
  
  public BCXMSSMTPrivateKey(PrivateKeyInfo keyInfo)
    throws IOException
  {
    XMSSMTKeyParams keyParams = XMSSMTKeyParams.getInstance(keyInfo.getPrivateKeyAlgorithm().getParameters());
    treeDigest = keyParams.getTreeDigest().getAlgorithm();
    
    XMSSPrivateKey xmssMtPrivateKey = XMSSPrivateKey.getInstance(keyInfo.parsePrivateKey());
    






    try
    {
      XMSSMTPrivateKeyParameters.Builder keyBuilder = new XMSSMTPrivateKeyParameters.Builder(new XMSSMTParameters(keyParams.getHeight(), keyParams.getLayers(), DigestUtil.getDigest(treeDigest))).withIndex(xmssMtPrivateKey.getIndex()).withSecretKeySeed(xmssMtPrivateKey.getSecretKeySeed()).withSecretKeyPRF(xmssMtPrivateKey.getSecretKeyPRF()).withPublicSeed(xmssMtPrivateKey.getPublicSeed()).withRoot(xmssMtPrivateKey.getRoot());
      
      if (xmssMtPrivateKey.getBdsState() != null)
      {
        keyBuilder.withBDSState((BDSStateMap)XMSSUtil.deserialize(xmssMtPrivateKey.getBdsState()));
      }
      
      this.keyParams = keyBuilder.build();
    }
    catch (ClassNotFoundException e)
    {
      throw new IOException("ClassNotFoundException processing BDS state: " + e.getMessage());
    }
  }
  
  public String getAlgorithm()
  {
    return "XMSSMT";
  }
  
  public String getFormat()
  {
    return "PKCS#8";
  }
  

  public byte[] getEncoded()
  {
    try
    {
      AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PQCObjectIdentifiers.xmss_mt, new XMSSMTKeyParams(keyParams.getParameters().getHeight(), keyParams.getParameters().getLayers(), new AlgorithmIdentifier(treeDigest)));
      PrivateKeyInfo pki = new PrivateKeyInfo(algorithmIdentifier, createKeyStructure());
      
      return pki.getEncoded();
    }
    catch (IOException e) {}
    
    return null;
  }
  

  CipherParameters getKeyParams()
  {
    return keyParams;
  }
  
  public boolean equals(Object o)
  {
    if (o == this)
    {
      return true;
    }
    
    if ((o instanceof BCXMSSMTPrivateKey))
    {
      BCXMSSMTPrivateKey otherKey = (BCXMSSMTPrivateKey)o;
      
      return (treeDigest.equals(treeDigest)) && (Arrays.areEqual(keyParams.toByteArray(), keyParams.toByteArray()));
    }
    
    return false;
  }
  
  public int hashCode()
  {
    return treeDigest.hashCode() + 37 * Arrays.hashCode(keyParams.toByteArray());
  }
  
  private XMSSMTPrivateKey createKeyStructure()
  {
    byte[] keyData = keyParams.toByteArray();
    
    int n = keyParams.getParameters().getDigestSize();
    int totalHeight = keyParams.getParameters().getHeight();
    int indexSize = (totalHeight + 7) / 8;
    int secretKeySize = n;
    int secretKeyPRFSize = n;
    int publicSeedSize = n;
    int rootSize = n;
    
    int position = 0;
    int index = (int)XMSSUtil.bytesToXBigEndian(keyData, position, indexSize);
    if (!XMSSUtil.isIndexValid(totalHeight, index))
    {
      throw new IllegalArgumentException("index out of bounds");
    }
    position += indexSize;
    byte[] secretKeySeed = XMSSUtil.extractBytesAtOffset(keyData, position, secretKeySize);
    position += secretKeySize;
    byte[] secretKeyPRF = XMSSUtil.extractBytesAtOffset(keyData, position, secretKeyPRFSize);
    position += secretKeyPRFSize;
    byte[] publicSeed = XMSSUtil.extractBytesAtOffset(keyData, position, publicSeedSize);
    position += publicSeedSize;
    byte[] root = XMSSUtil.extractBytesAtOffset(keyData, position, rootSize);
    position += rootSize;
    
    byte[] bdsStateBinary = XMSSUtil.extractBytesAtOffset(keyData, position, keyData.length - position);
    
    return new XMSSMTPrivateKey(index, secretKeySeed, secretKeyPRF, publicSeed, root, bdsStateBinary);
  }
  
  ASN1ObjectIdentifier getTreeDigestOID()
  {
    return treeDigest;
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
