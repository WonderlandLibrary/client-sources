package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.x500.X500Name;



















public class CertificateRequest
{
  protected short[] certificateTypes;
  protected Vector supportedSignatureAlgorithms;
  protected Vector certificateAuthorities;
  
  public CertificateRequest(short[] certificateTypes, Vector supportedSignatureAlgorithms, Vector certificateAuthorities)
  {
    this.certificateTypes = certificateTypes;
    this.supportedSignatureAlgorithms = supportedSignatureAlgorithms;
    this.certificateAuthorities = certificateAuthorities;
  }
  




  public short[] getCertificateTypes()
  {
    return certificateTypes;
  }
  



  public Vector getSupportedSignatureAlgorithms()
  {
    return supportedSignatureAlgorithms;
  }
  



  public Vector getCertificateAuthorities()
  {
    return certificateAuthorities;
  }
  






  public void encode(OutputStream output)
    throws IOException
  {
    if ((certificateTypes == null) || (certificateTypes.length == 0))
    {
      TlsUtils.writeUint8(0, output);
    }
    else
    {
      TlsUtils.writeUint8ArrayWithUint8Length(certificateTypes, output);
    }
    
    if (supportedSignatureAlgorithms != null)
    {

      TlsUtils.encodeSupportedSignatureAlgorithms(supportedSignatureAlgorithms, false, output);
    }
    
    if ((certificateAuthorities == null) || (certificateAuthorities.isEmpty()))
    {
      TlsUtils.writeUint16(0, output);
    }
    else
    {
      Vector derEncodings = new Vector(certificateAuthorities.size());
      
      int totalLength = 0;
      for (int i = 0; i < certificateAuthorities.size(); i++)
      {
        X500Name certificateAuthority = (X500Name)certificateAuthorities.elementAt(i);
        byte[] derEncoding = certificateAuthority.getEncoded("DER");
        derEncodings.addElement(derEncoding);
        totalLength += derEncoding.length + 2;
      }
      
      TlsUtils.checkUint16(totalLength);
      TlsUtils.writeUint16(totalLength, output);
      
      for (int i = 0; i < derEncodings.size(); i++)
      {
        byte[] derEncoding = (byte[])derEncodings.elementAt(i);
        TlsUtils.writeOpaque16(derEncoding, output);
      }
    }
  }
  










  public static CertificateRequest parse(TlsContext context, InputStream input)
    throws IOException
  {
    int numTypes = TlsUtils.readUint8(input);
    short[] certificateTypes = new short[numTypes];
    for (int i = 0; i < numTypes; i++)
    {
      certificateTypes[i] = TlsUtils.readUint8(input);
    }
    
    Vector supportedSignatureAlgorithms = null;
    if (TlsUtils.isTLSv12(context))
    {

      supportedSignatureAlgorithms = TlsUtils.parseSupportedSignatureAlgorithms(false, input);
    }
    
    Vector certificateAuthorities = new Vector();
    byte[] certAuthData = TlsUtils.readOpaque16(input);
    ByteArrayInputStream bis = new ByteArrayInputStream(certAuthData);
    while (bis.available() > 0)
    {
      byte[] derEncoding = TlsUtils.readOpaque16(bis);
      ASN1Primitive asn1 = TlsUtils.readDERObject(derEncoding);
      certificateAuthorities.addElement(X500Name.getInstance(asn1));
    }
    
    return new CertificateRequest(certificateTypes, supportedSignatureAlgorithms, certificateAuthorities);
  }
}
