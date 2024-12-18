package org.spongycastle.asn1.dvcs;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.cmp.PKIStatusInfo;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.PolicyInformation;



















public class DVCSCertInfo
  extends ASN1Object
{
  private int version = 1;
  
  private DVCSRequestInformation dvReqInfo;
  
  private DigestInfo messageImprint;
  
  private ASN1Integer serialNumber;
  
  private DVCSTime responseTime;
  
  private PKIStatusInfo dvStatus;
  private PolicyInformation policy;
  private ASN1Set reqSignature;
  private ASN1Sequence certs;
  private Extensions extensions;
  private static final int DEFAULT_VERSION = 1;
  private static final int TAG_DV_STATUS = 0;
  private static final int TAG_POLICY = 1;
  private static final int TAG_REQ_SIGNATURE = 2;
  private static final int TAG_CERTS = 3;
  
  public DVCSCertInfo(DVCSRequestInformation dvReqInfo, DigestInfo messageImprint, ASN1Integer serialNumber, DVCSTime responseTime)
  {
    this.dvReqInfo = dvReqInfo;
    this.messageImprint = messageImprint;
    this.serialNumber = serialNumber;
    this.responseTime = responseTime;
  }
  
  private DVCSCertInfo(ASN1Sequence seq)
  {
    int i = 0;
    ASN1Encodable x = seq.getObjectAt(i++);
    try
    {
      ASN1Integer encVersion = ASN1Integer.getInstance(x);
      version = encVersion.getValue().intValue();
      x = seq.getObjectAt(i++);
    }
    catch (IllegalArgumentException localIllegalArgumentException) {}
    


    dvReqInfo = DVCSRequestInformation.getInstance(x);
    x = seq.getObjectAt(i++);
    messageImprint = DigestInfo.getInstance(x);
    x = seq.getObjectAt(i++);
    serialNumber = ASN1Integer.getInstance(x);
    x = seq.getObjectAt(i++);
    responseTime = DVCSTime.getInstance(x);
    
    while (i < seq.size())
    {

      x = seq.getObjectAt(i++);
      
      if ((x instanceof ASN1TaggedObject))
      {
        ASN1TaggedObject t = ASN1TaggedObject.getInstance(x);
        int tagNo = t.getTagNo();
        
        switch (tagNo)
        {
        case 0: 
          dvStatus = PKIStatusInfo.getInstance(t, false);
          break;
        case 1: 
          policy = PolicyInformation.getInstance(ASN1Sequence.getInstance(t, false));
          break;
        case 2: 
          reqSignature = ASN1Set.getInstance(t, false);
          break;
        case 3: 
          certs = ASN1Sequence.getInstance(t, false);
          break;
        default: 
          throw new IllegalArgumentException("Unknown tag encountered: " + tagNo);
        }
        
      }
      else
      {
        try
        {
          extensions = Extensions.getInstance(x);
        }
        catch (IllegalArgumentException localIllegalArgumentException1) {}
      }
    }
  }
  



  public static DVCSCertInfo getInstance(Object obj)
  {
    if ((obj instanceof DVCSCertInfo))
    {
      return (DVCSCertInfo)obj;
    }
    if (obj != null)
    {
      return new DVCSCertInfo(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  


  public static DVCSCertInfo getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    return getInstance(ASN1Sequence.getInstance(obj, explicit));
  }
  

  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    if (version != 1)
    {
      v.add(new ASN1Integer(version));
    }
    v.add(dvReqInfo);
    v.add(messageImprint);
    v.add(serialNumber);
    v.add(responseTime);
    if (dvStatus != null)
    {
      v.add(new DERTaggedObject(false, 0, dvStatus));
    }
    if (policy != null)
    {
      v.add(new DERTaggedObject(false, 1, policy));
    }
    if (reqSignature != null)
    {
      v.add(new DERTaggedObject(false, 2, reqSignature));
    }
    if (certs != null)
    {
      v.add(new DERTaggedObject(false, 3, certs));
    }
    if (extensions != null)
    {
      v.add(extensions);
    }
    
    return new DERSequence(v);
  }
  
  public String toString()
  {
    StringBuffer s = new StringBuffer();
    
    s.append("DVCSCertInfo {\n");
    
    if (version != 1)
    {
      s.append("version: " + version + "\n");
    }
    s.append("dvReqInfo: " + dvReqInfo + "\n");
    s.append("messageImprint: " + messageImprint + "\n");
    s.append("serialNumber: " + serialNumber + "\n");
    s.append("responseTime: " + responseTime + "\n");
    if (dvStatus != null)
    {
      s.append("dvStatus: " + dvStatus + "\n");
    }
    if (policy != null)
    {
      s.append("policy: " + policy + "\n");
    }
    if (reqSignature != null)
    {
      s.append("reqSignature: " + reqSignature + "\n");
    }
    if (certs != null)
    {
      s.append("certs: " + certs + "\n");
    }
    if (extensions != null)
    {
      s.append("extensions: " + extensions + "\n");
    }
    
    s.append("}\n");
    return s.toString();
  }
  
  public int getVersion()
  {
    return version;
  }
  
  private void setVersion(int version)
  {
    this.version = version;
  }
  
  public DVCSRequestInformation getDvReqInfo()
  {
    return dvReqInfo;
  }
  
  private void setDvReqInfo(DVCSRequestInformation dvReqInfo)
  {
    this.dvReqInfo = dvReqInfo;
  }
  
  public DigestInfo getMessageImprint()
  {
    return messageImprint;
  }
  
  private void setMessageImprint(DigestInfo messageImprint)
  {
    this.messageImprint = messageImprint;
  }
  
  public ASN1Integer getSerialNumber()
  {
    return serialNumber;
  }
  
  public DVCSTime getResponseTime()
  {
    return responseTime;
  }
  
  public PKIStatusInfo getDvStatus()
  {
    return dvStatus;
  }
  
  public PolicyInformation getPolicy()
  {
    return policy;
  }
  
  public ASN1Set getReqSignature()
  {
    return reqSignature;
  }
  
  public TargetEtcChain[] getCerts()
  {
    if (certs != null)
    {
      return TargetEtcChain.arrayFromSequence(certs);
    }
    
    return null;
  }
  
  public Extensions getExtensions()
  {
    return extensions;
  }
}
