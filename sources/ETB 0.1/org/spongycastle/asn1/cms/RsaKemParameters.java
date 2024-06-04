package org.spongycastle.asn1.cms;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;














public class RsaKemParameters
  extends ASN1Object
{
  private final AlgorithmIdentifier keyDerivationFunction;
  private final BigInteger keyLength;
  
  private RsaKemParameters(ASN1Sequence sequence)
  {
    if (sequence.size() != 2)
    {
      throw new IllegalArgumentException("ASN.1 SEQUENCE should be of length 2");
    }
    keyDerivationFunction = AlgorithmIdentifier.getInstance(sequence.getObjectAt(0));
    keyLength = ASN1Integer.getInstance(sequence.getObjectAt(1)).getValue();
  }
  

  public static RsaKemParameters getInstance(Object o)
  {
    if ((o instanceof RsaKemParameters))
    {
      return (RsaKemParameters)o;
    }
    if (o != null)
    {
      return new RsaKemParameters(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  






  public RsaKemParameters(AlgorithmIdentifier keyDerivationFunction, int keyLength)
  {
    this.keyDerivationFunction = keyDerivationFunction;
    this.keyLength = BigInteger.valueOf(keyLength);
  }
  
  public AlgorithmIdentifier getKeyDerivationFunction()
  {
    return keyDerivationFunction;
  }
  
  public BigInteger getKeyLength()
  {
    return keyLength;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(keyDerivationFunction);
    v.add(new ASN1Integer(keyLength));
    
    return new DERSequence(v);
  }
}
