/*    */ package net.minecraft.item;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemMapBase
/*    */   extends Item
/*    */ {
/*    */   public boolean isMap() {
/* 14 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public Packet createMapDataPacket(ItemStack stack, World worldIn, EntityPlayer player) {
/* 19 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\item\ItemMapBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */