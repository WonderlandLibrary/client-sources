/*  1:   */ package net.minecraft.item;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.List;
/*  5:   */ import net.minecraft.entity.item.EntityFireworkRocket;
/*  6:   */ import net.minecraft.entity.player.EntityPlayer;
/*  7:   */ import net.minecraft.entity.player.PlayerCapabilities;
/*  8:   */ import net.minecraft.nbt.NBTTagCompound;
/*  9:   */ import net.minecraft.nbt.NBTTagList;
/* 10:   */ import net.minecraft.util.StatCollector;
/* 11:   */ import net.minecraft.world.World;
/* 12:   */ 
/* 13:   */ public class ItemFirework
/* 14:   */   extends Item
/* 15:   */ {
/* 16:   */   private static final String __OBFID = "CL_00000031";
/* 17:   */   
/* 18:   */   public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
/* 19:   */   {
/* 20:22 */     if (!par3World.isClient)
/* 21:   */     {
/* 22:24 */       EntityFireworkRocket var11 = new EntityFireworkRocket(par3World, par4 + par8, par5 + par9, par6 + par10, par1ItemStack);
/* 23:25 */       par3World.spawnEntityInWorld(var11);
/* 24:27 */       if (!par2EntityPlayer.capabilities.isCreativeMode) {
/* 25:29 */         par1ItemStack.stackSize -= 1;
/* 26:   */       }
/* 27:32 */       return true;
/* 28:   */     }
/* 29:36 */     return false;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
/* 33:   */   {
/* 34:45 */     if (par1ItemStack.hasTagCompound())
/* 35:   */     {
/* 36:47 */       NBTTagCompound var5 = par1ItemStack.getTagCompound().getCompoundTag("Fireworks");
/* 37:49 */       if (var5 != null)
/* 38:   */       {
/* 39:51 */         if (var5.func_150297_b("Flight", 99)) {
/* 40:53 */           par3List.add(StatCollector.translateToLocal("item.fireworks.flight") + " " + var5.getByte("Flight"));
/* 41:   */         }
/* 42:56 */         NBTTagList var6 = var5.getTagList("Explosions", 10);
/* 43:58 */         if ((var6 != null) && (var6.tagCount() > 0)) {
/* 44:60 */           for (int var7 = 0; var7 < var6.tagCount(); var7++)
/* 45:   */           {
/* 46:62 */             NBTTagCompound var8 = var6.getCompoundTagAt(var7);
/* 47:63 */             ArrayList var9 = new ArrayList();
/* 48:64 */             ItemFireworkCharge.func_150902_a(var8, var9);
/* 49:66 */             if (var9.size() > 0)
/* 50:   */             {
/* 51:68 */               for (int var10 = 1; var10 < var9.size(); var10++) {
/* 52:70 */                 var9.set(var10, "  " + (String)var9.get(var10));
/* 53:   */               }
/* 54:73 */               par3List.addAll(var9);
/* 55:   */             }
/* 56:   */           }
/* 57:   */         }
/* 58:   */       }
/* 59:   */     }
/* 60:   */   }
/* 61:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.item.ItemFirework
 * JD-Core Version:    0.7.0.1
 */