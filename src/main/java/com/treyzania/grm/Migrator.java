package com.treyzania.grm;

import java.util.List;

import com.treyzania.grm.git.Repository;

public class Migrator {
	
	private Repository dest;
	private List<Mapping> mappings;
	
	public boolean finished = false;
	
	public Migrator(Repository dest, List<Mapping> maps) {
		
		this.dest = dest;
		this.mappings = maps;
		
	}
	
	public synchronized void execute() {
		
		if (this.finished) return;
		
		// TODO
		
		this.finished = true;
		
	}
	
}
