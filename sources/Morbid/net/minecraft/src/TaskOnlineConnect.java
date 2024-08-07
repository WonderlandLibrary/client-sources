package net.minecraft.src;

import java.io.*;

public class TaskOnlineConnect extends TaskLongRunning
{
    private NetClientHandler field_96586_a;
    private final McoServer field_96585_c;
    private final GuiScreen field_96584_d;
    
    public TaskOnlineConnect(final GuiScreen par1GuiScreen, final McoServer par2McoServer) {
        this.field_96584_d = par1GuiScreen;
        this.field_96585_c = par2McoServer;
    }
    
    @Override
    public void run() {
        this.func_96576_b(StringTranslate.getInstance().translateKey("mco.connect.connecting"));
        final McoClient var1 = new McoClient(this.func_96578_b().session);
        boolean var2 = false;
        boolean var3 = false;
        McoServerAddress var4 = null;
        for (int var5 = 0; var5 < 10 && !this.func_96577_c(); ++var5) {
            try {
                var4 = var1.func_96374_a(this.field_96585_c.field_96408_a);
                var2 = true;
            }
            catch (ExceptionRetryCall exceptionRetryCall) {}
            catch (ExceptionMcoService var6) {
                var3 = true;
                this.func_96575_a(var6.getLocalizedMessage());
                break;
            }
            catch (IOException ex) {}
            catch (Exception var7) {
                var3 = true;
                this.func_96575_a(var7.getLocalizedMessage());
            }
            if (var2) {
                break;
            }
            this.func_96581_e();
        }
        if (!this.func_96577_c() && !var3) {
            if (var2) {
                final ServerAddress var8 = ServerAddress.func_78860_a(var4.field_96417_a);
                this.func_96582_a(var8.getIP(), var8.getPort());
            }
            else {
                this.func_96578_b().displayGuiScreen(this.field_96584_d);
            }
        }
    }
    
    private void func_96581_e() {
        try {
            Thread.sleep(5000L);
        }
        catch (InterruptedException var2) {
            System.err.println(var2);
        }
    }
    
    private void func_96582_a(final String par1Str, final int par2) {
        new ThreadOnlineConnect(this, par1Str, par2).start();
    }
    
    @Override
    public void func_96573_a() {
        if (this.field_96586_a != null) {
            this.field_96586_a.processReadPackets();
        }
    }
    
    static NetClientHandler func_96583_a(final TaskOnlineConnect par0TaskOnlineConnect, final NetClientHandler par1NetClientHandler) {
        return par0TaskOnlineConnect.field_96586_a = par1NetClientHandler;
    }
    
    static GuiScreen func_98172_a(final TaskOnlineConnect par0TaskOnlineConnect) {
        return par0TaskOnlineConnect.field_96584_d;
    }
    
    static NetClientHandler func_96580_a(final TaskOnlineConnect par0TaskOnlineConnect) {
        return par0TaskOnlineConnect.field_96586_a;
    }
}
