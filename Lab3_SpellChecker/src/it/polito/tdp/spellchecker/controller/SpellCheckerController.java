package it.polito.tdp.spellchecker.controller;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SpellCheckerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
   // private ComboBox<?> languageSwitch;
    private ComboBox<String> languageSwitch; // PER USARE IL MENU A TENDINA?

    @FXML
    private TextArea textInput;

    @FXML
    private Button spellBtn;

    @FXML
    private TextArea textOutput;

    @FXML
    private Label errCnt;

    @FXML
    private Button Reset;

    @FXML
    private Label miTimer;
    
    List<Dictionary> dictionaries;												// CREO UNA LIST DIZIONARI
	Dictionary dictionary;														// CREO UNA VAR DIZIONARIO
	static String[] LANGUAGE_AVAIABLE = {"English", "Italian"};					// CREO LA SCELTA DEL LINGUAGGIO

    @FXML
    void doChangeLanguage(ActionEvent event) {
    	
    	String language = (String) languageSwitch.getValue();			// PRENDO IL VALORE DEL MENU' A TENDINA
    	
    	for (Dictionary d : dictionaries) {								
    		if (d.getDictionaryLanguage().equals(language))			// CONTROLLO QUALE DIZIONARIO IMPOSTARE
    			dictionary = d;
    	}
    	
    }

    @FXML
    void doClear(ActionEvent event) {

    	textInput.clear();
    	textOutput.clear();
    	errCnt.setText("The text contains 0 errors");
    	miTimer.setText("Spell check completed in 0 seconds");
    	
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	long start = System.nanoTime();								// VARIABILE PER IL TEMPO D ESECUZIONE
    	String[] inputVector = textInput.getText().replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "").toLowerCase().split(" ");
    	List<String> inputList = new LinkedList<String>();			// LIST DELLE PAROLE IN INPUT
    	List<RichWord> wordResult;
    	int contaParoleErrate = 0;
    	String output = "";

    	for (int i = 0; i < inputVector.length; i++)			// AGGIUNGO LE PAROLE IN INPUT ALLA LIST
    		inputList.add(inputVector[i]);
    	wordResult = dictionary.spellCheckText(inputList);		// MI DA LA LIST SEGNANDO LE PAROLE SBAGLIATE

    	for (RichWord word : wordResult) {						// STAMPA LE PAROLE SBAGLIATE
    		if (word.isRight() == false) {
    			contaParoleErrate++;
    			output += (word.getWord() + "\n");
    		}

    	}
    	textOutput.appendText(output + "\n");
    	errCnt.setText("The text contains "+ contaParoleErrate + " errors");

    	miTimer.setText("Spell check completed in " + ((System.nanoTime() - start)/ 1_000_000_000.0) + " seconds");
    

    }

    @FXML
    void initialize() throws IOException {
        assert languageSwitch != null : "fx:id=\"languageSwitch\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert textInput != null : "fx:id=\"textInput\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert spellBtn != null : "fx:id=\"spellBtn\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert textOutput != null : "fx:id=\"textOutput\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert errCnt != null : "fx:id=\"errCnt\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert Reset != null : "fx:id=\"Reset\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert miTimer != null : "fx:id=\"miTimer\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        
        
        dictionaries = new LinkedList<Dictionary>();

    	for (int i = 0; i < LANGUAGE_AVAIABLE.length; i++) {
    		Dictionary d = new Dictionary();
    		d.loadDictionary(LANGUAGE_AVAIABLE[i]);						// AGGIUNGO LE LINGUE DAL FILE AL DIZIONARIO 
    		languageSwitch.getItems().add(LANGUAGE_AVAIABLE[i]);		// AGGIUNGO LE LINGUE NEL MENU A TENDINA
    		dictionaries.add(d);
    	}

    	languageSwitch.getSelectionModel().selectFirst();
    	dictionary = dictionaries.get(0);
    

    }
}
