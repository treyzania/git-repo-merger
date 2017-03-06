package com.treyzania.grm.git;

import java.io.IOException;

public class GitInitCommand extends GitExecWrapper<Void> {

	public GitInitCommand(Repository repo) {
		super(repo);
	}

	@Override
	public Void execute() throws IOException {
		
		Repository r = this.repo;
		if (!r.root.exists()) {
			r.root.mkdirs();
		}
		
		if (!r.root.isDirectory()) {
			throw new IOException("Destination path is not a directory!");
		}
		
		ProcessBuilder pb = this.builder();
		pb.command(this.git(), "init");
		
		Process p = pb.start();
		
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			System.out.println("Something weihd happened.");
		}
		
		return null;
		
	}

}
