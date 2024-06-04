package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.io.ByteArrayOutputStream;
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
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.KeyEncoder;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.agreement.ECDHBasicAgreement;
import org.spongycastle.crypto.engines.AESEngine;
import org.spongycastle.crypto.engines.DESedeEngine;
import org.spongycastle.crypto.engines.IESEngine;
import org.spongycastle.crypto.generators.ECKeyPairGenerator;
import org.spongycastle.crypto.generators.EphemeralKeyPairGenerator;
import org.spongycastle.crypto.generators.KDF2BytesGenerator;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.IESWithCipherParameters;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.parsers.ECIESPublicKeyParser;
import org.spongycastle.crypto.util.DigestFactory;
import org.spongycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.IESUtil;
import org.spongycastle.jcajce.provider.util.BadBlockException;
import org.spongycastle.jcajce.util.BCJcaJceHelper;
import org.spongycastle.jcajce.util.JcaJceHelper;
import org.spongycastle.jce.interfaces.ECKey;
import org.spongycastle.jce.interfaces.IESKey;
import org.spongycastle.jce.spec.ECParameterSpec;
import org.spongycastle.jce.spec.IESParameterSpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.Strings;

public class IESCipher
  extends CipherSpi
{
  private final JcaJceHelper helper = new BCJcaJceHelper();
  
  private int ivLength;
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
    if ((key instanceof ECKey))
    {
      return ((ECKey)key).getParameters().getCurve().getFieldSize();
    }
    

    throw new IllegalArgumentException("not an EC key");
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
      ECCurve c = ((ECKeyParameters)key).getParameters().getCurve();
      int feSize = (c.getFieldSize() + 7) / 8;
      len2 = 2 * feSize;
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
      return buffer.size() + len1 + 1 + len2 + len3;
    }
    if ((state == 2) || (state == 4))
    {
      return buffer.size() - len1 - len2 + len3;
    }
    

    throw new IllegalStateException("cipher not initialised");
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
    otherKeyParameter = null;
    

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
      if ((key instanceof PublicKey))
      {
        this.key = ECUtils.generatePublicKeyParameter((PublicKey)key);
      }
      else if ((key instanceof IESKey))
      {
        IESKey ieKey = (IESKey)key;
        
        this.key = ECUtils.generatePublicKeyParameter(ieKey.getPublic());
        otherKeyParameter = ECUtil.generatePrivateKeyParameter(ieKey.getPrivate());
      }
      else
      {
        throw new InvalidKeyException("must be passed recipient's public EC key for encryption");
      }
    }
    else if ((opmode == 2) || (opmode == 4))
    {
      if ((key instanceof PrivateKey))
      {
        this.key = ECUtil.generatePrivateKeyParameter((PrivateKey)key);
      }
      else if ((key instanceof IESKey))
      {
        IESKey ieKey = (IESKey)key;
        
        otherKeyParameter = ECUtils.generatePublicKeyParameter(ieKey.getPublic());
        this.key = ECUtil.generatePrivateKeyParameter(ieKey.getPrivate());
      }
      else
      {
        throw new InvalidKeyException("must be passed recipient's private EC key for decryption");
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
    
    ECDomainParameters ecParams = ((ECKeyParameters)key).getParameters();
    


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

      ECKeyPairGenerator gen = new ECKeyPairGenerator();
      gen.init(new ECKeyGenerationParameters(ecParams, random));
      
      final boolean usePointCompression = engineSpec.getPointCompression();
      EphemeralKeyPairGenerator kGen = new EphemeralKeyPairGenerator(gen, new KeyEncoder()
      {
        public byte[] getEncoded(AsymmetricKeyParameter keyParameter)
        {
          return ((ECPublicKeyParameters)keyParameter).getQ().getEncoded(usePointCompression);
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

        engine.init(key, params, new ECIESPublicKeyParser(ecParams));
        
        return engine.processBlock(in, 0, in.length);
      }
      catch (InvalidCipherTextException e)
      {
        throw new BadBlockException("unable to process block", e);
      }
    }
    

    throw new IllegalStateException("cipher not initialised");
  }
  








  public int engineDoFinal(byte[] input, int inputOffset, int inputLength, byte[] output, int outputOffset)
    throws ShortBufferException, IllegalBlockSizeException, BadPaddingException
  {
    byte[] buf = engineDoFinal(input, inputOffset, inputLength);
    System.arraycopy(buf, 0, output, outputOffset, buf.length);
    return buf.length;
  }
  




  public static class ECIES
    extends IESCipher
  {
    public ECIES()
    {
      super();
    }
  }
  
  public static class ECIESwithCipher
    extends IESCipher
  {
    public ECIESwithCipher(BlockCipher cipher, int ivLength)
    {
      super(
      
        ivLength);
    }
  }
  

  public static class ECIESwithDESedeCBC
    extends IESCipher.ECIESwithCipher
  {
    public ECIESwithDESedeCBC()
    {
      super(8);
    }
  }
  
  public static class ECIESwithAESCBC
    extends IESCipher.ECIESwithCipher
  {
    public ECIESwithAESCBC()
    {
      super(16);
    }
  }
}
