package test;

import controller.MeteoClient;
import controller.MeteoTokenClient;

import static view.SimpleConsole.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeteoClientLauncher {

	public static void main(String[] args) throws Exception {
		String request = null;
		do {
			request = read("Inserire:\n"
					+ "1 per richiedere un token\n"
					+ "2 per ottenere i dati correnti di una stazione\n"
					+ "3 per ottenere i dati giornalieri di una stazione\n"
					+ "4 per ottenere i dati specifici di una stazione\n"
					+ "5 per ottenere i dati interpolati in un punto specifico del globo\n"
					+ "exit per uscire\n");
			switch(request) {
			case "1":{
				try {
					List<String> lines = Files.readAllLines(Paths.get("./credenziali"));
					String email = lines.get(0);
					String password = lines.get(1);
				    // Set header
				    Map<String, String> headers = new HashMap<>();
				    MeteoTokenClient multipart = new MeteoTokenClient("https://api.meteonetwork.it/v3/login", "utf-8", headers);
				    // Add form field
				    multipart.addFormField("email", email);
				    multipart.addFormField("password", password);
				    /* Add file
				    multipart.addFilePart("imgFile", new File("/Users/apple/Desktop/test.png"));*/
				    // Print result
				    String response = multipart.finish();
				    log("Richiesta inviata...");
				    System.out.println(response);
				} catch (Exception e) {
				    e.printStackTrace();
				}
				break;
			}
			case "2":{
				String codiceStazione = read("Inserire il codice della stazione, oppure enter per ottenere i dati della stazione di default (5 Archi Velletri)");
				if(codiceStazione.trim().equals("")) {
					codiceStazione = "laz284";
				}
				MeteoClient client = new MeteoClient("https://api.meteonetwork.it/v3/data-realtime/" + codiceStazione);
				client.sendGET();
				break;
			}
			case "3":{
				String codiceStazione = read("Inserire il codice della stazione, oppure enter per ottenere i dati della stazione di default (5 Archi Velletri)");
				if(codiceStazione.trim().equals("")) {
					codiceStazione = "laz284";
				}
				MeteoClient client = new MeteoClient("https://api.meteonetwork.it/v3/data-daily/" + codiceStazione);
				client.sendGET();
				break;
			}
			case "4":{
				String codiceStazione = read("Inserire il codice della stazione, oppure enter per ottenere i dati della stazione di default (5 Archi Velletri)");
				if(codiceStazione.trim().equals("")) {
					codiceStazione = "laz284";
				}
				MeteoClient client = new MeteoClient("https://api.meteonetwork.it/v3/stations/" + codiceStazione);
				client.sendGET();
				break;
			}
			case "5":{
				String latitudine = read("Inserire la latitudine, oppure enter per quella di default (5 Archi Velletri)");
				if(latitudine.trim().equals("")) {
					latitudine = "41.658";
					// casa 41.681750
					// vallauri 41.676270
					// casa papà 42.256680
				}
				String longitudine = read("Inserire la longitudine, oppure enter per quella di default (5 Archi Velletri)");
				if(longitudine.trim().equals("")) {
					longitudine = "12.767";
					// casa 12.779410
					// vallauri 12.776880
					// casa papà 12.570410
				}
				MeteoClient client = new MeteoClient("https://api.meteonetwork.it/v3/interpolated-realtime?lat=" + latitudine + "&lon=" + longitudine );
				client.sendGET();				
				break;
			}
			default:{
				return;
			}
			}
			log("");
		}while(true);
		
	}

}
