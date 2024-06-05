package optfine;

import java.lang.reflect.*;

public class ReflectorField
{
    private ReflectorClass reflectorClass;
    private String targetFieldName;
    private boolean checked;
    private Field targetField;
    
    public ReflectorField(final ReflectorClass p_i58_1_, final String p_i58_2_) {
        this.reflectorClass = null;
        this.targetFieldName = null;
        this.checked = false;
        this.targetField = null;
        this.reflectorClass = p_i58_1_;
        this.targetFieldName = p_i58_2_;
        final Field field = this.getTargetField();
    }
    
    public Field getTargetField() {
        if (this.checked) {
            return this.targetField;
        }
        this.checked = true;
        final Class oclass = this.reflectorClass.getTargetClass();
        if (oclass == null) {
            return null;
        }
        try {
            this.targetField = oclass.getDeclaredField(this.targetFieldName);
            if (!this.targetField.isAccessible()) {
                this.targetField.setAccessible(true);
            }
        }
        catch (SecurityException securityexception) {
            securityexception.printStackTrace();
        }
        catch (NoSuchFieldException var4) {
            Config.log("(Reflector) Field not present: " + oclass.getName() + "." + this.targetFieldName);
        }
        return this.targetField;
    }
    
    public Object getValue() {
        return Reflector.getFieldValue(null, this);
    }
    
    public void setValue(final Object p_setValue_1_) {
        Reflector.setFieldValue(null, this, p_setValue_1_);
    }
    
    public boolean exists() {
        return this.checked ? (this.targetField != null) : (this.getTargetField() != null);
    }
}
