package org.newdawn.slick.tests.xml;

import java.util.ArrayList;

public class ItemContainer extends Item
{
    private ArrayList Ý;
    
    public ItemContainer() {
        this.Ý = new ArrayList();
    }
    
    private void HorizonCode_Horizon_È(final Item item) {
        this.Ý.add(item);
    }
    
    private void Â(final String name) {
        this.HorizonCode_Horizon_È = name;
    }
    
    private void HorizonCode_Horizon_È(final int condition) {
        this.Â = condition;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final String prefix) {
        System.out.println(String.valueOf(prefix) + "Item Container " + this.HorizonCode_Horizon_È + "," + this.Â);
        for (int i = 0; i < this.Ý.size(); ++i) {
            this.Ý.get(i).HorizonCode_Horizon_È(String.valueOf(prefix) + "\t");
        }
    }
}
