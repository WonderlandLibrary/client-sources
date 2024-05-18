/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature;

import de.dietrichpaul.clientbase.feature.gui.font.FontAtlas;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.IOException;

public class FontList implements SimpleSynchronousResourceReloadListener {
    public FontAtlas verdana;
    public FontAtlas amongUs;

    public FontList() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(this);
    }

    @Override
    public void reload(ResourceManager manager) {
        try {
            verdana = new FontAtlas(manager, "verdana");
            amongUs = new FontAtlas(manager, "amongus");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load fonts", e);
        }
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier("clientbase", "reload_fonts");
    }

    public FontAtlas getVerdana() {
        return verdana;
    }

    public FontAtlas getAmongUs() {
        return amongUs;
    }
}
