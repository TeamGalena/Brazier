package com.possible_triangle.brazier.world.block.tile.render;

import static net.minecraft.client.renderer.blockentity.SkullBlockRenderer.renderSkull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.possible_triangle.brazier.world.block.CursedSkullBlock;
import com.possible_triangle.brazier.world.block.tile.CursedSkullBlockEntity;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;

public class CursedSkullRenderer implements BlockEntityRenderer<CursedSkullBlockEntity> {

    private final SkullModelBase model;

    public CursedSkullRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new SkullModel(context.bakeLayer(CursedSkullBlock.LAYER));
    }

    @Override
    public void render(CursedSkullBlockEntity instance, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        var blockState = instance.getBlockState();
        boolean wall = blockState.getBlock() instanceof WallSkullBlock;
        var direction = wall ? blockState.getValue(WallSkullBlock.FACING) : null;

        float rotation = 22.5F * (wall ? (2 + direction.get2DDataValue()) * 4 : blockState.getValue(SkullBlock.ROTATION));
        var renderType = RenderType.entityCutoutNoCull(CursedSkullBlock.TEXTURE);

        poseStack.pushPose();
        if (direction != null) {
            var v = direction.step();
            v.mul(0.001f);
            poseStack.translate(v.x(), v.y(), v.z());
        }

        renderSkull(direction, rotation, 0, poseStack, bufferSource, packedLight, model, renderType);

        poseStack.popPose();
    }
}
