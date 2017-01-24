package com.treyzania.grm;

import com.treyzania.grm.git.Repository;
import com.treyzania.grm.git.SourceCommit;

public final class Migration {
	
	public final Mapping mapping;
	public final SourceCommit commit;
	
	public Migration(Mapping map, SourceCommit sc) {
		
		this.mapping = map;
		this.commit = sc;
		
	}
	
	public Repository getRepo() {
		return this.commit.repo;
	}
	
}
