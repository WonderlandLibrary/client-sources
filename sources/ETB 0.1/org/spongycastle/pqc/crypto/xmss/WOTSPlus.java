package org.spongycastle.pqc.crypto.xmss;

import java.util.ArrayList;
import java.util.List;
























final class WOTSPlus
{
  private final WOTSPlusParameters params;
  private final KeyedHashFunctions khf;
  private byte[] secretKeySeed;
  private byte[] publicSeed;
  
  protected WOTSPlus(WOTSPlusParameters params)
  {
    if (params == null)
    {
      throw new NullPointerException("params == null");
    }
    this.params = params;
    int n = params.getDigestSize();
    khf = new KeyedHashFunctions(params.getDigest(), n);
    secretKeySeed = new byte[n];
    publicSeed = new byte[n];
  }
  






  void importKeys(byte[] secretKeySeed, byte[] publicSeed)
  {
    if (secretKeySeed == null)
    {
      throw new NullPointerException("secretKeySeed == null");
    }
    if (secretKeySeed.length != params.getDigestSize())
    {
      throw new IllegalArgumentException("size of secretKeySeed needs to be equal to size of digest");
    }
    if (publicSeed == null)
    {
      throw new NullPointerException("publicSeed == null");
    }
    if (publicSeed.length != params.getDigestSize())
    {
      throw new IllegalArgumentException("size of publicSeed needs to be equal to size of digest");
    }
    this.secretKeySeed = secretKeySeed;
    this.publicSeed = publicSeed;
  }
  







  protected WOTSPlusSignature sign(byte[] messageDigest, OTSHashAddress otsHashAddress)
  {
    if (messageDigest == null)
    {
      throw new NullPointerException("messageDigest == null");
    }
    if (messageDigest.length != params.getDigestSize())
    {
      throw new IllegalArgumentException("size of messageDigest needs to be equal to size of digest");
    }
    if (otsHashAddress == null)
    {
      throw new NullPointerException("otsHashAddress == null");
    }
    List<Integer> baseWMessage = convertToBaseW(messageDigest, params.getWinternitzParameter(), params.getLen1());
    
    int checksum = 0;
    for (int i = 0; i < params.getLen1(); i++)
    {
      checksum += params.getWinternitzParameter() - 1 - ((Integer)baseWMessage.get(i)).intValue();
    }
    checksum <<= 8 - params.getLen2() * XMSSUtil.log2(params.getWinternitzParameter()) % 8;
    
    int len2Bytes = (int)Math.ceil(params.getLen2() * XMSSUtil.log2(params.getWinternitzParameter()) / 8.0D);
    List<Integer> baseWChecksum = convertToBaseW(XMSSUtil.toBytesBigEndian(checksum, len2Bytes), params
      .getWinternitzParameter(), params.getLen2());
    

    baseWMessage.addAll(baseWChecksum);
    

    byte[][] signature = new byte[params.getLen()][];
    for (int i = 0; i < params.getLen(); i++)
    {




      otsHashAddress = (OTSHashAddress)((OTSHashAddress.Builder)((OTSHashAddress.Builder)((OTSHashAddress.Builder)new OTSHashAddress.Builder().withLayerAddress(otsHashAddress.getLayerAddress())).withTreeAddress(otsHashAddress.getTreeAddress())).withOTSAddress(otsHashAddress.getOTSAddress()).withChainAddress(i).withHashAddress(otsHashAddress.getHashAddress()).withKeyAndMask(otsHashAddress.getKeyAndMask())).build();
      signature[i] = chain(expandSecretKeySeed(i), 0, ((Integer)baseWMessage.get(i)).intValue(), otsHashAddress);
    }
    return new WOTSPlusSignature(params, signature);
  }
  









  protected boolean verifySignature(byte[] messageDigest, WOTSPlusSignature signature, OTSHashAddress otsHashAddress)
  {
    if (messageDigest == null)
    {
      throw new NullPointerException("messageDigest == null");
    }
    if (messageDigest.length != params.getDigestSize())
    {
      throw new IllegalArgumentException("size of messageDigest needs to be equal to size of digest");
    }
    if (signature == null)
    {
      throw new NullPointerException("signature == null");
    }
    if (otsHashAddress == null)
    {
      throw new NullPointerException("otsHashAddress == null");
    }
    byte[][] tmpPublicKey = getPublicKeyFromSignature(messageDigest, signature, otsHashAddress).toByteArray();
    
    return XMSSUtil.areEqual(tmpPublicKey, getPublicKey(otsHashAddress).toByteArray());
  }
  









