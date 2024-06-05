package ClassSub;

import net.minecraft.client.gui.*;
import cn.Hanabi.injection.interfaces.*;
import net.minecraft.util.*;
import cn.Hanabi.modules.*;
import java.util.*;
import cn.Hanabi.modules.Player.*;
import cn.Hanabi.*;
import java.io.*;

public class Class208 extends GuiScreen
{
    Class33 sideBar;
    List<Class77> windows;
    
    
    public Class208() {
        this.windows = new ArrayList<Class77>();
        try {
            this.sideBar = new Class33();
            for (int i = 0; i < Category.values().length; ++i) {
                this.windows.add(new Class77(Category.values()[i]));
            }
        }
        catch (Throwable t) {}
    }
    
    public void initGui() {
        try {
            if (this.mc.theWorld != null) {
                ((IEntityRenderer)this.mc.entityRenderer).loadShader2(new ResourceLocation("shaders/post/blur.json"));
            }
            final Iterator<Mod> iterator = ModManager.getModList().iterator();
            while (iterator.hasNext()) {
                iterator.next().modButton = null;
            }
            final Iterator<Class77> iterator2 = this.windows.iterator();
            while (iterator2.hasNext()) {
                iterator2.next().createModUI();
            }
            Class77.booleanValueMap.clear();
        }
        catch (Throwable t) {
            Class200.tellPlayer("§b[Hanabi]§a加载Blur出现异常，建议关闭快速渲染�??");
        }
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        if (ModManager.getModule("StaffAnalyzer").isEnabled() && StaffAnalyzer.ui != null) {
            StaffAnalyzer.ui.mouseListener(n, n2);
        }
        if (Hanabi.INSTANCE.sbm != null) {
            Hanabi.INSTANCE.sbm.moveWindow(n, n2);
        }
        try {
            this.sideBar.draw();
            for (final Class81 class81 : this.sideBar.button) {
                if (class81.active) {
                    for (final Class77 class82 : this.windows) {
                        if (class82.category.toString().equals(class81.title)) {
                            class82.draw(n, n2);
                            break;
                        }
                    }
                    break;
                }
            }
        }
        catch (Throwable t) {}
    }
    
    protected void mouseReleased(final int n, final int n2, final int n3) {
    }
    
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        try {
            this.sideBar.onMouseClick(n, n2);
            for (final Class81 class81 : this.sideBar.button) {
                if (class81.active) {
                    for (final Class77 class82 : this.windows) {
                        if (class82.category.toString().equals(class81.title)) {
                            class82.onMouseClick(n, n2);
                            break;
                        }
                    }
                    break;
                }
            }
        }
        catch (Throwable t) {}
    }
    
    private boolean isHovering(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return n > n3 && n < n5 && n2 > n4 && n2 < n6;
    }
    
    public void onGuiClosed() {
        try {
            this.mc.entityRenderer.stopUseShader();
        }
        catch (Throwable t) {
            Class200.tellPlayer("§b[Hanabi]§a加载Blur出现异常，建议关闭快速渲染�??");
        }
    }
}
