/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nominaltech;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author joshglendenning
 */
public class SlimeBlock {
	Block block;
	Material switchType;
	boolean containsSlime;
	byte extraData = -1;
	
	SlimeBlock(Block block) {
		this.block = block;
		Material type = block.getType();
		if (!isSlimeBlockType(type)) {
			throw new IllegalArgumentException("Block is not a valid type");
		}
		switch (type) {
			case COBBLESTONE:
				this.switchType = Material.MOSSY_COBBLESTONE;
				this.containsSlime = false;
				break;
			case MOSSY_COBBLESTONE:
				this.switchType = Material.COBBLESTONE;
				this.containsSlime = true;
				break;
			case SMOOTH_BRICK:
				if (block.getData() == 0) {
					this.switchType = Material.SMOOTH_BRICK;
					this.extraData = 1;
					this.containsSlime = false;
				}
				if (block.getData() == 1) {
					this.switchType = Material.SMOOTH_BRICK;
					this.extraData = 0;
					this.containsSlime = true;
				}
				break;
			case PISTON_BASE:
				this.switchType = Material.PISTON_STICKY_BASE;
				this.containsSlime = false;
				break;
			case PISTON_STICKY_BASE:
				this.switchType = Material.PISTON_BASE;
				this.containsSlime = true;
				break;
				
		}
	}
	
	public static boolean isSlimeBlockType(Material type) {
		List<Material> allowedBlocks = Arrays.asList(new Material[]{
			Material.COBBLESTONE,
			Material.SMOOTH_BRICK,
			Material.MOSSY_COBBLESTONE,
			Material.PISTON_BASE,
			Material.PISTON_STICKY_BASE
		});
		return allowedBlocks.contains(type);
	}
	
	public void switchTypeByPlayer(Player player) {
		if (!containsSlime) {
			PlayerInventory inventory = player.getInventory();
			if (player.getItemInHand().getType() != Material.SLIME_BALL) return;
			int amountInHand = player.getItemInHand().getAmount();
			if (amountInHand == 1) player.getInventory().removeItem(player.getItemInHand());
			else player.getItemInHand().setAmount(amountInHand-1);
		}
		if (containsSlime) {
			ItemStack drop = new ItemStack(Material.SLIME_BALL);
			drop.setAmount(1);
			player.getWorld().dropItem(block.getLocation(), drop);
		}
		player.getWorld().playSound(player.getLocation(), Sound.SLIME_ATTACK, 1F, 1F);
		block.setType(switchType);
		if (extraData != -1) {
			block.setData(extraData);
		}
	}
	
}
