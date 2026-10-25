package com.possible_triangle.brazier;

import com.possible_triangle.brazier.world.block.CursedSkullBlock;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber
public class NeoForgeClientEvents {

    @SubscribeEvent
    private static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CursedSkullBlock.LAYER, SkullModel::createHumanoidHeadLayer);
    }

    @SubscribeEvent
    private static void registerSkullModel(EntityRenderersEvent.CreateSkullModels event) {
        var baked = event.getEntityModelSet().bakeLayer(CursedSkullBlock.LAYER);
        event.registerSkullModel(CursedSkullBlock.TYPE, new SkullModel(baked));
        SkullBlockRenderer.SKIN_BY_TYPE.put(CursedSkullBlock.TYPE, CursedSkullBlock.TEXTURE);
    }

}
