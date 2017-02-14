package com.treyzania.grm.git;

import java.io.IOException;

public class GitAddCommand extends GitExecWrapper<Void> {
	
	public GitAddCommand(Repository repo) {
		super(repo);
	}
	
	@Override
	public Void execute() throws IOException {
		
		ProcessBuilder pb = this.builder();
		pb.command(this.git(), "add", ".");
		
		Process p = pb.start();
		
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			throw new IOException("Interrupted while waiting for subprocess to exit.", e);
		}
		
		return null;
		
	}

}
