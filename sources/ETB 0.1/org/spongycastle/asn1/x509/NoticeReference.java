package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.Vector;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

















public class NoticeReference
  extends ASN1Object
{
  private DisplayText organization;
  private ASN1Sequence noticeNumbers;
  
  private static ASN1EncodableVector convertVector(Vector numbers)
  {
    ASN1EncodableVector av = new ASN1EncodableVector();
    
    Enumeration it = numbers.elements();
    
    while (it.hasMoreElements())
    {
      Object o = it.nextElement();
      
      ASN1Integer di;
      if ((o instanceof BigInteger))
      {
        di = new ASN1Integer((BigInteger)o);
      } else { ASN1Integer di;
        if ((o instanceof Integer))
        {
          di = new ASN1Integer(((Integer)o).intValue());
        }
        else
        {
          throw new IllegalArgumentException(); }
      }
      ASN1Integer di;
      av.add(di);
    }
    return av;
  }
  








  public NoticeReference(String organization, Vector numbers)
  {
    this(organization, convertVector(numbers));
  }
  








  public NoticeReference(String organization, ASN1EncodableVector noticeNumbers)
  {
    this(new DisplayText(organization), noticeNumbers);
  }
  








  public NoticeReference(DisplayText organization, ASN1EncodableVector noticeNumbers)
  {
    this.organization = organization;
    this.noticeNumbers = new DERSequence(noticeNumbers);
  }
  










  private NoticeReference(ASN1Sequence as)
  {
    if (as.size() != 2)
    {

      throw new IllegalArgumentException("Bad sequence size: " + as.size());
    }
    
    organization = DisplayText.getInstance(as.getObjectAt(0));
    noticeNumbers = ASN1Sequence.getInstance(as.getObjectAt(1));
  }
  

  public static NoticeReference getInstance(Object as)
  {
    if ((as instanceof NoticeReference))
    {
      return (NoticeReference)as;
    }
    if (as != null)
    {
      return new NoticeReference(ASN1Sequence.getInstance(as));
    }
    
    return null;
  }
  
  public DisplayText getOrganization()
  {
    return organization;
  }
  
  public ASN1Integer[] getNoticeNumbers()
  {
    ASN1Integer[] tmp = new ASN1Integer[noticeNumbers.size()];
    
    for (int i = 0; i != noticeNumbers.size(); i++)
    {
      tmp[i] = ASN1Integer.getInstance(noticeNumbers.getObjectAt(i));
    }
    
    return tmp;
  }
  





  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector av = new ASN1EncodableVector();
    av.add(organization);
    av.add(noticeNumbers);
    return new DERSequence(av);
  }
}
