package org.spongycastle.asn1.cms;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.util.Arrays;























public class CCMParameters
  extends ASN1Object
{
  private byte[] nonce;
  private int icvLen;
  
  public static CCMParameters getInstance(Object obj)
  {
    if ((obj instanceof CCMParameters))
    {
      return (CCMParameters)obj;
    }
    if (obj != null)
    {
      return new CCMParameters(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  

  private CCMParameters(ASN1Sequence seq)
  {
    nonce = ASN1OctetString.getInstance(seq.getObjectAt(0)).getOctets();
    
    if (seq.size() == 2)
    {
      icvLen = ASN1Integer.getInstance(seq.getObjectAt(1)).getValue().intValue();
    }
    else
    {
      icvLen = 12;
    }
  }
  


  public CCMParameters(byte[] nonce, int icvLen)
  {
    this.nonce = Arrays.clone(nonce);
    this.icvLen = icvLen;
  }
  
  public byte[] getNonce()
  {
    return Arrays.clone(nonce);
  }
  
  public int getIcvLen()
  {
    return icvLen;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(new DEROctetString(nonce));
    
    if (icvLen != 12)
    {
      v.add(new ASN1Integer(icvLen));
    }
    
    return new DERSequence(v);
  }
}
