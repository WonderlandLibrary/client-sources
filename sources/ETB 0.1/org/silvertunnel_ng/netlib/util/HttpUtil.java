package org.silvertunnel_ng.netlib.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.silvertunnel_ng.netlib.api.NetLayer;
import org.silvertunnel_ng.netlib.api.NetSocket;
import org.silvertunnel_ng.netlib.api.util.TcpipNetAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;













































public class HttpUtil
{
  public HttpUtil() {}
  
  private static final Logger LOG = LoggerFactory.getLogger(HttpUtil.class);
  public static final String HTTPTEST_SERVER_NAME = "httptest.silvertunnel-ng.org";
  public static final int HTTPTEST_SERVER_PORT = 80;
  
  public static final TcpipNetAddress HTTPTEST_SERVER_NETADDRESS = new TcpipNetAddress("httptest.silvertunnel-ng.org", 80);
  
  private static HttpUtil instance = new HttpUtil();
  
  public static HttpUtil getInstance() {
    return instance; }
  

  private static final Random RANDOM = new Random();
  













  public boolean executeSmallTest(NetSocket lowerLayerNetSocket, String idPrefix, long timeoutInMs)
    throws IOException
  {
    int randomNo = RANDOM.nextInt(1000000000);
    String id = idPrefix + randomNo;
    

    byte[] httpResponse = get(lowerLayerNetSocket, HTTPTEST_SERVER_NETADDRESS, "/httptest/smalltest.php?id=" + id, timeoutInMs);
    



    LOG.info("http response body: " + ByteArrayUtil.showAsString(httpResponse));
    byte[] expectedResponse = ("<response><id>" + id + "</id></response>\n").getBytes("UTF-8");
    boolean testOK = Arrays.equals(expectedResponse, httpResponse);
    if (testOK) {
      LOG.info("http response body = expected response body");
    } else {
      LOG.info("expected http response body is different: " + 
        ByteArrayUtil.showAsString(expectedResponse));
    }
    
    lowerLayerNetSocket.close();
    
    return testOK;
  }
  












  public byte[] get(NetLayer lowerNetLayer, TcpipNetAddress httpServerNetAddress, String pathOnHttpServer, long timeoutInMs)
    throws IOException
  {
    NetSocket s = lowerNetLayer.createNetSocket(null, null, httpServerNetAddress);
    
    return get(s, httpServerNetAddress, pathOnHttpServer, timeoutInMs);
  }
  












  public InputStream getReponseBodyInputStream(NetSocket lowerLayerNetSocket, TcpipNetAddress httpServerNetAddress, String pathOnHttpServer, long timeoutInMs)
    throws IOException
  {
    byte[] responseBody = get(lowerLayerNetSocket, httpServerNetAddress, pathOnHttpServer, timeoutInMs);
    
    return new ByteArrayInputStream(responseBody);
  }
  











  public static byte[] get(NetSocket lowerLayerNetSocket, TcpipNetAddress httpServerNetAddress, String pathOnHttpServer, long timeoutInMs)
    throws IOException
  {
    try
    {
      String request = "GET " + pathOnHttpServer + " HTTP/1.1\n" + "Host: " + getCleanHostname(httpServerNetAddress.getHostnameOrIpaddress()) + "\n" + "Connection: close\n" + "\n";
      


      byte[] requestBytes = request.getBytes("UTF-8");
      

      return request(lowerLayerNetSocket, httpServerNetAddress, pathOnHttpServer, requestBytes, timeoutInMs);
    } catch (UnsupportedEncodingException e) {
      LOG.error("this exception may never occur", e);
      throw new IOException(e.toString());
    }
  }
  







  private static String getCleanHostname(String hostname)
  {
    if (hostname.endsWith(".exit")) {
      String tmp = hostname.substring(0, hostname.length() - 5);
      tmp = tmp.substring(0, tmp.lastIndexOf('.'));
      return tmp;
    }
    return hostname;
  }
  












  public byte[] post(NetSocket lowerLayerNetSocket, TcpipNetAddress httpServerNetAddress, String pathOnHttpServer, byte[] dataToPost, long timeoutInMs)
    throws IOException
  {
    try
    {
      String request = "POST " + pathOnHttpServer + " HTTP/1.1\r\n" + "Host: " + httpServerNetAddress.getHostnameOrIpaddress() + "\r\n" + "Content-Type: text/plain; charset=utf-8\r\n" + "Content-Length: " + dataToPost.length + "\r\n" + "Connection: close\r\n" + "\r\n";
      



      byte[] requestBytes1 = request.getBytes("UTF-8");
      byte[] requestBytes = ByteArrayUtil.concatByteArrays(new byte[][] { requestBytes1, dataToPost });
      

      LOG.info("httpServerNetAddress=" + httpServerNetAddress + " with request=" + new String(requestBytes, "UTF-8"));
      

      byte[] response = request(lowerLayerNetSocket, httpServerNetAddress, pathOnHttpServer, requestBytes, timeoutInMs);
      



      if (LOG.isDebugEnabled()) {
        try {
          LOG.debug("response=" + new String(response, "UTF-8"));
        } catch (Exception e) {
          LOG.debug("response={}", Arrays.toString(response));
        }
      }
      
      return response;
    } catch (UnsupportedEncodingException e) {
      LOG.error("this exception may never occur", e);
      throw new IOException(e.toString());
    }
  }
  












