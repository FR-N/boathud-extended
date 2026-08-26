# Changelog

## 1.2.0 — Multi-version release

First multi-version release, rebuilt on a Stonecutter single-codebase pipeline.

### Supported Minecraft versions
- 1.21.1
- 1.21.4
- 1.21.8
- 1.21.11
- 26.1 / 26.1.1 / 26.1.2 (single jar covers all three)
- 26.2

### Added
- Full feature parity across all versions: speed bar, slip angle, G-meter,
  throttle/steering traces, ping & FPS display, telemetry CSV export,
  checkpoint timing
- Per-version HUD integration: vanilla HudRenderCallback on 1.21.x,
  extract-based rendering on 26.x
- Experience bar hiding via ExperienceBarRenderer mixin on 1.21.8+
- GitHub Actions CI building all versions (JDK 21 + 25)

### Fixed
- NullPointerException when mounting a boat right after joining a server
  before the player list entry arrives (login-into-boat anti-cheat setups)
- HUD state no longer leaks across server disconnects

### Changed
- Package renamed from `jewtvet.boathud_extended` to `frn.boathud_extended`
  (config file `boathud.properties` unchanged — old configs carry over)
- Build system migrated to Stonecutter + loom-back-compat with Mojang mappings
  on all versions
