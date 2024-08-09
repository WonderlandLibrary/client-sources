/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.io.IOException;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHeadToggle;
import net.minecraft.client.resources.data.VillagerMetadataSection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.villager.IVillagerDataHolder;
import net.minecraft.entity.villager.VillagerType;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;

public class VillagerLevelPendantLayer<T extends LivingEntity, M extends EntityModel<T>>
extends LayerRenderer<T, M>
implements IResourceManagerReloadListener {
    private static final Int2ObjectMap<ResourceLocation> field_215352_a = Util.make(new Int2ObjectOpenHashMap(), VillagerLevelPendantLayer::lambda$static$0);
    private final Object2ObjectMap<VillagerType, VillagerMetadataSection.HatType> field_215353_b = new Object2ObjectOpenHashMap<VillagerType, VillagerMetadataSection.HatType>();
    private final Object2ObjectMap<VillagerProfession, VillagerMetadataSection.HatType> field_215354_c = new Object2ObjectOpenHashMap<VillagerProfession, VillagerMetadataSection.HatType>();
    private final IReloadableResourceManager field_215355_d;
    private final String field_215356_e;

    public VillagerLevelPendantLayer(IEntityRenderer<T, M> iEntityRenderer, IReloadableResourceManager iReloadableResourceManager, String string) {
        super(iEntityRenderer);
        this.field_215355_d = iReloadableResourceManager;
        this.field_215356_e = string;
        iReloadableResourceManager.addReloadListener(this);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (!((Entity)t).isInvisible()) {
            VillagerData villagerData = ((IVillagerDataHolder)t).getVillagerData();
            VillagerType villagerType = villagerData.getType();
            VillagerProfession villagerProfession = villagerData.getProfession();
            VillagerMetadataSection.HatType hatType = this.func_215350_a(this.field_215353_b, "type", Registry.VILLAGER_TYPE, villagerType);
            VillagerMetadataSection.HatType hatType2 = this.func_215350_a(this.field_215354_c, "profession", Registry.VILLAGER_PROFESSION, villagerProfession);
            Object m = this.getEntityModel();
            ((IHeadToggle)m).func_217146_a(hatType2 == VillagerMetadataSection.HatType.NONE || hatType2 == VillagerMetadataSection.HatType.PARTIAL && hatType != VillagerMetadataSection.HatType.FULL);
            ResourceLocation resourceLocation = this.func_215351_a("type", Registry.VILLAGER_TYPE.getKey(villagerType));
            VillagerLevelPendantLayer.renderCutoutModel(m, resourceLocation, matrixStack, iRenderTypeBuffer, n, t, 1.0f, 1.0f, 1.0f);
            ((IHeadToggle)m).func_217146_a(true);
            if (villagerProfession != VillagerProfession.NONE && !((LivingEntity)t).isChild()) {
                ResourceLocation resourceLocation2 = this.func_215351_a("profession", Registry.VILLAGER_PROFESSION.getKey(villagerProfession));
                VillagerLevelPendantLayer.renderCutoutModel(m, resourceLocation2, matrixStack, iRenderTypeBuffer, n, t, 1.0f, 1.0f, 1.0f);
                if (villagerProfession != VillagerProfession.NITWIT) {
                    ResourceLocation resourceLocation3 = this.func_215351_a("profession_level", (ResourceLocation)field_215352_a.get(MathHelper.clamp(villagerData.getLevel(), 1, field_215352_a.size())));
                    VillagerLevelPendantLayer.renderCutoutModel(m, resourceLocation3, matrixStack, iRenderTypeBuffer, n, t, 1.0f, 1.0f, 1.0f);
                }
            }
        }
    }

    private ResourceLocation func_215351_a(String string, ResourceLocation resourceLocation) {
        return new ResourceLocation(resourceLocation.getNamespace(), "textures/entity/" + this.field_215356_e + "/" + string + "/" + resourceLocation.getPath() + ".png");
    }

    public <K> VillagerMetadataSection.HatType func_215350_a(Object2ObjectMap<K, VillagerMetadataSection.HatType> object2ObjectMap, String string, DefaultedRegistry<K> defaultedRegistry, K k) {
        return object2ObjectMap.computeIfAbsent(k, arg_0 -> this.lambda$func_215350_a$1(string, defaultedRegistry, k, arg_0));
    }

    @Override
    public void onResourceManagerReload(IResourceManager iResourceManager) {
        this.field_215354_c.clear();
        this.field_215353_b.clear();
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (T)((LivingEntity)entity2), f, f2, f3, f4, f5, f6);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private VillagerMetadataSection.HatType lambda$func_215350_a$1(String string, DefaultedRegistry defaultedRegistry, Object object, Object object2) {
        try (IResource iResource = this.field_215355_d.getResource(this.func_215351_a(string, defaultedRegistry.getKey(object)));){
            VillagerMetadataSection villagerMetadataSection = iResource.getMetadata(VillagerMetadataSection.field_217827_a);
            if (villagerMetadataSection == null) return VillagerMetadataSection.HatType.NONE;
            VillagerMetadataSection.HatType hatType = villagerMetadataSection.func_217826_a();
            return hatType;
        } catch (IOException iOException) {
            // empty catch block
        }
        return VillagerMetadataSection.HatType.NONE;
    }

    private static void lambda$static$0(Int2ObjectOpenHashMap int2ObjectOpenHashMap) {
        int2ObjectOpenHashMap.put(1, new ResourceLocation("stone"));
        int2ObjectOpenHashMap.put(2, new ResourceLocation("iron"));
        int2ObjectOpenHashMap.put(3, new ResourceLocation("gold"));
        int2ObjectOpenHashMap.put(4, new ResourceLocation("emerald"));
        int2ObjectOpenHashMap.put(5, new ResourceLocation("diamond"));
    }
}

