package com.davies.questlist.db;

public enum SkillTree {
	FITNESS ("Fitness", "Fitness", "FIT"),
	LEARNING ("Learning", "Learning", "INT"),
	CULTURE ("Culture", "Culture/History", "ART"),
	SOCIAL ("Social", "Social", "CHR"),
	PERSONAL ("Personal", "Personal", "PER");
	
	private final String name;
	private final String description;
	private final String abbrev;
	
	SkillTree(String name, String desc, String abbrev){
		this.name = name;
		this.description = desc;
		this.abbrev = abbrev;
	}

	public String[] getNames(){
		SkillTree[] tree = SkillTree.values();
		String[] names = new String[5];
		
		int i = 0;
		for (SkillTree skill : tree) {
			names[i] = skill.name;
			i++;
		}
		
		return names;
	}
	
	public String[] getDescriptions(){
		SkillTree[] tree = SkillTree.values();
		String[] descriptions = new String[5];
		
		int i = 0;
		for (SkillTree skill : tree) {
			descriptions[i] = skill.description;
			i++;
		}
		
		return descriptions;
	}
	
	public String[] getAbbreviations(){
		SkillTree[] tree = SkillTree.values();
		String[] abbreviations = new String[5];
		
		int i = 0;
		for (SkillTree skill : tree) {
			abbreviations[i] = skill.abbrev;
			i++;
		}
		
		return abbreviations;
	}
}
