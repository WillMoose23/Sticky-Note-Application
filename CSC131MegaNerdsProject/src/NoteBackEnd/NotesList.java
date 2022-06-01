package NoteBackEnd;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
import java.io.*;

/* Class: NotesList
 * Contains the list of Note objects for both the active notes and recycle bin
 * Factory for Note Object
 */

public class NotesList implements Serializable{
	
	//two ArrayList, notes contains the list of active notes, recycleBin contains the list of recycled notes
	private ArrayList<Note> notes;
	private ArrayList<Note> recycleBin;
	
	//the way that the notes are ordered in the list
	private NoteSort sortStrategy;
	
	//path to file containing NotesList object data
	private String path;
	
	//default constructor, initializing two lists and sorting strategy
	public NotesList() {
		notes = new ArrayList<Note>();
		recycleBin = new ArrayList<Note>();
		sortStrategy = new NewToOldSort();
		path = "data.dat";
	}
	
	//accepts an int and return a reference to a Note object at the corresponding index in notes list
	public Note get(int index) {
		return notes.get(index);
	}
	
	//accepts an int and returns a reference to a Note object at the corresponding index in the recycleBin list
	public Note recycleBinGet(int index) {
		return recycleBin.get(index);
	}
	
	//no parameters, return Boolean, imports necessary object data to file from variable path
	public Boolean importNote() {
		try {
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			int noteCount = (int)objectIn.readObject();
			for (int i = 0; i < noteCount; i++) {
				String inTitle = (String)objectIn.readObject();
				String inText = (String)objectIn.readObject();
				LocalDateTime inDate = (LocalDateTime)objectIn.readObject();
				Boolean inCompletion = (Boolean)objectIn.readObject();
				LocalDateTime inModification = (LocalDateTime)objectIn.readObject();
				LocalDateTime inCreation = (LocalDateTime)objectIn.readObject();
				notes.add(new Note(inTitle, inText, inDate, inModification, inCreation, inCompletion));
			}
			int recycleCount = (int)objectIn.readObject();
			for (int i = 0; i < recycleCount; i++) {
				String inTitle = (String)objectIn.readObject();
				String inText = (String)objectIn.readObject();
				LocalDateTime inDate = (LocalDateTime)objectIn.readObject();
				Boolean inCompletion = (Boolean)objectIn.readObject();
				LocalDateTime inModification = (LocalDateTime)objectIn.readObject();
				LocalDateTime inCreation = (LocalDateTime)objectIn.readObject();
				recycleBin.add(new Note(inTitle, inText, inDate, inModification, inCreation, inCompletion));
			}
			objectIn.close();
			fileIn.close();
			return true; 
		} catch (FileNotFoundException e) {
			//no file found, therefore no previous data. Continues with no data inside of notes and recycleBin
		} catch (IOException e) {
			System.out.println("IO Error");
		} catch (ClassNotFoundException e) {
			System.out.println("Class Error");
		}
		return false;
	}
	
	//no parameters, return Boolean, exports necessary object data to file from variable path
	public Boolean exportNote() {
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(notes.size());
			for (int i = 0; i < notes.size(); i++) {
				objectOut.writeObject(notes.get(i).getTitle());
				objectOut.writeObject(notes.get(i).getText());
				objectOut.writeObject(notes.get(i).getDate());
				objectOut.writeObject(notes.get(i).getCompleted());
				objectOut.writeObject(notes.get(i).getModification());
				objectOut.writeObject(notes.get(i).getCreation());
			}
			objectOut.writeObject(recycleBin.size());
			for (int i = 0; i < recycleBin.size(); i++) {
				objectOut.writeObject(recycleBin.get(i).getTitle());
				objectOut.writeObject(recycleBin.get(i).getText());
				objectOut.writeObject(recycleBin.get(i).getDate());
				objectOut.writeObject(recycleBin.get(i).getCompleted());
				objectOut.writeObject(recycleBin.get(i).getModification());
				objectOut.writeObject(recycleBin.get(i).getCreation());
			}
			objectOut.close();
			fileOut.close();
			return true; 
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		} catch (IOException e) {
			System.out.println("IO exception");
		}
		return false;
	}
	
	//no parameters and no return, removes all previous data
	public void clearAll() {
		notes.clear();
		recycleBin.clear();
		sortStrategy = new NewToOldSort();
		path = "data.dat";
	}
	
	//accepts String, sets the path to the String
	public void setPath(String path) {
		this.path = path;
	}
	
	//accepts two String and LocalDateTime and returns Boolean, creates a new Note and adds it to the notes list
	public void addNote(String title, String text, LocalDateTime date) {
		if (date == null) {
			notes.add(new Note(title, text));
		} else {
			notes.add(new Note(title, text, date));
		}
	}
	
	//removes note directly from note array; skips recycleBin
	public void directDelete(int index) {
	        notes.remove(index);
	}
	
	//no return value, accepts an int, moves the reference to the Note at the index from the notes list to the recycleBin list
	public void recycleNote(int index) {
		recycleBin.add(notes.get(index));
		notes.remove(index);
	}
	
	//no return value, accepts an int, moves the reference to the Note at the index from the recycleBin list to the notes list
	public void restoreNote(int index) {
		notes.add(recycleBin.get(index));
		recycleBin.remove(index);
	}
	
	//no return value, accepts an int, and removes the reference to the Note from the recycleBin list
	public void delete(int index) {
		recycleBin.remove(index);
	}
	
	//no parameter or return, removes all reference to Note objects from the recycleBin list
	public void deleteAll() {
		recycleBin.clear();
	}
	
	//accepts NoteSort and returns void, sorts the notes based on the sorting strategy
	public void sortNote(NoteSort sortingStrategy) {
		this.sortStrategy = sortingStrategy;
		notes = sortStrategy.sort(notes);
	}
	
	//accepts a String and returns an ArrayList, filters notesList for elements that have a title that contain key
	public ArrayList<Note> searchNote(String key) {
	        List<Note> filtered = new ArrayList<Note>();
	        if(key.length() > 0) {
	                filtered = notes.stream().filter(n->n.getTitle().contains(key)).collect(Collectors.toList());
	        }
		return (ArrayList<Note>) filtered;
	}
	
	//no parameter, returns an int for the size of the  notes list
	public int size() {
		return notes.size();
	}
	
	
	//no parameter, returns an int for the size of the recycleBin list
	public int recycleBinSize() {
		return recycleBin.size();
	}
}
