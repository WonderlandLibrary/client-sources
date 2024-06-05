package net.minecraft.src;

public class DemoWorldManager extends ItemInWorldManager
{
    private boolean field_73105_c;
    private boolean demoTimeExpired;
    private int field_73104_e;
    private int field_73102_f;
    
    public DemoWorldManager(final World par1World) {
        super(par1World);
        this.field_73105_c = false;
        this.demoTimeExpired = false;
        this.field_73104_e = 0;
        this.field_73102_f = 0;
    }
    
    @Override
    public void updateBlockRemoving() {
        super.updateBlockRemoving();
        ++this.field_73102_f;
        final long var1 = this.theWorld.getTotalWorldTime();
        final long var2 = var1 / 24000L + 1L;
        if (!this.field_73105_c && this.field_73102_f > 20) {
            this.field_73105_c = true;
            this.thisPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(5, 0));
        }
        this.demoTimeExpired = (var1 > 120500L);
        if (this.demoTimeExpired) {
            ++this.field_73104_e;
        }
        if (var1 % 24000L == 500L) {
            if (var2 <= 6L) {
                this.thisPlayerMP.sendChatToPlayer(this.thisPlayerMP.translateString("demo.day." + var2, new Object[0]));
            }
        }
        else if (var2 == 1L) {
            if (var1 == 100L) {
                this.thisPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(5, 101));
            }
            else if (var1 == 175L) {
                this.thisPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(5, 102));
            }
            else if (var1 == 250L) {
                this.thisPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(5, 103));
            }
        }
        else if (var2 == 5L && var1 % 24000L == 22000L) {
            this.thisPlayerMP.sendChatToPlayer(this.thisPlayerMP.translateString("demo.day.warning", new Object[0]));
        }
    }
    
    private void sendDemoReminder() {
        if (this.field_73104_e > 100) {
            this.thisPlayerMP.sendChatToPlayer(this.thisPlayerMP.translateString("demo.reminder", new Object[0]));
            this.field_73104_e = 0;
        }
    }
    
    @Override
    public void onBlockClicked(final int par1, final int par2, final int par3, final int par4) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
        }
        else {
            super.onBlockClicked(par1, par2, par3, par4);
        }
    }
    
    @Override
    public void uncheckedTryHarvestBlock(final int par1, final int par2, final int par3) {
        if (!this.demoTimeExpired) {
            super.uncheckedTryHarvestBlock(par1, par2, par3);
        }
    }
    
    @Override
    public boolean tryHarvestBlock(final int par1, final int par2, final int par3) {
        return !this.demoTimeExpired && super.tryHarvestBlock(par1, par2, par3);
    }
    
    @Override
    public boolean tryUseItem(final EntityPlayer par1EntityPlayer, final World par2World, final ItemStack par3ItemStack) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
            return false;
        }
        return super.tryUseItem(par1EntityPlayer, par2World, par3ItemStack);
    }
    
    @Override
    public boolean activateBlockOrUseItem(final EntityPlayer par1EntityPlayer, final World par2World, final ItemStack par3ItemStack, final int par4, final int par5, final int par6, final int par7, final float par8, final float par9, final float par10) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
            return false;
        }
        return super.activateBlockOrUseItem(par1EntityPlayer, par2World, par3ItemStack, par4, par5, par6, par7, par8, par9, par10);
    }
}
