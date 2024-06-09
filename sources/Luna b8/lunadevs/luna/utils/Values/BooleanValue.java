package lunadevs.luna.utils.Values;

public class BooleanValue
        extends Value<Boolean>
{
    public BooleanValue(String name, Boolean defaultValue)
    {
        super(name, defaultValue);
    }

    public void toggle(){
        if(this.getDefaultValue()==true){
            this.setValue(false);
        }else  if(this.getDefaultValue()==false){
            this.setValue(true);
        }
    }

}
