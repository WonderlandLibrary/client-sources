/*     */ package org.spongepowered.asm.util;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.util.throwables.ConstraintViolationException;
/*     */ import org.spongepowered.asm.util.throwables.InvalidConstraintException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ConstraintParser
/*     */ {
/*     */   public static class Constraint
/*     */   {
/*  94 */     public static final Constraint NONE = new Constraint();
/*     */ 
/*     */     
/*  97 */     private static final Pattern pattern = Pattern.compile("^([A-Z0-9\\-_\\.]+)\\((?:(<|<=|>|>=|=)?([0-9]+)(<|(-)([0-9]+)?|>|(\\+)([0-9]+)?)?)?\\)$");
/*     */     
/*     */     private final String expr;
/*     */     
/*     */     private String token;
/*     */     
/*     */     private String[] constraint;
/*     */     
/* 105 */     private int min = Integer.MIN_VALUE;
/*     */     
/* 107 */     private int max = Integer.MAX_VALUE;
/*     */     
/*     */     private Constraint next;
/*     */     
/*     */     Constraint(String expr) {
/* 112 */       this.expr = expr;
/* 113 */       Matcher matcher = pattern.matcher(expr);
/* 114 */       if (!matcher.matches()) {
/* 115 */         throw new InvalidConstraintException("Constraint syntax was invalid parsing: " + this.expr);
/*     */       }
/*     */       
/* 118 */       this.token = matcher.group(1);
/* 119 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 126 */         .constraint = new String[] { matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5), matcher.group(6), matcher.group(7), matcher.group(8) };
/*     */ 
/*     */       
/* 129 */       parse();
/*     */     }
/*     */     
/*     */     private Constraint() {
/* 133 */       this.expr = null;
/* 134 */       this.token = "*";
/* 135 */       this.constraint = new String[0];
/*     */     }
/*     */     
/*     */     private void parse() {
/* 139 */       if (!has(1)) {
/*     */         return;
/*     */       }
/*     */       
/* 143 */       this.max = this.min = val(1);
/* 144 */       boolean hasModifier = has(0);
/*     */       
/* 146 */       if (has(4)) {
/* 147 */         if (hasModifier) {
/* 148 */           throw new InvalidConstraintException("Unexpected modifier '" + elem(0) + "' in " + this.expr + " parsing range");
/*     */         }
/* 150 */         this.max = val(4);
/* 151 */         if (this.max < this.min)
/* 152 */           throw new InvalidConstraintException("Invalid range specified '" + this.max + "' is less than " + this.min + " in " + this.expr); 
/*     */         return;
/*     */       } 
/* 155 */       if (has(6)) {
/* 156 */         if (hasModifier) {
/* 157 */           throw new InvalidConstraintException("Unexpected modifier '" + elem(0) + "' in " + this.expr + " parsing range");
/*     */         }
/* 159 */         this.max = this.min + val(6);
/*     */         
/*     */         return;
/*     */       } 
/* 163 */       if (hasModifier) {
/* 164 */         if (has(3)) {
/* 165 */           throw new InvalidConstraintException("Unexpected trailing modifier '" + elem(3) + "' in " + this.expr);
/*     */         }
/* 167 */         String leading = elem(0);
/* 168 */         if (">".equals(leading)) {
/* 169 */           this.min++;
/* 170 */           this.max = Integer.MAX_VALUE;
/* 171 */         } else if (">=".equals(leading)) {
/* 172 */           this.max = Integer.MAX_VALUE;
/* 173 */         } else if ("<".equals(leading)) {
/* 174 */           this.max = --this.min;
/* 175 */           this.min = Integer.MIN_VALUE;
/* 176 */         } else if ("<=".equals(leading)) {
/* 177 */           this.max = this.min;
/* 178 */           this.min = Integer.MIN_VALUE;
/*     */         } 
/* 180 */       } else if (has(2)) {
/* 181 */         String trailing = elem(2);
/* 182 */         if ("<".equals(trailing)) {
/* 183 */           this.max = this.min;
/* 184 */           this.min = Integer.MIN_VALUE;
/*     */         } else {
/* 186 */           this.max = Integer.MAX_VALUE;
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private boolean has(int index) {
/* 192 */       return (this.constraint[index] != null);
/*     */     }
/*     */     
/*     */     private String elem(int index) {
/* 196 */       return this.constraint[index];
/*     */     }
/*     */     
/*     */     private int val(int index) {
/* 200 */       return (this.constraint[index] != null) ? Integer.parseInt(this.constraint[index]) : 0;
/*     */     }
/*     */     
/*     */     void append(Constraint next) {
/* 204 */       if (this.next != null) {
/* 205 */         this.next.append(next);
/*     */         return;
/*     */       } 
/* 208 */       this.next = next;
/*     */     }
/*     */     
/*     */     public String getToken() {
/* 212 */       return this.token;
/*     */     }
/*     */     
/*     */     public int getMin() {
/* 216 */       return this.min;
/*     */     }
/*     */     
/*     */     public int getMax() {
/* 220 */       return this.max;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void check(ITokenProvider environment) throws ConstraintViolationException {
/* 231 */       if (this != NONE) {
/* 232 */         Integer value = environment.getToken(this.token);
/* 233 */         if (value == null) {
/* 234 */           throw new ConstraintViolationException("The token '" + this.token + "' could not be resolved in " + environment, this);
/*     */         }
/* 236 */         if (value.intValue() < this.min) {
/* 237 */           throw new ConstraintViolationException("Token '" + this.token + "' has a value (" + value + ") which is less than the minimum value " + this.min + " in " + environment, this, value
/* 238 */               .intValue());
/*     */         }
/* 240 */         if (value.intValue() > this.max) {
/* 241 */           throw new ConstraintViolationException("Token '" + this.token + "' has a value (" + value + ") which is greater than the maximum value " + this.max + " in " + environment, this, value
/* 242 */               .intValue());
/*     */         }
/*     */       } 
/* 245 */       if (this.next != null) {
/* 246 */         this.next.check(environment);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getRangeHumanReadable() {
/* 255 */       if (this.min == Integer.MIN_VALUE && this.max == Integer.MAX_VALUE)
/* 256 */         return "ANY VALUE"; 
/* 257 */       if (this.min == Integer.MIN_VALUE)
/* 258 */         return String.format("less than or equal to %d", new Object[] { Integer.valueOf(this.max) }); 
/* 259 */       if (this.max == Integer.MAX_VALUE)
/* 260 */         return String.format("greater than or equal to %d", new Object[] { Integer.valueOf(this.min) }); 
/* 261 */       if (this.min == this.max) {
/* 262 */         return String.format("%d", new Object[] { Integer.valueOf(this.min) });
/*     */       }
/* 264 */       return String.format("between %d and %d", new Object[] { Integer.valueOf(this.min), Integer.valueOf(this.max) });
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 269 */       return String.format("Constraint(%s [%d-%d])", new Object[] { this.token, Integer.valueOf(this.min), Integer.valueOf(this.max) });
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
/*     */   public static Constraint parse(String expr) {
/* 285 */     if (expr == null || expr.length() == 0) {
/* 286 */       return Constraint.NONE;
/*     */     }
/*     */     
/* 289 */     String[] exprs = expr.replaceAll("\\s", "").toUpperCase().split(";");
/* 290 */     Constraint head = null;
/* 291 */     for (String subExpr : exprs) {
/* 292 */       Constraint next = new Constraint(subExpr);
/* 293 */       if (head == null) {
/* 294 */         head = next;
/*     */       } else {
/* 296 */         head.append(next);
/*     */       } 
/*     */     } 
/*     */     
/* 300 */     return (head != null) ? head : Constraint.NONE;
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
/*     */   public static Constraint parse(AnnotationNode annotation) {
/* 313 */     String constraints = Annotations.<String>getValue(annotation, "constraints", "");
/* 314 */     return parse(constraints);
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\as\\util\ConstraintParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */