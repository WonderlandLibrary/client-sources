package optfine;

import java.util.*;

public class FileUploadThread extends Thread
{
    private byte[] content;
    private Map headers;
    private IFileUploadListener listener;
    private String urlString;
    
    public byte[] getContent() {
        return this.content;
    }
    
    public IFileUploadListener getListener() {
        return this.listener;
    }
    
    @Override
    public void run() {
        try {
            HttpUtils.post(this.urlString, this.headers, this.content);
            this.listener.fileUploadFinished(this.urlString, this.content, null);
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        catch (Exception ex) {
            this.listener.fileUploadFinished(this.urlString, this.content, ex);
        }
    }
    
    public FileUploadThread(final String urlString, final Map headers, final byte[] content, final IFileUploadListener listener) {
        this.urlString = urlString;
        this.headers = headers;
        this.content = content;
        this.listener = listener;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public String getUrlString() {
        return this.urlString;
    }
}
