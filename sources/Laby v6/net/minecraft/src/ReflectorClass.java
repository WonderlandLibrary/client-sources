package net.minecraft.src;

public class ReflectorClass
{
    private String targetClassName;
    private boolean checked;
    private Class targetClass;

    public ReflectorClass(String p_i76_1_)
    {
        this(p_i76_1_, false);
    }

    public ReflectorClass(String p_i77_1_, boolean p_i77_2_)
    {
        this.targetClassName = null;
        this.checked = false;
        this.targetClass = null;
        this.targetClassName = p_i77_1_;

        if (!p_i77_2_)
        {
            Class oclass = this.getTargetClass();
        }
    }

    public ReflectorClass(Class p_i78_1_)
    {
        this.targetClassName = null;
        this.checked = false;
        this.targetClass = null;
        this.targetClass = p_i78_1_;
        this.targetClassName = p_i78_1_.getName();
        this.checked = true;
    }

    public Class getTargetClass()
    {
        if (this.checked)
        {
            return this.targetClass;
        }
        else
        {
            this.checked = true;

            try
            {
                this.targetClass = Class.forName(this.targetClassName);
            }
            catch (ClassNotFoundException var2)
            {
                Config.log("(Reflector) Class not present: " + this.targetClassName);
            }
            catch (Throwable throwable)
            {
                throwable.printStackTrace();
            }

            return this.targetClass;
        }
    }

    public boolean exists()
    {
        return this.getTargetClass() != null;
    }

    public String getTargetClassName()
    {
        return this.targetClassName;
    }

    public boolean isInstance(Object p_isInstance_1_)
    {
        return this.getTargetClass() == null ? false : this.getTargetClass().isInstance(p_isInstance_1_);
    }
}
