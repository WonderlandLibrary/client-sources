package org.spongycastle.crypto.examples;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.security.SecureRandom;
import org.spongycastle.crypto.CryptoException;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.engines.DESedeEngine;
import org.spongycastle.crypto.generators.DESedeKeyGenerator;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.encoders.Hex;


































public class DESExample
{
  private boolean encrypt = true;
  

  private PaddedBufferedBlockCipher cipher = null;
  

  private BufferedInputStream in = null;
  

  private BufferedOutputStream out = null;
  

  private byte[] key = null;
  



  public static void main(String[] args)
  {
    boolean encrypt = true;
    String infile = null;
    String outfile = null;
    String keyfile = null;
    
    if (args.length < 2)
    {
      DESExample de = new DESExample();
      System.err.println("Usage: java " + de.getClass().getName() + " infile outfile [keyfile]");
      
      System.exit(1);
    }
    
    keyfile = "deskey.dat";
    infile = args[0];
    outfile = args[1];
    
    if (args.length > 2)
    {
      encrypt = false;
      keyfile = args[2];
    }
    
    DESExample de = new DESExample(infile, outfile, keyfile, encrypt);
    de.process();
  }
  










  public DESExample() {}
  









  public DESExample(String infile, String outfile, String keyfile, boolean encrypt)
  {
    this.encrypt = encrypt;
    try
    {
      in = new BufferedInputStream(new FileInputStream(infile));
    }
    catch (FileNotFoundException fnf)
    {
      System.err.println("Input file not found [" + infile + "]");
      System.exit(1);
    }
    
    try
    {
      out = new BufferedOutputStream(new FileOutputStream(outfile));
    }
    catch (IOException fnf)
    {
      System.err.println("Output file not created [" + outfile + "]");
      System.exit(1);
    }
    
    if (encrypt)
    {




      try
      {




        SecureRandom sr = null;
        try
        {
          sr = new SecureRandom();
          













          sr.setSeed("www.bouncycastle.org".getBytes());
        }
        catch (Exception nsa)
        {
          System.err.println("Hmmm, no SHA1PRNG, you need the Sun implementation");
          
          System.exit(1);
        }
        KeyGenerationParameters kgp = new KeyGenerationParameters(sr, 192);
        





        DESedeKeyGenerator kg = new DESedeKeyGenerator();
        kg.init(kgp);
        



        key = kg.generateKey();
        





        BufferedOutputStream keystream = new BufferedOutputStream(new FileOutputStream(keyfile));
        
        byte[] keyhex = Hex.encode(key);
        keystream.write(keyhex, 0, keyhex.length);
        keystream.flush();
        keystream.close();
      }
      catch (IOException createKey)
      {
        System.err.println("Could not decryption create key file [" + keyfile + "]");
        
        System.exit(1);
      }
      
    }
    else
    {
      try
      {
        BufferedInputStream keystream = new BufferedInputStream(new FileInputStream(keyfile));
        
        int len = keystream.available();
        byte[] keyhex = new byte[len];
        keystream.read(keyhex, 0, len);
        key = Hex.decode(keyhex);
      }
      catch (IOException ioe)
      {
        System.err.println("Decryption key file not found, or not valid [" + keyfile + "]");
        
        System.exit(1);
      }
    }
  }
  




  private void process()
  {
    cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new DESedeEngine()));
    








    if (encrypt)
    {
      performEncrypt(key);
    }
    else
    {
      performDecrypt(key);
    }
    

    try
    {
      in.close();
      out.flush();
      out.close();
    }
    catch (IOException closing)
    {
      System.err.println("exception closing resources: " + closing.getMessage());
    }
  }
  






  private void performEncrypt(byte[] key)
  {
    cipher.init(true, new KeyParameter(key));
    











    int inBlockSize = 47;
    int outBlockSize = cipher.getOutputSize(inBlockSize);
    
    byte[] inblock = new byte[inBlockSize];
    byte[] outblock = new byte[outBlockSize];
    





    try
    {
      byte[] rv = null;
      int inL; while ((inL = in.read(inblock, 0, inBlockSize)) > 0)
      {
        int outL = cipher.processBytes(inblock, 0, inL, outblock, 0);
        



        if (outL > 0)
        {
          rv = Hex.encode(outblock, 0, outL);
          out.write(rv, 0, rv.length);
          out.write(10);
        }
      }
      




      try
      {
        int outL = cipher.doFinal(outblock, 0);
        if (outL > 0)
        {
          rv = Hex.encode(outblock, 0, outL);
          out.write(rv, 0, rv.length);
          out.write(10);
        }
        

      }
      catch (CryptoException localCryptoException) {}

    }
    catch (IOException ioeread)
    {
      ioeread.printStackTrace();
    }
  }
  






  private void performDecrypt(byte[] key)
  {
    cipher.init(false, new KeyParameter(key));
    






    BufferedReader br = new BufferedReader(new InputStreamReader(in));
    




    try
    {
      byte[] inblock = null;
      byte[] outblock = null;
      String rv = null;
      while ((rv = br.readLine()) != null)
      {
        inblock = Hex.decode(rv);
        outblock = new byte[cipher.getOutputSize(inblock.length)];
        
        int outL = cipher.processBytes(inblock, 0, inblock.length, outblock, 0);
        




        if (outL > 0)
        {
          out.write(outblock, 0, outL);
        }
      }
      




      try
      {
        int outL = cipher.doFinal(outblock, 0);
        if (outL > 0)
        {
          out.write(outblock, 0, outL);
        }
        

      }
      catch (CryptoException localCryptoException) {}

    }
    catch (IOException ioeread)
    {
      ioeread.printStackTrace();
    }
  }
}
