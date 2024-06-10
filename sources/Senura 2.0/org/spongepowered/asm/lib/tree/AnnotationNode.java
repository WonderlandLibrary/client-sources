/*     */ package org.spongepowered.asm.lib.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.spongepowered.asm.lib.AnnotationVisitor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnnotationNode
/*     */   extends AnnotationVisitor
/*     */ {
/*     */   public String desc;
/*     */   public List<Object> values;
/*     */   
/*     */   public AnnotationNode(String desc) {
/*  74 */     this(327680, desc);
/*  75 */     if (getClass() != AnnotationNode.class) {
/*  76 */       throw new IllegalStateException();
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
/*     */   public AnnotationNode(int api, String desc) {
/*  90 */     super(api);
/*  91 */     this.desc = desc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AnnotationNode(List<Object> values) {
/* 101 */     super(327680);
/* 102 */     this.values = values;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visit(String name, Object value) {
/* 111 */     if (this.values == null) {
/* 112 */       this.values = new ArrayList((this.desc != null) ? 2 : 1);
/*     */     }
/* 114 */     if (this.desc != null) {
/* 115 */       this.values.add(name);
/*     */     }
/* 117 */     if (value instanceof byte[]) {
/* 118 */       byte[] v = (byte[])value;
/* 119 */       ArrayList<Byte> l = new ArrayList<Byte>(v.length);
/* 120 */       for (byte b : v) {
/* 121 */         l.add(Byte.valueOf(b));
/*     */       }
/* 123 */       this.values.add(l);
/* 124 */     } else if (value instanceof boolean[]) {
/* 125 */       boolean[] v = (boolean[])value;
/* 126 */       ArrayList<Boolean> l = new ArrayList<Boolean>(v.length);
/* 127 */       for (boolean b : v) {
/* 128 */         l.add(Boolean.valueOf(b));
/*     */       }
/* 130 */       this.values.add(l);
/* 131 */     } else if (value instanceof short[]) {
/* 132 */       short[] v = (short[])value;
/* 133 */       ArrayList<Short> l = new ArrayList<Short>(v.length);
/* 134 */       for (short s : v) {
/* 135 */         l.add(Short.valueOf(s));
/*     */       }
/* 137 */       this.values.add(l);
/* 138 */     } else if (value instanceof char[]) {
/* 139 */       char[] v = (char[])value;
/* 140 */       ArrayList<Character> l = new ArrayList<Character>(v.length);
/* 141 */       for (char c : v) {
/* 142 */         l.add(Character.valueOf(c));
/*     */       }
/* 144 */       this.values.add(l);
/* 145 */     } else if (value instanceof int[]) {
/* 146 */       int[] v = (int[])value;
/* 147 */       ArrayList<Integer> l = new ArrayList<Integer>(v.length);
/* 148 */       for (int i : v) {
/* 149 */         l.add(Integer.valueOf(i));
/*     */       }
/* 151 */       this.values.add(l);
/* 152 */     } else if (value instanceof long[]) {
/* 153 */       long[] v = (long[])value;
/* 154 */       ArrayList<Long> l = new ArrayList<Long>(v.length);
/* 155 */       for (long lng : v) {
/* 156 */         l.add(Long.valueOf(lng));
/*     */       }
/* 158 */       this.values.add(l);
/* 159 */     } else if (value instanceof float[]) {
/* 160 */       float[] v = (float[])value;
/* 161 */       ArrayList<Float> l = new ArrayList<Float>(v.length);
/* 162 */       for (float f : v) {
/* 163 */         l.add(Float.valueOf(f));
/*     */       }
/* 165 */       this.values.add(l);
/* 166 */     } else if (value instanceof double[]) {
/* 167 */       double[] v = (double[])value;
/* 168 */       ArrayList<Double> l = new ArrayList<Double>(v.length);
/* 169 */       for (double d : v) {
/* 170 */         l.add(Double.valueOf(d));
/*     */       }
/* 172 */       this.values.add(l);
/*     */     } else {
/* 174 */       this.values.add(value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnum(String name, String desc, String value) {
/* 181 */     if (this.values == null) {
/* 182 */       this.values = new ArrayList((this.desc != null) ? 2 : 1);
/*     */     }
/* 184 */     if (this.desc != null) {
/* 185 */       this.values.add(name);
/*     */     }
/* 187 */     this.values.add(new String[] { desc, value });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String name, String desc) {
/* 193 */     if (this.values == null) {
/* 194 */       this.values = new ArrayList((this.desc != null) ? 2 : 1);
/*     */     }
/* 196 */     if (this.desc != null) {
/* 197 */       this.values.add(name);
/*     */     }
/* 199 */     AnnotationNode annotation = new AnnotationNode(desc);
/* 200 */     this.values.add(annotation);
/* 201 */     return annotation;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitArray(String name) {
/* 206 */     if (this.values == null) {
/* 207 */       this.values = new ArrayList((this.desc != null) ? 2 : 1);
/*     */     }
/* 209 */     if (this.desc != null) {
/* 210 */       this.values.add(name);
/*     */     }
/* 212 */     List<Object> array = new ArrayList();
/* 213 */     this.values.add(array);
/* 214 */     return new AnnotationNode(array);
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
/*     */   public void check(int api) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(AnnotationVisitor av) {
/* 246 */     if (av != null) {
/* 247 */       if (this.values != null) {
/* 248 */         for (int i = 0; i < this.values.size(); i += 2) {
/* 249 */           String name = (String)this.values.get(i);
/* 250 */           Object value = this.values.get(i + 1);
/* 251 */           accept(av, name, value);
/*     */         } 
/*     */       }
/* 254 */       av.visitEnd();
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
/*     */   static void accept(AnnotationVisitor av, String name, Object value) {
/* 270 */     if (av != null)
/* 271 */       if (value instanceof String[]) {
/* 272 */         String[] typeconst = (String[])value;
/* 273 */         av.visitEnum(name, typeconst[0], typeconst[1]);
/* 274 */       } else if (value instanceof AnnotationNode) {
/* 275 */         AnnotationNode an = (AnnotationNode)value;
/* 276 */         an.accept(av.visitAnnotation(name, an.desc));
/* 277 */       } else if (value instanceof List) {
/* 278 */         AnnotationVisitor v = av.visitArray(name);
/* 279 */         if (v != null) {
/* 280 */           List<?> array = (List)value;
/* 281 */           for (int j = 0; j < array.size(); j++) {
/* 282 */             accept(v, null, array.get(j));
/*     */           }
/* 284 */           v.visitEnd();
/*     */         } 
/*     */       } else {
/* 287 */         av.visit(name, value);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\AnnotationNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */