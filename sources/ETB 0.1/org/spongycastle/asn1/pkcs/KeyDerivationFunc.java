package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;



public class KeyDerivationFunc
  extends ASN1Object
{
  private AlgorithmIdentifier algId;
  
  public KeyDerivationFunc(ASN1ObjectIdentifier objectId, ASN1Encodable parameters)
  {
    algId = new AlgorithmIdentifier(objectId, parameters);
  }
  

  private KeyDerivationFunc(ASN1Sequence seq)
  {
    algId = AlgorithmIdentifier.getInstance(seq);
  }
  
  public static KeyDerivationFunc getInstance(Object obj)
  {
    if ((obj instanceof KeyDerivationFunc))
    {
      return (KeyDerivationFunc)obj;
    }
    if (obj != null)
    {
      return new KeyDerivationFunc(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public ASN1ObjectIdentifier getAlgorithm()
  {
    return algId.getAlgorithm();
  }
  
  public ASN1Encodable getParameters()
  {
    return algId.getParameters();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return algId.toASN1Primitive();
  }
}
