package com.omuraisu.alchemy.regi.potion.entity;

import com.omuraisu.alchemy.main.Alchemy;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class AlchemyEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(Registries.ENTITY_TYPE, Alchemy.Mod_ID);

    // エンティティの定義。サイズや追跡範囲を設定します
    public static final DeferredHolder<EntityType<?>, EntityType<AlchemyThrownPotion>> ALCHEMY_THROWN_POTION =
            ENTITIES.register("alchemy_thrown_potion",
                    () -> EntityType.Builder.<AlchemyThrownPotion>of(AlchemyThrownPotion::new, MobCategory.MISC)
                            .sized(0.25F, 0.25F) // 当たり判定の大きさ
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("alchemy_thrown_potion"));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}