package com.possible_triangle.brazier.world.block;

import com.mojang.serialization.MapCodec;
import com.possible_triangle.brazier.BrazierConstants;
import com.possible_triangle.brazier.index.BrazierContent;
import com.possible_triangle.brazier.world.block.tile.CursedSkullBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CursedSkullBlock extends SkullBlock {

    public static final SkullBlock.Type TYPE = () -> BrazierConstants.createId("cursed_skull").toString();

    private static final MapCodec<CursedSkullBlock> CODEC = simpleCodec(CursedSkullBlock::new);

    public CursedSkullBlock(Properties properties) {
        super(TYPE, properties);
    }

    @Override
    public MapCodec<? extends SkullBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CursedSkullBlockEntity(pos, state);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (level.getBlockEntity(pos) instanceof CursedSkullBlockEntity skull) {
            if (skull.capturedSoul().isPresent()) {
                level.addParticle(
                        BrazierContent.FLAME_PARTICLE.value(),
                        pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(),
                        0.0, 0.0, 0.0);
            }
        }
    }

}
