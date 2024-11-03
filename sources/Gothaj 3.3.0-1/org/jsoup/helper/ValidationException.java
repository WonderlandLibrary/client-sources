package org.jsoup.helper;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends IllegalArgumentException {
   public static final String Validator = Validate.class.getName();

   public ValidationException(String msg) {
      super(msg);
   }

   @Override
   public synchronized Throwable fillInStackTrace() {
      super.fillInStackTrace();
      StackTraceElement[] stackTrace = this.getStackTrace();
      List<StackTraceElement> filteredTrace = new ArrayList<>();

      for (StackTraceElement trace : stackTrace) {
         if (!trace.getClassName().equals(Validator)) {
            filteredTrace.add(trace);
         }
      }

      this.setStackTrace(filteredTrace.toArray(new StackTraceElement[0]));
      return this;
   }
}
