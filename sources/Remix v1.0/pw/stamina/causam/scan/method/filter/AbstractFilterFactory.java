package pw.stamina.causam.scan.method.filter;

public abstract class AbstractFilterFactory<T> implements FilterFactory<T>
{
    private final Class<T> supportedEventType;
    
    protected AbstractFilterFactory(final Class<T> supportedEventType) {
        this.supportedEventType = supportedEventType;
    }
    
    @Override
    public final Class<T> getSupportedEventType() {
        return this.supportedEventType;
    }
}
