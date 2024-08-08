package net.futureclient.client;

import net.futureclient.client.modules.miscellaneous.InvCleaner;

public class Je extends XB
{
    public Je() {
        super(new String[] { "InvCleaner", "InvClean", "InventoryClean", "InventoryCleaner", "InventoryCleanerList", "IC" });
    }
    
    @Override
    public String M(final String[] array) {
        if (array.length < 2) {
            return null;
        }
        final String s = array[0];
        final StringBuilder sb = new StringBuilder();
        int i = 1;
        int n = 1;
        while (i < array.length) {
            sb.append(array[n]);
            final StringBuilder sb2 = sb;
            ++n;
            sb2.append(" ");
            i = n;
        }
        final String s2 = (array.length == 2) ? array[1] : sb.toString().trim();
        final InvCleaner invCleaner = (InvCleaner)pg.M().M().M((Class)tc.class);
        if (s.equalsIgnoreCase("add") || s.equalsIgnoreCase("a")) {
            if (s2.equalsIgnoreCase("current")) {
                if (this.k.player.inventory.getCurrentItem().isEmpty()) {
                    return "You are not holding an item.";
                }
                if (invCleaner.d.contains(this.k.player.inventory.getCurrentItem().getDisplayName().toLowerCase())) {
                    return new StringBuilder().insert(0, this.k.player.inventory.getCurrentItem().getDisplayName().toLowerCase()).append("§7 is already added to the list.").toString();
                }
                invCleaner.d.add(this.k.player.inventory.getCurrentItem().getDisplayName().toLowerCase());
                return new StringBuilder().insert(0, "Added ").append(this.k.player.inventory.getCurrentItem().getDisplayName().toLowerCase()).append("§7 to the list.").toString();
            }
            else {
                if (invCleaner.d.contains(s2.toLowerCase())) {
                    return new StringBuilder().insert(0, s2.toLowerCase()).append(" is already added to the list.").toString();
                }
                invCleaner.d.add(s2.toLowerCase());
                return new StringBuilder().insert(0, "Added ").append(s2.toLowerCase()).append(" to the list.").toString();
            }
        }
        else {
            if (!s.equalsIgnoreCase("del") && !s.equalsIgnoreCase("remove")) {
                return "That item doesn't exist. Proper usage: ic del <item> | ic del current (Removes the current item you are holding.)";
            }
            if (s2.equalsIgnoreCase("current")) {
                if (this.k.player.inventory.getCurrentItem().isEmpty()) {
                    return "You are not holding an item.";
                }
                if (!invCleaner.d.contains(this.k.player.inventory.getCurrentItem().getDisplayName().toLowerCase())) {
                    return new StringBuilder().insert(0, "The list does not contain ").append(this.k.player.inventory.getCurrentItem().getDisplayName()).append("§7.").toString();
                }
                invCleaner.d.remove(this.k.player.inventory.getCurrentItem().getDisplayName().toLowerCase());
                return new StringBuilder().insert(0, "Removed ").append(this.k.player.inventory.getCurrentItem().getDisplayName().toLowerCase()).append("§7 from the list.").toString();
            }
            else {
                if (!invCleaner.d.contains(s2.toLowerCase())) {
                    return new StringBuilder().insert(0, "The list does not contain ").append(s2.toLowerCase()).append(".").toString();
                }
                invCleaner.d.remove(s2.toLowerCase());
                return new StringBuilder().insert(0, "Removed ").append(s2.toLowerCase()).append(" from the list.").toString();
            }
        }
    }
    
    @Override
    public String M() {
        return "&e[add|del] [item|current]";
    }
}
