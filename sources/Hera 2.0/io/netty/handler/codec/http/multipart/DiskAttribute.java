/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.handler.codec.http.HttpConstants;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DiskAttribute
/*     */   extends AbstractDiskHttpData
/*     */   implements Attribute
/*     */ {
/*     */   public static String baseDirectory;
/*     */   public static boolean deleteOnExitTemporaryFile = true;
/*     */   public static final String prefix = "Attr_";
/*     */   public static final String postfix = ".att";
/*     */   
/*     */   public DiskAttribute(String name) {
/*  42 */     super(name, HttpConstants.DEFAULT_CHARSET, 0L);
/*     */   }
/*     */   
/*     */   public DiskAttribute(String name, String value) throws IOException {
/*  46 */     super(name, HttpConstants.DEFAULT_CHARSET, 0L);
/*  47 */     setValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType() {
/*  52 */     return InterfaceHttpData.HttpDataType.Attribute;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue() throws IOException {
/*  57 */     byte[] bytes = get();
/*  58 */     return new String(bytes, this.charset.name());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(String value) throws IOException {
/*  63 */     if (value == null) {
/*  64 */       throw new NullPointerException("value");
/*     */     }
/*  66 */     byte[] bytes = value.getBytes(this.charset.name());
/*  67 */     ByteBuf buffer = Unpooled.wrappedBuffer(bytes);
/*  68 */     if (this.definedSize > 0L) {
/*  69 */       this.definedSize = buffer.readableBytes();
/*     */     }
/*  71 */     setContent(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addContent(ByteBuf buffer, boolean last) throws IOException {
/*  76 */     int localsize = buffer.readableBytes();
/*  77 */     if (this.definedSize > 0L && this.definedSize < this.size + localsize) {
/*  78 */       this.definedSize = this.size + localsize;
/*     */     }
/*  80 */     super.addContent(buffer, last);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*  84 */     return getName().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  89 */     if (!(o instanceof Attribute)) {
/*  90 */       return false;
/*     */     }
/*  92 */     Attribute attribute = (Attribute)o;
/*  93 */     return getName().equalsIgnoreCase(attribute.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(InterfaceHttpData o) {
/*  98 */     if (!(o instanceof Attribute)) {
/*  99 */       throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + o.getHttpDataType());
/*     */     }
/*     */     
/* 102 */     return compareTo((Attribute)o);
/*     */   }
/*     */   
/*     */   public int compareTo(Attribute o) {
/* 106 */     return getName().compareToIgnoreCase(o.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*     */     try {
/* 112 */       return getName() + '=' + getValue();
/* 113 */     } catch (IOException e) {
/* 114 */       return getName() + "=IoException";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean deleteOnExit() {
/* 120 */     return deleteOnExitTemporaryFile;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getBaseDirectory() {
/* 125 */     return baseDirectory;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDiskFilename() {
/* 130 */     return getName() + ".att";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getPostfix() {
/* 135 */     return ".att";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getPrefix() {
/* 140 */     return "Attr_";
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute copy() {
/* 145 */     DiskAttribute attr = new DiskAttribute(getName());
/* 146 */     attr.setCharset(getCharset());
/* 147 */     ByteBuf content = content();
/* 148 */     if (content != null) {
/*     */       try {
/* 150 */         attr.setContent(content.copy());
/* 151 */       } catch (IOException e) {
/* 152 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/* 155 */     return attr;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute duplicate() {
/* 160 */     DiskAttribute attr = new DiskAttribute(getName());
/* 161 */     attr.setCharset(getCharset());
/* 162 */     ByteBuf content = content();
/* 163 */     if (content != null) {
/*     */       try {
/* 165 */         attr.setContent(content.duplicate());
/* 166 */       } catch (IOException e) {
/* 167 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/* 170 */     return attr;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute retain(int increment) {
/* 175 */     super.retain(increment);
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute retain() {
/* 181 */     super.retain();
/* 182 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\multipart\DiskAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */