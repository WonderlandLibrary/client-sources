package pw.stamina.causam.select;

final class ExactMatchingKeySelector implements KeySelector
{
    private final Class<?> expectedKey;
    
    ExactMatchingKeySelector(final Class<?> expectedKey) {
        this.expectedKey = expectedKey;
    }
    
    @Override
    public boolean canSelect(final Class<?> key) {
        return key == this.expectedKey;
    }
}
