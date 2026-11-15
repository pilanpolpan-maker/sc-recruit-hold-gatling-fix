package com.example.rsp.mixin;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = {
    "com.talhanation.recruits.client.render.RecruitHumanRenderer",
    "com.talhanation.recruits.client.render.RecruitVillagerRenderer"
}, remap = false)
public abstract class RecruitPoseMixin {

    @Inject(method = "getArmPose", at = @At("HEAD"), cancellable = true, remap = false)
    private static void rsp$pose(@Coerce LivingEntity entity, InteractionHand hand,
                                 CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
        if (rsp$isScorchedGun(entity.getItemInHand(hand))) {
            cir.setReturnValue(HumanoidModel.ArmPose.CROSSBOW_HOLD);
        }
    }

    private static boolean rsp$isScorchedGun(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return false;
        Item item = stack.getItem();
        if (!"scguns".equals(BuiltInRegistries.ITEM.getKey(item).getNamespace())) return false;
        for (Class<?> c = item.getClass(); c != null; c = c.getSuperclass()) {
            if ("top.ribs.scguns.item.GunItem".equals(c.getName())) return true;
        }
        return false;
    }
}
