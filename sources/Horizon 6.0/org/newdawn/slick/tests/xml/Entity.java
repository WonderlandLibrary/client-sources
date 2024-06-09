package org.newdawn.slick.tests.xml;

public class Entity
{
    private float HorizonCode_Horizon_È;
    private float Â;
    private Inventory Ý;
    private Stats Ø­áŒŠá;
    
    private void HorizonCode_Horizon_È(final Inventory inventory) {
        this.Ý = inventory;
    }
    
    private void HorizonCode_Horizon_È(final Stats stats) {
        this.Ø­áŒŠá = stats;
    }
    
    public void HorizonCode_Horizon_È(final String prefix) {
        System.out.println(String.valueOf(prefix) + "Entity " + this.HorizonCode_Horizon_È + "," + this.Â);
        this.Ý.HorizonCode_Horizon_È(String.valueOf(prefix) + "\t");
        this.Ø­áŒŠá.HorizonCode_Horizon_È(String.valueOf(prefix) + "\t");
    }
}
