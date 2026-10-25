package com.possible_triangle.brazier.index;

import static com.possible_triangle.brazier.index.BrazierContent.REGISTRATE;

import com.possible_triangle.brazier.logic.CapturedSoul;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

public class BrazierComponents {

    public static final RegistryEntry<DataComponentType<?>, DataComponentType<CapturedSoul>> CAPTURED_SOUL = REGISTRATE.object("captured_soul")
            .generic(Registries.DATA_COMPONENT_TYPE, () -> DataComponentType.<CapturedSoul>builder()
                    .persistent(CapturedSoul.CODEC)
                    .networkSynchronized(CapturedSoul.STREAM_CODEC)
                    .build()
            )
            .register();

    static void init() {
        // Load this class
    }

}
