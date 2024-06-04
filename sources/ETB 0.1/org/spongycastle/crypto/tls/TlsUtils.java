package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.KeyUsage;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.TBSCertificate;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.digests.SHA224Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DSAPublicKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.util.PublicKeyFactory;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Integers;
import org.spongycastle.util.Shorts;
import org.spongycastle.util.Strings;
import org.spongycastle.util.io.Streams;





public class TlsUtils
{
  public static final byte[] EMPTY_BYTES = new byte[0];
  public static final short[] EMPTY_SHORTS = new short[0];
  public static final int[] EMPTY_INTS = new int[0];
  public static final long[] EMPTY_LONGS = new long[0];
  
  public static final Integer EXT_signature_algorithms = Integers.valueOf(13);
  
  public TlsUtils() {}
  
  public static void checkUint8(short i) throws IOException { if (!isValidUint8(i))
    {
      throw new TlsFatalAlert((short)80);
    }
  }
  
  public static void checkUint8(int i) throws IOException
  {
    if (!isValidUint8(i))
    {
      throw new TlsFatalAlert((short)80);
    }
  }
  
  public static void checkUint8(long i) throws IOException
  {
    if (!isValidUint8(i))
    {
      throw new TlsFatalAlert((short)80);
    }
  }
  
  public static void checkUint16(int i) throws IOException
  {
    if (!isValidUint16(i))
    {
      throw new TlsFatalAlert((short)80);
    }
  }
  
  public static void checkUint16(long i) throws IOException
  {
    if (!isValidUint16(i))
    {
      throw new TlsFatalAlert((short)80);
    }
  }
  
  public static void checkUint24(int i) throws IOException
  {
    if (!isValidUint24(i))
    {
      throw new TlsFatalAlert((short)80);
    }
  }
  
  public static void checkUint24(long i) throws IOException
  {
    if (!isValidUint24(i))
    {
      throw new TlsFatalAlert((short)80);
    }
  }
  
  public static void checkUint32(long i) throws IOException
  {
    if (!isValidUint32(i))
    {
      throw new TlsFatalAlert((short)80);
    }
  }
  
  public static void checkUint48(long i) throws IOException
  {
    if (!isValidUint48(i))
    {
      throw new TlsFatalAlert((short)80);
    }
  }
  
  public static void checkUint64(long i) throws IOException
  {
    if (!isValidUint64(i))
    {
      throw new TlsFatalAlert((short)80);
    }
  }
  
  public static boolean isValidUint8(short i)
  {
    return (i & 0xFF) == i;
  }
  
  public static boolean isValidUint8(int i)
  {
    return (i & 0xFF) == i;
  }
  
  public static boolean isValidUint8(long i)
  {
    return (i & 0xFF) == i;
  }
  
  public static boolean isValidUint16(int i)
  {
    return (i & 0xFFFF) == i;
  }
  
  public static boolean isValidUint16(long i)
  {
    return (i & 0xFFFF) == i;
  }
  
  public static boolean isValidUint24(int i)
  {
    return (i & 0xFFFFFF) == i;
  }
  
  public static boolean isValidUint24(long i)
  {
    return (i & 0xFFFFFF) == i;
  }
  
  public static boolean isValidUint32(long i)
  {
    return (i & 0xFFFFFFFF) == i;
  }
  
  public static boolean isValidUint48(long i)
  {
    return (i & 0xFFFFFFFFFFFF) == i;
  }
  
  public static boolean isValidUint64(long i)
  {
    return true;
  }
  
  public static boolean isSSL(TlsContext context)
  {
    return context.getServerVersion().isSSL();
  }
  
  public static boolean isTLSv11(ProtocolVersion version)
  {
    return ProtocolVersion.TLSv11.isEqualOrEarlierVersionOf(version.getEquivalentTLSVersion());
  }
  
  public static boolean isTLSv11(TlsContext context)
  {
    return isTLSv11(context.getServerVersion());
  }
  
  public static boolean isTLSv12(ProtocolVersion version)
  {
    return ProtocolVersion.TLSv12.isEqualOrEarlierVersionOf(version.getEquivalentTLSVersion());
  }
  
  public static boolean isTLSv12(TlsContext context)
  {
    return isTLSv12(context.getServerVersion());
  }
  
  public static void writeUint8(short i, OutputStream output)
    throws IOException
  {
    output.write(i);
  }
  
  public static void writeUint8(int i, OutputStream output)
    throws IOException
  {
    output.write(i);
  }
  
  public static void writeUint8(short i, byte[] buf, int offset)
  {
    buf[offset] = ((byte)i);
  }
  
  public static void writeUint8(int i, byte[] buf, int offset)
  {
    buf[offset] = ((byte)i);
  }
  
  public static void writeUint16(int i, OutputStream output)
    throws IOException
  {
    output.write(i >>> 8);
    output.write(i);
  }
  
  public static void writeUint16(int i, byte[] buf, int offset)
  {
    buf[offset] = ((byte)(i >>> 8));
    buf[(offset + 1)] = ((byte)i);
  }
  
  public static void writeUint24(int i, OutputStream output)
    throws IOException
  {
    output.write((byte)(i >>> 16));
    output.write((byte)(i >>> 8));
    output.write((byte)i);
  }
  
  public static void writeUint24(int i, byte[] buf, int offset)
  {
    buf[offset] = ((byte)(i >>> 16));
    buf[(offset + 1)] = ((byte)(i >>> 8));
    buf[(offset + 2)] = ((byte)i);
  }
  
  public static void writeUint32(long i, OutputStream output)
    throws IOException
  {
    output.write((byte)(int)(i >>> 24));
    output.write((byte)(int)(i >>> 16));
    output.write((byte)(int)(i >>> 8));
    output.write((byte)(int)i);
  }
  
  public static void writeUint32(long i, byte[] buf, int offset)
  {
    buf[offset] = ((byte)(int)(i >>> 24));
    buf[(offset + 1)] = ((byte)(int)(i >>> 16));
    buf[(offset + 2)] = ((byte)(int)(i >>> 8));
    buf[(offset + 3)] = ((byte)(int)i);
  }
  
  public static void writeUint48(long i, OutputStream output)
    throws IOException
  {
    output.write((byte)(int)(i >>> 40));
    output.write((byte)(int)(i >>> 32));
    output.write((byte)(int)(i >>> 24));
    output.write((byte)(int)(i >>> 16));
    output.write((byte)(int)(i >>> 8));
    output.write((byte)(int)i);
  }
  
  public static void writeUint48(long i, byte[] buf, int offset)
  {
    buf[offset] = ((byte)(int)(i >>> 40));
    buf[(offset + 1)] = ((byte)(int)(i >>> 32));
    buf[(offset + 2)] = ((byte)(int)(i >>> 24));
    buf[(offset + 3)] = ((byte)(int)(i >>> 16));
    buf[(offset + 4)] = ((byte)(int)(i >>> 8));
    buf[(offset + 5)] = ((byte)(int)i);
  }
  
  public static void writeUint64(long i, OutputStream output)
    throws IOException
  {
    output.write((byte)(int)(i >>> 56));
    output.write((byte)(int)(i >>> 48));
    output.write((byte)(int)(i >>> 40));
    output.write((byte)(int)(i >>> 32));
    output.write((byte)(int)(i >>> 24));
    output.write((byte)(int)(i >>> 16));
    output.write((byte)(int)(i >>> 8));
    output.write((byte)(int)i);
  }
  
