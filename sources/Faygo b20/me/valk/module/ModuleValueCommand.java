package me.valk.module;

import java.util.List;

import me.valk.command.Command;
import me.valk.utils.value.BooleanValue;
import me.valk.utils.value.RestrictedValue;
import me.valk.utils.value.Value;

/**
 * Created by Zeb on 5/1/2016.
 */
public class ModuleValueCommand extends Command{

    private Module module;

    public ModuleValueCommand(Module module){
        super(module.getName(), new String[]{}, "Set values for " + module.getName() + ".");
        this.module = module;
    }

    @Override
    public void onCommand(List<String> args){
        if(args.size() == 2){
            for(Value value : module.getData().getValues()){
                if(args.get(0).equalsIgnoreCase(value.getName())){
                    if(value instanceof BooleanValue){
                        String input = args.get(1);
                        boolean bool = false;

                        if(input.equalsIgnoreCase("true")){
                            bool = true;
                        }else if(input.equalsIgnoreCase("false")){
 
                        }else{
                            error("Value must be true or false.");
                            return;
                        }

                        value.setValue(bool);
                        addChat("Set " + value.getName() + " to " + ((BooleanValue) value).getValue());
                    }else if(value instanceof RestrictedValue){
                        RestrictedValue v = (RestrictedValue) value;
                        String input = args.get(1);
                        boolean bool = false;

                        if(RestrictedValue.fromString(input, (RestrictedValue) value) == null){
                            error("Please enter a valid number");
                            return;
                        }else{
                            Number number = RestrictedValue.fromString(input, (RestrictedValue) value);

                            if(number.doubleValue() < ((Number)v.getMin()).doubleValue() || number.doubleValue() > ((Number)v.getMax()).doubleValue()){
                                error("Range must be in the range of " + v.getMin() + " to " + v.getMax() + ".");
                                return;
                            }else{
                                v.setValue(number);
                                addChat("Set " + value.getName() + " to " + v.getValue());
                            }
                        }
                    }
                }
            }
        }
    }

}
