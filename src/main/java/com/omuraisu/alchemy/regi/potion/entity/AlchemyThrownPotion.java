package com.omuraisu.alchemy.regi.potion.entity;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import java.util.List;

public class AlchemyThrownPotion extends ThrowableItemProjectile {

    public AlchemyThrownPotion(EntityType<? extends AlchemyThrownPotion> type, Level level) {
        super(type, level);
    }

    public AlchemyThrownPotion(Level level, LivingEntity shooter) {
        super(AlchemyEntities.ALCHEMY_THROWN_POTION.get(), shooter, level);
    }

    @Override
    protected Item getDefaultItem() {
        // エンティティの内部データが空だった場合のデフォルト表示
        return Items.SPLASH_POTION;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            ItemStack stack = this.getItem();
            PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);

            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.SPLASH_POTION_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F);

            if (contents != null) {
                Entity hitEntity = result instanceof EntityHitResult entityHit ? entityHit.getEntity() : null;
                // ここで下のメソッドを呼び出す
                this.applySplash(contents, hitEntity);

                this.level().levelEvent(2002, this.blockPosition(), contents.getColor());
            }

            this.discard();
        }
    } // ← onHit の閉じカッコを確認！

    // ここは onHit の「外」に書く必要があります
    private void applySplash(PotionContents contents, Entity hitEntity) {
        AABB aabb = this.getBoundingBox().inflate(4.0D, 2.0D, 4.0D);
        List<LivingEntity> targets = this.level().getEntitiesOfClass(LivingEntity.class, aabb);

        if (!targets.isEmpty()) {
            for (LivingEntity target : targets) {
                if (target.isAffectedByPotions()) {
                    double distanceSq = this.distanceToSqr(target);

                    if (distanceSq < 16.0D) {
                        double multiplier = 1.0D - Math.sqrt(distanceSq) / 4.0D;
                        if (target == hitEntity) {
                            multiplier = 1.0D;
                        }

                        // getAllEffects() で全ての効果をループ
                        for (MobEffectInstance effect : contents.getAllEffects()) {
                            if (effect.getEffect().value().isInstantenous()) {
                                effect.getEffect().value().applyInstantenousEffect(this, this.getOwner(), target, effect.getAmplifier(), multiplier);
                            } else {
                                int duration = (int) (multiplier * (double) effect.getDuration() + 0.5D);
                                if (duration > 20) {
                                    target.addEffect(new MobEffectInstance(effect.getEffect(), duration, effect.getAmplifier(), effect.isAmbient(), effect.isVisible()));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}