  public static void writeUint64(long i, byte[] buf, int offset)
  {
    buf[offset] = ((byte)(int)(i >>> 56));
    buf[(offset + 1)] = ((byte)(int)(i >>> 48));
    buf[(offset + 2)] = ((byte)(int)(i >>> 40));
    buf[(offset + 3)] = ((byte)(int)(i >>> 32));
    buf[(offset + 4)] = ((byte)(int)(i >>> 24));
    buf[(offset + 5)] = ((byte)(int)(i >>> 16));
    buf[(offset + 6)] = ((byte)(int)(i >>> 8));
    buf[(offset + 7)] = ((byte)(int)i);
  }
  
  public static void writeOpaque8(byte[] buf, OutputStream output)
    throws IOException
  {
    checkUint8(buf.length);
    writeUint8(buf.length, output);
    output.write(buf);
  }
  
  public static void writeOpaque16(byte[] buf, OutputStream output)
    throws IOException
  {
    checkUint16(buf.length);
    writeUint16(buf.length, output);
    output.write(buf);
  }
  
  public static void writeOpaque24(byte[] buf, OutputStream output)
    throws IOException
  {
    checkUint24(buf.length);
    writeUint24(buf.length, output);
    output.write(buf);
  }
  
  public static void writeUint8Array(short[] uints, OutputStream output)
    throws IOException
  {
    for (int i = 0; i < uints.length; i++)
    {
      writeUint8(uints[i], output);
    }
  }
  
  public static void writeUint8Array(short[] uints, byte[] buf, int offset)
    throws IOException
  {
    for (int i = 0; i < uints.length; i++)
    {
      writeUint8(uints[i], buf, offset);
      offset++;
    }
  }
  
  public static void writeUint8ArrayWithUint8Length(short[] uints, OutputStream output)
    throws IOException
  {
    checkUint8(uints.length);
    writeUint8(uints.length, output);
    writeUint8Array(uints, output);
  }
  
  public static void writeUint8ArrayWithUint8Length(short[] uints, byte[] buf, int offset)
    throws IOException
  {
    checkUint8(uints.length);
    writeUint8(uints.length, buf, offset);
    writeUint8Array(uints, buf, offset + 1);
  }
  
  public static void writeUint16Array(int[] uints, OutputStream output)
    throws IOException
  {
    for (int i = 0; i < uints.length; i++)
    {
      writeUint16(uints[i], output);
    }
  }
  
  public static void writeUint16Array(int[] uints, byte[] buf, int offset)
    throws IOException
  {
    for (int i = 0; i < uints.length; i++)
    {
      writeUint16(uints[i], buf, offset);
      offset += 2;
    }
  }
  
  public static void writeUint16ArrayWithUint16Length(int[] uints, OutputStream output)
    throws IOException
  {
    int length = 2 * uints.length;
    checkUint16(length);
    writeUint16(length, output);
    writeUint16Array(uints, output);
  }
  
  public static void writeUint16ArrayWithUint16Length(int[] uints, byte[] buf, int offset)
    throws IOException
  {
    int length = 2 * uints.length;
    checkUint16(length);
    writeUint16(length, buf, offset);
    writeUint16Array(uints, buf, offset + 2);
  }
  
  public static byte[] encodeOpaque8(byte[] buf)
    throws IOException
  {
    checkUint8(buf.length);
    return Arrays.prepend(buf, (byte)buf.length);
  }
  
  public static byte[] encodeUint8ArrayWithUint8Length(short[] uints) throws IOException
  {
    byte[] result = new byte[1 + uints.length];
    writeUint8ArrayWithUint8Length(uints, result, 0);
    return result;
  }
  
  public static byte[] encodeUint16ArrayWithUint16Length(int[] uints) throws IOException
  {
    int length = 2 * uints.length;
    byte[] result = new byte[2 + length];
    writeUint16ArrayWithUint16Length(uints, result, 0);
    return result;
  }
  
  public static short readUint8(InputStream input)
    throws IOException
  {
    int i = input.read();
    if (i < 0)
    {
      throw new EOFException();
    }
    return (short)i;
  }
  
  public static short readUint8(byte[] buf, int offset)
  {
    return (short)(buf[offset] & 0xFF);
  }
  
  public static int readUint16(InputStream input)
    throws IOException
  {
    int i1 = input.read();
    int i2 = input.read();
    if (i2 < 0)
    {
      throw new EOFException();
    }
    return i1 << 8 | i2;
  }
  
  public static int readUint16(byte[] buf, int offset)
  {
    int n = (buf[offset] & 0xFF) << 8;
    n |= buf[(++offset)] & 0xFF;
    return n;
  }
  
  public static int readUint24(InputStream input)
    throws IOException
  {
    int i1 = input.read();
    int i2 = input.read();
    int i3 = input.read();
    if (i3 < 0)
    {
      throw new EOFException();
    }
    return i1 << 16 | i2 << 8 | i3;
  }
  
  public static int readUint24(byte[] buf, int offset)
  {
    int n = (buf[offset] & 0xFF) << 16;
    n |= (buf[(++offset)] & 0xFF) << 8;
    n |= buf[(++offset)] & 0xFF;
    return n;
  }
  
  public static long readUint32(InputStream input)
    throws IOException
  {
    int i1 = input.read();
    int i2 = input.read();
    int i3 = input.read();
    int i4 = input.read();
    if (i4 < 0)
    {
      throw new EOFException();
    }
    return (i1 << 24 | i2 << 16 | i3 << 8 | i4) & 0xFFFFFFFF;
  }
  
  public static long readUint32(byte[] buf, int offset)
  {
    int n = (buf[offset] & 0xFF) << 24;
    n |= (buf[(++offset)] & 0xFF) << 16;
    n |= (buf[(++offset)] & 0xFF) << 8;
    n |= buf[(++offset)] & 0xFF;
    return n & 0xFFFFFFFF;
  }
  
  public static long readUint48(InputStream input)
    throws IOException
  {
    int hi = readUint24(input);
    int lo = readUint24(input);
    return (hi & 0xFFFFFFFF) << 24 | lo & 0xFFFFFFFF;
  }
  
  public static long readUint48(byte[] buf, int offset)
  {
    int hi = readUint24(buf, offset);
    int lo = readUint24(buf, offset + 3);
    return (hi & 0xFFFFFFFF) << 24 | lo & 0xFFFFFFFF;
  }
  
  public static byte[] readAllOrNothing(int length, InputStream input)
    throws IOException
  {
    if (length < 1)
    {
      return EMPTY_BYTES;
    }
    byte[] buf = new byte[length];
    int read = Streams.readFully(input, buf);
    if (read == 0)
    {
      return null;
    }
    if (read != length)
    {
      throw new EOFException();
    }
    return buf;
  }
  
  public static byte[] readFully(int length, InputStream input)
    throws IOException
  {
    if (length < 1)
    {
      return EMPTY_BYTES;
    }
    byte[] buf = new byte[length];
    if (length != Streams.readFully(input, buf))
    {
      throw new EOFException();
    }
    return buf;
  }
  
  public static void readFully(byte[] buf, InputStream input)
    throws IOException
  {
    int length = buf.length;
    if ((length > 0) && (length != Streams.readFully(input, buf)))
    {
      throw new EOFException();
    }
  }
  
  public static byte[] readOpaque8(InputStream input)
    throws IOException
  {
    short length = readUint8(input);
    return readFully(length, input);
  }
  
