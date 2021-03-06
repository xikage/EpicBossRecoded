package me.ThaH3lper.com.Skills.AllSkills;

import java.util.Random;

import me.ThaH3lper.com.EpicBoss;
import me.ThaH3lper.com.Api.BossSkillEvent;
import me.ThaH3lper.com.Boss.Boss;

import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionBoss {
	private EpicBoss eb;
	Random r = new Random();
	
	// - potionboss type:duraction:lvl <HP chance(float)
	// - throw 20:0.2 =100 0.2
	// - throw 18:0.8 >200 1
	
	public PotionBoss(EpicBoss boss)
	{
		eb = boss;
	}
	public void executeBossPotions(String s, Boss b, int index)
	{
		String[] parts = s.split(" ");
		String[] settings = parts[1].split(":");
		float chance = Float.parseFloat(parts[3]);
		if(parts[2].contains(">"))
		{
			String exe = parts[2].replace(">", "");
			if(b.getHealth() > Integer.parseInt(exe))
			{
				SetBossPotion(settings[0], Integer.parseInt(settings[2]), Integer.parseInt(settings[1]), chance, b);
			}
		}
		else if(parts[2].contains("="))
		{
			String exe = parts[2].replace("=", "");
			if(b.getHealth() <= Integer.parseInt(exe))
			{
				SetBossPotion(settings[0], Integer.parseInt(settings[2]), Integer.parseInt(settings[1]), chance, b);
				b.setRemoveSkill(index);
			}
		}
		else if(parts[2].contains("<"))
		{
			String exe = parts[2].replace("<", "");
			if(b.getHealth() < Integer.parseInt(exe))
			{
				SetBossPotion(settings[0], Integer.parseInt(settings[2]), Integer.parseInt(settings[1]), chance, b);
			}
		}
		else if(parts[2].contains("/"))
		{
			String exe = parts[2].replace("/", "");
			String[] value = exe.split("-");
			if(b.getHealth() < Integer.parseInt(value[0]) && b.getHealth() > Integer.parseInt(value[1]))
			{
				SetBossPotion(settings[0], Integer.parseInt(settings[2]), Integer.parseInt(settings[1]), chance, b);
			}
		}
	}
	public void SetBossPotion(String potion, int lvl, int duration, float chance, Boss b)
	{
		if(r.nextFloat() <= chance)
		{
			eb.skillhandler.event = new BossSkillEvent(eb, b, "potionboss", false);
			Bukkit.getServer().getPluginManager().callEvent(eb.skillhandler.event);
			PotionEffect pe = new PotionEffect(PotionEffectType.getByName(potion), duration * 20, lvl-1);
			if(pe != null)
			{
				b.getLivingEntity().addPotionEffect(pe);
			}
		}
	}

}
