package dev.anvilcraft.addon.wolfplus.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.anvilcraft.addon.wolfplus.util.PowerTransmitterLinesUtil;
import dev.dubhe.anvilcraft.client.support.PowerGridSupport;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PowerGridSupport.class)
public class PowerGridSupportMixin {
    @Inject(
        method = "submitEnhancedTransmitterLine",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getInstance()Lnet/minecraft/client/Minecraft;", ordinal = 1),
        cancellable = true
    )
    private static void submitEnhancedTransmitterLine(Vec3 camera, CallbackInfo ci) {
        PowerTransmitterLinesUtil.submitEnhancedTransmitterLine(camera);
        ci.cancel();
    }

    @Inject(
        method = "submitTransmitterLine",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getInstance()Lnet/minecraft/client/Minecraft;", ordinal = 1),
        cancellable = true
    )
    private static void submitTransmitterLine(PoseStack poseStack, SubmitNodeCollector nodeCollector, Vec3 camera, CallbackInfo ci) {
        PowerTransmitterLinesUtil.submitTransmitterLine(poseStack, nodeCollector, camera);
        ci.cancel();
    }
}
