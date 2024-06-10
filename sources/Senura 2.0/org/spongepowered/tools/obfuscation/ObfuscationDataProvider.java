/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObfuscationDataProvider
/*     */   implements IObfuscationDataProvider
/*     */ {
/*     */   private final IMixinAnnotationProcessor ap;
/*     */   private final List<ObfuscationEnvironment> environments;
/*     */   
/*     */   public ObfuscationDataProvider(IMixinAnnotationProcessor ap, List<ObfuscationEnvironment> environments) {
/*  55 */     this.ap = ap;
/*  56 */     this.environments = environments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> ObfuscationData<T> getObfEntryRecursive(MemberInfo targetMember) {
/*  66 */     MemberInfo currentTarget = targetMember;
/*  67 */     ObfuscationData<String> obfTargetNames = getObfClass(currentTarget.owner);
/*  68 */     ObfuscationData<T> obfData = getObfEntry(currentTarget);
/*     */     try {
/*  70 */       while (obfData.isEmpty()) {
/*  71 */         TypeHandle targetType = this.ap.getTypeProvider().getTypeHandle(currentTarget.owner);
/*  72 */         if (targetType == null) {
/*  73 */           return obfData;
/*     */         }
/*     */         
/*  76 */         TypeHandle superClass = targetType.getSuperclass();
/*  77 */         obfData = getObfEntryUsing(currentTarget, superClass);
/*  78 */         if (!obfData.isEmpty()) {
/*  79 */           return applyParents(obfTargetNames, obfData);
/*     */         }
/*     */         
/*  82 */         for (TypeHandle iface : targetType.getInterfaces()) {
/*  83 */           obfData = getObfEntryUsing(currentTarget, iface);
/*  84 */           if (!obfData.isEmpty()) {
/*  85 */             return applyParents(obfTargetNames, obfData);
/*     */           }
/*     */         } 
/*     */         
/*  89 */         if (superClass == null) {
/*     */           break;
/*     */         }
/*     */         
/*  93 */         currentTarget = currentTarget.move(superClass.getName());
/*     */       } 
/*  95 */     } catch (Exception ex) {
/*  96 */       ex.printStackTrace();
/*  97 */       return getObfEntry(targetMember);
/*     */     } 
/*  99 */     return obfData;
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
/*     */   private <T> ObfuscationData<T> getObfEntryUsing(MemberInfo targetMember, TypeHandle targetClass) {
/* 113 */     return (targetClass == null) ? new ObfuscationData<T>() : getObfEntry(targetMember.move(targetClass.getName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> ObfuscationData<T> getObfEntry(MemberInfo targetMember) {
/* 124 */     if (targetMember.isField()) {
/* 125 */       return (ObfuscationData)getObfField(targetMember);
/*     */     }
/* 127 */     return (ObfuscationData)getObfMethod(targetMember.asMethodMapping());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> ObfuscationData<T> getObfEntry(IMapping<T> mapping) {
/* 133 */     if (mapping != null) {
/* 134 */       if (mapping.getType() == IMapping.Type.FIELD)
/* 135 */         return (ObfuscationData)getObfField((MappingField)mapping); 
/* 136 */       if (mapping.getType() == IMapping.Type.METHOD) {
/* 137 */         return (ObfuscationData)getObfMethod((MappingMethod)mapping);
/*     */       }
/*     */     } 
/*     */     
/* 141 */     return new ObfuscationData<T>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingMethod> getObfMethodRecursive(MemberInfo targetMember) {
/* 151 */     return getObfEntryRecursive(targetMember);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingMethod> getObfMethod(MemberInfo method) {
/* 161 */     return getRemappedMethod(method, method.isConstructor());
/*     */   }
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingMethod> getRemappedMethod(MemberInfo method) {
/* 166 */     return getRemappedMethod(method, true);
/*     */   }
/*     */   
/*     */   private ObfuscationData<MappingMethod> getRemappedMethod(MemberInfo method, boolean remapDescriptor) {
/* 170 */     ObfuscationData<MappingMethod> data = new ObfuscationData<MappingMethod>();
/*     */     
/* 172 */     for (ObfuscationEnvironment env : this.environments) {
/* 173 */       MappingMethod obfMethod = env.getObfMethod(method);
/* 174 */       if (obfMethod != null) {
/* 175 */         data.put(env.getType(), obfMethod);
/*     */       }
/*     */     } 
/*     */     
/* 179 */     if (!data.isEmpty() || !remapDescriptor) {
/* 180 */       return data;
/*     */     }
/*     */     
/* 183 */     return remapDescriptor(data, method);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingMethod> getObfMethod(MappingMethod method) {
/* 193 */     return getRemappedMethod(method, method.isConstructor());
/*     */   }
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingMethod> getRemappedMethod(MappingMethod method) {
/* 198 */     return getRemappedMethod(method, true);
/*     */   }
/*     */   
/*     */   private ObfuscationData<MappingMethod> getRemappedMethod(MappingMethod method, boolean remapDescriptor) {
/* 202 */     ObfuscationData<MappingMethod> data = new ObfuscationData<MappingMethod>();
/*     */     
/* 204 */     for (ObfuscationEnvironment env : this.environments) {
/* 205 */       MappingMethod obfMethod = env.getObfMethod(method);
/* 206 */       if (obfMethod != null) {
/* 207 */         data.put(env.getType(), obfMethod);
/*     */       }
/*     */     } 
/*     */     
/* 211 */     if (!data.isEmpty() || !remapDescriptor) {
/* 212 */       return data;
/*     */     }
/*     */     
/* 215 */     return remapDescriptor(data, new MemberInfo((IMapping)method));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingMethod> remapDescriptor(ObfuscationData<MappingMethod> data, MemberInfo method) {
/* 226 */     for (ObfuscationEnvironment env : this.environments) {
/* 227 */       MemberInfo obfMethod = env.remapDescriptor(method);
/* 228 */       if (obfMethod != null) {
/* 229 */         data.put(env.getType(), obfMethod.asMethodMapping());
/*     */       }
/*     */     } 
/*     */     
/* 233 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingField> getObfFieldRecursive(MemberInfo targetMember) {
/* 243 */     return getObfEntryRecursive(targetMember);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingField> getObfField(MemberInfo field) {
/* 252 */     return getObfField(field.asFieldMapping());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingField> getObfField(MappingField field) {
/* 261 */     ObfuscationData<MappingField> data = new ObfuscationData<MappingField>();
/*     */     
/* 263 */     for (ObfuscationEnvironment env : this.environments) {
/* 264 */       MappingField obfField = env.getObfField(field);
/* 265 */       if (obfField != null) {
/* 266 */         if (obfField.getDesc() == null && field.getDesc() != null) {
/* 267 */           obfField = obfField.transform(env.remapDescriptor(field.getDesc()));
/*     */         }
/* 269 */         data.put(env.getType(), obfField);
/*     */       } 
/*     */     } 
/*     */     
/* 273 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<String> getObfClass(TypeHandle type) {
/* 282 */     return getObfClass(type.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<String> getObfClass(String className) {
/* 291 */     ObfuscationData<String> data = new ObfuscationData<String>(className);
/*     */     
/* 293 */     for (ObfuscationEnvironment env : this.environments) {
/* 294 */       String obfClass = env.getObfClass(className);
/* 295 */       if (obfClass != null) {
/* 296 */         data.put(env.getType(), obfClass);
/*     */       }
/*     */     } 
/*     */     
/* 300 */     return data;
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
/*     */   private static <T> ObfuscationData<T> applyParents(ObfuscationData<String> parents, ObfuscationData<T> members) {
/* 312 */     for (ObfuscationType type : members) {
/* 313 */       String obfClass = parents.get(type);
/* 314 */       T obfMember = members.get(type);
/* 315 */       members.put(type, (T)MemberInfo.fromMapping((IMapping)obfMember).move(obfClass).asMapping());
/*     */     } 
/* 317 */     return members;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\tools\obfuscation\ObfuscationDataProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */