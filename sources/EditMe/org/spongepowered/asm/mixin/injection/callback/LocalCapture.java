package org.spongepowered.asm.mixin.injection.callback;

public enum LocalCapture {
   NO_CAPTURE(false, false),
   PRINT(false, true),
   CAPTURE_FAILSOFT,
   CAPTURE_FAILHARD,
   CAPTURE_FAILEXCEPTION;

   private final boolean captureLocals;
   private final boolean printLocals;
   private static final LocalCapture[] $VALUES = new LocalCapture[]{NO_CAPTURE, PRINT, CAPTURE_FAILSOFT, CAPTURE_FAILHARD, CAPTURE_FAILEXCEPTION};

   private LocalCapture() {
      this(true, false);
   }

   private LocalCapture(boolean var3, boolean var4) {
      this.captureLocals = var3;
      this.printLocals = var4;
   }

   boolean isCaptureLocals() {
      return this.captureLocals;
   }

   boolean isPrintLocals() {
      return this.printLocals;
   }
}
