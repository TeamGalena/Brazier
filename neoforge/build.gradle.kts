plugins {
    id("loader")
    id("com.possible-triangle.neoforge")
}

neoforge {
    dependOn(project(":common"))

    accessTransformer()

    dataGen {
        splitSourceSet()
    }
}

dependencies {
    modInclude(libs.galena.hats.neoforge)

    modInclude(libs.registrate.neoforge)
    modInclude(libs.multikulti.core.neoforge)
    modInclude(libs.multikulti.registrate.neoforge)

    modImplementation(libs.multikulti.datagen.neoforge)

    modCompileOnly(libs.jei.neoforge.api)

    modImplementation(libs.flywheel)
    modImplementation(libs.ponder)

    modImplementation(libs.create) { isTransitive = false }

    modRuntimeOnly(libs.jei.neoforge)

    "dataCompileOnly"(pack.neoforge.modrinth.supplementaries)
    modRuntimeOnly(pack.neoforge.modrinth.supplementaries)
    modRuntimeOnly(pack.neoforge.modrinth.moonlight)
    modRuntimeOnly(pack.neoforge.curseforge.configured)

    modRuntimeOnly(pack.neoforge.modrinth.freecam)
}

upload {
    modrinth {
        syncBodyFromReadme()
    }
}
