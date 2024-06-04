package org.spongycastle.pqc.jcajce.provider.gmss;

import java.security.PublicKey;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.pqc.asn1.GMSSPublicKey;
import org.spongycastle.pqc.asn1.PQCObjectIdentifiers;
import org.spongycastle.pqc.asn1.ParSet;
import org.spongycastle.pqc.crypto.gmss.GMSSParameters;
import org.spongycastle.pqc.crypto.gmss.GMSSPublicKeyParameters;
import org.spongycastle.pqc.jcajce.provider.util.KeyUtil;
import org.spongycastle.util.encoders.Hex;





























public class BCGMSSPublicKey
  implements CipherParameters, PublicKey
{
  private static final long serialVersionUID = 1L;
  private byte[] publicKeyBytes;
  private GMSSParameters gmssParameterSet;
  private GMSSParameters gmssParams;
  
  public BCGMSSPublicKey(byte[] pub, GMSSParameters gmssParameterSet)
  {
    this.gmssParameterSet = gmssParameterSet;
    publicKeyBytes = pub;
  }
  

  public BCGMSSPublicKey(GMSSPublicKeyParameters params)
  {
    this(params.getPublicKey(), params.getParameters());
  }
  





  public String getAlgorithm()
  {
    return "GMSS";
  }
  



  public byte[] getPublicKeyBytes()
  {
    return publicKeyBytes;
  }
  



  public GMSSParameters getParameterSet()
  {
    return gmssParameterSet;
  }
  






  public String toString()
  {
    String out = "GMSS public key : " + new String(Hex.encode(publicKeyBytes)) + "\nHeight of Trees: \n";
    

    for (int i = 0; i < gmssParameterSet.getHeightOfTrees().length; i++)
    {




      out = out + "Layer " + i + " : " + gmssParameterSet.getHeightOfTrees()[i] + " WinternitzParameter: " + gmssParameterSet.getWinternitzParameter()[i] + " K: " + gmssParameterSet.getK()[i] + "\n";
    }
    return out;
  }
  
  public byte[] getEncoded()
  {
    return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.gmss, new ParSet(gmssParameterSet.getNumOfLayers(), gmssParameterSet.getHeightOfTrees(), gmssParameterSet.getWinternitzParameter(), gmssParameterSet.getK()).toASN1Primitive()), new GMSSPublicKey(publicKeyBytes));
  }
  
  public String getFormat()
  {
    return "X.509";
  }
}
