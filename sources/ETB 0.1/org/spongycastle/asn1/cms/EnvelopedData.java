package org.spongycastle.asn1.cms;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.DERTaggedObject;

















public class EnvelopedData
  extends ASN1Object
{
  private ASN1Integer version;
  private OriginatorInfo originatorInfo;
  private ASN1Set recipientInfos;
  private EncryptedContentInfo encryptedContentInfo;
  private ASN1Set unprotectedAttrs;
  
  public EnvelopedData(OriginatorInfo originatorInfo, ASN1Set recipientInfos, EncryptedContentInfo encryptedContentInfo, ASN1Set unprotectedAttrs)
  {
    version = new ASN1Integer(calculateVersion(originatorInfo, recipientInfos, unprotectedAttrs));
    
    this.originatorInfo = originatorInfo;
    this.recipientInfos = recipientInfos;
    this.encryptedContentInfo = encryptedContentInfo;
    this.unprotectedAttrs = unprotectedAttrs;
  }
  




  public EnvelopedData(OriginatorInfo originatorInfo, ASN1Set recipientInfos, EncryptedContentInfo encryptedContentInfo, Attributes unprotectedAttrs)
  {
    version = new ASN1Integer(calculateVersion(originatorInfo, recipientInfos, ASN1Set.getInstance(unprotectedAttrs)));
    
    this.originatorInfo = originatorInfo;
    this.recipientInfos = recipientInfos;
    this.encryptedContentInfo = encryptedContentInfo;
    this.unprotectedAttrs = ASN1Set.getInstance(unprotectedAttrs);
  }
  

  /**
   * @deprecated
   */
  public EnvelopedData(ASN1Sequence seq)
  {
    int index = 0;
    
    version = ((ASN1Integer)seq.getObjectAt(index++));
    
    Object tmp = seq.getObjectAt(index++);
    
    if ((tmp instanceof ASN1TaggedObject))
    {
      originatorInfo = OriginatorInfo.getInstance((ASN1TaggedObject)tmp, false);
      tmp = seq.getObjectAt(index++);
    }
    
    recipientInfos = ASN1Set.getInstance(tmp);
    
    encryptedContentInfo = EncryptedContentInfo.getInstance(seq.getObjectAt(index++));
    
    if (seq.size() > index)
    {
      unprotectedAttrs = ASN1Set.getInstance((ASN1TaggedObject)seq.getObjectAt(index), false);
    }
  }
  











  public static EnvelopedData getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  














  public static EnvelopedData getInstance(Object obj)
  {
    if ((obj instanceof EnvelopedData))
    {
      return (EnvelopedData)obj;
    }
    
    if (obj != null)
    {
      return new EnvelopedData(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public ASN1Integer getVersion()
  {
    return version;
  }
  
  public OriginatorInfo getOriginatorInfo()
  {
    return originatorInfo;
  }
  
  public ASN1Set getRecipientInfos()
  {
    return recipientInfos;
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
    
    if (originatorInfo != null)
    {
      v.add(new DERTaggedObject(false, 0, originatorInfo));
    }
    
    v.add(recipientInfos);
    v.add(encryptedContentInfo);
    
    if (unprotectedAttrs != null)
    {
      v.add(new DERTaggedObject(false, 1, unprotectedAttrs));
    }
    
    return new BERSequence(v);
  }
  
  public static int calculateVersion(OriginatorInfo originatorInfo, ASN1Set recipientInfos, ASN1Set unprotectedAttrs)
  {
    int version;
    int version;
    if ((originatorInfo != null) || (unprotectedAttrs != null))
    {
      version = 2;
    }
    else
    {
      version = 0;
      
      Enumeration e = recipientInfos.getObjects();
      
      while (e.hasMoreElements())
      {
        RecipientInfo ri = RecipientInfo.getInstance(e.nextElement());
        
        if (ri.getVersion().getValue().intValue() != version)
        {
          version = 2;
          break;
        }
      }
    }
    
    return version;
  }
}
