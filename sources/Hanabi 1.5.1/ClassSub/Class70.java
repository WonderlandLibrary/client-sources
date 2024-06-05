package ClassSub;

import java.io.*;

class Class70 implements Runnable
{
    final Class296 val$track;
    final Class286 this$0;
    
    
    Class70(final Class286 this$0, final Class296 val$track) {
        this.this$0 = this$0;
        this.val$track = val$track;
    }
    
    @Override
    public void run() {
        Class164.INSTANCE.downLoadSong(this.this$0.musicFolder.getAbsolutePath(), String.valueOf(this.val$track.getId()), Class164.INSTANCE.getDownloadUrl(String.valueOf(this.val$track.getId())));
        final File file = new File(this.this$0.musicFolder, this.val$track.getId() + ".mp3");
        this.this$0.play(this.val$track);
        this.this$0.loadingThread = null;
    }
}
