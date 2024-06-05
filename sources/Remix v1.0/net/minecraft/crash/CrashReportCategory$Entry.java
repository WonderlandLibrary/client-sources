package net.minecraft.crash;

static class Entry
{
    private final String key;
    private final String value;
    
    public Entry(final String key, final Object value) {
        this.key = key;
        if (value == null) {
            this.value = "~~NULL~~";
        }
        else if (value instanceof Throwable) {
            final Throwable var3 = (Throwable)value;
            this.value = "~~ERROR~~ " + var3.getClass().getSimpleName() + ": " + var3.getMessage();
        }
        else {
            this.value = value.toString();
        }
    }
    
    public String getKey() {
        return this.key;
    }
    
    public String getValue() {
        return this.value;
    }
}
