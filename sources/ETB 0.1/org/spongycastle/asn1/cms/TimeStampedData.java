package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.DERIA5String;















public class TimeStampedData
  extends ASN1Object
{
  private ASN1Integer version;
  private DERIA5String dataUri;
  private MetaData metaData;
  private ASN1OctetString content;
  private Evidence temporalEvidence;
  
  public TimeStampedData(DERIA5String dataUri, MetaData metaData, ASN1OctetString content, Evidence temporalEvidence)
  {
    version = new ASN1Integer(1L);
    this.dataUri = dataUri;
    this.metaData = metaData;
    this.content = content;
    this.temporalEvidence = temporalEvidence;
  }
  
  private TimeStampedData(ASN1Sequence seq)
  {
    version = ASN1Integer.getInstance(seq.getObjectAt(0));
    
    int index = 1;
    if ((seq.getObjectAt(index) instanceof DERIA5String))
    {
      dataUri = DERIA5String.getInstance(seq.getObjectAt(index++));
    }
    if (((seq.getObjectAt(index) instanceof MetaData)) || ((seq.getObjectAt(index) instanceof ASN1Sequence)))
    {
      metaData = MetaData.getInstance(seq.getObjectAt(index++));
    }
    if ((seq.getObjectAt(index) instanceof ASN1OctetString))
    {
      content = ASN1OctetString.getInstance(seq.getObjectAt(index++));
    }
    temporalEvidence = Evidence.getInstance(seq.getObjectAt(index));
  }
  













  public static TimeStampedData getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof TimeStampedData)))
    {
      return (TimeStampedData)obj;
    }
    return new TimeStampedData(ASN1Sequence.getInstance(obj));
  }
  
  public DERIA5String getDataUri()
  {
    return dataUri;
  }
  
  public MetaData getMetaData()
  {
    return metaData;
  }
  
  public ASN1OctetString getContent()
  {
    return content;
  }
  
  public Evidence getTemporalEvidence()
  {
    return temporalEvidence;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(version);
    
    if (dataUri != null)
    {
      v.add(dataUri);
    }
    
    if (metaData != null)
    {
      v.add(metaData);
    }
    
    if (content != null)
    {
      v.add(content);
    }
    
    v.add(temporalEvidence);
    
    return new BERSequence(v);
  }
}
