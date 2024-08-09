package mathax.client.systems.hud.modules;

import mathax.client.systems.hud.DoubleTextHudElement;
import mathax.client.systems.hud.HUD;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class BiomeHud extends DoubleTextHudElement {
    private final BlockPos.Mutable blockPos = new BlockPos.Mutable();

    public BiomeHud(HUD hud) {
        super(hud, "biome", "Displays the biome you are in.", false);
    }

    @Override
    protected String getLeft() {
        return "Biome: ";
    }

    @Override
    protected String getRight() {
        if (isInEditor()) return "Plains";

        blockPos.set(mc.player.getX(), mc.player.getY(), mc.player.getZ());
        Identifier id = mc.world.getRegistryManager().get(RegistryKeys.BIOME).getId(mc.world.getBiome(blockPos).value());
        if (id == null) return "Unknown";

        return Arrays.stream(id.getPath().split("_")).map(StringUtils::capitalize).collect(Collectors.joining(" "));
    }
}
