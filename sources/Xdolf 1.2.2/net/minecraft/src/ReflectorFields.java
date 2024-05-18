package net.minecraft.src;

public class ReflectorFields
{
    private ReflectorClass reflectorClass;
    private Class fieldType;
    private int fieldCount;
    private ReflectorField[] reflectorFields;

    public ReflectorFields(ReflectorClass p_i87_1_, Class p_i87_2_, int p_i87_3_)
    {
        this.reflectorClass = p_i87_1_;
        this.fieldType = p_i87_2_;

        if (p_i87_1_.exists())
        {
            if (p_i87_2_ != null)
            {
                this.reflectorFields = new ReflectorField[p_i87_3_];

                for (int i = 0; i < this.reflectorFields.length; ++i)
                {
                    this.reflectorFields[i] = new ReflectorField(p_i87_1_, p_i87_2_, i);
                }
            }
        }
    }

    public ReflectorClass getReflectorClass()
    {
        return this.reflectorClass;
    }

    public Class getFieldType()
    {
        return this.fieldType;
    }

    public int getFieldCount()
    {
        return this.fieldCount;
    }

    public ReflectorField getReflectorField(int p_getReflectorField_1_)
    {
        return p_getReflectorField_1_ >= 0 && p_getReflectorField_1_ < this.reflectorFields.length ? this.reflectorFields[p_getReflectorField_1_] : null;
    }
}
