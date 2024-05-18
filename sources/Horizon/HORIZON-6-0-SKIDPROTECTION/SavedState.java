package HORIZON-6-0-SKIDPROTECTION;

import java.io.IOException;
import java.util.HashMap;

public class SavedState
{
    private String HorizonCode_Horizon_È;
    private Muffin Â;
    private HashMap Ý;
    private HashMap Ø­áŒŠá;
    
    public SavedState(final String fileName) throws SlickException {
        this.Ý = new HashMap();
        this.Ø­áŒŠá = new HashMap();
        this.HorizonCode_Horizon_È = fileName;
        try {
            this.Â();
        }
        catch (IOException e) {
            throw new SlickException("Failed to load state on startup", e);
        }
    }
    
    public double HorizonCode_Horizon_È(final String nameOfField) {
        return this.HorizonCode_Horizon_È(nameOfField, 0.0);
    }
    
    public double HorizonCode_Horizon_È(final String nameOfField, final double defaultValue) {
        final Double value = this.Ý.get(nameOfField);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
    
    public void Â(final String nameOfField, final double value) {
        this.Ý.put(nameOfField, new Double(value));
    }
    
    public String Â(final String nameOfField) {
        return this.HorizonCode_Horizon_È(nameOfField, null);
    }
    
    public String HorizonCode_Horizon_È(final String nameOfField, final String defaultValue) {
        final String value = this.Ø­áŒŠá.get(nameOfField);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }
    
    public void Â(final String nameOfField, final String value) {
        this.Ø­áŒŠá.put(nameOfField, value);
    }
    
    public void HorizonCode_Horizon_È() throws IOException {
        this.Â.HorizonCode_Horizon_È(this.Ý, String.valueOf(this.HorizonCode_Horizon_È) + "_Number");
        this.Â.HorizonCode_Horizon_È(this.Ø­áŒŠá, String.valueOf(this.HorizonCode_Horizon_È) + "_String");
    }
    
    public void Â() throws IOException {
        this.Ý = this.Â.HorizonCode_Horizon_È(String.valueOf(this.HorizonCode_Horizon_È) + "_Number");
        this.Ø­áŒŠá = this.Â.HorizonCode_Horizon_È(String.valueOf(this.HorizonCode_Horizon_È) + "_String");
    }
    
    public void Ý() {
        this.Ý.clear();
        this.Ø­áŒŠá.clear();
    }
    
    private boolean Ø­áŒŠá() {
        try {
            Class.forName("javax.jnlp.ServiceManager");
            Log.Ý("Webstart detected using Muffins");
        }
        catch (Exception e) {
            Log.Ý("Using Local File System");
            return false;
        }
        return true;
    }
}
