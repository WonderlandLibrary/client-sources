package org.spongycastle.asn1.tsp;

import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Extensions;






public class TimeStampReq
  extends ASN1Object
{
  ASN1Integer version;
  MessageImprint messageImprint;
  ASN1ObjectIdentifier tsaPolicy;
  ASN1Integer nonce;
  ASN1Boolean certReq;
  Extensions extensions;
  
  public static TimeStampReq getInstance(Object o)
  {
    if ((o instanceof TimeStampReq))
    {
      return (TimeStampReq)o;
    }
    if (o != null)
    {
      return new TimeStampReq(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  private TimeStampReq(ASN1Sequence seq)
  {
    int nbObjects = seq.size();
    
    int seqStart = 0;
    

    version = ASN1Integer.getInstance(seq.getObjectAt(seqStart));
    
    seqStart++;
    

    messageImprint = MessageImprint.getInstance(seq.getObjectAt(seqStart));
    
    seqStart++;
    
    for (int opt = seqStart; opt < nbObjects; opt++)
    {

      if ((seq.getObjectAt(opt) instanceof ASN1ObjectIdentifier))
      {
        tsaPolicy = ASN1ObjectIdentifier.getInstance(seq.getObjectAt(opt));

      }
      else if ((seq.getObjectAt(opt) instanceof ASN1Integer))
      {
        nonce = ASN1Integer.getInstance(seq.getObjectAt(opt));

      }
      else if ((seq.getObjectAt(opt) instanceof ASN1Boolean))
      {
        certReq = ASN1Boolean.getInstance(seq.getObjectAt(opt));

      }
      else if ((seq.getObjectAt(opt) instanceof ASN1TaggedObject))
      {
        ASN1TaggedObject tagged = (ASN1TaggedObject)seq.getObjectAt(opt);
        if (tagged.getTagNo() == 0)
        {
          extensions = Extensions.getInstance(tagged, false);
        }
      }
    }
  }
  






  public TimeStampReq(MessageImprint messageImprint, ASN1ObjectIdentifier tsaPolicy, ASN1Integer nonce, ASN1Boolean certReq, Extensions extensions)
  {
    version = new ASN1Integer(1L);
    
    this.messageImprint = messageImprint;
    this.tsaPolicy = tsaPolicy;
    this.nonce = nonce;
    this.certReq = certReq;
    this.extensions = extensions;
  }
  
  public ASN1Integer getVersion()
  {
    return version;
  }
  
  public MessageImprint getMessageImprint()
  {
    return messageImprint;
  }
  
  public ASN1ObjectIdentifier getReqPolicy()
  {
    return tsaPolicy;
  }
  
  public ASN1Integer getNonce()
  {
    return nonce;
  }
  
  public ASN1Boolean getCertReq()
  {
    return certReq;
  }
  
  public Extensions getExtensions()
  {
    return extensions;
  }
  














  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(version);
    v.add(messageImprint);
    
    if (tsaPolicy != null)
    {
      v.add(tsaPolicy);
    }
    
    if (nonce != null)
    {
      v.add(nonce);
    }
    
    if ((certReq != null) && (certReq.isTrue()))
    {
      v.add(certReq);
    }
    
    if (extensions != null)
    {
      v.add(new DERTaggedObject(false, 0, extensions));
    }
    
    return new DERSequence(v);
  }
}
