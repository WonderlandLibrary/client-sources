package org.spongycastle.crypto.digests;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.engines.ThreefishEngine;
import org.spongycastle.crypto.params.SkeinParameters;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Memoable;









































public class SkeinEngine
  implements Memoable
{
  public static final int SKEIN_256 = 256;
  public static final int SKEIN_512 = 512;
  public static final int SKEIN_1024 = 1024;
  private static final int PARAM_TYPE_KEY = 0;
  private static final int PARAM_TYPE_CONFIG = 4;
  private static final int PARAM_TYPE_MESSAGE = 48;
  private static final int PARAM_TYPE_OUTPUT = 63;
  
  private static class Configuration
  {
    private byte[] bytes = new byte[32];
    

    public Configuration(long outputSizeBits)
    {
      bytes[0] = 83;
      bytes[1] = 72;
      bytes[2] = 65;
      bytes[3] = 51;
      

      bytes[4] = 1;
      bytes[5] = 0;
      

      ThreefishEngine.wordToBytes(outputSizeBits, bytes, 8);
    }
    
    public byte[] getBytes()
    {
      return bytes;
    }
  }
  

  public static class Parameter
  {
    private int type;
    private byte[] value;
    
    public Parameter(int type, byte[] value)
    {
      this.type = type;
      this.value = value;
    }
    
    public int getType()
    {
      return type;
    }
    
    public byte[] getValue()
    {
      return value;
    }
  }
  

























  private static final Hashtable INITIAL_STATES = new Hashtable();
  final ThreefishEngine threefish;
  private final int outputSizeBytes;
  
  static {
    initialState(256, 128, new long[] { -2228972824489528736L, -8629553674646093540L, 1155188648486244218L, -3677226592081559102L });
    




    initialState(256, 160, new long[] { 1450197650740764312L, 3081844928540042640L, -3136097061834271170L, 3301952811952417661L });
    




    initialState(256, 224, new long[] { -4176654842910610933L, -8688192972455077604L, -7364642305011795836L, 4056579644589979102L });
    




    initialState(256, 256, new long[] { -243853671043386295L, 3443677322885453875L, -5531612722399640561L, 7662005193972177513L });
    




    initialState(512, 128, new long[] { -6288014694233956526L, 2204638249859346602L, 3502419045458743507L, -4829063503441264548L, 983504137758028059L, 1880512238245786339L, -6715892782214108542L, 7602827311880509485L });
    








    initialState(512, 160, new long[] { 2934123928682216849L, -4399710721982728305L, 1684584802963255058L, 5744138295201861711L, 2444857010922934358L, -2807833639722848072L, -5121587834665610502L, 118355523173251694L });
    








    initialState(512, 224, new long[] { -3688341020067007964L, -3772225436291745297L, -8300862168937575580L, 4146387520469897396L, 1106145742801415120L, 7455425944880474941L, -7351063101234211863L, -7048981346965512457L });
    








    initialState(512, 384, new long[] { -6631894876634615969L, -5692838220127733084L, -7099962856338682626L, -2911352911530754598L, 2000907093792408677L, 9140007292425499655L, 6093301768906360022L, 2769176472213098488L });
    








    initialState(512, 512, new long[] { 5261240102383538638L, 978932832955457283L, -8083517948103779378L, -7339365279355032399L, 6752626034097301424L, -1531723821829733388L, -7417126464950782685L, -5901786942805128141L });
  }
  








  private static void initialState(int blockSize, int outputSize, long[] state)
  {
    INITIAL_STATES.put(variantIdentifier(blockSize / 8, outputSize / 8), state);
  }
  
  private static Integer variantIdentifier(int blockSizeBytes, int outputSizeBytes)
  {
    return new Integer(outputSizeBytes << 16 | blockSizeBytes);
  }
  




  private static class UbiTweak
  {
    private static final long LOW_RANGE = 9223372034707292160L;
    



    private static final long T1_FINAL = Long.MIN_VALUE;
    



    private static final long T1_FIRST = 4611686018427387904L;
    


    private long[] tweak = new long[2];
    

    private boolean extendedPosition;
    


    public UbiTweak()
    {
      reset();
    }
    
    public void reset(UbiTweak tweak)
    {
      this.tweak = Arrays.clone(tweak, this.tweak);
      extendedPosition = extendedPosition;
    }
    
    public void reset()
    {
      tweak[0] = 0L;
      tweak[1] = 0L;
      extendedPosition = false;
      setFirst(true);
    }
    

    public void setType(int type)
    {
      tweak[1] = (tweak[1] & 0xFFFFFFC000000000 | (type & 0x3F) << 56);
    }
    
    public int getType()
    {
      return (int)(tweak[1] >>> 56 & 0x3F);
    }
    
    public void setFirst(boolean first)
    {
      if (first)
      {
        tweak[1] |= 0x4000000000000000;
      }
      else
      {
        tweak[1] &= 0xBFFFFFFFFFFFFFFF;
      }
    }
    
    public boolean isFirst()
    {
      return (tweak[1] & 0x4000000000000000) != 0L;
    }
    
    public void setFinal(boolean last)
    {
      if (last)
      {
        tweak[1] |= 0x8000000000000000;
      }
      else
      {
        tweak[1] &= 0x7FFFFFFFFFFFFFFF;
      }
    }
    
    public boolean isFinal()
    {
      return (tweak[1] & 0x8000000000000000) != 0L;
    }
    




    public void advancePosition(int advance)
    {
      if (extendedPosition)
      {
        long[] parts = new long[3];
        parts[0] = (tweak[0] & 0xFFFFFFFF);
        parts[1] = (tweak[0] >>> 32 & 0xFFFFFFFF);
        parts[2] = (tweak[1] & 0xFFFFFFFF);
        
        long carry = advance;
        for (int i = 0; i < parts.length; i++)
        {
          carry += parts[i];
          parts[i] = carry;
          carry >>>= 32;
        }
        tweak[0] = ((parts[1] & 0xFFFFFFFF) << 32 | parts[0] & 0xFFFFFFFF);
        tweak[1] = (tweak[1] & 0xFFFFFFFF00000000 | parts[2] & 0xFFFFFFFF);
      }
      else
      {
        long position = tweak[0];
        position += advance;
        tweak[0] = position;
        if (position > 9223372034707292160L)
        {
          extendedPosition = true;
        }
      }
    }
    
    public long[] getWords()
    {
      return tweak;
    }
    
    public String toString()
    {
      return getType() + " first: " + isFirst() + ", final: " + isFinal();
    }
  }
  





  private class UBI
  {
    private final SkeinEngine.UbiTweak tweak = new SkeinEngine.UbiTweak();
    


    private byte[] currentBlock;
    


    private int currentOffset;
    


    private long[] message;
    



    public UBI(int blockSize)
    {
      currentBlock = new byte[blockSize];
      message = new long[currentBlock.length / 8];
    }
    
    public void reset(UBI ubi)
    {
      currentBlock = Arrays.clone(currentBlock, currentBlock);
      currentOffset = currentOffset;
      message = Arrays.clone(message, message);
      tweak.reset(tweak);
    }
    
    public void reset(int type)
    {
      tweak.reset();
      tweak.setType(type);
      currentOffset = 0;
    }
    




    public void update(byte[] value, int offset, int len, long[] output)
    {
      int copied = 0;
      while (len > copied)
      {
        if (currentOffset == currentBlock.length)
        {
          processBlock(output);
          tweak.setFirst(false);
          currentOffset = 0;
        }
        
        int toCopy = Math.min(len - copied, currentBlock.length - currentOffset);
        System.arraycopy(value, offset + copied, currentBlock, currentOffset, toCopy);
        copied += toCopy;
        currentOffset += toCopy;
        tweak.advancePosition(toCopy);
      }
    }
    
    private void processBlock(long[] output)
    {
      threefish.init(true, chain, tweak.getWords());
      for (int i = 0; i < message.length; i++)
      {
        message[i] = ThreefishEngine.bytesToWord(currentBlock, i * 8);
      }
      
      threefish.processBlock(message, output);
      
      for (int i = 0; i < output.length; i++)
      {
        output[i] ^= message[i];
      }
    }
    

    public void doFinal(long[] output)
    {
      for (int i = currentOffset; i < currentBlock.length; i++)
      {
        currentBlock[i] = 0;
      }
      
      tweak.setFinal(true);
      processBlock(output);
    }
  }
  






  long[] chain;
  





  private long[] initialState;
  





  private byte[] key;
  




  private Parameter[] preMessageParameters;
  




  private Parameter[] postMessageParameters;
  




  private final UBI ubi;
  




  private final byte[] singleByte = new byte[1];
  








  public SkeinEngine(int blockSizeBits, int outputSizeBits)
  {
    if (outputSizeBits % 8 != 0)
    {
      throw new IllegalArgumentException("Output size must be a multiple of 8 bits. :" + outputSizeBits);
    }
    
    outputSizeBytes = (outputSizeBits / 8);
    
    threefish = new ThreefishEngine(blockSizeBits);
    ubi = new UBI(threefish.getBlockSize());
  }
  



  public SkeinEngine(SkeinEngine engine)
  {
    this(engine.getBlockSize() * 8, engine.getOutputSize() * 8);
    copyIn(engine);
  }
  
  private void copyIn(SkeinEngine engine)
  {
    ubi.reset(ubi);
    chain = Arrays.clone(chain, chain);
    initialState = Arrays.clone(initialState, initialState);
    key = Arrays.clone(key, key);
    preMessageParameters = clone(preMessageParameters, preMessageParameters);
    postMessageParameters = clone(postMessageParameters, postMessageParameters);
  }
  
  private static Parameter[] clone(Parameter[] data, Parameter[] existing)
  {
    if (data == null)
    {
      return null;
    }
    if ((existing == null) || (existing.length != data.length))
    {
      existing = new Parameter[data.length];
    }
    System.arraycopy(data, 0, existing, 0, existing.length);
    return existing;
  }
  
  public Memoable copy()
  {
    return new SkeinEngine(this);
  }
  
  public void reset(Memoable other)
  {
    SkeinEngine s = (SkeinEngine)other;
    if ((getBlockSize() != s.getBlockSize()) || (outputSizeBytes != outputSizeBytes))
    {
      throw new IllegalArgumentException("Incompatible parameters in provided SkeinEngine.");
    }
    copyIn(s);
  }
  
  public int getOutputSize()
  {
    return outputSizeBytes;
  }
  
  public int getBlockSize()
  {
    return threefish.getBlockSize();
  }
  






  public void init(SkeinParameters params)
  {
    chain = null;
    this.key = null;
    preMessageParameters = null;
    postMessageParameters = null;
    
    if (params != null)
    {
      byte[] key = params.getKey();
      if (key.length < 16)
      {
        throw new IllegalArgumentException("Skein key must be at least 128 bits.");
      }
      initParams(params.getParameters());
    }
    createInitialState();
    

    ubiInit(48);
  }
  
  private void initParams(Hashtable parameters)
  {
    Enumeration keys = parameters.keys();
    Vector pre = new Vector();
    Vector post = new Vector();
    
    while (keys.hasMoreElements())
    {
      Integer type = (Integer)keys.nextElement();
      byte[] value = (byte[])parameters.get(type);
      
      if (type.intValue() == 0)
      {
        key = value;
      }
      else if (type.intValue() < 48)
      {
        pre.addElement(new Parameter(type.intValue(), value));
      }
      else
      {
        post.addElement(new Parameter(type.intValue(), value));
      }
    }
    preMessageParameters = new Parameter[pre.size()];
    pre.copyInto(preMessageParameters);
    sort(preMessageParameters);
    
    postMessageParameters = new Parameter[post.size()];
    post.copyInto(postMessageParameters);
    sort(postMessageParameters);
  }
  
  private static void sort(Parameter[] params)
  {
    if (params == null)
    {
      return;
    }
    
    for (int i = 1; i < params.length; i++)
    {
      Parameter param = params[i];
      int hole = i;
      while ((hole > 0) && (param.getType() < params[(hole - 1)].getType()))
      {
        params[hole] = params[(hole - 1)];
        hole -= 1;
      }
      params[hole] = param;
    }
  }
  



  private void createInitialState()
  {
    long[] precalc = (long[])INITIAL_STATES.get(variantIdentifier(getBlockSize(), getOutputSize()));
    if ((key == null) && (precalc != null))
    {

      chain = Arrays.clone(precalc);

    }
    else
    {
      chain = new long[getBlockSize() / 8];
      

      if (key != null)
      {
        ubiComplete(0, key);
      }
      

      ubiComplete(4, new Configuration(outputSizeBytes * 8).getBytes());
    }
    

    if (preMessageParameters != null)
    {
      for (int i = 0; i < preMessageParameters.length; i++)
      {
        Parameter param = preMessageParameters[i];
        ubiComplete(param.getType(), param.getValue());
      }
    }
    initialState = Arrays.clone(chain);
  }
  




  public void reset()
  {
    System.arraycopy(initialState, 0, chain, 0, chain.length);
    
    ubiInit(48);
  }
  
  private void ubiComplete(int type, byte[] value)
  {
    ubiInit(type);
    ubi.update(value, 0, value.length, chain);
    ubiFinal();
  }
  
  private void ubiInit(int type)
  {
    ubi.reset(type);
  }
  
  private void ubiFinal()
  {
    ubi.doFinal(chain);
  }
  
  private void checkInitialised()
  {
    if (ubi == null)
    {
      throw new IllegalArgumentException("Skein engine is not initialised.");
    }
  }
  
  public void update(byte in)
  {
    singleByte[0] = in;
    update(singleByte, 0, 1);
  }
  
  public void update(byte[] in, int inOff, int len)
  {
    checkInitialised();
    ubi.update(in, inOff, len, chain);
  }
  
  public int doFinal(byte[] out, int outOff)
  {
    checkInitialised();
    if (out.length < outOff + outputSizeBytes)
    {
      throw new OutputLengthException("Output buffer is too short to hold output");
    }
    

    ubiFinal();
    

    if (postMessageParameters != null)
    {
      for (int i = 0; i < postMessageParameters.length; i++)
      {
        Parameter param = postMessageParameters[i];
        ubiComplete(param.getType(), param.getValue());
      }
    }
    

    int blockSize = getBlockSize();
    int blocksRequired = (outputSizeBytes + blockSize - 1) / blockSize;
    for (int i = 0; i < blocksRequired; i++)
    {
      int toWrite = Math.min(blockSize, outputSizeBytes - i * blockSize);
      output(i, out, outOff + i * blockSize, toWrite);
    }
    
    reset();
    
    return outputSizeBytes;
  }
  
  private void output(long outputSequence, byte[] out, int outOff, int outputBytes)
  {
    byte[] currentBytes = new byte[8];
    ThreefishEngine.wordToBytes(outputSequence, currentBytes, 0);
    


    long[] outputWords = new long[chain.length];
    ubiInit(63);
    ubi.update(currentBytes, 0, currentBytes.length, outputWords);
    ubi.doFinal(outputWords);
    
    int wordsRequired = (outputBytes + 8 - 1) / 8;
    for (int i = 0; i < wordsRequired; i++)
    {
      int toWrite = Math.min(8, outputBytes - i * 8);
      if (toWrite == 8)
      {
        ThreefishEngine.wordToBytes(outputWords[i], out, outOff + i * 8);
      }
      else
      {
        ThreefishEngine.wordToBytes(outputWords[i], currentBytes, 0);
        System.arraycopy(currentBytes, 0, out, outOff + i * 8, toWrite);
      }
    }
  }
}
