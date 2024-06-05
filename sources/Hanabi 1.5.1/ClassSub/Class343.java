package ClassSub;

import java.util.*;

class Class343
{
    String name;
    String telephone;
    String motherName;
    String address;
    
    
    public Class343(final String name, final String telephone, final String motherName, final String address) {
        this.name = name;
        this.telephone = telephone;
        this.motherName = motherName;
        this.address = address;
    }
    
    public String getSB() {
        final String[] array = { "�?" + this.motherName + "孝子" + this.name + "勇敢�?" + this.address + "�?你妈", "我是" + this.name + "我妈�?" + this.motherName + "�?" + this.address + "卖yin约pao热线" + this.telephone, "�?" + this.name + "�?" + this.address + "勇敢屠杀绿色玩家婊子�?", "购买�?" + this.name + "编写的我的世界Power送妈妈客户端请拨打热�?" + this.telephone, "对不起我叫白治军来自四川广安我今年当兵兵�?没过我只能上完初中回家辍学写端圈钱求求你们可怜可怜我�?", "我叫" + this.name + "我在" + this.address + "用邻水脐橙给你妈妈洗B呢购买Power拨打热线" + this.telephone };
        return array[new Random().nextInt(array.length)];
    }
}
