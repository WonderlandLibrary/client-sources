package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.GeneralName;

public class SinglePubInfo
  extends ASN1Object
{
  private ASN1Integer pubMethod;
  private GeneralName pubLocation;
  
  private SinglePubInfo(ASN1Sequence seq)
  {
    pubMethod = ASN1Integer.getInstance(seq.getObjectAt(0));
    
    if (seq.size() == 2)
    {
      pubLocation = GeneralName.getInstance(seq.getObjectAt(1));
    }
  }
  
  public static SinglePubInfo getInstance(Object o)
  {
    if ((o instanceof SinglePubInfo))
    {
      return (SinglePubInfo)o;
    }
    
    if (o != null)
    {
      return new SinglePubInfo(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  public GeneralName getPubLocation()
  {
    return pubLocation;
  }
  












  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(pubMethod);
    
    if (pubLocation != null)
    {
      v.add(pubLocation);
    }
    
    return new DERSequence(v);
  }
}
