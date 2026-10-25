package com.possible_triangle.brazier.world.block.tile.render;

import static net.minecraft.client.renderer.blockentity.SkullBlockRenderer.renderSkull;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.possible_triangle.brazier.BrazierConstants;
import com.possible_triangle.brazier.world.block.tile.CursedSkullBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.WallSkullBlock;

public class CursedSkullRenderer implements BlockEntityRenderer<CursedSkullBlockEntity> {

    public static final ModelLayerLocation LAYER = new ModelLayerLocation(BrazierConstants.createId("cursed_skull"), "main");
    public static final ResourceLocation TEXTURE = BrazierConstants.createId("textures/entity/cursed_skull.png");

    private final SkullModelBase model;

    public CursedSkullRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new SkullModel(context.bakeLayer(LAYER));
    }

    @Override
    public void render(CursedSkullBlockEntity instance, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        var blockState = instance.getBlockState();
        boolean wall = blockState.getBlock() instanceof WallSkullBlock;
        var direction = wall ? blockState.getValue(WallSkullBlock.FACING) : null;

        float rotation = 0;
        var renderType = RenderType.entityCutoutNoCull(TEXTURE);

        poseStack.pushPose();

        if (instance.capturedSoul().isPresent()) {
            var camera = Minecraft.getInstance().gameRenderer.getMainCamera();
            var offset = camera.getPosition().subtract(instance.getBlockPos().getCenter());
            var ticks = instance.getLevel().getGameTime() + partialTicks;

            var horizontalDistance = Math.sqrt(offset.x() * offset.x() + offset.z() * offset.z());
            var xRot = (float) Mth.atan2(offset.y(), horizontalDistance);
            var yRot = (float) (Mth.atan2(offset.z, offset.x()) + Math.PI / 2);
            var yOffset = (float) Math.sin(ticks / 10) * 0.1F + 0.2F;

            poseStack.translate(0, yOffset, 0.0);

            poseStack.rotateAround(Axis.YN.rotation(yRot), 0.5F, 0.5F, 0.5F);
            poseStack.rotateAround(Axis.XP.rotation(xRot), 0.5F, 0.25F, 0.5F);
        } else {
            rotation = 22.5F * (wall ? (2 + direction.get2DDataValue()) * 4 : blockState.getValue(SkullBlock.ROTATION));

            if (direction != null) {
                var v = direction.step();
                v.mul(0.001f);
                poseStack.translate(v.x(), v.y(), v.z());
            }
        }

        renderSkull(direction, rotation, 0, poseStack, bufferSource, packedLight, model, renderType);

        poseStack.popPose();
    }
}
