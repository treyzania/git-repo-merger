package com.treyzania.grm.git;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GitListFilesCommand extends GitExecBuilder<List<File>> {
	
	public GitListFilesCommand(Repository repo) {
		super(repo);
	}

	@Override
	public List<File> execute() throws IOException {
		
		ProcessBuilder pb = this.builder();
		pb.command(this.git(), "ls-tree", "-r", "master", "--name-only");
		
		Process p = pb.start();
		Scanner lines = new Scanner(new BufferedInputStream(p.getInputStream()));
		List<File> files = new ArrayList<>();
		
		lines.forEachRemaining(s -> {
			
			files.add(new File(s));
			System.err.println(s);
			
		});
		
		lines.close();
		
		return files;
		
	}
	
}
