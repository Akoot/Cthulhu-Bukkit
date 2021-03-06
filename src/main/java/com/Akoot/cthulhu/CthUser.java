package com.Akoot.cthulhu;

import java.math.BigDecimal;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.Akoot.cthulhu.util.CthFile;
import com.Akoot.cthulhu.utils.ChatUtil;
import com.Akoot.cthulhu.utils.RandomUtil;

public class CthUser
{
	private Player player;
	private Cthulhu plugin;

	public CthUser(Cthulhu plugin, Player player)
	{
		this.player = player;
		this.plugin = plugin;
	}

	public Player getBase()
	{
		return player;
	}
	
	public Boolean hasPermission(String perm)
	{
		if(player.hasPermission(perm)) return true;
		return false;
	}
	
	public Boolean hasPermission(String[] perms)
	{
		for(String perm: perms)
		{
			if(player.hasPermission(perm)) return true;
		}
		return false;
	}
	
	public int getPlaytime()
	{
		return getFile().getInt("playtime");
	}

	public CthFile getFile()
	{
		return plugin.getPlayerDataFile(player);
	}

	public void sendMessage(String msg)
	{
		player.sendMessage(ChatUtil.color(msg));
	}

	public int getLevel(Skill skill)
	{
		return (getFile().has(skill.name().toLowerCase()) ? getFile().getInt(skill.name()) : 0);
	}

	public void setLevel(Skill skill, int level)
	{
		getFile().set(skill.name().toLowerCase(), level);
	}
	
	public void levelUp(Skill skill)
	{
		int level = getLevel(skill) + 1;
		sendMessage("&6You are now a level " + level + " " + skill.name().toLowerCase());
		player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0);
		setLevel(skill, level);
	}
	
	public void levelUp(Skill skill, int chance)
	{
		if(RandomUtil.hasChance(chance - getLevel(skill))) levelUp(skill);
	}
	
	/**
	 *  Random XP Shit 
	 *  
	 */

	public int getTotalExperience()
	{
		int experience = 0;
		int level = player.getLevel();
		if(level >= 0 && level <= 15)
		{
			experience = (int) Math.ceil(Math.pow(level, 2) + (6 * level));
			int requiredExperience = 2 * level + 7;
			double currentExp = Double.parseDouble(Float.toString(player.getExp()));
			experience += Math.ceil(currentExp * requiredExperience);
			return experience;
		}
		else if(level > 15 && level <= 30) {
			experience = (int) Math.ceil((2.5 * Math.pow(level, 2) - (40.5 * level) + 360));
			int requiredExperience = 5 * level - 38;
			double currentExp = Double.parseDouble(Float.toString(player.getExp()));
			experience += Math.ceil(currentExp * requiredExperience);
			return experience;
		}
		else 
		{
			experience = (int) Math.ceil(((4.5 * Math.pow(level, 2) - (162.5 * level) + 2220)));
			int requiredExperience = 9 * level - 158;
			double currentExp = Double.parseDouble(Float.toString(player.getExp()));
			experience += Math.ceil(currentExp * requiredExperience);
			return experience;      
		}
	}

	public void setTotalExperience(int xp)
	{
		if(xp >= 0 && xp < 351)
		{
			int a = 1; int b = 6; int c = -xp;
			int level = (int) (-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
			int xpForLevel = (int) (Math.pow(level, 2) + (6 * level));
			int remainder = xp - xpForLevel;
			int experienceNeeded = (2 * level) + 7;
			float experience = (float) remainder / (float) experienceNeeded;
			experience = round(experience, 2);
			player.setLevel(level);
			player.setExp(experience);
		}
		else if(xp >= 352 && xp < 1507)
		{
			double a = 2.5; double b = -40.5; int c = -xp + 360;
			double dLevel = (-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
			int level = (int) Math.floor(dLevel);
			int xpForLevel = (int) (2.5 * Math.pow(level, 2) - (40.5 * level) + 360);
			int remainder = xp - xpForLevel;
			int experienceNeeded = (5 * level) - 38;
			float experience = (float) remainder / (float) experienceNeeded;
			experience = round(experience, 2);
			player.setLevel(level);
			player.setExp(experience);    
		}
		else
		{
			double a = 4.5; double b = -162.5; int c = -xp + 2220;
			double dLevel = (-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
			int level = (int) Math.floor(dLevel);
			int xpForLevel = (int) (4.5 * Math.pow(level, 2) - (162.5 * level) + 2220);
			int remainder = xp - xpForLevel;
			int experienceNeeded = (9 * level) - 158;
			float experience = (float) remainder / (float) experienceNeeded;
			experience = round(experience, 2);
			player.setLevel(level);
			player.setExp(experience);      
		}
	}

	public int getLevel(int xp)
	{
		if(xp >= 0 && xp < 351)
		{
			int a = 1; int b = 6; int c = -xp;
			return (int) (-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
		}
		else if(xp >= 352 && xp < 1507)
		{
			double a = 2.5; double b = -40.5; int c = -xp + 360;
			double dLevel = (-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
			return  (int) Math.floor(dLevel);
		}
		else
		{
			double a = 4.5; double b = -162.5; int c = -xp + 2220;
			double dLevel = (-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
			return (int) Math.floor(dLevel);
		}
	}

	private float round(float d, int decimalPlace)
	{
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_DOWN);
		return bd.floatValue();
	}
}
