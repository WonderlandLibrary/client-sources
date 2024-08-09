package mathax.client.systems.modules.experimental;

import mathax.client.eventbus.EventHandler;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Category;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class Sudoku extends Module {

    public Sudoku() {
        super(Categories.Experimental, Items.COMMAND_BLOCK, "Sudoku", "Self Destruct");
    }

    @EventHandler
    public boolean onActivate() {
        //it was at this moment he knew he fucked up
        throw new NullPointerException("Shit");
    }
}
