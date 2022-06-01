package BackTest;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import org.junit.Test;
import DateTimeUtil.*;
import NoteBackEnd.*;

public class NotesListTest {
	
	//Checking if when creating notes, fields have the proper data
	@Test
	public void noteCreationTest() {
		//testing the constructors for notes and get methods
		NotesList data = new NotesList();
		String title = "test case title";
		String text = "test case text";
		LocalDateTime date = DateTimeUtil.dateTimeStringParse("2015/08/04", "10:11:30");
		data.addNote(title, text, date);
		assertEquals("data.get(0).getTitle()", title, data.get(0).getTitle());
		assertEquals("data.get(0).geText()", text, data.get(0).getText());
		//testing the update method
		title = "test case 2 title";
		text = "test case 2 text";
		data.get(0).update(title, text, date);
		assertEquals("Update: data.get(0).getTitle()", title, data.get(0).getTitle());
		assertEquals("Update: data.get(0).geText()", text, data.get(0).getText());
	}

	//Checking if notes are being properly recycled, restored, and deleted
	@Test
	public void noteRecycleTest() {
		//testing the recycle methods
		NotesList data = new NotesList();
		String title = "test case title";
		String text = "test case text";
		LocalDateTime date = DateTimeUtil.dateTimeStringParse("2015/08/04", "10:11:30");
		data.addNote(title, text, date);
		data.recycleNote(0);
		assertEquals("data.recycleBinGet(0).getTitle()", title, data.recycleBinGet(0).getTitle());
		//testing the restore method
		data.restoreNote(0);
		assertEquals("data.Get(0).getTitle()", title, data.get(0).getTitle());
		//testing the delete methods
		data.addNote(title, text, date);
		data.recycleNote(0);
		data.delete(0);
		data.directDelete(0);
		assertEquals("data.size()", 0, data.size());
		assertEquals("data.recycleBinSize()", 0, data.recycleBinSize());
		data.addNote(title, text, date);
		data.recycleNote(0);
		data.deleteAll();
		assertEquals("data.deleteAll()", 0, data.recycleBinSize());
	}
	
	//Checking if notes are being properly imported and exported
	@Test
	public void noteimportexportTest() {
		//adding notes to the NotesList instance
		String title = "test case title";
		String text = "test case text";
		NotesList data = new NotesList();
		LocalDateTime date2 = LocalDateTime.parse("2015-08-04T10:11:30");
		data.addNote(title, text, date2);
		data.addNote(title, text, date2);
		data.recycleNote(1);
		//exporting notes
		data.exportNote();
		data.clearAll();
		data.importNote();
		//checking if notes are properly imported
		assertEquals("data.get(0).getTitle()", title, data.get(0).getTitle());
		assertEquals("data.get(0).geText()", text, data.get(0).getText());
		assertEquals("data.get(0).getDate()", date2, data.get(0).getDate());
		assertEquals("data.recycleBinGet(0).getTitle()", title, data.recycleBinGet(0).getTitle());
		assertEquals("data.recycleBinGet(0).geText()", text, data.recycleBinGet(0).getText());
		assertEquals("data.recycleBinGet(0).getDate()", date2, data.recycleBinGet(0).getDate());
		//testing when data file does not exist
		data.setPath("test");
		assertEquals("data.importNote()", false, data.importNote());
		//testing when cannot export note to file
		data.setPath("C:/");
		assertEquals("data.exportNote()", false, data.exportNote());
	}
	
	//Checking if the search is working
	@Test
	public void noteSearchTest() {
		//Checking the import and export methods
		NotesList data = new NotesList();
		String key = "test";
		LocalDateTime date = DateTimeUtil.dateTimeStringParse("2015/08/04", "10:11:30");
		data.addNote("test", "String", date);
		data.addNote("testtest", "String", date);
		data.addNote("testtesttest", "String", date);
		data.addNote("no", "String", date);
		//testing search method with a key
		assertEquals("data.searchNote(key).size()", 3, data.searchNote(key).size());
		//testing search  method when no key is present
		key = "";
		assertEquals("data.searchNote(key).size()", 0, data.searchNote(key).size());
	}
	
	//Checking the A to Z sorting method
	@Test
	public void noteAZSortTest() {
		NotesList sorted = new NotesList();
		NotesList toSort = new NotesList();
		//Adding notes to both list
		sorted.addNote("A", "Note A", null);
		sorted.addNote("M", "Note M", null);
		sorted.addNote("Z", "Note Z", null);
		toSort.addNote("M", "Note M", null);
		toSort.addNote("Z", "Note Z", null);
		toSort.addNote("A", "Note A", null);
		//Sorting toSort from A to Z
		toSort.sortNote(new AtoZSort());
		//Testing if the titles in both notes match up after sorting
		for (int i = 0; i < sorted.size(); i++) {
			assertEquals("toSort.get(i).get(title)" + i,sorted.get(i).getTitle(), toSort.get(i).getTitle());
		}
	}
	
	//Checking the Z to A sorting method
	@Test
	public void noteZASortTest() {
		NotesList sorted = new NotesList();
		NotesList toSort = new NotesList();
		//Adding notes to both list
		sorted.addNote("Z", "Note Z", null);
		sorted.addNote("M", "Note M", null);
		sorted.addNote("A", "Note A", null);
		toSort.addNote("M", "Note M", null);
		toSort.addNote("Z", "Note Z", null);
		toSort.addNote("A", "Note A", null);
		//Sorting toSort from A to Z
		toSort.sortNote(new ZtoASort());
		//Testing if the titles in both notes match up after sorting
		for (int i = 0; i < sorted.size(); i++) {
			assertEquals("toSort.get(i).get(title)" + i,sorted.get(i).getTitle(), toSort.get(i).getTitle());
		}
	}
}
