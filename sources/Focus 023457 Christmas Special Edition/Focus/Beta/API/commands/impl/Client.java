package Focus.Beta.API.commands.impl;

import Focus.Beta.API.commands.Command;
import Focus.Beta.UTILS.helper.Helper;

public class Client extends Command {
    public Client() {
        super("Client", new String[] { "cl" }, "", "");
    }

    @Override
    public String execute(String[] var1) {
        if(var1.length > 0){
            if(var1[0].equalsIgnoreCase("desc")){
                if(!var1[1].equalsIgnoreCase("clear")) {
                    Focus.Beta.Client.ClientDesc = var1[1];
                    Helper.sendMessage("Setted Client Description to " + var1[1] + ", .cl des clear to delete description");
                }else{
                    Focus.Beta.Client.ClientDesc = "";
                    Helper.sendMessage("Deleted Desc");
                }
            }else{
                Helper.sendMessage("Correct Usage .cl desc <Name>");
            }
        }else{
            Helper.sendMessage("Correct Usage .cl desc <Name>");
        }
        return null;
    }
}
