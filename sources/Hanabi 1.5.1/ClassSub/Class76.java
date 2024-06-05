package ClassSub;

class Class76 extends Thread
{
    final Class137 this$0;
    
    
    Class76(final Class137 this$0) {
        this.this$0 = this$0;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(50L);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        Class137.renderingValue = null;
    }
}