  public static byte[] readOpaque16(InputStream input)
    throws IOException
  {
    int length = readUint16(input);
    return readFully(length, input);
  }
  
  public static byte[] readOpaque24(InputStream input)
    throws IOException
  {
    int length = readUint24(input);
    return readFully(length, input);
  }
  
  public static short[] readUint8Array(int count, InputStream input)
    throws IOException
  {
    short[] uints = new short[count];
    for (int i = 0; i < count; i++)
    {
      uints[i] = readUint8(input);
    }
    return uints;
  }
  
  public static int[] readUint16Array(int count, InputStream input)
    throws IOException
  {
    int[] uints = new int[count];
    for (int i = 0; i < count; i++)
    {
      uints[i] = readUint16(input);
    }
    return uints;
  }
  
  public static ProtocolVersion readVersion(byte[] buf, int offset)
    throws IOException
  {
    return ProtocolVersion.get(buf[offset] & 0xFF, buf[(offset + 1)] & 0xFF);
  }
  
  public static ProtocolVersion readVersion(InputStream input)
    throws IOException
  {
    int i1 = input.read();
    int i2 = input.read();
    if (i2 < 0)
    {
      throw new EOFException();
    }
    return ProtocolVersion.get(i1, i2);
  }
  
  public static int readVersionRaw(byte[] buf, int offset)
    throws IOException
  {
    return buf[offset] << 8 | buf[(offset + 1)];
  }
  
  public static int readVersionRaw(InputStream input)
    throws IOException
  {
    int i1 = input.read();
    int i2 = input.read();
    if (i2 < 0)
    {
      throw new EOFException();
    }
    return i1 << 8 | i2;
  }
  
  public static ASN1Primitive readASN1Object(byte[] encoding) throws IOException
  {
    ASN1InputStream asn1 = new ASN1InputStream(encoding);
    ASN1Primitive result = asn1.readObject();
    if (null == result)
    {
      throw new TlsFatalAlert((short)50);
    }
    if (null != asn1.readObject())
    {
      throw new TlsFatalAlert((short)50);
    }
    return result;
  }
  



  public static ASN1Primitive readDERObject(byte[] encoding)
    throws IOException
  {
    ASN1Primitive result = readASN1Object(encoding);
    byte[] check = result.getEncoded("DER");
    if (!Arrays.areEqual(check, encoding))
    {
      throw new TlsFatalAlert((short)50);
    }
    return result;
  }
  
  public static void writeGMTUnixTime(byte[] buf, int offset)
  {
    int t = (int)(System.currentTimeMillis() / 1000L);
    buf[offset] = ((byte)(t >>> 24));
    buf[(offset + 1)] = ((byte)(t >>> 16));
    buf[(offset + 2)] = ((byte)(t >>> 8));
    buf[(offset + 3)] = ((byte)t);
  }
  
  public static void writeVersion(ProtocolVersion version, OutputStream output)
    throws IOException
  {
    output.write(version.getMajorVersion());
    output.write(version.getMinorVersion());
  }
  
  public static void writeVersion(ProtocolVersion version, byte[] buf, int offset)
  {
    buf[offset] = ((byte)version.getMajorVersion());
    buf[(offset + 1)] = ((byte)version.getMinorVersion());
  }
  
  public static Vector getAllSignatureAlgorithms()
  {
    Vector v = new Vector(4);
    v.addElement(Shorts.valueOf((short)0));
    v.addElement(Shorts.valueOf((short)1));
    v.addElement(Shorts.valueOf((short)2));
    v.addElement(Shorts.valueOf((short)3));
    return v;
  }
  
  public static Vector getDefaultDSSSignatureAlgorithms()
  {
    return vectorOfOne(new SignatureAndHashAlgorithm((short)2, (short)2));
  }
  
  public static Vector getDefaultECDSASignatureAlgorithms()
  {
    return vectorOfOne(new SignatureAndHashAlgorithm((short)2, (short)3));
  }
  
  public static Vector getDefaultRSASignatureAlgorithms()
  {
    return vectorOfOne(new SignatureAndHashAlgorithm((short)2, (short)1));
  }
  
  public static Vector getDefaultSupportedSignatureAlgorithms()
  {
    short[] hashAlgorithms = { 2, 3, 4, 5, 6 };
    
    short[] signatureAlgorithms = { 1, 2, 3 };
    

    Vector result = new Vector();
    for (int i = 0; i < signatureAlgorithms.length; i++)
    {
      for (int j = 0; j < hashAlgorithms.length; j++)
      {
        result.addElement(new SignatureAndHashAlgorithm(hashAlgorithms[j], signatureAlgorithms[i]));
      }
    }
    return result;
  }
  

  public static SignatureAndHashAlgorithm getSignatureAndHashAlgorithm(TlsContext context, TlsSignerCredentials signerCredentials)
    throws IOException
  {
    SignatureAndHashAlgorithm signatureAndHashAlgorithm = null;
    if (isTLSv12(context))
    {
      signatureAndHashAlgorithm = signerCredentials.getSignatureAndHashAlgorithm();
      if (signatureAndHashAlgorithm == null)
      {
        throw new TlsFatalAlert((short)80);
      }
    }
    return signatureAndHashAlgorithm;
  }
  
  public static byte[] getExtensionData(Hashtable extensions, Integer extensionType)
  {
    return extensions == null ? null : (byte[])extensions.get(extensionType);
  }
  
  public static boolean hasExpectedEmptyExtensionData(Hashtable extensions, Integer extensionType, short alertDescription)
    throws IOException
  {
    byte[] extension_data = getExtensionData(extensions, extensionType);
    if (extension_data == null)
    {
      return false;
    }
    if (extension_data.length != 0)
    {
      throw new TlsFatalAlert(alertDescription);
    }
    return true;
  }
  
  public static TlsSession importSession(byte[] sessionID, SessionParameters sessionParameters)
  {
    return new TlsSessionImpl(sessionID, sessionParameters);
  }
  
  public static boolean isSignatureAlgorithmsExtensionAllowed(ProtocolVersion clientVersion)
  {
    return ProtocolVersion.TLSv12.isEqualOrEarlierVersionOf(clientVersion.getEquivalentTLSVersion());
  }
  







  public static void addSignatureAlgorithmsExtension(Hashtable extensions, Vector supportedSignatureAlgorithms)
    throws IOException
  {
    extensions.put(EXT_signature_algorithms, createSignatureAlgorithmsExtension(supportedSignatureAlgorithms));
  }
  







  public static Vector getSignatureAlgorithmsExtension(Hashtable extensions)
    throws IOException
  {
    byte[] extensionData = getExtensionData(extensions, EXT_signature_algorithms);
    return extensionData == null ? null : readSignatureAlgorithmsExtension(extensionData);
  }
  







  public static byte[] createSignatureAlgorithmsExtension(Vector supportedSignatureAlgorithms)
    throws IOException
  {
    ByteArrayOutputStream buf = new ByteArrayOutputStream();
    

    encodeSupportedSignatureAlgorithms(supportedSignatureAlgorithms, false, buf);
    
    return buf.toByteArray();
  }
  







  public static Vector readSignatureAlgorithmsExtension(byte[] extensionData)
    throws IOException
  {
    if (extensionData == null)
    {
      throw new IllegalArgumentException("'extensionData' cannot be null");
    }
    
    ByteArrayInputStream buf = new ByteArrayInputStream(extensionData);
    

    Vector supported_signature_algorithms = parseSupportedSignatureAlgorithms(false, buf);
    
    TlsProtocol.assertEmpty(buf);
    
    return supported_signature_algorithms;
  }
  
