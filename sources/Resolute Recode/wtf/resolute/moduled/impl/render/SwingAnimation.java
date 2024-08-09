package wtf.resolute.moduled.impl.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.impl.combat.KillAura;
import wtf.resolute.moduled.settings.impl.BooleanSetting;
import wtf.resolute.moduled.settings.impl.ModeSetting;
import wtf.resolute.moduled.settings.impl.SliderSetting;
import net.minecraft.util.math.vector.Vector3f;


@ModuleAnontion(name = "SwingAnimation", type = Categories.Render,server = "")
public class SwingAnimation extends Module {

    public ModeSetting animationMode = new ModeSetting("Мод", "NoAnim", "1", "2", "3", "4", "5","6","7","8","9","10");
    public SliderSetting swingPower = new SliderSetting("Сила", 5.0f, 1.0f, 10.0f, 0.05f);
    public SliderSetting swingSpeed = new SliderSetting("Скорость", 10.0f, 3.0f, 10.0f, 1.0f);
    public SliderSetting scale = new SliderSetting("Размер", 1.0f, 0.5f, 1.5f, 0.05f);
    public final BooleanSetting onlyAura = new BooleanSetting("Только с киллаурой", true);
    public KillAura killAura;
    public final SliderSetting right_x = new SliderSetting("RightX", 0.0F, -2.0f, 2.0f, 0.1F);
    public final SliderSetting right_y = new SliderSetting("RightY", 0.0F, -2.0f, 2.0f, 0.1F);
    public final SliderSetting right_z = new SliderSetting("RightZ", 0.0F, -2.0f, 2.0f, 0.1F);
    public final SliderSetting left_x = new SliderSetting("LeftX", 0.0F, -2.0f, 2.0f, 0.1F);
    public final SliderSetting left_y = new SliderSetting("LeftY", 0.0F, -2.0f, 2.0f, 0.1F);
    public final SliderSetting left_z = new SliderSetting("LeftZ", 0.0F, -2.0f, 2.0f, 0.1F);

    public SwingAnimation(KillAura killAura) {
        this.killAura = killAura;
        addSettings(animationMode, swingPower, swingSpeed, scale, onlyAura,right_x, right_y, right_z, left_x, left_y, left_z);
    }

    public void animationProcess(MatrixStack stack, float swingProgress, Runnable runnable) {
        float anim = (float) Math.sin(swingProgress * (Math.PI / 2) * 2);
        float anim2 = (float) Math.sin(swingProgress * (Math.PI / 2) * 4);
        if (onlyAura.get() && killAura.getTarget() == null) {
            runnable.run();
            return;
        }

        switch (animationMode.getIndex()) {
            case 0:
                stack.scale(scale.get(), scale.get(), scale.get());
                stack.translate(0.4f, 0.1f, -0.5);
                stack.rotate(Vector3f.YP.rotationDegrees(90));
                stack.rotate(Vector3f.ZP.rotationDegrees(-60));
                stack.rotate(Vector3f.XP.rotationDegrees(-90
                        - (swingPower.get() * 10) * anim));
                break;
            case 1:
                stack.scale(scale.get(), scale.get(), scale.get());
                stack.translate(0.0, 0, -0.5f);
                stack.rotate(Vector3f.YP.rotationDegrees(15 * anim));

                stack.rotate(Vector3f.ZP.rotationDegrees(-60 * anim));
                stack.rotate(Vector3f.XP.rotationDegrees((-90 - (swingPower.get())) * anim));
                break;
            case 2:
                stack.scale(scale.get(), scale.get(), scale.get());
                stack.translate(0.4f, 0, -0.5f);
                stack.rotate(Vector3f.YP.rotationDegrees(90));
                stack.rotate(Vector3f.ZP.rotationDegrees(-30));
                stack.rotate(Vector3f.XP.rotationDegrees(-90
                        - (swingPower.get() * 10) * anim));
                break;
            case 3:
                stack.scale(scale.get(), scale.get(), scale.get());
                stack.translate(0.4f, 0, 0);
                stack.rotate(Vector3f.YP.rotationDegrees(90));
                stack.rotate(Vector3f.ZP.rotationDegrees(-30));
                stack.rotate(Vector3f.XP.rotationDegrees(-90
                        - (swingPower.get() * 10) * anim2));
                break;
            case 4:
                stack.scale(scale.get(), scale.get(), scale.get());
                stack.translate(0.4f, 0, 0);
                stack.rotate(Vector3f.YP.rotationDegrees(40));
                stack.rotate(Vector3f.ZP.rotationDegrees(20));
                stack.rotate(Vector3f.XP.rotationDegrees(-90
                        - (swingPower.get() * 10) * anim));
                break;
            case 5:
                stack.scale(scale.get(), scale.get(), scale.get());
                stack.translate(0.3f, 0.0f, -0.3f);
                stack.rotate(Vector3f.XP.rotationDegrees(-90));
                stack.rotate(Vector3f.YP.rotationDegrees(-90 + 90 * anim)); // Горизонтальный удар по дуге
                stack.rotate(Vector3f.ZP.rotationDegrees(15 * anim));
                break;
            case 6:
                stack.scale(scale.get(), scale.get(), scale.get());
                stack.translate(0.3f, 0.2f, -0.4f);
                stack.rotate(Vector3f.YP.rotationDegrees(45 * anim)); // Горизонтальный удар
                stack.rotate(Vector3f.XP.rotationDegrees(-90 - (swingPower.get() * 10) * anim));
                break;
            case 7:
                stack.scale(scale.get(), scale.get(), scale.get());
                stack.translate(0.3f, 0.2f, -0.2f);
                stack.rotate(Vector3f.YP.rotationDegrees(90));
                stack.rotate(Vector3f.ZP.rotationDegrees(-30));
                stack.rotate(Vector3f.XP.rotationDegrees(-120 - (swingPower.get() * 20) * anim));
                break;
            case 8:
                stack.scale(scale.get(), scale.get(), scale.get());
                stack.translate(0.1f, -0.2f * anim, -0.3f);
                stack.rotate(Vector3f.XP.rotationDegrees(-120 + 30 * anim)); // Удар снизу вверх
                stack.rotate(Vector3f.YP.rotationDegrees(20 * anim));
                break;
            default:
                stack.scale(scale.get(), scale.get(), scale.get());
                runnable.run();
                break;
        }
    }

}
