package com.possible_triangle.brazier.logic;

import com.google.common.collect.Maps;
import com.possible_triangle.brazier.config.DistanceHandler;
import com.possible_triangle.brazier.index.BrazierTags;
import com.possible_triangle.brazier.platform.Services;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

public class BrazierLogic {

    private static final HashMap<ResourceKey<Level>, HashMap<BlockPos, PreventionRules>> BRAZIERS = Maps.newHashMap();
    private static final float TOLERANCE = 0.03F;

    public static boolean isBorder(Vec3 pos, ResourceKey<Level> dimension) {
        synchronized (BRAZIERS) {
            return BRAZIERS.containsKey(dimension) && BRAZIERS.get(dimension).entrySet().stream().anyMatch(e -> {
                double distSqr = DistanceHandler.getDistanceSqr(pos, e.getKey());
                double minDist = Math.pow(e.getValue().distance() - TOLERANCE, 2);
                double maxDist = Math.pow(e.getValue().distance() + TOLERANCE, 2);
                return distSqr <= maxDist && distSqr >= minDist;
            });
        }
    }

    public static Stream<PreventionRules> inRange(BlockPos pos, ResourceKey<Level> dimension) {
        synchronized (BRAZIERS) {
            var entries = BRAZIERS.get(dimension);
            if (entries == null) return Stream.empty();
            return entries.entrySet().stream().filter(e -> {
                if (!Services.CONFIGS.server().protectAbove() && e.getKey().getY() < pos.getY()) return false;
                double dist = DistanceHandler.getDistanceSqr(pos, e.getKey());
                return dist <= Math.pow(e.getValue().distance(), 2);
            }).map(Map.Entry::getValue);
        }
    }

    public static void addBrazier(BlockPos pos, ResourceKey<Level> dimension, PreventionRules rules) {
        synchronized (BRAZIERS) {
            var map = BRAZIERS.computeIfAbsent(dimension, $ -> Maps.newHashMap());
            map.put(pos, rules);
        }
    }

    public static void removeBrazier(BlockPos pos, ResourceKey<Level> dimension) {
        synchronized (BRAZIERS) {
            if (BRAZIERS.containsKey(dimension)) BRAZIERS.get(dimension).remove(pos);
        }
    }

    private static boolean prevents(Entity entity) {
        EntityType<?> type = entity.getType();
        return BrazierTags.PREVENTABLE.matches(type::is, entity instanceof Monster);
    }

    private static boolean prevents(MobSpawnType reason) {
        return switch (reason) {
            case CHUNK_GENERATION, NATURAL, PATROL -> true;
            default -> false;
        };
    }

    public static boolean prevents(Entity entity, LevelAccessor level, MobSpawnType reason) {
        BlockPos pos = entity.blockPosition();
        return prevents(reason) && prevents(entity) && BrazierLogic.inRange(pos, entity.level().dimension()).findAny().isPresent();
    }

}
