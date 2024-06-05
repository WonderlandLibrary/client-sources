package net.minecraft.src;

import java.lang.reflect.*;

public class ReflectorField
{
    private ReflectorClass reflectorClass;
    private String targetFieldName;
    private boolean checked;
    private Field targetField;
    
    public ReflectorField(final ReflectorClass var1, final String var2) {
        this.reflectorClass = null;
        this.targetFieldName = null;
        this.checked = false;
        this.targetField = null;
        this.reflectorClass = var1;
        this.targetFieldName = var2;
        final Field var3 = this.getTargetField();
    }
    
    public Field getTargetField() {
        if (this.checked) {
            return this.targetField;
        }
        this.checked = true;
        final Class var1 = this.reflectorClass.getTargetClass();
        if (var1 == null) {
            return null;
        }
        try {
            this.targetField = var1.getDeclaredField(this.targetFieldName);
        }
        catch (SecurityException var2) {
            var2.printStackTrace();
        }
        catch (NoSuchFieldException var3) {
            Config.log("(Reflector) Field not present: " + var1.getName() + "." + this.targetFieldName);
        }
        return this.targetField;
    }
    
    public Object getValue() {
        return Reflector.getFieldValue(null, this);
    }
    
    public void setValue(final Object var1) {
        Reflector.setFieldValue(null, this, var1);
    }
    
    public boolean exists() {
        return this.getTargetField() != null;
    }
}
