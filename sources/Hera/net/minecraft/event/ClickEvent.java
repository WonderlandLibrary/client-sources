/*     */ package net.minecraft.event;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public class ClickEvent
/*     */ {
/*     */   private final Action action;
/*     */   private final String value;
/*     */   
/*     */   public ClickEvent(Action theAction, String theValue) {
/*  13 */     this.action = theAction;
/*  14 */     this.value = theValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Action getAction() {
/*  22 */     return this.action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/*  31 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  36 */     if (this == p_equals_1_)
/*     */     {
/*  38 */       return true;
/*     */     }
/*  40 */     if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/*     */       
/*  42 */       ClickEvent clickevent = (ClickEvent)p_equals_1_;
/*     */       
/*  44 */       if (this.action != clickevent.action)
/*     */       {
/*  46 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  50 */       if (this.value != null) {
/*     */         
/*  52 */         if (!this.value.equals(clickevent.value))
/*     */         {
/*  54 */           return false;
/*     */         }
/*     */       }
/*  57 */       else if (clickevent.value != null) {
/*     */         
/*  59 */         return false;
/*     */       } 
/*     */       
/*  62 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  73 */     return "ClickEvent{action=" + this.action + ", value='" + this.value + '\'' + '}';
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  78 */     int i = this.action.hashCode();
/*  79 */     i = 31 * i + ((this.value != null) ? this.value.hashCode() : 0);
/*  80 */     return i;
/*     */   }
/*     */   
/*     */   public enum Action
/*     */   {
/*  85 */     OPEN_URL("open_url", true),
/*  86 */     OPEN_FILE("open_file", false),
/*  87 */     RUN_COMMAND("run_command", true),
/*  88 */     TWITCH_USER_INFO("twitch_user_info", false),
/*  89 */     SUGGEST_COMMAND("suggest_command", true),
/*  90 */     CHANGE_PAGE("change_page", true);
/*     */     
/*  92 */     private static final Map<String, Action> nameMapping = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final boolean allowedInChat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String canonicalName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       Action[] arrayOfAction;
/* 118 */       for (i = (arrayOfAction = values()).length, b = 0; b < i; ) { Action clickevent$action = arrayOfAction[b];
/*     */         
/* 120 */         nameMapping.put(clickevent$action.getCanonicalName(), clickevent$action);
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     Action(String canonicalNameIn, boolean allowedInChatIn) {
/*     */       this.canonicalName = canonicalNameIn;
/*     */       this.allowedInChat = allowedInChatIn;
/*     */     }
/*     */     
/*     */     public boolean shouldAllowInChat() {
/*     */       return this.allowedInChat;
/*     */     }
/*     */     
/*     */     public String getCanonicalName() {
/*     */       return this.canonicalName;
/*     */     }
/*     */     
/*     */     public static Action getValueByCanonicalName(String canonicalNameIn) {
/*     */       return nameMapping.get(canonicalNameIn);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\event\ClickEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */