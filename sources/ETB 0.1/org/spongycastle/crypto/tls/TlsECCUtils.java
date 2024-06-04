package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Hashtable;
import org.spongycastle.asn1.x9.ECNamedCurveTable;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.agreement.ECDHBasicAgreement;
import org.spongycastle.crypto.generators.ECKeyPairGenerator;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECCurve.F2m;
import org.spongycastle.math.ec.ECCurve.Fp;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.field.PolynomialExtensionField;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.Integers;

public class TlsECCUtils
{
  public static final Integer EXT_elliptic_curves = Integers.valueOf(10);
  public static final Integer EXT_ec_point_formats = Integers.valueOf(11);
  
  private static final String[] CURVE_NAMES = { "sect163k1", "sect163r1", "sect163r2", "sect193r1", "sect193r2", "sect233k1", "sect233r1", "sect239k1", "sect283k1", "sect283r1", "sect409k1", "sect409r1", "sect571k1", "sect571r1", "secp160k1", "secp160r1", "secp160r2", "secp192k1", "secp192r1", "secp224k1", "secp224r1", "secp256k1", "secp256r1", "secp384r1", "secp521r1", "brainpoolP256r1", "brainpoolP384r1", "brainpoolP512r1" };
  

  public TlsECCUtils() {}
  
  public static void addSupportedEllipticCurvesExtension(Hashtable extensions, int[] namedCurves)
    throws IOException
  {
    extensions.put(EXT_elliptic_curves, createSupportedEllipticCurvesExtension(namedCurves));
  }
  
  public static void addSupportedPointFormatsExtension(Hashtable extensions, short[] ecPointFormats)
    throws IOException
  {
    extensions.put(EXT_ec_point_formats, createSupportedPointFormatsExtension(ecPointFormats));
  }
  
  public static int[] getSupportedEllipticCurvesExtension(Hashtable extensions) throws IOException
  {
    byte[] extensionData = TlsUtils.getExtensionData(extensions, EXT_elliptic_curves);
    return extensionData == null ? null : readSupportedEllipticCurvesExtension(extensionData);
  }
  
  public static short[] getSupportedPointFormatsExtension(Hashtable extensions) throws IOException
  {
    byte[] extensionData = TlsUtils.getExtensionData(extensions, EXT_ec_point_formats);
    return extensionData == null ? null : readSupportedPointFormatsExtension(extensionData);
  }
  
  public static byte[] createSupportedEllipticCurvesExtension(int[] namedCurves) throws IOException
  {
    if ((namedCurves == null) || (namedCurves.length < 1))
    {
      throw new TlsFatalAlert((short)80);
    }
    
    return TlsUtils.encodeUint16ArrayWithUint16Length(namedCurves);
  }
  
  public static byte[] createSupportedPointFormatsExtension(short[] ecPointFormats) throws IOException
  {
    if ((ecPointFormats == null) || (!Arrays.contains(ecPointFormats, (short)0)))
    {






      ecPointFormats = Arrays.append(ecPointFormats, (short)0);
    }
    
    return TlsUtils.encodeUint8ArrayWithUint8Length(ecPointFormats);
  }
  
  public static int[] readSupportedEllipticCurvesExtension(byte[] extensionData) throws IOException
  {
    if (extensionData == null)
    {
      throw new IllegalArgumentException("'extensionData' cannot be null");
    }
    
    ByteArrayInputStream buf = new ByteArrayInputStream(extensionData);
    
    int length = TlsUtils.readUint16(buf);
    if ((length < 2) || ((length & 0x1) != 0))
    {
      throw new TlsFatalAlert((short)50);
    }
    
    int[] namedCurves = TlsUtils.readUint16Array(length / 2, buf);
    
    TlsProtocol.assertEmpty(buf);
    
    return namedCurves;
  }
  
  public static short[] readSupportedPointFormatsExtension(byte[] extensionData) throws IOException
  {
    if (extensionData == null)
    {
      throw new IllegalArgumentException("'extensionData' cannot be null");
    }
    
    ByteArrayInputStream buf = new ByteArrayInputStream(extensionData);
    
    short length = TlsUtils.readUint8(buf);
    if (length < 1)
    {
      throw new TlsFatalAlert((short)50);
    }
    
    short[] ecPointFormats = TlsUtils.readUint8Array(length, buf);
    
    TlsProtocol.assertEmpty(buf);
    
    if (!Arrays.contains(ecPointFormats, (short)0))
    {




      throw new TlsFatalAlert((short)47);
    }
    
    return ecPointFormats;
  }
  