  public static void encodeSupportedSignatureAlgorithms(Vector supportedSignatureAlgorithms, boolean allowAnonymous, OutputStream output)
    throws IOException
  {
    if ((supportedSignatureAlgorithms == null) || (supportedSignatureAlgorithms.size() < 1) || 
      (supportedSignatureAlgorithms.size() >= 32768))
    {
      throw new IllegalArgumentException("'supportedSignatureAlgorithms' must have length from 1 to (2^15 - 1)");
    }
    


    int length = 2 * supportedSignatureAlgorithms.size();
    checkUint16(length);
    writeUint16(length, output);
    for (int i = 0; i < supportedSignatureAlgorithms.size(); i++)
    {
      SignatureAndHashAlgorithm entry = (SignatureAndHashAlgorithm)supportedSignatureAlgorithms.elementAt(i);
      if ((!allowAnonymous) && (entry.getSignature() == 0))
      {




        throw new IllegalArgumentException("SignatureAlgorithm.anonymous MUST NOT appear in the signature_algorithms extension");
      }
      
      entry.encode(output);
    }
  }
  

  public static Vector parseSupportedSignatureAlgorithms(boolean allowAnonymous, InputStream input)
    throws IOException
  {
    int length = readUint16(input);
    if ((length < 2) || ((length & 0x1) != 0))
    {
      throw new TlsFatalAlert((short)50);
    }
    int count = length / 2;
    Vector supportedSignatureAlgorithms = new Vector(count);
    for (int i = 0; i < count; i++)
    {
      SignatureAndHashAlgorithm entry = SignatureAndHashAlgorithm.parse(input);
      if ((!allowAnonymous) && (entry.getSignature() == 0))
      {




        throw new TlsFatalAlert((short)47);
      }
      supportedSignatureAlgorithms.addElement(entry);
    }
    return supportedSignatureAlgorithms;
  }
  
  public static void verifySupportedSignatureAlgorithm(Vector supportedSignatureAlgorithms, SignatureAndHashAlgorithm signatureAlgorithm)
    throws IOException
  {
    if ((supportedSignatureAlgorithms == null) || (supportedSignatureAlgorithms.size() < 1) || 
      (supportedSignatureAlgorithms.size() >= 32768))
    {
      throw new IllegalArgumentException("'supportedSignatureAlgorithms' must have length from 1 to (2^15 - 1)");
    }
    
    if (signatureAlgorithm == null)
    {
      throw new IllegalArgumentException("'signatureAlgorithm' cannot be null");
    }
    
    if (signatureAlgorithm.getSignature() != 0)
    {
      for (int i = 0; i < supportedSignatureAlgorithms.size(); i++)
      {
        SignatureAndHashAlgorithm entry = (SignatureAndHashAlgorithm)supportedSignatureAlgorithms.elementAt(i);
        if ((entry.getHash() == signatureAlgorithm.getHash()) && (entry.getSignature() == signatureAlgorithm.getSignature()))
        {
          return;
        }
      }
    }
    
    throw new TlsFatalAlert((short)47);
  }
  
  public static byte[] PRF(TlsContext context, byte[] secret, String asciiLabel, byte[] seed, int size)
  {
    ProtocolVersion version = context.getServerVersion();
    
    if (version.isSSL())
    {
      throw new IllegalStateException("No PRF available for SSLv3 session");
    }
    
    byte[] label = Strings.toByteArray(asciiLabel);
    byte[] labelSeed = concat(label, seed);
    
    int prfAlgorithm = context.getSecurityParameters().getPrfAlgorithm();
    
    if (prfAlgorithm == 0)
    {
      return PRF_legacy(secret, label, labelSeed, size);
    }
    
    Digest prfDigest = createPRFHash(prfAlgorithm);
    byte[] buf = new byte[size];
    hmac_hash(prfDigest, secret, labelSeed, buf);
    return buf;
  }
  
  public static byte[] PRF_legacy(byte[] secret, String asciiLabel, byte[] seed, int size)
  {
    byte[] label = Strings.toByteArray(asciiLabel);
    byte[] labelSeed = concat(label, seed);
    
    return PRF_legacy(secret, label, labelSeed, size);
  }
  
  static byte[] PRF_legacy(byte[] secret, byte[] label, byte[] labelSeed, int size)
  {
    int s_half = (secret.length + 1) / 2;
    byte[] s1 = new byte[s_half];
    byte[] s2 = new byte[s_half];
    System.arraycopy(secret, 0, s1, 0, s_half);
    System.arraycopy(secret, secret.length - s_half, s2, 0, s_half);
    
    byte[] b1 = new byte[size];
    byte[] b2 = new byte[size];
    hmac_hash(createHash((short)1), s1, labelSeed, b1);
    hmac_hash(createHash((short)2), s2, labelSeed, b2);
    for (int i = 0; i < size; i++)
    {
      int tmp91_89 = i; byte[] tmp91_87 = b1;tmp91_87[tmp91_89] = ((byte)(tmp91_87[tmp91_89] ^ b2[i]));
    }
    return b1;
  }
  
  static byte[] concat(byte[] a, byte[] b)
  {
    byte[] c = new byte[a.length + b.length];
    System.arraycopy(a, 0, c, 0, a.length);
    System.arraycopy(b, 0, c, a.length, b.length);
    return c;
  }
  
  static void hmac_hash(Digest digest, byte[] secret, byte[] seed, byte[] out)
  {
    HMac mac = new HMac(digest);
    mac.init(new KeyParameter(secret));
    byte[] a = seed;
    int size = digest.getDigestSize();
    int iterations = (out.length + size - 1) / size;
    byte[] buf = new byte[mac.getMacSize()];
    byte[] buf2 = new byte[mac.getMacSize()];
    for (int i = 0; i < iterations; i++)
    {
      mac.update(a, 0, a.length);
      mac.doFinal(buf, 0);
      a = buf;
      mac.update(a, 0, a.length);
      mac.update(seed, 0, seed.length);
      mac.doFinal(buf2, 0);
      System.arraycopy(buf2, 0, out, size * i, Math.min(size, out.length - size * i));
    }
  }
  
  static void validateKeyUsage(org.spongycastle.asn1.x509.Certificate c, int keyUsageBits)
    throws IOException
  {
    Extensions exts = c.getTBSCertificate().getExtensions();
    if (exts != null)
    {
      KeyUsage ku = KeyUsage.fromExtensions(exts);
      if (ku != null)
      {
        int bits = ku.getBytes()[0] & 0xFF;
        if ((bits & keyUsageBits) != keyUsageBits)
        {
          throw new TlsFatalAlert((short)46);
        }
      }
    }
  }
  
  static byte[] calculateKeyBlock(TlsContext context, int size)
  {
    SecurityParameters securityParameters = context.getSecurityParameters();
    byte[] master_secret = securityParameters.getMasterSecret();
    byte[] seed = concat(securityParameters.getServerRandom(), securityParameters
      .getClientRandom());
    
    if (isSSL(context))
    {
      return calculateKeyBlock_SSL(master_secret, seed, size);
    }
    
    return PRF(context, master_secret, "key expansion", seed, size);
  }
  
