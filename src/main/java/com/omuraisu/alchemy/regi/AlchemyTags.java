package com.omuraisu.alchemy.regi;

import com.omuraisu.alchemy.main.Alchemy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class AlchemyTags {

    public static class Blocks {
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Alchemy.Mod_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> ALCHEMY_BAGS = tag("alchemy_bags");
        public static final TagKey<Item> SPEED = tag("speed");
        public static final TagKey<Item> POWER = tag("power");
        public static final TagKey<Item> COMMON = tag("common");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Alchemy.Mod_ID, name));
        }
    }
}