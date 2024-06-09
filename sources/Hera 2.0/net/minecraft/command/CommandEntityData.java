/*    */ package net.minecraft.command;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.nbt.JsonToNBT;
/*    */ import net.minecraft.nbt.NBTException;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandEntityData
/*    */   extends CommandBase
/*    */ {
/*    */   public String getCommandName() {
/* 16 */     return "entitydata";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getRequiredPermissionLevel() {
/* 24 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCommandUsage(ICommandSender sender) {
/* 32 */     return "commands.entitydata.usage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*    */     NBTTagCompound nbttagcompound2;
/* 40 */     if (args.length < 2)
/*    */     {
/* 42 */       throw new WrongUsageException("commands.entitydata.usage", new Object[0]);
/*    */     }
/*    */ 
/*    */     
/* 46 */     Entity entity = func_175768_b(sender, args[0]);
/*    */     
/* 48 */     if (entity instanceof net.minecraft.entity.player.EntityPlayer)
/*    */     {
/* 50 */       throw new CommandException("commands.entitydata.noPlayers", new Object[] { entity.getDisplayName() });
/*    */     }
/*    */ 
/*    */     
/* 54 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 55 */     entity.writeToNBT(nbttagcompound);
/* 56 */     NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttagcompound.copy();
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 61 */       nbttagcompound2 = JsonToNBT.getTagFromJson(getChatComponentFromNthArg(sender, args, 1).getUnformattedText());
/*    */     }
/* 63 */     catch (NBTException nbtexception) {
/*    */       
/* 65 */       throw new CommandException("commands.entitydata.tagError", new Object[] { nbtexception.getMessage() });
/*    */     } 
/*    */     
/* 68 */     nbttagcompound2.removeTag("UUIDMost");
/* 69 */     nbttagcompound2.removeTag("UUIDLeast");
/* 70 */     nbttagcompound.merge(nbttagcompound2);
/*    */     
/* 72 */     if (nbttagcompound.equals(nbttagcompound1))
/*    */     {
/* 74 */       throw new CommandException("commands.entitydata.failed", new Object[] { nbttagcompound.toString() });
/*    */     }
/*    */ 
/*    */     
/* 78 */     entity.readFromNBT(nbttagcompound);
/* 79 */     notifyOperators(sender, this, "commands.entitydata.success", new Object[] { nbttagcompound.toString() });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isUsernameIndex(String[] args, int index) {
/* 90 */     return (index == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\command\CommandEntityData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */