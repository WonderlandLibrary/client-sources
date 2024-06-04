package org.spongycastle.asn1.bc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.util.Arrays;









public class SecretKeyData
  extends ASN1Object
{
  private final ASN1ObjectIdentifier keyAlgorithm;
  private final ASN1OctetString keyBytes;
  
  public SecretKeyData(ASN1ObjectIdentifier keyAlgorithm, byte[] keyBytes)
  {
    this.keyAlgorithm = keyAlgorithm;
    this.keyBytes = new DEROctetString(Arrays.clone(keyBytes));
  }
  
  private SecretKeyData(ASN1Sequence seq)
  {
    keyAlgorithm = ASN1ObjectIdentifier.getInstance(seq.getObjectAt(0));
    keyBytes = ASN1OctetString.getInstance(seq.getObjectAt(1));
  }
  
  public static SecretKeyData getInstance(Object o)
  {
    if ((o instanceof SecretKeyData))
    {
      return (SecretKeyData)o;
    }
    if (o != null)
    {
      return new SecretKeyData(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  public byte[] getKeyBytes()
  {
    return Arrays.clone(keyBytes.getOctets());
  }
  
  public ASN1ObjectIdentifier getKeyAlgorithm()
  {
    return keyAlgorithm;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(keyAlgorithm);
    v.add(keyBytes);
    
    return new DERSequence(v);
  }
}
