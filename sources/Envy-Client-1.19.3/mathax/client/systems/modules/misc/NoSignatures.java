package mathax.client.systems.modules.misc;

import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class NoSignatures extends Module {
    public NoSignatures() {
        super(Categories.Chat, Items.COMMAND_BLOCK, "no-signatures", "Prevents the client from sending chat signature.");
    }
}
