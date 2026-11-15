package com.example.rsp.mixin;

import java.util.Set;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = HumanoidModel.class,
       targets = "com.talhanation.recruits.client.models.RecruitVillagerModel",
       remap = false)
public abstract class RecruitGatlingPoseMixin {

    private static final Set<String> RSP_GATLING = Set.of(
            "hullbreaker", "shard_culler", "cyclone", "spitfire", "gattaler",
            "thunderhead", "4k_mining_laser", "flayed_god", "weevil", "lone_wonder");
    private static final Set<String> RSP_ZOMBIE = Set.of("whizzbanger");

    @Inject(method = {
            "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V",
            "setupAnim(Lcom/talhanation/recruits/entities/AbstractRecruitEntity;FFFFF)V"
    }, at = @At("TAIL"), remap = false)
    private void rsp$pose(@Coerce LivingEntity entity, float f1, float f2, float ageInTicks,
                          float f4, float f5, CallbackInfo ci) {
        if (!entity.getClass().getName().startsWith("com.talhanation.recruits.")) return;
        ItemStack stack = entity.getMainHandItem();
        if (stack.isEmpty()) return;
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (!"scguns".equals(key.getNamespace())) return;

        HumanoidModel<?> m = (HumanoidModel<?>) (Object) this;
        String path = key.getPath();

        if (RSP_GATLING.contains(path)) {
            // ค่าจาก MiniGunPose.getForwardPose ของ Scorch (องศาแปลงเป็นเรเดียน)
            m.rightArm.xRot = -0.2618F;  // -15
            m.rightArm.yRot = -0.7854F;  // -45
            m.rightArm.zRot = 0.0F;
            m.leftArm.xRot = -0.7854F;   // -45
            m.leftArm.yRot = 0.5236F;    // 30
            m.leftArm.zRot = 0.0F;
        } else if (RSP_ZOMBIE.contains(path)) {
            float bobZ = (float) Math.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
            float bobX = (float) Math.sin(ageInTicks * 0.067F) * 0.05F;
            m.rightArm.xRot = -1.5708F + bobX;
            m.rightArm.yRot = 0.0F;
            m.rightArm.zRot = bobZ;
            m.leftArm.xRot = -1.5708F - bobX;
            m.leftArm.yRot = 0.0F;
            m.leftArm.zRot = -bobZ;
        }
    }
}
