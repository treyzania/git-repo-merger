package com.treyzania.grm;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.treyzania.grm.git.Repository;

public class GRM {
	
	public static void main(String[] args) {
		
		if (args.length < 3) {
			
			System.out.println("grm: check your usage, you're doing something wrong");
			
		}
		
		Iterator<String> ai = Arrays.asList(args).iterator();
		
		String destPath = ai.next();
		Repository dest = new Repository(new File(destPath));
		
		List<Mapping> maps = new ArrayList<>();
		ai.forEachRemaining(m -> {
			
			String[] parts = m.split(":");
			File src = new File(parts[0]);
			maps.add(new Mapping(new Repository(src), new File(dest.root, parts[1])));
			
		});
		
		Migrator migrator = new Migrator(dest, maps);
		
		
	}
	
}
