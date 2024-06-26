package org.spongycastle.math.ec.tools;

import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import org.spongycastle.asn1.x9.ECNamedCurveTable;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.ec.CustomNamedCurves;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.util.Integers;

public class TraceOptimizer
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  
  private static final SecureRandom R = new SecureRandom();
  
  public TraceOptimizer() {}
  
  public static void main(String[] args) { SortedSet names = new TreeSet(enumToList(ECNamedCurveTable.getNames()));
    names.addAll(enumToList(CustomNamedCurves.getNames()));
    
    Iterator it = names.iterator();
    while (it.hasNext())
    {
      String name = (String)it.next();
      X9ECParameters x9 = CustomNamedCurves.getByName(name);
      if (x9 == null)
      {
        x9 = ECNamedCurveTable.getByName(name);
      }
      if ((x9 != null) && (ECAlgorithms.isF2mCurve(x9.getCurve())))
      {
        System.out.print(name + ":");
        implPrintNonZeroTraceBits(x9);
      }
    }
  }
  
  public static void printNonZeroTraceBits(X9ECParameters x9)
  {
    if (!ECAlgorithms.isF2mCurve(x9.getCurve()))
    {
      throw new IllegalArgumentException("Trace only defined over characteristic-2 fields");
    }
    
    implPrintNonZeroTraceBits(x9);
  }
  
  public static void implPrintNonZeroTraceBits(X9ECParameters x9)
  {
    ECCurve c = x9.getCurve();
    int m = c.getFieldSize();
    
    ArrayList nonZeroTraceBits = new ArrayList();
    






    for (int i = 0; i < m; i++)
    {
      BigInteger zi = ONE.shiftLeft(i);
      ECFieldElement fe = c.fromBigInteger(zi);
      int tr = calculateTrace(fe);
      if (tr != 0)
      {
        nonZeroTraceBits.add(Integers.valueOf(i));
        System.out.print(" " + i);
      }
    }
    System.out.println();
    





    for (int i = 0; i < 1000; i++)
    {
      BigInteger x = new BigInteger(m, R);
      ECFieldElement fe = c.fromBigInteger(x);
      int check = calculateTrace(fe);
      
      int tr = 0;
      for (int j = 0; j < nonZeroTraceBits.size(); j++)
      {
        int bit = ((Integer)nonZeroTraceBits.get(j)).intValue();
        if (x.testBit(bit))
        {
          tr ^= 0x1;
        }
      }
      
      if (check != tr)
      {
        throw new IllegalStateException("Optimized-trace sanity check failed");
      }
    }
  }
  

  private static int calculateTrace(ECFieldElement fe)
  {
    int m = fe.getFieldSize();
    ECFieldElement tr = fe;
    for (int i = 1; i < m; i++)
    {
      fe = fe.square();
      tr = tr.add(fe);
    }
    BigInteger b = tr.toBigInteger();
    if (b.bitLength() > 1)
    {
      throw new IllegalStateException();
    }
    return b.intValue();
  }
  
  private static ArrayList enumToList(Enumeration en)
  {
    ArrayList rv = new ArrayList();
    while (en.hasMoreElements())
    {
      rv.add(en.nextElement());
    }
    return rv;
  }
}
