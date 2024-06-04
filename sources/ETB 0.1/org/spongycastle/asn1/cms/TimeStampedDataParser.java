package org.spongycastle.asn1.cms;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1OctetStringParser;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.DERIA5String;
















public class TimeStampedDataParser
{
  private ASN1Integer version;
  private DERIA5String dataUri;
  private MetaData metaData;
  private ASN1OctetStringParser content;
  private Evidence temporalEvidence;
  private ASN1SequenceParser parser;
  
  private TimeStampedDataParser(ASN1SequenceParser parser)
    throws IOException
  {
    this.parser = parser;
    version = ASN1Integer.getInstance(parser.readObject());
    
    ASN1Encodable obj = parser.readObject();
    
    if ((obj instanceof DERIA5String))
    {
      dataUri = DERIA5String.getInstance(obj);
      obj = parser.readObject();
    }
    if (((obj instanceof MetaData)) || ((obj instanceof ASN1SequenceParser)))
    {
      metaData = MetaData.getInstance(obj.toASN1Primitive());
      obj = parser.readObject();
    }
    if ((obj instanceof ASN1OctetStringParser))
    {
      content = ((ASN1OctetStringParser)obj);
    }
  }
  
  public static TimeStampedDataParser getInstance(Object obj)
    throws IOException
  {
    if ((obj instanceof ASN1Sequence))
    {
      return new TimeStampedDataParser(((ASN1Sequence)obj).parser());
    }
    if ((obj instanceof ASN1SequenceParser))
    {
      return new TimeStampedDataParser((ASN1SequenceParser)obj);
    }
    
    return null;
  }
  
  public DERIA5String getDataUri()
  {
    return dataUri;
  }
  
  public MetaData getMetaData()
  {
    return metaData;
  }
  
  public ASN1OctetStringParser getContent()
  {
    return content;
  }
  
  public Evidence getTemporalEvidence()
    throws IOException
  {
    if (temporalEvidence == null)
    {
      temporalEvidence = Evidence.getInstance(parser.readObject().toASN1Primitive());
    }
    
    return temporalEvidence;
  }
}
