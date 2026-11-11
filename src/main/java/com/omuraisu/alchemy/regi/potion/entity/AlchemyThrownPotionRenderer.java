package com.omuraisu.alchemy.regi.potion.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class AlchemyThrownPotionRenderer extends ThrownItemRenderer<AlchemyThrownPotion> {
    public AlchemyThrownPotionRenderer(EntityRendererProvider.Context context) {
        // 飛んでいる時の見た目の大きさを 1.0F（標準）に設定
        super(context, 1.0F, true);
    }
}