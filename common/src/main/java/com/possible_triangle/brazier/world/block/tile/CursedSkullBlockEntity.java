package com.possible_triangle.brazier.world.block.tile;

import com.possible_triangle.brazier.index.BrazierBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CursedSkullBlockEntity extends SkullBlockEntity {

    public CursedSkullBlockEntity(BlockPos pos, BlockState blockState) {
        super(pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return BrazierBlocks.CURSED_SKULL_ENTITY.value();
    }
}
