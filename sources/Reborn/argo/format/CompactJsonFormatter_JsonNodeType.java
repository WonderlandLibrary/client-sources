package argo.format;

import argo.jdom.*;

class CompactJsonFormatter_JsonNodeType
{
    static final int[] enumJsonNodeTypeMappingArray;
    
    static {
        enumJsonNodeTypeMappingArray = new int[JsonNodeType.values().length];
        try {
            CompactJsonFormatter_JsonNodeType.enumJsonNodeTypeMappingArray[JsonNodeType.ARRAY.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            CompactJsonFormatter_JsonNodeType.enumJsonNodeTypeMappingArray[JsonNodeType.OBJECT.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            CompactJsonFormatter_JsonNodeType.enumJsonNodeTypeMappingArray[JsonNodeType.STRING.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            CompactJsonFormatter_JsonNodeType.enumJsonNodeTypeMappingArray[JsonNodeType.NUMBER.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            CompactJsonFormatter_JsonNodeType.enumJsonNodeTypeMappingArray[JsonNodeType.FALSE.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            CompactJsonFormatter_JsonNodeType.enumJsonNodeTypeMappingArray[JsonNodeType.TRUE.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            CompactJsonFormatter_JsonNodeType.enumJsonNodeTypeMappingArray[JsonNodeType.NULL.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
    }
}
