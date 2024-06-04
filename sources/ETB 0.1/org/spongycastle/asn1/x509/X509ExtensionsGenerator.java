package org.spongycastle.asn1.x509;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;



/**
 * @deprecated
 */
public class X509ExtensionsGenerator
{
  private Hashtable extensions = new Hashtable();
  private Vector extOrdering = new Vector();
  

  public X509ExtensionsGenerator() {}
  
  public void reset()
  {
    extensions = new Hashtable();
    extOrdering = new Vector();
  }
  











  public void addExtension(ASN1ObjectIdentifier oid, boolean critical, ASN1Encodable value)
  {
    try
    {
      addExtension(oid, critical, value.toASN1Primitive().getEncoded("DER"));
    }
    catch (IOException e)
    {
      throw new IllegalArgumentException("error encoding value: " + e);
    }
  }
  











  public void addExtension(ASN1ObjectIdentifier oid, boolean critical, byte[] value)
  {
    if (extensions.containsKey(oid))
    {
      throw new IllegalArgumentException("extension " + oid + " already added");
    }
    
    extOrdering.addElement(oid);
    extensions.put(oid, new X509Extension(critical, new DEROctetString(value)));
  }
  





  public boolean isEmpty()
  {
    return extOrdering.isEmpty();
  }
  





  public X509Extensions generate()
  {
    return new X509Extensions(extOrdering, extensions);
  }
}
