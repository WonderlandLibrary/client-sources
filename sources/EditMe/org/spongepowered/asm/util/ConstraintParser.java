package org.spongepowered.asm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;

public final class ConstraintParser {
   private ConstraintParser() {
   }

   public static ConstraintParser.Constraint parse(String var0) {
      if (var0 != null && var0.length() != 0) {
         String[] var1 = var0.replaceAll("\\s", "").toUpperCase().split(";");
         ConstraintParser.Constraint var2 = null;
         String[] var3 = var1;
         int var4 = var1.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String var6 = var3[var5];
            ConstraintParser.Constraint var7 = new ConstraintParser.Constraint(var6);
            if (var2 == null) {
               var2 = var7;
            } else {
               var2.append(var7);
            }
         }

         return var2 != null ? var2 : ConstraintParser.Constraint.NONE;
      } else {
         return ConstraintParser.Constraint.NONE;
      }
   }

   public static ConstraintParser.Constraint parse(AnnotationNode var0) {
      String var1 = (String)Annotations.getValue(var0, "constraints", (Object)"");
      return parse(var1);
   }

   public static class Constraint {
      public static final ConstraintParser.Constraint NONE = new ConstraintParser.Constraint();
      private static final Pattern pattern = Pattern.compile("^([A-Z0-9\\-_\\.]+)\\((?:(<|<=|>|>=|=)?([0-9]+)(<|(-)([0-9]+)?|>|(\\+)([0-9]+)?)?)?\\)$");
      private final String expr;
      private String token;
      private String[] constraint;
      private int min = Integer.MIN_VALUE;
      private int max = Integer.MAX_VALUE;
      private ConstraintParser.Constraint next;

      Constraint(String var1) {
         this.expr = var1;
         Matcher var2 = pattern.matcher(var1);
         if (!var2.matches()) {
            throw new InvalidConstraintException("Constraint syntax was invalid parsing: " + this.expr);
         } else {
            this.token = var2.group(1);
            this.constraint = new String[]{var2.group(2), var2.group(3), var2.group(4), var2.group(5), var2.group(6), var2.group(7), var2.group(8)};
            this.parse();
         }
      }

      private Constraint() {
         this.expr = null;
         this.token = "*";
         this.constraint = new String[0];
      }

      private void parse() {
         if (this.has(1)) {
            this.max = this.min = this.val(1);
            boolean var1 = this.has(0);
            if (this.has(4)) {
               if (var1) {
                  throw new InvalidConstraintException("Unexpected modifier '" + this.elem(0) + "' in " + this.expr + " parsing range");
               } else {
                  this.max = this.val(4);
                  if (this.max < this.min) {
                     throw new InvalidConstraintException("Invalid range specified '" + this.max + "' is less than " + this.min + " in " + this.expr);
                  }
               }
            } else if (this.has(6)) {
               if (var1) {
                  throw new InvalidConstraintException("Unexpected modifier '" + this.elem(0) + "' in " + this.expr + " parsing range");
               } else {
                  this.max = this.min + this.val(6);
               }
            } else {
               String var2;
               if (var1) {
                  if (this.has(3)) {
                     throw new InvalidConstraintException("Unexpected trailing modifier '" + this.elem(3) + "' in " + this.expr);
                  }

                  var2 = this.elem(0);
                  if (">".equals(var2)) {
                     ++this.min;
                     this.max = Integer.MAX_VALUE;
                  } else if (">=".equals(var2)) {
                     this.max = Integer.MAX_VALUE;
                  } else if ("<".equals(var2)) {
                     this.max = --this.min;
                     this.min = Integer.MIN_VALUE;
                  } else if ("<=".equals(var2)) {
                     this.max = this.min;
                     this.min = Integer.MIN_VALUE;
                  }
               } else if (this.has(2)) {
                  var2 = this.elem(2);
                  if ("<".equals(var2)) {
                     this.max = this.min;
                     this.min = Integer.MIN_VALUE;
                  } else {
                     this.max = Integer.MAX_VALUE;
                  }
               }

            }
         }
      }

      private boolean has(int var1) {
         return this.constraint[var1] != null;
      }

      private String elem(int var1) {
         return this.constraint[var1];
      }

      private int val(int var1) {
         return this.constraint[var1] != null ? Integer.parseInt(this.constraint[var1]) : 0;
      }

      void append(ConstraintParser.Constraint var1) {
         if (this.next != null) {
            this.next.append(var1);
         } else {
            this.next = var1;
         }
      }

      public String getToken() {
         return this.token;
      }

      public int getMin() {
         return this.min;
      }

      public int getMax() {
         return this.max;
      }

      public void check(ITokenProvider var1) throws ConstraintViolationException {
         if (this != NONE) {
            Integer var2 = var1.getToken(this.token);
            if (var2 == null) {
               throw new ConstraintViolationException("The token '" + this.token + "' could not be resolved in " + var1, this);
            }

            if (var2 < this.min) {
               throw new ConstraintViolationException("Token '" + this.token + "' has a value (" + var2 + ") which is less than the minimum value " + this.min + " in " + var1, this, var2);
            }

            if (var2 > this.max) {
               throw new ConstraintViolationException("Token '" + this.token + "' has a value (" + var2 + ") which is greater than the maximum value " + this.max + " in " + var1, this, var2);
            }
         }

         if (this.next != null) {
            this.next.check(var1);
         }

      }

      public String getRangeHumanReadable() {
         if (this.min == Integer.MIN_VALUE && this.max == Integer.MAX_VALUE) {
            return "ANY VALUE";
         } else if (this.min == Integer.MIN_VALUE) {
            return String.format("less than or equal to %d", this.max);
         } else if (this.max == Integer.MAX_VALUE) {
            return String.format("greater than or equal to %d", this.min);
         } else {
            return this.min == this.max ? String.format("%d", this.min) : String.format("between %d and %d", this.min, this.max);
         }
      }

      public String toString() {
         return String.format("Constraint(%s [%d-%d])", this.token, this.min, this.max);
      }
   }
}
