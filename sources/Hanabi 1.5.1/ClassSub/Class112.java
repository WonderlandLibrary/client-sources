package ClassSub;

import java.util.concurrent.*;
import javafx.embed.swing.*;

class Class112 implements Runnable
{
    final CountDownLatch val$latch;
    final Class286 this$0;
    
    
    Class112(final Class286 this$0, final CountDownLatch val$latch) {
        this.this$0 = this$0;
        this.val$latch = val$latch;
    }
    
    @Override
    public void run() {
        new JFXPanel();
        this.val$latch.countDown();
    }
}
