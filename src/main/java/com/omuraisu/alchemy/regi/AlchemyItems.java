package com.omuraisu.alchemy.regi;

import com.omuraisu.alchemy.config.AlchemyConfig;
import com.omuraisu.alchemy.item.ItemAlchemy_Bug;
import com.omuraisu.alchemy.main.Alchemy;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AlchemyItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Alchemy.Mod_ID);

    // 各バッグの登録
    //【汎用型】
    public static final DeferredHolder<Item, Item> COAL_ALCHEMY_BAG = ITEMS.register("coal_alchemy_bag",
            () -> new ItemAlchemy_Bug(new Item.Properties().rarity(Rarity.COMMON).stacksTo(1), AlchemyConfig.COAL));

    public static final DeferredHolder<Item, Item> COPPER_ALCHEMY_BAG = ITEMS.register("copper_alchemy_bag",
            () -> new ItemAlchemy_Bug(new Item.Properties().rarity(Rarity.COMMON).stacksTo(1), AlchemyConfig.COPPER));

    public static final DeferredHolder<Item, Item> IRON_ALCHEMY_BAG = ITEMS.register("iron_alchemy_bag",
            () -> new ItemAlchemy_Bug(new Item.Properties().rarity(Rarity.COMMON).stacksTo(1), AlchemyConfig.IRON));

    public static final DeferredHolder<Item, Item> REDSTONE_ALCHEMY_BAG = ITEMS.register("redstone_alchemy_bag",
            () -> new ItemAlchemy_Bug(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1), AlchemyConfig.REDSTONE));

    //【投擲速度特化形】
    public static final DeferredHolder<Item, Item> LAPIS_ALCHEMY_BAG = ITEMS.register("lapis_alchemy_bag",
            () -> new ItemAlchemy_Bug(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(1), AlchemyConfig.LAPIS));


    public static final DeferredHolder<Item, Item> GOLD_ALCHEMY_BAG = ITEMS.register("gold_alchemy_bag",
            () -> new ItemAlchemy_Bug(new Item.Properties().rarity(Rarity.RARE).stacksTo(1), AlchemyConfig.GOLD));

    public static final DeferredHolder<Item, Item> NETHERITE_ALCHEMY_BAG = ITEMS.register("netherite_alchemy_bag",
            () -> new ItemAlchemy_Bug(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1), AlchemyConfig.NETHERITE));

    //【ポーションレベル特化形】
    public static final DeferredHolder<Item, Item> EMERALD_ALCHEMY_BAG = ITEMS.register("emerald_alchemy_bag",
            () -> new ItemAlchemy_Bug(new Item.Properties().rarity(Rarity.RARE).stacksTo(1), AlchemyConfig.EMERALD));

    public static final DeferredHolder<Item, Item> DIAMOND_ALCHEMY_BAG = ITEMS.register("diamond_alchemy_bag",
            () -> new ItemAlchemy_Bug(new Item.Properties().rarity(Rarity.RARE).stacksTo(1), AlchemyConfig.DIAMOND));

    public static final DeferredHolder<Item, Item> AMETHYST_ALCHEMY_BAG = ITEMS.register("amethyst_alchemy_bag",
            () -> new ItemAlchemy_Bug(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1), AlchemyConfig.AMETHYST));

    // アップグレード用アイテム
    public static final DeferredHolder<Item, Item> ALCHEMY_BAG_UPGRADE = ITEMS.register("alchemy_bag_upgrade",
            () -> new Item(new Item.Properties().rarity(Rarity.EPIC).stacksTo(64)));
}