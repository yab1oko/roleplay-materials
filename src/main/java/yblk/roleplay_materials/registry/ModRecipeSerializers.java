package yblk.roleplay_materials.registry;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yblk.roleplay_materials.Roleplay_materials;
import yblk.roleplay_materials.recipe.ToolDamageShapelessRecipe;

public final class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Roleplay_materials.MODID);

    public static final RegistryObject<RecipeSerializer<ToolDamageShapelessRecipe>> TOOL_DAMAGE_SHAPELESS =
            SERIALIZERS.register("tool_damage_shapeless", ToolDamageShapelessRecipe.Serializer::new);

    private ModRecipeSerializers() {
    }
}
