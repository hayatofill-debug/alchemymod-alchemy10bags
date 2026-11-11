package com.omuraisu.alchemy.regi.potion.gui;

import com.omuraisu.alchemy.regi.potion.AlchemyPotion;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class AlchemyModPotionScreen extends Screen {

    private static final List<Holder<Potion>> POTIONS = List.of(
            Potions.REGENERATION, Potions.HEALING, Potions.STRENGTH,
            Potions.NIGHT_VISION, Potions.POISON, Potions.HARMING, Potions.WEAKNESS
    );

    public AlchemyModPotionScreen() {
        super(Component.translatable("gui.alchemy.potion_select"));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // 背景を暗くする
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        for (int i = 0; i < POTIONS.size(); i++) {
            double angle = Math.toRadians(i * (360.0 / POTIONS.size()) - 90);
            int x = centerX + (int) (Math.cos(angle) * 60) - 8;
            int y = centerY + (int) (Math.sin(angle) * 60) - 8;

            ItemStack potionStack = new ItemStack(Items.POTION);
            potionStack.set(net.minecraft.core.component.DataComponents.POTION_CONTENTS,
                    new PotionContents(POTIONS.get(i)));

            guiGraphics.renderItem(potionStack, x, y);

            if (mouseX >= x && mouseX <= x + 16 && mouseY >= y && mouseY <= y + 16) {
                guiGraphics.renderTooltip(this.font, potionStack, mouseX, mouseY);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        for (int i = 0; i < POTIONS.size(); i++) {
            double angle = Math.toRadians(i * (360.0 / POTIONS.size()) - 90);
            int x = centerX + (int) (Math.cos(angle) * 60) - 8;
            int y = centerY + (int) (Math.sin(angle) * 60) - 8;

            if (mouseX >= x && mouseX <= x + 16 && mouseY >= y && mouseY <= y + 16) {
                ResourceLocation potionId = BuiltInRegistries.POTION.getKey(POTIONS.get(i).value());

                // 自作の AlchemyPotion パケットを送る
                PacketDistributor.sendToServer(new AlchemyPotion(potionId.toString()));

                this.onClose();
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() { return false; }
}
