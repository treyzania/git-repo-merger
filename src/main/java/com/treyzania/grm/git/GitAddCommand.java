package com.treyzania.grm.git;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GitAddCommand extends GitExecWrapper<Void> {
	
	private List<File> files;
	
	public GitAddCommand(Repository repo, List<File> files) {
		
		super(repo);
		
		this.files = files;
		
	}
	
	@Override
	public Void execute() throws IOException {
		
		ProcessBuilder pb = this.builder();
		
		List<String> args = new ArrayList<>();
		args.add(this.git());
		args.add("add");
		this.files.forEach(f -> args.add(f.getName()));
		
		pb.command(args);
		
		Process p = pb.start();
		
		try {
			p.waitFor();
		} catch (InterruptedException e) {
			throw new IOException("Interrupted while waiting for subprocess to exit.", e);
		}
		
		return null;
		
	}

}
