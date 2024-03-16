import model.NoteBook;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.powermock.reflect.Whitebox;
import service.NoteBookService;
import service.NoteBookServiseImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class NoteTests {
    private final NoteBookService noteService = new NoteBookServiseImpl();

    @Mock
    public List<NoteBook> mockNoteList = new ArrayList<>();
    private String input;
    private InputStream inputStream;
    private NoteBook newNote;

    @DisplayName("Уникальность генерируемых id")
    @Test
    public void generateUniqueId() {
        input = "Test\ntext";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        newNote = noteService.createNote();

        String input2 = "Text text\nlabel label";
        InputStream inputStream2 = new ByteArrayInputStream(input2.getBytes());
        System.setIn(inputStream2);
        NoteBook newNote2 = noteService.createNote();

        assertNotEquals(newNote.getId(), newNote2.getId());
    }

    @DisplayName("Создание записки с некорректным текстом")
    @Test
    public void createNoteWithInvalidText() {
        input = "My\nlabel";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Whitebox.setInternalState(NoteBook.class, "noteList", mockNoteList);
        newNote = noteService.createNote();

        assertThrows(NoSuchElementException.class, noteService::createNote);
        assertEquals(0, mockNoteList.size());
        assertNull(newNote);
    }

    @DisplayName("Создание записки с корректным текстом")
    @Test
    public void createNoteWithValidText() {
        input = "It is'n my job\nlabel";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Whitebox.setInternalState(NoteBook.class, "noteList", mockNoteList);
        newNote = noteService.createNote();

        assertEquals(1, mockNoteList.size());
        assertEquals("It is'n my job", newNote.getText());
    }

    @DisplayName("Создание записки с некорректной меткой")
    @Test
    public void createNoteWithInvalidLabel() {
        input = "It is'n my job\n@#$%$%^";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Whitebox.setInternalState(NoteBook.class, "noteList", mockNoteList);
        newNote = noteService.createNote();

        assertEquals(0, mockNoteList.size());
        assertNull(newNote);
    }

    @DisplayName("Создание записки с корректной меткой")
    @Test
    public void createNote_validLabel_addNoteToList() {
        input = "It is'n my job\nlabel";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Whitebox.setInternalState(NoteBook.class, "noteList", mockNoteList);
        newNote = noteService.createNote();

        assertFalse(mockNoteList.isEmpty());
        assertEquals(List.of("LABEL"), newNote.getLabelsList());
    }

    @DisplayName("Удаление записки с некорректным id")
    @Test
    public void removeNoteWithInvalidId() {
        String id = "айди";
        InputStream inputStream = new ByteArrayInputStream(id.getBytes());
        System.setIn(inputStream);
        noteService.removeNote();
        assertThrows(NoSuchElementException.class, noteService::removeNote);
    }

    @DisplayName("Удаление записки с корректным id")
    @Test
    public void removeNoteWithValidId() {
        input = "It is'n my job\nlabel";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Whitebox.setInternalState(NoteBook.class, "noteList", mockNoteList);
        newNote = noteService.createNote();
        InputStream is = new ByteArrayInputStream(String.valueOf(newNote.getId()).getBytes());
        System.setIn(is);
        noteService.removeNote();

        assertTrue(mockNoteList.isEmpty());
    }
}
