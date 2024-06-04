package org.spongycastle.pqc.jcajce.provider.sphincs;

import java.io.IOException;
import java.security.PrivateKey;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.asn1.SPHINCS256KeyParams;
import org.spongycastle.pqc.crypto.sphincs.SPHINCSPrivateKeyParameters;
import org.spongycastle.pqc.jcajce.interfaces.SPHINCSKey;
import org.spongycastle.util.Arrays;





public class BCSphincs256PrivateKey
  implements PrivateKey, SPHINCSKey
{
  private static final long serialVersionUID = 1L;
  private final ASN1ObjectIdentifier treeDigest;
  private final SPHINCSPrivateKeyParameters params;
  
  public BCSphincs256PrivateKey(ASN1ObjectIdentifier treeDigest, SPHINCSPrivateKeyParameters params)
  {
    this.treeDigest = treeDigest;
    this.params = params;
  }
  
  public BCSphincs256PrivateKey(PrivateKeyInfo keyInfo)
    throws IOException
  {
    treeDigest = SPHINCS256KeyParams.getInstance(keyInfo.getPrivateKeyAlgorithm().getParameters()).getTreeDigest().getAlgorithm();
    params = new SPHINCSPrivateKeyParameters(ASN1OctetString.getInstance(keyInfo.parsePrivateKey()).getOctets());
  }
  






  public boolean equals(Object o)
  {
    if (o == this)
    {
      return true;
    }
    
    if ((o instanceof BCSphincs256PrivateKey))
    {
      BCSphincs256PrivateKey otherKey = (BCSphincs256PrivateKey)o;
      
      return (treeDigest.equals(treeDigest)) && (Arrays.areEqual(params.getKeyData(), params.getKeyData()));
    }
    
    return false;
  }
  
  public int hashCode()
  {
    return treeDigest.hashCode() + 37 * Arrays.hashCode(params.getKeyData());
  }
  



  public final String getAlgorithm()
  {
    return "SPHINCS-256";
  }
  

  public byte[] getEncoded()
  {
    try
    {
      AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PQCObjectIdentifiers.sphincs256, new SPHINCS256KeyParams(new AlgorithmIdentifier(treeDigest)));
      PrivateKeyInfo pki = new PrivateKeyInfo(algorithmIdentifier, new DEROctetString(params.getKeyData()));
      
      return pki.getEncoded();
    }
    catch (IOException e) {}
    
    return null;
  }
  

  public String getFormat()
  {
    return "PKCS#8";
  }
  
  public byte[] getKeyData()
  {
    return params.getKeyData();
  }
  
  CipherParameters getKeyParams()
  {
    return params;
  }
}
