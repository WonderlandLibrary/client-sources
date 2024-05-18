package org.spongepowered.asm.mixin.injection.callback;

public enum LocalCapture
{
    NO_CAPTURE(false, false), 
    PRINT(false, true), 
    CAPTURE_FAILSOFT, 
    CAPTURE_FAILHARD, 
    CAPTURE_FAILEXCEPTION;
    
    private final boolean captureLocals;
    private final boolean printLocals;
    private static final LocalCapture[] $VALUES;
    
    public static LocalCapture[] values() {
        return LocalCapture.$VALUES.clone();
    }
    
    public static LocalCapture valueOf(final String s) {
        return Enum.valueOf(LocalCapture.class, s);
    }
    
    private LocalCapture() {
        this(true, false);
    }
    
    private LocalCapture(final boolean captureLocals, final boolean printLocals) {
        this.captureLocals = captureLocals;
        this.printLocals = printLocals;
    }
    
    boolean isCaptureLocals() {
        return this.captureLocals;
    }
    
    boolean isPrintLocals() {
        return this.printLocals;
    }
    
    static {
        $VALUES = new LocalCapture[] { LocalCapture.NO_CAPTURE, LocalCapture.PRINT, LocalCapture.CAPTURE_FAILSOFT, LocalCapture.CAPTURE_FAILHARD, LocalCapture.CAPTURE_FAILEXCEPTION };
    }
}
