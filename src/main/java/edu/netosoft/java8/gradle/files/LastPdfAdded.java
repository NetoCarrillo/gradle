/*
 */
package edu.netosoft.java8.gradle.files;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ernesto
 */
public class LastPdfAdded extends PdfWorker{
	private static final Logger LOGGER =
			Logger.getLogger(LastPdfAdded.class.getName());
	
	public void move(){
		Path sourceDir = Paths.get(getDownloadDir());
		File[] pdfs = sourceDir.toFile().listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname){
				return pathname.getName().endsWith("pdf");
			}
		});
		
		if(pdfs == null || pdfs.length == 0){
			LOGGER.log(Level.SEVERE, "No se encontraron archivos PDF");
		}else{
			try{
				Path pdf = pdfs[0].toPath();
				FileTime ctime = Files.readAttributes(pdf, BasicFileAttributes.class).creationTime();
				for(int i = 1; i < pdfs.length; i++){
					Path path = pdfs[i].toPath();
					FileTime xtime = Files.readAttributes(path, BasicFileAttributes.class).creationTime();
					if(xtime.compareTo(ctime) > 0){
						pdf = path;
						ctime = xtime;
					}
				}
				
				LOGGER.info(String.format("El PDF más nuevo %s\n\t%s", ctime.toString(), pdf.toString()));
				
				super.movePdf(pdf);
				
			}catch(IOException ex){
				LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
	}
}
