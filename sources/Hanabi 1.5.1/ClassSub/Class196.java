package ClassSub;

class Class196 extends Thread
{
    final Class137 val$selector;
    final Class137 this$0;
    
    
    Class196(final Class137 this$0, final Class137 val$selector) {
        this.this$0 = this$0;
        this.val$selector = val$selector;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(50L);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Class137.access$002(this.val$selector, false);
        Class137.access$102(this.val$selector, false);
        Class137.renderingValue = null;
    }
}
