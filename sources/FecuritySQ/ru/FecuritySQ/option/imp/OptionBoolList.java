package ru.FecuritySQ.option.imp;

import ru.FecuritySQ.option.Option;

import java.util.Arrays;
import java.util.List;

public class OptionBoolList extends Option {

    public List<OptionBoolean> list;
    public OptionBoolList(String name, OptionBoolean... list) {
        super(name, Arrays.asList(list));
        this.list = Arrays.asList(list);
    }


    public List<OptionBoolean> get(){
        return list;
    }

    public boolean get(int index){
        return list.get(index).get();
    }

    public OptionBoolean setting(String settingName) {
        return get().stream().filter(booleanSetting -> booleanSetting.name.equalsIgnoreCase(settingName)).findFirst().orElse(null);
    }

}
