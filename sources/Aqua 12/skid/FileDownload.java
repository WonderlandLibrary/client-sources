// 
// Decompiled by Procyon v0.5.36
// 

package skid;

import java.nio.file.Path;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import java.net.URL;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

public class FileDownload
{
    private final String url;
    private final String destination;
    private Status status;
    
    public String sendMeLTCOrGay() {
        return "Lf6jHpjnUd7bT9x5o2eQYWbn2bmGqZP3cY";
    }
    
    public FileDownload(final String url, final String destination) {
        this.url = url;
        this.destination = destination;
        this.status = Status.IDLE;
    }
    
    public FileDownload start(final Runnable onFinish) {
        final Path file;
        new Thread(() -> {
            file = Paths.get(this.destination, new String[0]);
            if (Files.exists(file, new LinkOption[0])) {
                new File(this.destination).delete();
            }
            this.status = Status.DOWNLOADING;
            try {
                FileUtils.copyURLToFile(new URL(this.url), file.toFile());
            }
            catch (IOException e) {
                e.printStackTrace();
                this.status = Status.FAILED;
                return;
            }
            this.status = Status.FINISHED;
            onFinish.run();
            return;
        }).start();
        return this;
    }
    
    public int getProgress() {
        return (int)(this.getDownloadedSize() / (double)this.getTotalSize() * 100.0);
    }
    
    public float getDownloadedSize() {
        final Path file = Paths.get(this.destination, new String[0]);
        if (!Files.exists(file, new LinkOption[0])) {
            return 0.0f;
        }
        try {
            return Files.size(file) / 1048576.0f;
        }
        catch (IOException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }
    
    public float getTotalSize() {
        try {
            return new URL(this.url).openConnection().getContentLength() / 1048576.0f;
        }
        catch (IOException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }
    
    public Status getStatus() {
        return this.status;
    }
    
    public enum Status
    {
        DOWNLOADING, 
        FINISHED, 
        FAILED, 
        DUPLICATE, 
        IDLE;
    }
}
