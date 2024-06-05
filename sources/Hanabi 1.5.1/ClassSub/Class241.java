package ClassSub;

class Class241 implements Runnable
{
    final Class296 val$track;
    final Class286 this$0;
    
    
    Class241(final Class286 this$0, final Class296 val$track) {
        this.this$0 = this$0;
        this.val$track = val$track;
    }
    
    @Override
    public void run() {
        if (Class286.instance.isLoop) {
            this.this$0.play(this.val$track);
        }
        else {
            this.this$0.next();
        }
    }
}
