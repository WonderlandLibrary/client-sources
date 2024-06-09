/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.command.CommandException;
/*    */ import net.minecraft.command.EntityNotFoundException;
/*    */ import net.minecraft.command.ICommandSender;
/*    */ import net.minecraft.command.PlayerSelector;
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ 
/*    */ public class ChatComponentProcessor
/*    */ {
/*    */   public static IChatComponent processComponent(ICommandSender commandSender, IChatComponent component, Entity entityIn) throws CommandException {
/* 14 */     IChatComponent ichatcomponent = null;
/*    */     
/* 16 */     if (component instanceof ChatComponentScore) {
/*    */       
/* 18 */       ChatComponentScore chatcomponentscore = (ChatComponentScore)component;
/* 19 */       String s = chatcomponentscore.getName();
/*    */       
/* 21 */       if (PlayerSelector.hasArguments(s)) {
/*    */         
/* 23 */         List<Entity> list = PlayerSelector.matchEntities(commandSender, s, Entity.class);
/*    */         
/* 25 */         if (list.size() != 1)
/*    */         {
/* 27 */           throw new EntityNotFoundException();
/*    */         }
/*    */         
/* 30 */         s = ((Entity)list.get(0)).getName();
/*    */       } 
/*    */       
/* 33 */       ichatcomponent = (entityIn != null && s.equals("*")) ? new ChatComponentScore(entityIn.getName(), chatcomponentscore.getObjective()) : new ChatComponentScore(s, chatcomponentscore.getObjective());
/* 34 */       ((ChatComponentScore)ichatcomponent).setValue(chatcomponentscore.getUnformattedTextForChat());
/*    */     }
/* 36 */     else if (component instanceof ChatComponentSelector) {
/*    */       
/* 38 */       String s1 = ((ChatComponentSelector)component).getSelector();
/* 39 */       ichatcomponent = PlayerSelector.matchEntitiesToChatComponent(commandSender, s1);
/*    */       
/* 41 */       if (ichatcomponent == null)
/*    */       {
/* 43 */         ichatcomponent = new ChatComponentText("");
/*    */       }
/*    */     }
/* 46 */     else if (component instanceof ChatComponentText) {
/*    */       
/* 48 */       ichatcomponent = new ChatComponentText(((ChatComponentText)component).getChatComponentText_TextValue());
/*    */     }
/*    */     else {
/*    */       
/* 52 */       if (!(component instanceof ChatComponentTranslation))
/*    */       {
/* 54 */         return component;
/*    */       }
/*    */       
/* 57 */       Object[] aobject = ((ChatComponentTranslation)component).getFormatArgs();
/*    */       
/* 59 */       for (int i = 0; i < aobject.length; i++) {
/*    */         
/* 61 */         Object object = aobject[i];
/*    */         
/* 63 */         if (object instanceof IChatComponent)
/*    */         {
/* 65 */           aobject[i] = processComponent(commandSender, (IChatComponent)object, entityIn);
/*    */         }
/*    */       } 
/*    */       
/* 69 */       ichatcomponent = new ChatComponentTranslation(((ChatComponentTranslation)component).getKey(), aobject);
/*    */     } 
/*    */     
/* 72 */     ChatStyle chatstyle = component.getChatStyle();
/*    */     
/* 74 */     if (chatstyle != null)
/*    */     {
/* 76 */       ichatcomponent.setChatStyle(chatstyle.createShallowCopy());
/*    */     }
/*    */     
/* 79 */     for (IChatComponent ichatcomponent1 : component.getSiblings())
/*    */     {
/* 81 */       ichatcomponent.appendSibling(processComponent(commandSender, ichatcomponent1, entityIn));
/*    */     }
/*    */     
/* 84 */     return ichatcomponent;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\ChatComponentProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */