package org.yaml.snakeyaml.error;

public class MarkedYAMLException extends YAMLException {
   private static final long serialVersionUID = -9119388488683035101L;
   private String context;
   private Mark contextMark;
   private String problem;
   private Mark problemMark;
   private String note;

   protected MarkedYAMLException(String var1, Mark var2, String var3, Mark var4, String var5) {
      this(var1, var2, var3, var4, var5, (Throwable)null);
   }

   protected MarkedYAMLException(String var1, Mark var2, String var3, Mark var4, String var5, Throwable var6) {
      super(var1 + "; " + var3 + "; " + var4, var6);
      this.context = var1;
      this.contextMark = var2;
      this.problem = var3;
      this.problemMark = var4;
      this.note = var5;
   }

   protected MarkedYAMLException(String var1, Mark var2, String var3, Mark var4) {
      this(var1, var2, var3, var4, (String)null, (Throwable)null);
   }

   protected MarkedYAMLException(String var1, Mark var2, String var3, Mark var4, Throwable var5) {
      this(var1, var2, var3, var4, (String)null, var5);
   }

   public String getMessage() {
      return this.toString();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      if (this.context != null) {
         var1.append(this.context);
         var1.append("\n");
      }

      if (this.contextMark != null && (this.problem == null || this.problemMark == null || this.contextMark.getName().equals(this.problemMark.getName()) || this.contextMark.getLine() != this.problemMark.getLine() || this.contextMark.getColumn() != this.problemMark.getColumn())) {
         var1.append(this.contextMark.toString());
         var1.append("\n");
      }

      if (this.problem != null) {
         var1.append(this.problem);
         var1.append("\n");
      }

      if (this.problemMark != null) {
         var1.append(this.problemMark.toString());
         var1.append("\n");
      }

      if (this.note != null) {
         var1.append(this.note);
         var1.append("\n");
      }

      return var1.toString();
   }

   public String getContext() {
      return this.context;
   }

   public Mark getContextMark() {
      return this.contextMark;
   }

   public String getProblem() {
      return this.problem;
   }

   public Mark getProblemMark() {
      return this.problemMark;
   }
}
