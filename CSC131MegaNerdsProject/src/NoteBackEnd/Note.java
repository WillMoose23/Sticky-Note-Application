package NoteBackEnd;

import java.time.LocalDateTime;

/* 
 * Note class
 * Implements the NoteComposite interface
 * Contains object data and method for needed for notes
 * Constructor called from the NotesList Class
 */

public class Note {
	
	//stored values for Note object
	private String title;
	private String text;
	private LocalDateTime date;
	private LocalDateTime lastModification;
	private LocalDateTime creation;
	private Boolean completed;
	
	//constructor, accepts 2 String and LocalDateTime, creates a new Note object, package visibility
	Note(String title, String text, LocalDateTime date) {
		this.title = title;
		this.text = text;
		this.date = date;
		completed = false;
		lastModification = LocalDateTime.now();
		creation = LocalDateTime.now();
	}
	
	//constructor, accepts 2 String, creates a new Note object, package visibility
	Note(String title, String text) {
		this.title = title;
		this.text = text;
		this.date = null;
		completed = false;
		lastModification = LocalDateTime.now();
		creation = LocalDateTime.now();
	}
	
	//constructor, accepts 2 String, one Boolean, 3 LocalDateTime, package visibility
	Note(String title, String text, LocalDateTime date, LocalDateTime lastModification, LocalDateTime creation, Boolean completed) {
		this.title = title;
		this.text = text;
		this.date = date;
		this.lastModification = lastModification;
		this.creation = creation;
		this.completed = completed;
	}
	
	
	//no parameters, returns String for the title of the Note
	public String getTitle() {
		return title;
	}
	
	//no parameters, returns String for the text of the Note
	public String getText() {
		return text;
	}
	
	//no parameters, returns a Boolean for the completion status of the Note
	public Boolean getCompleted() {
		return completed;
	}
	
	//no parameters, returns LocalDateTime for the date of the Note
	public LocalDateTime getDate() {
		return date;
	}
	
	//no parameters, returns LocalDateTime for the creation time of the Note
	public LocalDateTime getCreation() {
		return creation;
	}
	
	//no parameters, returns LocalDateTime for the last modification time of the Note
	public LocalDateTime getModification() {
		return lastModification;
	}
	
	//accepts 2 String and LocalDateTime, no return, updates the values of in the instance of Note and updates the last modification time
	public void update(String title, String text, LocalDateTime currentDate) {
		this.title = title;
		this.text = text;
		LocalDateTime temp = currentDate;
		this.date = temp;
		lastModification = LocalDateTime.now();
	}
	
	//no parameters or return, switches the value of completed
	public void toggleCompletion(boolean choice) {
		completed = choice;
	}
}
