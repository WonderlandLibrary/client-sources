/*    */ package net.minecraft.client.gui.inventory;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.inventory.Container;
/*    */ import net.minecraft.inventory.ICrafting;
/*    */ import net.minecraft.inventory.IInventory;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class CreativeCrafting
/*    */   implements ICrafting
/*    */ {
/*    */   private final Minecraft mc;
/*    */   
/*    */   public CreativeCrafting(Minecraft mc) {
/* 16 */     this.mc = mc;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateCraftingInventory(Container containerToSend, List<ItemStack> itemsList) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
/* 32 */     this.mc.playerController.sendSlotPacket(stack, slotInd);
/*    */   }
/*    */   
/*    */   public void sendProgressBarUpdate(Container containerIn, int varToUpdate, int newValue) {}
/*    */   
/*    */   public void func_175173_a(Container p_175173_1_, IInventory p_175173_2_) {}
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\inventory\CreativeCrafting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */