package com.omuraisu.alchemy.main.ClientEvents;

import com.omuraisu.alchemy.regi.AlchemyTags;
import com.omuraisu.alchemy.regi.potion.gui.AlchemyModPotionScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;

public class KeyInputHandler {

    // アノテーションは残しますが、クラス側の @EventBusSubscriber は消しました
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        // AlchemyClientEvents のキーが押されたかチェック
        if (AlchemyClientEvents.ALCHEMY_GUI_KEY.consumeClick()) {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                ItemStack stack = player.getMainHandItem();

                // タグをチェックしてGUIを開く
                if (stack.is(AlchemyTags.Items.ALCHEMY_BAGS)) {
                    Minecraft.getInstance().setScreen(new AlchemyModPotionScreen());
                }
            }
        }
    }
}