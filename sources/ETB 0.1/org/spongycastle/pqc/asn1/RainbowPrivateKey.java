package org.spongycastle.pqc.asn1;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.pqc.crypto.rainbow.Layer;
import org.spongycastle.pqc.crypto.rainbow.util.RainbowUtil;
































public class RainbowPrivateKey
  extends ASN1Object
{
  private ASN1Integer version;
  private ASN1ObjectIdentifier oid;
  private byte[][] invA1;
  private byte[] b1;
  private byte[][] invA2;
  private byte[] b2;
  private byte[] vi;
  private Layer[] layers;
  
  private RainbowPrivateKey(ASN1Sequence seq)
  {
    if ((seq.getObjectAt(0) instanceof ASN1Integer))
    {
      version = ASN1Integer.getInstance(seq.getObjectAt(0));
    }
    else
    {
      oid = ASN1ObjectIdentifier.getInstance(seq.getObjectAt(0));
    }
    

    ASN1Sequence asnA1 = (ASN1Sequence)seq.getObjectAt(1);
    invA1 = new byte[asnA1.size()][];
    for (int i = 0; i < asnA1.size(); i++)
    {
      invA1[i] = ((ASN1OctetString)asnA1.getObjectAt(i)).getOctets();
    }
    

    ASN1Sequence asnb1 = (ASN1Sequence)seq.getObjectAt(2);
    b1 = ((ASN1OctetString)asnb1.getObjectAt(0)).getOctets();
    

    ASN1Sequence asnA2 = (ASN1Sequence)seq.getObjectAt(3);
    invA2 = new byte[asnA2.size()][];
    for (int j = 0; j < asnA2.size(); j++)
    {
      invA2[j] = ((ASN1OctetString)asnA2.getObjectAt(j)).getOctets();
    }
    

    ASN1Sequence asnb2 = (ASN1Sequence)seq.getObjectAt(4);
    b2 = ((ASN1OctetString)asnb2.getObjectAt(0)).getOctets();
    

    ASN1Sequence asnvi = (ASN1Sequence)seq.getObjectAt(5);
    vi = ((ASN1OctetString)asnvi.getObjectAt(0)).getOctets();
    

    ASN1Sequence asnLayers = (ASN1Sequence)seq.getObjectAt(6);
    
    byte[][][][] alphas = new byte[asnLayers.size()][][][];
    byte[][][][] betas = new byte[asnLayers.size()][][][];
    byte[][][] gammas = new byte[asnLayers.size()][][];
    byte[][] etas = new byte[asnLayers.size()][];
    
    for (int l = 0; l < asnLayers.size(); l++)
    {
      ASN1Sequence asnLayer = (ASN1Sequence)asnLayers.getObjectAt(l);
      

      ASN1Sequence alphas3d = (ASN1Sequence)asnLayer.getObjectAt(0);
      alphas[l] = new byte[alphas3d.size()][][];
      for (int m = 0; m < alphas3d.size(); m++)
      {
        ASN1Sequence alphas2d = (ASN1Sequence)alphas3d.getObjectAt(m);
        alphas[l][m] = new byte[alphas2d.size()][];
        for (int n = 0; n < alphas2d.size(); n++)
        {
          alphas[l][m][n] = ((ASN1OctetString)alphas2d.getObjectAt(n)).getOctets();
        }
      }
      

      ASN1Sequence betas3d = (ASN1Sequence)asnLayer.getObjectAt(1);
      betas[l] = new byte[betas3d.size()][][];
      for (int mb = 0; mb < betas3d.size(); mb++)
      {
        ASN1Sequence betas2d = (ASN1Sequence)betas3d.getObjectAt(mb);
        betas[l][mb] = new byte[betas2d.size()][];
        for (int nb = 0; nb < betas2d.size(); nb++)
        {
          betas[l][mb][nb] = ((ASN1OctetString)betas2d.getObjectAt(nb)).getOctets();
        }
      }
      

      ASN1Sequence gammas2d = (ASN1Sequence)asnLayer.getObjectAt(2);
      gammas[l] = new byte[gammas2d.size()][];
      for (int mg = 0; mg < gammas2d.size(); mg++)
      {
        gammas[l][mg] = ((ASN1OctetString)gammas2d.getObjectAt(mg)).getOctets();
      }
      

      etas[l] = ((ASN1OctetString)asnLayer.getObjectAt(3)).getOctets();
    }
    
    int numOfLayers = vi.length - 1;
    layers = new Layer[numOfLayers];
    for (int i = 0; i < numOfLayers; i++)
    {

      Layer l = new Layer(vi[i], vi[(i + 1)], RainbowUtil.convertArray(alphas[i]), RainbowUtil.convertArray(betas[i]), RainbowUtil.convertArray(gammas[i]), RainbowUtil.convertArray(etas[i]));
      layers[i] = l;
    }
  }
  


  public RainbowPrivateKey(short[][] invA1, short[] b1, short[][] invA2, short[] b2, int[] vi, Layer[] layers)
  {
    version = new ASN1Integer(1L);
    this.invA1 = RainbowUtil.convertArray(invA1);
    this.b1 = RainbowUtil.convertArray(b1);
    this.invA2 = RainbowUtil.convertArray(invA2);
    this.b2 = RainbowUtil.convertArray(b2);
    this.vi = RainbowUtil.convertIntArray(vi);
    this.layers = layers;
  }
  
  public static RainbowPrivateKey getInstance(Object o)
  {
    if ((o instanceof RainbowPrivateKey))
    {
      return (RainbowPrivateKey)o;
    }
    if (o != null)
    {
      return new RainbowPrivateKey(ASN1Sequence.getInstance(o));
    }
    
    return null;
  }
  
  public ASN1Integer getVersion()
  {
    return version;
  }
  





  public short[][] getInvA1()
  {
    return RainbowUtil.convertArray(invA1);
  }
  





  public short[] getB1()
  {
    return RainbowUtil.convertArray(b1);
  }
  





  public short[] getB2()
  {
    return RainbowUtil.convertArray(b2);
  }
  





  public short[][] getInvA2()
  {
    return RainbowUtil.convertArray(invA2);
  }
  





  public Layer[] getLayers()
  {
    return layers;
  }
  





  public int[] getVi()
  {
    return RainbowUtil.convertArraytoInt(vi);
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector v = new ASN1EncodableVector();
    

    if (version != null)
    {
      v.add(version);
    }
    else
    {
      v.add(oid);
    }
    

    ASN1EncodableVector asnA1 = new ASN1EncodableVector();
    for (int i = 0; i < invA1.length; i++)
    {
      asnA1.add(new DEROctetString(invA1[i]));
    }
    v.add(new DERSequence(asnA1));
    

    ASN1EncodableVector asnb1 = new ASN1EncodableVector();
    asnb1.add(new DEROctetString(b1));
    v.add(new DERSequence(asnb1));
    

    ASN1EncodableVector asnA2 = new ASN1EncodableVector();
    for (int i = 0; i < invA2.length; i++)
    {
      asnA2.add(new DEROctetString(invA2[i]));
    }
    v.add(new DERSequence(asnA2));
    

    ASN1EncodableVector asnb2 = new ASN1EncodableVector();
    asnb2.add(new DEROctetString(b2));
    v.add(new DERSequence(asnb2));
    

    ASN1EncodableVector asnvi = new ASN1EncodableVector();
    asnvi.add(new DEROctetString(vi));
    v.add(new DERSequence(asnvi));
    

    ASN1EncodableVector asnLayers = new ASN1EncodableVector();
    
    for (int l = 0; l < layers.length; l++)
    {
      ASN1EncodableVector aLayer = new ASN1EncodableVector();
      

      byte[][][] alphas = RainbowUtil.convertArray(layers[l].getCoeffAlpha());
      ASN1EncodableVector alphas3d = new ASN1EncodableVector();
      for (int i = 0; i < alphas.length; i++)
      {
        ASN1EncodableVector alphas2d = new ASN1EncodableVector();
        for (int j = 0; j < alphas[i].length; j++)
        {
          alphas2d.add(new DEROctetString(alphas[i][j]));
        }
        alphas3d.add(new DERSequence(alphas2d));
      }
      aLayer.add(new DERSequence(alphas3d));
      

      byte[][][] betas = RainbowUtil.convertArray(layers[l].getCoeffBeta());
      ASN1EncodableVector betas3d = new ASN1EncodableVector();
      for (int i = 0; i < betas.length; i++)
      {
        ASN1EncodableVector betas2d = new ASN1EncodableVector();
        for (int j = 0; j < betas[i].length; j++)
        {
          betas2d.add(new DEROctetString(betas[i][j]));
        }
        betas3d.add(new DERSequence(betas2d));
      }
      aLayer.add(new DERSequence(betas3d));
      

      byte[][] gammas = RainbowUtil.convertArray(layers[l].getCoeffGamma());
      ASN1EncodableVector asnG = new ASN1EncodableVector();
      for (int i = 0; i < gammas.length; i++)
      {
        asnG.add(new DEROctetString(gammas[i]));
      }
      aLayer.add(new DERSequence(asnG));
      

      aLayer.add(new DEROctetString(RainbowUtil.convertArray(layers[l].getCoeffEta())));
      

      asnLayers.add(new DERSequence(aLayer));
    }
    
    v.add(new DERSequence(asnLayers));
    
    return new DERSequence(v);
  }
}
