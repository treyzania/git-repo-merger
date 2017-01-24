package com.treyzania.grm;

import java.io.File;

import com.treyzania.grm.git.Repository;

public class Mapping {
	
	public final Repository source;
	public final File dest;
	
	public Mapping(Repository src, File destRoot) {
		
		this.source = src;
		this.dest = destRoot;
		
	}
	
	public String getSuffix() {
		
		String[] parts = this.dest.getAbsolutePath().split(File.pathSeparator);
		return parts[parts.length - 1];
		
	}
	
}
