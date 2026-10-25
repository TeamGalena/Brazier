package com.possible_triangle.brazier.datagen.providers;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateProvider;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.fml.LogicalSide;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;

public class LootInjectRegistrateProvider extends GlobalLootModifierProvider implements RegistrateProvider {

    public static final ProviderType<LootInjectRegistrateProvider> TYPE = ProviderType.registerServerData("Loot Injects", LootInjectRegistrateProvider::new);

    private final AbstractRegistrate<?> parent;

    public LootInjectRegistrateProvider(AbstractRegistrate<?> parent, PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, parent.getModid());
        this.parent = parent;
    }

    @Override
    public LogicalSide getSide() {
        return LogicalSide.SERVER;
    }

    @Override
    protected void start() {
        parent.genData(TYPE, this);
    }
}