  private static byte[] request(NetSocket lowerLayerNetSocket, TcpipNetAddress httpServerNetAddress, String pathOnHttpServer, byte[] requestBytes, long timeoutInMs)
    throws IOException
  {
    long startTime = System.currentTimeMillis();
    

    NetSocket s = lowerLayerNetSocket;
    



    HttpUtilResponseReceiverThread receiverThread = new HttpUtilResponseReceiverThread(s.getInputStream());
    

    OutputStream os = s.getOutputStream();
    try {
      LOG.info("send HTTP request now: " + ByteArrayUtil.showAsString(requestBytes));
      os.write(requestBytes);
    } catch (UnsupportedEncodingException e) {
      LOG.error("this exception may never occur", e);
    }
    os.flush();
    
    receiverThread.start();
    
    long millis = Math.max(100L, timeoutInMs - (System.currentTimeMillis() - startTime));
    try {
      receiverThread.join(millis);
    }
    catch (InterruptedException e) {
      LOG.debug("got IterruptedException", e.getMessage());
    }
    

    byte[] response = receiverThread.readCurrentResultAndStopThread();
    s.close();
    if (LOG.isDebugEnabled()) {
      try {
        LOG.debug("response=" + new String(response, "UTF-8"));
      } catch (Exception e) {
        LOG.debug("response=" + response);
      }
    }
    

    int endOfHeaders = response.length;
    int startOfBody = response.length + 1;
    for (int i = 0; i < response.length; i++) {
      if ((i + 1 < response.length) && (response[i] == 10) && (response[(i + 1)] == 10))
      {
        endOfHeaders = i;
        startOfBody = i + 2;
        break; }
      if (i + 3 < response.length) {
        if ((response[i] == 10) && (response[(i + 1)] == 13) && (response[(i + 2)] == 10) && (response[(i + 3)] == 13))
        {
          endOfHeaders = i;
          startOfBody = i + 4;
          break;
        }
        if ((response[i] == 13) && (response[(i + 1)] == 10) && (response[(i + 2)] == 13) && (response[(i + 3)] == 10))
        {
          endOfHeaders = i;
          startOfBody = i + 4;
          break;
        }
      }
    }
    byte[] responseHeaders = new byte[endOfHeaders];
    if (endOfHeaders > 0) {
      System.arraycopy(response, 0, responseHeaders, 0, endOfHeaders);
    }
    int bodyLen = Math.max(0, response.length - startOfBody);
    byte[] responseBody = new byte[bodyLen];
    if (bodyLen > 0) {
      System.arraycopy(response, startOfBody, responseBody, 0, bodyLen);
    }
    

    String responseHeadersAsString = ByteArrayUtil.showAsString(responseHeaders);
    String CHUNKED_CONTENT_HEADER = "Transfer-Encoding: chunked";
    if (responseHeadersAsString.contains("Transfer-Encoding: chunked"))
    {
      responseBody = decodeChunkedHttpResponse(responseBody);
    }
    

    LOG.info("received HTTP response header: " + responseHeadersAsString);
    LOG.info("received HTTP response body of " + responseBody.length + " bytes");
    

    return responseBody;
  }
  





  protected static byte[] decodeChunkedHttpResponse(byte[] chunkedResponse)
  {
    List<Byte> result = new ArrayList(chunkedResponse.length);
    StringBuffer chunkLenStr = new StringBuffer();
    for (int i = 0; i < chunkedResponse.length;)
    {
      int offset = isNewLine(chunkedResponse, i);
      if (offset > 0)
      {

        i += offset;
        int HEX_RADIX = 16;
        int chunkLength = Integer.parseInt(chunkLenStr.toString(), 16);
        
        if (chunkLength == 0) {
          break;
        }
        for (; 
            (i < chunkedResponse.length) && (chunkLength > 0); chunkLength--) {
          result.add(Byte.valueOf(chunkedResponse[i]));i++;
        }
        
        chunkLenStr = new StringBuffer();
        i += isNewLine(chunkedResponse, i);
      }
      else
      {
        chunkLenStr.append((char)chunkedResponse[(i++)]);
      }
    }
    

    byte[] decodedChunkedHttpResponse = new byte[result.size()];
    for (int i = 0; i < decodedChunkedHttpResponse.length; i++) {
      decodedChunkedHttpResponse[i] = ((Byte)result.get(i)).byteValue();
    }
    return decodedChunkedHttpResponse;
  }
  







  private static int isNewLine(byte[] data, int index)
  {
    if ((index + 1 < data.length) && (((data[index] == 10) && (data[(index + 1)] == 13)) || ((data[index] == 13) && (data[(index + 1)] == 10))))
    {


      return 2; }
    if ((index < data.length) && (data[index] == 10))
    {
      return 1;
    }
    return 0;
  }
}
