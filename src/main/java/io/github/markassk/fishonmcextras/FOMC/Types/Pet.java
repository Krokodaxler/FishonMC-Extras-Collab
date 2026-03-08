package io.github.markassk.fishonmcextras.FOMC.Types;

import io.github.markassk.fishonmcextras.FOMC.Enums.*;
import io.github.markassk.fishonmcextras.util.ItemStackHelper;
import io.github.markassk.fishonmcextras.util.UUIDHelper;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Pet extends FOMCItem {

    public final UUID id;
    public final PetType pet;
    public final Climate climate;
    public final Location location;

    public final int lvl;

    public final float currentXp;
    public final float neededXp;

    public final Stat climateStat;
    public final Stat locationStat;

    public final float percentPetRating;

    public final String discovererName;
    public final UUID discoverer;

    public final String date;

    public final String petItem;

    private Pet(NbtCompound nbtCompound, String type) {
        super(type, Rarity.LOOKUP.valueOfId(nbtCompound.getString("rarity")));
        this.id = UUIDHelper.getUUID(nbtCompound.getIntArray("id"));
        this.pet = PetType.LOOKUP.valueOfId(nbtCompound.getString("pet"));
        this.climate = Climate.LOOKUP.valueOfId(nbtCompound.getString("climate"));
        this.location = Location.LOOKUP.valueOfId(nbtCompound.getString("location"));
        this.lvl = nbtCompound.getInt("level");
        this.currentXp = nbtCompound.getFloat("xp_cur");
        this.neededXp = nbtCompound.getFloat("xp_need");
        this.climateStat = new Stat(nbtCompound, StatType.CLIMATE);
        this.locationStat = new Stat(nbtCompound, StatType.LOCATION);
        this.percentPetRating = getPercentPetRating(this.climateStat.percentLuck, this.climateStat.percentScale,
                this.locationStat.percentLuck, this.locationStat.percentScale);
        this.discovererName = nbtCompound.getString("username");
        this.discoverer = UUIDHelper.getUUID(nbtCompound.getIntArray("uuid"));

        this.date = nbtCompound.getString("date");

        this.petItem = readPetItem(nbtCompound);
    }

    public Pet(
            PetType pet,
            Rarity rarity,
            float cMaxLuck,
            float cMaxScale,
            float cPercentLuck,
            float cPercentScale,
            float lMaxLuck,
            float lMaxScale,
            float lPercentLuck,
            float lPercentScale) {
        super("pet", rarity);
        this.id = UUID.randomUUID();
        this.pet = pet;
        this.climate = Climate.UNKNOWN;
        this.location = Location.UNKNOWN;
        this.lvl = 100;
        this.currentXp = 0;
        this.neededXp = 0;
        this.climateStat = new Stat(
                "",
                cMaxLuck,
                cMaxScale,
                cPercentLuck,
                cPercentScale);
        this.locationStat = new Stat(
                "",
                lMaxLuck,
                lMaxScale,
                lPercentLuck,
                lPercentScale);
        this.percentPetRating = getPercentPetRating(climateStat.percentLuck, climateStat.percentScale,
                locationStat.percentLuck, locationStat.percentScale);
        this.discovererName = "";
        this.discoverer = null;
        this.date = LocalDate.now().toString();

        this.petItem = null;

    }

    public enum StatType {
        LOCATION, CLIMATE
    }

    public static class Stat {
        public final String id;
        public final float currentLuck;
        public final float currentScale;
        public final float maxLuck;
        public final float maxScale;
        public final float percentLuck;
        public final float percentScale;

        private Stat(NbtCompound nbtCompound, StatType base) {
            switch (base) {
                case StatType.CLIMATE -> {
                    this.id = nbtCompound.getString("climate");
                    this.currentLuck = nbtCompound.getList("cbase", NbtElement.COMPOUND_TYPE).getCompound(0)
                            .getInt("cur");
                    this.currentScale = nbtCompound.getList("cbase", NbtElement.COMPOUND_TYPE).getCompound(1)
                            .getInt("cur");
                    this.maxLuck = nbtCompound.getList("cbase", NbtElement.COMPOUND_TYPE).getCompound(0)
                            .getInt("cur_max");
                    this.maxScale = nbtCompound.getList("cbase", NbtElement.COMPOUND_TYPE).getCompound(1)
                            .getInt("cur_max");
                    this.percentLuck = nbtCompound.getList("cbase", NbtElement.COMPOUND_TYPE).getCompound(0)
                            .getFloat("percent_max");
                    this.percentScale = nbtCompound.getList("cbase", NbtElement.COMPOUND_TYPE).getCompound(1)
                            .getFloat("percent_max");
                }
                case StatType.LOCATION -> {
                    this.id = nbtCompound.getString("location");
                    this.currentLuck = nbtCompound.getList("lbase", NbtElement.COMPOUND_TYPE).getCompound(0)
                            .getInt("cur");
                    this.currentScale = nbtCompound.getList("lbase", NbtElement.COMPOUND_TYPE).getCompound(1)
                            .getInt("cur");
                    this.maxLuck = nbtCompound.getList("lbase", NbtElement.COMPOUND_TYPE).getCompound(0)
                            .getInt("cur_max");
                    this.maxScale = nbtCompound.getList("lbase", NbtElement.COMPOUND_TYPE).getCompound(1)
                            .getInt("cur_max");
                    this.percentLuck = nbtCompound.getList("lbase", NbtElement.COMPOUND_TYPE).getCompound(0)
                            .getFloat("percent_max");
                    this.percentScale = nbtCompound.getList("lbase", NbtElement.COMPOUND_TYPE).getCompound(1)
                            .getFloat("percent_max");
                }
                default -> {
                    this.id = Defaults.EMPTY_STRING;
                    this.currentScale = 0f;
                    this.maxLuck = 0f;
                    this.maxScale = 0f;
                    this.percentLuck = 0f;
                    this.percentScale = 0f;
                    this.currentLuck = 0f;
                }
            }
        }

        private Stat(
                String id,
                float maxLuck,
                float maxScale,
                float percentLuck,
                float percentScale) {
            this.id = id;
            this.currentLuck = 0f;
            this.currentScale = 0f;
            this.maxLuck = maxLuck;
            this.maxScale = maxScale;
            this.percentLuck = percentLuck;
            this.percentScale = percentScale;
        }
    }

    private static float getPercentPetRating(float climateLuck, float climateScale, float locationLuck,
            float locationScale) {
        return (climateLuck + climateScale + locationLuck + locationScale) / 4;
    }

    public static Pet getPet(ItemStack itemStack, String type) {
        return new Pet(Objects.requireNonNull(ItemStackHelper.getNbt(itemStack)), type);
    }

    public static Pet getPet(ItemStack itemStack) {
        if (itemStack.get(DataComponentTypes.LORE) != null
                && itemStack.get(DataComponentTypes.CUSTOM_DATA) != null
                && !Objects.requireNonNull(ItemStackHelper.getNbt(itemStack)).getBoolean("shopitem")) {
            NbtCompound nbtCompound = ItemStackHelper.getNbt(itemStack);
            if (nbtCompound != null && nbtCompound.contains("type")
                    && Objects.equals(nbtCompound.getString("type"), Defaults.ItemTypes.PET)) {
                return Pet.getPet(itemStack, Defaults.ItemTypes.PET);
            }
        }
        return null;
    }

    public static String getPetItem(ItemStack itemStack) {
        if (itemStack.get(DataComponentTypes.LORE) != null
                && itemStack.get(DataComponentTypes.CUSTOM_DATA) != null
                && !Objects.requireNonNull(ItemStackHelper.getNbt(itemStack)).getBoolean("item")) {
            NbtCompound nbtCompound = ItemStackHelper.getNbt(itemStack);
            return readPetItem(nbtCompound);
        }
        return null;
    }

    private static String readPetItem(NbtCompound nbtCompound) {
        if (nbtCompound == null) {
            return null;
        }
        NbtList items = nbtCompound.getList("item", NbtElement.COMPOUND_TYPE);
        if (items.isEmpty()) {
            return null;
        }
        NbtCompound item = items.getCompound(0);
        return item
                .getCompound("components")
                .getCompound("minecraft:custom_data")
                .getString("petItem");
    }
}
