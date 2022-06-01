package NoteBackEnd;

import java.util.ArrayList;

/*
 * Class: AtoZSort
 * Implements the NoteSort interface strategy
 * Sorts the notes in the AtoZ order
 */

public class AtoZSort implements NoteSort{
	
	//accepts and returns an ArrayList of Notes that is sorted from A to Z
	@Override
	public ArrayList<Note> sort(ArrayList<Note> notes) {
		Note noteTemp;
		// outer loop of bubble sort
		for (int i = 0; i < notes.size() - 1; i++) {
			// inner loop of bubble sort
			for( int j = 0; j < notes.size() - i - 1; j++) {							
				// if note1 comes before note2 alphabetically
				if (notes.get(j).getTitle().compareTo(notes.get(j+1).getTitle()) >= 0){
						noteTemp = notes.get(j);												
						notes.set(j, notes.get(j+1));
						notes.set(j+1, noteTemp);
				}
			}
		}
		// return sorted arraylist
		return notes;
	}
}