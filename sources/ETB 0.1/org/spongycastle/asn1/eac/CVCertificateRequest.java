package org.spongycastle.asn1.eac;

import java.io.IOException;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1ApplicationSpecific;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ParsingException;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERApplicationSpecific;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.util.Arrays;





public class CVCertificateRequest
  extends ASN1Object
{
  private final ASN1ApplicationSpecific original;
  private CertificateBody certificateBody;
  private byte[] innerSignature = null;
  private byte[] outerSignature = null;
  
  private static final int bodyValid = 1;
  private static final int signValid = 2;
  
  private CVCertificateRequest(ASN1ApplicationSpecific request)
    throws IOException
  {
    original = request;
    
    if ((request.isConstructed()) && (request.getApplicationTag() == 7))
    {
      ASN1Sequence seq = ASN1Sequence.getInstance(request.getObject(16));
      
      initCertBody(ASN1ApplicationSpecific.getInstance(seq.getObjectAt(0)));
      
      outerSignature = ASN1ApplicationSpecific.getInstance(seq.getObjectAt(seq.size() - 1)).getContents();
    }
    else
    {
      initCertBody(request);
    }
  }
  
  private void initCertBody(ASN1ApplicationSpecific request)
    throws IOException
  {
    if (request.getApplicationTag() == 33)
    {
      int valid = 0;
      ASN1Sequence seq = ASN1Sequence.getInstance(request.getObject(16));
      for (Enumeration en = seq.getObjects(); en.hasMoreElements();)
      {
        ASN1ApplicationSpecific obj = ASN1ApplicationSpecific.getInstance(en.nextElement());
        switch (obj.getApplicationTag())
        {
        case 78: 
          certificateBody = CertificateBody.getInstance(obj);
          valid |= 0x1;
          break;
        case 55: 
          innerSignature = obj.getContents();
          valid |= 0x2;
          break;
        default: 
          throw new IOException("Invalid tag, not an CV Certificate Request element:" + obj.getApplicationTag());
        }
      }
      if ((valid & 0x3) == 0)
      {
        throw new IOException("Invalid CARDHOLDER_CERTIFICATE in request:" + request.getApplicationTag());
      }
    }
    else
    {
      throw new IOException("not a CARDHOLDER_CERTIFICATE in request:" + request.getApplicationTag());
    }
  }
  
  public static CVCertificateRequest getInstance(Object obj)
  {
    if ((obj instanceof CVCertificateRequest))
    {
      return (CVCertificateRequest)obj;
    }
    if (obj != null)
    {
      try
      {
        return new CVCertificateRequest(ASN1ApplicationSpecific.getInstance(obj));
      }
      catch (IOException e)
      {
        throw new ASN1ParsingException("unable to parse data: " + e.getMessage(), e);
      }
    }
    
    return null;
  }
  





  public CertificateBody getCertificateBody()
  {
    return certificateBody;
  }
  




  public PublicKeyDataObject getPublicKey()
  {
    return certificateBody.getPublicKey();
  }
  
  public byte[] getInnerSignature()
  {
    return Arrays.clone(innerSignature);
  }
  
  public byte[] getOuterSignature()
  {
    return Arrays.clone(outerSignature);
  }
  
  public boolean hasOuterSignature()
  {
    return outerSignature != null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    if (original != null)
    {
      return original;
    }
    

    ASN1EncodableVector v = new ASN1EncodableVector();
    
    v.add(certificateBody);
    
    try
    {
      v.add(new DERApplicationSpecific(false, 55, new DEROctetString(innerSignature)));
    }
    catch (IOException e)
    {
      throw new IllegalStateException("unable to convert signature!");
    }
    
    return new DERApplicationSpecific(33, v);
  }
}
