/*
 */
package edu.netosoft.java8.gradle.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.util.logging.Logger;

/**
 *
 * @author Ernesto
 */
public abstract class PdfWorker{
	private static final Logger LOGGER =
			Logger.getLogger(PdfWorker.class.getName());
	
	private String downloadDir;
	private String repoDir;
	
	public String getDownloadDir(){
		return downloadDir;
	}

	public void setDownloadDir(String downloadDir){
		this.downloadDir = downloadDir;
	}

	public void setRepoDir(String repoDir){
		this.repoDir = repoDir;
	}
	
	protected final void movePdf(Path file) throws IOException{
		Path destiny = Paths.get(repoDir, String.format("%d.pdf", System.currentTimeMillis()));
		Files.move(file, destiny);

		LOGGER.info(String.format("Se movio de %s a %s", file.toString(), destiny.toString()));
	}
}
