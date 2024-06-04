package org.spongycastle.util.encoders;












public class UrlBase64Encoder
  extends Base64Encoder
{
  public UrlBase64Encoder()
  {
    encodingTable[(encodingTable.length - 2)] = 45;
    encodingTable[(encodingTable.length - 1)] = 95;
    padding = 46;
    
    initialiseDecodingTable();
  }
}
