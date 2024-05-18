package HORIZON-6-0-SKIDPROTECTION;

public class FileDownloadThread extends Thread
{
    private String HorizonCode_Horizon_È;
    private IFileDownloadListener Â;
    
    public FileDownloadThread(final String urlString, final IFileDownloadListener listener) {
        this.HorizonCode_Horizon_È = null;
        this.Â = null;
        this.HorizonCode_Horizon_È = urlString;
        this.Â = listener;
    }
    
    @Override
    public void run() {
        try {
            final byte[] e = HttpUtils.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È);
            this.Â.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, e, null);
        }
        catch (Exception var2) {
            this.Â.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, null, var2);
        }
    }
    
    public String HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public IFileDownloadListener Â() {
        return this.Â;
    }
}
