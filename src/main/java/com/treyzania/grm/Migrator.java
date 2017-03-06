package com.treyzania.grm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.treyzania.grm.git.GitAddCommand;
import com.treyzania.grm.git.GitCommitTransferredCommitCommand;
import com.treyzania.grm.git.Repository;
import com.treyzania.grm.git.SourceCommit;

public class Migrator {
	
	private Repository dest;
	private List<Mapping> mappings;
	
	private boolean finished = false;
	
	public Migrator(Repository dest, List<Mapping> maps) {
		
		this.dest = dest;
		this.mappings = maps;
		
	}
	
	public synchronized void execute() throws IOException {
		
		if (this.finished) return;
		
		List<Migration> migrations = new ArrayList<>();
		for (Mapping m : this.mappings) {
			
			List<SourceCommit> commits = m.source.getCommits();
			for (SourceCommit c : commits) {
				migrations.add(new Migration(m, c));
			}
			
		}
		
		// Now we order them in the correct way.
		Collections.sort(migrations, (a, b) -> a.commit.compareTo(b.commit));
		
		for (Migration mg : migrations) {
			
			try {
				
				// Checkout the commit from the source, then copy them to the new Repo.
				mg.getRepo().checkout(mg.commit);
				mg.mapping.updateDesintation();
				
				// Stage the changes.
				GitAddCommand addCmd = new GitAddCommand(this.dest);
				addCmd.execute();
				
				// Commit to the new repo.
				GitCommitTransferredCommitCommand commitCmd = new GitCommitTransferredCommitCommand(this.dest, mg.commit, mg.mapping.getSuffix());
				commitCmd.execute();
				
			} catch (IOException e) {
				throw new IOException("Problem performing subprocesses to transfer commit " + mg.commit.hash + " in repo at " + mg.getRepo().root.getAbsolutePath(), e);
			}
			
		}
		
		this.finished = true;
		
	}
	
}