  protected WOTSPlusPublicKeyParameters getPublicKeyFromSignature(byte[] messageDigest, WOTSPlusSignature signature, OTSHashAddress otsHashAddress)
  {
    if (messageDigest == null)
    {
      throw new NullPointerException("messageDigest == null");
    }
    if (messageDigest.length != params.getDigestSize())
    {
      throw new IllegalArgumentException("size of messageDigest needs to be equal to size of digest");
    }
    if (signature == null)
    {
      throw new NullPointerException("signature == null");
    }
    if (otsHashAddress == null)
    {
      throw new NullPointerException("otsHashAddress == null");
    }
    List<Integer> baseWMessage = convertToBaseW(messageDigest, params.getWinternitzParameter(), params.getLen1());
    
    int checksum = 0;
    for (int i = 0; i < params.getLen1(); i++)
    {
      checksum += params.getWinternitzParameter() - 1 - ((Integer)baseWMessage.get(i)).intValue();
    }
    checksum <<= 8 - params.getLen2() * XMSSUtil.log2(params.getWinternitzParameter()) % 8;
    
    int len2Bytes = (int)Math.ceil(params.getLen2() * XMSSUtil.log2(params.getWinternitzParameter()) / 8.0D);
    List<Integer> baseWChecksum = convertToBaseW(XMSSUtil.toBytesBigEndian(checksum, len2Bytes), params
      .getWinternitzParameter(), params.getLen2());
    

    baseWMessage.addAll(baseWChecksum);
    
    byte[][] publicKey = new byte[params.getLen()][];
    for (int i = 0; i < params.getLen(); i++)
    {




      otsHashAddress = (OTSHashAddress)((OTSHashAddress.Builder)((OTSHashAddress.Builder)((OTSHashAddress.Builder)new OTSHashAddress.Builder().withLayerAddress(otsHashAddress.getLayerAddress())).withTreeAddress(otsHashAddress.getTreeAddress())).withOTSAddress(otsHashAddress.getOTSAddress()).withChainAddress(i).withHashAddress(otsHashAddress.getHashAddress()).withKeyAndMask(otsHashAddress.getKeyAndMask())).build();
      publicKey[i] = chain(signature.toByteArray()[i], ((Integer)baseWMessage.get(i)).intValue(), params
        .getWinternitzParameter() - 1 - ((Integer)baseWMessage.get(i)).intValue(), otsHashAddress);
    }
    return new WOTSPlusPublicKeyParameters(params, publicKey);
  }
  










  private byte[] chain(byte[] startHash, int startIndex, int steps, OTSHashAddress otsHashAddress)
  {
    int n = params.getDigestSize();
    if (startHash == null)
    {
      throw new NullPointerException("startHash == null");
    }
    if (startHash.length != n)
    {
      throw new IllegalArgumentException("startHash needs to be " + n + "bytes");
    }
    if (otsHashAddress == null)
    {
      throw new NullPointerException("otsHashAddress == null");
    }
    if (otsHashAddress.toByteArray() == null)
    {
      throw new NullPointerException("otsHashAddress byte array == null");
    }
    if (startIndex + steps > params.getWinternitzParameter() - 1)
    {
      throw new IllegalArgumentException("max chain length must not be greater than w");
    }
    
    if (steps == 0)
    {
      return startHash;
    }
    
    byte[] tmp = chain(startHash, startIndex, steps - 1, otsHashAddress);
    


    otsHashAddress = (OTSHashAddress)((OTSHashAddress.Builder)((OTSHashAddress.Builder)((OTSHashAddress.Builder)new OTSHashAddress.Builder().withLayerAddress(otsHashAddress.getLayerAddress())).withTreeAddress(otsHashAddress.getTreeAddress())).withOTSAddress(otsHashAddress.getOTSAddress()).withChainAddress(otsHashAddress.getChainAddress()).withHashAddress(startIndex + steps - 1).withKeyAndMask(0)).build();
    byte[] key = khf.PRF(publicSeed, otsHashAddress.toByteArray());
    


    otsHashAddress = (OTSHashAddress)((OTSHashAddress.Builder)((OTSHashAddress.Builder)((OTSHashAddress.Builder)new OTSHashAddress.Builder().withLayerAddress(otsHashAddress.getLayerAddress())).withTreeAddress(otsHashAddress.getTreeAddress())).withOTSAddress(otsHashAddress.getOTSAddress()).withChainAddress(otsHashAddress.getChainAddress()).withHashAddress(otsHashAddress.getHashAddress()).withKeyAndMask(1)).build();
    byte[] bitmask = khf.PRF(publicSeed, otsHashAddress.toByteArray());
    byte[] tmpMasked = new byte[n];
    for (int i = 0; i < n; i++)
    {
      tmpMasked[i] = ((byte)(tmp[i] ^ bitmask[i]));
    }
    tmp = khf.F(key, tmpMasked);
    return tmp;
  }
  








