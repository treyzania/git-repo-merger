package com.treyzania.grm.git;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GitListFilesCommand extends GitExecWrapper<List<File>> {
	
	private SourceCommit commit;
	
	public GitListFilesCommand(Repository repo, SourceCommit commit) {
		
		super(repo);
		
		this.commit = commit;
		
	}

	@Override
	public List<File> execute() throws IOException {
		
		ProcessBuilder pb = this.builder();
		pb.command(this.git(), "ls-tree", "-r", this.commit.hash, "--name-only");
		
		Process p = pb.start();
		Scanner lines = new Scanner(new BufferedInputStream(p.getInputStream()));
		List<File> files = new ArrayList<>();
		
		lines.forEachRemaining(s -> {
			files.add(new File(this.repo.root, s));
		});
		
		lines.close();
		
		// For good measure.
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			// ???
		}
		
		return files;
		
	}
	
}
