package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;

public class LoadingList
{
    private static LoadingList HorizonCode_Horizon_È;
    private ArrayList Â;
    private int Ý;
    
    static {
        LoadingList.HorizonCode_Horizon_È = new LoadingList();
    }
    
    public static LoadingList HorizonCode_Horizon_È() {
        return LoadingList.HorizonCode_Horizon_È;
    }
    
    public static void HorizonCode_Horizon_È(final boolean loading) {
        LoadingList.HorizonCode_Horizon_È = new LoadingList();
        InternalTextureLoader.HorizonCode_Horizon_È().Â(loading);
        SoundStore.Å().HorizonCode_Horizon_È(loading);
    }
    
    public static boolean Â() {
        return InternalTextureLoader.HorizonCode_Horizon_È().Â();
    }
    
    private LoadingList() {
        this.Â = new ArrayList();
    }
    
    public void HorizonCode_Horizon_È(final DeferredResource resource) {
        ++this.Ý;
        this.Â.add(resource);
    }
    
    public void Â(final DeferredResource resource) {
        Log.Ý("Early loading of deferred resource due to req: " + resource.Â());
        --this.Ý;
        this.Â.remove(resource);
    }
    
    public int Ý() {
        return this.Ý;
    }
    
    public int Ø­áŒŠá() {
        return this.Â.size();
    }
    
    public DeferredResource Âµá€() {
        if (this.Â.size() == 0) {
            return null;
        }
        return this.Â.remove(0);
    }
}