  public static String getNameOfNamedCurve(int namedCurve)
  {
    return isSupportedNamedCurve(namedCurve) ? CURVE_NAMES[(namedCurve - 1)] : null;
  }
  
  public static ECDomainParameters getParametersForNamedCurve(int namedCurve)
  {
    String curveName = getNameOfNamedCurve(namedCurve);
    if (curveName == null)
    {
      return null;
    }
    


    X9ECParameters ecP = org.spongycastle.crypto.ec.CustomNamedCurves.getByName(curveName);
    if (ecP == null)
    {
      ecP = ECNamedCurveTable.getByName(curveName);
      if (ecP == null)
      {
        return null;
      }
    }
    

    return new ECDomainParameters(ecP.getCurve(), ecP.getG(), ecP.getN(), ecP.getH(), ecP.getSeed());
  }
  
  public static boolean hasAnySupportedNamedCurves()
  {
    return CURVE_NAMES.length > 0;
  }
  
  public static boolean containsECCCipherSuites(int[] cipherSuites)
  {
    for (int i = 0; i < cipherSuites.length; i++)
    {
      if (isECCCipherSuite(cipherSuites[i]))
      {
        return true;
      }
    }
    return false;
  }
  
  public static boolean isECCCipherSuite(int cipherSuite)
  {
    switch (cipherSuite)
    {






























    case 49153: 
    case 49154: 
    case 49155: 
    case 49156: 
    case 49157: 
    case 49158: 
    case 49159: 
    case 49160: 
    case 49161: 
    case 49162: 
    case 49163: 
    case 49164: 
    case 49165: 
    case 49166: 
    case 49167: 
    case 49168: 
    case 49169: 
    case 49170: 
    case 49171: 
    case 49172: 
    case 49173: 
    case 49174: 
    case 49175: 
    case 49176: 
    case 49177: 
    case 49187: 
    case 49188: 
    case 49189: 
    case 49190: 
    case 49191: 
    case 49192: 
    case 49193: 
    case 49194: 
    case 49195: 
    case 49196: 
    case 49197: 
    case 49198: 
    case 49199: 
    case 49200: 
    case 49201: 
    case 49202: 
    case 49203: 
    case 49204: 
    case 49205: 
    case 49206: 
    case 49207: 
    case 49208: 
    case 49209: 
    case 49210: 
    case 49211: 
    case 49266: 
    case 49267: 
    case 49268: 
    case 49269: 
    case 49270: 
    case 49271: 
    case 49272: 
    case 49273: 
    case 49286: 
    case 49287: 
    case 49288: 
    case 49289: 
    case 49290: 
    case 49291: 
    case 49292: 
    case 49293: 
    case 49306: 
    case 49307: 
    case 49324: 
    case 49325: 
    case 49326: 
    case 49327: 
    case 52392: 
    case 52393: 
    case 52396: 
    case 65282: 
    case 65283: 
    case 65284: 
    case 65285: 
    case 65300: 
    case 65301: 
      return true;
    }
    
    return false;
  }
  

  public static boolean areOnSameCurve(ECDomainParameters a, ECDomainParameters b)
  {
    return (a != null) && (a.equals(b));
  }
  
  public static boolean isSupportedNamedCurve(int namedCurve)
  {
    return (namedCurve > 0) && (namedCurve <= CURVE_NAMES.length);
  }
  
  public static boolean isCompressionPreferred(short[] ecPointFormats, short compressionFormat)
  {
    if (ecPointFormats == null)
    {
      return false;
    }
    for (int i = 0; i < ecPointFormats.length; i++)
    {
      short ecPointFormat = ecPointFormats[i];
      if (ecPointFormat == 0)
      {
        return false;
      }
      if (ecPointFormat == compressionFormat)
      {
        return true;
      }
    }
    return false;
  }
  
