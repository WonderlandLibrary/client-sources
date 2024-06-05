package ClassSub;

class Class252 implements Runnable
{
    final Class286 this$0;
    
    
    Class252(final Class286 this$0) {
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
                if (n3 < Class286.getInstance().lyric.get(n).getTime()) {
                    if (n2 == 0) {
                        Class344.INSTANCE.ly = Class286.getInstance().lyric.get(n - 1).getText();
                        if (Class286.instance.tLyric.isEmpty()) {
                            try {
                                Class344.INSTANCE.tly = Class286.getInstance().lyric.get(n).getText();
                            }
                            catch (Exception ex) {
                                Class344.INSTANCE.tly = "";
                            }
                        }
                    }
                    n2 = 1;
                }
                else {
                    ++n;
                    n2 = 0;
                }
            }
            catch (Exception ex2) {
                Class344.INSTANCE.ly = "";
            }
            if (n - 1 > Class286.getInstance().lyric.size() || Class286.getInstance().getCurrentTrack() != currentTrack || !this.this$0.playLyric) {
                break;
            }
            try {
                Thread.sleep(50L);
            }
            catch (Exception ex3) {}
        }
    }
}
