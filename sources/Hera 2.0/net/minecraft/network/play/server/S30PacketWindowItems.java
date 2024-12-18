/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class S30PacketWindowItems
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int windowId;
/*    */   private ItemStack[] itemStacks;
/*    */   
/*    */   public S30PacketWindowItems() {}
/*    */   
/*    */   public S30PacketWindowItems(int windowIdIn, List<ItemStack> p_i45186_2_) {
/* 21 */     this.windowId = windowIdIn;
/* 22 */     this.itemStacks = new ItemStack[p_i45186_2_.size()];
/*    */     
/* 24 */     for (int i = 0; i < this.itemStacks.length; i++) {
/*    */       
/* 26 */       ItemStack itemstack = p_i45186_2_.get(i);
/* 27 */       this.itemStacks[i] = (itemstack == null) ? null : itemstack.copy();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 36 */     this.windowId = buf.readUnsignedByte();
/* 37 */     int i = buf.readShort();
/* 38 */     this.itemStacks = new ItemStack[i];
/*    */     
/* 40 */     for (int j = 0; j < i; j++)
/*    */     {
/* 42 */       this.itemStacks[j] = buf.readItemStackFromBuffer();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 51 */     buf.writeByte(this.windowId);
/* 52 */     buf.writeShort(this.itemStacks.length); byte b; int i;
/*    */     ItemStack[] arrayOfItemStack;
/* 54 */     for (i = (arrayOfItemStack = this.itemStacks).length, b = 0; b < i; ) { ItemStack itemstack = arrayOfItemStack[b];
/*    */       
/* 56 */       buf.writeItemStackToBuffer(itemstack);
/*    */       b++; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 65 */     handler.handleWindowItems(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_148911_c() {
/* 70 */     return this.windowId;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack[] getItemStacks() {
/* 75 */     return this.itemStacks;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\network\play\server\S30PacketWindowItems.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */