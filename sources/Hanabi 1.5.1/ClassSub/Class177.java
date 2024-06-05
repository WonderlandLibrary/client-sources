package ClassSub;

import java.io.*;

protected class Class177 implements Serializable
{
    protected Class277 first;
    protected Class177 next;
    final Class192 this$0;
    
    
    protected Class177(final Class192 this$0) {
        this.this$0 = this$0;
    }
    
    public void clear() {
        if (this.first != null) {
            Class192.access$000(this.this$0, this.first);
            this.first = null;
        }
    }
    
    public void add(final Class277 prev) {
        if (this.first != null) {
            this.first.insertBefore(prev);
        }
        else {
            this.first = prev;
            prev.next = prev;
            prev.prev = prev;
        }
    }
    
    public void computeAngles() {
        if (this.first == null) {
            return;
        }
        Class277 class277 = this.first;
        do {
            class277.computeAngle();
        } while ((class277 = class277.next) != this.first);
    }
    
    public boolean doesIntersectSegment(final Class224 class224, final Class224 class225) {
        final double n = class225.x - class224.x;
        final double n2 = class225.y - class224.y;
        Class277 first = this.first;
        while (true) {
            final Class277 next = first.next;
            if (first.pt != class224 && next.pt != class224 && first.pt != class225 && next.pt != class225) {
                final double n3 = next.pt.x - first.pt.x;
                final double n4 = next.pt.y - first.pt.y;
                final double n5 = n * n4 - n2 * n3;
                if (Math.abs(n5) > 1.0E-5) {
                    final double n6 = first.pt.x - class224.x;
                    final double n7 = first.pt.y - class224.y;
                    final double n8 = (n4 * n6 - n3 * n7) / n5;
                    final double n9 = (n2 * n6 - n * n7) / n5;
                    if (n8 >= 0.0 && n8 <= 1.0 && n9 >= 0.0 && n9 <= 1.0) {
                        return true;
                    }
                }
            }
            if (next == this.first) {
                return false;
            }
            first = next;
        }
    }
    
    public int countPoints() {
        if (this.first == null) {
            return 0;
        }
        int n = 0;
        Class277 class277 = this.first;
        do {
            ++n;
        } while ((class277 = class277.next) != this.first);
        return n;
    }
    
    public boolean contains(final Class224 class224) {
        return this.first != null && (this.first.prev.pt.equals(class224) || this.first.pt.equals(class224));
    }
}
