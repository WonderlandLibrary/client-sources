package org.spongycastle.asn1.cms;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.ASN1TaggedObjectParser;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;














public class EncryptedContentInfoParser
{
  private ASN1ObjectIdentifier _contentType;
  private AlgorithmIdentifier _contentEncryptionAlgorithm;
  private ASN1TaggedObjectParser _encryptedContent;
  
  public EncryptedContentInfoParser(ASN1SequenceParser seq)
    throws IOException
  {
    _contentType = ((ASN1ObjectIdentifier)seq.readObject());
    _contentEncryptionAlgorithm = AlgorithmIdentifier.getInstance(seq.readObject().toASN1Primitive());
    _encryptedContent = ((ASN1TaggedObjectParser)seq.readObject());
  }
  
  public ASN1ObjectIdentifier getContentType()
  {
    return _contentType;
  }
  
  public AlgorithmIdentifier getContentEncryptionAlgorithm()
  {
    return _contentEncryptionAlgorithm;
  }
  

  public ASN1Encodable getEncryptedContent(int tag)
    throws IOException
  {
    return _encryptedContent.getObjectParser(tag, false);
  }
}
