/*      */ package io.netty.handler.codec.http.multipart;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufHolder;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.channel.ChannelHandlerContext;
/*      */ import io.netty.handler.codec.DecoderResult;
/*      */ import io.netty.handler.codec.http.DefaultFullHttpRequest;
/*      */ import io.netty.handler.codec.http.DefaultHttpContent;
/*      */ import io.netty.handler.codec.http.FullHttpMessage;
/*      */ import io.netty.handler.codec.http.FullHttpRequest;
/*      */ import io.netty.handler.codec.http.HttpConstants;
/*      */ import io.netty.handler.codec.http.HttpContent;
/*      */ import io.netty.handler.codec.http.HttpHeaders;
/*      */ import io.netty.handler.codec.http.HttpMessage;
/*      */ import io.netty.handler.codec.http.HttpMethod;
/*      */ import io.netty.handler.codec.http.HttpRequest;
/*      */ import io.netty.handler.codec.http.HttpVersion;
/*      */ import io.netty.handler.codec.http.LastHttpContent;
/*      */ import io.netty.handler.stream.ChunkedInput;
/*      */ import io.netty.util.ReferenceCounted;
/*      */ import io.netty.util.internal.ThreadLocalRandom;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.net.URLEncoder;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class HttpPostRequestEncoder
/*      */   implements ChunkedInput<HttpContent>
/*      */ {
/*      */   public enum EncoderMode
/*      */   {
/*   61 */     RFC1738,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   66 */     RFC3986;
/*      */   }
/*      */   
/*   69 */   private static final Map<Pattern, String> percentEncodings = new HashMap<Pattern, String>(); private final HttpDataFactory factory; private final HttpRequest request; private final Charset charset; private boolean isChunked; private final List<InterfaceHttpData> bodyListDatas; final List<InterfaceHttpData> multipartHttpDatas; private final boolean isMultipart; String multipartDataBoundary; String multipartMixedBoundary; private boolean headerFinalized; private final EncoderMode encoderMode; private boolean isLastChunk; private boolean isLastChunkSent; private FileUpload currentFileUpload; private boolean duringMixedMode; private long globalBodySize; private ListIterator<InterfaceHttpData> iterator; private ByteBuf currentBuffer; private InterfaceHttpData currentData; private boolean isKey;
/*      */   
/*      */   static {
/*   72 */     percentEncodings.put(Pattern.compile("\\*"), "%2A");
/*   73 */     percentEncodings.put(Pattern.compile("\\+"), "%20");
/*   74 */     percentEncodings.put(Pattern.compile("%7E"), "~");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpPostRequestEncoder(HttpRequest request, boolean multipart) throws ErrorDataEncoderException {
/*  139 */     this(new DefaultHttpDataFactory(16384L), request, multipart, HttpConstants.DEFAULT_CHARSET, EncoderMode.RFC1738);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpPostRequestEncoder(HttpDataFactory factory, HttpRequest request, boolean multipart) throws ErrorDataEncoderException {
/*  158 */     this(factory, request, multipart, HttpConstants.DEFAULT_CHARSET, EncoderMode.RFC1738);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpPostRequestEncoder(HttpDataFactory factory, HttpRequest request, boolean multipart, Charset charset, EncoderMode encoderMode) throws ErrorDataEncoderException {
/*  801 */     this.isKey = true; if (factory == null) throw new NullPointerException("factory");  if (request == null) throw new NullPointerException("request");  if (charset == null) throw new NullPointerException("charset");  if (request.getMethod() != HttpMethod.POST) throw new ErrorDataEncoderException("Cannot create a Encoder if not a POST");  this.request = request; this.charset = charset; this.factory = factory; this.bodyListDatas = new ArrayList<InterfaceHttpData>(); this.isLastChunk = false; this.isLastChunkSent = false; this.isMultipart = multipart; this.multipartHttpDatas = new ArrayList<InterfaceHttpData>(); this.encoderMode = encoderMode; if (this.isMultipart) initDataMultipart(); 
/*      */   } public void cleanFiles() { this.factory.cleanRequestHttpDatas(this.request); } public boolean isMultipart() { return this.isMultipart; }
/*      */   private void initDataMultipart() { this.multipartDataBoundary = getNewMultipartDelimiter(); }
/*      */   private void initMixedMultipart() { this.multipartMixedBoundary = getNewMultipartDelimiter(); }
/*      */   private static String getNewMultipartDelimiter() { return Long.toHexString(ThreadLocalRandom.current().nextLong()).toLowerCase(); }
/*      */   public List<InterfaceHttpData> getBodyListAttributes() { return this.bodyListDatas; }
/*      */   public void setBodyHttpDatas(List<InterfaceHttpData> datas) throws ErrorDataEncoderException { if (datas == null) throw new NullPointerException("datas");  this.globalBodySize = 0L; this.bodyListDatas.clear(); this.currentFileUpload = null; this.duringMixedMode = false; this.multipartHttpDatas.clear(); for (InterfaceHttpData data : datas) addBodyHttpData(data);  }
/*  808 */   private ByteBuf fillByteBuf() { int length = this.currentBuffer.readableBytes();
/*  809 */     if (length > 8096) {
/*  810 */       ByteBuf byteBuf = this.currentBuffer.slice(this.currentBuffer.readerIndex(), 8096);
/*  811 */       this.currentBuffer.skipBytes(8096);
/*  812 */       return byteBuf;
/*      */     } 
/*      */     
/*  815 */     ByteBuf slice = this.currentBuffer;
/*  816 */     this.currentBuffer = null;
/*  817 */     return slice; } public void addBodyAttribute(String name, String value) throws ErrorDataEncoderException { if (name == null)
/*      */       throw new NullPointerException("name");  String svalue = value; if (value == null)
/*      */       svalue = "";  Attribute data = this.factory.createAttribute(this.request, name, svalue); addBodyHttpData(data); }
/*      */   public void addBodyFileUpload(String name, File file, String contentType, boolean isText) throws ErrorDataEncoderException { if (name == null)
/*      */       throw new NullPointerException("name");  if (file == null)
/*      */       throw new NullPointerException("file");  String scontentType = contentType; String contentTransferEncoding = null; if (contentType == null)
/*      */       if (isText) { scontentType = "text/plain"; }
/*      */       else { scontentType = "application/octet-stream"; }
/*      */         if (!isText)
/*      */       contentTransferEncoding = HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value();  FileUpload fileUpload = this.factory.createFileUpload(this.request, name, file.getName(), scontentType, contentTransferEncoding, null, file.length()); try { fileUpload.setContent(file); }
/*      */     catch (IOException e) { throw new ErrorDataEncoderException(e); }
/*      */      addBodyHttpData(fileUpload); }
/*      */   public void addBodyFileUploads(String name, File[] file, String[] contentType, boolean[] isText) throws ErrorDataEncoderException { if (file.length != contentType.length && file.length != isText.length)
/*      */       throw new NullPointerException("Different array length");  for (int i = 0; i < file.length; i++)
/*      */       addBodyFileUpload(name, file[i], contentType[i], isText[i]);  }
/*  832 */   private HttpContent encodeNextChunkMultipart(int sizeleft) throws ErrorDataEncoderException { if (this.currentData == null) {
/*  833 */       return null;
/*      */     }
/*      */     
/*  836 */     if (this.currentData instanceof InternalAttribute) {
/*  837 */       byteBuf = ((InternalAttribute)this.currentData).toByteBuf();
/*  838 */       this.currentData = null;
/*      */     } else {
/*  840 */       if (this.currentData instanceof Attribute) {
/*      */         try {
/*  842 */           byteBuf = ((Attribute)this.currentData).getChunk(sizeleft);
/*  843 */         } catch (IOException e) {
/*  844 */           throw new ErrorDataEncoderException(e);
/*      */         } 
/*      */       } else {
/*      */         try {
/*  848 */           byteBuf = ((HttpData)this.currentData).getChunk(sizeleft);
/*  849 */         } catch (IOException e) {
/*  850 */           throw new ErrorDataEncoderException(e);
/*      */         } 
/*      */       } 
/*  853 */       if (byteBuf.capacity() == 0) {
/*      */         
/*  855 */         this.currentData = null;
/*  856 */         return null;
/*      */       } 
/*      */     } 
/*  859 */     if (this.currentBuffer == null) {
/*  860 */       this.currentBuffer = byteBuf;
/*      */     } else {
/*  862 */       this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, byteBuf });
/*      */     } 
/*  864 */     if (this.currentBuffer.readableBytes() < 8096) {
/*  865 */       this.currentData = null;
/*  866 */       return null;
/*      */     } 
/*  868 */     ByteBuf byteBuf = fillByteBuf();
/*  869 */     return (HttpContent)new DefaultHttpContent(byteBuf); }
/*      */   public void addBodyHttpData(InterfaceHttpData data) throws ErrorDataEncoderException { if (this.headerFinalized)
/*      */       throw new ErrorDataEncoderException("Cannot add value once finalized");  if (data == null)
/*      */       throw new NullPointerException("data");  this.bodyListDatas.add(data); if (!this.isMultipart) { if (data instanceof Attribute) { Attribute attribute = (Attribute)data; try { String key = encodeAttribute(attribute.getName(), this.charset); String value = encodeAttribute(attribute.getValue(), this.charset); Attribute newattribute = this.factory.createAttribute(this.request, key, value); this.multipartHttpDatas.add(newattribute); this.globalBodySize += (newattribute.getName().length() + 1) + newattribute.length() + 1L; } catch (IOException e) { throw new ErrorDataEncoderException(e); }  } else if (data instanceof FileUpload) { FileUpload fileUpload = (FileUpload)data; String key = encodeAttribute(fileUpload.getName(), this.charset); String value = encodeAttribute(fileUpload.getFilename(), this.charset); Attribute newattribute = this.factory.createAttribute(this.request, key, value); this.multipartHttpDatas.add(newattribute); this.globalBodySize += (newattribute.getName().length() + 1) + newattribute.length() + 1L; }  return; }  if (data instanceof Attribute) { if (this.duringMixedMode) { InternalAttribute internalAttribute = new InternalAttribute(this.charset); internalAttribute.addValue("\r\n--" + this.multipartMixedBoundary + "--"); this.multipartHttpDatas.add(internalAttribute); this.multipartMixedBoundary = null; this.currentFileUpload = null; this.duringMixedMode = false; }  InternalAttribute internal = new InternalAttribute(this.charset); if (!this.multipartHttpDatas.isEmpty())
/*      */         internal.addValue("\r\n");  internal.addValue("--" + this.multipartDataBoundary + "\r\n"); Attribute attribute = (Attribute)data; internal.addValue("Content-Disposition: form-data; name=\"" + attribute.getName() + "\"\r\n"); Charset localcharset = attribute.getCharset(); if (localcharset != null)
/*      */         internal.addValue("Content-Type: text/plain; charset=" + localcharset + "\r\n");  internal.addValue("\r\n"); this.multipartHttpDatas.add(internal); this.multipartHttpDatas.add(data); this.globalBodySize += attribute.length() + internal.size(); } else if (data instanceof FileUpload) { boolean localMixed; FileUpload fileUpload = (FileUpload)data; InternalAttribute internal = new InternalAttribute(this.charset); if (!this.multipartHttpDatas.isEmpty())
/*      */         internal.addValue("\r\n");  if (this.duringMixedMode) { if (this.currentFileUpload != null && this.currentFileUpload.getName().equals(fileUpload.getName())) { localMixed = true; } else { internal.addValue("--" + this.multipartMixedBoundary + "--"); this.multipartHttpDatas.add(internal); this.multipartMixedBoundary = null; internal = new InternalAttribute(this.charset); internal.addValue("\r\n"); localMixed = false; this.currentFileUpload = fileUpload; this.duringMixedMode = false; }  } else if (this.currentFileUpload != null && this.currentFileUpload.getName().equals(fileUpload.getName())) { initMixedMultipart(); InternalAttribute pastAttribute = (InternalAttribute)this.multipartHttpDatas.get(this.multipartHttpDatas.size() - 2); this.globalBodySize -= pastAttribute.size(); StringBuilder replacement = new StringBuilder(139 + this.multipartDataBoundary.length() + this.multipartMixedBoundary.length() * 2 + fileUpload.getFilename().length() + fileUpload.getName().length()); replacement.append("--"); replacement.append(this.multipartDataBoundary); replacement.append("\r\n"); replacement.append("Content-Disposition"); replacement.append(": "); replacement.append("form-data"); replacement.append("; "); replacement.append("name"); replacement.append("=\""); replacement.append(fileUpload.getName()); replacement.append("\"\r\n"); replacement.append("Content-Type"); replacement.append(": "); replacement.append("multipart/mixed"); replacement.append("; "); replacement.append("boundary"); replacement.append('='); replacement.append(this.multipartMixedBoundary); replacement.append("\r\n\r\n"); replacement.append("--"); replacement.append(this.multipartMixedBoundary); replacement.append("\r\n"); replacement.append("Content-Disposition"); replacement.append(": "); replacement.append("attachment"); replacement.append("; "); replacement.append("filename"); replacement.append("=\""); replacement.append(fileUpload.getFilename()); replacement.append("\"\r\n"); pastAttribute.setValue(replacement.toString(), 1); pastAttribute.setValue("", 2); this.globalBodySize += pastAttribute.size(); localMixed = true; this.duringMixedMode = true; }
/*      */       else { localMixed = false; this.currentFileUpload = fileUpload; this.duringMixedMode = false; }
/*      */        if (localMixed) { internal.addValue("--" + this.multipartMixedBoundary + "\r\n"); internal.addValue("Content-Disposition: attachment; filename=\"" + fileUpload.getFilename() + "\"\r\n"); }
/*      */       else { internal.addValue("--" + this.multipartDataBoundary + "\r\n"); internal.addValue("Content-Disposition: form-data; name=\"" + fileUpload.getName() + "\"; " + "filename" + "=\"" + fileUpload.getFilename() + "\"\r\n"); }
/*      */        internal.addValue("Content-Type: " + fileUpload.getContentType()); String contentTransferEncoding = fileUpload.getContentTransferEncoding(); if (contentTransferEncoding != null && contentTransferEncoding.equals(HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value())) { internal.addValue("\r\nContent-Transfer-Encoding: " + HttpPostBodyUtil.TransferEncodingMechanism.BINARY.value() + "\r\n\r\n"); }
/*      */       else if (fileUpload.getCharset() != null) { internal.addValue("; charset=" + fileUpload.getCharset() + "\r\n\r\n"); }
/*      */       else { internal.addValue("\r\n\r\n"); }
/*      */        this.multipartHttpDatas.add(internal); this.multipartHttpDatas.add(data); this.globalBodySize += fileUpload.length() + internal.size(); }
/*  883 */      } private HttpContent encodeNextChunkUrlEncoded(int sizeleft) throws ErrorDataEncoderException { if (this.currentData == null) {
/*  884 */       return null;
/*      */     }
/*  886 */     int size = sizeleft;
/*      */ 
/*      */ 
/*      */     
/*  890 */     if (this.isKey) {
/*  891 */       String key = this.currentData.getName();
/*  892 */       buffer = Unpooled.wrappedBuffer(key.getBytes());
/*  893 */       this.isKey = false;
/*  894 */       if (this.currentBuffer == null) {
/*  895 */         this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { buffer, Unpooled.wrappedBuffer("=".getBytes()) });
/*      */         
/*  897 */         size -= buffer.readableBytes() + 1;
/*      */       } else {
/*  899 */         this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, buffer, Unpooled.wrappedBuffer("=".getBytes()) });
/*      */         
/*  901 */         size -= buffer.readableBytes() + 1;
/*      */       } 
/*  903 */       if (this.currentBuffer.readableBytes() >= 8096) {
/*  904 */         buffer = fillByteBuf();
/*  905 */         return (HttpContent)new DefaultHttpContent(buffer);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  911 */       buffer = ((HttpData)this.currentData).getChunk(size);
/*  912 */     } catch (IOException e) {
/*  913 */       throw new ErrorDataEncoderException(e);
/*      */     } 
/*      */ 
/*      */     
/*  917 */     ByteBuf delimiter = null;
/*  918 */     if (buffer.readableBytes() < size) {
/*  919 */       this.isKey = true;
/*  920 */       delimiter = this.iterator.hasNext() ? Unpooled.wrappedBuffer("&".getBytes()) : null;
/*      */     } 
/*      */ 
/*      */     
/*  924 */     if (buffer.capacity() == 0) {
/*  925 */       this.currentData = null;
/*  926 */       if (this.currentBuffer == null) {
/*  927 */         this.currentBuffer = delimiter;
/*      */       }
/*  929 */       else if (delimiter != null) {
/*  930 */         this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, delimiter });
/*      */       } 
/*      */       
/*  933 */       if (this.currentBuffer.readableBytes() >= 8096) {
/*  934 */         buffer = fillByteBuf();
/*  935 */         return (HttpContent)new DefaultHttpContent(buffer);
/*      */       } 
/*  937 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  941 */     if (this.currentBuffer == null) {
/*  942 */       if (delimiter != null) {
/*  943 */         this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { buffer, delimiter });
/*      */       } else {
/*  945 */         this.currentBuffer = buffer;
/*      */       }
/*      */     
/*  948 */     } else if (delimiter != null) {
/*  949 */       this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, buffer, delimiter });
/*      */     } else {
/*  951 */       this.currentBuffer = Unpooled.wrappedBuffer(new ByteBuf[] { this.currentBuffer, buffer });
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  956 */     if (this.currentBuffer.readableBytes() < 8096) {
/*  957 */       this.currentData = null;
/*  958 */       this.isKey = true;
/*  959 */       return null;
/*      */     } 
/*      */     
/*  962 */     ByteBuf buffer = fillByteBuf();
/*  963 */     return (HttpContent)new DefaultHttpContent(buffer); }
/*      */   public HttpRequest finalizeRequest() throws ErrorDataEncoderException { if (!this.headerFinalized) { if (this.isMultipart) { InternalAttribute internal = new InternalAttribute(this.charset); if (this.duringMixedMode)
/*      */           internal.addValue("\r\n--" + this.multipartMixedBoundary + "--");  internal.addValue("\r\n--" + this.multipartDataBoundary + "--\r\n"); this.multipartHttpDatas.add(internal); this.multipartMixedBoundary = null; this.currentFileUpload = null; this.duringMixedMode = false; this.globalBodySize += internal.size(); }  this.headerFinalized = true; } else { throw new ErrorDataEncoderException("Header already encoded"); }  HttpHeaders headers = this.request.headers(); List<String> contentTypes = headers.getAll("Content-Type"); List<String> transferEncoding = headers.getAll("Transfer-Encoding"); if (contentTypes != null) { headers.remove("Content-Type"); for (String contentType : contentTypes) { String lowercased = contentType.toLowerCase(); if (lowercased.startsWith("multipart/form-data") || lowercased.startsWith("application/x-www-form-urlencoded"))
/*      */           continue;  headers.add("Content-Type", contentType); }  }  if (this.isMultipart) { String value = "multipart/form-data; boundary=" + this.multipartDataBoundary; headers.add("Content-Type", value); } else { headers.add("Content-Type", "application/x-www-form-urlencoded"); }
/*      */      long realSize = this.globalBodySize; if (this.isMultipart) { this.iterator = this.multipartHttpDatas.listIterator(); }
/*      */     else { realSize--; this.iterator = this.multipartHttpDatas.listIterator(); }
/*      */      headers.set("Content-Length", String.valueOf(realSize)); if (realSize > 8096L || this.isMultipart) { this.isChunked = true; if (transferEncoding != null) { headers.remove("Transfer-Encoding"); for (String v : transferEncoding) { if (v.equalsIgnoreCase("chunked"))
/*      */             continue;  headers.add("Transfer-Encoding", v); }
/*      */          }
/*      */        HttpHeaders.setTransferEncodingChunked((HttpMessage)this.request); return new WrappedHttpRequest(this.request); }
/*      */      HttpContent chunk = nextChunk(); if (this.request instanceof FullHttpRequest) { FullHttpRequest fullRequest = (FullHttpRequest)this.request; ByteBuf chunkContent = chunk.content(); if (fullRequest.content() != chunkContent) { fullRequest.content().clear().writeBytes(chunkContent); chunkContent.release(); }
/*      */        return (HttpRequest)fullRequest; }
/*      */      return new WrappedFullHttpRequest(this.request, chunk); }
/*      */   public boolean isChunked() { return this.isChunked; }
/*      */   private String encodeAttribute(String s, Charset charset) throws ErrorDataEncoderException { if (s == null)
/*      */       return "";  try { String encoded = URLEncoder.encode(s, charset.name()); if (this.encoderMode == EncoderMode.RFC3986)
/*      */         for (Map.Entry<Pattern, String> entry : percentEncodings.entrySet()) { String replacement = entry.getValue(); encoded = ((Pattern)entry.getKey()).matcher(encoded).replaceAll(replacement); }
/*      */           return encoded; }
/*      */     catch (UnsupportedEncodingException e) { throw new ErrorDataEncoderException(charset.name(), e); }
/*  982 */      } public void close() throws Exception {} public HttpContent readChunk(ChannelHandlerContext ctx) throws Exception { if (this.isLastChunkSent) {
/*  983 */       return null;
/*      */     }
/*  985 */     return nextChunk(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private HttpContent nextChunk() throws ErrorDataEncoderException {
/*  998 */     if (this.isLastChunk) {
/*  999 */       this.isLastChunkSent = true;
/* 1000 */       return (HttpContent)LastHttpContent.EMPTY_LAST_CONTENT;
/*      */     } 
/*      */     
/* 1003 */     int size = 8096;
/*      */     
/* 1005 */     if (this.currentBuffer != null) {
/* 1006 */       size -= this.currentBuffer.readableBytes();
/*      */     }
/* 1008 */     if (size <= 0) {
/*      */       
/* 1010 */       ByteBuf byteBuf = fillByteBuf();
/* 1011 */       return (HttpContent)new DefaultHttpContent(byteBuf);
/*      */     } 
/*      */     
/* 1014 */     if (this.currentData != null) {
/*      */       
/* 1016 */       if (this.isMultipart) {
/* 1017 */         HttpContent chunk = encodeNextChunkMultipart(size);
/* 1018 */         if (chunk != null) {
/* 1019 */           return chunk;
/*      */         }
/*      */       } else {
/* 1022 */         HttpContent chunk = encodeNextChunkUrlEncoded(size);
/* 1023 */         if (chunk != null)
/*      */         {
/* 1025 */           return chunk;
/*      */         }
/*      */       } 
/* 1028 */       size = 8096 - this.currentBuffer.readableBytes();
/*      */     } 
/* 1030 */     if (!this.iterator.hasNext()) {
/* 1031 */       this.isLastChunk = true;
/*      */       
/* 1033 */       ByteBuf byteBuf = this.currentBuffer;
/* 1034 */       this.currentBuffer = null;
/* 1035 */       return (HttpContent)new DefaultHttpContent(byteBuf);
/*      */     } 
/* 1037 */     while (size > 0 && this.iterator.hasNext()) {
/* 1038 */       HttpContent chunk; this.currentData = this.iterator.next();
/*      */       
/* 1040 */       if (this.isMultipart) {
/* 1041 */         chunk = encodeNextChunkMultipart(size);
/*      */       } else {
/* 1043 */         chunk = encodeNextChunkUrlEncoded(size);
/*      */       } 
/* 1045 */       if (chunk == null) {
/*      */         
/* 1047 */         size = 8096 - this.currentBuffer.readableBytes();
/*      */         
/*      */         continue;
/*      */       } 
/* 1051 */       return chunk;
/*      */     } 
/*      */     
/* 1054 */     this.isLastChunk = true;
/* 1055 */     if (this.currentBuffer == null) {
/* 1056 */       this.isLastChunkSent = true;
/*      */       
/* 1058 */       return (HttpContent)LastHttpContent.EMPTY_LAST_CONTENT;
/*      */     } 
/*      */     
/* 1061 */     ByteBuf buffer = this.currentBuffer;
/* 1062 */     this.currentBuffer = null;
/* 1063 */     return (HttpContent)new DefaultHttpContent(buffer);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEndOfInput() throws Exception {
/* 1068 */     return this.isLastChunkSent;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class ErrorDataEncoderException
/*      */     extends Exception
/*      */   {
/*      */     private static final long serialVersionUID = 5020247425493164465L;
/*      */ 
/*      */     
/*      */     public ErrorDataEncoderException() {}
/*      */     
/*      */     public ErrorDataEncoderException(String msg) {
/* 1081 */       super(msg);
/*      */     }
/*      */     
/*      */     public ErrorDataEncoderException(Throwable cause) {
/* 1085 */       super(cause);
/*      */     }
/*      */     
/*      */     public ErrorDataEncoderException(String msg, Throwable cause) {
/* 1089 */       super(msg, cause);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class WrappedHttpRequest implements HttpRequest { private final HttpRequest request;
/*      */     
/*      */     WrappedHttpRequest(HttpRequest request) {
/* 1096 */       this.request = request;
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpRequest setProtocolVersion(HttpVersion version) {
/* 1101 */       this.request.setProtocolVersion(version);
/* 1102 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpRequest setMethod(HttpMethod method) {
/* 1107 */       this.request.setMethod(method);
/* 1108 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpRequest setUri(String uri) {
/* 1113 */       this.request.setUri(uri);
/* 1114 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpMethod getMethod() {
/* 1119 */       return this.request.getMethod();
/*      */     }
/*      */ 
/*      */     
/*      */     public String getUri() {
/* 1124 */       return this.request.getUri();
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpVersion getProtocolVersion() {
/* 1129 */       return this.request.getProtocolVersion();
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpHeaders headers() {
/* 1134 */       return this.request.headers();
/*      */     }
/*      */ 
/*      */     
/*      */     public DecoderResult getDecoderResult() {
/* 1139 */       return this.request.getDecoderResult();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setDecoderResult(DecoderResult result) {
/* 1144 */       this.request.setDecoderResult(result);
/*      */     } }
/*      */   
/*      */   private static final class WrappedFullHttpRequest extends WrappedHttpRequest implements FullHttpRequest {
/*      */     private final HttpContent content;
/*      */     
/*      */     private WrappedFullHttpRequest(HttpRequest request, HttpContent content) {
/* 1151 */       super(request);
/* 1152 */       this.content = content;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest setProtocolVersion(HttpVersion version) {
/* 1157 */       super.setProtocolVersion(version);
/* 1158 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest setMethod(HttpMethod method) {
/* 1163 */       super.setMethod(method);
/* 1164 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest setUri(String uri) {
/* 1169 */       super.setUri(uri);
/* 1170 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest copy() {
/* 1175 */       DefaultFullHttpRequest copy = new DefaultFullHttpRequest(getProtocolVersion(), getMethod(), getUri(), content().copy());
/*      */       
/* 1177 */       copy.headers().set(headers());
/* 1178 */       copy.trailingHeaders().set(trailingHeaders());
/* 1179 */       return (FullHttpRequest)copy;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest duplicate() {
/* 1184 */       DefaultFullHttpRequest duplicate = new DefaultFullHttpRequest(getProtocolVersion(), getMethod(), getUri(), content().duplicate());
/*      */       
/* 1186 */       duplicate.headers().set(headers());
/* 1187 */       duplicate.trailingHeaders().set(trailingHeaders());
/* 1188 */       return (FullHttpRequest)duplicate;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest retain(int increment) {
/* 1193 */       this.content.retain(increment);
/* 1194 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public FullHttpRequest retain() {
/* 1199 */       this.content.retain();
/* 1200 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public ByteBuf content() {
/* 1205 */       return this.content.content();
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpHeaders trailingHeaders() {
/* 1210 */       if (this.content instanceof LastHttpContent) {
/* 1211 */         return ((LastHttpContent)this.content).trailingHeaders();
/*      */       }
/* 1213 */       return HttpHeaders.EMPTY_HEADERS;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int refCnt() {
/* 1219 */       return this.content.refCnt();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean release() {
/* 1224 */       return this.content.release();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean release(int decrement) {
/* 1229 */       return this.content.release(decrement);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\multipart\HttpPostRequestEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */