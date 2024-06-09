package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Iterator;

public final class EventManager extends ListManager<Listener>
{
    public void HorizonCode_Horizon_È(final Listener listener) {
        if (!this.Â().contains(listener)) {
            this.Â().add(listener);
        }
    }
    
    public void HorizonCode_Horizon_È(final EventVelocity event) {
        if (this.Â() == null) {
            return;
        }
        for (final Listener listener : this.Â()) {
            listener.HorizonCode_Horizon_È(event);
        }
    }
    
    public void Â(final Listener listener) {
        if (this.Â().contains(listener)) {
            this.Â().remove(listener);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È() {
        this.HorizonCode_Horizon_È = (List<T>)new CopyOnWriteArrayList<Object>();
    }
}
