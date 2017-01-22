package com.treyzania.grm.git;

import java.io.IOException;

public class GitListFilesCommand extends GitExecBuilder {
	
	public GitListFilesCommand(Repository repo) {
		super(repo);
	}

	@Override
	public Process execute() throws IOException {
		
		ProcessBuilder pb = this.builder();
		pb.command(this.git(), "ls-tree", "-r", "master", "--name-only");
		
		return pb.start();
		
	}
	
}
