package us.dev.direkt.module.internal.misc.decoder.handler;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

class Alphabits {
    static int scrambleselection = 0;

    public Set<String> makeKeysR(String key) {
        Set<String> set = new HashSet<>();

        return set;
    }

    public Set<String> makeKeys(String key) {
        Set<String> set = new HashSet<>();


        for (int z = 0; z < key.length(); z++) {

            if (scrambleselection == 0) {
                Set<String> temp = new HashSet<>();
                char c = key.charAt(z);

                //s1 = t*s0 ∪ {t} ∪ s0 = {t}
                temp.addAll(set.stream()
                        .map(str -> str + c)
                        .collect(Collectors.toList()));
                set.add(String.valueOf(c));
                set.addAll(temp);
            } else {
                set.add(key);
            }
        }
        return set;
    }

    public void jumble() {
        scrambleselection = 1;
    }

}
