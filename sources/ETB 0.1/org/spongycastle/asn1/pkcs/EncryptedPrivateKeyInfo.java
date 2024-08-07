package org.spongycastle.asn1.pkcs;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;



public class EncryptedPrivateKeyInfo
  extends ASN1Object
{
  private AlgorithmIdentifier algId;
  private ASN1OctetString data;
  
  private EncryptedPrivateKeyInfo(ASN1Sequence seq)
  {
    Enumeration e = seq.getObjects();
    
    algId = AlgorithmIdentifier.getInstance(e.nextElement());
    data = ASN1OctetString.getInstance(e.nextElement());
  }
  


  public EncryptedPrivateKeyInfo(AlgorithmIdentifier algId, byte[] encoding)
  {
    this.algId = algId;
    data = new DEROctetString(encoding);
  }
  

  public static EncryptedPrivateKeyInfo getInstance(Object obj)
  {
    if ((obj instanceof EncryptedPrivateKeyInfo))
    {
      return (EncryptedPrivateKeyInfo)obj;
    }
    if (obj != null)
    {
      return new EncryptedPrivateKeyInfo(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  
  public AlgorithmIdentifier getEncryptionAlgorithm()
  {
    return algId;
  }
  
  public byte[] getEncryptedData()
  {
    return data.getOctets();
  }
  















  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(algId);
    v.add(data);
    
    return new DERSequence(v);
  }
}
