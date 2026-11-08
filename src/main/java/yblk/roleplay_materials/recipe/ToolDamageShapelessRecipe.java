package yblk.roleplay_materials.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;
import yblk.roleplay_materials.registry.ModRecipeSerializers;

/**
 * Shapeless craft: material ingredients + one tool ingredient that is damaged, not consumed.
 */
public class ToolDamageShapelessRecipe implements CraftingRecipe {
    private final ResourceLocation id;
    private final String group;
    private final Ingredient tool;
    private final NonNullList<Ingredient> materials;
    private final ItemStack result;
    private final int damage;

    public ToolDamageShapelessRecipe(ResourceLocation id, String group, Ingredient tool,
                                     NonNullList<Ingredient> materials, ItemStack result, int damage) {
        this.id = id;
        this.group = group;
        this.tool = tool;
        this.materials = materials;
        this.result = result;
        this.damage = Math.max(1, damage);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        int toolSlot = findToolSlot(container);
        if (toolSlot < 0) {
            return false;
        }
        java.util.List<ItemStack> remaining = new java.util.ArrayList<>();
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty() && i != toolSlot) {
                remaining.add(stack);
            }
        }
        if (remaining.size() != materials.size()) {
            return false;
        }
        boolean[] used = new boolean[remaining.size()];
        for (Ingredient ingredient : materials) {
            boolean found = false;
            for (int i = 0; i < remaining.size(); i++) {
                if (used[i]) {
                    continue;
                }
                if (ingredient.test(remaining.get(i))) {
                    used[i] = true;
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess access) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= materials.size() + 1;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return result;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);
        int toolSlot = findToolSlot(container);
        for (int i = 0; i < remaining.size(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.isEmpty()) {
                continue;
            }
            if (i == toolSlot) {
                ItemStack copy = stack.copy();
                int newDamage = copy.getDamageValue() + damage;
                if (newDamage < copy.getMaxDamage()) {
                    copy.setDamageValue(newDamage);
                    remaining.set(i, copy);
                }
                continue;
            }
            ItemStack leftover = ForgeHooks.getCraftingRemainingItem(stack);
            if (!leftover.isEmpty()) {
                remaining.set(i, leftover);
            }
        }
        return remaining;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> all = NonNullList.create();
        all.add(tool);
        all.addAll(materials);
        return all;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.TOOL_DAMAGE_SHAPELESS.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }

    private int findToolSlot(CraftingContainer container) {
        int found = -1;
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.isEmpty() || !tool.test(stack)) {
                continue;
            }
            if (!stack.isDamageableItem() || stack.getDamageValue() >= stack.getMaxDamage()) {
                continue;
            }
            if (found >= 0) {
                return -1;
            }
            found = i;
        }
        return found;
    }

    public static class Serializer implements RecipeSerializer<ToolDamageShapelessRecipe> {
        @Override
        public ToolDamageShapelessRecipe fromJson(ResourceLocation id, JsonObject json) {
            String group = GsonHelper.getAsString(json, "group", "");
            Ingredient tool = Ingredient.fromJson(json.get("tool"), false);
            NonNullList<Ingredient> materials = NonNullList.create();
            JsonArray array = GsonHelper.getAsJsonArray(json, "ingredients");
            for (int i = 0; i < array.size(); i++) {
                Ingredient ingredient = Ingredient.fromJson(array.get(i), false);
                if (!ingredient.isEmpty()) {
                    materials.add(ingredient);
                }
            }
            ItemStack result = net.minecraft.world.item.crafting.ShapedRecipe.itemStackFromJson(
                    GsonHelper.getAsJsonObject(json, "result"));
            int damage = GsonHelper.getAsInt(json, "damage", 1);
            return new ToolDamageShapelessRecipe(id, group, tool, materials, result, damage);
        }

        @Override
        public ToolDamageShapelessRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            String group = buf.readUtf();
            Ingredient tool = Ingredient.fromNetwork(buf);
            int size = buf.readVarInt();
            NonNullList<Ingredient> materials = NonNullList.withSize(size, Ingredient.EMPTY);
            for (int i = 0; i < size; i++) {
                materials.set(i, Ingredient.fromNetwork(buf));
            }
            ItemStack result = buf.readItem();
            int damage = buf.readVarInt();
            return new ToolDamageShapelessRecipe(id, group, tool, materials, result, damage);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ToolDamageShapelessRecipe recipe) {
            buf.writeUtf(recipe.group);
            recipe.tool.toNetwork(buf);
            buf.writeVarInt(recipe.materials.size());
            for (Ingredient ingredient : recipe.materials) {
                ingredient.toNetwork(buf);
            }
            buf.writeItem(recipe.result);
            buf.writeVarInt(recipe.damage);
        }
    }
}
