package HORIZON-6-0-SKIDPROTECTION;

import java.util.HashMap;
import java.util.ArrayList;

public class Diagram
{
    private ArrayList HorizonCode_Horizon_È;
    private HashMap Â;
    private HashMap Ý;
    private HashMap Ø­áŒŠá;
    private float Âµá€;
    private float Ó;
    
    public Diagram(final float width, final float height) {
        this.HorizonCode_Horizon_È = new ArrayList();
        this.Â = new HashMap();
        this.Ý = new HashMap();
        this.Ø­áŒŠá = new HashMap();
        this.Âµá€ = width;
        this.Ó = height;
    }
    
    public float HorizonCode_Horizon_È() {
        return this.Âµá€;
    }
    
    public float Â() {
        return this.Ó;
    }
    
    public void HorizonCode_Horizon_È(final String name, final String href) {
        this.Â.put(name, href);
    }
    
    public void HorizonCode_Horizon_È(final String name, final Gradient gradient) {
        this.Ý.put(name, gradient);
    }
    
    public String HorizonCode_Horizon_È(final String name) {
        return this.Â.get(name);
    }
    
    public Gradient Â(final String name) {
        return this.Ý.get(name);
    }
    
    public String[] Ý() {
        return (String[])this.Â.keySet().toArray(new String[0]);
    }
    
    public Figure Ý(final String id) {
        return this.Ø­áŒŠá.get(id);
    }
    
    public void HorizonCode_Horizon_È(final Figure figure) {
        this.HorizonCode_Horizon_È.add(figure);
        this.Ø­áŒŠá.put(figure.Ø­áŒŠá().Â("id"), figure);
        final String fillRef = figure.Ø­áŒŠá().Ø­áŒŠá("fill");
        final Gradient gradient = this.Â(fillRef);
        if (gradient != null && gradient.HorizonCode_Horizon_È()) {
            for (int i = 0; i < InkscapeLoader.HorizonCode_Horizon_È; ++i) {
                figure.Ý().Çªà¢();
            }
        }
    }
    
    public int Ø­áŒŠá() {
        return this.HorizonCode_Horizon_È.size();
    }
    
    public Figure HorizonCode_Horizon_È(final int index) {
        return this.HorizonCode_Horizon_È.get(index);
    }
    
    public void Â(final Figure figure) {
        this.HorizonCode_Horizon_È.remove(figure);
        this.Ø­áŒŠá.remove(figure.Ø­áŒŠá().Â("id"));
    }
}
