package org.spongycastle.jcajce.provider.asymmetric.dsa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.spec.DSAParameterSpec;
import java.security.spec.DSAPrivateKeySpec;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DSAParameter;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAPrivateKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.util.Strings;



public class BCDSAPrivateKey
  implements DSAPrivateKey, PKCS12BagAttributeCarrier
{
  private static final long serialVersionUID = -4677259546958385734L;
  private BigInteger x;
  private transient DSAParams dsaSpec;
  private transient PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();
  


  protected BCDSAPrivateKey() {}
  

  BCDSAPrivateKey(DSAPrivateKey key)
  {
    x = key.getX();
    dsaSpec = key.getParams();
  }
  

  BCDSAPrivateKey(DSAPrivateKeySpec spec)
  {
    x = spec.getX();
    dsaSpec = new DSAParameterSpec(spec.getP(), spec.getQ(), spec.getG());
  }
  

  public BCDSAPrivateKey(PrivateKeyInfo info)
    throws IOException
  {
    DSAParameter params = DSAParameter.getInstance(info.getPrivateKeyAlgorithm().getParameters());
    ASN1Integer derX = (ASN1Integer)info.parsePrivateKey();
    
    x = derX.getValue();
    dsaSpec = new DSAParameterSpec(params.getP(), params.getQ(), params.getG());
  }
  

  BCDSAPrivateKey(DSAPrivateKeyParameters params)
  {
    x = params.getX();
    dsaSpec = new DSAParameterSpec(params.getParameters().getP(), params.getParameters().getQ(), params.getParameters().getG());
  }
  
  public String getAlgorithm()
  {
    return "DSA";
  }
  





  public String getFormat()
  {
    return "PKCS#8";
  }
  






  public byte[] getEncoded()
  {
    return KeyUtil.getEncodedPrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_dsa, new DSAParameter(dsaSpec.getP(), dsaSpec.getQ(), dsaSpec.getG()).toASN1Primitive()), new ASN1Integer(getX()));
  }
  
  public DSAParams getParams()
  {
    return dsaSpec;
  }
  
  public BigInteger getX()
  {
    return x;
  }
  

  public boolean equals(Object o)
  {
    if (!(o instanceof DSAPrivateKey))
    {
      return false;
    }
    
    DSAPrivateKey other = (DSAPrivateKey)o;
    
    return (getX().equals(other.getX())) && 
      (getParams().getG().equals(other.getParams().getG())) && 
      (getParams().getP().equals(other.getParams().getP())) && 
      (getParams().getQ().equals(other.getParams().getQ()));
  }
  
  public int hashCode()
  {
    return 
      getX().hashCode() ^ getParams().getG().hashCode() ^ getParams().getP().hashCode() ^ getParams().getQ().hashCode();
  }
  


  public void setBagAttribute(ASN1ObjectIdentifier oid, ASN1Encodable attribute)
  {
    attrCarrier.setBagAttribute(oid, attribute);
  }
  

  public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier oid)
  {
    return attrCarrier.getBagAttribute(oid);
  }
  
  public Enumeration getBagAttributeKeys()
  {
    return attrCarrier.getBagAttributeKeys();
  }
  

  private void readObject(ObjectInputStream in)
    throws IOException, ClassNotFoundException
  {
    in.defaultReadObject();
    
    dsaSpec = new DSAParameterSpec((BigInteger)in.readObject(), (BigInteger)in.readObject(), (BigInteger)in.readObject());
    attrCarrier = new PKCS12BagAttributeCarrierImpl();
  }
  

  private void writeObject(ObjectOutputStream out)
    throws IOException
  {
    out.defaultWriteObject();
    
    out.writeObject(dsaSpec.getP());
    out.writeObject(dsaSpec.getQ());
    out.writeObject(dsaSpec.getG());
  }
  
  public String toString()
  {
    StringBuffer buf = new StringBuffer();
    String nl = Strings.lineSeparator();
    
    BigInteger y = getParams().getG().modPow(x, getParams().getP());
    
    buf.append("DSA Private Key [").append(DSAUtil.generateKeyFingerprint(y, getParams())).append("]").append(nl);
    buf.append("            y: ").append(y.toString(16)).append(nl);
    
    return buf.toString();
  }
}
