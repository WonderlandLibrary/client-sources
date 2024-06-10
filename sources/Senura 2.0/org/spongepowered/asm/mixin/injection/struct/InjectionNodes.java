/*     */ package org.spongepowered.asm.mixin.injection.struct;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.util.Bytecode;
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
/*     */ public class InjectionNodes
/*     */   extends ArrayList<InjectionNodes.InjectionNode>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public static class InjectionNode
/*     */     implements Comparable<InjectionNode>
/*     */   {
/*  58 */     private static int nextId = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final AbstractInsnNode originalTarget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private AbstractInsnNode currentTarget;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Map<String, Object> decorations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InjectionNode(AbstractInsnNode node) {
/*  88 */       this.currentTarget = this.originalTarget = node;
/*  89 */       this.id = nextId++;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getId() {
/*  96 */       return this.id;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AbstractInsnNode getOriginalTarget() {
/* 103 */       return this.originalTarget;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AbstractInsnNode getCurrentTarget() {
/* 111 */       return this.currentTarget;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InjectionNode replace(AbstractInsnNode target) {
/* 120 */       this.currentTarget = target;
/* 121 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InjectionNode remove() {
/* 128 */       this.currentTarget = null;
/* 129 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean matches(AbstractInsnNode node) {
/* 141 */       return (this.originalTarget == node || this.currentTarget == node);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isReplaced() {
/* 148 */       return (this.originalTarget != this.currentTarget);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isRemoved() {
/* 155 */       return (this.currentTarget == null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <V> InjectionNode decorate(String key, V value) {
/* 166 */       if (this.decorations == null) {
/* 167 */         this.decorations = new HashMap<String, Object>();
/*     */       }
/* 169 */       this.decorations.put(key, value);
/* 170 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasDecoration(String key) {
/* 180 */       return (this.decorations != null && this.decorations.get(key) != null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <V> V getDecoration(String key) {
/* 192 */       return (this.decorations == null) ? null : (V)this.decorations.get(key);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compareTo(InjectionNode other) {
/* 200 */       return (other == null) ? Integer.MAX_VALUE : (hashCode() - other.hashCode());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 208 */       return String.format("InjectionNode[%s]", new Object[] { Bytecode.describeNode(this.currentTarget).replaceAll("\\s+", " ") });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InjectionNode add(AbstractInsnNode node) {
/* 220 */     InjectionNode injectionNode = get(node);
/* 221 */     if (injectionNode == null) {
/* 222 */       injectionNode = new InjectionNode(node);
/* 223 */       add(injectionNode);
/*     */     } 
/* 225 */     return injectionNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InjectionNode get(AbstractInsnNode node) {
/* 236 */     for (InjectionNode injectionNode : this) {
/* 237 */       if (injectionNode.matches(node)) {
/* 238 */         return injectionNode;
/*     */       }
/*     */     } 
/* 241 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(AbstractInsnNode node) {
/* 251 */     return (get(node) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void replace(AbstractInsnNode oldNode, AbstractInsnNode newNode) {
/* 262 */     InjectionNode injectionNode = get(oldNode);
/* 263 */     if (injectionNode != null) {
/* 264 */       injectionNode.replace(newNode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(AbstractInsnNode node) {
/* 275 */     InjectionNode injectionNode = get(node);
/* 276 */     if (injectionNode != null)
/* 277 */       injectionNode.remove(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\struct\InjectionNodes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */