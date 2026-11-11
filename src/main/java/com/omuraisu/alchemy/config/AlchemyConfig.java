package com.omuraisu.alchemy.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import java.util.List;

public class AlchemyConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // バッグごとの設定用クラス
    public static class BagConfig {
        public final ModConfigSpec.IntValue amplifier;
        public final ModConfigSpec.DoubleValue velocity;
        public final ModConfigSpec.IntValue cooldown;

        // BUILDERを引数で受け取るように修正
        public BagConfig(ModConfigSpec.Builder builder, String name, int defAmp, double defVel, int defCool) {
            builder.push(name);

            amplifier = builder.comment("potion level (0=Lv1, 1=Lv2...)")
                    .translation("alchemy.config.amplifier")
                    .defineInRange("amplifier", defAmp, 0, 255);

            velocity = builder.comment("velocity")
                    .translation("alchemy.config.velocity")
                    .defineInRange("velocity", defVel, 0.1, 10.0);

            cooldown = builder.comment("cooldown (Ticks, 20=1sec)")
                    .translation("alchemy.config.cooldown")
                    .defineInRange("cooldown", defCool, 0, 72000);

            builder.pop();
        }
    }

    // 10種のバッグ設定の「宣言」のみを上で行う
    //【汎用型】
    public static final BagConfig COAL;
    public static final BagConfig COPPER;
    public static final BagConfig IRON;
    public static final BagConfig REDSTONE;

    //【投擲速度特化形】
    public static final BagConfig LAPIS;
    public static final BagConfig GOLD;
    public static final BagConfig AMETHYST;

    //【ポーションレベル特化形】
    public static final BagConfig EMERALD;
    public static final BagConfig DIAMOND;
    public static final BagConfig NETHERITE;

    // ポーションリストの設定
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ALLOWED_POTIONS;

    // Config全体の最終スペック
    public static final ModConfigSpec SPEC;

    // static初期化ブロックの中で、正しい順番でBUILDERに登録していく
    static {
        // 1. 各バッグの設定を順番に初期化（BUILDERを渡す）
        COAL = new BagConfig(BUILDER, "coal_bag", 0, 1.0, 240);
        COPPER = new BagConfig(BUILDER, "copper_bag", 0, 1.2, 180);
        IRON = new BagConfig(BUILDER, "iron_bag", 1, 1.4, 120);
        REDSTONE = new BagConfig(BUILDER, "redstone_bag", 2, 1.5, 60);

        LAPIS = new BagConfig(BUILDER, "lapis_bag", 1, 2.0, 15);
        GOLD = new BagConfig(BUILDER, "gold_bag", 2, 3.0, 5);
        AMETHYST = new BagConfig(BUILDER, "amethyst_bag", 4, 4.0, 0);

        EMERALD = new BagConfig(BUILDER, "emerald_bag", 4, 1.0, 60);
        DIAMOND = new BagConfig(BUILDER, "diamond_bag", 9, 1.0, 80);
        NETHERITE = new BagConfig(BUILDER, "netherite_bag", 19, 1.0, 300);

        // 2. 共通設定の登録
        BUILDER.push("General");
        ALLOWED_POTIONS = BUILDER.comment("バッグで使用可能なポーションIDリスト")
                .defineListAllowEmpty("allowed_potions",
                        List.of("minecraft:regeneration",
                                "minecraft:swiftness",
                                "minecraft:healing",
                                "minecraft:strength"),
                        () -> "minecraft:healing",
                        obj -> obj instanceof String);
        BUILDER.pop();

        // 3. すべての設定を登録し終えた後に、最後にbuild()を呼ぶ
        SPEC = BUILDER.build();
    }
}