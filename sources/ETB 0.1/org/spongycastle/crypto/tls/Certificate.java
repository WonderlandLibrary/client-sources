package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Primitive;















public class Certificate
{
  public static final Certificate EMPTY_CHAIN = new Certificate(new org.spongycastle.asn1.x509.Certificate[0]);
  
  protected org.spongycastle.asn1.x509.Certificate[] certificateList;
  

  public Certificate(org.spongycastle.asn1.x509.Certificate[] certificateList)
  {
    if (certificateList == null)
    {
      throw new IllegalArgumentException("'certificateList' cannot be null");
    }
    
    this.certificateList = certificateList;
  }
  




  public org.spongycastle.asn1.x509.Certificate[] getCertificateList()
  {
    return cloneCertificateList();
  }
  
  public org.spongycastle.asn1.x509.Certificate getCertificateAt(int index)
  {
    return certificateList[index];
  }
  
  public int getLength()
  {
    return certificateList.length;
  }
  




  public boolean isEmpty()
  {
    return certificateList.length == 0;
  }
  






  public void encode(OutputStream output)
    throws IOException
  {
    Vector derEncodings = new Vector(certificateList.length);
    
    int totalLength = 0;
    for (int i = 0; i < certificateList.length; i++)
    {
      byte[] derEncoding = certificateList[i].getEncoded("DER");
      derEncodings.addElement(derEncoding);
      totalLength += derEncoding.length + 3;
    }
    
    TlsUtils.checkUint24(totalLength);
    TlsUtils.writeUint24(totalLength, output);
    
    for (int i = 0; i < derEncodings.size(); i++)
    {
      byte[] derEncoding = (byte[])derEncodings.elementAt(i);
      TlsUtils.writeOpaque24(derEncoding, output);
    }
  }
  







  public static Certificate parse(InputStream input)
    throws IOException
  {
    int totalLength = TlsUtils.readUint24(input);
    if (totalLength == 0)
    {
      return EMPTY_CHAIN;
    }
    
    byte[] certListData = TlsUtils.readFully(totalLength, input);
    
    ByteArrayInputStream buf = new ByteArrayInputStream(certListData);
    
    Vector certificate_list = new Vector();
    while (buf.available() > 0)
    {
      byte[] berEncoding = TlsUtils.readOpaque24(buf);
      ASN1Primitive asn1Cert = TlsUtils.readASN1Object(berEncoding);
      certificate_list.addElement(org.spongycastle.asn1.x509.Certificate.getInstance(asn1Cert));
    }
    
    org.spongycastle.asn1.x509.Certificate[] certificateList = new org.spongycastle.asn1.x509.Certificate[certificate_list.size()];
    for (int i = 0; i < certificate_list.size(); i++)
    {
      certificateList[i] = ((org.spongycastle.asn1.x509.Certificate)certificate_list.elementAt(i));
    }
    return new Certificate(certificateList);
  }
  
  protected org.spongycastle.asn1.x509.Certificate[] cloneCertificateList()
  {
    org.spongycastle.asn1.x509.Certificate[] result = new org.spongycastle.asn1.x509.Certificate[certificateList.length];
    System.arraycopy(certificateList, 0, result, 0, result.length);
    return result;
  }
}
