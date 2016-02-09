package com.Akoot.cthulhu.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class ChatUtil 
{
	public static String[] colors = {"a","b","c","d","e","3","4","5","6","9","2"};
	public static String[] rainbowseq = {"a","3","9","5","d","c","6","e"};
	private static Random random = new Random();

	public static String color(String s)
	{
		s = s.replaceAll("&h", "&" + colors[random.nextInt(colors.length - 1)]);
		if(s.contains("&x"))
		{
			s = s.substring(0, s.indexOf("&x")) + rainbow(s.substring(s.indexOf("&x") + 2));
		}
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public enum Tags
	{
		tip,
		link,
		url,
		suggestion,
		suggest,
		help,
	}
	
	public static String itemName(ItemStack item)
	{
		return item.getType().name().toLowerCase().replace("_", " ");
	}
	public static String getTime(int time)
	{
		int minutes = time % 60;
		String message = "";
		List<String> args = new ArrayList<String>();

		if(time >= 525600)
		{
			int hours = time / 60;
			int days = hours / 24;
			int weeks = days / 7;
			int months = weeks / 4;
			int years = months / 12;
			args.add(years + toPlural(years, " year"));
		}
		if(time >= 43800)
		{
			int hours = time / 60;
			int days = hours / 24;
			int weeks = days / 7;
			int months = weeks / 4;
			args.add(months + toPlural(months, " month"));
		}
		if(time >= 10080)
		{
			int hours = time / 60;
			int days = hours / 24;
			int weeks = days / 7;
			args.add(weeks + toPlural(weeks, " week"));
		}
		if(time >= 1440)
		{
			int hours = time / 60;
			int days = hours / 24;
			args.add(days + toPlural(days, " day"));
		}
		if (time >= 60)
		{
			int hours = time / 60;
			args.add(hours + toPlural(hours, " hour"));
		}
		if (time < 60)
		{
			args.add(minutes + toPlural(minutes, " minute"));
		}
		args.add(minutes + toPlural(minutes, " minute"));
		return message;
	}
	public static String rainbow(String msg)
	{
		String rainbow = "";
		int i = random.nextInt(rainbowseq.length - 1);
		for(char c: msg.toCharArray())
		{
			if(i >= rainbowseq.length)
			{
				i = 0;
			}
			String ch = String.valueOf(c);
			if(!ch.equals("\\s"))
			{
				ch = "&" + rainbowseq[i] + ch;
			}
			rainbow += ch;
			i++;
		}
		return rainbow;
	}

	public static String toString(Object[] args)
	{
		String msg = "";
		for(Object s: args)
		{
			msg += s + " ";
		}
		msg = msg.trim();
		return msg;
	}

	public static String toPlural(int amt, String msg)
	{
		return msg + (amt == 1 ? "" : "s");
	}

	public static String toString(List<String> args)
	{
		String[] args2 = new String[args.size()];
		for(int i = 0; i > args.size(); i++)
		{
			args2[i] = args.get(i);
		}
		return toString(args2);
	}
}
