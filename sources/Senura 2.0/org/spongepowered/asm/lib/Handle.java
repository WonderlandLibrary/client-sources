/*     */ package org.spongepowered.asm.lib;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Handle
/*     */ {
/*     */   final int tag;
/*     */   final String owner;
/*     */   final String name;
/*     */   final String desc;
/*     */   final boolean itf;
/*     */   
/*     */   @Deprecated
/*     */   public Handle(int tag, String owner, String name, String desc) {
/*  99 */     this(tag, owner, name, desc, (tag == 9));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Handle(int tag, String owner, String name, String desc, boolean itf) {
/* 126 */     this.tag = tag;
/* 127 */     this.owner = owner;
/* 128 */     this.name = name;
/* 129 */     this.desc = desc;
/* 130 */     this.itf = itf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTag() {
/* 144 */     return this.tag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getOwner() {
/* 155 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 164 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDesc() {
/* 173 */     return this.desc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInterface() {
/* 184 */     return this.itf;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 189 */     if (obj == this) {
/* 190 */       return true;
/*     */     }
/* 192 */     if (!(obj instanceof Handle)) {
/* 193 */       return false;
/*     */     }
/* 195 */     Handle h = (Handle)obj;
/* 196 */     return (this.tag == h.tag && this.itf == h.itf && this.owner.equals(h.owner) && this.name
/* 197 */       .equals(h.name) && this.desc.equals(h.desc));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 202 */     return this.tag + (this.itf ? 64 : 0) + this.owner.hashCode() * this.name.hashCode() * this.desc.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 220 */     return this.owner + '.' + this.name + this.desc + " (" + this.tag + (this.itf ? " itf" : "") + ')';
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\Handle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */