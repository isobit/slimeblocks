/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nominaltech;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

/**
 *
 * @author joshglendenning
 */
public class SlimeBlocks extends JavaPlugin implements Listener {

	String commandPrefix = "test";

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {
		//TODO: Put something here
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase(commandPrefix)) {
			sender.sendMessage("Hello, world!");
			return true;
		}
		return false;
	}
	
	@EventHandler
	public void onPlayerInteractBlock(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock() instanceof Block) {
				Block block = event.getClickedBlock();
				if (SlimeBlock.isSlimeBlockType(block.getType())){
					SlimeBlock slimeBlock = new SlimeBlock(block);
					slimeBlock.switchTypeByPlayer(player);
				}
			}
		}

	}

}
