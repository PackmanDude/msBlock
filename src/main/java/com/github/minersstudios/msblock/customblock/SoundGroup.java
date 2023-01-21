package com.github.minersstudios.msblock.customblock;

import com.github.minersstudios.msblock.Main;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public class SoundGroup {
	private @Nullable String placeSound;
	private @NotNull SoundCategory placeSoundCategory;
	private float placeSoundPitch;
	private float placeSoundVolume;
	private @Nullable String breakSound;
	private @NotNull SoundCategory breakSoundCategory;
	private float breakSoundPitch;
	private float breakSoundVolume;
	private @Nullable String hitSound;
	private @NotNull SoundCategory hitSoundCategory;
	private float hitSoundPitch;
	private float hitSoundVolume;
	private @Nullable String stepSound;
	private @NotNull SoundCategory stepSoundCategory;
	private float stepSoundPitch;
	private float stepSoundVolume;

	public SoundGroup(
			@Nullable String placeSound,
			@NotNull SoundCategory placeSoundCategory,
			float placeSoundPitch,
			float placeSoundVolume,
			@Nullable String breakSound,
			@NotNull SoundCategory breakSoundCategory,
			float breakSoundPitch,
			float breakSoundVolume,
			@Nullable String hitSound,
			@NotNull SoundCategory hitSoundCategory,
			float hitSoundPitch,
			float hitSoundVolume,
			@Nullable String stepSound,
			@NotNull SoundCategory stepSoundCategory,
			float stepSoundPitch,
			float stepSoundVolume
	) {
		this.placeSound = placeSound;
		this.placeSoundCategory = placeSoundCategory;
		this.placeSoundPitch = placeSoundPitch;
		this.placeSoundVolume = placeSoundVolume;
		this.breakSound = breakSound;
		this.breakSoundCategory = breakSoundCategory;
		this.breakSoundPitch = breakSoundPitch;
		this.breakSoundVolume = breakSoundVolume;
		this.hitSound = hitSound;
		this.hitSoundCategory = hitSoundCategory;
		this.hitSoundPitch = hitSoundPitch;
		this.hitSoundVolume = hitSoundVolume;
		this.stepSound = stepSound;
		this.stepSoundCategory = stepSoundCategory;
		this.stepSoundPitch = stepSoundPitch;
		this.stepSoundVolume = stepSoundVolume;
	}

	public void setPlaceSound(@Nullable String placeSound) {
		this.placeSound = placeSound;
	}

	public @Nullable String getPlaceSound() {
		return this.placeSound;
	}

	public void setPlaceSoundCategory(@NotNull SoundCategory soundCategory) {
		this.placeSoundCategory = soundCategory;
	}

	public @NotNull SoundCategory getPlaceSoundCategory() {
		return this.placeSoundCategory;
	}

	public void setPlaceSoundPitch(float placeSoundPitch) {
		this.placeSoundPitch = placeSoundPitch;
	}

	public float getPlaceSoundPitch() {
		return this.placeSoundPitch;
	}

	public void setPlaceSoundVolume(float placeSoundVolume) {
		this.placeSoundVolume = placeSoundVolume;
	}

	public float getPlaceSoundVolume() {
		return this.placeSoundVolume;
	}

	public void setBreakSound(@Nullable String breakSound) {
		this.breakSound = breakSound;
	}

	public @Nullable String getBreakSound() {
		return this.breakSound;
	}

	public void setBreakSoundCategory(@NotNull SoundCategory soundCategory) {
		this.breakSoundCategory = soundCategory;
	}

	public @NotNull SoundCategory getBreakSoundCategory() {
		return this.breakSoundCategory;
	}

	public void setBreakSoundPitch(float breakSoundPitch) {
		this.breakSoundPitch = breakSoundPitch;
	}

	public float getBreakSoundPitch() {
		return this.breakSoundPitch;
	}

	public void setBreakSoundVolume(float breakSoundVolume) {
		this.breakSoundVolume = breakSoundVolume;
	}

	public float getBreakSoundVolume() {
		return this.breakSoundVolume;
	}

	public void setHitSound(@Nullable String hitSound) {
		this.hitSound = hitSound;
	}

	public @Nullable String getHitSound() {
		return this.hitSound;
	}

	public void setHitSoundCategory(@NotNull SoundCategory soundCategory) {
		this.hitSoundCategory = soundCategory;
	}

	public @NotNull SoundCategory getHitSoundCategory() {
		return this.hitSoundCategory;
	}

	public float getHitSoundPitch() {
		return this.hitSoundPitch;
	}

	public void setHitSoundPitch(float hitSoundPitch) {
		this.hitSoundPitch = hitSoundPitch;
	}

	public float getHitSoundVolume() {
		return this.hitSoundVolume;
	}

	public void setHitSoundVolume(float hitSoundVolume) {
		this.hitSoundVolume = hitSoundVolume;
	}

	public void setStepSound(@Nullable String stepSound) {
		this.stepSound = stepSound;
	}

	public @Nullable String getStepSound() {
		return this.stepSound;
	}

	public void setStepSoundCategory(@NotNull SoundCategory soundCategory) {
		this.stepSoundCategory = soundCategory;
	}

	public @NotNull SoundCategory getStepSoundCategory() {
		return this.stepSoundCategory;
	}

	public void setStepSoundPitch(float stepSoundPitch) {
		this.stepSoundPitch = stepSoundPitch;
	}

	public float getStepSoundPitch() {
		return this.stepSoundPitch;
	}

	public void setStepSoundVolume(float stepSoundVolume) {
		this.stepSoundVolume = stepSoundVolume;
	}

	public float getStepSoundVolume() {
		return this.stepSoundVolume;
	}

	public void playPlaceSound(@NotNull Location location) {
		if (this.placeSound == null) return;
		if (this.placeSound.equalsIgnoreCase("block.wood.place")) {
			location.getWorld().playSound(location, Main.getConfigCache().woodSoundPlace, this.placeSoundCategory, this.placeSoundVolume, this.placeSoundPitch);
		} else {
			location.getWorld().playSound(location, this.placeSound, this.placeSoundCategory, this.placeSoundVolume, this.placeSoundPitch);
		}
	}

	public void playBreakSound(@NotNull Location location) {
		if (this.breakSound == null) return;
		if (this.breakSound.equalsIgnoreCase("block.wood.break")) {
			location.getWorld().playSound(location,  Main.getConfigCache().woodSoundBreak, this.breakSoundCategory, this.breakSoundVolume, this.breakSoundPitch);
		} else {
			location.getWorld().playSound(location, this.breakSound, this.breakSoundCategory, this.breakSoundVolume, this.breakSoundPitch);
		}
	}

	public void playHitSound(@NotNull Location location) {
		if (this.hitSound == null) return;
		if (this.hitSound.equalsIgnoreCase("block.wood.hit")) {
			location.getWorld().playSound(location, Main.getConfigCache().woodSoundHit, this.hitSoundCategory, this.hitSoundVolume, this.hitSoundPitch);
		} else {
			location.getWorld().playSound(location, this.hitSound, this.hitSoundCategory, this.hitSoundVolume, this.hitSoundPitch);
		}
	}

	public void playStepSound(@NotNull Location location) {
		if (this.stepSound == null) return;
		if (this.stepSound.equalsIgnoreCase("block.wood.step")) {
			location.getWorld().playSound(location, Main.getConfigCache().woodSoundStep, this.stepSoundCategory, this.stepSoundVolume, this.stepSoundPitch);
		} else {
			location.getWorld().playSound(location, this.stepSound, this.stepSoundCategory, this.stepSoundVolume, this.stepSoundPitch);
		}
	}
}
