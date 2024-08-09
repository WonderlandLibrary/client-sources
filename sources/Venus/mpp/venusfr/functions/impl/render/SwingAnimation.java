/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.impl.combat.KillAura;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import net.minecraft.util.math.vector.Vector3f;

@FunctionRegister(name="SwingAnimation", type=Category.Visual)
public class SwingAnimation
extends Function {
    public ModeSetting animationMode = new ModeSetting("\u041c\u043e\u0434", "1", "1", "2", "3", "4");
    public SliderSetting swingPower = new SliderSetting("\u0421\u0438\u043b\u0430", 5.0f, 1.0f, 10.0f, 0.05f);
    public SliderSetting swingSpeed = new SliderSetting("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c", 10.0f, 3.0f, 10.0f, 1.0f);
    public SliderSetting scale = new SliderSetting("\u0420\u0430\u0437\u043c\u0435\u0440", 1.0f, 0.5f, 1.5f, 0.05f);
    public final BooleanSetting onlyAura = new BooleanSetting("\u0422\u043e\u043b\u044c\u043a\u043e \u0441 \u043a\u0438\u043b\u043b\u0430\u0443\u0440\u043e\u0439", true);
    public KillAura killAura;

    public SwingAnimation(KillAura killAura) {
        this.killAura = killAura;
        this.addSettings(this.animationMode, this.swingPower, this.swingSpeed, this.scale, this.onlyAura);
    }

    public void animationProcess(MatrixStack matrixStack, float f, Runnable runnable) {
        float f2 = (float)Math.sin((double)f * 1.5707963267948966 * 2.0);
        if (((Boolean)this.onlyAura.get()).booleanValue() && this.killAura.getTarget() == null) {
            runnable.run();
            return;
        }
        switch (this.animationMode.getIndex()) {
            case 0: {
                matrixStack.scale(((Float)this.scale.get()).floatValue(), ((Float)this.scale.get()).floatValue(), ((Float)this.scale.get()).floatValue());
                matrixStack.translate(0.4f, 0.1f, -0.5);
                matrixStack.rotate(Vector3f.YP.rotationDegrees(90.0f));
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(-60.0f));
                matrixStack.rotate(Vector3f.XP.rotationDegrees(-90.0f - ((Float)this.swingPower.get()).floatValue() * 10.0f * f2));
                break;
            }
            case 1: {
                matrixStack.scale(((Float)this.scale.get()).floatValue(), ((Float)this.scale.get()).floatValue(), ((Float)this.scale.get()).floatValue());
                matrixStack.translate(0.0, 0.0, -0.5);
                matrixStack.rotate(Vector3f.YP.rotationDegrees(15.0f * f2));
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(-60.0f * f2));
                matrixStack.rotate(Vector3f.XP.rotationDegrees((-90.0f - ((Float)this.swingPower.get()).floatValue()) * f2));
                break;
            }
            case 2: {
                matrixStack.scale(((Float)this.scale.get()).floatValue(), ((Float)this.scale.get()).floatValue(), ((Float)this.scale.get()).floatValue());
                matrixStack.translate(0.4f, 0.0, -0.5);
                matrixStack.rotate(Vector3f.YP.rotationDegrees(90.0f));
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(-30.0f));
                matrixStack.rotate(Vector3f.XP.rotationDegrees(-90.0f - ((Float)this.swingPower.get()).floatValue() * 10.0f * f2));
                break;
            }
            default: {
                matrixStack.scale(((Float)this.scale.get()).floatValue(), ((Float)this.scale.get()).floatValue(), ((Float)this.scale.get()).floatValue());
                runnable.run();
            }
        }
    }
}

