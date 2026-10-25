package com.possible_triangle.brazier.index.behaviour;

import com.possible_triangle.brazier.index.BrazierComponents;
import com.possible_triangle.brazier.logic.CapturedSoul;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class SoulUser {
    private final AABB range;

    // private static final String INFLUENCES_KEY = "soul_influences";
    // private static final String POS_KEY = "pos";
    // private static final String DATA_KEY = "data";

    public SoulUser(AABB range) {
        this.range = range;
    }

    private final Map<BlockPos, CapturedSoul> influences = new HashMap<>();

    public void tick(BlockPos origin, Level level) {
        var found = new HashSet<BlockPos>();

        synchronized (influences) {
            BlockPos.betweenClosedStream(range).forEach(offset -> {
                var pos = origin.offset(offset);
                var influence = level.getBlockEntity(pos);
                if (influence == null) return;

                var capturedSoul = influence.components().get(BrazierComponents.CAPTURED_SOUL.value());
                if (capturedSoul == null) return;

                influences.put(pos, capturedSoul);
            });

            influences.keySet().forEach(key -> {
                if()
            });
        }
    }

    /*
    protected void load(CompoundTag nbt, HolderLookup.Provider registries) {
        var ops = RegistryOps.create(NbtOps.INSTANCE, registries);

        if (nbt.contains(INFLUENCES_KEY)) {
            var compound = nbt.getCompound(INFLUENCES_KEY);
        }
    }

    protected void save(CompoundTag nbt, HolderLookup.Provider registries) {
        var ops = RegistryOps.create(NbtOps.INSTANCE, registries);

        var list = new ListTag();
        influences.forEach((key, value) -> {
            var encoded = CapturedSoul.CODEC.encodeStart(ops, value);
            encoded.ifSuccess(it -> {
                var compound = new CompoundTag();
                compound.put(POS_KEY, NbtUtils.writeBlockPos(key));
                compound.put(DATA_KEY, it);
            });
        });

        nbt.put(INFLUENCES_KEY, list);
    }
     */

}
