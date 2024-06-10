/*     */ package org.spongepowered.asm.lib.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.spongepowered.asm.lib.AnnotationVisitor;
/*     */ import org.spongepowered.asm.lib.Attribute;
/*     */ import org.spongepowered.asm.lib.ClassVisitor;
/*     */ import org.spongepowered.asm.lib.FieldVisitor;
/*     */ import org.spongepowered.asm.lib.TypePath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FieldNode
/*     */   extends FieldVisitor
/*     */ {
/*     */   public int access;
/*     */   public String name;
/*     */   public String desc;
/*     */   public String signature;
/*     */   public Object value;
/*     */   public List<AnnotationNode> visibleAnnotations;
/*     */   public List<AnnotationNode> invisibleAnnotations;
/*     */   public List<TypeAnnotationNode> visibleTypeAnnotations;
/*     */   public List<TypeAnnotationNode> invisibleTypeAnnotations;
/*     */   public List<Attribute> attrs;
/*     */   
/*     */   public FieldNode(int access, String name, String desc, String signature, Object value) {
/* 147 */     this(327680, access, name, desc, signature, value);
/* 148 */     if (getClass() != FieldNode.class) {
/* 149 */       throw new IllegalStateException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldNode(int api, int access, String name, String desc, String signature, Object value) {
/* 179 */     super(api);
/* 180 */     this.access = access;
/* 181 */     this.name = name;
/* 182 */     this.desc = desc;
/* 183 */     this.signature = signature;
/* 184 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 194 */     AnnotationNode an = new AnnotationNode(desc);
/* 195 */     if (visible) {
/* 196 */       if (this.visibleAnnotations == null) {
/* 197 */         this.visibleAnnotations = new ArrayList<AnnotationNode>(1);
/*     */       }
/* 199 */       this.visibleAnnotations.add(an);
/*     */     } else {
/* 201 */       if (this.invisibleAnnotations == null) {
/* 202 */         this.invisibleAnnotations = new ArrayList<AnnotationNode>(1);
/*     */       }
/* 204 */       this.invisibleAnnotations.add(an);
/*     */     } 
/* 206 */     return an;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 212 */     TypeAnnotationNode an = new TypeAnnotationNode(typeRef, typePath, desc);
/* 213 */     if (visible) {
/* 214 */       if (this.visibleTypeAnnotations == null) {
/* 215 */         this.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
/*     */       }
/* 217 */       this.visibleTypeAnnotations.add(an);
/*     */     } else {
/* 219 */       if (this.invisibleTypeAnnotations == null) {
/* 220 */         this.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
/*     */       }
/* 222 */       this.invisibleTypeAnnotations.add(an);
/*     */     } 
/* 224 */     return an;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitAttribute(Attribute attr) {
/* 229 */     if (this.attrs == null) {
/* 230 */       this.attrs = new ArrayList<Attribute>(1);
/*     */     }
/* 232 */     this.attrs.add(attr);
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
/*     */   public void visitEnd() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void check(int api) {
/* 254 */     if (api == 262144) {
/* 255 */       if (this.visibleTypeAnnotations != null && this.visibleTypeAnnotations
/* 256 */         .size() > 0) {
/* 257 */         throw new RuntimeException();
/*     */       }
/* 259 */       if (this.invisibleTypeAnnotations != null && this.invisibleTypeAnnotations
/* 260 */         .size() > 0) {
/* 261 */         throw new RuntimeException();
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
/*     */   public void accept(ClassVisitor cv) {
/* 273 */     FieldVisitor fv = cv.visitField(this.access, this.name, this.desc, this.signature, this.value);
/* 274 */     if (fv == null) {
/*     */       return;
/*     */     }
/*     */     
/* 278 */     int n = (this.visibleAnnotations == null) ? 0 : this.visibleAnnotations.size(); int i;
/* 279 */     for (i = 0; i < n; i++) {
/* 280 */       AnnotationNode an = this.visibleAnnotations.get(i);
/* 281 */       an.accept(fv.visitAnnotation(an.desc, true));
/*     */     } 
/* 283 */     n = (this.invisibleAnnotations == null) ? 0 : this.invisibleAnnotations.size();
/* 284 */     for (i = 0; i < n; i++) {
/* 285 */       AnnotationNode an = this.invisibleAnnotations.get(i);
/* 286 */       an.accept(fv.visitAnnotation(an.desc, false));
/*     */     } 
/* 288 */     n = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations.size();
/* 289 */     for (i = 0; i < n; i++) {
/* 290 */       TypeAnnotationNode an = this.visibleTypeAnnotations.get(i);
/* 291 */       an.accept(fv.visitTypeAnnotation(an.typeRef, an.typePath, an.desc, true));
/*     */     } 
/*     */ 
/*     */     
/* 295 */     n = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations.size();
/* 296 */     for (i = 0; i < n; i++) {
/* 297 */       TypeAnnotationNode an = this.invisibleTypeAnnotations.get(i);
/* 298 */       an.accept(fv.visitTypeAnnotation(an.typeRef, an.typePath, an.desc, false));
/*     */     } 
/*     */     
/* 301 */     n = (this.attrs == null) ? 0 : this.attrs.size();
/* 302 */     for (i = 0; i < n; i++) {
/* 303 */       fv.visitAttribute(this.attrs.get(i));
/*     */     }
/* 305 */     fv.visitEnd();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\FieldNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */