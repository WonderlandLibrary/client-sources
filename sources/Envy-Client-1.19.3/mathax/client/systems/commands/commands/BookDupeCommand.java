package mathax.client.systems.commands.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import mathax.client.systems.commands.Command;
import mathax.client.utils.player.FindItemResult;
import mathax.client.utils.player.InvUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class BookDupeCommand extends Command {
    private static final List<String> DUPE_PAGES;

    static {
        List<String> pages = new ArrayList<>();
        pages.add(0, "ࠀ".repeat(21845));
        for (int i = 1; i < 40; i++) pages.add(i, "a".repeat(256));

        DUPE_PAGES = pages;
    }

    public BookDupeCommand() {
        super("dupe", "Dupes using a held, writable book.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.executes(context -> {
            FindItemResult book = InvUtils.findInHotbar(Items.WRITABLE_BOOK);
            if (book.getHand() == null) error("No book found, you must be holding a writable book!");
            else {
                int i = book.isMainHand() ? mc.player.getInventory().selectedSlot : 40;
                mc.player.networkHandler.sendPacket(new BookUpdateC2SPacket(i, DUPE_PAGES, Optional.of("Dupe Book")));
            }

            return SINGLE_SUCCESS;
        });
    }
}