  public static byte[] serializeECFieldElement(int fieldSize, BigInteger x) throws IOException
  {
    return BigIntegers.asUnsignedByteArray((fieldSize + 7) / 8, x);
  }
  
  public static byte[] serializeECPoint(short[] ecPointFormats, ECPoint point) throws IOException
  {
    ECCurve curve = point.getCurve();
    






    boolean compressed = false;
    if (ECAlgorithms.isFpCurve(curve))
    {
      compressed = isCompressionPreferred(ecPointFormats, (short)1);
    }
    else if (ECAlgorithms.isF2mCurve(curve))
    {
      compressed = isCompressionPreferred(ecPointFormats, (short)2);
    }
    return point.getEncoded(compressed);
  }
  
  public static byte[] serializeECPublicKey(short[] ecPointFormats, ECPublicKeyParameters keyParameters)
    throws IOException
  {
    return serializeECPoint(ecPointFormats, keyParameters.getQ());
  }
  
  public static BigInteger deserializeECFieldElement(int fieldSize, byte[] encoding) throws IOException
  {
    int requiredLength = (fieldSize + 7) / 8;
    if (encoding.length != requiredLength)
    {
      throw new TlsFatalAlert((short)50);
    }
    return new BigInteger(1, encoding);
  }
  
  public static ECPoint deserializeECPoint(short[] ecPointFormats, ECCurve curve, byte[] encoding) throws IOException
  {
    if ((encoding == null) || (encoding.length < 1))
    {
      throw new TlsFatalAlert((short)47);
    }
    
    short actualFormat;
    switch (encoding[0])
    {
    case 2: 
    case 3: 
      short actualFormat;
      if (ECAlgorithms.isF2mCurve(curve))
      {
        actualFormat = 2;
      } else { short actualFormat;
        if (ECAlgorithms.isFpCurve(curve))
        {
          actualFormat = 1;
        }
        else
        {
          throw new TlsFatalAlert((short)47);
        }
      }
      
      break;
    case 4: 
      actualFormat = 0;
      break;
    case 0: case 1: 
    case 5: 
    case 6: 
    case 7: 
    default: 
      throw new TlsFatalAlert((short)47);
    }
    short actualFormat;
    if ((actualFormat != 0) && ((ecPointFormats == null) || 
      (!Arrays.contains(ecPointFormats, actualFormat))))
    {
      throw new TlsFatalAlert((short)47);
    }
    
    return curve.decodePoint(encoding);
  }
  
  public static ECPublicKeyParameters deserializeECPublicKey(short[] ecPointFormats, ECDomainParameters curve_params, byte[] encoding)
    throws IOException
  {
    try
    {
      ECPoint Y = deserializeECPoint(ecPointFormats, curve_params.getCurve(), encoding);
      return new ECPublicKeyParameters(Y, curve_params);
    }
    catch (RuntimeException e)
    {
      throw new TlsFatalAlert((short)47, e);
    }
  }
  
  public static byte[] calculateECDHBasicAgreement(ECPublicKeyParameters publicKey, ECPrivateKeyParameters privateKey)
  {
    ECDHBasicAgreement basicAgreement = new ECDHBasicAgreement();
    basicAgreement.init(privateKey);
    BigInteger agreementValue = basicAgreement.calculateAgreement(publicKey);
    





    return BigIntegers.asUnsignedByteArray(basicAgreement.getFieldSize(), agreementValue);
  }
  
  public static AsymmetricCipherKeyPair generateECKeyPair(SecureRandom random, ECDomainParameters ecParams)
  {
    ECKeyPairGenerator keyPairGenerator = new ECKeyPairGenerator();
    keyPairGenerator.init(new ECKeyGenerationParameters(ecParams, random));
    return keyPairGenerator.generateKeyPair();
  }
  
  public static ECPrivateKeyParameters generateEphemeralClientKeyExchange(SecureRandom random, short[] ecPointFormats, ECDomainParameters ecParams, OutputStream output)
    throws IOException
  {
    AsymmetricCipherKeyPair kp = generateECKeyPair(random, ecParams);
    
    ECPublicKeyParameters ecPublicKey = (ECPublicKeyParameters)kp.getPublic();
    writeECPoint(ecPointFormats, ecPublicKey.getQ(), output);
    
    return (ECPrivateKeyParameters)kp.getPrivate();
  }
  


