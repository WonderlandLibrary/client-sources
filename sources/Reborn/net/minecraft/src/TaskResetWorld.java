package net.minecraft.src;

import java.io.*;

class TaskResetWorld extends TaskLongRunning
{
    private final long field_96591_c;
    private final String field_104066_d;
    final GuiScreenResetWorld field_96592_a;
    
    public TaskResetWorld(final GuiScreenResetWorld par1GuiScreenResetWorld, final long par2, final String par4Str) {
        this.field_96592_a = par1GuiScreenResetWorld;
        this.field_96591_c = par2;
        this.field_104066_d = par4Str;
    }
    
    @Override
    public void run() {
        final McoClient var1 = new McoClient(this.func_96578_b().session);
        final String var2 = StringTranslate.getInstance().translateKey("mco.reset.world.resetting.screen.title");
        this.func_96576_b(var2);
        try {
            var1.func_96376_d(this.field_96591_c, this.field_104066_d);
            GuiScreenResetWorld.func_96147_b(this.field_96592_a).displayGuiScreen(GuiScreenResetWorld.func_96148_a(this.field_96592_a));
        }
        catch (ExceptionMcoService var3) {
            this.func_96575_a(var3.field_96391_b);
        }
        catch (IOException ex) {}
    }
}