  static byte[] calculateKeyBlock_SSL(byte[] master_secret, byte[] random, int size)
  {
    Digest md5 = createHash();
    Digest sha1 = createHash((short)2);
    int md5Size = md5.getDigestSize();
    byte[] shatmp = new byte[sha1.getDigestSize()];
    byte[] tmp = new byte[size + md5Size];
    
    int i = 0;int pos = 0;
    while (pos < size)
    {
      byte[] ssl3Const = SSL3_CONST[i];
      
      sha1.update(ssl3Const, 0, ssl3Const.length);
      sha1.update(master_secret, 0, master_secret.length);
      sha1.update(random, 0, random.length);
      sha1.doFinal(shatmp, 0);
      
      md5.update(master_secret, 0, master_secret.length);
      md5.update(shatmp, 0, shatmp.length);
      md5.doFinal(tmp, pos);
      
      pos += md5Size;
      i++;
    }
    
    return Arrays.copyOfRange(tmp, 0, size);
  }
  
  static byte[] calculateMasterSecret(TlsContext context, byte[] pre_master_secret)
  {
    SecurityParameters securityParameters = context.getSecurityParameters();
    byte[] seed;
    byte[] seed;
    if (extendedMasterSecret)
    {
      seed = securityParameters.getSessionHash();
    }
    else
    {
      seed = concat(securityParameters.getClientRandom(), securityParameters.getServerRandom());
    }
    
    if (isSSL(context))
    {
      return calculateMasterSecret_SSL(pre_master_secret, seed);
    }
    
    String asciiLabel = extendedMasterSecret ? "extended master secret" : "master secret";
    


    return PRF(context, pre_master_secret, asciiLabel, seed, 48);
  }
  
  static byte[] calculateMasterSecret_SSL(byte[] pre_master_secret, byte[] random)
  {
    Digest md5 = createHash();
    Digest sha1 = createHash((short)2);
    int md5Size = md5.getDigestSize();
    byte[] shatmp = new byte[sha1.getDigestSize()];
    
    byte[] rval = new byte[md5Size * 3];
    int pos = 0;
    
    for (int i = 0; i < 3; i++)
    {
      byte[] ssl3Const = SSL3_CONST[i];
      
      sha1.update(ssl3Const, 0, ssl3Const.length);
      sha1.update(pre_master_secret, 0, pre_master_secret.length);
      sha1.update(random, 0, random.length);
      sha1.doFinal(shatmp, 0);
      
      md5.update(pre_master_secret, 0, pre_master_secret.length);
      md5.update(shatmp, 0, shatmp.length);
      md5.doFinal(rval, pos);
      
      pos += md5Size;
    }
    
    return rval;
  }
  
  static byte[] calculateVerifyData(TlsContext context, String asciiLabel, byte[] handshakeHash)
  {
    if (isSSL(context))
    {
      return handshakeHash;
    }
    
    SecurityParameters securityParameters = context.getSecurityParameters();
    byte[] master_secret = securityParameters.getMasterSecret();
    int verify_data_length = securityParameters.getVerifyDataLength();
    
    return PRF(context, master_secret, asciiLabel, handshakeHash, verify_data_length);
  }
  
  public static Digest createHash(short hashAlgorithm)
  {
    switch (hashAlgorithm)
    {
    case 1: 
      return new MD5Digest();
    case 2: 
      return new SHA1Digest();
    case 3: 
      return new SHA224Digest();
    case 4: 
      return new SHA256Digest();
    case 5: 
      return new SHA384Digest();
    case 6: 
      return new SHA512Digest();
    }
    throw new IllegalArgumentException("unknown HashAlgorithm");
  }
  

  public static Digest createHash(SignatureAndHashAlgorithm signatureAndHashAlgorithm)
  {
    return signatureAndHashAlgorithm == null ? new CombinedHash() : 
    
      createHash(signatureAndHashAlgorithm.getHash());
  }
  
  public static Digest cloneHash(short hashAlgorithm, Digest hash)
  {
    switch (hashAlgorithm)
    {
    case 1: 
      return new MD5Digest((MD5Digest)hash);
    case 2: 
      return new SHA1Digest((SHA1Digest)hash);
    case 3: 
      return new SHA224Digest((SHA224Digest)hash);
    case 4: 
      return new SHA256Digest((SHA256Digest)hash);
    case 5: 
      return new SHA384Digest((SHA384Digest)hash);
    case 6: 
      return new SHA512Digest((SHA512Digest)hash);
    }
    throw new IllegalArgumentException("unknown HashAlgorithm");
  }
  

  public static Digest createPRFHash(int prfAlgorithm)
  {
    switch (prfAlgorithm)
    {
    case 0: 
      return new CombinedHash();
    }
    return createHash(getHashAlgorithmForPRFAlgorithm(prfAlgorithm));
  }
  

  public static Digest clonePRFHash(int prfAlgorithm, Digest hash)
  {
    switch (prfAlgorithm)
    {
    case 0: 
      return new CombinedHash((CombinedHash)hash);
    }
    return cloneHash(getHashAlgorithmForPRFAlgorithm(prfAlgorithm), hash);
  }
  

  public static short getHashAlgorithmForPRFAlgorithm(int prfAlgorithm)
  {
    switch (prfAlgorithm)
    {
    case 0: 
      throw new IllegalArgumentException("legacy PRF not a valid algorithm");
    case 1: 
      return 4;
    case 2: 
      return 5;
    }
    throw new IllegalArgumentException("unknown PRFAlgorithm");
  }
  

  public static ASN1ObjectIdentifier getOIDForHashAlgorithm(short hashAlgorithm)
  {
    switch (hashAlgorithm)
    {
    case 1: 
      return PKCSObjectIdentifiers.md5;
    case 2: 
      return X509ObjectIdentifiers.id_SHA1;
    case 3: 
      return NISTObjectIdentifiers.id_sha224;
    case 4: 
      return NISTObjectIdentifiers.id_sha256;
    case 5: 
      return NISTObjectIdentifiers.id_sha384;
    case 6: 
      return NISTObjectIdentifiers.id_sha512;
    }
    throw new IllegalArgumentException("unknown HashAlgorithm");
  }
  

  static short getClientCertificateType(Certificate clientCertificate, Certificate serverCertificate)
    throws IOException
  {
    if (clientCertificate.isEmpty())
    {
      return -1;
    }
    
    org.spongycastle.asn1.x509.Certificate x509Cert = clientCertificate.getCertificateAt(0);
    SubjectPublicKeyInfo keyInfo = x509Cert.getSubjectPublicKeyInfo();
    try
    {
      AsymmetricKeyParameter publicKey = PublicKeyFactory.createKey(keyInfo);
      if (publicKey.isPrivate())
      {
        throw new TlsFatalAlert((short)80);
      }
      















      if ((publicKey instanceof RSAKeyParameters))
      {
        validateKeyUsage(x509Cert, 128);
        return 1;
      }
      




      if ((publicKey instanceof DSAPublicKeyParameters))
      {
        validateKeyUsage(x509Cert, 128);
        return 2;
      }
      





      if ((publicKey instanceof ECPublicKeyParameters))
      {
        validateKeyUsage(x509Cert, 128);
        
        return 64;
      }
      


      throw new TlsFatalAlert((short)43);
    }
    catch (Exception e)
    {
      throw new TlsFatalAlert((short)43, e);
    }
  }
  
