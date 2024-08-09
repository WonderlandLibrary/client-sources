/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.hud.elements;

import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudElement;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;

public class ImageHud extends HudElement {



    public static final HudElementInfo<ImageHud> INFO = new HudElementInfo<>(Hud.GROUP, "Image", "Draws a specified image on the screen...", ImageHud::new);


    public ImageHud() {
        super(INFO);
        MeteorClient.EVENT_BUS.subscribe(this);
    }


}
