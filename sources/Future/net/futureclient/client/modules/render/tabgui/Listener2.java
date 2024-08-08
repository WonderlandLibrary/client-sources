package net.futureclient.client.modules.render.tabgui;

import net.futureclient.client.qa;
import net.futureclient.client.Za;
import net.futureclient.client.Va;
import org.lwjgl.input.Keyboard;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.TabGui;
import net.futureclient.client.IE;
import net.futureclient.client.n;

public class Listener2 extends n<IE>
{
    public final TabGui k;
    
    public Listener2(final TabGui k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((IE)event);
    }
    
    @Override
    public void M(final IE ie) {
        IE ie2 = null;
        Label_0785: {
            if (ie.M() == IE.RD.M) {
                Keyboard.enableRepeatEvents(false);
                switch (ie.M()) {
                    case 200: {
                        if (!this.k.D.j) {
                            break;
                        }
                        Keyboard.enableRepeatEvents(true);
                        if (this.k.D.D) {
                            final qa d = this.k.D;
                            --d.a;
                            if (this.k.D.a < 0) {
                                this.k.D.a = this.k.D.A.size() - 1;
                            }
                            this.k.D.K = 11;
                            ie2 = ie;
                            break Label_0785;
                        }
                        final qa d2 = this.k.D;
                        --d2.d;
                        if (this.k.D.d < 0) {
                            this.k.D.d = this.k.D.A.get(this.k.D.a).M().size() - 1;
                        }
                        if (this.k.D.A.get(this.k.D.a).M().size() > 1) {
                            ie2 = ie;
                            this.k.D.K = 11;
                            break Label_0785;
                        }
                        break;
                    }
                    case 208: {
                        if (!this.k.D.j) {
                            break;
                        }
                        Keyboard.enableRepeatEvents(true);
                        if (this.k.D.D) {
                            final qa d3 = this.k.D;
                            ++d3.a;
                            if (this.k.D.a > this.k.D.A.size() - 1) {
                                this.k.D.a = 0;
                            }
                            this.k.D.K = -11;
                            ie2 = ie;
                            break Label_0785;
                        }
                        final qa d4 = this.k.D;
                        ++d4.d;
                        if (this.k.D.d > this.k.D.A.get(this.k.D.a).M().size() - 1) {
                            this.k.D.d = 0;
                        }
                        if (this.k.D.A.get(this.k.D.a).M().size() > 1) {
                            ie2 = ie;
                            this.k.D.K = -11;
                            break Label_0785;
                        }
                        break;
                    }
                    case 203:
                        if (!this.k.D.D) {
                            ie2 = ie;
                            this.k.D.D = true;
                            break Label_0785;
                        }
                        break;
                    case 205:
                        if (this.k.D.D) {
                            this.k.D.D = false;
                            ie2 = ie;
                            this.k.D.d = 0;
                            break Label_0785;
                        }
                        if (!this.k.D.j) {
                            this.k.D.j = true;
                            ie2 = ie;
                            this.k.D.D = true;
                            break Label_0785;
                        }
                        this.k.D.A.get(this.k.D.a).M().get(this.k.D.d).M().M();
                        ie2 = ie;
                        break Label_0785;
                    case 28:
                        if (TabGui.M(this.k).M() && !this.k.D.D && this.k.D.j) {
                            this.k.D.A.get(this.k.D.a).M().get(this.k.D.d).M().M();
                            break;
                        }
                        break;
                }
            }
            ie2 = ie;
        }
        if (ie2.M() == IE.RD.K) {
            switch (ie.M()) {
                case 200:
                case 208:
                    Keyboard.enableRepeatEvents(false);
                    break;
            }
        }
    }
}
