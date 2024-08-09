package wtf.resolute.utiled.render;

import com.mojang.blaze3d.systems.RenderSystem;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import wtf.resolute.utiled.client.IMinecraft;
import wtf.resolute.utiled.math.Cvect;

import java.lang.reflect.Method;

@UtilityClass
public class ScreenUtils implements IMinecraft {
    public boolean isHovered(double x, double y, double width, double height, double mouseX, double mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    public float getMiddleOfBox(double objectHeight,double boxHeight) {
        return (float) (boxHeight / 2f - objectHeight / 2f);
    }

    public void startTranslate(Cvect currentTranslate) {
        RenderSystem.translated(currentTranslate.x,
                currentTranslate.y, 0);
    }

    public void endTranslate(Cvect currentTranslate) {
        RenderSystem.translated(-currentTranslate.x,
                -currentTranslate.y, 0);
    }

    public double[] worldToScreen(double x, double y, double z) {
        if (Minecraft.getInstance().currentScreen != null && !(Minecraft.getInstance().currentScreen instanceof ChatScreen) && (Minecraft.getInstance().currentScreen == null)) {
            return null;
        } else {
            try {
                Vector3d camera_pos = Minecraft.getInstance().getRenderManager().info.getProjectedView();
                Quaternion camera_rotation_conj = Minecraft.getInstance().getRenderManager().getCameraOrientation().copy();
                camera_rotation_conj.conjugate();
                Vector3f result3f = new Vector3f((float)(camera_pos.x - x), (float)(camera_pos.y - y), (float)(camera_pos.z - z));
                result3f.transform(camera_rotation_conj);
                GameRenderer gameRenderer = Minecraft.getInstance().gameRenderer;
                Method method = null;
                Method[] var11 = GameRenderer.class.getDeclaredMethods();
                int var12 = var11.length;

                for(int var13 = 0; var13 < var12; ++var13) {
                    Method m = var11[var13];
                    if (m.getParameterCount() == 3 && m.getParameterTypes()[2] == Boolean.TYPE && m.getParameterTypes()[1] == Float.TYPE && m.getParameterTypes()[0] == ActiveRenderInfo.class && m.getReturnType() == Double.TYPE) {
                        method = m;
                    }
                }

                method.setAccessible(true);
                float fov = ((Double)method.invoke(gameRenderer, Minecraft.getInstance().getRenderManager().info, 1, true)).floatValue();
                float half_height = (float)Minecraft.getInstance().getMainWindow().getScaledHeight() / 2.0F;
                float scale_factor = half_height / (result3f.getZ() * (float)Math.tan(Math.toRadians((double)(fov / 2.0F))));
                if (result3f.getZ() < 0.0F) {
                    return new double[]{(double)(-result3f.getX() * scale_factor + (float)(Minecraft.getInstance().getMainWindow().getScaledWidth() / 2)), (double)((float)(Minecraft.getInstance().getMainWindow().getScaledHeight() / 2) - result3f.getY() * scale_factor)};
                }
            } catch (Exception var15) {
                var15.printStackTrace();
            }

            return null;
        }
    }

    public Vector2f project(double x, double y, double z) {
        double[] projected = worldToScreen(x,y,z);

        if (projected != null) {
            return new Vector2f((float) projected[0], (float) projected[1]);
        }
        return new Vector2f(-1000,-1000);
    }

    public Cvect getMouse() {
        return new Cvect(
                (float) (mc.mouseHelper.getMouseX() / mc.getMainWindow().getScaleFactor()),
                (float) (mc.mouseHelper.getMouseY() / mc.getMainWindow().getScaleFactor())
        );
    }
}

