package com.boomer.client.module.modules.visuals;

import java.awt.Color;

import com.boomer.client.module.Module;

/**
 * made by oHare for BoomerWare
 *
 * @since 7/18/2019
 **/
public class AntiHurtCam extends Module {
    public AntiHurtCam() {
        super("AntiHurtCam", Category.VISUALS, new Color(0xFF646C).getRGB());
        setRenderlabel("Anti Hurt Cam");
        setDescription("Removes hurt cam effects");
    }
    @Override
    public boolean hasSubscribers() {
        return false;
    }
}