  static void trackHashAlgorithms(TlsHandshakeHash handshakeHash, Vector supportedSignatureAlgorithms)
  {
    if (supportedSignatureAlgorithms != null)
    {
      for (int i = 0; i < supportedSignatureAlgorithms.size(); i++)
      {

        SignatureAndHashAlgorithm signatureAndHashAlgorithm = (SignatureAndHashAlgorithm)supportedSignatureAlgorithms.elementAt(i);
        short hashAlgorithm = signatureAndHashAlgorithm.getHash();
        

        if (!HashAlgorithm.isPrivate(hashAlgorithm))
        {
          handshakeHash.trackHashAlgorithm(hashAlgorithm);
        }
      }
    }
  }
  
  public static boolean hasSigningCapability(short clientCertificateType)
  {
    switch (clientCertificateType)
    {
    case 1: 
    case 2: 
    case 64: 
      return true;
    }
    return false;
  }
  

  public static TlsSigner createTlsSigner(short clientCertificateType)
  {
    switch (clientCertificateType)
    {
    case 2: 
      return new TlsDSSSigner();
    case 64: 
      return new TlsECDSASigner();
    case 1: 
      return new TlsRSASigner();
    }
    throw new IllegalArgumentException("'clientCertificateType' is not a type with signing capability");
  }
  

  static final byte[] SSL_CLIENT = { 67, 76, 78, 84 };
  static final byte[] SSL_SERVER = { 83, 82, 86, 82 };
  

  static final byte[][] SSL3_CONST = genSSL3Const();
  
  private static byte[][] genSSL3Const()
  {
    int n = 10;
    byte[][] arr = new byte[n][];
    for (int i = 0; i < n; i++)
    {
      byte[] b = new byte[i + 1];
      Arrays.fill(b, (byte)(65 + i));
      arr[i] = b;
    }
    return arr;
  }
  
  private static Vector vectorOfOne(Object obj)
  {
    Vector v = new Vector(1);
    v.addElement(obj);
    return v;
  }
  
  public static int getCipherType(int ciphersuite) throws IOException
  {
    switch (getEncryptionAlgorithm(ciphersuite))
    {
    case 10: 
    case 11: 
    case 15: 
    case 16: 
    case 17: 
    case 18: 
    case 19: 
    case 20: 
    case 21: 
    case 103: 
    case 104: 
      return 2;
    
    case 3: 
    case 4: 
    case 5: 
    case 6: 
    case 7: 
    case 8: 
    case 9: 
    case 12: 
    case 13: 
    case 14: 
      return 1;
    
    case 0: 
    case 1: 
    case 2: 
      return 0;
    }
    
    throw new TlsFatalAlert((short)80);
  }
  
  public static int getEncryptionAlgorithm(int ciphersuite)
    throws IOException
  {
    switch (ciphersuite)
    {
    case 10: 
    case 13: 
    case 16: 
    case 19: 
    case 22: 
    case 27: 
    case 139: 
    case 143: 
    case 147: 
    case 49155: 
    case 49160: 
    case 49165: 
    case 49170: 
    case 49175: 
    case 49178: 
    case 49179: 
    case 49180: 
    case 49204: 
      return 7;
    
    case 47: 
    case 48: 
    case 49: 
    case 50: 
    case 51: 
    case 52: 
    case 60: 
    case 62: 
    case 63: 
    case 64: 
    case 103: 
    case 108: 
    case 140: 
    case 144: 
    case 148: 
    case 174: 
    case 178: 
    case 182: 
    case 49156: 
    case 49161: 
    case 49166: 
    case 49171: 
    case 49176: 
    case 49181: 
    case 49182: 
    case 49183: 
    case 49187: 
    case 49189: 
    case 49191: 
    case 49193: 
    case 49205: 
    case 49207: 
      return 8;
    
    case 49308: 
    case 49310: 
    case 49316: 
    case 49318: 
    case 49324: 
      return 15;
    
    case 49312: 
    case 49314: 
    case 49320: 
    case 49322: 
    case 49326: 
      return 16;
    
    case 156: 
    case 158: 
    case 160: 
    case 162: 
    case 164: 
    case 166: 
    case 168: 
    case 170: 
    case 172: 
    case 49195: 
    case 49197: 
    case 49199: 
    case 49201: 
      return 10;
    
    case 65280: 
    case 65282: 
    case 65284: 
    case 65296: 
    case 65298: 
    case 65300: 
      return 103;
    
    case 53: 
    case 54: 
    case 55: 
    case 56: 
    case 57: 
    case 58: 
    case 61: 
    case 104: 
    case 105: 
    case 106: 
    case 107: 
    case 109: 
    case 141: 
    case 145: 
    case 149: 
    case 175: 
    case 179: 
    case 183: 
    case 49157: 
    case 49162: 
    case 49167: 
    case 49172: 
    case 49177: 
    case 49184: 
    case 49185: 
    case 49186: 
    case 49188: 
    case 49190: 
    case 49192: 
    case 49194: 
    case 49206: 
    case 49208: 
      return 9;
    
    case 49309: 
    case 49311: 
    case 49317: 
    case 49319: 
    case 49325: 
      return 17;
    
    case 49313: 
    case 49315: 
    case 49321: 
    case 49323: 
    case 49327: 
      return 18;
    
    case 157: 
    case 159: 
    case 161: 
    case 163: 
    case 165: 
    case 167: 
    case 169: 
    case 171: 
    case 173: 
    case 49196: 
    case 49198: 
    case 49200: 
    case 49202: 
      return 11;
    
    case 65281: 
    case 65283: 
    case 65285: 
    case 65297: 
    case 65299: 
    case 65301: 
      return 104;
    
    case 65: 
    case 66: 
    case 67: 
    case 68: 
    case 69: 
    case 70: 
    case 186: 
    case 187: 
    case 188: 
    case 189: 
    case 190: 
    case 191: 
    case 49266: 
    case 49268: 
    case 49270: 
    case 49272: 
    case 49300: 
    case 49302: 
    case 49304: 
    case 49306: 
      return 12;
    
    case 49274: 
    case 49276: 
    case 49278: 
    case 49280: 
    case 49282: 
    case 49284: 
    case 49286: 
    case 49288: 
    case 49290: 
    case 49292: 
    case 49294: 
    case 49296: 
    case 49298: 
      return 19;
    
    case 132: 
    case 133: 
    case 134: 
    case 135: 
    case 136: 
    case 137: 
    case 192: 
    case 193: 
    case 194: 
    case 195: 
    case 196: 
    case 197: 
    case 49267: 
    case 49269: 
    case 49271: 
    case 49273: 
    case 49301: 
    case 49303: 
    case 49305: 
    case 49307: 
      return 13;
    
    case 49275: 
    case 49277: 
    case 49279: 
    case 49281: 
    case 49283: 
    case 49285: 
    case 49287: 
    case 49289: 
    case 49291: 
    case 49293: 
    case 49295: 
    case 49297: 
    case 49299: 
      return 20;
    
    case 52392: 
    case 52393: 
    case 52394: 
    case 52395: 
    case 52396: 
    case 52397: 
    case 52398: 
      return 21;
    
    case 1: 
      return 0;
    
    case 2: 
    case 44: 
    case 45: 
    case 46: 
    case 49153: 
    case 49158: 
    case 49163: 
    case 49168: 
    case 49173: 
    case 49209: 
      return 0;
    
    case 59: 
    case 176: 
    case 180: 
    case 184: 
    case 49210: 
      return 0;
    
    case 177: 
    case 181: 
    case 185: 
    case 49211: 
      return 0;
    
    case 4: 
    case 24: 
      return 2;
    
    case 5: 
    case 138: 
    case 142: 
    case 146: 
    case 49154: 
    case 49159: 
    case 49164: 
    case 49169: 
    case 49174: 
    case 49203: 
      return 2;
    
    case 150: 
    case 151: 
    case 152: 
    case 153: 
    case 154: 
    case 155: 
      return 14;
    }
    
    throw new TlsFatalAlert((short)80);
  }
  
