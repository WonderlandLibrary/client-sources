package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.DLSequence;

public class AuthenticatedSafe
  extends ASN1Object
{
  private ContentInfo[] info;
  private boolean isBer = true;
  

  private AuthenticatedSafe(ASN1Sequence seq)
  {
    info = new ContentInfo[seq.size()];
    
    for (int i = 0; i != info.length; i++)
    {
      info[i] = ContentInfo.getInstance(seq.getObjectAt(i));
    }
    
    isBer = (seq instanceof BERSequence);
  }
  

  public static AuthenticatedSafe getInstance(Object o)
  {
    if ((o instanceof AuthenticatedSafe))
    {
      return (AuthenticatedSafe)o;
    }
    
    if (o != null)
    {
      return new AuthenticatedSafe(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  

  public AuthenticatedSafe(ContentInfo[] info)
  {
    this.info = info;
  }
  
  public ContentInfo[] getContentInfo()
  {
    return info;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    for (int i = 0; i != info.length; i++)
    {
      v.add(info[i]);
    }
    
    if (isBer)
    {
      return new BERSequence(v);
    }
    

    return new DLSequence(v);
  }
}
