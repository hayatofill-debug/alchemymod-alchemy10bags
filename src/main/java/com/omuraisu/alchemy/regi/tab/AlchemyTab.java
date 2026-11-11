package com.omuraisu.alchemy.regi.tab;

import com.omuraisu.alchemy.main.Alchemy;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AlchemyTab {

    public static final DeferredRegister<CreativeModeTab> Mod_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Alchemy.Mod_ID);

    public static final DeferredHolder<CreativeModeTab,CreativeModeTab> ALCHEMY_MAIN = Mod_TABS.register("alchemy_main",
            ()->{return CreativeModeTab.builder()
                    .icon(()->new ItemStack(Items.BUNDLE))
                    .title(Component.translatable("itemGroup.alchemy_main"))
                    .displayItems((param,output)->{
                        for(Item item: com.omuraisu.alchemy.regi.tab.AlchemyMain.items){
                            output.accept(item);
                        }
                    })
                    .build();
    });
}
