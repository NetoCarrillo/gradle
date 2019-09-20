package edu.netosoft.java8.gradle.files;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ernesto
 */
public class PDFWatcher extends PdfWorker{
	private static final Logger LOGGER =
			Logger.getLogger(PDFWatcher.class.getName());

	private static final String EVENT_LOG =
			"Event\n\tname:%s\n\ttype:%s\n\tcontext:%s\n";
	
	private int waitSeconds;

	public int getWaitSeconds(){
		return waitSeconds;
	}

	public void setWaitSeconds(int waitSeconds){
		this.waitSeconds = waitSeconds;
	}
		
	public void watchForNewPdf(){
		try{
			Path dir = Paths.get(getDownloadDir());
			WatchService watchService =
					FileSystems.getDefault().newWatchService();
			
			dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
			
			WatchKey watchKey = watchService.poll(waitSeconds, TimeUnit.SECONDS);
			
			if(watchKey == null){
				LOGGER.warning("No se encontraron cambios");
			}else{
				List<WatchEvent<?>> events = watchKey.pollEvents();
				if(events.size() > 1){
					reportMultipleEvents(events);
				}else{
					movePdf(events.get(0));
				}
			}
		}catch(IOException ex){
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
		}catch(InterruptedException ex){
			LOGGER.log(Level.SEVERE, "No se encontraron cambios", ex);
		}
	}
	
	private void movePdf(WatchEvent<?> event) throws IOException{
		WatchEvent.Kind<?> kind = event.kind();
		String filename = event.context().toString();
		System.out.printf("Event\n\tname:%s\n\ttype:%s\n\tcontext:%s\n",
				kind.name(), kind.type(), filename);
		super.movePdf(Paths.get(getDownloadDir(), filename));
	}
	
	private void reportMultipleEvents(List<WatchEvent<?>> events){
		LOGGER.log(Level.SEVERE, "Múltiples eventos encontrados");
		StringBuilder sb = new StringBuilder();
		events.forEach((event) -> {
			WatchEvent.Kind<?> kind = event.kind();
			sb.append(String.format(EVENT_LOG,
					kind.name(), kind.type(), event.context().toString()));
		});
		LOGGER.log(Level.SEVERE, sb.toString());
	}
}
