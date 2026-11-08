package yblk.roleplay_materials.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KeyItem extends Item {
    public static final String TEXTURE_TAG = "Texture";
    public static final int TEXTURE_COUNT = 6;

    public KeyItem(Properties properties) {
        super(properties);
    }

    public static int getTexture(ItemStack stack) {
        if (!stack.hasTag() || !stack.getTag().contains(TEXTURE_TAG, Tag.TAG_INT)) {
            return 1;
        }
        return Mth.clamp(stack.getTag().getInt(TEXTURE_TAG), 1, TEXTURE_COUNT);
    }

    /**
     * Sets visual texture index while preserving every other NBT field.
     * Texture 1 clears only Texture/CustomModelData so a fresh creative key still matches.
     */
    public static void setTexture(ItemStack stack, int texture) {
        int value = Mth.clamp(texture, 1, TEXTURE_COUNT);
        CompoundTag tag = stack.getTag();
        if (value <= 1) {
            if (tag == null) {
                return;
            }
            tag.remove(TEXTURE_TAG);
            tag.remove("CustomModelData");
            if (tag.isEmpty()) {
                stack.setTag(null);
            }
            return;
        }
        tag = stack.getOrCreateTag();
        tag.putInt(TEXTURE_TAG, value);
        tag.putInt("CustomModelData", value);
    }

    public static int cycleTexture(ItemStack stack) {
        int next = getTexture(stack) % TEXTURE_COUNT + 1;
        setTexture(stack, next);
        return next;
    }

    private static boolean tryCycle(Player player, ItemStack stack, Level level) {
        if (player == null || !player.getAbilities().instabuild || !player.isShiftKeyDown()) {
            return false;
        }
        if (!level.isClientSide) {
            int texture = cycleTexture(stack);
            player.displayClientMessage(
                    Component.translatable("item.roleplay_materials.key.texture", texture, TEXTURE_COUNT)
                            .withStyle(ChatFormatting.GRAY),
                    true);
        }
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (tryCycle(player, stack, level)) {
            return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (tryCycle(player, context.getItemInHand(), context.getLevel())) {
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide());
        }
        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("item.roleplay_materials.key.texture", getTexture(stack), TEXTURE_COUNT)
                .withStyle(ChatFormatting.DARK_GRAY));
        tooltip.add(Component.translatable("item.roleplay_materials.key.tooltip")
                .withStyle(ChatFormatting.DARK_GRAY));
    }
}
