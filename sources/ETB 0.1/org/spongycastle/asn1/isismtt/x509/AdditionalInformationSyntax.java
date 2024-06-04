package org.spongycastle.asn1.isismtt.x509;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.x500.DirectoryString;









public class AdditionalInformationSyntax
  extends ASN1Object
{
  private DirectoryString information;
  
  public static AdditionalInformationSyntax getInstance(Object obj)
  {
    if ((obj instanceof AdditionalInformationSyntax))
    {
      return (AdditionalInformationSyntax)obj;
    }
    
    if (obj != null)
    {
      return new AdditionalInformationSyntax(DirectoryString.getInstance(obj));
    }
    
    return null;
  }
  
  private AdditionalInformationSyntax(DirectoryString information)
  {
    this.information = information;
  }
  





  public AdditionalInformationSyntax(String information)
  {
    this(new DirectoryString(information));
  }
  
  public DirectoryString getInformation()
  {
    return information;
  }
  










  public ASN1Primitive toASN1Primitive()
  {
    return information.toASN1Primitive();
  }
}
