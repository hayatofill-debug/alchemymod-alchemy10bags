package com.omuraisu.alchemy.regi.potion;

import com.omuraisu.alchemy.main.Alchemy;
import com.omuraisu.alchemy.regi.AlchemyTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public record AlchemyPotion(String potionName) implements CustomPacketPayload {

    // パケットの識別子
    public static final Type<AlchemyPotion> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Alchemy.Mod_ID, "select_potion"));

    // データの読み書き定義
    public static final StreamCodec<FriendlyByteBuf, AlchemyPotion> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            AlchemyPotion::potionName,
            AlchemyPotion::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    // パケットを受け取った時のサーバー側の処理
    public static void handle(final AlchemyPotion payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            ItemStack stack = player.getMainHandItem();

            // ここで自分のアイテムクラス（ItemAlchemy_Bugなど）かチェック
            if (stack.is(AlchemyTags.Items.ALCHEMY_BAGS)) {
                CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
                CompoundTag tag = customData.copyTag();
                tag.putString("SelectedPotion", payload.potionName());
                stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

                // 確認用ログ（後で消してOK）
                System.out.println("ポーション選択を保存しました: " + payload.potionName());
            }
        });
    }

    // メインクラスの ModEventBus から呼ばれる登録メソッド
    public static void register(PayloadRegistrar registrar) {
        registrar.playToServer(TYPE, STREAM_CODEC, AlchemyPotion::handle);
    }
}