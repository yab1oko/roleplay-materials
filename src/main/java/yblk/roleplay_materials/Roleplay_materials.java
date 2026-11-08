package yblk.roleplay_materials;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import yblk.roleplay_materials.registry.ModCreativeTabs;
import yblk.roleplay_materials.registry.ModItems;
import yblk.roleplay_materials.registry.ModRecipeSerializers;

@Mod(Roleplay_materials.MODID)
public class Roleplay_materials {
    public static final String MODID = "roleplay_materials";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Roleplay_materials() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(modEventBus);
        ModCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        ModRecipeSerializers.SERIALIZERS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("Roleplay Materials loaded");
    }
}
