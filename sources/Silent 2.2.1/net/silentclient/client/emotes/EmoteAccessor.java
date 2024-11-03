package net.silentclient.client.emotes;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.silentclient.client.emotes.animation.AnimationMeshConfig;
import net.silentclient.client.emotes.bobj.BOBJBone;
import net.silentclient.client.emotes.bobj.BOBJLoader;
import net.silentclient.client.emotes.emoticons.accessor.IEmoteAccessor;
import net.silentclient.client.emotes.emoticons.accessor.ParticleType;
import net.silentclient.client.emotes.particles.ParticleEndRod;
import net.silentclient.client.emotes.particles.PopcornParticle;
import net.silentclient.client.emotes.particles.SaltParticle;
import org.joml.Vector4f;

public class EmoteAccessor implements IEmoteAccessor {
    public EntityLivingBase entity;
    public AnimatorController controller;

    public EmoteAccessor(AnimatorController animatorcontroller) {
        this.controller = animatorcontroller;
    }

    @Override
    public Vector4f calcPosition(BOBJBone bobjbone, float f, float f1, float f2, float f3) {
        return this.controller.calcPosition(this.entity, bobjbone, f, f1, f2, f3);
    }

    @Override
    public AnimationMeshConfig getConfig(String s) {
        return this.controller.userConfig.meshes.get(s);
    }

    @Override
    public BOBJLoader.BOBJData getData() {
        return this.controller.animation.data;
    }

    @Override
    public void setupMatrix(BOBJBone bobjbone) {
        this.controller.setupMatrix(bobjbone);
    }

    @Override
    public void setItem(ItemStack m) {
        this.controller.itemSlot = m;
    }

    @Override
    public void setItemScale(float f) {
        this.controller.itemSlotScale = f;
    }

    @Override
    public void setHand(boolean flag) {
        this.controller.right = flag;
    }

    @Override
    public void spawnParticle(ParticleType particletype, double d0, double d1, double d2, double d3, double d4, double d5) {
        if (particletype == ParticleType.POPCORN) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new PopcornParticle(this.entity.worldObj, d0, d1, d2, d4));
        } else if (particletype == ParticleType.SALT) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new SaltParticle(this.entity.worldObj, d0, d1, d2, d3, d4, d5));
        } else if (particletype == ParticleType.END_ROD) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleEndRod(this.entity.worldObj, d0, d1, d2, d3, d4, d5));
        } else {
            EnumParticleTypes enumparticletypes = null;
            if (particletype == ParticleType.WATER_DROP) {
                enumparticletypes = EnumParticleTypes.WATER_DROP;
            } else if (particletype == ParticleType.SPELL_MOB) {
                enumparticletypes = EnumParticleTypes.SPELL_MOB;
            } else if (particletype == ParticleType.EXPLODE) {
                enumparticletypes = EnumParticleTypes.EXPLOSION_NORMAL;
            } else if (particletype == ParticleType.SMOKE) {
                enumparticletypes = EnumParticleTypes.SMOKE_NORMAL;
            } else if (particletype == ParticleType.SNOW_PUFF) {
                enumparticletypes = EnumParticleTypes.SNOWBALL;
            } else if (particletype == ParticleType.FLAME) {
                enumparticletypes = EnumParticleTypes.FLAME;
            } else if (particletype == ParticleType.CLOUD) {
                enumparticletypes = EnumParticleTypes.CLOUD;
            }

            if (enumparticletypes != null) {
                this.entity.worldObj.spawnParticle(enumparticletypes, d0, d1, d2, d3, d4, d5);
            }
        }
    }

    @Override
    public void spawnItemParticle(ItemStack m, double d0, double d1, double d2, double d3, double d4, double d5) {
        if (m != null) {
            Item item = m.getItem();
            EnumParticleTypes enumparticletypes;
            int i;
            if (item instanceof ItemBlock) {
                enumparticletypes = EnumParticleTypes.BLOCK_CRACK;
                i = Block.getIdFromBlock(((ItemBlock) item).getBlock());
            } else {
                enumparticletypes = EnumParticleTypes.ITEM_CRACK;
                i = Item.getIdFromItem(item);
            }

            this.entity.worldObj.spawnParticle(enumparticletypes, d0, d1, d2, d3, d4, d5, i, m.stackSize);
        }
    }

    @Override
    public void renderBlock(ItemStack m) {
        Item item = m.getItem();
        if (item instanceof ItemBlock) {
            BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            blockrendererdispatcher.renderBlockBrightness(((ItemBlock) item).getBlock().getDefaultState(), 1.0F);
        }
    }

    @Override
    public void throwSnowball(double d0, double d1, double d2, double d3, double d4, double d5) {
        this.controller.runnables.add(() -> {
            EntitySnowball entitysnowball = new EntitySnowball(this.entity.worldObj, d0, d1, d2);
            entitysnowball.setThrowableHeading(d3, d4, d5, 1.0F, 0.0F);
            this.entity.worldObj.spawnEntityInWorld(entitysnowball);
        });
    }

    @Override
    public void createFirework(double d0, double d1, double d2, double d3, double d4, double d5, NBTTagCompound f) {
        if (f != null) {
            this.entity.worldObj.makeFireworks(d0, d1, d2, d3, d4, d5, f);
        }
    }
}