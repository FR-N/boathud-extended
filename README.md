# BoatHud Extended

[English](README.md) | [简体中文](README_zh-CN.md)

A racing-style HUD for ice boat racing on Fabric — speed bar with live slip angle, G-meter, throttle & steering traces, ping/FPS readout, lap timing and CSV telemetry. Client-side only.

**Now multi-version:** 1.21.1 · 1.21.4 · 1.21.8 · 1.21.11 · 26.1.x · 26.2

> A multi-version port of [jewtvet's BoatHud Extended](https://github.com/jewtvet/boathud_extended), rebuilt on a Stonecutter single-codebase pipeline with Mojang mappings.

## Features

- **Speed bar** with three visual styles and overspeed blink
- **Live slip angle** readout — know exactly how sideways you are
- **G-meter** + throttle/steering input traces (extended mode)
- **Ping & FPS** display with colour coding
- **Lap timing** — checkpoint files with delta & speed diff vs reference laps
- **CSV telemetry export** for post-run analysis (speed, gLon, gLat, slip angle, angular velocity, inputs, position)
- **Config screen** via ModMenu + Cloth Config, or edit `config/boathud.properties` directly
- Hides the vanilla hotbar/status bars/experience bar while driving

## Dependencies

[Fabric API](https://modrinth.com/mod/fabric-api) is required. [Cloth Config](https://modrinth.com/mod/cloth-config) is required for the config screen, [Mod Menu](https://modrinth.com/mod/modmenu) is optional.

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) for your Minecraft version
2. Drop the matching `boathud_extended-1.2.0-mc<version>.jar`, Fabric API and Cloth Config into your `mods` folder
3. Drive

Upgrading from 1.1.0? Your `config/boathud.properties` carries over unchanged.

## Building from source

Requires JDK 21 (and JDK 25 toolchain provisioning for 26.x — handled automatically via foojay).

```bash
./gradlew build            # all versions
./gradlew :1.21.8:build    # a single version
```

Jars land in `versions/<version>/build/libs/`.

## Credits

- [jewtvet](https://github.com/jewtvet) — original **BoatHud Extended** (1.21.1)
- [hibiii](https://github.com/hibiii) — **BoatHud**, reference patterns for 26.x rendering
- [FR-N](https://github.com/FR-N) — multi-version port, NPE fixes, Stonecutter pipeline

## License

[MIT](LICENSE) — © FR-N, © jewtvet, © hibiii (see LICENSE for attribution details).
