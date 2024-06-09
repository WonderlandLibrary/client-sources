// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.command.impl.commands;

import intent.AquaDev.aqua.modules.Module;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.command.Command;

public class toggle extends Command
{
    public toggle() {
        super("t");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length == 1) {
            final Module mod = Aqua.moduleManager.getModuleByName(args[0]);
            Aqua.INSTANCE.fileUtil.saveKeys();
            Aqua.INSTANCE.fileUtil.saveModules();
            if (mod != null) {
                mod.toggle();
            }
        }
    }
}
