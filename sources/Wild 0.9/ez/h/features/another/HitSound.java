package ez.h.features.another;

import ez.h.event.events.*;
import ez.h.utils.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class HitSound extends Feature
{
    OptionMode mode;
    OptionSlider volume;
    
    @EventTarget
    public void onPacketReceive(final EventPacketReceive eventPacketReceive) {
        if (eventPacketReceive.getPacket() instanceof kq) {
            final kq kq = (kq)eventPacketReceive.getPacket();
            if (kq.a() == qf.fv || kq.a() == qf.fy || kq.a() == qf.fA) {
                final float n = this.volume.getNum() / 10.0f;
                if (this.mode.isMode("NeverLose")) {
                    Utils.playSound("neverlose.wav", -30.0f + n * 3.0f, false);
                }
                if (this.mode.isMode("Metallic")) {
                    Utils.playSound("metallic.wav", -30.0f + n * 3.0f, false);
                }
                if (this.mode.isMode("Bonk")) {
                    Utils.playSound("bonk.wav", -30.0f + n * 3.0f, false);
                }
                if (this.mode.isMode("Uwu")) {
                    Utils.playSound("uwu.wav", -30.0f + n * 3.0f, false);
                }
                if (this.mode.isMode("Ahhh")) {
                    Utils.playSound("ahhh.wav", -30.0f + n * 3.0f, false);
                }
            }
        }
    }
    
    public HitSound() {
        super("HitSound", "\u0418\u0437\u043c\u0435\u043d\u044f\u0435\u0442 \u0437\u0432\u0443\u043a \u0443\u0434\u0430\u0440\u0430.", Category.ANOTHER);
        this.volume = new OptionSlider(this, "Volume", 50.0f, 0.0f, 100.0f, OptionSlider.SliderType.NULLINT);
        this.mode = new OptionMode(this, "Mode", "Bonk", new String[] { "Bonk", "NeverLose", "Metallic", "Uwu", "Ahhh" }, 0);
        this.addOptions(this.mode);
    }
}