  public static int getKeyExchangeAlgorithm(int ciphersuite)
    throws IOException
  {
    switch (ciphersuite)
    {
    case 24: 
    case 27: 
    case 52: 
    case 58: 
    case 70: 
    case 108: 
    case 109: 
    case 137: 
    case 155: 
    case 166: 
    case 167: 
    case 191: 
    case 197: 
    case 49284: 
    case 49285: 
      return 11;
    
    case 13: 
    case 48: 
    case 54: 
    case 62: 
    case 66: 
    case 104: 
    case 133: 
    case 151: 
    case 164: 
    case 165: 
    case 187: 
    case 193: 
    case 49282: 
    case 49283: 
      return 7;
    
    case 16: 
    case 49: 
    case 55: 
    case 63: 
    case 67: 
    case 105: 
    case 134: 
    case 152: 
    case 160: 
    case 161: 
    case 188: 
    case 194: 
    case 49278: 
    case 49279: 
      return 9;
    
    case 19: 
    case 50: 
    case 56: 
    case 64: 
    case 68: 
    case 106: 
    case 135: 
    case 153: 
    case 162: 
    case 163: 
    case 189: 
    case 195: 
    case 49280: 
    case 49281: 
      return 3;
    
    case 45: 
    case 142: 
    case 143: 
    case 144: 
    case 145: 
    case 170: 
    case 171: 
    case 178: 
    case 179: 
    case 180: 
    case 181: 
    case 49296: 
    case 49297: 
    case 49302: 
    case 49303: 
    case 49318: 
    case 49319: 
    case 49322: 
    case 49323: 
    case 52397: 
    case 65298: 
    case 65299: 
      return 14;
    
    case 22: 
    case 51: 
    case 57: 
    case 69: 
    case 103: 
    case 107: 
    case 136: 
    case 154: 
    case 158: 
    case 159: 
    case 190: 
    case 196: 
    case 49276: 
    case 49277: 
    case 49310: 
    case 49311: 
    case 49314: 
    case 49315: 
    case 52394: 
    case 65280: 
    case 65281: 
      return 5;
    
    case 49173: 
    case 49174: 
    case 49175: 
    case 49176: 
    case 49177: 
      return 20;
    
    case 49153: 
    case 49154: 
    case 49155: 
    case 49156: 
    case 49157: 
    case 49189: 
    case 49190: 
    case 49197: 
    case 49198: 
    case 49268: 
    case 49269: 
    case 49288: 
    case 49289: 
      return 16;
    
    case 49163: 
    case 49164: 
    case 49165: 
    case 49166: 
    case 49167: 
    case 49193: 
    case 49194: 
    case 49201: 
    case 49202: 
    case 49272: 
    case 49273: 
    case 49292: 
    case 49293: 
      return 18;
    
    case 49158: 
    case 49159: 
    case 49160: 
    case 49161: 
    case 49162: 
    case 49187: 
    case 49188: 
    case 49195: 
    case 49196: 
    case 49266: 
    case 49267: 
    case 49286: 
    case 49287: 
    case 49324: 
    case 49325: 
    case 49326: 
    case 49327: 
    case 52393: 
    case 65284: 
    case 65285: 
      return 17;
    
    case 49203: 
    case 49204: 
    case 49205: 
    case 49206: 
    case 49207: 
    case 49208: 
    case 49209: 
    case 49210: 
    case 49211: 
    case 49306: 
    case 49307: 
    case 52396: 
    case 65300: 
    case 65301: 
      return 24;
    
    case 49168: 
    case 49169: 
    case 49170: 
    case 49171: 
    case 49172: 
    case 49191: 
    case 49192: 
    case 49199: 
    case 49200: 
    case 49270: 
    case 49271: 
    case 49290: 
    case 49291: 
    case 52392: 
    case 65282: 
    case 65283: 
      return 19;
    
    case 44: 
    case 138: 
    case 139: 
    case 140: 
    case 141: 
    case 168: 
    case 169: 
    case 174: 
    case 175: 
    case 176: 
    case 177: 
    case 49294: 
    case 49295: 
    case 49300: 
    case 49301: 
    case 49316: 
    case 49317: 
    case 49320: 
    case 49321: 
    case 52395: 
    case 65296: 
    case 65297: 
      return 13;
    
    case 1: 
    case 2: 
    case 4: 
    case 5: 
    case 10: 
    case 47: 
    case 53: 
    case 59: 
    case 60: 
    case 61: 
    case 65: 
    case 132: 
    case 150: 
    case 156: 
    case 157: 
    case 186: 
    case 192: 
    case 49274: 
    case 49275: 
    case 49308: 
    case 49309: 
    case 49312: 
    case 49313: 
      return 1;
    
    case 46: 
    case 146: 
    case 147: 
    case 148: 
    case 149: 
    case 172: 
    case 173: 
    case 182: 
    case 183: 
    case 184: 
    case 185: 
    case 49298: 
    case 49299: 
    case 49304: 
    case 49305: 
    case 52398: 
      return 15;
    
    case 49178: 
    case 49181: 
    case 49184: 
      return 21;
    
    case 49180: 
    case 49183: 
    case 49186: 
      return 22;
    
    case 49179: 
    case 49182: 
    case 49185: 
      return 23;
    }
    
    throw new TlsFatalAlert((short)80);
  }
  
