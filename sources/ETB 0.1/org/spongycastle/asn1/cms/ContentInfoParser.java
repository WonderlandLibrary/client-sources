package org.spongycastle.asn1.cms;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.ASN1TaggedObjectParser;












public class ContentInfoParser
{
  private ASN1ObjectIdentifier contentType;
  private ASN1TaggedObjectParser content;
  
  public ContentInfoParser(ASN1SequenceParser seq)
    throws IOException
  {
    contentType = ((ASN1ObjectIdentifier)seq.readObject());
    content = ((ASN1TaggedObjectParser)seq.readObject());
  }
  
  public ASN1ObjectIdentifier getContentType()
  {
    return contentType;
  }
  

  public ASN1Encodable getContent(int tag)
    throws IOException
  {
    if (content != null)
    {
      return content.getObjectParser(tag, true);
    }
    
    return null;
  }
}
