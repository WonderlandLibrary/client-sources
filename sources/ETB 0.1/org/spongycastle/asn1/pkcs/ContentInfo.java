package org.spongycastle.asn1.pkcs;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.BERTaggedObject;
import org.spongycastle.asn1.DLSequence;


public class ContentInfo
  extends ASN1Object
  implements PKCSObjectIdentifiers
{
  private ASN1ObjectIdentifier contentType;
  private ASN1Encodable content;
  private boolean isBer = true;
  

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
  

  private ContentInfo(ASN1Sequence seq)
  {
    Enumeration e = seq.getObjects();
    
    contentType = ((ASN1ObjectIdentifier)e.nextElement());
    
    if (e.hasMoreElements())
    {
      content = ((ASN1TaggedObject)e.nextElement()).getObject();
    }
    
    isBer = (seq instanceof BERSequence);
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
      v.add(new BERTaggedObject(true, 0, content));
    }
    
    if (isBer)
    {
      return new BERSequence(v);
    }
    

    return new DLSequence(v);
  }
}
