package HORIZON-6-0-SKIDPROTECTION;

import java.util.Map;

public class FileUploadThread extends Thread
{
    private String HorizonCode_Horizon_È;
    private Map Â;
    private byte[] Ý;
    private IFileUploadListener Ø­áŒŠá;
    
    public FileUploadThread(final String urlString, final Map headers, final byte[] content, final IFileUploadListener listener) {
        this.HorizonCode_Horizon_È = urlString;
        this.Â = headers;
        this.Ý = content;
        this.Ø­áŒŠá = listener;
    }
    
    @Override
    public void run() {
        try {
            HttpUtils.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, this.Ý);
            this.Ø­áŒŠá.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Ý, null);
        }
        catch (Exception var2) {
            this.Ø­áŒŠá.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Ý, var2);
        }
    }
    
    public String HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public byte[] Â() {
        return this.Ý;
    }
    
    public IFileUploadListener Ý() {
        return this.Ø­áŒŠá;
    }
}
