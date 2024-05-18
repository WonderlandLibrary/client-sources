package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.*;
import me.darkmagician6.morbid.util.*;

public final class AutoSoup extends ModBase
{
    private final int soupID = 282;
    private final int bowlID = 281;
    private int health;
    private int count1;
    private int count2;
    public static int soups;
    
    public AutoSoup() {
        super("AutoSoup", "P", true, ".t soup");
        this.health = 14;
        this.setDescription("Eats soup for you when your health is below the treshold.");
    }
    
    @Override
    public void postMotionUpdate() {
        ++this.count1;
        ++this.count2;
        if (this.count2 >= 10 || (this.count2 >= 5 && this.isHealthLow())) {
            this.cleanHotbar();
        }
        if (this.count1 >= 10 || (this.count1 >= 5 && this.isHealthLow())) {
            this.prepareHotbar();
        }
        if (this.isHealthLow()) {
            this.eatSoup();
        }
        this.updateSoupCount();
    }
    
    @Override
    public void onCommand(final String s) {
        final String[] split = s.split(" ");
        if (s.toLowerCase().startsWith(".sh")) {
            try {
                final int soupHealth = Integer.parseInt(split[1]);
                this.health = soupHealth;
                this.getWrapper();
                MorbidWrapper.addChat("Soup health set to: " + this.health);
            }
            catch (Exception e) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .health [health to eat soup]");
            }
            ModBase.setCommandExists(true);
        }
    }
    
    private void eatSoup() {
        final boolean sword = Morbid.getManager().getMod("autosword").isEnabled();
        if (!sword) {
            Morbid.getManager().getMod("autosword").setEnabled(false);
        }
        for (int i = 44; i >= 9; --i) {
            this.getWrapper();
            final wm stack = MorbidWrapper.getPlayer().bL.a(i).c();
            if (stack != null) {
                if (i >= 36 && i <= 44) {
                    if (stack.c == 282) {
                        this.getWrapper();
                        MorbidWrapper.getPlayer().bK.c = i - 36;
                        this.getWrapper();
                        MorbidWrapper.getController().e();
                        final int n = -1;
                        final int n2 = -1;
                        final int n3 = -1;
                        final int n4 = -1;
                        this.getWrapper();
                        MorbidHelper.sendPacket(new fr(n, n2, n3, n4, MorbidWrapper.getPlayer().bK.h(), 0.0f, 0.0f, 0.0f));
                        break;
                    }
                }
                else if (stack.c == 282) {
                    if (!this.isHotbarFree()) {
                        this.getWrapper();
                        final bdr controller = MorbidWrapper.getController();
                        final int n5 = 0;
                        final int n6 = 44;
                        final int n7 = 0;
                        final int n8 = 0;
                        this.getWrapper();
                        controller.a(n5, n6, n7, n8, MorbidWrapper.getPlayer());
                        this.getWrapper();
                        final bdr controller2 = MorbidWrapper.getController();
                        final int n9 = 0;
                        final int n10 = -999;
                        final int n11 = 0;
                        final int n12 = 0;
                        this.getWrapper();
                        controller2.a(n9, n10, n11, n12, MorbidWrapper.getPlayer());
                    }
                    this.getWrapper();
                    final bdr controller3 = MorbidWrapper.getController();
                    final int n13 = 0;
                    final int n14 = i;
                    final int n15 = 0;
                    final int n16 = 1;
                    this.getWrapper();
                    controller3.a(n13, n14, n15, n16, MorbidWrapper.getPlayer());
                    break;
                }
            }
        }
        if (!sword) {
            Morbid.getManager().getMod("autosword").setEnabled(true);
        }
    }
    
    private void prepareHotbar() {
        for (int i1 = 36; i1 <= 44; ++i1) {
            this.getWrapper();
            final wm stack1 = MorbidWrapper.getPlayer().bL.a(i1).c();
            if (stack1 == null) {
                for (int i2 = 35; i2 >= 9; --i2) {
                    this.getWrapper();
                    final wm stack2 = MorbidWrapper.getPlayer().bL.a(i2).c();
                    if (stack2 != null) {
                        if (stack2.c == 282) {
                            this.getWrapper();
                            final bdr controller = MorbidWrapper.getController();
                            final int n = 0;
                            final int n2 = i2;
                            final int n3 = 0;
                            final int n4 = 1;
                            this.getWrapper();
                            controller.a(n, n2, n3, n4, MorbidWrapper.getPlayer());
                            this.count2 = 0;
                            return;
                        }
                    }
                }
            }
        }
    }
    
    private void cleanHotbar() {
        this.getWrapper();
        final wm topLeftSlot = MorbidWrapper.getPlayer().bL.a(9).c();
        for (int i = 36; i <= 44; ++i) {
            this.getWrapper();
            final wm stack = MorbidWrapper.getPlayer().bL.a(i).c();
            if (stack != null) {
                if (stack.c == 281) {
                    if (topLeftSlot != null && (topLeftSlot.c != 281 || topLeftSlot.a >= 64)) {
                        if (topLeftSlot.c == 282) {
                            this.getWrapper();
                            final bdr controller = MorbidWrapper.getController();
                            final int n = 0;
                            final int n2 = 9;
                            final int n3 = 0;
                            final int n4 = 0;
                            this.getWrapper();
                            controller.a(n, n2, n3, n4, MorbidWrapper.getPlayer());
                            this.getWrapper();
                            final bdr controller2 = MorbidWrapper.getController();
                            final int n5 = 0;
                            final int n6 = i;
                            final int n7 = 0;
                            final int n8 = 0;
                            this.getWrapper();
                            controller2.a(n5, n6, n7, n8, MorbidWrapper.getPlayer());
                            this.getWrapper();
                            final bdr controller3 = MorbidWrapper.getController();
                            final int n9 = 0;
                            final int n10 = 9;
                            final int n11 = 0;
                            final int n12 = 0;
                            this.getWrapper();
                            controller3.a(n9, n10, n11, n12, MorbidWrapper.getPlayer());
                            this.count1 = 0;
                            return;
                        }
                        this.getWrapper();
                        final bdr controller4 = MorbidWrapper.getController();
                        final int n13 = 0;
                        final int n14 = 9;
                        final int n15 = 0;
                        final int n16 = 0;
                        this.getWrapper();
                        controller4.a(n13, n14, n15, n16, MorbidWrapper.getPlayer());
                        this.getWrapper();
                        final bdr controller5 = MorbidWrapper.getController();
                        final int n17 = 0;
                        final int n18 = -999;
                        final int n19 = 0;
                        final int n20 = 0;
                        this.getWrapper();
                        controller5.a(n17, n18, n19, n20, MorbidWrapper.getPlayer());
                    }
                    this.getWrapper();
                    final bdr controller6 = MorbidWrapper.getController();
                    final int n21 = 0;
                    final int n22 = i;
                    final int n23 = 0;
                    final int n24 = 1;
                    this.getWrapper();
                    controller6.a(n21, n22, n23, n24, MorbidWrapper.getPlayer());
                    this.count1 = 0;
                    return;
                }
            }
        }
    }
    
    private void updateSoupCount() {
        AutoSoup.soups = 0;
        for (int i = 44; i >= 9; --i) {
            this.getWrapper();
            final wm stack = MorbidWrapper.getPlayer().bL.a(i).c();
            if (stack != null) {
                if (stack.c == 282) {
                    ++AutoSoup.soups;
                }
            }
        }
    }
    
    private boolean isHealthLow() {
        this.getWrapper();
        return MorbidWrapper.getPlayer().aX() <= this.health;
    }
    
    private boolean isHotbarFree() {
        for (int i = 36; i <= 44; ++i) {
            this.getWrapper();
            final wm stack = MorbidWrapper.getPlayer().bL.a(i).c();
            if (stack == null || stack.c == 282) {
                return true;
            }
        }
        return false;
    }
}
