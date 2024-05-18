package mods.accountmanager.gui;

import com.google.common.collect.Lists;
import java.util.List;
import mods.accountmanager.utils.FancyGuiListExtended;
import net.minecraft.client.Minecraft;

public class AccountList extends FancyGuiListExtended
{
    private List<AccountEntry> field_148198_l = Lists.<AccountEntry>newArrayList();
    private int selectedSlotIndex = -1;
    private static final String __OBFID = "CL_00000819";

    public AccountList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn, int startBox)
    {
        super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn, startBox);
    }

    public FancyGuiListExtended.IGuiListEntry getListEntry(int index)
    {
        return (FancyGuiListExtended.IGuiListEntry)this.field_148198_l.get(index);
    }

    protected int getSize()
    {
        return this.field_148198_l.size();
    }

    public AccountEntry getAccountEntry()
    {
        return (AccountEntry)this.field_148198_l.get(this.selectedSlotIndex);
    }

    public void setSelected(int selectedSlotIndexIn)
    {
        this.selectedSlotIndex = selectedSlotIndexIn;
    }

    protected boolean isSelected(int slotIndex)
    {
        return slotIndex == this.selectedSlotIndex;
    }

    public void setAccounts(AccountManagerGUI gui, List accounts)
    {
        this.field_148198_l.clear();

        for (Object object : accounts)
        {
            this.field_148198_l.add(new AccountEntry(gui, (String)object, this));
        }
    }

    public List<AccountEntry> getAccountEntrys()
    {
        return this.field_148198_l;
    }

    protected int getScrollBarX()
    {
        return super.getScrollBarX() + 30;
    }

    public int getListWidth()
    {
        return super.getListWidth() + 85;
    }
}
