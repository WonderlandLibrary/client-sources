package org.spongycastle.asn1.icao;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;


















public class LDSSecurityObject
  extends ASN1Object
  implements ICAOObjectIdentifiers
{
  public static final int ub_DataGroups = 16;
  private ASN1Integer version = new ASN1Integer(0L);
  
  private AlgorithmIdentifier digestAlgorithmIdentifier;
  private DataGroupHash[] datagroupHash;
  private LDSVersionInfo versionInfo;
  
  public static LDSSecurityObject getInstance(Object obj)
  {
    if ((obj instanceof LDSSecurityObject))
    {
      return (LDSSecurityObject)obj;
    }
    if (obj != null)
    {
      return new LDSSecurityObject(ASN1Sequence.getInstance(obj));
    }
    
    return null;
  }
  

  private LDSSecurityObject(ASN1Sequence seq)
  {
    if ((seq == null) || (seq.size() == 0))
    {
      throw new IllegalArgumentException("null or empty sequence passed.");
    }
    
    Enumeration e = seq.getObjects();
    

    version = ASN1Integer.getInstance(e.nextElement());
    
    digestAlgorithmIdentifier = AlgorithmIdentifier.getInstance(e.nextElement());
    
    ASN1Sequence datagroupHashSeq = ASN1Sequence.getInstance(e.nextElement());
    
    if (version.getValue().intValue() == 1)
    {
      versionInfo = LDSVersionInfo.getInstance(e.nextElement());
    }
    
    checkDatagroupHashSeqSize(datagroupHashSeq.size());
    
    datagroupHash = new DataGroupHash[datagroupHashSeq.size()];
    for (int i = 0; i < datagroupHashSeq.size(); i++)
    {
      datagroupHash[i] = DataGroupHash.getInstance(datagroupHashSeq.getObjectAt(i));
    }
  }
  


  public LDSSecurityObject(AlgorithmIdentifier digestAlgorithmIdentifier, DataGroupHash[] datagroupHash)
  {
    version = new ASN1Integer(0L);
    this.digestAlgorithmIdentifier = digestAlgorithmIdentifier;
    this.datagroupHash = datagroupHash;
    
    checkDatagroupHashSeqSize(datagroupHash.length);
  }
  



  public LDSSecurityObject(AlgorithmIdentifier digestAlgorithmIdentifier, DataGroupHash[] datagroupHash, LDSVersionInfo versionInfo)
  {
    version = new ASN1Integer(1L);
    this.digestAlgorithmIdentifier = digestAlgorithmIdentifier;
    this.datagroupHash = datagroupHash;
    this.versionInfo = versionInfo;
    
    checkDatagroupHashSeqSize(datagroupHash.length);
  }
  
  private void checkDatagroupHashSeqSize(int size)
  {
    if ((size < 2) || (size > 16))
    {
      throw new IllegalArgumentException("wrong size in DataGroupHashValues : not in (2..16)");
    }
  }
  
  public int getVersion()
  {
    return version.getValue().intValue();
  }
  
  public AlgorithmIdentifier getDigestAlgorithmIdentifier()
  {
    return digestAlgorithmIdentifier;
  }
  
  public DataGroupHash[] getDatagroupHash()
  {
    return datagroupHash;
  }
  
  public LDSVersionInfo getVersionInfo()
  {
    return versionInfo;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector seq = new ASN1EncodableVector();
    
    seq.add(version);
    seq.add(digestAlgorithmIdentifier);
    
    ASN1EncodableVector seqname = new ASN1EncodableVector();
    for (int i = 0; i < datagroupHash.length; i++)
    {
      seqname.add(datagroupHash[i]);
    }
    seq.add(new DERSequence(seqname));
    
    if (versionInfo != null)
    {
      seq.add(versionInfo);
    }
    
    return new DERSequence(seq);
  }
}
