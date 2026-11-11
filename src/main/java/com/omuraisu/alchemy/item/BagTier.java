package com.omuraisu.alchemy.item;

/**
 * バッグの性能数値を管理するデータ構造
 * @param amplifier     ポーションのレベル補正 (0 = Lv1, 1 = Lv2...)
 * @param velocity      投擲速度 (標準は 1.5F 前後)
 * @param cooldownTicks 右クリックの連射制限 (20 ticks = 1秒)
 */
public record BagTier(int amplifier, float velocity, int cooldownTicks) {
}