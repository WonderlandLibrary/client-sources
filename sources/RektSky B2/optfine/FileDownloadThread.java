package optfine;

public class FileDownloadThread extends Thread
{
    private String urlString;
    private IFileDownloadListener listener;
    
    public FileDownloadThread(final String p_i32_1_, final IFileDownloadListener p_i32_2_) {
        this.urlString = null;
        this.listener = null;
        this.urlString = p_i32_1_;
        this.listener = p_i32_2_;
    }
    
    @Override
    public void run() {
        try {
            final byte[] abyte = HttpUtils.get(this.urlString);
            this.listener.fileDownloadFinished(this.urlString, abyte, null);
        }
        catch (Exception exception) {
            this.listener.fileDownloadFinished(this.urlString, null, exception);
        }
    }
    
    public String getUrlString() {
        return this.urlString;
    }
    
    public IFileDownloadListener getListener() {
        return this.listener;
    }
}
