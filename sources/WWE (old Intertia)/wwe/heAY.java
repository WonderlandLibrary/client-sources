package wwe;

import wwe.modules.player.*;
import net.minecraft.item.*;
import wwe.utils.*;

public class heAY extends HAxG
{
    public heAY() {
        super("InventoryCleaner");
    }
    
    @Override
    public void lpzH(final String s, final String[] array) {
        try {
            if (array.length < 2) {
                HAxG.TuzD(this.ZlRY());
                return;
            }
            if (array[0].equalsIgnoreCase("add")) {
                final String s2 = array[1];
                if (!InventoryCleaner.KtLJ.contains(Item.getByNameOrId(s2))) {
                    if (Item.getByNameOrId(s2) != null) {
                        InventoryCleaner.KtLJ.add(Item.getByNameOrId(s2));
                        HAxG.TuzD("Added " + s2 + " to inventory cleaner");
                        Wrapper.ypJK().iroC();
                        if (heAY.\u2006\u2005\u2002\u200b\u200c\u2009\u2000) {
                            throw null;
                        }
                    }
                    else {
                        HAxG.TuzD("Is that a item?");
                        if (heAY.\u2006\u2005\u2002\u200b\u200c\u2009\u2000) {
                            throw null;
                        }
                    }
                }
                else {
                    HAxG.TuzD("Thats already in the list");
                }
                if (heAY.\u2006\u2005\u2002\u200b\u200c\u2009\u2000) {
                    throw null;
                }
            }
            else if (this.wJNH(array[0])) {
                final String s3 = array[1];
                if (InventoryCleaner.KtLJ.contains(Item.getByNameOrId(s3))) {
                    InventoryCleaner.KtLJ.remove(Item.getByNameOrId(s3));
                    HAxG.TuzD("Removed " + s3 + " from inventory cleaner");
                    Wrapper.ypJK().iroC();
                    if (heAY.\u2006\u2005\u2002\u200b\u200c\u2009\u2000) {
                        throw null;
                    }
                }
                else {
                    HAxG.TuzD("That wasent add to inventory cleaner?");
                }
                if (heAY.\u2006\u2005\u2002\u200b\u200c\u2009\u2000) {
                    throw null;
                }
            }
            else {
                HAxG.TuzD(this.ZlRY());
            }
            if (heAY.\u2006\u2005\u2002\u200b\u200c\u2009\u2000) {
                throw null;
            }
        }
        catch (Exception ex) {
            this.kVjF(ex);
        }
    }
    
    @Override
    public String UiIp() {
        return "add/removes items from inventory cleaner";
    }
    
    @Override
    public String ZlRY() {
        return "inventorycleaner add/remove name of item";
    }
}
