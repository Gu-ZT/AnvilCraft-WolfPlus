package dev.anvilcraft.addon.wolfplus.util;

import dev.anvilcraft.addon.wolfplus.util.geometry.DelaunayTriangulator;
import dev.dubhe.anvilcraft.api.power.PowerComponentType;
import dev.dubhe.anvilcraft.api.power.SimplePowerGrid;
import dev.dubhe.anvilcraft.client.renderer.Line;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ISimplePowerGridExtension {
    default SimplePowerGrid self() {
        return (SimplePowerGrid) this;
    }

    default Set<Line> delta$getPowerTransmitterLines() {
        throw new AssertionError();
    }

    default void delta$setPowerTransmitterLines(Set<Line> lines) {
        throw new AssertionError();
    }

    default void delta$createPowerTransmitterLines() {
        if (!this.delta$getPowerTransmitterLines().isEmpty()) return;
        List<Map.Entry<Vec3, Integer>> shapes = self().getPowerComponentInfoList().stream()
            .filter(it -> it.type() == PowerComponentType.TRANSMITTER)
            .map(it -> Map.entry(it.pos().getCenter(), it.range()))
            .toList();
        if (shapes.size() <= 2) {
            if (shapes.size() == 2) {
                this.delta$setPowerTransmitterLines(
                    Set.of(
                        new Line(
                            shapes.get(0).getKey(),
                            shapes.get(1).getKey(),
                            (float) shapes.get(0).getKey().distanceTo(shapes.get(1).getKey())
                        )
                    )
                );
            }
            return;
        }
        Map<Vec3, Integer> map = Map.ofEntries(shapes.<Map.Entry<Vec3, Integer>>toArray(Map.Entry[]::new));
        List<Vec3> points = shapes.stream().map(Map.Entry::getKey).toList();
        Set<Line> lines = new HashSet<>();
        for (DelaunayTriangulator.Edge edge : DelaunayTriangulator.triangulate(points.size(), index -> {
            Vec3 vec3 = points.get(index);
            double offset = (vec3.y - 1024) / 2048;
            return new DelaunayTriangulator.Point(index, vec3.x + offset, vec3.z + offset);
        })) {
            Vec3 vec3 = points.get(edge.a());
            Vec3 vec4 = points.get(edge.b());
            int i1 = map.getOrDefault(vec3, 0);
            int i2 = map.getOrDefault(vec4, 0);
            if (!PowerTransmitterLinesUtil.isOverlap(vec3, i1, vec4, i2)) continue;
            lines.add(new Line(
                vec3,
                vec4,
                (float) vec3.distanceTo(vec4)
            ));
        }
        this.delta$setPowerTransmitterLines(lines);
    }
}
