plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenLocal()
    maven("https://registry.somethingcatchy.net/repository/maven-public/")
}

dependencies {
    implementation("com.possible-triangle:core:1.4.+")
}
