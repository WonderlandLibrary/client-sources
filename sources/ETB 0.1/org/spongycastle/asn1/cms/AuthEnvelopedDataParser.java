package org.spongycastle.asn1.cms;

import java.io.IOException;
import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1ParsingException;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.ASN1SetParser;
import org.spongycastle.asn1.ASN1TaggedObjectParser;














public class AuthEnvelopedDataParser
{
  private ASN1SequenceParser seq;
  private ASN1Integer version;
  private ASN1Encodable nextObject;
  private boolean originatorInfoCalled;
  private EncryptedContentInfoParser authEncryptedContentInfoParser;
  
  public AuthEnvelopedDataParser(ASN1SequenceParser seq)
    throws IOException
  {
    this.seq = seq;
    

    version = ASN1Integer.getInstance(seq.readObject());
    if (version.getValue().intValue() != 0)
    {
      throw new ASN1ParsingException("AuthEnvelopedData version number must be 0");
    }
  }
  
  public ASN1Integer getVersion()
  {
    return version;
  }
  
  public OriginatorInfo getOriginatorInfo()
    throws IOException
  {
    originatorInfoCalled = true;
    
    if (nextObject == null)
    {
      nextObject = seq.readObject();
    }
    
    if (((nextObject instanceof ASN1TaggedObjectParser)) && (((ASN1TaggedObjectParser)nextObject).getTagNo() == 0))
    {
      ASN1SequenceParser originatorInfo = (ASN1SequenceParser)((ASN1TaggedObjectParser)nextObject).getObjectParser(16, false);
      nextObject = null;
      return OriginatorInfo.getInstance(originatorInfo.toASN1Primitive());
    }
    
    return null;
  }
  
  public ASN1SetParser getRecipientInfos()
    throws IOException
  {
    if (!originatorInfoCalled)
    {
      getOriginatorInfo();
    }
    
    if (nextObject == null)
    {
      nextObject = seq.readObject();
    }
    
    ASN1SetParser recipientInfos = (ASN1SetParser)nextObject;
    nextObject = null;
    return recipientInfos;
  }
  
  public EncryptedContentInfoParser getAuthEncryptedContentInfo()
    throws IOException
  {
    if (nextObject == null)
    {
      nextObject = seq.readObject();
    }
    
    if (nextObject != null)
    {
      ASN1SequenceParser o = (ASN1SequenceParser)nextObject;
      nextObject = null;
      authEncryptedContentInfoParser = new EncryptedContentInfoParser(o);
      return authEncryptedContentInfoParser;
    }
    
    return null;
  }
  
  public ASN1SetParser getAuthAttrs()
    throws IOException
  {
    if (nextObject == null)
    {
      nextObject = seq.readObject();
    }
    
    if ((nextObject instanceof ASN1TaggedObjectParser))
    {
      ASN1Encodable o = nextObject;
      nextObject = null;
      return (ASN1SetParser)((ASN1TaggedObjectParser)o).getObjectParser(17, false);
    }
    


    if (!authEncryptedContentInfoParser.getContentType().equals(CMSObjectIdentifiers.data))
    {
      throw new ASN1ParsingException("authAttrs must be present with non-data content");
    }
    
    return null;
  }
  
  public ASN1OctetString getMac()
    throws IOException
  {
    if (nextObject == null)
    {
      nextObject = seq.readObject();
    }
    
    ASN1Encodable o = nextObject;
    nextObject = null;
    
    return ASN1OctetString.getInstance(o.toASN1Primitive());
  }
  
  public ASN1SetParser getUnauthAttrs()
    throws IOException
  {
    if (nextObject == null)
    {
      nextObject = seq.readObject();
    }
    
    if (nextObject != null)
    {
      ASN1Encodable o = nextObject;
      nextObject = null;
      return (ASN1SetParser)((ASN1TaggedObjectParser)o).getObjectParser(17, false);
    }
    
    return null;
  }
}
