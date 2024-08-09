package src.Wiksi.functions.impl.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.impl.combat.KillAura;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.functions.settings.impl.SliderSetting;
import net.minecraft.util.math.vector.Vector3f;

@FunctionRegister(
        name = "SwingAnimation",
        type = Category.Render
)
public class SwingAnimation extends Function {
    public ModeSetting animationMode = new ModeSetting("Мод", "Swap", new String[]{"Swap", "Hit", "Old Smooth", "SwapBack", "New", "360", "Smooth"});
    public SliderSetting swingPower = new SliderSetting("Сила", 5.0F, 1.0F, 10.0F, 0.05F);
    public SliderSetting swingSpeed = new SliderSetting("Скорость", 5.0F, 3.0F, 10.0F, 0.05F);
    public SliderSetting scale = new SliderSetting("Размер", 1.0F, 0.5F, 1.5F, 0.05F);
    public final BooleanSetting onlyAura = new BooleanSetting("Только с аурой", false);
    public final BooleanSetting fullCircleRotation = new BooleanSetting("Вращение на 360", false);
    public KillAura killAura;

    public SwingAnimation(KillAura killAura) {
        this.killAura = killAura;
        this.addSettings(new Setting[]{this.animationMode, this.swingPower, this.swingSpeed, this.scale, this.onlyAura, this.fullCircleRotation});
    }

    public void animationProcess(MatrixStack stack, float swingProgress, Runnable runnable) {
        float anim = (float) Math.sin((double) swingProgress * 1.5707963267948966 * 2.0);
        if ((Boolean) this.onlyAura.get() && this.killAura.getTarget() == null) {
            runnable.run();
        } else {
            if ((Boolean) this.fullCircleRotation.get()) {
                long time = System.currentTimeMillis();
                float rotation = (float) (time % 3600L) / 10.0F;
                stack.scale((Float) this.scale.get(), (Float) this.scale.get(), (Float) this.scale.get());
                float yOffset = -0.2F * swingProgress;
                stack.translate(0.0, (double) yOffset, -0.5);
                stack.rotate(Vector3f.YP.rotationDegrees(rotation));
            } else {
                    switch (this.animationMode.getIndex()) {
                        case 0:
                            stack.scale((Float) this.scale.get(), (Float) this.scale.get(), (Float) this.scale.get());
                            stack.translate(0.4000000059604645, 0.10000000149011612, -0.5);
                            stack.rotate(Vector3f.YP.rotationDegrees(90.0F));
                            stack.rotate(Vector3f.ZP.rotationDegrees(-60.0F));
                            stack.rotate(Vector3f.XP.rotationDegrees(-90.0F - (Float) this.swingPower.get() * 10.0F * anim));
                            break;
                        case 1:
                            stack.scale((Float) this.scale.get(), (Float) this.scale.get(), (Float) this.scale.get());
                            stack.translate(0.0, 0.0, -0.5);
                            stack.rotate(Vector3f.YP.rotationDegrees(15.0F * anim));
                            stack.rotate(Vector3f.ZP.rotationDegrees(-60.0F * anim));
                            stack.rotate(Vector3f.XP.rotationDegrees((-90.0F - (Float) this.swingPower.get()) * anim));
                            break;
                        case 2:
                            stack.scale((Float) this.scale.get(), (Float) this.scale.get(), (Float) this.scale.get());
                            stack.translate(0.4000000059604645, 0.0, -0.5);
                            stack.rotate(Vector3f.YP.rotationDegrees(90.0F));
                            stack.rotate(Vector3f.ZP.rotationDegrees(-30.0F));
                            stack.rotate(Vector3f.XP.rotationDegrees(-90.0F - (Float) this.swingPower.get() * 10.0F * anim));
                            break;
                        case 3:
                            stack.scale((Float) this.scale.get(), (Float) this.scale.get(), (Float) this.scale.get());
                            stack.translate(0.4000000059604645, 0.10000000149011612, -0.5);
                            stack.rotate(Vector3f.YP.rotationDegrees(90.0F));
                            stack.rotate(Vector3f.ZP.rotationDegrees(-60.0F));
                            stack.rotate(Vector3f.XP.rotationDegrees(-90.0F + (Float) this.swingPower.get() * 10.0F * anim));
                            break;
                        case 4:
                            stack.scale((Float) this.scale.get(), (Float) this.scale.get(), (Float) this.scale.get());
                            stack.translate(0.4000000059604645, 0.10000000149011612, -0.5);
                            stack.rotate(Vector3f.YP.rotationDegrees(90.0F + (Float) this.swingPower.get() * 10.0F * anim));
                            stack.rotate(Vector3f.ZP.rotationDegrees(-60.0F));
                            stack.rotate(Vector3f.XP.rotationDegrees(-90.0F + (Float) this.swingPower.get() * 10.0F * anim));
                            break;
                        case 5:
                            stack.scale((Float) this.scale.get(), (Float) this.scale.get(), (Float) this.scale.get());
                            stack.translate(0.0, 0.0, -0.5);
                            stack.rotate(Vector3f.YP.rotationDegrees(15.0F * anim));
                            stack.rotate(Vector3f.ZP.rotationDegrees(-20.0F * anim));
                            stack.rotate(Vector3f.XP.rotationDegrees((-430.0F - (Float) this.swingPower.get()) * anim));
                            break;
                        default:
                            stack.scale((Float) this.scale.get(), (Float) this.scale.get(), (Float) this.scale.get());
                            runnable.run();
                    }

                }
            }
        }
    }
