package hn.blacknight0981.hNEnchant.utils;

import org.bukkit.block.BlockFace;

public class AreaCalculator {
    private final int range;

    public AreaCalculator(int range) {
        this.range = range;
    }

    // 根據玩家的面向獲取指定長、寬、高的區域偏移量
    public int[][] getOffsets(BlockFace face) {
        return switch (face) {
            case UP, DOWN -> calculateXZOffsets(); // XZ 平面擴展
            case NORTH, SOUTH -> calculateXYOffsets(); // XY 平面擴展
            case EAST, WEST -> calculateYZOffsets(); // YZ 平面擴展
            default -> new int[][]{{0, 0, 0}};
        };
    }

    // 計算 XZ 平面的偏移量（對應 UP、DOWN）
    private int[][] calculateXZOffsets() {
        int size = (2 * range + 1);
        int[][] offsets = new int[size * size][3];
        int index = 0;

        // 在 XZ 平面內擴展，Y 軸固定為 0
        for (int x = -range; x <= range; x++) {
            for (int z = -range; z <= range; z++) {
                offsets[index++] = new int[]{x, 0, z}; // 固定 Y 軸，XZ 平面
            }
        }
        return offsets;
    }

    // 計算 YZ 平面的偏移量（對應 NORTH、SOUTH）
    private int[][] calculateYZOffsets() {
        int size = (2 * range + 1);
        int[][] offsets = new int[size * size][3];
        int index = 0;

        // 在 YZ 平面內擴展，X 軸固定為 0
        for (int y = -range; y <= range; y++) {
            for (int z = -range; z <= range; z++) {
                offsets[index++] = new int[]{0, y, z}; // 固定 X 軸，YZ 平面
            }
        }
        return offsets;
    }

    // 計算 XY 平面的偏移量（對應 EAST、WEST）
    private int[][] calculateXYOffsets() {
        int size = (2 * range + 1);
        int[][] offsets = new int[size * size][3];
        int index = 0;

        // 在 XY 平面內擴展，Z 軸固定為 0
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                offsets[index++] = new int[]{x, y, 0}; // 固定 Z 軸，XY 平面
            }
        }
        return offsets;
    }
}
