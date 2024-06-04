package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.BERTaggedObject;
























public class EncryptedData
  extends ASN1Object
{
  private ASN1Integer version;
  private EncryptedContentInfo encryptedContentInfo;
  private ASN1Set unprotectedAttrs;
  
  public static EncryptedData getInstance(Object o)
  {
    if ((o instanceof EncryptedData))
    {
      return (EncryptedData)o;
    }
    
    if (o != null)
    {
      return new EncryptedData(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  public EncryptedData(EncryptedContentInfo encInfo)
  {
    this(encInfo, null);
  }
  
  public EncryptedData(EncryptedContentInfo encInfo, ASN1Set unprotectedAttrs)
  {
    version = new ASN1Integer(unprotectedAttrs == null ? 0L : 2L);
    encryptedContentInfo = encInfo;
    this.unprotectedAttrs = unprotectedAttrs;
  }
  
  private EncryptedData(ASN1Sequence seq)
  {
    version = ASN1Integer.getInstance(seq.getObjectAt(0));
    encryptedContentInfo = EncryptedContentInfo.getInstance(seq.getObjectAt(1));
    
    if (seq.size() == 3)
    {
      unprotectedAttrs = ASN1Set.getInstance((ASN1TaggedObject)seq.getObjectAt(2), false);
    }
  }
  
  public ASN1Integer getVersion()
  {
    return version;
  }
  
  public EncryptedContentInfo getEncryptedContentInfo()
  {
    return encryptedContentInfo;
  }
  
  public ASN1Set getUnprotectedAttrs()
  {
    return unprotectedAttrs;
  }
  



  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(version);
    v.add(encryptedContentInfo);
    if (unprotectedAttrs != null)
    {
      v.add(new BERTaggedObject(false, 1, unprotectedAttrs));
    }
    
    return new BERSequence(v);
  }
}
