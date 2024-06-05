package net.minecraft.world;

static class Value
{
    private final ValueType type;
    private String valueString;
    private boolean valueBoolean;
    private int valueInteger;
    private double valueDouble;
    
    public Value(final String value, final ValueType type) {
        this.type = type;
        this.setValue(value);
    }
    
    public void setValue(final String value) {
        this.valueString = value;
        if (value != null) {
            if (value.equals("false")) {
                this.valueBoolean = false;
                return;
            }
            if (value.equals("true")) {
                this.valueBoolean = true;
                return;
            }
        }
        this.valueBoolean = Boolean.parseBoolean(value);
        this.valueInteger = (this.valueBoolean ? 1 : 0);
        try {
            this.valueInteger = Integer.parseInt(value);
        }
        catch (NumberFormatException ex) {}
        try {
            this.valueDouble = Double.parseDouble(value);
        }
        catch (NumberFormatException ex2) {}
    }
    
    public String getGameRuleStringValue() {
        return this.valueString;
    }
    
    public boolean getGameRuleBooleanValue() {
        return this.valueBoolean;
    }
    
    public int getInt() {
        return this.valueInteger;
    }
    
    public ValueType getType() {
        return this.type;
    }
}
