package com.possible_triangle.brazier.datagen.providers;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateProvider;
import net.mehvahdjukaar.supplementaries.client.MobHeadShadersManager.MobHeadEffect;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.data.JsonCodecProvider;

public class MobHeadEffectsRegistrateProvider extends JsonCodecProvider<MobHeadEffect> implements RegistrateProvider {

    public static final ProviderType<MobHeadEffectsRegistrateProvider> TYPE = ProviderType.registerProvider("Loot Injects", MobHeadEffectsRegistrateProvider::new);

    private final AbstractRegistrate<?> parent;

    public MobHeadEffectsRegistrateProvider(ProviderType.Context<?> context) {
        super(context.output(), PackOutput.Target.RESOURCE_PACK, "mob_head_effects", PackType.CLIENT_RESOURCES, MobHeadEffect.CODEC, context.provider(), context.parent().getModid(), context.fileHelper());
        this.parent = context.parent();
    }

    @Override
    public LogicalSide getSide() {
        return LogicalSide.CLIENT;
    }

    @Override
    protected void gather() {
        parent.genData(TYPE, this);
    }

    public void add(ResourceLocation id, MobHeadEffect value) {
        conditionally(id, builder -> builder
                .withCarrier(value)
                .addCondition(new ModLoadedCondition("supplementaries"))
        );
    }

}
