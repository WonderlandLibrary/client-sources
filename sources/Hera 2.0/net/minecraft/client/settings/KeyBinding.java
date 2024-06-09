/*     */ package net.minecraft.client.settings;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.IntHashMap;
/*     */ 
/*     */ public class KeyBinding
/*     */   implements Comparable<KeyBinding> {
/*  12 */   private static final List<KeyBinding> keybindArray = Lists.newArrayList();
/*  13 */   private static final IntHashMap<KeyBinding> hash = new IntHashMap();
/*  14 */   private static final Set<String> keybindSet = Sets.newHashSet();
/*     */   
/*     */   private final String keyDescription;
/*     */   
/*     */   private final int keyCodeDefault;
/*     */   
/*     */   private final String keyCategory;
/*     */   
/*     */   private int keyCode;
/*     */   
/*     */   public boolean pressed;
/*     */   private int pressTime;
/*     */   
/*     */   public static void onTick(int keyCode) {
/*  28 */     if (keyCode != 0) {
/*     */       
/*  30 */       KeyBinding keybinding = (KeyBinding)hash.lookup(keyCode);
/*     */       
/*  32 */       if (keybinding != null)
/*     */       {
/*  34 */         keybinding.pressTime++;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setKeyBindState(int keyCode, boolean pressed) {
/*  41 */     if (keyCode != 0) {
/*     */       
/*  43 */       KeyBinding keybinding = (KeyBinding)hash.lookup(keyCode);
/*     */       
/*  45 */       if (keybinding != null)
/*     */       {
/*  47 */         keybinding.pressed = pressed;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void unPressAllKeys() {
/*  54 */     for (KeyBinding keybinding : keybindArray)
/*     */     {
/*  56 */       keybinding.unpressKey();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void resetKeyBindingArrayAndHash() {
/*  62 */     hash.clearMap();
/*     */     
/*  64 */     for (KeyBinding keybinding : keybindArray)
/*     */     {
/*  66 */       hash.addKey(keybinding.keyCode, keybinding);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Set<String> getKeybinds() {
/*  72 */     return keybindSet;
/*     */   }
/*     */ 
/*     */   
/*     */   public KeyBinding(String description, int keyCode, String category) {
/*  77 */     this.keyDescription = description;
/*  78 */     this.keyCode = keyCode;
/*  79 */     this.keyCodeDefault = keyCode;
/*  80 */     this.keyCategory = category;
/*  81 */     keybindArray.add(this);
/*  82 */     hash.addKey(keyCode, this);
/*  83 */     keybindSet.add(category);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isKeyDown() {
/*  91 */     return this.pressed;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKeyCategory() {
/*  96 */     return this.keyCategory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPressed() {
/* 105 */     if (this.pressTime == 0)
/*     */     {
/* 107 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 111 */     this.pressTime--;
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void unpressKey() {
/* 118 */     this.pressTime = 0;
/* 119 */     this.pressed = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKeyDescription() {
/* 124 */     return this.keyDescription;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getKeyCodeDefault() {
/* 129 */     return this.keyCodeDefault;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getKeyCode() {
/* 134 */     return this.keyCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setKeyCode(int keyCode) {
/* 139 */     this.keyCode = keyCode;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(KeyBinding p_compareTo_1_) {
/* 144 */     int i = I18n.format(this.keyCategory, new Object[0]).compareTo(I18n.format(p_compareTo_1_.keyCategory, new Object[0]));
/*     */     
/* 146 */     if (i == 0)
/*     */     {
/* 148 */       i = I18n.format(this.keyDescription, new Object[0]).compareTo(I18n.format(p_compareTo_1_.keyDescription, new Object[0]));
/*     */     }
/*     */     
/* 151 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\settings\KeyBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */