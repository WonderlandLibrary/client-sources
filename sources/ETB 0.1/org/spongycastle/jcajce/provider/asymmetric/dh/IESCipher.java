package org.spongycastle.jcajce.provider.asymmetric.dh;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.interfaces.DHKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.KeyEncoder;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.agreement.DHBasicAgreement;
import org.spongycastle.crypto.engines.AESEngine;
import org.spongycastle.crypto.engines.DESedeEngine;
import org.spongycastle.crypto.engines.IESEngine;
import org.spongycastle.crypto.generators.DHKeyPairGenerator;
import org.spongycastle.crypto.generators.EphemeralKeyPairGenerator;
import org.spongycastle.crypto.generators.KDF2BytesGenerator;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DHKeyGenerationParameters;
import org.spongycastle.crypto.params.DHKeyParameters;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.crypto.params.IESWithCipherParameters;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.parsers.DHIESPublicKeyParser;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.jcajce.provider.asymmetric.util.DHUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.IESUtil;
import org.spongycastle.jcajce.provider.util.BadBlockException;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.interfaces.IESKey;
import org.spongycastle.jce.spec.IESParameterSpec;
import org.spongycastle.util.BigIntegers;
import org.spongycastle.util.Strings;

public class IESCipher
  extends CipherSpi
{
  private final JcaJceHelper helper = new BCJcaJceHelper();
  
  private final int ivLength;
  private IESEngine engine;
  private int state = -1;
  private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
  private AlgorithmParameters engineParam = null;
  private IESParameterSpec engineSpec = null;
  private AsymmetricKeyParameter key;
  private SecureRandom random;
  private boolean dhaesMode = false;
  private AsymmetricKeyParameter otherKeyParameter = null;
  
  public IESCipher(IESEngine engine)
  {
    this.engine = engine;
    ivLength = 0;
  }
  
  public IESCipher(IESEngine engine, int ivLength)
  {
    this.engine = engine;
    this.ivLength = ivLength;
  }
  
  public int engineGetBlockSize()
  {
    if (engine.getCipher() != null)
    {
      return engine.getCipher().getBlockSize();
    }
    

    return 0;
  }
  


  public int engineGetKeySize(Key key)
  {
    if ((key instanceof DHKey))
    {
      return ((DHKey)key).getParams().getP().bitLength();
    }
    

    throw new IllegalArgumentException("not a DH key");
  }
  


  public byte[] engineGetIV()
  {
    if (engineSpec != null)
    {
      return engineSpec.getNonce();
    }
    return null;
  }
  
  public AlgorithmParameters engineGetParameters()
  {
    if ((engineParam == null) && (engineSpec != null))
    {
      try
      {
        engineParam = helper.createAlgorithmParameters("IES");
        engineParam.init(engineSpec);
      }
      catch (Exception e)
      {
        throw new RuntimeException(e.toString());
      }
    }
    
    return engineParam;
  }
  

  public void engineSetMode(String mode)
    throws NoSuchAlgorithmException
  {
    String modeName = Strings.toUpperCase(mode);
    
    if (modeName.equals("NONE"))
    {
      dhaesMode = false;
    }
    else if (modeName.equals("DHAES"))
    {
      dhaesMode = true;
    }
    else
    {
      throw new IllegalArgumentException("can't support mode " + mode);
    }
  }
  


  public int engineGetOutputSize(int inputLen)
  {
    if (key == null)
    {
      throw new IllegalStateException("cipher not initialised");
    }
    
    int len1 = engine.getMac().getMacSize();
    int len2;
    int len2; if (otherKeyParameter == null)
    {
      len2 = 1 + 2 * (((DHKeyParameters)key).getParameters().getP().bitLength() + 7) / 8;
    }
    else
    {
      len2 = 0;
    }
    int len3;
    if (engine.getCipher() == null)
    {
      len3 = inputLen;
    } else { int len3;
      if ((state == 1) || (state == 3))
      {
        len3 = engine.getCipher().getOutputSize(inputLen);
      } else { int len3;
        if ((state == 2) || (state == 4))
        {
          len3 = engine.getCipher().getOutputSize(inputLen - len1 - len2);
        }
        else
        {
          throw new IllegalStateException("cipher not initialised"); }
      } }
    int len3;
    if ((state == 1) || (state == 3))
    {
      return buffer.size() + len1 + len2 + len3;
    }
    if ((state == 2) || (state == 4))
    {
      return buffer.size() - len1 - len2 + len3;
    }
    

    throw new IllegalStateException("IESCipher not initialised");
  }
  


  public void engineSetPadding(String padding)
    throws NoSuchPaddingException
  {
    String paddingName = Strings.toUpperCase(padding);
    

    if (!paddingName.equals("NOPADDING"))
    {


      if ((!paddingName.equals("PKCS5PADDING")) && (!paddingName.equals("PKCS7PADDING")))
      {




        throw new NoSuchPaddingException("padding not available with IESCipher");
      }
    }
  }
  





  public void engineInit(int opmode, Key key, AlgorithmParameters params, SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    AlgorithmParameterSpec paramSpec = null;
    
    if (params != null)
    {
      try
      {
        paramSpec = params.getParameterSpec(IESParameterSpec.class);
      }
      catch (Exception e)
      {
        throw new InvalidAlgorithmParameterException("cannot recognise parameters: " + e.toString());
      }
    }
    
    engineParam = params;
    engineInit(opmode, key, paramSpec, random);
  }
  






  public void engineInit(int opmode, Key key, AlgorithmParameterSpec engineSpec, SecureRandom random)
    throws InvalidAlgorithmParameterException, InvalidKeyException
  {
    if (engineSpec == null)
    {
      byte[] nonce = null;
      if ((ivLength != 0) && (opmode == 1))
      {
        nonce = new byte[ivLength];
        random.nextBytes(nonce);
      }
      this.engineSpec = IESUtil.guessParameterSpec(engine.getCipher(), nonce);
    }
    else if ((engineSpec instanceof IESParameterSpec))
    {
      this.engineSpec = ((IESParameterSpec)engineSpec);
    }
    else
    {
      throw new InvalidAlgorithmParameterException("must be passed IES parameters");
    }
    
    byte[] nonce = this.engineSpec.getNonce();
    
    if ((ivLength != 0) && ((nonce == null) || (nonce.length != ivLength)))
    {
      throw new InvalidAlgorithmParameterException("NONCE in IES Parameters needs to be " + ivLength + " bytes long");
    }
    

    if ((opmode == 1) || (opmode == 3))
    {
      if ((key instanceof DHPublicKey))
      {
        this.key = DHUtil.generatePublicKeyParameter((PublicKey)key);
      }
      else if ((key instanceof IESKey))
      {
        IESKey ieKey = (IESKey)key;
        
        this.key = DHUtil.generatePublicKeyParameter(ieKey.getPublic());
        otherKeyParameter = DHUtil.generatePrivateKeyParameter(ieKey.getPrivate());
      }
      else
      {
        throw new InvalidKeyException("must be passed recipient's public DH key for encryption");
      }
    }
    else if ((opmode == 2) || (opmode == 4))
    {
      if ((key instanceof DHPrivateKey))
      {
        this.key = DHUtil.generatePrivateKeyParameter((PrivateKey)key);
      }
      else if ((key instanceof IESKey))
      {
        IESKey ieKey = (IESKey)key;
        
        otherKeyParameter = DHUtil.generatePublicKeyParameter(ieKey.getPublic());
        this.key = DHUtil.generatePrivateKeyParameter(ieKey.getPrivate());
      }
      else
      {
        throw new InvalidKeyException("must be passed recipient's private DH key for decryption");
      }
      
    }
    else {
      throw new InvalidKeyException("must be passed EC key");
    }
    
    this.random = random;
    state = opmode;
    buffer.reset();
  }
  





  public void engineInit(int opmode, Key key, SecureRandom random)
    throws InvalidKeyException
  {
    try
    {
      engineInit(opmode, key, (AlgorithmParameterSpec)null, random);
    }
    catch (InvalidAlgorithmParameterException e)
    {
      throw new IllegalArgumentException("cannot handle supplied parameter spec: " + e.getMessage());
    }
  }
  







  public byte[] engineUpdate(byte[] input, int inputOffset, int inputLen)
  {
    buffer.write(input, inputOffset, inputLen);
    return null;
  }
  






  public int engineUpdate(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset)
  {
    buffer.write(input, inputOffset, inputLen);
    return 0;
  }
  






  public byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen)
    throws IllegalBlockSizeException, BadPaddingException
  {
    if (inputLen != 0)
    {
      buffer.write(input, inputOffset, inputLen);
    }
    
    byte[] in = buffer.toByteArray();
    buffer.reset();
    




    CipherParameters params = new IESWithCipherParameters(engineSpec.getDerivationV(), engineSpec.getEncodingV(), engineSpec.getMacKeySize(), engineSpec.getCipherKeySize());
    
    if (engineSpec.getNonce() != null)
    {
      params = new ParametersWithIV(params, engineSpec.getNonce());
    }
    
    DHParameters dhParams = ((DHKeyParameters)key).getParameters();
    

    if (otherKeyParameter != null)
    {
      try
      {
        if ((state == 1) || (state == 3))
        {
          engine.init(true, otherKeyParameter, key, params);
        }
        else
        {
          engine.init(false, key, otherKeyParameter, params);
        }
        return engine.processBlock(in, 0, in.length);
      }
      catch (Exception e)
      {
        throw new BadBlockException("unable to process block", e);
      }
    }
    
    if ((state == 1) || (state == 3))
    {

      DHKeyPairGenerator gen = new DHKeyPairGenerator();
      gen.init(new DHKeyGenerationParameters(random, dhParams));
      
      EphemeralKeyPairGenerator kGen = new EphemeralKeyPairGenerator(gen, new KeyEncoder()
      {
        public byte[] getEncoded(AsymmetricKeyParameter keyParameter)
        {
          byte[] Vloc = new byte[(((DHKeyParameters)keyParameter).getParameters().getP().bitLength() + 7) / 8];
          byte[] Vtmp = BigIntegers.asUnsignedByteArray(((DHPublicKeyParameters)keyParameter).getY());
          
          if (Vtmp.length > Vloc.length)
          {
            throw new IllegalArgumentException("Senders's public key longer than expected.");
          }
          

          System.arraycopy(Vtmp, 0, Vloc, Vloc.length - Vtmp.length, Vtmp.length);
          

          return Vloc;
        }
      });
      

      try
      {
        engine.init(key, params, kGen);
        
        return engine.processBlock(in, 0, in.length);
      }
      catch (Exception e)
      {
        throw new BadBlockException("unable to process block", e);
      }
    }
    if ((state == 2) || (state == 4))
    {
      try
      {

        engine.init(key, params, new DHIESPublicKeyParser(((DHKeyParameters)key).getParameters()));
        
        return engine.processBlock(in, 0, in.length);
      }
      catch (InvalidCipherTextException e)
      {
        throw new BadBlockException("unable to process block", e);
      }
    }
    

    throw new IllegalStateException("IESCipher not initialised");
  }
  









  public int engineDoFinal(byte[] input, int inputOffset, int inputLength, byte[] output, int outputOffset)
    throws ShortBufferException, IllegalBlockSizeException, BadPaddingException
  {
    byte[] buf = engineDoFinal(input, inputOffset, inputLength);
    System.arraycopy(buf, 0, output, outputOffset, buf.length);
    return buf.length;
  }
  





  public static class IES
    extends IESCipher
  {
    public IES()
    {
      super();
    }
  }
  
  public static class IESwithDESedeCBC
    extends IESCipher
  {
    public IESwithDESedeCBC()
    {
      super(
      
        8);
    }
  }
  

  public static class IESwithAESCBC
    extends IESCipher
  {
    public IESwithAESCBC()
    {
      super(
      
        16);
    }
  }
}
