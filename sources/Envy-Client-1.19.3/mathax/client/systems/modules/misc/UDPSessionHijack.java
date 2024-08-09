package mathax.client.systems.modules.misc;

import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class UDPSessionHijack extends Module {
    public UDPSessionHijack() {
        super(Categories.Misc, Items.DIRT, "UDP Session Hijack", "CRYSTAL || UDP Session Hijacking attack on a Minecraft server uses a carefully crafted malicious packet to misc vulnerabilities and disrupt the server's session.");
    }
}
