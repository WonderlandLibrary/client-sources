/*    */ package io.netty.channel.sctp;
/*    */ 
/*    */ import com.sun.nio.sctp.SctpStandardSocketOptions;
/*    */ import io.netty.channel.ChannelOption;
/*    */ import java.net.SocketAddress;
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
/*    */ public class SctpChannelOption<T>
/*    */   extends ChannelOption<T>
/*    */ {
/* 28 */   public static final SctpChannelOption<Boolean> SCTP_DISABLE_FRAGMENTS = new SctpChannelOption("SCTP_DISABLE_FRAGMENTS");
/*    */   
/* 30 */   public static final SctpChannelOption<Boolean> SCTP_EXPLICIT_COMPLETE = new SctpChannelOption("SCTP_EXPLICIT_COMPLETE");
/*    */   
/* 32 */   public static final SctpChannelOption<Integer> SCTP_FRAGMENT_INTERLEAVE = new SctpChannelOption("SCTP_FRAGMENT_INTERLEAVE");
/*    */   
/* 34 */   public static final SctpChannelOption<SctpStandardSocketOptions.InitMaxStreams> SCTP_INIT_MAXSTREAMS = new SctpChannelOption("SCTP_INIT_MAXSTREAMS");
/*    */ 
/*    */   
/* 37 */   public static final SctpChannelOption<Boolean> SCTP_NODELAY = new SctpChannelOption("SCTP_NODELAY");
/*    */   
/* 39 */   public static final SctpChannelOption<SocketAddress> SCTP_PRIMARY_ADDR = new SctpChannelOption("SCTP_PRIMARY_ADDR");
/*    */   
/* 41 */   public static final SctpChannelOption<SocketAddress> SCTP_SET_PEER_PRIMARY_ADDR = new SctpChannelOption("SCTP_SET_PEER_PRIMARY_ADDR");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   protected SctpChannelOption(String name) {
/* 49 */     super(name);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\sctp\SctpChannelOption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */