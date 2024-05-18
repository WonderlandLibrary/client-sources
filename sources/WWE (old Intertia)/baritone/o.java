package baritone;

import java.util.concurrent.*;

final class o implements Runnable
{
    private n a;
    
    private o(final n a) {
        this.a = a;
        super();
    }
    
    @Override
    public final void run() {
        LinkedBlockingQueue a;
        while ((a = n.a(this.a)) != null) {
            try {
                n.a(this.a, p.a(a.take()));
                continue;
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
                return;
            }
            break;
        }
    }
    
    o(final n n, final byte b) {
        this(n);
    }
}
