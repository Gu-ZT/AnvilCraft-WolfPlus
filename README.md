# AnvilCraft WolfPlus

AnvilCraft WolfPlus 是一个基于 NeoForge 的 AnvilCraft 附属模组。

## 当前功能

从现有代码可以确认，本项目当前主要围绕功率网格（power grid）扩展：

- 通过 mixin 扩展功率网格相关逻辑
- 为输电组件收集可参与连线的点位
- 使用 `DelaunayTriangulator` 生成候选连线
- 再按覆盖范围是否重叠过滤，最终生成需要渲染的输电线

相关代码位置：

- 模组入口：`src/main/java/dev/anvilcraft/addon/wolfplus/AnvilCraftWolfPlus.java`
- 功率网格扩展：`src/main/java/dev/anvilcraft/addon/wolfplus/util/ISimplePowerGridExtension.java`
- 连线渲染工具：`src/main/java/dev/anvilcraft/addon/wolfplus/util/PowerTransmitterLinesUtil.java`
- mixin 配置：`src/main/resources/anvilcraft_wolfplus.mixins.json`

## 开发环境

- Minecraft: `26.1.2`
- NeoForge: `26.1.2.76`
- Java toolchain: `25`

## 构建

在项目根目录执行：

```bash
./gradlew build
```

如需只运行测试：

```bash
./gradlew test
```

## 说明

当前仓库没有额外提供安装包分发、兼容性矩阵或发布说明；如需补充，建议基于后续实际发布流程再完善。