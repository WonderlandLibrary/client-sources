package org.spongycastle.asn1.x509;

import java.io.IOException;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.util.Strings;

















































public abstract class X509NameEntryConverter
{
  public X509NameEntryConverter() {}
  
  protected ASN1Primitive convertHexEncoded(String str, int off)
    throws IOException
  {
    str = Strings.toLowerCase(str);
    byte[] data = new byte[(str.length() - off) / 2];
    for (int index = 0; index != data.length; index++)
    {
      char left = str.charAt(index * 2 + off);
      char right = str.charAt(index * 2 + off + 1);
      
      if (left < 'a')
      {
        data[index] = ((byte)(left - '0' << 4));
      }
      else
      {
        data[index] = ((byte)(left - 'a' + 10 << 4));
      }
      if (right < 'a')
      {
        int tmp99_97 = index; byte[] tmp99_96 = data;tmp99_96[tmp99_97] = ((byte)(tmp99_96[tmp99_97] | (byte)(right - '0')));
      }
      else
      {
        int tmp116_114 = index; byte[] tmp116_113 = data;tmp116_113[tmp116_114] = ((byte)(tmp116_113[tmp116_114] | (byte)(right - 'a' + 10)));
      }
    }
    
    ASN1InputStream aIn = new ASN1InputStream(data);
    
    return aIn.readObject();
  }
  





  protected boolean canBePrintable(String str)
  {
    return DERPrintableString.isPrintableString(str);
  }
  
  public abstract ASN1Primitive getConvertedValue(ASN1ObjectIdentifier paramASN1ObjectIdentifier, String paramString);
}
