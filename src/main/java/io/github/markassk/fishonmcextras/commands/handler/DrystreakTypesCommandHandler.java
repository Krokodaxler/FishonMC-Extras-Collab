package io.github.markassk.fishonmcextras.commands.handler;

import io.github.markassk.fishonmcextras.FOMC.Enums.FishSize;
import io.github.markassk.fishonmcextras.FOMC.Enums.FishVariant;
import io.github.markassk.fishonmcextras.FOMC.Enums.RareCatch;
import io.github.markassk.fishonmcextras.FOMC.Enums.Rarity;
import io.github.markassk.fishonmcextras.util.TextHelper;
import io.github.markassk.fishonmcextras.handler.ProfileDataHandler;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.text.Text;

public class DrystreakTypesCommandHandler {

    private DrystreakTypesCommandHandler() {
    }

    public static DrystreakTypesCommandHandler getDrystreakTypesCommandHandler() {
        return new DrystreakTypesCommandHandler();
    }

    private static ProfileDataHandler.ProfileData profileData = ProfileDataHandler.instance().profileData;
    private static int allFishCaught = profileData.allFishCaughtCount;

    public static List<Text> getDryStreakBreakdown(String type) {
        List<Text> breakdownList = new ArrayList<>(List.of(TextHelper.concat(Text.literal("Drystreak Tracker:\n"))));
        switch (type.toLowerCase()) {
            case "all":
                breakdownList.addAll(getSizeBreakdown("sizes"));
                breakdownList.add(Text.literal("\n"));
                breakdownList.addAll(getRarityBreakdown("rarities"));
                breakdownList.add(Text.literal("\n"));
                breakdownList.addAll(getVariantBreakdown("variants"));
                breakdownList.add(Text.literal("\n"));
                breakdownList.add(Text.literal("─ ʀᴀʀᴇ ᴅʀᴏᴘs: \n"));
                breakdownList.addAll(getPetAndRareDropBreakdown("pet"));
                breakdownList.add(Text.literal("\n"));
                breakdownList.addAll(getPetAndRareDropBreakdown("shard"));
                breakdownList.add(Text.literal("\n"));
                breakdownList.addAll(getPetAndRareDropBreakdown("infusioncapsule"));
                breakdownList.add(Text.literal("\n"));
                breakdownList.addAll(getPetAndRareDropBreakdown("lightningbottle"));
                break;
            case "sizes", "baby", "juvenile", "adult", "large", "gigantic":
                breakdownList.addAll(getSizeBreakdown(type));
                break;
            case "rarities", "common", "rare", "epic", "legendary", "mythical":
                breakdownList.addAll(getRarityBreakdown(type));
                break;
            case "variants", "albino", "melanistic", "trophy", "fabled":
                breakdownList.addAll(getVariantBreakdown(type));
                break;
            case "pet", "shard", "infusioncapsule", "lightningbottle":
                breakdownList.addAll(getPetAndRareDropBreakdown(type));
                break;
            default:
                breakdownList.add(Text.literal("Invalid drystreak type specified."));
                break;
        }
        return breakdownList;
    }

