package de.tired.api.userinterface.element;

import de.tired.api.util.font.CustomFont;
import de.tired.api.util.font.FontManager;
import de.tired.api.userinterface.UI;

public class ElementText {

    private UI ui;

    private int x, y;

    private String text;

    private CustomFont customFont;

    public enum FONT {
        entypo, bebasF, bebasFBig, notosansF,notosansFBig, IBMPlexSans, SFPRO, SFPROBig, robotoF, confortaa, confortaaBig, robotoT
    }

    public FONT font;

    public ElementText(UI ui, FONT font, int x, int y, String text) {
        this.ui = ui;
        this.x = x;
        this.y = y;
        this.text = text;
        this.font = font;
        switch (font) {
            case SFPRO:
                customFont = FontManager.SFPRO;
                break;
            case bebasF:
                customFont = FontManager.bebasF;
                break;
            case bebasFBig:
                customFont = FontManager.bebasFBig;
                break;
            case entypo:
                customFont = FontManager.entypo;
                break;
            case robotoF:
                customFont = FontManager.robotoF;
                break;
            case confortaa:
                customFont = FontManager.confortaa;
                break;
            case IBMPlexSans:
                customFont = FontManager.IBMPlexSans;
                break;
            case robotoT:
                customFont = FontManager.robotoT;
                break;
            case SFPROBig:
                customFont = FontManager.SFPROBig;
                break;
            case notosansF:
                customFont = FontManager.notosansF;
                break;
            case confortaaBig:
                customFont = FontManager.confortaaBig;
                break;
            case notosansFBig:
                customFont = FontManager.notosansFBig;
                break;
        }
    }


    public void renderText() {

        FontManager.SFPRO.drawString(text, x, y, -1);

    }


}