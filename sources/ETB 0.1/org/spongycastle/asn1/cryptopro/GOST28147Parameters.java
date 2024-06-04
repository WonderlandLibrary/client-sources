package org.spongycastle.asn1.cryptopro;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.util.Arrays;







public class GOST28147Parameters
  extends ASN1Object
{
  private ASN1OctetString iv;
  private ASN1ObjectIdentifier paramSet;
  
  public static GOST28147Parameters getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  

  public static GOST28147Parameters getInstance(Object obj)
  {
    if ((obj instanceof GOST28147Parameters))
    {
      return (GOST28147Parameters)obj;
    }
    
    if (obj != null)
    {
      return new GOST28147Parameters(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  


  public GOST28147Parameters(byte[] iv, ASN1ObjectIdentifier paramSet)
  {
    this.iv = new DEROctetString(iv);
    this.paramSet = paramSet;
  }
  

  /**
   * @deprecated
   */
  public GOST28147Parameters(ASN1Sequence seq)
  {
    Enumeration e = seq.getObjects();
    
    iv = ((ASN1OctetString)e.nextElement());
    paramSet = ((ASN1ObjectIdentifier)e.nextElement());
  }
  











  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(iv);
    v.add(paramSet);
    
    return new DERSequence(v);
  }
  





  public ASN1ObjectIdentifier getEncryptionParamSet()
  {
    return paramSet;
  }
  





  public byte[] getIV()
  {
    return Arrays.clone(iv.getOctets());
  }
}
