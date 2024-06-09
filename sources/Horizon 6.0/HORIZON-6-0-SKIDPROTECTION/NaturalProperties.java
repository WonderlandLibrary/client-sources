package HORIZON-6-0-SKIDPROTECTION;

public class NaturalProperties
{
    public int HorizonCode_Horizon_È;
    public boolean Â;
    
    public NaturalProperties(final String type) {
        this.HorizonCode_Horizon_È = 1;
        this.Â = false;
        if (type.equals("4")) {
            this.HorizonCode_Horizon_È = 4;
        }
        else if (type.equals("2")) {
            this.HorizonCode_Horizon_È = 2;
        }
        else if (type.equals("F")) {
            this.Â = true;
        }
        else if (type.equals("4F")) {
            this.HorizonCode_Horizon_È = 4;
            this.Â = true;
        }
        else if (type.equals("2F")) {
            this.HorizonCode_Horizon_È = 2;
            this.Â = true;
        }
        else {
            Config.Â("NaturalTextures: Unknown type: " + type);
        }
    }
    
    public boolean HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È == 2 || this.HorizonCode_Horizon_È == 4 || this.Â;
    }
}
