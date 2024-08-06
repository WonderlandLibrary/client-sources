package com.shroomclient.shroomclientnextgen.modules.impl.render;

import com.shroomclient.shroomclientnextgen.config.ConfigHide;
import com.shroomclient.shroomclientnextgen.config.ConfigMode;
import com.shroomclient.shroomclientnextgen.config.ConfigOption;
import com.shroomclient.shroomclientnextgen.events.SubscribeEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderScreenEvent;
import com.shroomclient.shroomclientnextgen.events.impl.RenderTickEvent;
import com.shroomclient.shroomclientnextgen.events.impl.ScreenClickedEvent;
import com.shroomclient.shroomclientnextgen.modules.Module;
import com.shroomclient.shroomclientnextgen.modules.ModuleCategory;
import com.shroomclient.shroomclientnextgen.modules.RegisterModule;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.RenderUtil;
import com.shroomclient.shroomclientnextgen.util.ThemeUtil;
import java.awt.*;
import java.util.Objects;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

@RegisterModule(
    name = "Bedwars Util",
    uniqueId = "bedwarsutil",
    description = "",
    category = ModuleCategory.Render
)
public class BedwarsUtil extends Module {

    public enum Mode {
        Rounded,
        Nerdyass,
    }

    @ConfigMode
    @ConfigOption(name = "Mode", description = "", order = 1)
    public Mode mode = Mode.Nerdyass;

    @ConfigHide
    @ConfigOption(
        name = "X Position",
        description = "",
        min = 1,
        max = 800,
        order = 102
    )
    public static Integer xPos = 10;

    @ConfigHide
    @ConfigOption(
        name = "Y Position",
        description = "",
        min = 1,
        max = 600,
        order = 101
    )
    public static Integer yPos = 10;

    @Override
    protected void onEnable() {
        prot = "false";
        sharp = false;
        protColor = new Color(255, 0, 0);
        sharpColor = new Color(255, 0, 0);
    }

    @Override
    protected void onDisable() {
        prot = "false";
        sharp = false;
        protColor = new Color(255, 0, 0);
        sharpColor = new Color(255, 0, 0);
    }

    public String prot = "false";
    public boolean sharp = false;
    public Color protColor = new Color(255, 0, 0);
    public Color sharpColor = new Color(255, 0, 0);

    @SubscribeEvent
    public void onRenderTick(RenderTickEvent e) {
        RenderUtil.setContext(e.drawContext);

        sharp = getSwordWithSharpness() != -1;
        int protLevel = getArmorProtectionLevel();
        prot = protLevel > 0 ? String.valueOf(protLevel) : "false";
        switch (mode) {
            case Rounded -> {
                int boxWidth = (int) (RenderUtil.getFontWidth(
                        "Sharpness: " + sharp
                    ) +
                    2);
                int boxHeight = 33;

                square = new float[] { xPos, yPos, boxWidth, boxHeight };

                RenderUtil.drawCenteredRoundedRect(
                    xPos,
                    yPos,
                    boxWidth + 3,
                    boxHeight,
                    5,
                    new Color(20, 20, 20, 100),
                    false,
                    false,
                    false,
                    false
                );
                RenderUtil.drawCenteredRoundedGlow(
                    xPos,
                    yPos,
                    boxWidth + 3,
                    boxHeight,
                    5,
                    5,
                    ThemeUtil.themeColors()[0],
                    false,
                    false,
                    false,
                    false
                );
                RenderUtil.drawTextShadow(
                    "Bedwars Utils",
                    xPos - 30,
                    yPos - 15,
                    ThemeUtil.themeColors()[0]
                );
                RenderUtil.drawTextShadow(
                    "Prot: " + prot,
                    xPos - 39,
                    yPos - 5,
                    ThemeUtil.themeColors()[0]
                );
                RenderUtil.drawTextShadow(
                    "Sharpness: " + sharp,
                    xPos - 40,
                    yPos + 5,
                    ThemeUtil.themeColors()[0]
                );
            }
            case Nerdyass -> {
                square = new float[] {
                    xPos - 25,
                    yPos - 5,
                    RenderUtil.getFontWidth("Sharpness: " + sharp) + 20,
                    30,
                };

                protColor = Objects.equals(prot, "false")
                    ? new Color(255, 0, 0)
                    : new Color(0, 255, 0);
                sharpColor = sharp
                    ? new Color(0, 255, 0)
                    : new Color(255, 0, 0);
                RenderUtil.drawTextShadow("-", xPos - 20, yPos, Color.red);
                RenderUtil.drawTextShadow("-", xPos - 20, yPos + 10, Color.red);
                RenderUtil.drawTextShadow(
                    "Prot: ",
                    xPos - 12,
                    yPos,
                    Color.white
                );
                RenderUtil.drawTextShadow(
                    "Sharpness: ",
                    xPos - 12,
                    yPos + 10,
                    Color.white
                );
                RenderUtil.drawTextShadow(
                    prot,
                    xPos - 14 + (int) RenderUtil.getFontWidth("Prot: "),
                    yPos,
                    protColor
                );
                RenderUtil.drawTextShadow(
                    String.valueOf(sharp),
                    xPos - 14 + (int) RenderUtil.getFontWidth("Sharpness: "),
                    yPos + 10,
                    sharpColor
                );
            }
        }
    }