    private static List<Text> getRarityBreakdown(String rarity) {
        switch (rarity.toLowerCase()) {
            case "rarities":
                return 
                List.of(TextHelper.concat(
                    Text.literal("─ ʀᴀʀɪᴛɪᴇs: \n"),
                    Text.literal("└ "), Rarity.COMMON.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.rarityDryStreak.getOrDefault(Rarity.COMMON, 0))), Text.literal("\n"),
                    Text.literal("└ "), Rarity.RARE.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.rarityDryStreak.getOrDefault(Rarity.RARE, 0))), Text.literal("\n"),
                    Text.literal("└ "), Rarity.EPIC.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.rarityDryStreak.getOrDefault(Rarity.EPIC, 0))), Text.literal("\n"),
                    Text.literal("└ "), Rarity.LEGENDARY.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.rarityDryStreak.getOrDefault(Rarity.LEGENDARY, 0))), Text.literal("\n"),
                    Text.literal("└ "), Rarity.MYTHICAL.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.rarityDryStreak.getOrDefault(Rarity.MYTHICAL, 0)))));
            default:
                Rarity rarityConstant = Rarity.LOOKUP.valueOfId(rarity.toLowerCase());
                return List.of(TextHelper.concat(
                    Text.literal("└ "), rarityConstant.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.rarityDryStreak.getOrDefault(rarityConstant, 0)))));
        }
    }

    private static List<Text> getSizeBreakdown(String size) {
        switch (size.toLowerCase()) {
            case "sizes":
                return 
                List.of(TextHelper.concat(
                    Text.literal("─ sɪᴢᴇs: \n"),
                    Text.literal("└ "), FishSize.BABY.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.fishSizeDryStreak.getOrDefault(FishSize.BABY, 0))), Text.literal("\n"),
                    Text.literal("└ "), FishSize.JUVENILE.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.fishSizeDryStreak.getOrDefault(FishSize.JUVENILE, 0))), Text.literal("\n"),
                    Text.literal("└ "), FishSize.ADULT.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.fishSizeDryStreak.getOrDefault(FishSize.ADULT, 0))), Text.literal("\n"),
                    Text.literal("└ "), FishSize.LARGE.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.fishSizeDryStreak.getOrDefault(FishSize.LARGE, 0))), Text.literal("\n"),
                    Text.literal("└ "), FishSize.GIGANTIC.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.fishSizeDryStreak.getOrDefault(FishSize.GIGANTIC, 0)))));
            default:
                FishSize sizeConstant = FishSize.LOOKUP.valueOfId(size.toLowerCase());
                return List.of(TextHelper.concat(
                    Text.literal("└ "), sizeConstant.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.fishSizeDryStreak.getOrDefault(sizeConstant, 0)))));
        }
    }

    private static List<Text> getVariantBreakdown(String variant) {
        switch (variant.toLowerCase()) {
            case "variants":
                return 
                List.of(TextHelper.concat(
                    Text.literal("─ ᴠᴀʀɪᴀɴᴛs: \n"),
                    Text.literal("└ "), FishVariant.ALBINO.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.variantDryStreak.getOrDefault(FishVariant.ALBINO, 0))), Text.literal("\n"),
                    Text.literal("└ "), FishVariant.MELANISTIC.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.variantDryStreak.getOrDefault(FishVariant.MELANISTIC, 0))), Text.literal("\n"),
                    Text.literal("└ "), FishVariant.TROPHY.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.variantDryStreak.getOrDefault(FishVariant.TROPHY, 0))), Text.literal("\n"),
                    Text.literal("└ "), FishVariant.FABLED.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.variantDryStreak.getOrDefault(FishVariant.FABLED, 0)))));
            default:
                FishVariant variantConstant = FishVariant.LOOKUP.valueOfId(variant.toLowerCase());
                return List.of(TextHelper.concat(
                    Text.literal("└ "), variantConstant.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.variantDryStreak.getOrDefault(variantConstant, 0)))));
        }
    }

    private static List<Text> getPetAndRareDropBreakdown(String type) {
        switch (type.toLowerCase()) {
            case "pet":
                return 
                List.of(TextHelper.concat(
                    Text.literal("└ "), RareCatch.PET.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.petDryStreak))));
            case "shard":
                return 
                List.of(TextHelper.concat(
                    Text.literal("└ "), RareCatch.SHARD.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.shardDryStreak))));
            case "infusioncapsule":
                return 
                List.of(TextHelper.concat(
                    Text.literal("└ "), RareCatch.INFUSION_CAPSULE.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.infusionCapsuleDryStreak))));
            case "lightningbottle":
                return 
                List.of(TextHelper.concat(
                    Text.literal("└ "), RareCatch.LIGHTNING_BOTTLE.TAG, Text.literal(" "), Text.literal(TextHelper.fmt(allFishCaught - profileData.lightningBottleDryStreak))));
            default:
                return List.of(Text.literal("Invalid drystreak type specified."));
        }
    }
}