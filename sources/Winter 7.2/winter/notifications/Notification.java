/*
 * Decompiled with CFR 0_122.
 */
package winter.notifications;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import winter.utils.other.Timer;

public class Notification {
    private int seconds;
    private Timer drawTime;
    private String title;
    private String text;
    private int transOff;

    public Notification(String title, String text, int seconds) {
        this.title = title;
        this.text = text;
        this.drawTime = new Timer();
        this.seconds = seconds;
        this.transOff = 26;
        this.drawTime.reset();
    }

    public void transition() {
        if (!this.drawTime.hasPassed(1000 * this.seconds)) {
            if (this.transOff - 3 >= 0) {
                this.transOff -= 3;
            }
        } else if (this.drawTime.hasPassed(1000 * this.seconds) && this.transOff + 3 <= 26) {
            this.transOff += 3;
        }
    }

    public void render(int bar2, int background) {
        FontRenderer fr2 = Minecraft.getMinecraft().fontRendererObj;
        ScaledResolution sr2 = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        Gui.drawRect(sr2.getScaledWidth() - 121, sr2.getScaledHeight() - 26 + this.transOff, sr2.getScaledWidth() - 1, sr2.getScaledHeight() - 1 + this.transOff, background);
        Gui.drawRect(sr2.getScaledWidth() - 120, sr2.getScaledHeight() - 25 + this.transOff, sr2.getScaledWidth() - 2, sr2.getScaledHeight() - 16 + this.transOff, bar2);
        fr2.drawString(this.title, sr2.getScaledWidth() - 119, sr2.getScaledHeight() - 24 + this.transOff, -16777216);
        fr2.drawString(this.text, sr2.getScaledWidth() - 118, sr2.getScaledHeight() - 13 + this.transOff, -1);
    }
}

