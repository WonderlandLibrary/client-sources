package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.lang.reflect.InvocationTargetException;

public class Event implements Cancellable_865113938
{
    private boolean HorizonCode_Horizon_È;
    
    public Event Â() {
        this.HorizonCode_Horizon_È = false;
        HorizonCode_Horizon_È(this);
        return this;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public void HorizonCode_Horizon_È(final boolean state) {
        this.HorizonCode_Horizon_È = state;
    }
    
    private static final void HorizonCode_Horizon_È(final Event event) {
        final FlexibleArray dataList = EventManager_1550089733.Â(event.getClass());
        if (dataList != null) {
            for (final MethodData data : dataList) {
                try {
                    data.Â.invoke(data.HorizonCode_Horizon_È, event);
                }
                catch (IllegalAccessException var4) {
                    System.out.println("Can't invoke '" + data.Â.getName() + "' because it's not accessible.");
                }
                catch (IllegalArgumentException var5) {
                    System.out.println("Can't invoke '" + data.Â.getName() + "' because the parameter/s don't match.");
                }
                catch (InvocationTargetException ex) {}
            }
        }
    }
}
