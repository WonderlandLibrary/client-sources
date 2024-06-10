package me.kaimson.melonclient.features.modules.keystrokes;

import me.kaimson.melonclient.features.modules.utils.*;
import me.kaimson.melonclient.utils.*;
import me.kaimson.melonclient.features.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import java.util.*;
import me.kaimson.melonclient.features.modules.keystrokes.keys.*;

public class KeystrokesModule extends IModuleRenderer
{
    public final int gap = 2;
    public KeyLayoutBuilder builder;
    public final Setting boxColor;
    public final Setting boxPressedColor;
    public final Setting boxSize;
    public final Setting boxFade;
    public final Setting showCPS;
    public final Setting textColor;
    public final Setting pressedTextColor;
    public final Setting outline;
    public final Setting outlineColor;
    public float offset;
    
    public KeystrokesModule() {
        super("Keystrokes");
        new Setting(this, "Box Options");
        this.boxColor = new Setting(this, "Box Color").setDefault(new Color(0, 0, 0, 50).getRGB(), 0);
        this.boxPressedColor = new Setting(this, "Box Pressed Color").setDefault(new Color(255, 255, 255, 100).getRGB(), 0);
        this.boxSize = new Setting(this, "Box Size").setDefault(20.0f).setRange(10.0f, 30.0f, 0.1f).onValueChanged(setting -> this.builder = this.createLayout());
        this.boxFade = new Setting(this, "Fade").setDefault(250).setRange(0, 500, 1);
        this.showCPS = new Setting(this, "Show CPS").setDefault(true);
        this.textColor = new Setting(this, "Text Color").setDefault(new Color(255, 255, 255, 255).getRGB(), 0);
        this.pressedTextColor = new Setting(this, "Pressed Text Color").setDefault(new Color(0, 0, 0, 255).getRGB(), 0);
        this.outline = new Setting(this, "Outline").setDefault(false);
        this.outlineColor = new Setting(this, "Outline Color").setDefault(new Color(0, 0, 0, 100).getRGB(), 0);
    }
    
    @Override
    public int getWidth() {
        if (this.builder == null) {
            this.builder = this.createLayout();
        }
        return (int)this.builder.getWidth();
    }
    
    @Override
    public int getHeight() {
        return (int)this.builder.getHeight();
    }
    
    @Override
    public void render(final float x, final float y) {
        if (this.builder == null) {
            this.builder = this.createLayout();
        }
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0f);
        for (final Rows row : this.builder.rows) {
            GL11.glPushMatrix();
            for (final Key key : row.getKeys()) {
                key.render();
                final double offset = row.getWidth() + this.builder.getGapSize();
                this.offset += key.getHeight();
                GL11.glTranslated(offset, 0.0, 0.0);
            }
            GL11.glPopMatrix();
            GL11.glTranslated(0.0, row.getHeight() + this.builder.getGapSize(), 0.0);
            this.offset = 0.0f;
        }
        GL11.glPopMatrix();
    }
    
    private KeyLayoutBuilder createLayout() {
        final FillerKey filler = new FillerKey(2, this);
        final Key keyW = this.create(this.mc.t.Y, this);
        final Key keyA = this.create(this.mc.t.Z, this);
        final Key keyS = this.create(this.mc.t.aa, this);
        final Key keyD = this.create(this.mc.t.ab, this);
        final Key keyLMB = new KeyMouse(2, this.mc.t.ai, this).setLeft();
        final Key keyRMB = new KeyMouse(2, this.mc.t.ag, this).setRight();
        final Key keySpace = new KeySpacebar(2, this.mc.t.ac, this);
        return new KeyLayoutBuilder().setGapSize(2).setWidth(this.boxSize.getFloat() * 3.0f + 4.0f).addRow(filler, keyW, filler).addRow(keyA, keyS, keyD).addRow(keyLMB, keyRMB).addRow(keySpace).build();
    }
    
    private Key create(final avb keyBinding, final KeystrokesModule keystrokesModule) {
        return new Key(2, keyBinding, keystrokesModule);
    }
}
