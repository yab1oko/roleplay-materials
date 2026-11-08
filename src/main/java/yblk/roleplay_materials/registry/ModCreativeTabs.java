package yblk.roleplay_materials.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import yblk.roleplay_materials.Roleplay_materials;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Roleplay_materials.MODID);

    public static final RegistryObject<CreativeModeTab> MAIN = CREATIVE_MODE_TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.roleplay_materials"))
                    .icon(() -> new ItemStack(ModItems.SCRAP.get()))
                    .displayItems((params, output) -> {
                        output.accept(ModItems.SCRAP.get());
                        output.accept(ModItems.WOOD.get());
                        output.accept(ModItems.PLANK.get());
                        output.accept(ModItems.CLOTH.get());
                        output.accept(ModItems.METAL_PIPE.get());
                        output.accept(ModItems.NAILBOX.get());
                        output.accept(ModItems.GLUE.get());
                        output.accept(ModItems.TAPE.get());
                        output.accept(ModItems.SEWING_KIT.get());
                        output.accept(ModItems.CHEMICAL.get());
                        output.accept(ModItems.PEBBLE.get());
                        output.accept(ModItems.OLD_MONEY.get());
                        output.accept(ModItems.TALON_TICKET.get());
                        output.accept(ModItems.KEY.get());
                        output.accept(ModItems.FORGED_IRON_NUGGET.get());
                        output.accept(ModItems.FORGED_IRON_INGOT.get());
                        output.accept(ModItems.FORGED_IRON_PLATE.get());
                        output.accept(ModItems.CIRCUIT.get());
                        output.accept(ModItems.MOTOR.get());
                        output.accept(ModItems.MACE_PART.get());
                        output.accept(ModItems.CIRCULAR_SAW.get());
                        output.accept(ModItems.SAW_SWORD_PART.get());
                        output.accept(ModItems.MACHETE_PART.get());
                        output.accept(ModItems.SPEAR_STEEL_HEAD.get());
                        output.accept(ModItems.BATTERY.get());
                        output.accept(ModItems.GAS_FILTER.get());
                    })
                    .build());

    private ModCreativeTabs() {
    }
}
