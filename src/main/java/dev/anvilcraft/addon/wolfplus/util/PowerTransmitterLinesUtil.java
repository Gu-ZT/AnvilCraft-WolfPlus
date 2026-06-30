package dev.anvilcraft.addon.wolfplus.util;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.anvilcraft.lib.v2.rendering.ALRPostEffects;
import dev.dubhe.anvilcraft.api.power.SimplePowerGrid;
import dev.dubhe.anvilcraft.client.init.ModRenderTypes;
import dev.dubhe.anvilcraft.client.renderer.Line;
import dev.dubhe.anvilcraft.client.support.PowerGridSupport;
import dev.dubhe.anvilcraft.constant.Constant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("DataFlowIssue")
public class PowerTransmitterLinesUtil {
    public static void submitTransmitterLine(PoseStack poseStack, SubmitNodeCollector nodeCollector, Vec3 camera) {
        String level = Minecraft.getInstance().level.dimension().identifier().toString();
        Collection<SimplePowerGrid> gridToRender = PowerGridSupport.getGridMap().values();
        nodeCollector.submitCustomGeometry(
            poseStack, RenderTypes.lines(), (pose, buffer) -> {
                for (SimplePowerGrid grid : gridToRender) {
                    if (!grid.shouldRender(camera)) continue;
                    if (!grid.getLevel().equals(level)) continue;
                    PowerTransmitterLinesUtil.getPowerTransmitterLines(grid).forEach(it -> it.render(
                        pose,
                        buffer,
                        camera,
                        Constant.TRANSMITTER_LINE_COLOR
                    ));
                }
            }
        );
    }

    public static void submitEnhancedTransmitterLine(Vec3 camera) {
        String level = Minecraft.getInstance().level.dimension().identifier().toString();
        Collection<SimplePowerGrid> gridToRender = new ArrayList<>();
        for (SimplePowerGrid simplePowerGrid : PowerGridSupport.getGridMap().values()) {
            if (!simplePowerGrid.shouldRender(camera)) continue;
            if (!simplePowerGrid.getLevel().equals(level)) continue;
            if (PowerTransmitterLinesUtil.getPowerTransmitterLines(simplePowerGrid).isEmpty()) continue;
            gridToRender.add(simplePowerGrid);
        }
        if (gridToRender.isEmpty()) return;
        ALRPostEffects.getBloomPostEffect().drawBloomed(
            (nodeCollector, poseStack1) -> nodeCollector.submitCustomGeometry(
                poseStack1,
                ModRenderTypes.LINE_BLOOM,
                (pose, buffer) -> {
                    for (SimplePowerGrid grid : gridToRender) {
                        PowerTransmitterLinesUtil.getPowerTransmitterLines(grid).forEach(it -> it.render(
                            pose,
                            buffer,
                            camera,
                            Constant.TRANSMITTER_LINE_COLOR
                        ));
                    }
                }
            )
        );
    }

    public static Collection<Line> getPowerTransmitterLines(SimplePowerGrid grid) {
        ISimplePowerGridExtension extension = (ISimplePowerGridExtension) grid;
        extension.delta$createPowerTransmitterLines();
        extension.delta$getPowerTransmitterLines();
        return extension.delta$getPowerTransmitterLines();
    }

    public static boolean isOverlap(Vec3 a, int rangeA, Vec3 b, int rangeB) {
        AABB a1 = new AABB(
            a.x - rangeA - 0.5, a.y - rangeA - 0.5, a.z - rangeA - 0.5,
            a.x + rangeA + 0.5, a.y + rangeA + 0.5, a.z + rangeA + 0.5
        );
        AABB a2 = new AABB(
            b.x - rangeB - 0.5, b.y - rangeB - 0.5, b.z - rangeB - 0.5,
            b.x + rangeB + 0.5, b.y + rangeB + 0.5, b.z + rangeB + 0.5
        );
        return a1.intersects(a2);
    }
}
