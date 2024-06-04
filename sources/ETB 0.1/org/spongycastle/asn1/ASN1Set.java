package org.spongycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import org.spongycastle.util.Arrays.Iterator;
import org.spongycastle.util.Iterable;
























































































public abstract class ASN1Set
  extends ASN1Primitive
  implements Iterable<ASN1Encodable>
{
  private Vector set = new Vector();
  private boolean isSorted = false;
  








  public static ASN1Set getInstance(Object obj)
  {
    if ((obj == null) || ((obj instanceof ASN1Set)))
    {
      return (ASN1Set)obj;
    }
    if ((obj instanceof ASN1SetParser))
    {
      return getInstance(((ASN1SetParser)obj).toASN1Primitive());
    }
    if ((obj instanceof byte[]))
    {
      try
      {
        return getInstance(ASN1Primitive.fromByteArray((byte[])obj));
      }
      catch (IOException e)
      {
        throw new IllegalArgumentException("failed to construct set from byte[]: " + e.getMessage());
      }
    }
    if ((obj instanceof ASN1Encodable))
    {
      ASN1Primitive primitive = ((ASN1Encodable)obj).toASN1Primitive();
      
      if ((primitive instanceof ASN1Set))
      {
        return (ASN1Set)primitive;
      }
    }
    
    throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
  }
  



















  public static ASN1Set getInstance(ASN1TaggedObject obj, boolean explicit)
  {
    if (explicit)
    {
      if (!obj.isExplicit())
      {
        throw new IllegalArgumentException("object implicit - explicit expected.");
      }
      
      return (ASN1Set)obj.getObject();
    }
    






    if (obj.isExplicit())
    {
      if ((obj instanceof BERTaggedObject))
      {
        return new BERSet(obj.getObject());
      }
      

      return new DLSet(obj.getObject());
    }
    


    if ((obj.getObject() instanceof ASN1Set))
    {
      return (ASN1Set)obj.getObject();
    }
    




    if ((obj.getObject() instanceof ASN1Sequence))
    {
      ASN1Sequence s = (ASN1Sequence)obj.getObject();
      
      if ((obj instanceof BERTaggedObject))
      {
        return new BERSet(s.toArray());
      }
      

      return new DLSet(s.toArray());
    }
    



    throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
  }
  




  protected ASN1Set() {}
  



  protected ASN1Set(ASN1Encodable obj)
  {
    set.addElement(obj);
  }
  







  protected ASN1Set(ASN1EncodableVector v, boolean doSort)
  {
    for (int i = 0; i != v.size(); i++)
    {
      set.addElement(v.get(i));
    }
    
    if (doSort)
    {
      sort();
    }
  }
  







  protected ASN1Set(ASN1Encodable[] array, boolean doSort)
  {
    for (int i = 0; i != array.length; i++)
    {
      set.addElement(array[i]);
    }
    
    if (doSort)
    {
      sort();
    }
  }
  
  public Enumeration getObjects()
  {
    return set.elements();
  }
  







  public ASN1Encodable getObjectAt(int index)
  {
    return (ASN1Encodable)set.elementAt(index);
  }
  





  public int size()
  {
    return set.size();
  }
  
  public ASN1Encodable[] toArray()
  {
    ASN1Encodable[] values = new ASN1Encodable[size()];
    
    for (int i = 0; i != size(); i++)
    {
      values[i] = getObjectAt(i);
    }
    
    return values;
  }
  
  public ASN1SetParser parser()
  {
    final ASN1Set outer = this;
    
    new ASN1SetParser()
    {
      private final int max = size();
      private int index;
      
      public ASN1Encodable readObject()
        throws IOException
      {
        if (index == max)
        {
          return null;
        }
        
        ASN1Encodable obj = getObjectAt(index++);
        if ((obj instanceof ASN1Sequence))
        {
          return ((ASN1Sequence)obj).parser();
        }
        if ((obj instanceof ASN1Set))
        {
          return ((ASN1Set)obj).parser();
        }
        
        return obj;
      }
      
      public ASN1Primitive getLoadedObject()
      {
        return outer;
      }
      
      public ASN1Primitive toASN1Primitive()
      {
        return outer;
      }
    };
  }
  
  public int hashCode()
  {
    Enumeration e = getObjects();
    int hashCode = size();
    
    while (e.hasMoreElements())
    {
      Object o = getNext(e);
      hashCode *= 17;
      
      hashCode ^= o.hashCode();
    }
    
    return hashCode;
  }
  




  ASN1Primitive toDERObject()
  {
    if (isSorted)
    {
      ASN1Set derSet = new DERSet();
      
      set = set;
      
      return derSet;
    }
    

    Vector v = new Vector();
    
    for (int i = 0; i != set.size(); i++)
    {
      v.addElement(set.elementAt(i));
    }
    
    ASN1Set derSet = new DERSet();
    
    set = v;
    
    derSet.sort();
    
    return derSet;
  }
  





  ASN1Primitive toDLObject()
  {
    ASN1Set derSet = new DLSet();
    
    set = set;
    
    return derSet;
  }
  

  boolean asn1Equals(ASN1Primitive o)
  {
    if (!(o instanceof ASN1Set))
    {
      return false;
    }
    
    ASN1Set other = (ASN1Set)o;
    
    if (size() != other.size())
    {
      return false;
    }
    
    Enumeration s1 = getObjects();
    Enumeration s2 = other.getObjects();
    
    while (s1.hasMoreElements())
    {
      ASN1Encodable obj1 = getNext(s1);
      ASN1Encodable obj2 = getNext(s2);
      
      ASN1Primitive o1 = obj1.toASN1Primitive();
      ASN1Primitive o2 = obj2.toASN1Primitive();
      
      if ((o1 != o2) && (!o1.equals(o2)))
      {



        return false;
      }
    }
    return true;
  }
  
  private ASN1Encodable getNext(Enumeration e)
  {
    ASN1Encodable encObj = (ASN1Encodable)e.nextElement();
    

    if (encObj == null)
    {
      return DERNull.INSTANCE;
    }
    
    return encObj;
  }
  





  private boolean lessThanOrEqual(byte[] a, byte[] b)
  {
    int len = Math.min(a.length, b.length);
    for (int i = 0; i != len; i++)
    {
      if (a[i] != b[i])
      {
        return (a[i] & 0xFF) < (b[i] & 0xFF);
      }
    }
    return len == a.length;
  }
  

  private byte[] getDEREncoded(ASN1Encodable obj)
  {
    try
    {
      return obj.toASN1Primitive().getEncoded("DER");
    }
    catch (IOException e)
    {
      throw new IllegalArgumentException("cannot encode object added to SET");
    }
  }
  
  protected void sort()
  {
    if (!isSorted)
    {
      isSorted = true;
      if (set.size() > 1)
      {
        boolean swapped = true;
        int lastSwap = set.size() - 1;
        
        while (swapped)
        {
          int index = 0;
          int swapIndex = 0;
          byte[] a = getDEREncoded((ASN1Encodable)set.elementAt(0));
          
          swapped = false;
          
          while (index != lastSwap)
          {
            byte[] b = getDEREncoded((ASN1Encodable)set.elementAt(index + 1));
            
            if (lessThanOrEqual(a, b))
            {
              a = b;
            }
            else
            {
              Object o = set.elementAt(index);
              
              set.setElementAt(set.elementAt(index + 1), index);
              set.setElementAt(o, index + 1);
              
              swapped = true;
              swapIndex = index;
            }
            
            index++;
          }
          
          lastSwap = swapIndex;
        }
      }
    }
  }
  
  boolean isConstructed()
  {
    return true;
  }
  
  abstract void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException;
  
  public String toString()
  {
    return set.toString();
  }
  
  public Iterator<ASN1Encodable> iterator()
  {
    return new Arrays.Iterator(toArray());
  }
}
