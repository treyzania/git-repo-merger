package com.treyzania.grm;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.treyzania.grm.git.Repository;

public final class Mapping {
	
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
	
	public void cleanDestinationDirectory() throws IOException {
		
		this.dest.delete();
		this.dest.mkdirs();
		
	}
	
	public List<File> updateDesintation() throws IOException {
		
		this.cleanDestinationDirectory();
		
		Path rootPath = this.source.root.toPath();
		Path destPath = this.dest.toPath();
		List<File> copied = new ArrayList<>();
		
		for (File f : this.source.getTrackedFiles()) {
			
			// Do some work to figure out the new path.
			Path f2Path = destPath.resolve(rootPath.relativize(f.toPath()));
			File f2 = f2Path.toFile();
			
			// Commons IO does all the heavy lifting.
			System.out.println("copying: " + f.getAbsolutePath() + " -> " + f2.getAbsolutePath());
			FileUtils.copyFile(f, f2, true);
			copied.add(f2);
			
		}
		
		return copied;
		
	}
	
}
