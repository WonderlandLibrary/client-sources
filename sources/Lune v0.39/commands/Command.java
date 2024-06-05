package me.superskidder.lune.commands;

/**
 * @description:
 * @author: Qian_Xia
 * @create: 2020-08-23 20:40
 **/
public class Command {
    /**
     * The standard name of a command
     */
    private String name;
    /**
     * Multiple names for a command
     */
    private String[] names;

    private boolean dev;

    public Command(String name, String... names){
        this.name = name;
        this.names = names;
    }

    public boolean isDev() {
        return dev;
    }

    public Command(String name, boolean dev, String... names){
        this.name = name;
        this.dev = dev;
        this.names = names;
    }

    /**
     * 传进来的参数不包括命令字符串 只有命令之后的字符串
     * @param args
     */
    public void run(String[] args){}

    public String getName(){
        return this.name;
    }

    public String[] getNames(){
        if (this.names.length == 0) {
            return new String[]{this.name};
        }

        String[] names = new String[this.names.length + 1];
        for (int i = 0; i < names.length; i++) {
            if(i == 0){
                names[0] = name;
                continue;
            }
            names[i] = this.names[i - 1];
        }
        return names;
    }
}
