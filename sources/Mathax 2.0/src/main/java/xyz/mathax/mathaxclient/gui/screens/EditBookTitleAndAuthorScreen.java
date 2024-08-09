package xyz.mathax.mathaxclient.gui.screens;

import xyz.mathax.mathaxclient.systems.themes.Theme;
import xyz.mathax.mathaxclient.gui.WindowScreen;
import xyz.mathax.mathaxclient.gui.widgets.containers.WTable;
import xyz.mathax.mathaxclient.gui.widgets.input.WTextBox;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.BookUpdateC2SPacket;
import net.minecraft.util.Hand;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class EditBookTitleAndAuthorScreen extends WindowScreen {
    private final ItemStack itemStack;

    private final Hand hand;

    public EditBookTitleAndAuthorScreen(Theme theme, ItemStack itemStack, Hand hand) {
        super(theme, "Edit title & author");
        this.itemStack = itemStack;
        this.hand = hand;
    }

    @Override
    public void initWidgets() {
        WTable table = add(theme.table()).expandX().widget();

        table.add(theme.label("Title"));
        WTextBox title = table.add(theme.textBox(itemStack.getOrCreateNbt().getString("title"))).minWidth(220).expandX().widget();
        table.row();

        table.add(theme.label("Author"));
        WTextBox author = table.add(theme.textBox(itemStack.getNbt().getString("author"))).minWidth(220).expandX().widget();
        table.row();

        table.add(theme.button("Done")).expandX().widget().action = () -> {
            itemStack.getNbt().putString("author", author.get());
            itemStack.getNbt().putString("title", title.get());

            BookScreen.Contents contents = new BookScreen.WrittenBookContents(itemStack);
            List<String> pages = new ArrayList<>(contents.getPageCount());
            for (int i = 0; i < contents.getPageCount(); i++) {
                pages.add(contents.getPage(i).getString());
            }

            mc.getNetworkHandler().sendPacket(new BookUpdateC2SPacket(hand == Hand.MAIN_HAND ? mc.player.getInventory().selectedSlot : 40, pages, Optional.of(title.get())));

            close();
        };
    }
}
