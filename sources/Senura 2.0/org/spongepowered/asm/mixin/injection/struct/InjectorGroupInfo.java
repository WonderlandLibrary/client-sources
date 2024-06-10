/*     */ package org.spongepowered.asm.mixin.injection.struct;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.injection.Group;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InjectionValidationException;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InjectorGroupInfo
/*     */ {
/*     */   private final String name;
/*     */   
/*     */   public static final class Map
/*     */     extends HashMap<String, InjectorGroupInfo>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*  52 */     private static final InjectorGroupInfo NO_GROUP = new InjectorGroupInfo("NONE", true);
/*     */ 
/*     */     
/*     */     public InjectorGroupInfo get(Object key) {
/*  56 */       return forName(key.toString());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InjectorGroupInfo forName(String name) {
/*  67 */       InjectorGroupInfo value = super.get(name);
/*  68 */       if (value == null) {
/*  69 */         value = new InjectorGroupInfo(name);
/*  70 */         put(name, value);
/*     */       } 
/*  72 */       return value;
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
/*     */     public InjectorGroupInfo parseGroup(MethodNode method, String defaultGroup) {
/*  84 */       return parseGroup(Annotations.getInvisible(method, Group.class), defaultGroup);
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
/*     */     public InjectorGroupInfo parseGroup(AnnotationNode annotation, String defaultGroup) {
/*  96 */       if (annotation == null) {
/*  97 */         return NO_GROUP;
/*     */       }
/*     */       
/* 100 */       String name = (String)Annotations.getValue(annotation, "name");
/* 101 */       if (name == null || name.isEmpty()) {
/* 102 */         name = defaultGroup;
/*     */       }
/* 104 */       InjectorGroupInfo groupInfo = forName(name);
/*     */       
/* 106 */       Integer min = (Integer)Annotations.getValue(annotation, "min");
/* 107 */       if (min != null && min.intValue() != -1) {
/* 108 */         groupInfo.setMinRequired(min.intValue());
/*     */       }
/*     */       
/* 111 */       Integer max = (Integer)Annotations.getValue(annotation, "max");
/* 112 */       if (max != null && max.intValue() != -1) {
/* 113 */         groupInfo.setMaxAllowed(max.intValue());
/*     */       }
/*     */       
/* 116 */       return groupInfo;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void validateAll() throws InjectionValidationException {
/* 125 */       for (InjectorGroupInfo group : values()) {
/* 126 */         group.validate();
/*     */       }
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
/*     */ 
/*     */   
/* 140 */   private final List<InjectionInfo> members = new ArrayList<InjectionInfo>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean isDefault;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   private int minCallbackCount = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 155 */   private int maxCallbackCount = Integer.MAX_VALUE;
/*     */   
/*     */   public InjectorGroupInfo(String name) {
/* 158 */     this(name, false);
/*     */   }
/*     */   
/*     */   InjectorGroupInfo(String name, boolean flag) {
/* 162 */     this.name = name;
/* 163 */     this.isDefault = flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 168 */     return String.format("@Group(name=%s, min=%d, max=%d)", new Object[] { getName(), Integer.valueOf(getMinRequired()), Integer.valueOf(getMaxAllowed()) });
/*     */   }
/*     */   
/*     */   public boolean isDefault() {
/* 172 */     return this.isDefault;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 176 */     return this.name;
/*     */   }
/*     */   
/*     */   public int getMinRequired() {
/* 180 */     return Math.max(this.minCallbackCount, 1);
/*     */   }
/*     */   
/*     */   public int getMaxAllowed() {
/* 184 */     return Math.min(this.maxCallbackCount, 2147483647);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<InjectionInfo> getMembers() {
/* 193 */     return Collections.unmodifiableCollection(this.members);
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
/*     */   public void setMinRequired(int min) {
/* 205 */     if (min < 1) {
/* 206 */       throw new IllegalArgumentException("Cannot set zero or negative value for injector group min count. Attempted to set min=" + min + " on " + this);
/*     */     }
/*     */     
/* 209 */     if (this.minCallbackCount > 0 && this.minCallbackCount != min) {
/* 210 */       LogManager.getLogger("mixin").warn("Conflicting min value '{}' on @Group({}), previously specified {}", new Object[] { Integer.valueOf(min), this.name, 
/* 211 */             Integer.valueOf(this.minCallbackCount) });
/*     */     }
/* 213 */     this.minCallbackCount = Math.max(this.minCallbackCount, min);
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
/*     */   public void setMaxAllowed(int max) {
/* 225 */     if (max < 1) {
/* 226 */       throw new IllegalArgumentException("Cannot set zero or negative value for injector group max count. Attempted to set max=" + max + " on " + this);
/*     */     }
/*     */     
/* 229 */     if (this.maxCallbackCount < Integer.MAX_VALUE && this.maxCallbackCount != max) {
/* 230 */       LogManager.getLogger("mixin").warn("Conflicting max value '{}' on @Group({}), previously specified {}", new Object[] { Integer.valueOf(max), this.name, 
/* 231 */             Integer.valueOf(this.maxCallbackCount) });
/*     */     }
/* 233 */     this.maxCallbackCount = Math.min(this.maxCallbackCount, max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InjectorGroupInfo add(InjectionInfo member) {
/* 243 */     this.members.add(member);
/* 244 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InjectorGroupInfo validate() throws InjectionValidationException {
/* 254 */     if (this.members.size() == 0)
/*     */     {
/* 256 */       return this;
/*     */     }
/*     */     
/* 259 */     int total = 0;
/* 260 */     for (InjectionInfo member : this.members) {
/* 261 */       total += member.getInjectedCallbackCount();
/*     */     }
/*     */     
/* 264 */     int min = getMinRequired();
/* 265 */     int max = getMaxAllowed();
/* 266 */     if (total < min)
/* 267 */       throw new InjectionValidationException(this, String.format("expected %d invocation(s) but only %d succeeded", new Object[] { Integer.valueOf(min), Integer.valueOf(total) })); 
/* 268 */     if (total > max) {
/* 269 */       throw new InjectionValidationException(this, String.format("maximum of %d invocation(s) allowed but %d succeeded", new Object[] { Integer.valueOf(max), Integer.valueOf(total) }));
/*     */     }
/*     */     
/* 272 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\injection\struct\InjectorGroupInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */