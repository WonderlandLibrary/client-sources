/*     */ package org.spongepowered.asm.mixin.transformer;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.util.Counter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MethodMapper
/*     */ {
/*  51 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */   
/*  53 */   private static final List<String> classes = new ArrayList<String>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private static final Map<String, Counter> methods = new HashMap<String, Counter>();
/*     */   
/*     */   private final ClassInfo info;
/*     */   
/*     */   public MethodMapper(MixinEnvironment env, ClassInfo info) {
/*  64 */     this.info = info;
/*     */   }
/*     */   
/*     */   public ClassInfo getClassInfo() {
/*  68 */     return this.info;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remapHandlerMethod(MixinInfo mixin, MethodNode handler, ClassInfo.Method method) {
/*  79 */     if (!(handler instanceof MixinInfo.MixinMethodNode) || !((MixinInfo.MixinMethodNode)handler).isInjector()) {
/*     */       return;
/*     */     }
/*     */     
/*  83 */     if (method.isUnique()) {
/*  84 */       logger.warn("Redundant @Unique on injector method {} in {}. Injectors are implicitly unique", new Object[] { method, mixin });
/*     */     }
/*     */     
/*  87 */     if (method.isRenamed()) {
/*  88 */       handler.name = method.getName();
/*     */       
/*     */       return;
/*     */     } 
/*  92 */     String handlerName = getHandlerName((MixinInfo.MixinMethodNode)handler);
/*  93 */     handler.name = method.renameTo(handlerName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHandlerName(MixinInfo.MixinMethodNode method) {
/* 103 */     String prefix = InjectionInfo.getInjectorPrefix(method.getInjectorAnnotation());
/* 104 */     String classUID = getClassUID(method.getOwner().getClassRef());
/* 105 */     String methodUID = getMethodUID(method.name, method.desc, !method.isSurrogate());
/* 106 */     return String.format("%s$%s$%s%s", new Object[] { prefix, method.name, classUID, methodUID });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getClassUID(String classRef) {
/* 116 */     int index = classes.indexOf(classRef);
/* 117 */     if (index < 0) {
/* 118 */       index = classes.size();
/* 119 */       classes.add(classRef);
/*     */     } 
/* 121 */     return finagle(index);
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
/*     */   private static String getMethodUID(String name, String desc, boolean increment) {
/* 133 */     String descriptor = String.format("%s%s", new Object[] { name, desc });
/* 134 */     Counter id = methods.get(descriptor);
/* 135 */     if (id == null) {
/* 136 */       id = new Counter();
/* 137 */       methods.put(descriptor, id);
/* 138 */     } else if (increment) {
/* 139 */       id.value++;
/*     */     } 
/* 141 */     return String.format("%03x", new Object[] { Integer.valueOf(id.value) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String finagle(int index) {
/* 151 */     String hex = Integer.toHexString(index);
/* 152 */     StringBuilder sb = new StringBuilder();
/* 153 */     for (int pos = 0; pos < hex.length(); pos++) {
/* 154 */       char c = hex.charAt(pos);
/* 155 */       sb.append(c = (char)(c + ((c < ':') ? 49 : 10)));
/*     */     } 
/* 157 */     return Strings.padStart(sb.toString(), 3, 'z');
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\MethodMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */