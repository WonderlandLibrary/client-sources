package ez.h.features.combat;

import ez.h.utils.*;
import ez.h.event.events.*;
import ez.h.event.*;
import ez.h.features.*;
import java.util.*;
import ez.h.ui.clickgui.options.*;

public class AutoPotion extends Feature
{
    private OptionSlider delay;
    private final Counter counter;
    private static final float THROWING_PITCH = 88.5f;
    private static final int SLOT_NOT_FOUND = -1;
    private OptionBoolean fireResistence;
    private boolean throwing;
    private OptionBoolean speed;
    private OptionBoolean strength;
    private final Map<PotionsEnum, Boolean> potionsToThrow;
    private OptionBoolean ground;
    
    private void throwPotion(final PotionsEnum potionsEnum) {
        final int potionSlot = this.getPotionSlot(potionsEnum);
        if (potionSlot == -1) {
            return;
        }
        this.throwing = true;
        Utils.rotationCounter = (0xA8 ^ 0xA4);
        AutoPotion.mc.h.d.a((ht)new lk.c(AutoPotion.mc.h.v, 88.5f, AutoPotion.mc.h.z));
        AutoPotion.mc.h.d.a((ht)new lv(potionSlot));
        AutoPotion.mc.h.d.a((ht)new mb(ub.a));
        AutoPotion.mc.h.d.a((ht)new lv(AutoPotion.mc.h.bv.d));
        Debug.executeLaterNotStack((long)this.delay.getNum(), () -> this.throwing = false);
    }
    
    @EventTarget
    public void onUpdate(final EventMotion eventMotion) {
        if (this.counter.isDelay((long)this.delay.getNum())) {
            this.updateMap();
            this.potionsToThrow.forEach((potionsEnum, b) -> {
                if (b && (!this.ground.isEnabled() || AutoPotion.mc.h.z) && !this.isPotionActived(potionsEnum) && !this.throwing) {
                    this.throwPotion(potionsEnum);
                    this.counter.setLastMS();
                }
            });
        }
    }
    
    private void updateMap() {
        this.potionsToThrow.put(PotionsEnum.STRENGTH, this.strength.isEnabled());
        this.potionsToThrow.put(PotionsEnum.SPEED, this.speed.isEnabled());
        this.potionsToThrow.put(PotionsEnum.FIRERES, this.fireResistence.isEnabled());
    }
    
    public AutoPotion() {
        super("AutoPotion", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u043a\u0438\u0434\u0430\u0435\u0442 \u0437\u0435\u043b\u044c\u044f \u043f\u043e\u0434 \u0432\u0430\u0441.", Category.COMBAT);
        this.potionsToThrow = new HashMap<PotionsEnum, Boolean>();
        this.counter = new Counter();
        this.delay = new OptionSlider(this, "Delay", 100.0f, 1.0f, 1000.0f, OptionSlider.SliderType.MS);
        this.ground = new OptionBoolean(this, "Ground", true);
        this.strength = new OptionBoolean(this, "Strength", true);
        this.speed = new OptionBoolean(this, "Speed", true);
        this.fireResistence = new OptionBoolean(this, "Fire Resistence", true);
        this.addOptions(this.ground, this.delay, this.strength, this.speed, this.fireResistence);
    }
    
    private int getPotionSlot(final PotionsEnum potionsEnum) {
        for (int i = 0; i < 9; ++i) {
            final aip a = AutoPotion.mc.h.bv.a(i);
            if (a != aip.EMPTY && this.isValidPotion(a, potionsEnum)) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean isPotionActived(final PotionsEnum potionsEnum) {
        final uz a = uz.a(potionsEnum.getId());
        if (a == null) {
            System.out.println("potionsEnum.getId() \u0432\u0435\u0440\u043d\u0443\u043b \u043d\u0435\u0438\u0437\u0432\u0435\u0441\u0442\u043d\u044b\u0439 ID \u0437\u0435\u043b\u044c\u044f!");
            return false;
        }
        return AutoPotion.mc.h.a(a);
    }
    
    private boolean isValidPotion(final aip aip, final PotionsEnum potionsEnum) {
        return aip.c() == air.bI && aki.a(aip).stream().anyMatch(va -> va.a() == uz.a(potionsEnum.getId()));
    }
    
    enum PotionsEnum
    {
        SPEED(1), 
        FIRERES(0x93 ^ 0x9F);
        
        private int id;
        
        STRENGTH(5);
        
        public int getId() {
            return this.id;
        }
        
        private PotionsEnum(final int id) {
            this.id = id;
        }
    }
}
