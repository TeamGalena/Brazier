package com.possible_triangle.brazier.world.block.tile;

import com.possible_triangle.brazier.index.BrazierBlocks;
import com.possible_triangle.brazier.index.BrazierComponents;
import com.possible_triangle.brazier.logic.CapturedSoul;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CursedSkullBlockEntity extends SkullBlockEntity {

    @Nullable
    private CapturedSoul capturedSoul;

    public CursedSkullBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return BrazierBlocks.CURSED_SKULL_ENTITY.value();
    }

    public Optional<CapturedSoul> capturedSoul() {
        return Optional.ofNullable(capturedSoul);
    }

    @Override
    protected void applyImplicitComponents(DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        capturedSoul = componentInput.get(BrazierComponents.CAPTURED_SOUL.value());
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(BrazierComponents.CAPTURED_SOUL.value(), capturedSoul);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        var ops = RegistryOps.create(NbtOps.INSTANCE, registries);
        var result = DataComponentMap.CODEC.encodeStart(ops, collectComponents());
        result.ifSuccess(it -> tag.put("components", it));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        if (tag.contains("components")) {
            var ops = RegistryOps.create(NbtOps.INSTANCE, registries);
            var result = DataComponentMap.CODEC.parse(ops, tag.get("components"));
            result.ifSuccess(components -> {
                applyComponents(components, DataComponentPatch.EMPTY);
            });
        }
    }
}
