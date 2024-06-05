package ClassSub;

public class Class19 extends RuntimeException
{
    public static final int EXCEPTION_TYPE_EMPTY = -1;
    public static final int EXCEPTION_TYPE_VOID = 0;
    public static final int EXCEPTION_TYPE_OBJECT = 1;
    public static final int EXCEPTION_TYPE_FUNCTION = 2;
    public static final int EXCEPTION_TYPE_STRING = 3;
    public static final int EXCEPTION_TYPE_NUMBER = 4;
    public static final int EXCEPTION_TYPE_BOOLEAN = 5;
    public static final int EXCEPTION_TYPE_ERROR = 6;
    protected String message;
    protected String filename;
    protected int lineno;
    protected String source;
    protected int tokenIndex;
    private int wrappedExceptionType;
    private Object wrappedException;
    
    
    public Class19() {
        this((String)null);
    }
    
    public Class19(final String s) {
        this(s, null, -1, null, -1);
    }
    
    public Class19(final String message, final String filename, final int lineno, final String source, final int tokenIndex) {
        super(message);
        this.message = null;
        this.filename = null;
        this.lineno = -1;
        this.source = null;
        this.tokenIndex = -1;
        this.wrappedExceptionType = -1;
        this.wrappedException = null;
        this.message = message;
        this.filename = filename;
        this.lineno = lineno;
        this.source = source;
        this.tokenIndex = tokenIndex;
        this.wrappedExceptionType = -1;
    }
    
    public Class19(final int wrappedExceptionType, final Object wrappedException) {
        this();
        this.wrappedExceptionType = wrappedExceptionType;
        this.wrappedException = wrappedException;
    }
    
    public int getWrappedExceptionType() {
        return this.wrappedExceptionType;
    }
    
    public Object getWrappedException() {
        return this.wrappedException;
    }
}
