package controller;

import static view.SimpleConsole.log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class MeteoClient {
	
	private HttpURLConnection httpConn;
	private String requestURL;
	
	public MeteoClient(String requestURL) throws Exception {
		this.requestURL = requestURL;
        //"Stazione meteorologica di 5 ARCHI - Velletri (RM)"
        //"Velletri 5 ARCHI"
		log("Inizializzazione del Client completata");
	}
	
	public void sendGET() throws Exception {
		URL url = new URI(requestURL).toURL();
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoInput(true);
        httpConn.setRequestMethod("GET");	
        httpConn.setRequestProperty("Host", "api.meteonetwork.it");
        httpConn.setRequestProperty("Authorization", "Bearer 19358|FZ0zIuEsoGbcVbxiZuMH76nDmY8aBCLYfJ0nmFyV");
        
		int responseCode = httpConn.getResponseCode();
		
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			System.out.println(response.toString());
		} else {
			System.out.println("GET request did not work.");
		}

	}
}
