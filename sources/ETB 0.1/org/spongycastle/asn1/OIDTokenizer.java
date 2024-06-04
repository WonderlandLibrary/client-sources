package org.spongycastle.asn1;





public class OIDTokenizer
{
  private String oid;
  



  private int index;
  




  public OIDTokenizer(String oid)
  {
    this.oid = oid;
    index = 0;
  }
  





  public boolean hasMoreTokens()
  {
    return index != -1;
  }
  





  public String nextToken()
  {
    if (index == -1)
    {
      return null;
    }
    

    int end = oid.indexOf('.', index);
    
    if (end == -1)
    {
      String token = oid.substring(index);
      index = -1;
      return token;
    }
    
    String token = oid.substring(index, end);
    
    index = (end + 1);
    return token;
  }
}
