package org.spongycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAParameterSpec;
import java.security.spec.DSAPublicKeySpec;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DSAParameter;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAPublicKeyParameters;
import org.spongycastle.util.Strings;




public class JDKDSAPublicKey
  implements DSAPublicKey
{
  private static final long serialVersionUID = 1752452449903495175L;
  private BigInteger y;
  private DSAParams dsaSpec;
  
  JDKDSAPublicKey(DSAPublicKeySpec spec)
  {
    y = spec.getY();
    dsaSpec = new DSAParameterSpec(spec.getP(), spec.getQ(), spec.getG());
  }
  

  JDKDSAPublicKey(DSAPublicKey key)
  {
    y = key.getY();
    dsaSpec = key.getParams();
  }
  

  JDKDSAPublicKey(DSAPublicKeyParameters params)
  {
    y = params.getY();
    dsaSpec = new DSAParameterSpec(params.getParameters().getP(), params.getParameters().getQ(), params.getParameters().getG());
  }
  


  JDKDSAPublicKey(BigInteger y, DSAParameterSpec dsaSpec)
  {
    this.y = y;
    this.dsaSpec = dsaSpec;
  }
  




  JDKDSAPublicKey(SubjectPublicKeyInfo info)
  {
    try
    {
      derY = (ASN1Integer)info.parsePublicKey();
    }
    catch (IOException e) {
      ASN1Integer derY;
      throw new IllegalArgumentException("invalid info structure in DSA public key");
    }
    ASN1Integer derY;
    y = derY.getValue();
    
    if (isNotNull(info.getAlgorithm().getParameters()))
    {
      DSAParameter params = DSAParameter.getInstance(info.getAlgorithm().getParameters());
      
      dsaSpec = new DSAParameterSpec(params.getP(), params.getQ(), params.getG());
    }
  }
  
  private boolean isNotNull(ASN1Encodable parameters)
  {
    return (parameters != null) && (!DERNull.INSTANCE.equals(parameters));
  }
  
  public String getAlgorithm()
  {
    return "DSA";
  }
  
  public String getFormat()
  {
    return "X.509";
  }
  
  public byte[] getEncoded()
  {
    try
    {
      if (dsaSpec == null)
      {
        return new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_dsa), new ASN1Integer(y)).getEncoded("DER");
      }
      
      return new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_dsa, new DSAParameter(dsaSpec.getP(), dsaSpec.getQ(), dsaSpec.getG())), new ASN1Integer(y)).getEncoded("DER");
    }
    catch (IOException e) {}
    
    return null;
  }
  

  public DSAParams getParams()
  {
    return dsaSpec;
  }
  
  public BigInteger getY()
  {
    return y;
  }
  
  public String toString()
  {
    StringBuffer buf = new StringBuffer();
    String nl = Strings.lineSeparator();
    
    buf.append("DSA Public Key").append(nl);
    buf.append("            y: ").append(getY().toString(16)).append(nl);
    
    return buf.toString();
  }
  
  public int hashCode()
  {
    return 
      getY().hashCode() ^ getParams().getG().hashCode() ^ getParams().getP().hashCode() ^ getParams().getQ().hashCode();
  }
  

  public boolean equals(Object o)
  {
    if (!(o instanceof DSAPublicKey))
    {
      return false;
    }
    
    DSAPublicKey other = (DSAPublicKey)o;
    
    return (getY().equals(other.getY())) && 
      (getParams().getG().equals(other.getParams().getG())) && 
      (getParams().getP().equals(other.getParams().getP())) && 
      (getParams().getQ().equals(other.getParams().getQ()));
  }
  

  private void readObject(ObjectInputStream in)
    throws IOException, ClassNotFoundException
  {
    y = ((BigInteger)in.readObject());
    dsaSpec = new DSAParameterSpec((BigInteger)in.readObject(), (BigInteger)in.readObject(), (BigInteger)in.readObject());
  }
  

  private void writeObject(ObjectOutputStream out)
    throws IOException
  {
    out.writeObject(y);
    out.writeObject(dsaSpec.getP());
    out.writeObject(dsaSpec.getQ());
    out.writeObject(dsaSpec.getG());
  }
}
