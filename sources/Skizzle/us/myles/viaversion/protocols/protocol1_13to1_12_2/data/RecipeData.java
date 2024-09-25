/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.MappingData;
import us.myles.ViaVersion.util.GsonUtil;
import us.myles.viaversion.libs.gson.reflect.TypeToken;

public class RecipeData {
    public static Map<String, Recipe> recipes;

    public static void init() {
        InputStream stream = MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/itemrecipes1_12_2to1_13.json");
        InputStreamReader reader = new InputStreamReader(stream);
        try {
            recipes = (Map)GsonUtil.getGson().fromJson((Reader)reader, new TypeToken<Map<String, Recipe>>(){}.getType());
        }
        finally {
            try {
                reader.close();
            }
            catch (IOException iOException) {}
        }
    }

    public static class Recipe {
        private String type;
        private String group;
        private int width;
        private int height;
        private float experience;
        private int cookingTime;
        private Item[] ingredient;
        private Item[][] ingredients;
        private Item result;

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getGroup() {
            return this.group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public int getWidth() {
            return this.width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return this.height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public float getExperience() {
            return this.experience;
        }

        public void setExperience(float experience) {
            this.experience = experience;
        }

        public int getCookingTime() {
            return this.cookingTime;
        }

        public void setCookingTime(int cookingTime) {
            this.cookingTime = cookingTime;
        }

        public Item[] getIngredient() {
            return this.ingredient;
        }

        public void setIngredient(Item[] ingredient) {
            this.ingredient = ingredient;
        }

        public Item[][] getIngredients() {
            return this.ingredients;
        }

        public void setIngredients(Item[][] ingredients) {
            this.ingredients = ingredients;
        }

        public Item getResult() {
            return this.result;
        }

        public void setResult(Item result) {
            this.result = result;
        }
    }
}