    static int getSwordWithSharpness() {
        if (C.p() != null) {
            for (int i = 0; i < 9; ++i) {
                ItemStack stack = C.p().getInventory().getStack(i);
                if (stack != null && stack.getItem() instanceof SwordItem) {
                    if (
                        EnchantmentHelper.getLevel(
                            Enchantments.SHARPNESS,
                            stack
                        ) >
                        0
                    ) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    static int getArmorProtectionLevel() {
        if (C.p() != null) {
            int highestProtection = 0;
            ItemStack[] armorItems = {
                C.p().getInventory().getArmorStack(0),
                C.p().getInventory().getArmorStack(1),
                C.p().getInventory().getArmorStack(2),
                C.p().getInventory().getArmorStack(3),
            };

            for (ItemStack stack : armorItems) {
                if (stack != null && stack.getItem() instanceof ArmorItem) {
                    int protectionLevel = EnchantmentHelper.getLevel(
                        Enchantments.PROTECTION,
                        stack
                    );
                    if (protectionLevel > highestProtection) {
                        highestProtection = protectionLevel;
                    }
                }
            }
            return highestProtection;
        }
        return 0;
    }

    // dragging code
    boolean dragging = false;
    double mouseXold = 0;
    double mouseYold = 0;
    double oldHudX = 0;
    double oldHudY = 0;

    public static float[] square = new float[] { 0, 0, 0, 0 };

    @SubscribeEvent
    public void ChatEvent(ScreenClickedEvent e) {
        if (C.mc.currentScreen instanceof ChatScreen) {
            if (
                e.mouseX >= square[0] &&
                e.mouseX <= square[0] + square[2] &&
                e.mouseY >= square[1] &&
                e.mouseY <= square[1] + square[3]
            ) {
                dragging = e.button == 0;
                mouseXold = e.mouseX;
                mouseYold = e.mouseY;

                oldHudX = xPos;
                oldHudY = yPos;
            }

            if (!e.down && e.button == 0) {
                dragging = false;
            }
        }
    }

    @SubscribeEvent
    public void onDrawScreen(RenderScreenEvent e) {
        if (C.mc.currentScreen instanceof ChatScreen) {
            double mouseMovedX = e.mouseX - mouseXold;
            double mouseMovedY = e.mouseY - mouseYold;

            if (dragging) {
                xPos = (int) (oldHudX + mouseMovedX);
                yPos = (int) (oldHudY + mouseMovedY);

                RenderUtil.drawRoundedGlow(
                    square[0],
                    square[1],
                    square[2],
                    square[3],
                    0,
                    5,
                    new Color(255, 255, 255),
                    200,
                    false,
                    false,
                    false,
                    false
                );
            }
        }
    }
}
