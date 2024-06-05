package tk.rektsky.Module.Settings;

public class StringSetting extends Setting
{
    private int maxTexts;
    private int minTexts;
    private String value;
    private String defaultValue;
    
    @Override
    public void setValue(final Object value) {
        if (value instanceof String) {
            if (this.maxTexts == -1) {
                this.maxTexts = 34;
            }
            if (((String)value).length() < this.minTexts || ((String)value).length() > this.maxTexts) {
                return;
            }
            this.value = (String)value;
        }
    }
    
    public StringSetting(final String name, final int minTexts, final int maxTexts, final String defaultValue) {
        this.maxTexts = -1;
        this.minTexts = -1;
        this.name = name;
        this.minTexts = minTexts;
        this.maxTexts = maxTexts;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }
    
    @Override
    public String getValue() {
        return this.value;
    }
    
    public int getMaxTexts() {
        return this.maxTexts;
    }
    
    public int getMinTexts() {
        return this.minTexts;
    }
    
    @Override
    public Object getDefaultValue() {
        return this.defaultValue;
    }
}
