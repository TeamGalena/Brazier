package com.possible_triangle.brazier.config;

import com.possible_triangle.brazier.platform.Services;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class DistanceHandler {

    public enum Type {

        SPHERE((a, b) -> b.distToCenterSqr(a.x, a.y, a.z)),
        CYLINDER((a, b) -> b.distToCenterSqr(a.x, b.getY(), a.z));

        private final BiFunction<Vec3, BlockPos, Double> calc;

        Type(BiFunction<Vec3, BlockPos, Double> calc) {
            this.calc = calc;
        }

    }

    public static double getDistanceSqr(Vec3 from, BlockPos to) {
        return Services.CONFIGS.server().distanceCalculator().calc.apply(from, to);
    }

    public static double getDistanceSqr(BlockPos from, BlockPos to) {
        return getDistanceSqr(new Vec3(from.getX(), from.getY(), from.getZ()), to);
    }

}
