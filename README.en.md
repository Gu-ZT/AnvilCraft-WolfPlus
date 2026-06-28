# AnvilCraft WolfPlus

AnvilCraft WolfPlus is a NeoForge add-on mod for AnvilCraft.

## Current scope

Based on the current codebase, this project is mainly focused on power-grid extensions:

- Extending power-grid-related behavior through mixins
- Collecting transmitter points that can participate in line generation
- Using `DelaunayTriangulator` to build candidate connections
- Filtering those connections by overlap range before producing the rendered transmitter lines

Relevant source files:

- Mod entry point: `src/main/java/dev/anvilcraft/addon/wolfplus/AnvilCraftWolfPlus.java`
- Power-grid extension logic: `src/main/java/dev/anvilcraft/addon/wolfplus/util/ISimplePowerGridExtension.java`
- Line rendering utilities: `src/main/java/dev/anvilcraft/addon/wolfplus/util/PowerTransmitterLinesUtil.java`
- Mixin configuration: `src/main/resources/anvilcraft_wolfplus.mixins.json`

## Development environment

- Minecraft: `26.1.2`
- NeoForge: `26.1.2.76`
- Java toolchain: `25`

## Build

Run from the project root:

```bash
./gradlew build
```

To run tests only:

```bash
./gradlew test
```

## Notes

This repository does not currently include release packaging, compatibility matrices, or distribution instructions. If those are needed later, they should be documented alongside the actual release process.