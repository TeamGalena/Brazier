plugins {
    id("loader")
    id("com.possible-triangle.neoforge-api")
}

dependencies {
    modCompileOnly(libs.registrate.neoforge)
    accessTransformers(libs.multikulti.core.common)
    modCompileOnlyApi(libs.multikulti.registrate.common) {
        exclude(module = "neoforge_stub")
    }

    modCompileOnly(libs.config.api.port.common)

    modCompileOnlyApi(libs.jei.common.api)
}