  static ECPrivateKeyParameters generateEphemeralServerKeyExchange(SecureRandom random, int[] namedCurves, short[] ecPointFormats, OutputStream output)
    throws IOException
  {
    int namedCurve = -1;
    if (namedCurves == null)
    {

      namedCurve = 23;
    }
    else
    {
      for (int i = 0; i < namedCurves.length; i++)
      {
        int entry = namedCurves[i];
        if ((NamedCurve.isValid(entry)) && (isSupportedNamedCurve(entry)))
        {
          namedCurve = entry;
          break;
        }
      }
    }
    
    ECDomainParameters ecParams = null;
    if (namedCurve >= 0)
    {
      ecParams = getParametersForNamedCurve(namedCurve);



    }
    else if (Arrays.contains(namedCurves, 65281))
    {
      ecParams = getParametersForNamedCurve(23);
    }
    else if (Arrays.contains(namedCurves, 65282))
    {
      ecParams = getParametersForNamedCurve(10);
    }
    

    if (ecParams == null)
    {




      throw new TlsFatalAlert((short)80);
    }
    
    if (namedCurve < 0)
    {
      writeExplicitECParameters(ecPointFormats, ecParams, output);
    }
    else
    {
      writeNamedECParameters(namedCurve, output);
    }
    
    return generateEphemeralClientKeyExchange(random, ecPointFormats, ecParams, output);
  }
  
  public static ECPublicKeyParameters validateECPublicKey(ECPublicKeyParameters key)
    throws IOException
  {
    return key;
  }
  
  public static int readECExponent(int fieldSize, InputStream input) throws IOException
  {
    BigInteger K = readECParameter(input);
    if (K.bitLength() < 32)
    {
      int k = K.intValue();
      if ((k > 0) && (k < fieldSize))
      {
        return k;
      }
    }
    throw new TlsFatalAlert((short)47);
  }
  
  public static BigInteger readECFieldElement(int fieldSize, InputStream input) throws IOException
  {
    return deserializeECFieldElement(fieldSize, TlsUtils.readOpaque8(input));
  }
  
  public static BigInteger readECParameter(InputStream input)
    throws IOException
  {
    return new BigInteger(1, TlsUtils.readOpaque8(input));
  }
  
  public static ECDomainParameters readECParameters(int[] namedCurves, short[] ecPointFormats, InputStream input)
    throws IOException
  {
    try
    {
      short curveType = TlsUtils.readUint8(input);
      
      switch (curveType)
      {

      case 1: 
        checkNamedCurve(namedCurves, 65281);
        
        BigInteger prime_p = readECParameter(input);
        BigInteger a = readECFieldElement(prime_p.bitLength(), input);
        BigInteger b = readECFieldElement(prime_p.bitLength(), input);
        byte[] baseEncoding = TlsUtils.readOpaque8(input);
        BigInteger order = readECParameter(input);
        BigInteger cofactor = readECParameter(input);
        ECCurve curve = new ECCurve.Fp(prime_p, a, b, order, cofactor);
        ECPoint base = deserializeECPoint(ecPointFormats, curve, baseEncoding);
        return new ECDomainParameters(curve, base, order, cofactor);
      

      case 2: 
        checkNamedCurve(namedCurves, 65282);
        
        int m = TlsUtils.readUint16(input);
        short basis = TlsUtils.readUint8(input);
        if (!ECBasisType.isValid(basis))
        {
          throw new TlsFatalAlert((short)47);
        }
        
        int k1 = readECExponent(m, input);int k2 = -1;int k3 = -1;
        if (basis == 2)
        {
          k2 = readECExponent(m, input);
          k3 = readECExponent(m, input);
        }
        
        BigInteger a = readECFieldElement(m, input);
        BigInteger b = readECFieldElement(m, input);
        byte[] baseEncoding = TlsUtils.readOpaque8(input);
        BigInteger order = readECParameter(input);
        BigInteger cofactor = readECParameter(input);
        
        ECCurve curve = basis == 2 ? new ECCurve.F2m(m, k1, k2, k3, a, b, order, cofactor) : new ECCurve.F2m(m, k1, a, b, order, cofactor);
        


        ECPoint base = deserializeECPoint(ecPointFormats, curve, baseEncoding);
        
        return new ECDomainParameters(curve, base, order, cofactor);
      

      case 3: 
        int namedCurve = TlsUtils.readUint16(input);
        if (!NamedCurve.refersToASpecificNamedCurve(namedCurve))
        {





          throw new TlsFatalAlert((short)47);
        }
        
        checkNamedCurve(namedCurves, namedCurve);
        
        return getParametersForNamedCurve(namedCurve);
      }
      
      throw new TlsFatalAlert((short)47);

    }
    catch (RuntimeException e)
    {
      throw new TlsFatalAlert((short)47, e);
    }
  }
  
