package service;

import model.NoteBook;

import java.util.List;
import java.util.Scanner;

public interface NoteBookService {
    void getCommands();

    NoteBook createNote();

    List<NoteBook> getAllNotes();

    void removeNote();

    void saveNoteAsFile();

    void exit(Scanner scanner);
}
