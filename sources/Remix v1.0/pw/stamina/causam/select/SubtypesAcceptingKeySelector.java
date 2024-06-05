package pw.stamina.causam.select;

final class SubtypesAcceptingKeySelector implements KeySelector
{
    private final Class<?> superClass;
    
    SubtypesAcceptingKeySelector(final Class<?> superClass) {
        this.superClass = superClass;
    }
    
    @Override
    public boolean canSelect(final Class<?> key) {
        return this.superClass.isAssignableFrom(key);
    }
}
