package de.tired.api.guis.creditsandupdates;

import de.tired.api.util.shader.renderapi.AnimationUtil;

import de.tired.api.util.shader.renderapi.ColorUtil;

import de.tired.shaderloader.ShaderManager;
import de.tired.shaderloader.ShaderRenderer;
import de.tired.api.util.font.FontManager;
import de.tired.shaderloader.list.BackGroundShader;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;

/**
 * Ich weiß naming conventions anderes level wusste aber echt keinen namen dafür
 */
public class GUI extends GuiScreen {

    ArrayList<String> credits = new ArrayList<>();

    ArrayList<String> changeLog = new ArrayList<>();

    ArrayList<String> memes = new ArrayList<>();

    ArrayList<String> pics = new ArrayList<>();

    private int scrollAmount = 4;

    public float animationY;

    private int lastY;
    public GUI() {
        credits.add("Verus Scaffold Disabler by Kroko.");
        credits.add("Verus Speed by Kroko.");
        credits.add("Kroko fixxed code.");
        credits.add("Skush added obfuscation.");
        credits.add("Outuby made the website.");
        credits.add("https://www.geeksforgeeks.org/auto-complete-feature-using-trie/");
        credits.add("Hycraft glide by Auxy.");


        changeLog.add("Recoded velocity");
        changeLog.add("Improved KillAura");
        changeLog.add("Added ShaderESP & Recoded esp");
        changeLog.add("Added DROPSHADOW");

        memes.add("Join https://discord.gg/7g3PzFJmRx !");
        memes.add("Tired-Client.de");
        memes.add("Outuby got some sexy legs!");
        memes.add("haze.yt/phantom");
        memes.add("Snow - UwU");
        memes.add("Skush- If her age is on the clock, she is ready for the cock");
        memes.add("Outuby- ab 7 darfst du schieben");
        memes.add("Skush- ich hatte sex mit einer 4 jaehrigen - Felix");
        memes.add("Pry - Outuby hat einen faulen Eierkopf");
        memes.add("Skush- kinder toeten");
        memes.add("Snow- HELICOPTER HELICOPTER");
        memes.add("Reeperk- Minecraft wurde mit HTML Entwickelt");
        memes.add("Meme: George Floyd");
        memes.add("Free Tired Client b54 Crack - No Rat - 2021 ~ Princekin");
        memes.add("Skush- mir fehlen die leichen von den kindern die ich getötet habe");

        pics.add("george.png");
        pics.add("funny1.png");
        pics.add("Download.jpg");
        pics.add("schwere-beine.png");
        pics.add("lol.png");

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ShaderManager.shaderBy(BackGroundShader.class).doRender();

        ShaderRenderer.stopBlur();
        ShaderRenderer.startBlur();
        Gui.drawRect(40, 2, 920, 2220, Integer.MIN_VALUE);
        ShaderRenderer.stopBlur();
        ShaderRenderer.startBlur();
        Gui.drawRect(40, 2, 920, 2220, Integer.MIN_VALUE);
        ShaderRenderer.stopBlur();
        FontManager.bebasFBig.drawCenteredStringWithShadow("Credits", width / 2, 9 + animationY * 2, ColorUtil.fade(new Color(244, 244, 244), 12, 12).getRGB());



        int yAdd = 30;
        for (String string : credits) {
            this.animationY = (float) AnimationUtil.getAnimationState((double) this.animationY, scrollAmount, Math.max(3.6D, Math.abs((double) animationY - yAdd)) * 1.2);
            int wheel = Mouse.getDWheel();

            if (wheel < 0) {
                if (yAdd + 300 > -7 + yAdd) scrollAmount -= 16;
            } else if (wheel > 0) {
                scrollAmount += 34;
                if (scrollAmount > 0)
                    scrollAmount = 0;
            }
            yAdd += animationY;
            FontManager.robotoF.drawCenteredStringWithShadow(string, width / 2,  yAdd + 10, -1);
            yAdd += 20;
            lastY = (int) (yAdd + animationY);
        }
        FontManager.bebasFBig.drawCenteredStringWithShadow("Changelog", width / 2, lastY + 10,  ColorUtil.fade(new Color(244, 244, 244), 22, 12).getRGB());
        int yAdd2 = lastY + 30;
        for (String string : changeLog) {
            FontManager.robotoF.drawCenteredStringWithShadow(string, width / 2, 10 + yAdd2, -1);
            yAdd2 += 20;
        }

        int yAdd3 = yAdd2 + 120;
        for (String string : memes) {
            FontManager.bebasFBig.drawCenteredStringWithShadow(string, width / 2, 10 + yAdd3, -1);
            yAdd3 += 30;
        }
        int yAdd4 = yAdd3 + 50;
        for (String string : pics) {
            this.mc.getTextureManager().bindTexture(new ResourceLocation("client/" + string));
            Gui.drawModalRectWithCustomSizedTexture(width / 2 - 100, yAdd4 + 20, 240, 240, 240, 240, 240, 240);
            yAdd4 += 270;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }


}
