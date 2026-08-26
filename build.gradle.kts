plugins {
    id("dev.kikugie.loom-back-compat")
}

// CI builds triggered by a v* tag derive the mod version from the tag itself,
// so the release jars always match the release version without touching
// stonecutter.properties.toml. Local builds fall back to mod.version.
val ciReleaseTag: String? = System.getenv("GITHUB_REF")
    ?.takeIf { it.startsWith("refs/tags/v") }
    ?.substringAfterLast("/")

version = if (ciReleaseTag != null) "${ciReleaseTag}-mc${sc.current.version}" else "${property("mod.version")}-mc${sc.current.version}"
base.archivesName = property("mod.id") as String
group = property("mod.group") as String

val requiredJava: JavaVersion = when {
    sc.current.parsed >= "26.1" -> JavaVersion.VERSION_25
    else -> JavaVersion.VERSION_21
}

val modId: String = sc.properties["mod.id"]
val modName: String = sc.properties["mod.name"]
val mcCompat: String = sc.properties["mod.mc_compat"]
val mixinPackage: String = (property("mod.group") as String) + ".mixin"
val mixinJava: String = "JAVA_" + requiredJava.majorVersion
// ExperienceBarMixin 仅存在于 1.21.8~1.21.11（经验条 1.21.8 起迁出 Gui 类；26.x 为 contextual bar 另行处理）
val extraMixins: String = if (sc.current.parsed >= "1.21.8" && sc.current.parsed < "26.1") ",\"ExperienceBarMixin\"" else ""
val fabricApi: String = sc.properties["deps.fabric_api"]
val modmenu: String = sc.properties["deps.modmenu"]
val clothConfig: String = sc.properties["deps.cloth"]

repositories {
    maven("https://maven.terraformersmc.com/releases/")
    maven("https://maven.shedaniel.me/")
}

dependencies {
    minecraft("com.mojang:minecraft:${sc.current.version}")
    loomx.applyMojangMappings()
    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricApi")
    modImplementation("com.terraformersmc:modmenu:$modmenu")
    modApi("me.shedaniel.cloth:cloth-config-fabric:$clothConfig") {
        exclude(group = "net.fabricmc.fabric-api")
    }
}

loom {
    runConfigs.all {
        runDirectory = rootProject.file("run")
    }
}

java {
    withSourcesJar()
    sourceCompatibility = requiredJava
    targetCompatibility = requiredJava
    toolchain {
        vendor = JvmVendorSpec.ADOPTIUM
        languageVersion = JavaLanguageVersion.of(requiredJava.majorVersion)
    }
}

tasks {
    processResources {
        val props = mapOf(
            "id" to modId,
            "name" to modName,
            "version" to version.toString(),
            "minecraft" to mcCompat,
            "java" to mixinJava,
            "extra_mixins" to extraMixins,
        )
        inputs.properties(props)
        filesMatching("fabric.mod.json") { expand(props) }
        filesMatching("*.mixins.json") { expand(props) }
    }
}
