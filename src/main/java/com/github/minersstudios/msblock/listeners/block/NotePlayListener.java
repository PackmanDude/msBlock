package com.github.minersstudios.msblock.listeners.block;

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.NotePlayEvent;
import org.jetbrains.annotations.NotNull;

public class NotePlayListener implements Listener {

	@EventHandler
	public void onNotePlay(@NotNull NotePlayEvent event) {
		event.setCancelled(!(event.getInstrument() == Instrument.BIT && event.getNote().equals(new Note(0))));
	}
}
