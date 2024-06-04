package org.spongycastle.jce;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.x509.KeyUsage;




















public class X509KeyUsage
  extends ASN1Object
{
  public static final int digitalSignature = 128;
  public static final int nonRepudiation = 64;
  public static final int keyEncipherment = 32;
  public static final int dataEncipherment = 16;
  public static final int keyAgreement = 8;
  public static final int keyCertSign = 4;
  public static final int cRLSign = 2;
  public static final int encipherOnly = 1;
  public static final int decipherOnly = 32768;
  private int usage = 0;
  








  public X509KeyUsage(int usage)
  {
    this.usage = usage;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new KeyUsage(usage).toASN1Primitive();
  }
}
