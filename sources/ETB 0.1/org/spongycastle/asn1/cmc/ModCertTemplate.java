package org.spongycastle.asn1.cmc;

import org.spongycastle.asn1.ASN1Boolean;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.crmf.CertTemplate;













public class ModCertTemplate
  extends ASN1Object
{
  private final BodyPartPath pkiDataReference;
  private final BodyPartList certReferences;
  private final boolean replace;
  private final CertTemplate certTemplate;
  
  public ModCertTemplate(BodyPartPath pkiDataReference, BodyPartList certReferences, boolean replace, CertTemplate certTemplate)
  {
    this.pkiDataReference = pkiDataReference;
    this.certReferences = certReferences;
    this.replace = replace;
    this.certTemplate = certTemplate;
  }
  
  private ModCertTemplate(ASN1Sequence seq)
  {
    if ((seq.size() != 4) && (seq.size() != 3))
    {
      throw new IllegalArgumentException("incorrect sequence size");
    }
    pkiDataReference = BodyPartPath.getInstance(seq.getObjectAt(0));
    certReferences = BodyPartList.getInstance(seq.getObjectAt(1));
    
    if (seq.size() == 4)
    {
      replace = ASN1Boolean.getInstance(seq.getObjectAt(2)).isTrue();
      certTemplate = CertTemplate.getInstance(seq.getObjectAt(3));
    }
    else
    {
      replace = true;
      certTemplate = CertTemplate.getInstance(seq.getObjectAt(2));
    }
  }
  
  public static ModCertTemplate getInstance(Object o)
  {
    if ((o instanceof ModCertTemplate))
    {
      return (ModCertTemplate)o;
    }
    
    if (o != null)
    {
      return new ModCertTemplate(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  public BodyPartPath getPkiDataReference()
  {
    return pkiDataReference;
  }
  
  public BodyPartList getCertReferences()
  {
    return certReferences;
  }
  
  public boolean isReplacingFields()
  {
    return replace;
  }
  
  public CertTemplate getCertTemplate()
  {
    return certTemplate;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(pkiDataReference);
    v.add(certReferences);
    if (!replace)
    {
      v.add(ASN1Boolean.getInstance(replace));
    }
    v.add(certTemplate);
    
    return new DERSequence(v);
  }
}
