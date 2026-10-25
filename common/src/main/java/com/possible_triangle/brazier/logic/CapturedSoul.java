package com.possible_triangle.brazier.logic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.entity.EntityType;

public record CapturedSoul(Holder<EntityType<?>> type) {

    public static final Codec<CapturedSoul> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(
                    RegistryFixedCodec.create(Registries.ENTITY_TYPE).fieldOf("type").forGetter(CapturedSoul::type)
            ).apply(builder, CapturedSoul::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CapturedSoul> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.holderRegistry(Registries.ENTITY_TYPE), CapturedSoul::type,
            CapturedSoul::new
    );

}
