package de.resourcepacks24.utils;

import de.resourcepacks24.main.Pack;
import java.util.ArrayList;

public interface PackInfoCallback
{
    void result(ArrayList<Pack> var1);

    void progress(int var1);
}
