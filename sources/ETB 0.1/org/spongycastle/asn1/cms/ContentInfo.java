package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.BERTaggedObject;































public class ContentInfo
  extends ASN1Object
  implements CMSObjectIdentifiers
{
  private ASN1ObjectIdentifier contentType;
  private ASN1Encodable content;
  
  public static ContentInfo getInstance(Object obj)
  {
    if ((obj instanceof ContentInfo))
    {
      return (ContentInfo)obj;
    }
    if (obj != null)
    {
      return new ContentInfo(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  


  public static ContentInfo getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  

  /**
   * @deprecated
   */
  public ContentInfo(ASN1Sequence seq)
  {
    if ((seq.size() < 1) || (seq.size() > 2))
    {
      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    
    contentType = ((ASN1ObjectIdentifier)seq.getObjectAt(0));
    
    if (seq.size() > 1)
    {
      ASN1TaggedObject tagged = (ASN1TaggedObject)seq.getObjectAt(1);
      if ((!tagged.isExplicit()) || (tagged.getTagNo() != 0))
      {
        throw new IllegalArgumentException("Bad tag for 'content'");
      }
      
      content = tagged.getObject();
    }
  }
  


  public ContentInfo(ASN1ObjectIdentifier contentType, ASN1Encodable content)
  {
    this.contentType = contentType;
    this.content = content;
  }
  
  public ASN1ObjectIdentifier getContentType()
  {
    return contentType;
  }
  
  public ASN1Encodable getContent()
  {
    return content;
  }
  



  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(contentType);
    
    if (content != null)
    {
      v.add(new BERTaggedObject(0, content));
    }
    
    return new BERSequence(v);
  }
}
