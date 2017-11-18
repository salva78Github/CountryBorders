/**
 * Sample Skeleton for 'Country.fxml' Controller Class
 */

package it.polito.tdp.country;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.country.exception.CountryBordersException;
import it.polito.tdp.country.model.Country;
import it.polito.tdp.country.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class CountryController {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="boxPartenza"
	private ComboBox<Country> boxPartenza; // Value injected by FXMLLoader

	@FXML // fx:id="boxDestinazione"
	private ComboBox<Country> boxDestinazione; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	private Model model;

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(Model model) {
		this.model = model;

		// riempio la prima tendina con l'elenco completo delle nazioni
		// che me lo dà il modello
		try {
			boxPartenza.getItems().addAll(this.model.retrieveCountries());

		} catch (CountryBordersException e) {
			txtResult.setText("Errore nel popolamento della tendina delle nazioni.");
		}
	}

	@FXML
	void handlePercorso(ActionEvent event) {

		Country destinazione = boxDestinazione.getValue();
		List<Country> percorso = model.getPercorso(destinazione);
		txtResult.appendText("\nPercorso per destinazione " + destinazione + ":\n");
		txtResult.appendText(percorso.toString());
		
	}

	@FXML
	void handleRaggiungibili(ActionEvent event) {
		Country partenza = boxPartenza.getValue();
		if (partenza == null) {
			txtResult.setText("Errore: devi selezionare lo stato di partenza.");
		} else {

			try {
				List<Country> raggiungibili = this.model.getRaggiungibili(partenza);
				txtResult.setText(raggiungibili.toString());
				//ripulisco tendina ogni volta
				boxDestinazione.getItems().clear();
				boxDestinazione.getItems().addAll(raggiungibili);
			} catch (CountryBordersException e) {
				txtResult.setText("Errore nel popolamento della tendina delle nazioni raggiungibili.");
			}
		}
	}

	@FXML // This method is called by the FXMLLoader when initialization is
			// complete
	void initialize() {
		assert boxPartenza != null : "fx:id=\"boxPartenza\" was not injected: check your FXML file 'Country.fxml'.";
		assert boxDestinazione != null : "fx:id=\"boxDestinazione\" was not injected: check your FXML file 'Country.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Country.fxml'.";

	}
}
