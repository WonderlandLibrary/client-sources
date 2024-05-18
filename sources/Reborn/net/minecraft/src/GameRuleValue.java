package net.minecraft.src;

class GameRuleValue
{
    private String valueString;
    private boolean valueBoolean;
    private int valueInteger;
    private double valueDouble;
    
    public GameRuleValue(final String par1Str) {
        this.setValue(par1Str);
    }
    
    public void setValue(final String par1Str) {
        this.valueString = par1Str;
        this.valueBoolean = Boolean.parseBoolean(par1Str);
        try {
            this.valueInteger = Integer.parseInt(par1Str);
        }
        catch (NumberFormatException ex) {}
        try {
            this.valueDouble = Double.parseDouble(par1Str);
        }
        catch (NumberFormatException ex2) {}
    }
    
    public String getGameRuleStringValue() {
        return this.valueString;
    }
    
    public boolean getGameRuleBooleanValue() {
        return this.valueBoolean;
    }
}
