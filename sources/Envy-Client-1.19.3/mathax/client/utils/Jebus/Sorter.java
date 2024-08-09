package mathax.client.utils.Jebus;

import java.util.ArrayList;
import java.util.Comparator;

public class Sorter {


    public static ArrayList<String> sort(ArrayList<String> list, SortMode sortMode) {
        if (sortMode.equals(SortMode.Shortest)) {
            list.sort(Comparator.comparing(String::length));
        } else {
            list.sort(Comparator.comparing(String::length).reversed());
        }
        return list;
    }


    public enum SortMode {
        Longest,
        Shortest
    }
}
