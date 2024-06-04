package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;



public class RSASSAPSSparams
  extends ASN1Object
{
  private AlgorithmIdentifier hashAlgorithm;
  private AlgorithmIdentifier maskGenAlgorithm;
  private ASN1Integer saltLength;
  private ASN1Integer trailerField;
  public static final AlgorithmIdentifier DEFAULT_HASH_ALGORITHM = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
  public static final AlgorithmIdentifier DEFAULT_MASK_GEN_FUNCTION = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, DEFAULT_HASH_ALGORITHM);
  public static final ASN1Integer DEFAULT_SALT_LENGTH = new ASN1Integer(20L);
  public static final ASN1Integer DEFAULT_TRAILER_FIELD = new ASN1Integer(1L);
  

  public static RSASSAPSSparams getInstance(Object obj)
  {
    if ((obj instanceof RSASSAPSSparams))
    {
      return (RSASSAPSSparams)obj;
    }
    if (obj != null)
    {
      return new RSASSAPSSparams(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  



  public RSASSAPSSparams()
  {
    hashAlgorithm = DEFAULT_HASH_ALGORITHM;
    maskGenAlgorithm = DEFAULT_MASK_GEN_FUNCTION;
    saltLength = DEFAULT_SALT_LENGTH;
    trailerField = DEFAULT_TRAILER_FIELD;
  }
  




  public RSASSAPSSparams(AlgorithmIdentifier hashAlgorithm, AlgorithmIdentifier maskGenAlgorithm, ASN1Integer saltLength, ASN1Integer trailerField)
  {
    this.hashAlgorithm = hashAlgorithm;
    this.maskGenAlgorithm = maskGenAlgorithm;
    this.saltLength = saltLength;
    this.trailerField = trailerField;
  }
  

  private RSASSAPSSparams(ASN1Sequence seq)
  {
    hashAlgorithm = DEFAULT_HASH_ALGORITHM;
    maskGenAlgorithm = DEFAULT_MASK_GEN_FUNCTION;
    saltLength = DEFAULT_SALT_LENGTH;
    trailerField = DEFAULT_TRAILER_FIELD;
    
    for (int i = 0; i != seq.size(); i++)
    {
      ASN1TaggedObject o = (ASN1TaggedObject)seq.getObjectAt(i);
      
      switch (o.getTagNo())
      {
      case 0: 
        hashAlgorithm = AlgorithmIdentifier.getInstance(o, true);
        break;
      case 1: 
        maskGenAlgorithm = AlgorithmIdentifier.getInstance(o, true);
        break;
      case 2: 
        saltLength = ASN1Integer.getInstance(o, true);
        break;
      case 3: 
        trailerField = ASN1Integer.getInstance(o, true);
        break;
      default: 
        throw new IllegalArgumentException("unknown tag");
      }
    }
  }
  
  public AlgorithmIdentifier getHashAlgorithm()
  {
    return hashAlgorithm;
  }
  
  public AlgorithmIdentifier getMaskGenAlgorithm()
  {
    return maskGenAlgorithm;
  }
  
  public BigInteger getSaltLength()
  {
    return saltLength.getValue();
  }
  
  public BigInteger getTrailerField()
  {
    return trailerField.getValue();
  }
  


























  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    if (!hashAlgorithm.equals(DEFAULT_HASH_ALGORITHM))
    {
      v.add(new DERTaggedObject(true, 0, hashAlgorithm));
    }
    
    if (!maskGenAlgorithm.equals(DEFAULT_MASK_GEN_FUNCTION))
    {
      v.add(new DERTaggedObject(true, 1, maskGenAlgorithm));
    }
    
    if (!saltLength.equals(DEFAULT_SALT_LENGTH))
    {
      v.add(new DERTaggedObject(true, 2, saltLength));
    }
    
    if (!trailerField.equals(DEFAULT_TRAILER_FIELD))
    {
      v.add(new DERTaggedObject(true, 3, trailerField));
    }
    
    return new DERSequence(v);
  }
}
