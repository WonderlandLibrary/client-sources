/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.MappingData;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

public class RecipeData {
    public static Map<String, Recipe> recipes;

    public static void init() {
        InputStream inputStream = MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/itemrecipes1_12_2to1_13.json");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        try {
            recipes = (Map)GsonUtil.getGson().fromJson((Reader)inputStreamReader, new TypeToken<Map<String, Recipe>>(){}.getType());
        } finally {
            try {
                inputStreamReader.close();
            } catch (IOException iOException) {}
        }
    }

    public static class Recipe {
        private String type;
        private String group;
        private int width;
        private int height;
        private float experience;
        private int cookingTime;
        private DataItem[] ingredient;
        private DataItem[][] ingredients;
        private DataItem result;

        public String getType() {
            return this.type;
        }

        public void setType(String string) {
            this.type = string;
        }

        public String getGroup() {
            return this.group;
        }

        public void setGroup(String string) {
            this.group = string;
        }

        public int getWidth() {
            return this.width;
        }

        public void setWidth(int n) {
            this.width = n;
        }

        public int getHeight() {
            return this.height;
        }

        public void setHeight(int n) {
            this.height = n;
        }

        public float getExperience() {
            return this.experience;
        }

        public void setExperience(float f) {
            this.experience = f;
        }

        public int getCookingTime() {
            return this.cookingTime;
        }

        public void setCookingTime(int n) {
            this.cookingTime = n;
        }

        public DataItem[] getIngredient() {
            return this.ingredient;
        }

        public void setIngredient(DataItem[] dataItemArray) {
            this.ingredient = dataItemArray;
        }

        public DataItem[][] getIngredients() {
            return this.ingredients;
        }

        public void setIngredients(DataItem[][] dataItemArray) {
            this.ingredients = dataItemArray;
        }

        public DataItem getResult() {
            return this.result;
        }

        public void setResult(DataItem dataItem) {
            this.result = dataItem;
        }
    }
}

