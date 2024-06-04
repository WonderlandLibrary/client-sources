package org.spongycastle.asn1.bc;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.pkcs.KeyDerivationFunc;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.util.Arrays;










public class PbkdMacIntegrityCheck
  extends ASN1Object
{
  private final AlgorithmIdentifier macAlgorithm;
  private final KeyDerivationFunc pbkdAlgorithm;
  private final ASN1OctetString mac;
  
  public PbkdMacIntegrityCheck(AlgorithmIdentifier macAlgorithm, KeyDerivationFunc pbkdAlgorithm, byte[] mac)
  {
    this.macAlgorithm = macAlgorithm;
    this.pbkdAlgorithm = pbkdAlgorithm;
    this.mac = new DEROctetString(Arrays.clone(mac));
  }
  
  private PbkdMacIntegrityCheck(ASN1Sequence seq)
  {
    macAlgorithm = AlgorithmIdentifier.getInstance(seq.getObjectAt(0));
    pbkdAlgorithm = KeyDerivationFunc.getInstance(seq.getObjectAt(1));
    mac = ASN1OctetString.getInstance(seq.getObjectAt(2));
  }
  
  public static PbkdMacIntegrityCheck getInstance(Object o)
  {
    if ((o instanceof PbkdMacIntegrityCheck))
    {
      return (PbkdMacIntegrityCheck)o;
    }
    if (o != null)
    {
      return new PbkdMacIntegrityCheck(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  public AlgorithmIdentifier getMacAlgorithm()
  {
    return macAlgorithm;
  }
  
  public KeyDerivationFunc getPbkdAlgorithm()
  {
    return pbkdAlgorithm;
  }
  
  public byte[] getMac()
  {
    return Arrays.clone(mac.getOctets());
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(macAlgorithm);
    v.add(pbkdAlgorithm);
    v.add(mac);
    
    return new DERSequence(v);
  }
}
