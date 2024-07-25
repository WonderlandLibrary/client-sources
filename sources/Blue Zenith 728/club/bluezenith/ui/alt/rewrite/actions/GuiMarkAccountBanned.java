package club.bluezenith.ui.alt.rewrite.actions;

import club.bluezenith.ui.button.ScrollingSelector;
import net.minecraft.client.gui.GuiScreen;

public class GuiMarkAccountBanned extends GuiScreen { //todo do something w dis

    private static final String[] oneToTwelve = new String[12],
                                  oneToTwentyFour = new String[24],
                                  oneToThirty = new String[30],
                                  oneToSixty = new String[60];

    public GuiMarkAccountBanned(GuiScreen parent) {
        this.parentScreen = parent;
    }

    @Override
    public void initGui() {
        int count = 5;
        int widthPerButton = 50;
        this.buttonList.add(new ScrollingSelector(0, width / 2 - widthPerButton * count, height / 2, "Years", "1", "2"));
        this.buttonList.add(new ScrollingSelector(0, width / 2 - widthPerButton * (count - 1), height / 2, "Months", oneToTwelve));
        this.buttonList.add(new ScrollingSelector(0, width / 2 - widthPerButton * (count - 2), height / 2, "Days", oneToThirty));
        this.buttonList.add(new ScrollingSelector(0, width / 2 - widthPerButton * (count - 3), height / 2, "Hours", oneToTwentyFour));
        this.buttonList.add(new ScrollingSelector(0, width / 2 - widthPerButton * (count - 4), height / 2, "Minutes", oneToSixty));
        this.buttonList.add(new ScrollingSelector(0, width / 2, height / 2, "Seconds", oneToSixty));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    static {
        for (int i = 0; i <= 60; i++) {
            final String number = String.valueOf(i);
            if(i < oneToTwelve.length) oneToTwelve[i] = number;
            if(i < oneToTwentyFour.length) oneToTwentyFour[i] = number;
            if(i < oneToThirty.length) oneToThirty[i] = number;
            if(i < oneToSixty.length) oneToSixty[i] = number;
        }
    }
}
