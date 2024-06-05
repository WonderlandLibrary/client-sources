package ClassSub;

import java.awt.*;
import org.jetbrains.annotations.*;
import cn.Hanabi.*;
import java.util.function.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import cn.Hanabi.utils.fontmanager.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.audio.*;
import java.io.*;
import cn.Hanabi.modules.*;

public class Class8 extends GuiScreen
{
    @NotNull
    public static Color PANEL_MAIN_COLOR;
    @NotNull
    public static Color PANEL_SECONDARY_COLOR;
    @NotNull
    private List<Class325> panels;
    
    
    public Class8() {
        this.panels = new ArrayList<Class325>();
    }
    
    public void init() {
        int n = 0;
        final int n2 = 100;
        for (final Category category : Category.values()) {
            final Class325 class325 = new Class325(category.toString(), n, 50, n2);
            Hanabi.INSTANCE.moduleManager.getModules().stream().filter(Class8::lambda$init$0).forEach((Consumer<? super Object>)Class8::lambda$init$1);
            if (class325.getButtons().size() > 0) {
                this.panels.add(class325);
                n += (int)(n2 * 1.2);
            }
        }
    }
    
    public void drawScreen(final int n, final int n2, final float n3) {
        final UnicodeFontRenderer comfortaa16 = Hanabi.INSTANCE.fontManager.comfortaa16;
        for (final Class325 class325 : this.panels) {
            Gui.drawRect(class325.getX() - 2, class325.getY() - 2, class325.getX() + class325.getWidth() + 2, class325.getY() + 20, Class118.rainbow(0));
            GL11.glLineWidth(class325.isDrag() ? 2.0f : 1.0f);
            Class145.drawRect(2, class325.getX() - 2, class325.getY() - 2, class325.getX() + class325.getWidth() + 2, class325.getY() + 20, Class8.PANEL_MAIN_COLOR.hashCode());
            comfortaa16.drawStringWithShadow(class325.getPanelName(), class325.getX() + 2.0f, class325.getY() + 5.0f, 16777215);
            for (int i = 0; i < class325.getButtons().size(); ++i) {
                final Class214 class326 = class325.getButtons().get(i);
                Gui.drawRect(class325.getX(), class325.getY() + 20 + 20 * i, class325.getX() + class325.getWidth(), class325.getY() + 20 * i + 40, Class8.PANEL_MAIN_COLOR.getRGB());
                comfortaa16.drawStringWithShadow(class326.getModule().getName(), class325.getX() + 5.0f, class325.getY() + 20 + 20 * i + 7, class326.getModule().isEnabled() ? 65280 : 16777215);
                class326.renderExtended(class325.getX() + class325.getWidth(), class325.getY() + 20 + 20 * i + 7);
            }
            if (class325.isDrag()) {
                class325.setX(n + class325.getDragX());
                class325.setY(n2 + class325.getDragY());
            }
        }
        super.drawScreen(n, n2, n3);
    }
    
    protected void mouseClicked(final int n, final int n2, final int n3) throws IOException {
        if (n3 != 0 && n3 != 1) {
            return;
        }
    Label_0398:
        for (int i = this.panels.size() - 1; i >= 0; --i) {
            final Class325 class325 = this.panels.get(i);
            if (class325.isHoverHead(n, n2) && n3 == 0) {
                class325.setDrag(true);
                class325.setDragX(class325.getX() - n);
                class325.setDragY(class325.getY() - n2);
                this.panels.remove(class325);
                this.panels.add(class325);
                break;
            }
            for (int j = 0; j < class325.getButtons().size(); ++j) {
                final Class214 class326 = class325.getButtons().get(j);
                if (class326.isHover(class325.getX(), class325.getY() + 20 + 20 * j, class325.getWidth(), 20, n, n2) && n3 == 0) {
                    class326.getModule().setState(!class326.getModule().getState());
                    this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("random.bow"), 1.0f));
                    break Label_0398;
                }
                if (class326.isHover(class325.getX(), class325.getY() + 20 + 20 * j, class325.getWidth(), 20, n, n2) && n3 == 1) {
                    class326.setExtended(!class326.isExtended());
                    this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("random.bow"), 1.0f));
                    break Label_0398;
                }
                if (class326.onMouseClick(n, n2, n3)) {
                    break Label_0398;
                }
            }
        }
        super.mouseClicked(n, n2, n3);
    }
    
    protected void mouseReleased(final int n, final int n2, final int n3) {
        final Iterator<Class325> iterator = this.panels.iterator();
        while (iterator.hasNext()) {
            iterator.next().setDrag(false);
        }
        super.mouseReleased(n, n2, n3);
    }
    
    public void onGuiClosed() {
        this.mc.entityRenderer.stopUseShader();
    }
    
    private static void lambda$init$1(final Class325 class325, final Mod mod) {
        class325.addButton(new Class214(class325, mod));
    }
    
    private static boolean lambda$init$0(final Category category, final Mod mod) {
        return mod.getCategory() == category;
    }
    
    static {
        Class8.PANEL_MAIN_COLOR = new Color(0, 0, 0, 200);
        Class8.PANEL_SECONDARY_COLOR = new Color(4359924);
    }
}
