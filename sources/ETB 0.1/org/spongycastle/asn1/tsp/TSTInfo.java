package org.spongycastle.asn1.tsp;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.GeneralName;


public class TSTInfo
  extends ASN1Object
{
  private ASN1Integer version;
  private ASN1ObjectIdentifier tsaPolicyId;
  private MessageImprint messageImprint;
  private ASN1Integer serialNumber;
  private ASN1GeneralizedTime genTime;
  private Accuracy accuracy;
  private ASN1Boolean ordering;
  private ASN1Integer nonce;
  private GeneralName tsa;
  private Extensions extensions;
  
  public static TSTInfo getInstance(Object o)
  {
    if ((o instanceof TSTInfo))
    {
      return (TSTInfo)o;
    }
    if (o != null)
    {
      return new TSTInfo(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  private TSTInfo(ASN1Sequence seq)
  {
    Enumeration e = seq.getObjects();
    

    version = ASN1Integer.getInstance(e.nextElement());
    

    tsaPolicyId = ASN1ObjectIdentifier.getInstance(e.nextElement());
    

    messageImprint = MessageImprint.getInstance(e.nextElement());
    

    serialNumber = ASN1Integer.getInstance(e.nextElement());
    

    genTime = ASN1GeneralizedTime.getInstance(e.nextElement());
    

    ordering = ASN1Boolean.getInstance(false);
    
    while (e.hasMoreElements())
    {
      ASN1Object o = (ASN1Object)e.nextElement();
      
      if ((o instanceof ASN1TaggedObject))
      {
        ASN1TaggedObject tagged = (ASN1TaggedObject)o;
        
        switch (tagged.getTagNo())
        {
        case 0: 
          tsa = GeneralName.getInstance(tagged, true);
          break;
        case 1: 
          extensions = Extensions.getInstance(tagged, false);
          break;
        default: 
          throw new IllegalArgumentException("Unknown tag value " + tagged.getTagNo());
        }
      }
      else if (((o instanceof ASN1Sequence)) || ((o instanceof Accuracy)))
      {
        accuracy = Accuracy.getInstance(o);
      }
      else if ((o instanceof ASN1Boolean))
      {
        ordering = ASN1Boolean.getInstance(o);
      }
      else if ((o instanceof ASN1Integer))
      {
        nonce = ASN1Integer.getInstance(o);
      }
    }
  }
  




  public TSTInfo(ASN1ObjectIdentifier tsaPolicyId, MessageImprint messageImprint, ASN1Integer serialNumber, ASN1GeneralizedTime genTime, Accuracy accuracy, ASN1Boolean ordering, ASN1Integer nonce, GeneralName tsa, Extensions extensions)
  {
    version = new ASN1Integer(1L);
    this.tsaPolicyId = tsaPolicyId;
    this.messageImprint = messageImprint;
    this.serialNumber = serialNumber;
    this.genTime = genTime;
    
    this.accuracy = accuracy;
    this.ordering = ordering;
    this.nonce = nonce;
    this.tsa = tsa;
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
  
  public ASN1ObjectIdentifier getPolicy()
  {
    return tsaPolicyId;
  }
  
  public ASN1Integer getSerialNumber()
  {
    return serialNumber;
  }
  
  public Accuracy getAccuracy()
  {
    return accuracy;
  }
  
  public ASN1GeneralizedTime getGenTime()
  {
    return genTime;
  }
  
  public ASN1Boolean getOrdering()
  {
    return ordering;
  }
  
  public ASN1Integer getNonce()
  {
    return nonce;
  }
  
  public GeneralName getTsa()
  {
    return tsa;
  }
  
  public Extensions getExtensions()
  {
    return extensions;
  }
  























  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector seq = new ASN1EncodableVector();
    seq.add(version);
    
    seq.add(tsaPolicyId);
    seq.add(messageImprint);
    seq.add(serialNumber);
    seq.add(genTime);
    
    if (accuracy != null)
    {
      seq.add(accuracy);
    }
    
    if ((ordering != null) && (ordering.isTrue()))
    {
      seq.add(ordering);
    }
    
    if (nonce != null)
    {
      seq.add(nonce);
    }
    
    if (tsa != null)
    {
      seq.add(new DERTaggedObject(true, 0, tsa));
    }
    
    if (extensions != null)
    {
      seq.add(new DERTaggedObject(false, 1, extensions));
    }
    
    return new DERSequence(seq);
  }
}
