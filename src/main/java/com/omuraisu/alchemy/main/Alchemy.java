package com.omuraisu.alchemy.main;

import com.omuraisu.alchemy.config.AlchemyConfig; // 追加
import com.omuraisu.alchemy.main.ClientEvents.AlchemyClientEvents;
import com.omuraisu.alchemy.main.ClientEvents.KeyInputHandler;
import com.omuraisu.alchemy.regi.AlchemyItems;
import com.omuraisu.alchemy.regi.potion.AlchemyPotion;
import com.omuraisu.alchemy.regi.potion.entity.AlchemyEntities;
import com.omuraisu.alchemy.regi.potion.entity.AlchemyThrownPotionRenderer;
import com.omuraisu.alchemy.regi.tab.AlchemyTab;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer; // 追加
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(Alchemy.Mod_ID)
public class Alchemy {

    public static final String Mod_ID = "alchemy";

    // コンストラクタに ModContainer を追加
    public Alchemy(IEventBus modEventBus, ModContainer container) {
        // --- Configの登録 ---
        container.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, AlchemyConfig.SPEC);

        // アイテムとタブの登録
        AlchemyItems.ITEMS.register(modEventBus);
        AlchemyTab.Mod_TABS.register(modEventBus);
        // ポーションの登録
        AlchemyEntities.register(modEventBus);

        // パケット（通信）の登録
        modEventBus.addListener(this::registerPayloads);

        // クライアント側限定の登録処理
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(AlchemyClientEvents::registerKeys);
            NeoForge.EVENT_BUS.register(KeyInputHandler.class);
            modEventBus.addListener((net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers event) -> {
                event.registerEntityRenderer(AlchemyEntities.ALCHEMY_THROWN_POTION.get(), AlchemyThrownPotionRenderer::new);
            });
        }
    }

    private void registerPayloads(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(Mod_ID);
        AlchemyPotion.register(registrar);
    }
}