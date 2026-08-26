# BoatHud Extended（冰船 HUD 扩展版）

[English](README.md) | [简体中文](README_zh-CN.md)

面向 Fabric 冰船竞速的赛车风格 HUD——速度条 + 实时侧滑角、G 值表、油门/转向输入轨迹、延迟/FPS 显示、圈速计时与 CSV 遥测导出。仅客户端。

**多版本支持：** 1.21.1 · 1.21.4 · 1.21.8 · 1.21.11 · 26.1.x · 26.2

> 基于 [jewtvet 的 BoatHud Extended](https://github.com/jewtvet/boathud_extended) 的多版本移植，使用 Stonecutter 单代码库管线 + Mojang 映射重构。

## 功能

- **速度条**：三种视觉样式，超速闪烁提示
- **实时侧滑角**：精准掌握漂移状态
- **G 值表** + 油门/转向输入轨迹（扩展模式）
- **延迟/FPS** 显示，带颜色分级
- **圈速计时**：检查点文件，对比参考圈显示差值与速度差
- **CSV 遥测导出**：速度、纵/横向加速度、侧滑角、角速度、输入、坐标，方便赛后分析
- **配置界面**：ModMenu + Cloth Config，或直接编辑 `config/boathud.properties`
- 驾驶时自动隐藏原版快捷栏 / 状态栏 / 经验条

## 支持版本与依赖

| MC 版本 | Java | Fabric API | Cloth Config | Mod Menu（可选） |
|---|---|---|---|---|
| 1.21.1 | 21 | 0.116.15+1.21.1 | 15.0.140 | 11.0.4 |
| 1.21.4 | 21 | 0.119.4+1.21.4 | 17.0.144 | 13.0.4 |
| 1.21.8 | 21 | 0.136.1+1.21.8 | 19.0.147 | 15.0.2 |
| 1.21.11 | 21 | 0.141.6+1.21.11 | 21.11.153 | 17.0.0 |
| 26.1 / 26.1.1 / 26.1.2 | 25 | 0.155.2+26.1.2 | 26.1.154 | 18.0.0 |
| 26.2 | 25 | 0.158.0+26.2 | 26.2.155 | 20.0.1 |

> Fabric API / Cloth Config / Mod Menu 均可在 Modrinth 下载对应版本。

## 安装

1. 为你的游戏版本安装 [Fabric Loader](https://fabricmc.net/use/)
2. 将对应版本的 `boathud_extended-1.2.0-mc<版本>.jar`、Fabric API、Cloth Config 放入 `mods` 文件夹
3. 开船！

从 1.1.0 升级？`config/boathud.properties` 完全兼容，旧配置无需改动。

## 从源码构建

需要 JDK 21（26.x 的 JDK 25 工具链由 foojay 自动准备）。

```bash
./gradlew build            # 全部版本
./gradlew :1.21.8:build    # 单个版本
```

产物位于 `versions/<版本>/build/libs/`。

## 已知限制

- 26.1 / 26.2 暂不隐藏经验条（26.x 改为 contextual bar 渲染，方案研究中）
- 1.21.4 / 1.21.11 / 26.1 未做运行时实测（编译签名已对照官方映射核准）

## 致谢

- [jewtvet](https://github.com/jewtvet) — 原版 **BoatHud Extended**（1.21.1）
- [hibiii](https://github.com/hibiii) — **BoatHud**，26.x 渲染参照
- [FR-N](https://github.com/FR-N) — 多版本移植、NPE 修复、Stonecutter 管线

## 许可证

[MIT](LICENSE) — © FR-N、© jewtvet、© hibiii（详见 LICENSE 署名说明）。
