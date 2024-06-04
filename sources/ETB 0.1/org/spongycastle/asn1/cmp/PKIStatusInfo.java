package org.spongycastle.asn1.cmp;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;




public class PKIStatusInfo
  extends ASN1Object
{
  ASN1Integer status;
  PKIFreeText statusString;
  DERBitString failInfo;
  
  public static PKIStatusInfo getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  

  public static PKIStatusInfo getInstance(Object obj)
  {
    if ((obj instanceof PKIStatusInfo))
    {
      return (PKIStatusInfo)obj;
    }
    if (obj != null)
    {
      return new PKIStatusInfo(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  

  private PKIStatusInfo(ASN1Sequence seq)
  {
    status = ASN1Integer.getInstance(seq.getObjectAt(0));
    
    statusString = null;
    failInfo = null;
    
    if (seq.size() > 2)
    {
      statusString = PKIFreeText.getInstance(seq.getObjectAt(1));
      failInfo = DERBitString.getInstance(seq.getObjectAt(2));
    }
    else if (seq.size() > 1)
    {
      Object obj = seq.getObjectAt(1);
      if ((obj instanceof DERBitString))
      {
        failInfo = DERBitString.getInstance(obj);
      }
      else
      {
        statusString = PKIFreeText.getInstance(obj);
      }
    }
  }
  



  public PKIStatusInfo(PKIStatus status)
  {
    this.status = ASN1Integer.getInstance(status.toASN1Primitive());
  }
  







  public PKIStatusInfo(PKIStatus status, PKIFreeText statusString)
  {
    this.status = ASN1Integer.getInstance(status.toASN1Primitive());
    this.statusString = statusString;
  }
  



  public PKIStatusInfo(PKIStatus status, PKIFreeText statusString, PKIFailureInfo failInfo)
  {
    this.status = ASN1Integer.getInstance(status.toASN1Primitive());
    this.statusString = statusString;
    this.failInfo = failInfo;
  }
  
  public BigInteger getStatus()
  {
    return status.getValue();
  }
  
  public PKIFreeText getStatusString()
  {
    return statusString;
  }
  
  public DERBitString getFailInfo()
  {
    return failInfo;
  }
  































  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(status);
    
    if (statusString != null)
    {
      v.add(statusString);
    }
    
    if (failInfo != null)
    {
      v.add(failInfo);
    }
    
    return new DERSequence(v);
  }
}
