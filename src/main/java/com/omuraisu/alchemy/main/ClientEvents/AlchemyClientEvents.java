package com.omuraisu.alchemy.main.ClientEvents;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class AlchemyClientEvents {
    // キーバインドの定義
    public static final KeyMapping ALCHEMY_GUI_KEY = new KeyMapping(
            "key.alchemy.open_gui",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "key.categories.alchemy"
    );

    // 手動で呼び出すための登録メソッド
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(ALCHEMY_GUI_KEY);
    }
}