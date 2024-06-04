package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;






















public class UserNotice
  extends ASN1Object
{
  private final NoticeReference noticeRef;
  private final DisplayText explicitText;
  
  public UserNotice(NoticeReference noticeRef, DisplayText explicitText)
  {
    this.noticeRef = noticeRef;
    this.explicitText = explicitText;
  }
  








  public UserNotice(NoticeReference noticeRef, String str)
  {
    this(noticeRef, new DisplayText(str));
  }
  










  private UserNotice(ASN1Sequence as)
  {
    if (as.size() == 2)
    {
      noticeRef = NoticeReference.getInstance(as.getObjectAt(0));
      explicitText = DisplayText.getInstance(as.getObjectAt(1));
    }
    else if (as.size() == 1)
    {
      if ((as.getObjectAt(0).toASN1Primitive() instanceof ASN1Sequence))
      {
        noticeRef = NoticeReference.getInstance(as.getObjectAt(0));
        explicitText = null;
      }
      else
      {
        noticeRef = null;
        explicitText = DisplayText.getInstance(as.getObjectAt(0));
      }
    }
    else if (as.size() == 0)
    {
      noticeRef = null;
      explicitText = null;
    }
    else
    {
      throw new IllegalArgumentException("Bad sequence size: " + as.size());
    }
  }
  

  public static UserNotice getInstance(Object obj)
  {
    if ((obj instanceof UserNotice))
    {
      return (UserNotice)obj;
    }
    
    if (obj != null)
    {
      return new UserNotice(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public NoticeReference getNoticeRef()
  {
    return noticeRef;
  }
  
  public DisplayText getExplicitText()
  {
    return explicitText;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector av = new ASN1EncodableVector();
    
    if (noticeRef != null)
    {
      av.add(noticeRef);
    }
    
    if (explicitText != null)
    {
      av.add(explicitText);
    }
    
    return new DERSequence(av);
  }
}
