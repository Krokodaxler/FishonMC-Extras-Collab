package io.github.markassk.fishonmcextras.handler;

import io.github.markassk.fishonmcextras.FOMC.Enums.EnumConstant;
import io.github.markassk.fishonmcextras.FOMC.Enums.FishSize;
import io.github.markassk.fishonmcextras.FOMC.Enums.FishVariant;
import io.github.markassk.fishonmcextras.FOMC.Enums.Rarity;
import io.github.markassk.fishonmcextras.screens.widget.IconButtonWidget;
import io.github.markassk.fishonmcextras.util.TextHelper;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class StatsImportHandler {
    private static StatsImportHandler INSTANCE = new StatsImportHandler();
    private ProfileDataHandler.ProfileData dummyProfileData = new ProfileDataHandler.ProfileData();

    public boolean screenInit = false;
    public boolean isOnScreen = false;

    public static StatsImportHandler instance() {
        if (INSTANCE == null) {
            INSTANCE = new StatsImportHandler();
        }
        return INSTANCE;
    }

    public void tick(MinecraftClient minecraftClient) {
        if(screenInit && isOwnPage(minecraftClient)) {
            this.createButton(minecraftClient);
            this.screenInit = false;
        }

        if(isOnScreen && minecraftClient.currentScreen != null && !Objects.equals(minecraftClient.currentScreen.getTitle().getString(), "\uEEE4픲")) {
            Screens.getButtons(minecraftClient.currentScreen).forEach(clickableWidget -> {
                if(clickableWidget.getMessage() != null) {
                    if(Objects.equals(clickableWidget.getMessage().getString(), "Import Stats")) {
                        clickableWidget.active = false;
                        clickableWidget.visible = false;
                        isOnScreen = false;
                    }
                }
            });
        }
    }

    private void getData(MinecraftClient minecraftClient) {
        ProfileDataHandler.ProfileData dummyProfileData = new ProfileDataHandler.ProfileData();
        ProfileDataHandler.ProfileData oldProfileData = ProfileDataHandler.instance().profileData;

        AtomicInteger fishCaught = new AtomicInteger(-1);

        for (int i = 0; i < Objects.requireNonNull(minecraftClient.player).currentScreenHandler.slots.size(); i++) {
            ItemStack itemStack = minecraftClient.player.currentScreenHandler.getSlot(i).getStack();

            if(minecraftClient.player.currentScreenHandler.getSlot(i).inventory != minecraftClient.player.getInventory() && itemStack.getItem() == Items.KNOWLEDGE_BOOK && isOwnPage(minecraftClient)) {
                List<Text> loreLines = Objects.requireNonNull(itemStack.get(DataComponentTypes.LORE)).lines();

                loreLines.forEach(lore -> {
                    String loreLine = lore.getString();
                    if (loreLine.contains(Rarity.COMMON.TAG.getString())) dummyProfileData.allRarityCounts.put(Rarity.COMMON, getValue(loreLine, Rarity.COMMON));
                    else if (loreLine.contains(Rarity.RARE.TAG.getString())) dummyProfileData.allRarityCounts.put(Rarity.RARE, getValue(loreLine, Rarity.RARE));
                    else if (loreLine.contains(Rarity.EPIC.TAG.getString())) dummyProfileData.allRarityCounts.put(Rarity.EPIC, getValue(loreLine, Rarity.EPIC));
                    else if (loreLine.contains(Rarity.LEGENDARY.TAG.getString())) dummyProfileData.allRarityCounts.put(Rarity.LEGENDARY, getValue(loreLine, Rarity.LEGENDARY));
                    else if (loreLine.contains(Rarity.MYTHICAL.TAG.getString())) dummyProfileData.allRarityCounts.put(Rarity.MYTHICAL, getValue(loreLine, Rarity.MYTHICAL));
                    else if (loreLine.contains(FishSize.BABY.TAG.getString())) dummyProfileData.allFishSizeCounts.put(FishSize.BABY, getValue(loreLine, FishSize.BABY));
                    else if (loreLine.contains(FishSize.JUVENILE.TAG.getString())) dummyProfileData.allFishSizeCounts.put(FishSize.JUVENILE, getValue(loreLine, FishSize.JUVENILE));
                    else if (loreLine.contains(FishSize.ADULT.TAG.getString())) dummyProfileData.allFishSizeCounts.put(FishSize.ADULT, getValue(loreLine, FishSize.ADULT));
                    else if (loreLine.contains(FishSize.LARGE.TAG.getString())) dummyProfileData.allFishSizeCounts.put(FishSize.LARGE, getValue(loreLine, FishSize.LARGE));
                    else if (loreLine.contains(FishSize.GIGANTIC.TAG.getString())) dummyProfileData.allFishSizeCounts.put(FishSize.GIGANTIC, getValue(loreLine, FishSize.GIGANTIC));
                    else if (loreLine.contains(FishVariant.ALBINO.TAG.getString())) dummyProfileData.allVariantCounts.put(FishVariant.ALBINO, getValue(loreLine, FishVariant.ALBINO));
                    else if (loreLine.contains(FishVariant.MELANISTIC.TAG.getString())) dummyProfileData.allVariantCounts.put(FishVariant.MELANISTIC, getValue(loreLine, FishVariant.MELANISTIC));
                    else if (loreLine.contains(FishVariant.TROPHY.TAG.getString())) dummyProfileData.allVariantCounts.put(FishVariant.TROPHY, getValue(loreLine, FishVariant.TROPHY));
                    else if (loreLine.contains(FishVariant.FABLED.TAG.getString())) dummyProfileData.allVariantCounts.put(FishVariant.FABLED, getValue(loreLine, FishVariant.FABLED));
                    else if (loreLine.contains(FishVariant.SPOOKY.TAG.getString())) dummyProfileData.allVariantCounts.put(FishVariant.SPOOKY, getValue(loreLine, FishVariant.SPOOKY));
                    else if (loreLine.contains(FishVariant.FROZEN.TAG.getString())) dummyProfileData.allVariantCounts.put(FishVariant.FROZEN, getValue(loreLine, FishVariant.FROZEN));
                    else if (loreLine.contains("ꜰɪꜱʜ ᴄᴀᴜɢʜᴛ")) fishCaught.set(getValue(loreLine));
                });
            }
        }

        if(fishCaught.get() != -1) {
            dummyProfileData.allPetCaughtCount = oldProfileData.allPetCaughtCount;
            dummyProfileData.allShardCaughtCount = oldProfileData.allShardCaughtCount;
            dummyProfileData.allFishCaughtCount = fishCaught.get();
            dummyProfileData.petDryStreak = Math.min(fishCaught.get() - (oldProfileData.allFishCaughtCount - oldProfileData.petDryStreak), fishCaught.get());
            dummyProfileData.shardDryStreak = Math.min(fishCaught.get() - (oldProfileData.allFishCaughtCount - oldProfileData.shardDryStreak), fishCaught.get());
            dummyProfileData.rarityDryStreak.put(Rarity.COMMON, Math.min(fishCaught.get() - (oldProfileData.allFishCaughtCount - oldProfileData.rarityDryStreak.getOrDefault(Rarity.COMMON, oldProfileData.allFishCaughtCount)), fishCaught.get()));
            dummyProfileData.rarityDryStreak.put(Rarity.RARE, Math.min(fishCaught.get() - (oldProfileData.allFishCaughtCount - oldProfileData.rarityDryStreak.getOrDefault(Rarity.RARE, oldProfileData.allFishCaughtCount)), fishCaught.get()));
            dummyProfileData.rarityDryStreak.put(Rarity.EPIC, Math.min(fishCaught.get() - (oldProfileData.allFishCaughtCount - oldProfileData.rarityDryStreak.getOrDefault(Rarity.EPIC, oldProfileData.allFishCaughtCount)), fishCaught.get()));
            dummyProfileData.rarityDryStreak.put(Rarity.LEGENDARY, Math.min(fishCaught.get() - (oldProfileData.allFishCaughtCount - oldProfileData.rarityDryStreak.getOrDefault(Rarity.LEGENDARY, oldProfileData.allFishCaughtCount)), fishCaught.get()));
            dummyProfileData.rarityDryStreak.put(Rarity.MYTHICAL, Math.min(fishCaught.get() - (oldProfileData.allFishCaughtCount - oldProfileData.rarityDryStreak.getOrDefault(Rarity.MYTHICAL, oldProfileData.allFishCaughtCount)), fishCaught.get()));
            dummyProfileData.rarityDryStreak.put(Rarity.SPECIAL, fishCaught.get());
            dummyProfileData.fishSizeDryStreak.put(FishSize.BABY, Math.min(fishCaught.get() - (oldProfileData.allFishCaughtCount - oldProfileData.fishSizeDryStreak.getOrDefault(FishSize.BABY, oldProfileData.allFishCaughtCount)), fishCaught.get()));
            dummyProfileData.fishSizeDryStreak.put(FishSize.JUVENILE, Math.min(fishCaught.get() - (oldProfileData.allFishCaughtCount - oldProfileData.fishSizeDryStreak.getOrDefault(FishSize.JUVENILE, oldProfileData.allFishCaughtCount)), fishCaught.get()));
            dummyProfileData.fishSizeDryStreak.put(FishSize.ADULT, Math.min(fishCaught.get() - (oldProfileData.allFishCaughtCount - oldProfileData.fishSizeDryStreak.getOrDefault(FishSize.ADULT, oldProfileData.allFishCaughtCount)), fishCaught.get()));
            dummyProfileData.fishSizeDryStreak.put(FishSize.LARGE, Math.min(fishCaught.get() - (oldProfileData.allFishCaughtCount - oldProfileData.fishSizeDryStreak.getOrDefault(FishSize.LARGE, oldProfileData.allFishCaughtCount)), fishCaught.get()));
            dummyProfileData.fishSizeDryStreak.put(FishSize.GIGANTIC, Math.min(fishCaught.get() - (oldProfileData.allFishCaughtCount - oldProfileData.fishSizeDryStreak.getOrDefault(FishSize.GIGANTIC, oldProfileData.allFishCaughtCount)), fishCaught.get()));
            dummyProfileData.variantDryStreak.put(FishVariant.ALBINO, Math.min(fishCaught.get() - (oldProfileData.allFishCaughtCount - oldProfileData.variantDryStreak.getOrDefault(FishVariant.ALBINO, oldProfileData.allFishCaughtCount)), fishCaught.get()));
            dummyProfileData.variantDryStreak.put(FishVariant.MELANISTIC, Math.min(fishCaught.get() - (oldProfileData.allFishCaughtCount - oldProfileData.variantDryStreak.getOrDefault(FishVariant.MELANISTIC, oldProfileData.allFishCaughtCount)), fishCaught.get()));
            dummyProfileData.variantDryStreak.put(FishVariant.TROPHY, Math.min(fishCaught.get() - (oldProfileData.allFishCaughtCount - oldProfileData.variantDryStreak.getOrDefault(FishVariant.TROPHY, oldProfileData.allFishCaughtCount)), fishCaught.get()));
            dummyProfileData.variantDryStreak.put(FishVariant.FABLED, Math.min(fishCaught.get() - (oldProfileData.allFishCaughtCount - oldProfileData.variantDryStreak.getOrDefault(FishVariant.FABLED, oldProfileData.allFishCaughtCount)), fishCaught.get()));
            dummyProfileData.variantDryStreak.put(FishVariant.SPOOKY, Math.min(fishCaught.get() - (oldProfileData.allFishCaughtCount - oldProfileData.variantDryStreak.getOrDefault(FishVariant.SPOOKY, oldProfileData.allFishCaughtCount)), fishCaught.get()));
            dummyProfileData.variantDryStreak.put(FishVariant.FROZEN, Math.min(fishCaught.get() - (oldProfileData.allFishCaughtCount - oldProfileData.variantDryStreak.getOrDefault(FishVariant.FROZEN, oldProfileData.allFishCaughtCount)), fishCaught.get()));

            this.dummyProfileData = dummyProfileData;
        }
    }

    private IconButtonWidget getButton(MinecraftClient minecraftClient) {
        return IconButtonWidget.builder(Text.literal("Import Stats"), button ->
                        StatsImportHandler.instance().onButtonClick(minecraftClient))
                .position(minecraftClient.getWindow().getScaledWidth() / 2 + 100, minecraftClient.getWindow().getScaledHeight() / 2 - 100)
                .tooltip(Tooltip.of(
                        TextHelper.concat(
                                Text.literal("Import your stats into ").formatted(Formatting.WHITE),
                                Text.literal("FoE").formatted(Formatting.DARK_GREEN, Formatting.BOLD),
                                Text.literal(".\n").formatted(Formatting.WHITE),
                                Text.literal("This will delete your previous all time stats!\n").formatted(Formatting.RED),
                                Text.literal("- The stats are not accurate and could be off by 5.\n- You can change your FoE stats in the config file located in /config/foe/stats.").formatted(Formatting.GRAY, Formatting.ITALIC)
                        )))
                .itemIcon(Items.COMMAND_BLOCK.getDefaultStack())
                .build();
    }


    private void createButton(MinecraftClient minecraftClient) {
        if (minecraftClient.currentScreen != null) {
            Screens.getButtons(minecraftClient.currentScreen).add(getButton(minecraftClient));
        }
    }

    private void saveStats() {
        ProfileDataHandler.instance().profileData.allRarityCounts = dummyProfileData.allRarityCounts;
        ProfileDataHandler.instance().profileData.allFishSizeCounts = dummyProfileData.allFishSizeCounts;
        ProfileDataHandler.instance().profileData.allVariantCounts = dummyProfileData.allVariantCounts;
        ProfileDataHandler.instance().profileData.allFishCaughtCount = dummyProfileData.allFishCaughtCount;
        ProfileDataHandler.instance().profileData.petDryStreak = dummyProfileData.petDryStreak;
        ProfileDataHandler.instance().profileData.shardDryStreak = dummyProfileData.shardDryStreak;
        ProfileDataHandler.instance().profileData.rarityDryStreak = dummyProfileData.rarityDryStreak;
        ProfileDataHandler.instance().profileData.fishSizeDryStreak = dummyProfileData.fishSizeDryStreak;
        ProfileDataHandler.instance().profileData.variantDryStreak = dummyProfileData.variantDryStreak;
        ProfileDataHandler.instance().profileData.isStatsInitialized = true;
    }

    public void onButtonClick(MinecraftClient minecraftClient) {
        this.getData(minecraftClient);
        this.saveStats();
    }

    private int toIntFromString(String value) {
        value = value.trim();
        if(value.contains("K")) {
            return (int) (Float.parseFloat(value.substring(0, value.indexOf("K"))) * 1000f);
        } else {
            return Integer.parseInt(value);
        }
    }

    public boolean isOwnPage(MinecraftClient minecraftClient) {
        AtomicBoolean isMe = new AtomicBoolean(false);
        for (int i = 0; i < Objects.requireNonNull(minecraftClient.player).currentScreenHandler.slots.size(); i++) {
            ItemStack itemStack = minecraftClient.player.currentScreenHandler.getSlot(i).getStack();
            if (minecraftClient.player.currentScreenHandler.getSlot(i).inventory != minecraftClient.player.getInventory() && itemStack.getItem() == Items.PLAYER_HEAD && Objects.requireNonNull(itemStack.get(DataComponentTypes.PROFILE)).id().isPresent()) {

                if(Objects.requireNonNull(itemStack.get(DataComponentTypes.PROFILE)).id().get().equals(minecraftClient.player.getUuid())) {

                    isMe.set(true);
                }
            }
        }
        return isMe.get();
    }

    private int getValue(String line, EnumConstant prefix) {
        return toIntFromString(line.substring(line.indexOf(prefix.tag().getString()) + prefix.tag().getString().length()));
    }

    private int getValue(String line) {
        return toIntFromString(line.substring(line.indexOf(":") + 1));
    }
}