  private List<Integer> convertToBaseW(byte[] messageDigest, int w, int outLength)
  {
    if (messageDigest == null)
    {
      throw new NullPointerException("msg == null");
    }
    if ((w != 4) && (w != 16))
    {
      throw new IllegalArgumentException("w needs to be 4 or 16");
    }
    int logW = XMSSUtil.log2(w);
    if (outLength > 8 * messageDigest.length / logW)
    {
      throw new IllegalArgumentException("outLength too big");
    }
    
    ArrayList<Integer> res = new ArrayList();
    for (int i = 0; i < messageDigest.length; i++)
    {
      for (int j = 8 - logW; j >= 0; j -= logW)
      {
        res.add(Integer.valueOf(messageDigest[i] >> j & w - 1));
        if (res.size() == outLength)
        {
          return res;
        }
      }
    }
    return res;
  }
  









  protected byte[] getWOTSPlusSecretKey(byte[] secretKeySeed, OTSHashAddress otsHashAddress)
  {
    otsHashAddress = (OTSHashAddress)((OTSHashAddress.Builder)((OTSHashAddress.Builder)new OTSHashAddress.Builder().withLayerAddress(otsHashAddress.getLayerAddress())).withTreeAddress(otsHashAddress.getTreeAddress())).withOTSAddress(otsHashAddress.getOTSAddress()).build();
    return khf.PRF(secretKeySeed, otsHashAddress.toByteArray());
  }
  






  private byte[] expandSecretKeySeed(int index)
  {
    if ((index < 0) || (index >= params.getLen()))
    {
      throw new IllegalArgumentException("index out of bounds");
    }
    return khf.PRF(secretKeySeed, XMSSUtil.toBytesBigEndian(index, 32));
  }
  





  protected WOTSPlusParameters getParams()
  {
    return params;
  }
  





  protected KeyedHashFunctions getKhf()
  {
    return khf;
  }
  





  protected byte[] getSecretKeySeed()
  {
    return XMSSUtil.cloneArray(getSecretKeySeed());
  }
  





  protected byte[] getPublicSeed()
  {
    return XMSSUtil.cloneArray(publicSeed);
  }
  





  protected WOTSPlusPrivateKeyParameters getPrivateKey()
  {
    byte[][] privateKey = new byte[params.getLen()][];
    for (int i = 0; i < privateKey.length; i++)
    {
      privateKey[i] = expandSecretKeySeed(i);
    }
    return new WOTSPlusPrivateKeyParameters(params, privateKey);
  }
  







  protected WOTSPlusPublicKeyParameters getPublicKey(OTSHashAddress otsHashAddress)
  {
    if (otsHashAddress == null)
    {
      throw new NullPointerException("otsHashAddress == null");
    }
    byte[][] publicKey = new byte[params.getLen()][];
    
    for (int i = 0; i < params.getLen(); i++)
    {




      otsHashAddress = (OTSHashAddress)((OTSHashAddress.Builder)((OTSHashAddress.Builder)((OTSHashAddress.Builder)new OTSHashAddress.Builder().withLayerAddress(otsHashAddress.getLayerAddress())).withTreeAddress(otsHashAddress.getTreeAddress())).withOTSAddress(otsHashAddress.getOTSAddress()).withChainAddress(i).withHashAddress(otsHashAddress.getHashAddress()).withKeyAndMask(otsHashAddress.getKeyAndMask())).build();
      publicKey[i] = chain(expandSecretKeySeed(i), 0, params.getWinternitzParameter() - 1, otsHashAddress);
    }
    return new WOTSPlusPublicKeyParameters(params, publicKey);
  }
}
