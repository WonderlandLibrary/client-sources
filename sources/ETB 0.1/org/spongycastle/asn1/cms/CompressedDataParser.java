package org.spongycastle.asn1.cms;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;













public class CompressedDataParser
{
  private ASN1Integer _version;
  private AlgorithmIdentifier _compressionAlgorithm;
  private ContentInfoParser _encapContentInfo;
  
  public CompressedDataParser(ASN1SequenceParser seq)
    throws IOException
  {
    _version = ((ASN1Integer)seq.readObject());
    _compressionAlgorithm = AlgorithmIdentifier.getInstance(seq.readObject().toASN1Primitive());
    _encapContentInfo = new ContentInfoParser((ASN1SequenceParser)seq.readObject());
  }
  
  public ASN1Integer getVersion()
  {
    return _version;
  }
  
  public AlgorithmIdentifier getCompressionAlgorithmIdentifier()
  {
    return _compressionAlgorithm;
  }
  
  public ContentInfoParser getEncapContentInfo()
  {
    return _encapContentInfo;
  }
}
