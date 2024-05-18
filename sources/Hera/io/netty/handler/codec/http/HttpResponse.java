package io.netty.handler.codec.http;

public interface HttpResponse extends HttpMessage {
  HttpResponseStatus getStatus();
  
  HttpResponse setStatus(HttpResponseStatus paramHttpResponseStatus);
  
  HttpResponse setProtocolVersion(HttpVersion paramHttpVersion);
}


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\HttpResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */