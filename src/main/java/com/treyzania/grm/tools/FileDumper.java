package com.treyzania.grm.tools;

import java.io.File;
import java.io.IOException;

import com.treyzania.grm.git.Repository;

public class FileDumper {

	public static void main(String[] args) {
		
		Repository repo = new Repository(new File(args[0]));
		
		try {
			repo.getRefreshedCommits();
		} catch (IOException e) {
			
			e.printStackTrace();
			return;
			
		}
		
		repo.assumeCheckedOutLast();
		
		try {
			repo.getTrackedFiles().forEach(f -> System.out.println(f.getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
