package de.tired.interfaces;

import de.tired.api.util.font.CustomFont;

import java.awt.*;

public interface FHook {

    CustomFont fontRenderer = new CustomFont(new Font("Arial", Font.PLAIN, 20), true, true);
    CustomFont big = new CustomFont(new Font("Arial", Font.BOLD, 40), true, true);
    CustomFont big2 = new CustomFont(new Font("Arial", Font.PLAIN, 30), true, true);
    CustomFont login = new CustomFont(new Font("Arial", Font.BOLD, 33), true, true);
    CustomFont fontRenderer2 = new CustomFont(new Font("Arial", Font.BOLD, 20), true, true);
    CustomFont fontRenderer2B = new CustomFont(new Font("Arial", Font.BOLD, 14), true, true);
    CustomFont fontRenderer3 = new CustomFont(new Font("Arial", Font.PLAIN, 13), true, true);
    CustomFont github = new CustomFont(new Font("Arial", Font.PLAIN, 10), true, true);
    CustomFont fontRenderer4 = new CustomFont(new Font("Arial", Font.PLAIN, 7), true, true);
}
