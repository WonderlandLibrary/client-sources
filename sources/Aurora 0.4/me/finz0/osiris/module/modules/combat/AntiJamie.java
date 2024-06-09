package me.finz0.osiris.module.modules.combat;

import me.finz0.osiris.module.Module;

import java.net.URI;

/**
 *  Memeszz
 *  EZ TRANNY
 */
public class AntiJamie extends Module {
    public AntiJamie() {
        super("AntiJamie", Category.MISC, "Opens a ddoser for jamie!");
    }
    {
    }
    public void onEnable() {

 try {

     java.awt.Desktop.getDesktop().browse(URI.create("https://freestresser.to/"));
 } catch (Exception e) {

        }
    }
}
