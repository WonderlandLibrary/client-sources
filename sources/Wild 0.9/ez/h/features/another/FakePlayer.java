package ez.h.features.another;

import java.util.*;
import com.mojang.authlib.*;
import ez.h.features.*;

public class FakePlayer extends Feature
{
    bue player;
    
    @Override
    public void onEnable() {
        this.player = new bue((amu)FakePlayer.mc.f, new GameProfile(new UUID(69L, 96L), "ezh1488"));
        this.player.bv = FakePlayer.mc.h.bv;
        this.player.bx = FakePlayer.mc.h.bx;
        this.player.a(FakePlayer.mc.h.p, FakePlayer.mc.h.q, FakePlayer.mc.h.r, FakePlayer.mc.h.v, FakePlayer.mc.h.w);
        this.player.aP = FakePlayer.mc.h.aP;
        FakePlayer.mc.f.a(this.player.S(), (vg)this.player);
        FakePlayer.mc.g.a();
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        if (this.player != null) {
            FakePlayer.mc.f.e(this.player.S());
        }
        super.onDisable();
    }
    
    public FakePlayer() {
        super("FakePlayer", "\u0421\u043e\u0437\u0434\u0430\u0451\u0442 \u0441\u0443\u0449\u043d\u043e\u0441\u0442\u044c \u0438\u0433\u0440\u043e\u043a\u0430 \u0434\u043b\u044f \u0442\u0435\u0441\u0442\u0430 \u0444\u0443\u043d\u043a\u0446\u0438\u0439.", Category.ANOTHER);
    }
}
