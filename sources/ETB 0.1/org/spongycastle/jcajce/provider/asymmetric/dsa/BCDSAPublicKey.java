package org.spongycastle.jcajce.provider.asymmetric.dsa;

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
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.util.Strings;

public class BCDSAPublicKey
  implements DSAPublicKey
{
  private static final long serialVersionUID = 1752452449903495175L;
  private static BigInteger ZERO = BigInteger.valueOf(0L);
  
  private BigInteger y;
  
  private transient DSAPublicKeyParameters lwKeyParams;
  
  private transient DSAParams dsaSpec;
  
  BCDSAPublicKey(DSAPublicKeySpec spec)
  {
    y = spec.getY();
    dsaSpec = new DSAParameterSpec(spec.getP(), spec.getQ(), spec.getG());
    lwKeyParams = new DSAPublicKeyParameters(y, DSAUtil.toDSAParameters(dsaSpec));
  }
  

  BCDSAPublicKey(DSAPublicKey key)
  {
    y = key.getY();
    dsaSpec = key.getParams();
    lwKeyParams = new DSAPublicKeyParameters(y, DSAUtil.toDSAParameters(dsaSpec));
  }
  

  BCDSAPublicKey(DSAPublicKeyParameters params)
  {
    y = params.getY();
    if (params != null)
    {
      dsaSpec = new DSAParameterSpec(params.getParameters().getP(), params.getParameters().getQ(), params.getParameters().getG());
    }
    else
    {
      dsaSpec = null;
    }
    lwKeyParams = params;
  }
  



  public BCDSAPublicKey(SubjectPublicKeyInfo info)
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
    else
    {
      dsaSpec = null;
    }
    
    lwKeyParams = new DSAPublicKeyParameters(y, DSAUtil.toDSAParameters(dsaSpec));
  }
  
  private boolean isNotNull(ASN1Encodable parameters)
  {
    return (parameters != null) && (!DERNull.INSTANCE.equals(parameters.toASN1Primitive()));
  }
  
  public String getAlgorithm()
  {
    return "DSA";
  }
  
  public String getFormat()
  {
    return "X.509";
  }
  
  DSAPublicKeyParameters engineGetKeyParameters()
  {
    return lwKeyParams;
  }
  
  public byte[] getEncoded()
  {
    if (dsaSpec == null)
    {
      return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_dsa), new ASN1Integer(y));
    }
    
    return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_dsa, new DSAParameter(dsaSpec.getP(), dsaSpec.getQ(), dsaSpec.getG()).toASN1Primitive()), new ASN1Integer(y));
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
    
    buf.append("DSA Public Key [").append(DSAUtil.generateKeyFingerprint(y, getParams())).append("]").append(nl);
    buf.append("            y: ").append(getY().toString(16)).append(nl);
    
    return buf.toString();
  }
  
  public int hashCode()
  {
    if (dsaSpec != null)
    {
      return 
        getY().hashCode() ^ getParams().getG().hashCode() ^ getParams().getP().hashCode() ^ getParams().getQ().hashCode();
    }
    

    return getY().hashCode();
  }
  


  public boolean equals(Object o)
  {
    if (!(o instanceof DSAPublicKey))
    {
      return false;
    }
    
    DSAPublicKey other = (DSAPublicKey)o;
    
    if (dsaSpec != null)
    {
      return (getY().equals(other.getY())) && 
        (other.getParams() != null) && 
        (getParams().getG().equals(other.getParams().getG())) && 
        (getParams().getP().equals(other.getParams().getP())) && 
        (getParams().getQ().equals(other.getParams().getQ()));
    }
    

    return (getY().equals(other.getY())) && (other.getParams() == null);
  }
  


  private void readObject(ObjectInputStream in)
    throws IOException, ClassNotFoundException
  {
    in.defaultReadObject();
    
    BigInteger p = (BigInteger)in.readObject();
    if (p.equals(ZERO))
    {
      dsaSpec = null;
    }
    else
    {
      dsaSpec = new DSAParameterSpec(p, (BigInteger)in.readObject(), (BigInteger)in.readObject());
    }
    lwKeyParams = new DSAPublicKeyParameters(y, DSAUtil.toDSAParameters(dsaSpec));
  }
  

  private void writeObject(ObjectOutputStream out)
    throws IOException
  {
    out.defaultWriteObject();
    
    if (dsaSpec == null)
    {
      out.writeObject(ZERO);
    }
    else
    {
      out.writeObject(dsaSpec.getP());
      out.writeObject(dsaSpec.getQ());
      out.writeObject(dsaSpec.getG());
    }
  }
}
