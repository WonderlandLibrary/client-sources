package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import java.io.IOException;

public class CompositeIOException extends IOException
{
    private ArrayList HorizonCode_Horizon_È;
    
    public CompositeIOException() {
        this.HorizonCode_Horizon_È = new ArrayList();
    }
    
    public void HorizonCode_Horizon_È(final Exception e) {
        this.HorizonCode_Horizon_È.add(e);
    }
    
    @Override
    public String getMessage() {
        String msg = "Composite Exception: \n";
        for (int i = 0; i < this.HorizonCode_Horizon_È.size(); ++i) {
            msg = String.valueOf(msg) + "\t" + this.HorizonCode_Horizon_È.get(i).getMessage() + "\n";
        }
        return msg;
    }
}
