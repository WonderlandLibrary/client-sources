/*     */ package org.spongepowered.asm.mixin.injection.points;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.TypeInsnNode;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
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
/*     */ @AtCode("NEW")
/*     */ public class BeforeNew
/*     */   extends InjectionPoint
/*     */ {
/*     */   private final String target;
/*     */   private final String desc;
/*     */   private final int ordinal;
/*     */   
/*     */   public BeforeNew(InjectionPointData data) {
/* 107 */     super(data);
/*     */     
/* 109 */     this.ordinal = data.getOrdinal();
/* 110 */     String target = Strings.emptyToNull(data.get("class", data.get("target", "")).replace('.', '/'));
/* 111 */     MemberInfo member = MemberInfo.parseAndValidate(target, data.getContext());
/* 112 */     this.target = member.toCtorType();
/* 113 */     this.desc = member.toCtorDesc();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasDescriptor() {
/* 120 */     return (this.desc != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 126 */     boolean found = false;
/* 127 */     int ordinal = 0;
/*     */     
/* 129 */     Collection<TypeInsnNode> newNodes = new ArrayList<TypeInsnNode>();
/* 130 */     Collection<AbstractInsnNode> candidates = (this.desc != null) ? (Collection)newNodes : nodes;
/* 131 */     ListIterator<AbstractInsnNode> iter = insns.iterator();
/* 132 */     while (iter.hasNext()) {
/* 133 */       AbstractInsnNode insn = iter.next();
/*     */       
/* 135 */       if (insn instanceof TypeInsnNode && insn.getOpcode() == 187 && matchesOwner((TypeInsnNode)insn)) {
/* 136 */         if (this.ordinal == -1 || this.ordinal == ordinal) {
/* 137 */           candidates.add(insn);
/* 138 */           found = (this.desc == null);
/*     */         } 
/*     */         
/* 141 */         ordinal++;
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     if (this.desc != null) {
/* 146 */       for (TypeInsnNode newNode : newNodes) {
/* 147 */         if (findCtor(insns, newNode)) {
/* 148 */           nodes.add(newNode);
/* 149 */           found = true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 154 */     return found;
/*     */   }
/*     */   
/*     */   protected boolean findCtor(InsnList insns, TypeInsnNode newNode) {
/* 158 */     int indexOf = insns.indexOf((AbstractInsnNode)newNode);
/* 159 */     for (Iterator<AbstractInsnNode> iter = insns.iterator(indexOf); iter.hasNext(); ) {
/* 160 */       AbstractInsnNode insn = iter.next();
/* 161 */       if (insn instanceof MethodInsnNode && insn.getOpcode() == 183) {
/* 162 */         MethodInsnNode methodNode = (MethodInsnNode)insn;
/* 163 */         if ("<init>".equals(methodNode.name) && methodNode.owner.equals(newNode.desc) && methodNode.desc.equals(this.desc)) {
/* 164 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 168 */     return false;
/*     */   }
/*     */   
/*     */   private boolean matchesOwner(TypeInsnNode insn) {
/* 172 */     return (this.target == null || this.target.equals(insn.desc));
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\points\BeforeNew.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */