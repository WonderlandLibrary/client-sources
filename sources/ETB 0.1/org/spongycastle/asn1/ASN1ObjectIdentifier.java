package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.spongycastle.util.Arrays;













public class ASN1ObjectIdentifier
  extends ASN1Primitive
{
  private final String identifier;
  private byte[] body;
  private static final long LONG_LIMIT = 72057594037927808L;
  
  public static ASN1ObjectIdentifier getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof ASN1ObjectIdentifier)))
    {
      return (ASN1ObjectIdentifier)obj;
    }
    
    if (((obj instanceof ASN1Encodable)) && ((((ASN1Encodable)obj).toASN1Primitive() instanceof ASN1ObjectIdentifier)))
    {
      return (ASN1ObjectIdentifier)((ASN1Encodable)obj).toASN1Primitive();
    }
    
    if ((obj instanceof byte[]))
    {
      byte[] enc = (byte[])obj;
      try
      {
        return (ASN1ObjectIdentifier)fromByteArray(enc);
      }
      catch (IOException e)
      {
        throw new IllegalArgumentException("failed to construct object identifier from byte[]: " + e.getMessage());
      }
    }
    
    throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
  }
  












  public static ASN1ObjectIdentifier getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    ASN1Primitive o = obj.getObject();
    
    if ((explicit) || ((o instanceof ASN1ObjectIdentifier)))
    {
      return getInstance(o);
    }
    

    return fromOctetString(ASN1OctetString.getInstance(obj.getObject()).getOctets());
  }
  




  ASN1ObjectIdentifier(byte[] bytes)
  {
    StringBuffer objId = new StringBuffer();
    long value = 0L;
    BigInteger bigValue = null;
    boolean first = true;
    
    for (int i = 0; i != bytes.length; i++)
    {
      int b = bytes[i] & 0xFF;
      
      if (value <= 72057594037927808L)
      {
        value += (b & 0x7F);
        if ((b & 0x80) == 0)
        {
          if (first)
          {
            if (value < 40L)
            {
              objId.append('0');
            }
            else if (value < 80L)
            {
              objId.append('1');
              value -= 40L;
            }
            else
            {
              objId.append('2');
              value -= 80L;
            }
            first = false;
          }
          
          objId.append('.');
          objId.append(value);
          value = 0L;
        }
        else
        {
          value <<= 7;
        }
      }
      else
      {
        if (bigValue == null)
        {
          bigValue = BigInteger.valueOf(value);
        }
        bigValue = bigValue.or(BigInteger.valueOf(b & 0x7F));
        if ((b & 0x80) == 0)
        {
          if (first)
          {
            objId.append('2');
            bigValue = bigValue.subtract(BigInteger.valueOf(80L));
            first = false;
          }
          
          objId.append('.');
          objId.append(bigValue);
          bigValue = null;
          value = 0L;
        }
        else
        {
          bigValue = bigValue.shiftLeft(7);
        }
      }
    }
    
    identifier = objId.toString();
    body = Arrays.clone(bytes);
  }
  






  public ASN1ObjectIdentifier(String identifier)
  {
    if (identifier == null)
    {
      throw new IllegalArgumentException("'identifier' cannot be null");
    }
    if (!isValidIdentifier(identifier))
    {
      throw new IllegalArgumentException("string " + identifier + " not an OID");
    }
    
    this.identifier = identifier;
  }
  






  ASN1ObjectIdentifier(ASN1ObjectIdentifier oid, String branchID)
  {
    if (!isValidBranchID(branchID, 0))
    {
      throw new IllegalArgumentException("string " + branchID + " not a valid OID branch");
    }
    
    identifier = (oid.getId() + "." + branchID);
  }
  





  public String getId()
  {
    return identifier;
  }
  






  public ASN1ObjectIdentifier branch(String branchID)
  {
    return new ASN1ObjectIdentifier(this, branchID);
  }
  






  public boolean on(ASN1ObjectIdentifier stem)
  {
    String id = getId();String stemId = stem.getId();
    return (id.length() > stemId.length()) && (id.charAt(stemId.length()) == '.') && (id.startsWith(stemId));
  }
  


  private void writeField(ByteArrayOutputStream out, long fieldValue)
  {
    byte[] result = new byte[9];
    int pos = 8;
    result[pos] = ((byte)((int)fieldValue & 0x7F));
    while (fieldValue >= 128L)
    {
      fieldValue >>= 7;
      result[(--pos)] = ((byte)((int)fieldValue & 0x7F | 0x80));
    }
    out.write(result, pos, 9 - pos);
  }
  


  private void writeField(ByteArrayOutputStream out, BigInteger fieldValue)
  {
    int byteCount = (fieldValue.bitLength() + 6) / 7;
    if (byteCount == 0)
    {
      out.write(0);
    }
    else
    {
      BigInteger tmpValue = fieldValue;
      byte[] tmp = new byte[byteCount];
      for (int i = byteCount - 1; i >= 0; i--)
      {
        tmp[i] = ((byte)(tmpValue.intValue() & 0x7F | 0x80));
        tmpValue = tmpValue.shiftRight(7);
      }
      int tmp79_78 = (byteCount - 1); byte[] tmp79_74 = tmp;tmp79_74[tmp79_78] = ((byte)(tmp79_74[tmp79_78] & 0x7F));
      out.write(tmp, 0, tmp.length);
    }
  }
  
  private void doOutput(ByteArrayOutputStream aOut)
  {
    OIDTokenizer tok = new OIDTokenizer(identifier);
    int first = Integer.parseInt(tok.nextToken()) * 40;
    
    String secondToken = tok.nextToken();
    if (secondToken.length() <= 18)
    {
      writeField(aOut, first + Long.parseLong(secondToken));
    }
    else
    {
      writeField(aOut, new BigInteger(secondToken).add(BigInteger.valueOf(first)));
    }
    
    while (tok.hasMoreTokens())
    {
      String token = tok.nextToken();
      if (token.length() <= 18)
      {
        writeField(aOut, Long.parseLong(token));
      }
      else
      {
        writeField(aOut, new BigInteger(token));
      }
    }
  }
  
  private synchronized byte[] getBody()
  {
    if (body == null)
    {
      ByteArrayOutputStream bOut = new ByteArrayOutputStream();
      
      doOutput(bOut);
      
      body = bOut.toByteArray();
    }
    
    return body;
  }
  
  boolean isConstructed()
  {
    return false;
  }
  
  int encodedLength()
    throws IOException
  {
    int length = getBody().length;
    
    return 1 + StreamUtil.calculateBodyLength(length) + length;
  }
  

  void encode(ASN1OutputStream out)
    throws IOException
  {
    byte[] enc = getBody();
    
    out.write(6);
    out.writeLength(enc.length);
    out.write(enc);
  }
  
  public int hashCode()
  {
    return identifier.hashCode();
  }
  

  boolean asn1Equals(ASN1Primitive o)
  {
    if (o == this)
    {
      return true;
    }
    
    if (!(o instanceof ASN1ObjectIdentifier))
    {
      return false;
    }
    
    return identifier.equals(identifier);
  }
  
  public String toString()
  {
    return getId();
  }
  

  private static boolean isValidBranchID(String branchID, int start)
  {
    boolean periodAllowed = false;
    
    int pos = branchID.length();
    for (;;) { pos--; if (pos < start)
        return periodAllowed;
      char ch = branchID.charAt(pos);
      

      if (('0' <= ch) && (ch <= '9'))
      {
        periodAllowed = true;
      }
      else
      {
        if (ch != '.')
          break;
        if (!periodAllowed)
        {
          return false;
        }
        
        periodAllowed = false;
      }
    }
    
    return false;
    

    return periodAllowed;
  }
  

  private static boolean isValidIdentifier(String identifier)
  {
    if ((identifier.length() < 3) || (identifier.charAt(1) != '.'))
    {
      return false;
    }
    
    char first = identifier.charAt(0);
    if ((first < '0') || (first > '2'))
    {
      return false;
    }
    
    return isValidBranchID(identifier, 2);
  }
  










  public ASN1ObjectIdentifier intern()
  {
    OidHandle hdl = new OidHandle(getBody());
    ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier)pool.get(hdl);
    if (oid == null)
    {
      oid = (ASN1ObjectIdentifier)pool.putIfAbsent(hdl, this);
      if (oid == null)
      {
        oid = this;
      }
    }
    return oid;
  }
  
  private static final ConcurrentMap<OidHandle, ASN1ObjectIdentifier> pool = new ConcurrentHashMap();
  
  private static class OidHandle
  {
    private final int key;
    private final byte[] enc;
    
    OidHandle(byte[] enc)
    {
      key = Arrays.hashCode(enc);
      this.enc = enc;
    }
    
    public int hashCode()
    {
      return key;
    }
    
    public boolean equals(Object o)
    {
      if ((o instanceof OidHandle))
      {
        return Arrays.areEqual(enc, enc);
      }
      
      return false;
    }
  }
  
  static ASN1ObjectIdentifier fromOctetString(byte[] enc)
  {
    OidHandle hdl = new OidHandle(enc);
    ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier)pool.get(hdl);
    if (oid == null)
    {
      return new ASN1ObjectIdentifier(enc);
    }
    return oid;
  }
}
