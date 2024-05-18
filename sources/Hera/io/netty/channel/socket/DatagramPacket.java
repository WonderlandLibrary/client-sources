/*    */ package io.netty.channel.socket;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.ByteBufHolder;
/*    */ import io.netty.channel.AddressedEnvelope;
/*    */ import io.netty.channel.DefaultAddressedEnvelope;
/*    */ import io.netty.util.ReferenceCounted;
/*    */ import java.net.InetSocketAddress;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DatagramPacket
/*    */   extends DefaultAddressedEnvelope<ByteBuf, InetSocketAddress>
/*    */   implements ByteBufHolder
/*    */ {
/*    */   public DatagramPacket(ByteBuf data, InetSocketAddress recipient) {
/* 34 */     super(data, recipient);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DatagramPacket(ByteBuf data, InetSocketAddress recipient, InetSocketAddress sender) {
/* 42 */     super(data, recipient, sender);
/*    */   }
/*    */ 
/*    */   
/*    */   public DatagramPacket copy() {
/* 47 */     return new DatagramPacket(((ByteBuf)content()).copy(), (InetSocketAddress)recipient(), (InetSocketAddress)sender());
/*    */   }
/*    */ 
/*    */   
/*    */   public DatagramPacket duplicate() {
/* 52 */     return new DatagramPacket(((ByteBuf)content()).duplicate(), (InetSocketAddress)recipient(), (InetSocketAddress)sender());
/*    */   }
/*    */ 
/*    */   
/*    */   public DatagramPacket retain() {
/* 57 */     super.retain();
/* 58 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public DatagramPacket retain(int increment) {
/* 63 */     super.retain(increment);
/* 64 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\socket\DatagramPacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */