package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collection;
import java.util.ArrayList;

public class XMLElementList
{
    private ArrayList HorizonCode_Horizon_È;
    
    public XMLElementList() {
        this.HorizonCode_Horizon_È = new ArrayList();
    }
    
    public void HorizonCode_Horizon_È(final XMLElement element) {
        this.HorizonCode_Horizon_È.add(element);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È.size();
    }
    
    public XMLElement HorizonCode_Horizon_È(final int i) {
        return this.HorizonCode_Horizon_È.get(i);
    }
    
    public boolean Â(final XMLElement element) {
        return this.HorizonCode_Horizon_È.contains(element);
    }
    
    public void HorizonCode_Horizon_È(final Collection collection) {
        collection.addAll(this.HorizonCode_Horizon_È);
    }
}
