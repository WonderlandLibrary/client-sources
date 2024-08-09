package ru.FecuritySQ.clickgui.elements;


import ru.FecuritySQ.font.Fonts;
import ru.FecuritySQ.module.дисплей.ClickGui;
import ru.FecuritySQ.option.imp.OptionBoolList;
import ru.FecuritySQ.option.imp.OptionBoolean;

import java.awt.*;
import java.util.stream.Collectors;

public class ElementBoolList extends Element {
    private ElementModule module;

    private OptionBoolList listSetting;
    public float elementOpenHeight = 15;

    public boolean open;

    public ElementBoolList(ElementModule elementModule, OptionBoolList option){
        this.module = elementModule;
        this.listSetting = option;
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        this.width = this.module.width;
        this.height = 18;
        int x = (int) getX();
        int y = (int) getY();
        int width = (int) getWidth();
        int height = (int) getHeight();
        String selectedText = listSetting.get().stream().filter(OptionBoolean::get).map(booleanSetting -> booleanSetting.name.substring(0, 2)).collect(Collectors.toList()).toString().replace("[", "").replace("]", "");
        int textColor = Color.GRAY.getRGB();
        Color color = ClickGui.color.get();
        Fonts.GREYCLIFF.drawString(stack,listSetting.name + ": ", x + 2.5F, y + 2.5F, Color.GRAY.getRGB());
        String substring = selectedText.substring(0, Math.min(selectedText.length(), 18)) + (selectedText.length() > 18 ? "..." : "");

        if(listSetting.get().stream().filter(OptionBoolean::get).count() > 3){
            substring = "***";
        }

        Fonts.GREYCLIFF.drawString(stack, substring, x + module.panel.width - Fonts.GREYCLIFF.getStringWidth(substring) - 3, y + 2F, color.getRGB());

        if (open) {
            handleRender(x, (int) (y + getHeight() + 4), width, textColor);
        }
    }

    private void handleRender(int x, int y, int width, int textColor) {
        Color color = ClickGui.color.get();
        this.height = getHeightWithExpand();
        for (OptionBoolean e : listSetting.get().stream().collect(Collectors.toList())) {
            Fonts.GREYCLIFF.drawCenteredString(stack, e.name, x + (module.panel.width / 2F), y, e.get() ? color.getRGB() : textColor);
            y += (elementOpenHeight - 3);
        }
    }

    private void handleClick(int mouseX, int mouseY, int x, int y, int width) {
        for (OptionBoolean e : this.listSetting.get()) {
            if (mouseX >= x && mouseY >= y && mouseX <= x + width && mouseY <= y + elementOpenHeight - 3) {
                listSetting.setting(e.name).set(!e.get());
            }

            y += elementOpenHeight - 3;
        }
    }


    public int getHeightWithExpand() {
        return (int) (getHeight() + (int) listSetting.list.size() * (elementOpenHeight - 3) + 4);
    }

    @Override
    public void mouseClicked(int x, int y, int button) {

        if(collided(x ,y ) && button == 1){
            this.open = !this.open;
        }

        if (open) {
            handleClick(x, y, (int) getX(), (int) (getY() + 18), (int) getWidth());
        }
        super.mouseClicked(x, y, button);
    }
}
