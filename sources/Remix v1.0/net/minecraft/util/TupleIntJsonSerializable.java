package net.minecraft.util;

public class TupleIntJsonSerializable
{
    private int integerValue;
    private IJsonSerializable jsonSerializableValue;
    
    public int getIntegerValue() {
        return this.integerValue;
    }
    
    public void setIntegerValue(final int p_151188_1_) {
        this.integerValue = p_151188_1_;
    }
    
    public IJsonSerializable getJsonSerializableValue() {
        return this.jsonSerializableValue;
    }
    
    public void setJsonSerializableValue(final IJsonSerializable p_151190_1_) {
        this.jsonSerializableValue = p_151190_1_;
    }
}
