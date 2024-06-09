package org.newdawn.slick.tests.xml;

import java.util.ArrayList;

public class GameData
{
    private ArrayList HorizonCode_Horizon_È;
    
    public GameData() {
        this.HorizonCode_Horizon_È = new ArrayList();
    }
    
    private void HorizonCode_Horizon_È(final Entity entity) {
        this.HorizonCode_Horizon_È.add(entity);
    }
    
    public void HorizonCode_Horizon_È(final String prefix) {
        System.out.println(String.valueOf(prefix) + "GameData");
        for (int i = 0; i < this.HorizonCode_Horizon_È.size(); ++i) {
            this.HorizonCode_Horizon_È.get(i).HorizonCode_Horizon_È(String.valueOf(prefix) + "\t");
        }
    }
}