  private static void checkNamedCurve(int[] namedCurves, int namedCurve) throws IOException
  {
    if ((namedCurves != null) && (!Arrays.contains(namedCurves, namedCurve)))
    {





      throw new TlsFatalAlert((short)47);
    }
  }
  
  public static void writeECExponent(int k, OutputStream output) throws IOException
  {
    BigInteger K = BigInteger.valueOf(k);
    writeECParameter(K, output);
  }
  
  public static void writeECFieldElement(ECFieldElement x, OutputStream output) throws IOException
  {
    TlsUtils.writeOpaque8(x.getEncoded(), output);
  }
  
  public static void writeECFieldElement(int fieldSize, BigInteger x, OutputStream output) throws IOException
  {
    TlsUtils.writeOpaque8(serializeECFieldElement(fieldSize, x), output);
  }
  
  public static void writeECParameter(BigInteger x, OutputStream output) throws IOException
  {
    TlsUtils.writeOpaque8(BigIntegers.asUnsignedByteArray(x), output);
  }
  
  public static void writeExplicitECParameters(short[] ecPointFormats, ECDomainParameters ecParameters, OutputStream output)
    throws IOException
  {
    ECCurve curve = ecParameters.getCurve();
    
    if (ECAlgorithms.isFpCurve(curve))
    {
      TlsUtils.writeUint8((short)1, output);
      
      writeECParameter(curve.getField().getCharacteristic(), output);
    }
    else if (ECAlgorithms.isF2mCurve(curve))
    {
      PolynomialExtensionField field = (PolynomialExtensionField)curve.getField();
      int[] exponents = field.getMinimalPolynomial().getExponentsPresent();
      
      TlsUtils.writeUint8((short)2, output);
      
      int m = exponents[(exponents.length - 1)];
      TlsUtils.checkUint16(m);
      TlsUtils.writeUint16(m, output);
      
      if (exponents.length == 3)
      {
        TlsUtils.writeUint8((short)1, output);
        writeECExponent(exponents[1], output);
      }
      else if (exponents.length == 5)
      {
        TlsUtils.writeUint8((short)2, output);
        writeECExponent(exponents[1], output);
        writeECExponent(exponents[2], output);
        writeECExponent(exponents[3], output);
      }
      else
      {
        throw new IllegalArgumentException("Only trinomial and pentomial curves are supported");
      }
    }
    else
    {
      throw new IllegalArgumentException("'ecParameters' not a known curve type");
    }
    
    writeECFieldElement(curve.getA(), output);
    writeECFieldElement(curve.getB(), output);
    TlsUtils.writeOpaque8(serializeECPoint(ecPointFormats, ecParameters.getG()), output);
    writeECParameter(ecParameters.getN(), output);
    writeECParameter(ecParameters.getH(), output);
  }
  
  public static void writeECPoint(short[] ecPointFormats, ECPoint point, OutputStream output) throws IOException
  {
    TlsUtils.writeOpaque8(serializeECPoint(ecPointFormats, point), output);
  }
  
  public static void writeNamedECParameters(int namedCurve, OutputStream output) throws IOException
  {
    if (!NamedCurve.refersToASpecificNamedCurve(namedCurve))
    {





      throw new TlsFatalAlert((short)80);
    }
    
    TlsUtils.writeUint8((short)3, output);
    TlsUtils.checkUint16(namedCurve);
    TlsUtils.writeUint16(namedCurve, output);
  }
}
