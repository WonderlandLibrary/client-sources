package org.spongepowered.asm.lib;

class Handler
{
    Label start;
    Label end;
    Label handler;
    String desc;
    int type;
    Handler next;
    
    Handler() {
        super();
    }
    
    static Handler remove(Handler next, final Label label, final Label label2) {
        if (next == null) {
            return null;
        }
        next.next = remove(next.next, label, label2);
        final int position = next.start.position;
        final int position2 = next.end.position;
        final int position3 = label.position;
        final int n = (label2 == null) ? 0 : label2.position;
        if (position3 < position2 && n > position) {
            if (position3 <= position) {
                if (n >= position2) {
                    next = next.next;
                }
                else {
                    next.start = label2;
                }
            }
            else if (n >= position2) {
                next.end = label;
            }
            else {
                final Handler next2 = new Handler();
                next2.start = label2;
                next2.end = next.end;
                next2.handler = next.handler;
                next2.desc = next.desc;
                next2.type = next.type;
                next2.next = next.next;
                next.end = label;
                next.next = next2;
            }
        }
        return next;
    }
}
