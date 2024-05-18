/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ 
/*     */ 
/*     */ public class GuiUtilRenderComponents
/*     */ {
/*     */   public static String func_178909_a(String p_178909_0_, boolean p_178909_1_) {
/*  14 */     return (!p_178909_1_ && !(Minecraft.getMinecraft()).gameSettings.chatColours) ? EnumChatFormatting.getTextWithoutFormattingCodes(p_178909_0_) : p_178909_0_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<IChatComponent> func_178908_a(IChatComponent p_178908_0_, int p_178908_1_, FontRenderer p_178908_2_, boolean p_178908_3_, boolean p_178908_4_) {
/*  19 */     int i = 0;
/*  20 */     ChatComponentText chatComponentText = new ChatComponentText("");
/*  21 */     List<IChatComponent> list = Lists.newArrayList();
/*  22 */     List<IChatComponent> list1 = Lists.newArrayList((Iterable)p_178908_0_);
/*     */     
/*  24 */     for (int j = 0; j < list1.size(); j++) {
/*     */       
/*  26 */       IChatComponent ichatcomponent1 = list1.get(j);
/*  27 */       String s = ichatcomponent1.getUnformattedTextForChat();
/*  28 */       boolean flag = false;
/*     */       
/*  30 */       if (s.contains("\n")) {
/*     */         
/*  32 */         int k = s.indexOf('\n');
/*  33 */         String s1 = s.substring(k + 1);
/*  34 */         s = s.substring(0, k + 1);
/*  35 */         ChatComponentText chatcomponenttext = new ChatComponentText(s1);
/*  36 */         chatcomponenttext.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/*  37 */         list1.add(j + 1, chatcomponenttext);
/*  38 */         flag = true;
/*     */       } 
/*     */       
/*  41 */       String s4 = func_178909_a(String.valueOf(ichatcomponent1.getChatStyle().getFormattingCode()) + s, p_178908_4_);
/*  42 */       String s5 = s4.endsWith("\n") ? s4.substring(0, s4.length() - 1) : s4;
/*  43 */       int i1 = p_178908_2_.getStringWidth(s5);
/*  44 */       ChatComponentText chatcomponenttext1 = new ChatComponentText(s5);
/*  45 */       chatcomponenttext1.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/*     */       
/*  47 */       if (i + i1 > p_178908_1_) {
/*     */         
/*  49 */         String s2 = p_178908_2_.trimStringToWidth(s4, p_178908_1_ - i, false);
/*  50 */         String s3 = (s2.length() < s4.length()) ? s4.substring(s2.length()) : null;
/*     */         
/*  52 */         if (s3 != null && s3.length() > 0) {
/*     */           
/*  54 */           int l = s2.lastIndexOf(" ");
/*     */           
/*  56 */           if (l >= 0 && p_178908_2_.getStringWidth(s4.substring(0, l)) > 0) {
/*     */             
/*  58 */             s2 = s4.substring(0, l);
/*     */             
/*  60 */             if (p_178908_3_)
/*     */             {
/*  62 */               l++;
/*     */             }
/*     */             
/*  65 */             s3 = s4.substring(l);
/*     */           }
/*  67 */           else if (i > 0 && !s4.contains(" ")) {
/*     */             
/*  69 */             s2 = "";
/*  70 */             s3 = s4;
/*     */           } 
/*     */           
/*  73 */           ChatComponentText chatcomponenttext2 = new ChatComponentText(s3);
/*  74 */           chatcomponenttext2.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/*  75 */           list1.add(j + 1, chatcomponenttext2);
/*     */         } 
/*     */         
/*  78 */         i1 = p_178908_2_.getStringWidth(s2);
/*  79 */         chatcomponenttext1 = new ChatComponentText(s2);
/*  80 */         chatcomponenttext1.setChatStyle(ichatcomponent1.getChatStyle().createShallowCopy());
/*  81 */         flag = true;
/*     */       } 
/*     */       
/*  84 */       if (i + i1 <= p_178908_1_) {
/*     */         
/*  86 */         i += i1;
/*  87 */         chatComponentText.appendSibling((IChatComponent)chatcomponenttext1);
/*     */       }
/*     */       else {
/*     */         
/*  91 */         flag = true;
/*     */       } 
/*     */       
/*  94 */       if (flag) {
/*     */         
/*  96 */         list.add(chatComponentText);
/*  97 */         i = 0;
/*  98 */         chatComponentText = new ChatComponentText("");
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     list.add(chatComponentText);
/* 103 */     return list;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiUtilRenderComponents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */