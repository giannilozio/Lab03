package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Dictionary {

	private List<RichWord>parole = new LinkedList<>();
	
	private String dictionaryLanguage;
	
	
	public void loadDictionary(String language) throws IOException {			// MI PASSA LA STRING LINGUAGGIO
	
		dictionaryLanguage=language;
		
	FileReader file= new FileReader("rsc/"+language+".txt");
	
	BufferedReader br = new BufferedReader(file);
	String linea;
	
	
	try {
		while (( linea = br .readLine()) != null ) {
			
			String[] parola=linea.split("\n");
			
			String nome = parola[0];
			 boolean isRight= true;
			
			RichWord rw = new RichWord(nome,isRight);
			parole.add(rw);
			
			}
		br.close();
	} catch (IOException e) {
		System.out.println("Errore nella lettura del file");
		}
	}

	
	private boolean isWordPresent (RichWord word) {
		for(RichWord richWord : parole) {
			if(richWord.equals(word))
				return true;				
		}
		return false;
	}
	
	public List<RichWord> spellCheckText(List<String> inputTextList){ // MI PASSA UNA LISTA DI PAROLE
		
		RichWord richWord;
		List<RichWord> outputList = new LinkedList<RichWord>();

		for (String word : inputTextList) {
			richWord = new RichWord(word, true);

			if (isWordPresent(richWord) == false) {				// CONTROLLO SE LA PAROLA PASSATA E' PRESENTE NEL DIZIONARIO
				richWord.setRight(false);
			}

			outputList.add(richWord);
		}

		return outputList;
	}
	
	
	
	public String getDictionaryLanguage() {
		return dictionaryLanguage;
	}
	
	
	
	
	
	
	
}
