package io.github.liticane.monoxide.ui.screens.clickgui.monoxide.component.api;

import io.github.liticane.monoxide.ui.screens.clickgui.monoxide.MonoxideClickGuiScreen;
import io.github.liticane.monoxide.util.interfaces.Methods;
import io.github.liticane.monoxide.util.render.font.FontStorage;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

@Getter
@Setter
@SuppressWarnings("unused")
public class Component {

    public MonoxideClickGuiScreen parent;
    protected Minecraft mc = Minecraft.getMinecraft();

    public Component(MonoxideClickGuiScreen parent) {
        this.parent = parent;
    }

    public float x, y;
    public float width, height;

    public boolean expanded, dragging;

    public final FontRenderer fontRenderer20 = FontStorage.getInstance().findFont("SF UI", 20);
    public final FontRenderer fontRenderer24 = FontStorage.getInstance().findFont("SF UI", 24);

    public void init() { /* */ };

    public void draw(int mouseX, int mouseY) { /* */ };
    public void clicked(int mouseX, int mouseY, int button) { /* */ };
    public void released(int mouseX, int mouseY, int button) { /* */ };
    public void keyboard(char character, int keyCode) { /* */ };

}