  public static int getMACAlgorithm(int ciphersuite)
    throws IOException
  {
    switch (ciphersuite)
    {
    case 156: 
    case 157: 
    case 158: 
    case 159: 
    case 160: 
    case 161: 
    case 162: 
    case 163: 
    case 164: 
    case 165: 
    case 166: 
    case 167: 
    case 168: 
    case 169: 
    case 170: 
    case 171: 
    case 172: 
    case 173: 
    case 49195: 
    case 49196: 
    case 49197: 
    case 49198: 
    case 49199: 
    case 49200: 
    case 49201: 
    case 49202: 
    case 49274: 
    case 49275: 
    case 49276: 
    case 49277: 
    case 49278: 
    case 49279: 
    case 49280: 
    case 49281: 
    case 49282: 
    case 49283: 
    case 49284: 
    case 49285: 
    case 49286: 
    case 49287: 
    case 49288: 
    case 49289: 
    case 49290: 
    case 49291: 
    case 49292: 
    case 49293: 
    case 49294: 
    case 49295: 
    case 49296: 
    case 49297: 
    case 49298: 
    case 49299: 
    case 49308: 
    case 49309: 
    case 49310: 
    case 49311: 
    case 49312: 
    case 49313: 
    case 49314: 
    case 49315: 
    case 49316: 
    case 49317: 
    case 49318: 
    case 49319: 
    case 49320: 
    case 49321: 
    case 49322: 
    case 49323: 
    case 49324: 
    case 49325: 
    case 49326: 
    case 49327: 
    case 52392: 
    case 52393: 
    case 52394: 
    case 52395: 
    case 52396: 
    case 52397: 
    case 52398: 
    case 65280: 
    case 65281: 
    case 65282: 
    case 65283: 
    case 65284: 
    case 65285: 
    case 65296: 
    case 65297: 
    case 65298: 
    case 65299: 
    case 65300: 
    case 65301: 
      return 0;
    
    case 1: 
    case 4: 
    case 24: 
      return 1;
    
    case 2: 
    case 5: 
    case 10: 
    case 13: 
    case 16: 
    case 19: 
    case 22: 
    case 27: 
    case 44: 
    case 45: 
    case 46: 
    case 47: 
    case 48: 
    case 49: 
    case 50: 
    case 51: 
    case 52: 
    case 53: 
    case 54: 
    case 55: 
    case 56: 
    case 57: 
    case 58: 
    case 65: 
    case 66: 
    case 67: 
    case 68: 
    case 69: 
    case 70: 
    case 132: 
    case 133: 
    case 134: 
    case 135: 
    case 136: 
    case 137: 
    case 138: 
    case 139: 
    case 140: 
    case 141: 
    case 142: 
    case 143: 
    case 144: 
    case 145: 
    case 146: 
    case 147: 
    case 148: 
    case 149: 
    case 150: 
    case 151: 
    case 152: 
    case 153: 
    case 154: 
    case 155: 
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
    case 49178: 
    case 49179: 
    case 49180: 
    case 49181: 
    case 49182: 
    case 49183: 
    case 49184: 
    case 49185: 
    case 49186: 
    case 49203: 
    case 49204: 
    case 49205: 
    case 49206: 
    case 49209: 
      return 2;
    
    case 59: 
    case 60: 
    case 61: 
    case 62: 
    case 63: 
    case 64: 
    case 103: 
    case 104: 
    case 105: 
    case 106: 
    case 107: 
    case 108: 
    case 109: 
    case 174: 
    case 176: 
    case 178: 
    case 180: 
    case 182: 
    case 184: 
    case 186: 
    case 187: 
    case 188: 
    case 189: 
    case 190: 
    case 191: 
    case 192: 
    case 193: 
    case 194: 
    case 195: 
    case 196: 
    case 197: 
    case 49187: 
    case 49189: 
    case 49191: 
    case 49193: 
    case 49207: 
    case 49210: 
    case 49266: 
    case 49268: 
    case 49270: 
    case 49272: 
    case 49300: 
    case 49302: 
    case 49304: 
    case 49306: 
      return 3;
    
    case 175: 
    case 177: 
    case 179: 
    case 181: 
    case 183: 
    case 185: 
    case 49188: 
    case 49190: 
    case 49192: 
    case 49194: 
    case 49208: 
    case 49211: 
    case 49267: 
    case 49269: 
    case 49271: 
    case 49273: 
    case 49301: 
    case 49303: 
    case 49305: 
    case 49307: 
      return 4;
    }
    
    throw new TlsFatalAlert((short)80);
  }
  

  public static ProtocolVersion getMinimumVersion(int ciphersuite)
  {
    switch (ciphersuite)
    {
    case 59: 
    case 60: 
    case 61: 
    case 62: 
    case 63: 
    case 64: 
    case 103: 
    case 104: 
    case 105: 
    case 106: 
    case 107: 
    case 108: 
    case 109: 
    case 156: 
    case 157: 
    case 158: 
    case 159: 
    case 160: 
    case 161: 
    case 162: 
    case 163: 
    case 164: 
    case 165: 
    case 166: 
    case 167: 
    case 168: 
    case 169: 
    case 170: 
    case 171: 
    case 172: 
    case 173: 
    case 186: 
    case 187: 
    case 188: 
    case 189: 
    case 190: 
    case 191: 
    case 192: 
    case 193: 
    case 194: 
    case 195: 
    case 196: 
    case 197: 
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
    case 49266: 
    case 49267: 
    case 49268: 
    case 49269: 
    case 49270: 
    case 49271: 
    case 49272: 
    case 49273: 
    case 49274: 
    case 49275: 
    case 49276: 
    case 49277: 
    case 49278: 
    case 49279: 
    case 49280: 
    case 49281: 
    case 49282: 
    case 49283: 
    case 49284: 
    case 49285: 
    case 49286: 
    case 49287: 
    case 49288: 
    case 49289: 
    case 49290: 
    case 49291: 
    case 49292: 
    case 49293: 
    case 49294: 
    case 49295: 
    case 49296: 
    case 49297: 
    case 49298: 
    case 49299: 
    case 49308: 
    case 49309: 
    case 49310: 
    case 49311: 
    case 49312: 
    case 49313: 
    case 49314: 
    case 49315: 
    case 49316: 
    case 49317: 
    case 49318: 
    case 49319: 
    case 49320: 
    case 49321: 
    case 49322: 
    case 49323: 
    case 49324: 
    case 49325: 
    case 49326: 
    case 49327: 
    case 52392: 
    case 52393: 
    case 52394: 
    case 52395: 
    case 52396: 
    case 52397: 
    case 52398: 
    case 65280: 
    case 65281: 
    case 65282: 
    case 65283: 
    case 65284: 
    case 65285: 
    case 65296: 
    case 65297: 
    case 65298: 
    case 65299: 
    case 65300: 
    case 65301: 
      return ProtocolVersion.TLSv12;
    }
    
    return ProtocolVersion.SSLv3;
  }
  
  public static boolean isAEADCipherSuite(int ciphersuite)
    throws IOException
  {
    return 2 == getCipherType(ciphersuite);
  }
  
  public static boolean isBlockCipherSuite(int ciphersuite) throws IOException
  {
    return 1 == getCipherType(ciphersuite);
  }
  
  public static boolean isStreamCipherSuite(int ciphersuite) throws IOException
  {
    return 0 == getCipherType(ciphersuite);
  }
  

  public static boolean isValidCipherSuiteForSignatureAlgorithms(int cipherSuite, Vector sigAlgs)
  {
    try
    {
      keyExchangeAlgorithm = getKeyExchangeAlgorithm(cipherSuite);
    }
    catch (IOException e) {
      int keyExchangeAlgorithm;
      return true;
    }
    int keyExchangeAlgorithm;
    switch (keyExchangeAlgorithm)
    {
    case 11: 
    case 12: 
    case 20: 
      return sigAlgs.contains(Shorts.valueOf((short)0));
    
    case 5: 
    case 6: 
    case 19: 
    case 23: 
      return sigAlgs.contains(Shorts.valueOf((short)1));
    
    case 3: 
    case 4: 
    case 22: 
      return sigAlgs.contains(Shorts.valueOf((short)2));
    
    case 17: 
      return sigAlgs.contains(Shorts.valueOf((short)3));
    }
    
    return true;
  }
  

  public static boolean isValidCipherSuiteForVersion(int cipherSuite, ProtocolVersion serverVersion)
  {
    return getMinimumVersion(cipherSuite).isEqualOrEarlierVersionOf(serverVersion.getEquivalentTLSVersion());
  }
  
  public static Vector getUsableSignatureAlgorithms(Vector sigHashAlgs)
  {
    if (sigHashAlgs == null)
    {
      return getAllSignatureAlgorithms();
    }
    
    Vector v = new Vector(4);
    v.addElement(Shorts.valueOf((short)0));
    for (int i = 0; i < sigHashAlgs.size(); i++)
    {
      SignatureAndHashAlgorithm sigHashAlg = (SignatureAndHashAlgorithm)sigHashAlgs.elementAt(i);
      

      Short sigAlg = Shorts.valueOf(sigHashAlg.getSignature());
      if (!v.contains(sigAlg))
      {
        v.addElement(sigAlg);
      }
    }
    
    return v;
  }
}
