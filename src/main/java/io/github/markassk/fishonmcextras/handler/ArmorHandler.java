package io.github.markassk.fishonmcextras.handler;

import io.github.markassk.fishonmcextras.FOMC.Enums.Location;
import io.github.markassk.fishonmcextras.FOMC.Types.Armor;
import io.github.markassk.fishonmcextras.FOMC.Types.FOMCItem;
import io.github.markassk.fishonmcextras.config.FishOnMCExtrasConfig;
import io.github.markassk.fishonmcextras.util.ItemStackHelper;
import io.github.markassk.fishonmcextras.util.TextHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArmorHandler {
    private static ArmorHandler INSTANCE = new ArmorHandler();
    private final FishOnMCExtrasConfig config = FishOnMCExtrasConfig.getConfig();

    public enum CustomArmorQuality {
        BROKEN(Text.literal("ʙʀᴏᴋᴇɴ").withColor(0xFF74403B)),
        TORN(Text.literal("ᴛᴏʀɴ").withColor(0xFFFF5555)),
        DAMAGED(Text.literal("ᴅᴀᴍᴀɢᴇᴅ").withColor(0xFFFCFC54)),
        BLEMISHED(Text.literal("ʙʟᴇᴍɪsʜᴇᴅ").withColor(0xFFFCA800)),
        WELL_WORN(Text.literal("ᴡᴇʟʟ ᴡᴏʀɴ").withColor(0xFF54FC54)),
        USED(Text.literal("ᴜsᴇᴅ").withColor(0xFF00A800)),
        MINT(Text.literal("ᴍɪɴᴛ").withColor(0xFF54FCFC)),
        SUBLIME(Text.literal("sᴜʙʟɪᴍᴇ").withColor(0xFFFC54FC)),
        SUPERIOR(Text.literal("sᴜᴘᴇʀɪᴏʀ").withColor(0xFFA800A8));

        public final Text tag;

        CustomArmorQuality(Text tag) {
            this.tag = tag;
        }

        public static CustomArmorQuality valueOfQuality(int quality) {
            if (quality <= 1)
                return BROKEN;
            if (quality < 40)
                return TORN;
            if (quality < 50)
                return DAMAGED;
            if (quality < 60)
                return BLEMISHED;
            if (quality < 70)
                return WELL_WORN;
            if (quality < 80)
                return USED;
            if (quality < 90)
                return MINT;
            if (quality < 100)
                return SUBLIME;
            return SUPERIOR;
        }
    }

    public ItemStack currentChestplateItem = Items.AIR.getDefaultStack();
    public ItemStack currentLeggingsItem = Items.AIR.getDefaultStack();
    public ItemStack currentBootsItem = Items.AIR.getDefaultStack();
    public Armor currentChestplate = null;
    public Armor currentLeggings = null;
    public Armor currentBoots = null;
    public boolean isWrongChestplateClimate = false;
    public boolean isWrongLeggingsClimate = false;
    public boolean isWrongBootsClimate = false;

    public static ArmorHandler instance() {
        if (INSTANCE == null) {
            INSTANCE = new ArmorHandler();
        }
        return INSTANCE;
    }

    public void tick(MinecraftClient minecraftClient) {
        if (minecraftClient.player == null) {
            return;
        }

        if (!currentBootsItem
                .equals(minecraftClient.player.getInventory().armor.get(EquipmentSlot.FEET.getEntitySlotId())) &&
                minecraftClient.player.getInventory().armor.get(EquipmentSlot.FEET.getEntitySlotId())
                        .getItem() == Items.LEATHER_BOOTS) {
            Armor armor = Armor
                    .getArmor(minecraftClient.player.getInventory().armor.get(EquipmentSlot.FEET.getEntitySlotId()));
            if (armor != null) {
                this.currentBootsItem = minecraftClient.player.getInventory().armor
                        .get(EquipmentSlot.FEET.getEntitySlotId());
                this.currentBoots = armor;
            }
        } else if (minecraftClient.player.getInventory().armor.get(EquipmentSlot.FEET.getEntitySlotId()).isEmpty()) {
            this.currentBootsItem = Items.AIR.getDefaultStack();
            this.currentBoots = null;
        }

        if (!currentLeggingsItem
                .equals(minecraftClient.player.getInventory().armor.get(EquipmentSlot.LEGS.getEntitySlotId())) &&
                minecraftClient.player.getInventory().armor.get(EquipmentSlot.LEGS.getEntitySlotId())
                        .getItem() == Items.LEATHER_LEGGINGS) {
            Armor armor = Armor
                    .getArmor(minecraftClient.player.getInventory().armor.get(EquipmentSlot.LEGS.getEntitySlotId()));
            if (armor != null) {
                this.currentLeggingsItem = minecraftClient.player.getInventory().armor
                        .get(EquipmentSlot.LEGS.getEntitySlotId());
                this.currentLeggings = armor;
            }
        } else if (minecraftClient.player.getInventory().armor.get(EquipmentSlot.LEGS.getEntitySlotId()).isEmpty()) {
            this.currentLeggingsItem = Items.AIR.getDefaultStack();
            this.currentLeggings = null;
        }

        if (!currentChestplateItem
                .equals(minecraftClient.player.getInventory().armor.get(EquipmentSlot.CHEST.getEntitySlotId())) &&
                minecraftClient.player.getInventory().armor.get(EquipmentSlot.CHEST.getEntitySlotId())
                        .getItem() == Items.LEATHER_CHESTPLATE) {
            Armor armor = Armor
                    .getArmor(minecraftClient.player.getInventory().armor.get(EquipmentSlot.CHEST.getEntitySlotId()));
            if (armor != null) {
                this.currentChestplateItem = minecraftClient.player.getInventory().armor
                        .get(EquipmentSlot.CHEST.getEntitySlotId());
                this.currentChestplate = armor;
            }
        } else if (minecraftClient.player.getInventory().armor.get(EquipmentSlot.CHEST.getEntitySlotId()).isEmpty()) {
            this.currentChestplateItem = Items.AIR.getDefaultStack();
            this.currentChestplate = null;
        }

        if (BossBarHandler.instance().currentLocation != Location.UNKNOWN
                && BossBarHandler.instance().currentLocation != Location.CREW_ISLAND) {
            String currentLocationClimate = BossBarHandler.instance().currentLocation.CLIMATE.ID;
            this.isWrongChestplateClimate = currentChestplate != null
                    && !Objects.equals(currentChestplate.climate.ID, currentLocationClimate);
            this.isWrongLeggingsClimate = currentLeggings != null
                    && !Objects.equals(currentLeggings.climate.ID, currentLocationClimate);
            this.isWrongBootsClimate = currentBoots != null
                    && !Objects.equals(currentBoots.climate.ID, currentLocationClimate);
        }

        if (config.fun.changeArmorQuality) {
            for (ItemStack itemStack : minecraftClient.player.getInventory().main) {
                if (FOMCItem.isArmor(itemStack)) {
                    updateLore(itemStack);
                }
            }
            
            for (ItemStack itemStack : minecraftClient.player.getInventory().armor) {
                if (FOMCItem.isArmor(itemStack)) {
                    updateLore(itemStack);
                }
            }

            if (minecraftClient.currentScreen != null && minecraftClient.player.currentScreenHandler != null) {
                for (int i = 0; i < minecraftClient.player.currentScreenHandler.slots.size(); i++) {
                    ItemStack itemStack = minecraftClient.player.currentScreenHandler.getSlot(i).getStack();
                    if (FOMCItem.isArmor(itemStack)) {
                        updateLore(itemStack);
                    }
                }
            }
        }
    }

    public void appendTooltip(List<Text> textList, ItemStack itemStack) {
        if (config.armorStatsTooltip.showOnRerollScreen)
            this.appendTooltipArmorRollScreen(textList, itemStack);
        if (config.armorStatsTooltip.showOnPressingKeybind)
            this.appendTooltipArmorRoll(textList, itemStack);
    }

    private void appendTooltipArmorRoll(List<Text> textList, ItemStack itemStack) {
        if (itemStack.getItem() == Items.LEATHER_CHESTPLATE || itemStack.getItem() == Items.LEATHER_BOOTS
                || itemStack.getItem() == Items.LEATHER_LEGGINGS) {
            if (KeybindHandler.instance().showExtraInfo) {
                int offsetSlot = 0;

                NbtCompound nbtCompound = ItemStackHelper.getNbt(itemStack);
                if (nbtCompound != null && nbtCompound.contains("renderInfo", NbtElement.LIST_TYPE)) {
                    offsetSlot = 7;
                }

                Armor armor = Armor.getArmor(itemStack);
                if (armor != null && armor.identified) {
                    Text emptyLine = armor.rarity.LORE_TAG;

                    int slot = textList.size() - (MinecraftClient.getInstance().options.advancedItemTooltips ? 9 : 7);
                    if (armor.armorBonuses.get(4).rolled)
                        insertArmorRollTooltip(slot - offsetSlot, 4, armor, emptyLine, textList);
                    if (armor.armorBonuses.get(3).rolled)
                        insertArmorRollTooltip(slot - offsetSlot, 3, armor, emptyLine, textList);
                    if (armor.armorBonuses.get(2).rolled)
                        insertArmorRollTooltip(slot - offsetSlot, 2, armor, emptyLine, textList);
                    if (armor.armorBonuses.get(1).rolled)
                        insertArmorRollTooltip(slot - offsetSlot, 1, armor, emptyLine, textList);
                    if (armor.armorBonuses.get(0).rolled)
                        insertArmorRollTooltip(slot - offsetSlot, 0, armor, emptyLine, textList);
                }
            }
        }
    }

    private void insertArmorRollTooltip(int slot, int index, Armor armor, Text prefix, List<Text> textList) {
        if (armor.armorBonuses.get(index).rolled) {
            int amount = calculateMoneyRolls(armor.armorBonuses.get(index).rolls, armor.armorBonuses.get(index).tier);

            Text amountText = TextHelper.concat(
                    prefix,
                    Text.literal("    └ ʀᴇʀᴏʟʟѕ: ").formatted(Formatting.GRAY),
                    Text.literal(String.valueOf(armor.armorBonuses.get(index).rolls - 1)).formatted(Formatting.YELLOW),
                    Text.literal("x").formatted(Formatting.WHITE),
                    Text.literal(" | ᴛᴏᴛᴀʟ: ").formatted(Formatting.GRAY),
                    Text.literal("$").formatted(Formatting.GREEN),
                    Text.literal(TextHelper.fmnt(amount)).formatted(Formatting.GREEN));

            textList.add(slot + index, amountText);
        }
    }

    private void appendTooltipArmorRollScreen(List<Text> textList, ItemStack itemStack) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        boolean isEye = false;
        boolean isArmor = false;
        int slot = -1;
        Armor armor = null;
        Armor testArmor = null;

        if (minecraftClient.player != null) {
            testArmor = Armor.getArmor(minecraftClient.player.currentScreenHandler.getSlot(31).getStack());
        }

        for (int i = 0; i < (minecraftClient.player != null ? minecraftClient.player.currentScreenHandler.slots.size()
                : 0); i++) {
            ItemStack itemStackFromInv = minecraftClient.player.currentScreenHandler.getSlot(i).getStack();
            if (minecraftClient.player.currentScreenHandler.getSlot(i).inventory != minecraftClient.player
                    .getInventory()
                    && (itemStack.getItem() == Items.ENDER_EYE)
                    && itemStack.equals(itemStackFromInv)) {
                isEye = true;
                slot = i - 11;
            }

            if (minecraftClient.player.currentScreenHandler.getSlot(31).inventory != minecraftClient.player
                    .getInventory()
                    && testArmor != null
                    && isEye) {
                isArmor = true;
                armor = testArmor;
            }
        }

        if (isEye && isArmor && slot != -1) {
            int amount = calculateMoneyRolls(armor.armorBonuses.get(slot).rolls, armor.armorBonuses.get(slot).tier);
            Text amountText = TextHelper.concat(
                    textList.get(6).copy(),
                    Text.literal("  └ ʀᴇʀᴏʟʟѕ: ").formatted(Formatting.GRAY),
                    Text.literal(String.valueOf(armor.armorBonuses.get(slot).rolls - 1)).formatted(Formatting.YELLOW),
                    Text.literal("x").formatted(Formatting.WHITE),
                    Text.literal(" | ᴛᴏᴛᴀʟ: ").formatted(Formatting.GRAY),
                    Text.literal("$").formatted(Formatting.GREEN),
                    Text.literal(TextHelper.fmnt(amount)).formatted(Formatting.GREEN));

            textList.add(8, amountText);
        }
    }

    private static int calculateMoneyRolls(int rolls, int tier) {
        rolls = rolls - 1;

        int amount = 0;
        if (rolls > 0)
            amount += 100;
        if (rolls > 1)
            amount += 400;
        if (rolls > 2)
            amount += 900;
        if (rolls > 3)
            amount += 1600;
        if (rolls > 4)
            amount += 2500;
        if (rolls > 5)
            amount += 3600;
        if (rolls > 6)
            amount += 4900;
        if (rolls > 7)
            amount += 6400;
        if (rolls > 8)
            amount += 8100;
        if (rolls > 9)
            amount += 10000;
        if (rolls > 10)
            amount += 12100;
        if (rolls > 11)
            amount += 14400;
        if (rolls > 12)
            amount += 16900;
        if (rolls > 13)
            amount += 19600;
        if (rolls > 14)
            amount += 22500;
        if (rolls > 15 && tier < 4) {
            int extraRolls = rolls - 15;
            amount = amount + extraRolls * 25000;
            return amount;
        }
        if (rolls > 15)
            amount += 25600;
        if (rolls > 16)
            amount += 28900;
        if (rolls > 17)
            amount += 32400;
        if (rolls > 18 && tier == 4) {
            int extraRolls = rolls - 18;
            amount = amount + extraRolls * 32500;
            return amount;
        }
        if (rolls > 18)
            amount += 36100;
        if (rolls > 19)
            amount += 40000;
        if (rolls > 20)
            amount += 44100;
        if (rolls > 21)
            amount += 48400;
        if (rolls > 22)
            amount += 50000;

        if (rolls > 23 && tier == 5) {
            int extraRolls = rolls - 23;
            amount = amount + extraRolls * 50000;
        }
        return amount;
    }

    private void updateLore(ItemStack stack) {
        LoreComponent loreComponent = stack.get(DataComponentTypes.LORE);
        NbtCompound nbtCompound = ItemStackHelper.getNbt(stack);
        if (loreComponent == null)
            return;

        if (nbtCompound == null || !nbtCompound.contains("quality", NbtElement.NUMBER_TYPE))
            return;

        int quality = nbtCompound.getInt("quality");
        CustomArmorQuality customQuality = CustomArmorQuality.valueOfQuality(quality);

        List<Text> lines = new ArrayList<>(loreComponent.lines());
        boolean changed = false;

        for (int i = 0; i < lines.size(); i++) {
            Text line = lines.get(i);
            String lineText = line.getString();
            String lowered = lineText.toLowerCase();
            if (!lowered.contains("ᴇꞯᴜɪᴘᴍᴇɴᴛ ꞯᴜᴀʟɪᴛʏ")) {
                continue;
            }

            if (lineText.contains(customQuality.tag.getString())) {
                continue;
            }

            MutableText newLine = Text.empty().setStyle(line.getStyle());

            Armor armor = Armor.getArmor(stack);

            Text rarityPrefixText = armor.rarity.LORE_TAG.copy()
                    .setStyle(net.minecraft.text.Style.EMPTY
                            .withColor(Formatting.WHITE)
                            .withItalic(false)
                            .withBold(false)
                            .withStrikethrough(false)
                            .withObfuscated(false)).append(" ");
            
            Text prefix = Text.literal(" ᴇꞯᴜɪᴘᴍᴇɴᴛ ꞯᴜᴀʟɪᴛʏ: ")
                    .setStyle(net.minecraft.text.Style.EMPTY
                            .withColor(Formatting.DARK_GRAY)
                            .withItalic(false));

            MutableText qualityText = customQuality.tag.copy().setStyle(
                    customQuality.tag.getStyle()
                            .withItalic(false)
                            .withBold(false)
                            .withStrikethrough(false)
                            .withObfuscated(false));

            Text bracketStart = Text.literal(" [")
                .setStyle(net.minecraft.text.Style.EMPTY
                        .withColor(Formatting.DARK_GRAY)
                        .withItalic(false));

            Text qualityValueText = Text.literal(String.valueOf(quality) + "%")
                    .setStyle(net.minecraft.text.Style.EMPTY
                            .withColor(qualityText.getStyle().getColor() != null ? qualityText.getStyle().getColor() : TextColor.fromRgb(0xFFFFFF))
                            .withItalic(false));

            Text bracketEnd = Text.literal("]")
                .setStyle(net.minecraft.text.Style.EMPTY
                        .withColor(Formatting.DARK_GRAY)
                        .withItalic(false));


            

            newLine.append(rarityPrefixText);
            newLine.append(prefix);     
            newLine.append(qualityText);
            newLine.append(bracketStart);
            newLine.append(qualityValueText);
            newLine.append(bracketEnd);
            
            lines.set(i, newLine);
            changed = true;
        }

        if (changed) {
            stack.set(DataComponentTypes.LORE, new LoreComponent(lines));
        }
    }
}
