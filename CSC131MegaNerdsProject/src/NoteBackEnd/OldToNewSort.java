package NoteBackEnd;

import java.util.ArrayList;

/*
 * Class: OldToNewSort
 * Implements the NoteSort interface strategy
 * Sorts the notes in from oldest to newest order
 */

public class OldToNewSort implements NoteSort{
	
	//accepts and returns an ArrayList of Notes that is sorted from oldest to newest
	@Override
	public ArrayList<Note> sort(ArrayList<Note> notes) {
		Note noteTemp;
		//outer loop of bubble sort
		for (int i = 0; i < notes.size() - 1; i++) {
			//inner loop of bubble sort
			for( int j = 0; j < notes.size() - i - 1; j++) {
				//if last modification date of note1 is after than last modification date of note2
				if (notes.get(j).getModification().isAfter(notes.get(j+1).getModification())) {
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
