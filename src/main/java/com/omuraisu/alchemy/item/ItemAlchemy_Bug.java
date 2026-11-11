package com.omuraisu.alchemy.item;

import com.omuraisu.alchemy.config.AlchemyConfig;
import com.omuraisu.alchemy.regi.AlchemyTags;
import com.omuraisu.alchemy.regi.potion.entity.AlchemyThrownPotion;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemAlchemy_Bug extends Item {
    private final AlchemyConfig.BagConfig config;

    public ItemAlchemy_Bug(Properties properties, AlchemyConfig.BagConfig config) {
        super(properties);
        this.config = config;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        if (!level.isClientSide) {
            CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            String potionName = customData.copyTag().getString("SelectedPotion");

            if (!potionName.isEmpty()) {
                var potionHolder = BuiltInRegistries.POTION.getHolder(ResourceLocation.parse(potionName));

                if (potionHolder.isPresent()) {
                    // 1. エンティティ生成
                    AlchemyThrownPotion thrownPotion = new AlchemyThrownPotion(level, player);

                    // 2. ポーション内容の構築
                    PotionContents baseContents = new PotionContents(potionHolder.get());
                    List<MobEffectInstance> upgradedEffects = new ArrayList<>();
                    int currentAmp = this.config.amplifier.get();

                    for (MobEffectInstance effect : baseContents.getAllEffects()) {
                        upgradedEffects.add(new MobEffectInstance(
                                effect.getEffect(),
                                effect.getDuration(),
                                currentAmp, // コンフィグの威力を適用
                                effect.isAmbient(),
                                effect.isVisible()
                        ));
                    }

                    // 3. アイテムスタックの作成とコンポーネント設定
                    ItemStack visualStack = new ItemStack(Items.SPLASH_POTION);
                    // 1.21.1ではPotionContentsを正しくセットしないと「ただの瓶」になります
                    visualStack.set(DataComponents.POTION_CONTENTS, new PotionContents(Optional.empty(), Optional.empty(), upgradedEffects));

                    // エンティティにアイテム情報をセット（これで見た目と中身がポーションになる）
                    thrownPotion.setItem(visualStack);

                    // 4. 生成位置の微調整（自分に当たって消滅するのを防ぐ）
                    // プレイヤーの目の少し前から発射するようにします
                    thrownPotion.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());

                    // 5. 射出処理
                    float currentVel = this.config.velocity.get().floatValue();
                    // inaccuracyを0.5Fに下げて直進性を上げ、5ブロック先でも当たりやすくします
                    thrownPotion.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, currentVel, 0.5F);

                    level.addFreshEntity(thrownPotion);

                    // 6. クールタイム
                    int cooldownTicks = Math.max(5, this.config.cooldown.get());
                    player.getCooldowns().addCooldown(this, cooldownTicks);

                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
                }
            }
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("alchemy.item.amplifier").append(Component.literal("Lv" + (this.config.amplifier.get() + 1)).withStyle(ChatFormatting.GOLD)));
        tooltip.add(Component.translatable("alchemy.item.velocity").append(Component.literal(String.format("%.1f", this.config.velocity.get())).withStyle(ChatFormatting.GREEN)));
        tooltip.add(Component.translatable("alchemy.item.cooldown").append(Component.literal(String.format("%.1fs", (float)this.config.cooldown.get() / 20F)).withStyle(ChatFormatting.BLUE)));

        if (stack.is(AlchemyTags.Items.ALCHEMY_BAGS)) {
            tooltip.add(Component.translatable("tooltip.alchemy.alchemy_bags").withStyle(ChatFormatting.WHITE));
        }
        if (stack.is(AlchemyTags.Items.SPEED)) {
            tooltip.add(Component.translatable("tooltip.alchemy.speed").withStyle(ChatFormatting.WHITE));
        }
        if (stack.is(AlchemyTags.Items.POWER)) {
            tooltip.add(Component.translatable("tooltip.alchemy.power").withStyle(ChatFormatting.WHITE));
        }
        if (stack.is(AlchemyTags.Items.COMMON)) {
            tooltip.add(Component.translatable("tooltip.alchemy.common").withStyle(ChatFormatting.WHITE));
        }

        super.appendHoverText(stack, context, tooltip, flag);
    }
}