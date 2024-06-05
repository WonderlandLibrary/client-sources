package ClassSub;

class Class133 implements Runnable
{
    final Class286 this$0;
    
    
    Class133(final Class286 this$0) {
        this.this$0 = this$0;
    }
    
    @Override
    public void run() {
        int n = 1;
        int n2 = 0;
        final Class296 currentTrack = Class286.getInstance().getCurrentTrack();
        while (true) {
            final long n3 = (long)Class286.getInstance().getMediaPlayer().getCurrentTime().toMillis();
            try {
                if (n3 <= Class286.getInstance().tLyric.get(n).getTime()) {
                    if (n2 == 0) {
                        Class344.INSTANCE.tly = Class286.getInstance().tLyric.get(n - 1).getText();
                    }
                    n2 = 1;
                }
                else {
                    ++n;
                    n2 = 0;
                }
            }
            catch (Exception ex) {
                Class344.INSTANCE.tly = "";
            }
            if (n - 1 > Class286.getInstance().tLyric.size() || Class286.getInstance().getCurrentTrack() != currentTrack || !this.this$0.playTranslateLyric) {
                break;
            }
            try {
                Thread.sleep(50L);
            }
            catch (Exception ex2) {}
        }
    }
}
