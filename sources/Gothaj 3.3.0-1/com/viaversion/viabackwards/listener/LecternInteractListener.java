package com.viaversion.viabackwards.listener;

import com.viaversion.viabackwards.BukkitPlugin;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.Protocol1_13_2To1_14;
import com.viaversion.viaversion.bukkit.listeners.ViaBukkitListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Lectern;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class LecternInteractListener extends ViaBukkitListener {
   public LecternInteractListener(BukkitPlugin plugin) {
      super(plugin, Protocol1_13_2To1_14.class);
   }

   @EventHandler(
      priority = EventPriority.HIGHEST,
      ignoreCancelled = true
   )
   public void onLecternInteract(PlayerInteractEvent event) {
      Block block = event.getClickedBlock();
      if (block != null && block.getType() == Material.LECTERN) {
         Player player = event.getPlayer();
         if (this.isOnPipe(player)) {
            Lectern lectern = (Lectern)block.getState();
            ItemStack book = lectern.getInventory().getItem(0);
            if (book != null) {
               BookMeta meta = (BookMeta)book.getItemMeta();
               ItemStack newBook = new ItemStack(Material.WRITTEN_BOOK);
               BookMeta newBookMeta = (BookMeta)newBook.getItemMeta();
               newBookMeta.setPages(meta.getPages());
               newBookMeta.setAuthor("an upsidedown person");
               newBookMeta.setTitle("buk");
               newBook.setItemMeta(newBookMeta);
               player.openBook(newBook);
               event.setCancelled(true);
            }
         }
      }
   }
}
