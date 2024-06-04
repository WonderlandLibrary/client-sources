package org.spongycastle.asn1.isismtt.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.GeneralName;













































































































public class AdmissionSyntax
  extends ASN1Object
{
  private GeneralName admissionAuthority;
  private ASN1Sequence contentsOfAdmissions;
  
  public static AdmissionSyntax getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof AdmissionSyntax)))
    {
      return (AdmissionSyntax)obj;
    }
    
    if ((obj instanceof ASN1Sequence))
    {
      return new AdmissionSyntax((ASN1Sequence)obj);
    }
    

    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  





































  private AdmissionSyntax(ASN1Sequence seq)
  {
    switch (seq.size())
    {
    case 1: 
      contentsOfAdmissions = DERSequence.getInstance(seq.getObjectAt(0));
      break;
    case 2: 
      admissionAuthority = GeneralName.getInstance(seq.getObjectAt(0));
      contentsOfAdmissions = DERSequence.getInstance(seq.getObjectAt(1));
      break;
    default: 
      throw new IllegalArgumentException("Bad sequence size: " + seq.size());
    }
    
  }
  





  public AdmissionSyntax(GeneralName admissionAuthority, ASN1Sequence contentsOfAdmissions)
  {
    this.admissionAuthority = admissionAuthority;
    this.contentsOfAdmissions = contentsOfAdmissions;
  }
  





































  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector vec = new ASN1EncodableVector();
    if (admissionAuthority != null)
    {
      vec.add(admissionAuthority);
    }
    vec.add(contentsOfAdmissions);
    return new DERSequence(vec);
  }
  



  public GeneralName getAdmissionAuthority()
  {
    return admissionAuthority;
  }
  



  public Admissions[] getContentsOfAdmissions()
  {
    Admissions[] admissions = new Admissions[contentsOfAdmissions.size()];
    int count = 0;
    for (Enumeration e = contentsOfAdmissions.getObjects(); e.hasMoreElements();)
    {
      admissions[(count++)] = Admissions.getInstance(e.nextElement());
    }
    return admissions;
  }
}
