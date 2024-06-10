/*  1:   */ package net.minecraft.client.gui.inventory;
/*  2:   */ 
/*  3:   */ import java.util.List;
/*  4:   */ import net.minecraft.client.Minecraft;
/*  5:   */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*  6:   */ import net.minecraft.inventory.Container;
/*  7:   */ import net.minecraft.inventory.ICrafting;
/*  8:   */ import net.minecraft.item.ItemStack;
/*  9:   */ 
/* 10:   */ public class CreativeCrafting
/* 11:   */   implements ICrafting
/* 12:   */ {
/* 13:   */   private final Minecraft field_146109_a;
/* 14:   */   private static final String __OBFID = "CL_00000751";
/* 15:   */   
/* 16:   */   public CreativeCrafting(Minecraft par1Minecraft)
/* 17:   */   {
/* 18:16 */     this.field_146109_a = par1Minecraft;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void sendContainerAndContentsToPlayer(Container par1Container, List par2List) {}
/* 22:   */   
/* 23:   */   public void sendSlotContents(Container par1Container, int par2, ItemStack par3ItemStack)
/* 24:   */   {
/* 25:27 */     this.field_146109_a.playerController.sendSlotPacket(par3ItemStack, par2);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void sendProgressBarUpdate(Container par1Container, int par2, int par3) {}
/* 29:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.inventory.CreativeCrafting
 * JD-Core Version:    0.7.0.1
 */