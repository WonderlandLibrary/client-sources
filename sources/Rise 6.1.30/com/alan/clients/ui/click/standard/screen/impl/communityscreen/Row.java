package com.alan.clients.ui.click.standard.screen.impl.communityscreen;

import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.ui.click.standard.RiseClickGUI;
import com.alan.clients.ui.click.standard.screen.impl.CommunityScreen;
import com.alan.clients.util.Accessor;
import com.alan.clients.util.dragging.Mouse;
import com.alan.clients.util.gui.GUIUtil;
import com.alan.clients.util.gui.ScrollUtil;
import com.alan.clients.util.vector.Vector2f;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;

import static com.alan.clients.ui.click.standard.screen.impl.CommunityScreen.PADDING;

@Getter
@Setter
public class Row extends ArrayList<Element> implements Accessor {
    private String name;
    private static float SPACER = 10;
    private Vector2f position;
    private ScrollUtil scrollUtil = new ScrollUtil();

    public Row(int initialCapacity, String name) {
        super(initialCapacity);
        this.name = name;
    }

    public Row(String name) {
        this.name = name;
    }

    public Row(@NotNull Collection<? extends Element> c, String name) {
        super(c);
        this.name = name;
    }

    public void render(Vector2f position) {
        try {
            RiseClickGUI clickGUI = getClickGUI();
            this.position = new Vector2f(position.x, position.y);
            Fonts.MAIN.get(18, Weight.REGULAR).draw(name, this.position.x, this.position.y, Color.WHITE.getRGB());

            String size = this.size() + "";
            Fonts.MAIN.get(18, Weight.REGULAR).draw(size, this.position.x + Fonts.MAIN.get(18, Weight.REGULAR).width(name) + PADDING / 2f, this.position.y, getTheme().getFirstColor().getRGB());

            if (!this.isEmpty()) {
                scrollUtil.onRender(over());
                scrollUtil.setMax(-this.size() * (PADDING + get(0).getScale().x) + getClickGUI().scale.x - getClickGUI().sidebar.sidebarWidth - PADDING);

                if (!scrollUtil.isActive()) {
                    scrollUtil.target = Math.round(scrollUtil.target / (PADDING + get(0).getScale().x)) * (PADDING + get(0).getScale().x);
                }

                this.position.y += PADDING + Fonts.MAIN.get(18, Weight.REGULAR).height();
                this.position.x += scrollUtil.getScroll();

                for (Element element : this) {
                    if (!((this.position.x > clickGUI.getPosition().x + clickGUI.getScale().x) ||
                            (this.position.x + element.getScale().x < clickGUI.position.x + clickGUI.sidebar.sidebarWidth))) {
                        element.render(this.position);
                    }

                    this.position.x += PADDING + element.getScale().x;
                }
            }

            this.position = new Vector2f(position.x, position.y);
        } catch (ConcurrentModificationException concurrentModificationException) {
            concurrentModificationException.printStackTrace();
        }
    }

    public int getHeight() {
        return (int) ((isEmpty() ? 0 : get(0).getScale().getY()) + PADDING + Fonts.MAIN.get(18, Weight.REGULAR).height());
    }

    public void onClick(int mouseX, int mouseY, int mouseButton) {
        for (Element element : this) {
            element.onClick(mouseX, mouseY, mouseButton);
        }
    }

    public void onScroll() {
        if (position == null) return;

        if (over()) {
            CommunityScreen.SCROLL = false;
        }
    }

    public boolean over() {
        if (isEmpty()) return false;
        return GUIUtil.mouseOver(getClickGUI().position, getClickGUI().scale, Mouse.getMouse()) && GUIUtil.mouseOver(
                new Vector2f((float) (getClickGUI().position.x + getClickGUI().sidebar.sidebarWidth), position.y),
                new Vector2f((float) (getClickGUI().scale.x - getClickGUI().sidebar.sidebarWidth), get(0).getScale().y + PADDING * 2 + 10),
                Mouse.getMouse());
    }

    public void init() {
        getScrollUtil().setScroll(0);
        getScrollUtil().setTarget(0);
    }
}
