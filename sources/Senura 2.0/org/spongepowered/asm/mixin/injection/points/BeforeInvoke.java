/*     */ package org.spongepowered.asm.mixin.injection.points;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.ListIterator;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
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
/*     */ @AtCode("INVOKE")
/*     */ public class BeforeInvoke
/*     */   extends InjectionPoint
/*     */ {
/*     */   protected final MemberInfo target;
/*     */   protected final boolean allowPermissive;
/*     */   protected final int ordinal;
/*     */   protected final String className;
/*     */   protected final IMixinContext context;
/*     */   
/*     */   public enum SearchType
/*     */   {
/*  78 */     STRICT,
/*  79 */     PERMISSIVE;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   protected final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */   
/*     */   private boolean log = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public BeforeInvoke(InjectionPointData data) {
/* 120 */     super(data);
/*     */     
/* 122 */     this.target = data.getTarget();
/* 123 */     this.ordinal = data.getOrdinal();
/* 124 */     this.log = data.get("log", false);
/* 125 */     this.className = getClassName();
/* 126 */     this.context = data.getContext();
/* 127 */     this
/* 128 */       .allowPermissive = (this.context.getOption(MixinEnvironment.Option.REFMAP_REMAP) && this.context.getOption(MixinEnvironment.Option.REFMAP_REMAP_ALLOW_PERMISSIVE) && !this.context.getReferenceMapper().isDefault());
/*     */   }
/*     */   
/*     */   private String getClassName() {
/* 132 */     InjectionPoint.AtCode atCode = getClass().<InjectionPoint.AtCode>getAnnotation(InjectionPoint.AtCode.class);
/* 133 */     return String.format("@At(%s)", new Object[] { (atCode != null) ? atCode.value() : getClass().getSimpleName().toUpperCase() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeforeInvoke setLogging(boolean logging) {
/* 143 */     this.log = logging;
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 154 */     log("{} is searching for an injection point in method with descriptor {}", new Object[] { this.className, desc });
/*     */     
/* 156 */     if (!find(desc, insns, nodes, this.target, SearchType.STRICT) && this.target.desc != null && this.allowPermissive) {
/* 157 */       this.logger.warn("STRICT match for {} using \"{}\" in {} returned 0 results, attempting permissive search. To inhibit permissive search set mixin.env.allowPermissiveMatch=false", new Object[] { this.className, this.target, this.context });
/*     */       
/* 159 */       return find(desc, insns, nodes, this.target, SearchType.PERMISSIVE);
/*     */     } 
/* 161 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes, MemberInfo member, SearchType searchType) {
/* 165 */     if (member == null) {
/* 166 */       return false;
/*     */     }
/*     */     
/* 169 */     MemberInfo target = (searchType == SearchType.PERMISSIVE) ? member.transform(null) : member;
/*     */     
/* 171 */     int ordinal = 0;
/* 172 */     int found = 0;
/*     */     
/* 174 */     ListIterator<AbstractInsnNode> iter = insns.iterator();
/* 175 */     while (iter.hasNext()) {
/* 176 */       AbstractInsnNode insn = iter.next();
/*     */       
/* 178 */       if (matchesInsn(insn)) {
/* 179 */         MemberInfo nodeInfo = new MemberInfo(insn);
/* 180 */         log("{} is considering insn {}", new Object[] { this.className, nodeInfo });
/*     */         
/* 182 */         if (target.matches(nodeInfo.owner, nodeInfo.name, nodeInfo.desc)) {
/* 183 */           log("{} > found a matching insn, checking preconditions...", new Object[] { this.className });
/*     */           
/* 185 */           if (matchesInsn(nodeInfo, ordinal)) {
/* 186 */             log("{} > > > found a matching insn at ordinal {}", new Object[] { this.className, Integer.valueOf(ordinal) });
/*     */             
/* 188 */             if (addInsn(insns, nodes, insn)) {
/* 189 */               found++;
/*     */             }
/*     */             
/* 192 */             if (this.ordinal == ordinal) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */           
/* 197 */           ordinal++;
/*     */         } 
/*     */       } 
/*     */       
/* 201 */       inspectInsn(desc, insns, insn);
/*     */     } 
/*     */     
/* 204 */     if (searchType == SearchType.PERMISSIVE && found > 1) {
/* 205 */       this.logger.warn("A permissive match for {} using \"{}\" in {} matched {} instructions, this may cause unexpected behaviour. To inhibit permissive search set mixin.env.allowPermissiveMatch=false", new Object[] { this.className, member, this.context, 
/* 206 */             Integer.valueOf(found) });
/*     */     }
/*     */     
/* 209 */     return (found > 0);
/*     */   }
/*     */   
/*     */   protected boolean addInsn(InsnList insns, Collection<AbstractInsnNode> nodes, AbstractInsnNode insn) {
/* 213 */     nodes.add(insn);
/* 214 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean matchesInsn(AbstractInsnNode insn) {
/* 218 */     return insn instanceof org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void inspectInsn(String desc, InsnList insns, AbstractInsnNode insn) {}
/*     */ 
/*     */   
/*     */   protected boolean matchesInsn(MemberInfo nodeInfo, int ordinal) {
/* 226 */     log("{} > > comparing target ordinal {} with current ordinal {}", new Object[] { this.className, Integer.valueOf(this.ordinal), Integer.valueOf(ordinal) });
/* 227 */     return (this.ordinal == -1 || this.ordinal == ordinal);
/*     */   }
/*     */   
/*     */   protected void log(String message, Object... params) {
/* 231 */     if (this.log)
/* 232 */       this.logger.info(message, params); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\points\BeforeInvoke.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */