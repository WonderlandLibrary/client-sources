package org.spongycastle.asn1.eac;

import java.io.IOException;
import org.spongycastle.asn1.ASN1ApplicationSpecific;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1ParsingException;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERApplicationSpecific;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.util.Arrays;













public class CVCertificate
  extends ASN1Object
{
  private CertificateBody certificateBody;
  private byte[] signature;
  private int valid;
  private static int bodyValid = 1;
  private static int signValid = 2;
  






  private void setPrivateData(ASN1ApplicationSpecific appSpe)
    throws IOException
  {
    valid = 0;
    if (appSpe.getApplicationTag() == 33)
    {
      ASN1InputStream content = new ASN1InputStream(appSpe.getContents());
      ASN1Primitive tmpObj;
      while ((tmpObj = content.readObject()) != null)
      {

        if ((tmpObj instanceof DERApplicationSpecific))
        {
          DERApplicationSpecific aSpe = (DERApplicationSpecific)tmpObj;
          switch (aSpe.getApplicationTag())
          {
          case 78: 
            certificateBody = CertificateBody.getInstance(aSpe);
            valid |= bodyValid;
            break;
          case 55: 
            signature = aSpe.getContents();
            valid |= signValid;
            break;
          default: 
            throw new IOException("Invalid tag, not an Iso7816CertificateStructure :" + aSpe.getApplicationTag());
          }
        }
        else
        {
          throw new IOException("Invalid Object, not an Iso7816CertificateStructure");
        }
      }
      content.close();
    }
    else
    {
      throw new IOException("not a CARDHOLDER_CERTIFICATE :" + appSpe.getApplicationTag());
    }
    
    if (valid != (signValid | bodyValid))
    {
      throw new IOException("invalid CARDHOLDER_CERTIFICATE :" + appSpe.getApplicationTag());
    }
  }
  






  public CVCertificate(ASN1InputStream aIS)
    throws IOException
  {
    initFrom(aIS);
  }
  
  private void initFrom(ASN1InputStream aIS)
    throws IOException
  {
    ASN1Primitive obj;
    while ((obj = aIS.readObject()) != null)
    {
      if ((obj instanceof DERApplicationSpecific))
      {
        setPrivateData((DERApplicationSpecific)obj);
      }
      else
      {
        throw new IOException("Invalid Input Stream for creating an Iso7816CertificateStructure");
      }
    }
  }
  







  private CVCertificate(ASN1ApplicationSpecific appSpe)
    throws IOException
  {
    setPrivateData(appSpe);
  }
  







  public CVCertificate(CertificateBody body, byte[] signature)
    throws IOException
  {
    certificateBody = body;
    this.signature = Arrays.clone(signature);
    
    valid |= bodyValid;
    valid |= signValid;
  }
  






  public static CVCertificate getInstance(Object obj)
  {
    if ((obj instanceof CVCertificate))
    {
      return (CVCertificate)obj;
    }
    if (obj != null)
    {
      try
      {
        return new CVCertificate(DERApplicationSpecific.getInstance(obj));
      }
      catch (IOException e)
      {
        throw new ASN1ParsingException("unable to parse data: " + e.getMessage(), e);
      }
    }
    
    return null;
  }
  






  public byte[] getSignature()
  {
    return Arrays.clone(signature);
  }
  





  public CertificateBody getBody()
  {
    return certificateBody;
  }
  



  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(certificateBody);
    
    try
    {
      v.add(new DERApplicationSpecific(false, 55, new DEROctetString(signature)));
    }
    catch (IOException e)
    {
      throw new IllegalStateException("unable to convert signature!");
    }
    
    return new DERApplicationSpecific(33, v);
  }
  



  public ASN1ObjectIdentifier getHolderAuthorization()
    throws IOException
  {
    CertificateHolderAuthorization cha = certificateBody.getCertificateHolderAuthorization();
    return cha.getOid();
  }
  



  public PackedDate getEffectiveDate()
    throws IOException
  {
    return certificateBody.getCertificateEffectiveDate();
  }
  







  public int getCertificateType()
  {
    return certificateBody.getCertificateType();
  }
  



  public PackedDate getExpirationDate()
    throws IOException
  {
    return certificateBody.getCertificateExpirationDate();
  }
  









  public int getRole()
    throws IOException
  {
    CertificateHolderAuthorization cha = certificateBody.getCertificateHolderAuthorization();
    return cha.getAccessRights();
  }
  




  public CertificationAuthorityReference getAuthorityReference()
    throws IOException
  {
    return certificateBody.getCertificationAuthorityReference();
  }
  




  public CertificateHolderReference getHolderReference()
    throws IOException
  {
    return certificateBody.getCertificateHolderReference();
  }
  





  public int getHolderAuthorizationRole()
    throws IOException
  {
    int rights = certificateBody.getCertificateHolderAuthorization().getAccessRights();
    return rights & 0xC0;
  }
  





  public Flags getHolderAuthorizationRights()
    throws IOException
  {
    return new Flags(certificateBody.getCertificateHolderAuthorization().getAccessRights() & 0x1F);
  }
}
