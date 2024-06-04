package org.spongycastle.asn1.x509;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;





public class ExtensionsGenerator
{
  private Hashtable extensions = new Hashtable();
  private Vector extOrdering = new Vector();
  

  public ExtensionsGenerator() {}
  
  public void reset()
  {
    extensions = new Hashtable();
    extOrdering = new Vector();
  }
  











  public void addExtension(ASN1ObjectIdentifier oid, boolean critical, ASN1Encodable value)
    throws IOException
  {
    addExtension(oid, critical, value.toASN1Primitive().getEncoded("DER"));
  }
  











  public void addExtension(ASN1ObjectIdentifier oid, boolean critical, byte[] value)
  {
    if (extensions.containsKey(oid))
    {
      throw new IllegalArgumentException("extension " + oid + " already added");
    }
    
    extOrdering.addElement(oid);
    extensions.put(oid, new Extension(oid, critical, new DEROctetString(value)));
  }
  






  public void addExtension(Extension extension)
  {
    if (extensions.containsKey(extension.getExtnId()))
    {
      throw new IllegalArgumentException("extension " + extension.getExtnId() + " already added");
    }
    
    extOrdering.addElement(extension.getExtnId());
    extensions.put(extension.getExtnId(), extension);
  }
  





  public boolean isEmpty()
  {
    return extOrdering.isEmpty();
  }
  





  public Extensions generate()
  {
    Extension[] exts = new Extension[extOrdering.size()];
    
    for (int i = 0; i != extOrdering.size(); i++)
    {
      exts[i] = ((Extension)extensions.get(extOrdering.elementAt(i)));
    }
    
    return new Extensions(exts);
  }
}
