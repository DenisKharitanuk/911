package service;

import dao.NoteBookDaoImpl;
import model.NoteBook;

import java.util.List;
import java.util.Scanner;

public class NoteBookServiseImpl implements NoteBookService {

    private NoteBookDaoImpl noteBookDao = new NoteBookDaoImpl();

    @Override
    public void getCommands() {
        noteBookDao.getCommands();

    }

    @Override
    public NoteBook createNote() {
        return noteBookDao.createNote();
    }

    @Override
    public List<NoteBook> getAllNotes() {
        return noteBookDao.getAllNotes();
    }

    @Override
    public void deleteNote() {
        noteBookDao.removeNote();

    }

    @Override
    public void saveNoteAsFile() {
        noteBookDao.saveNoteAsFile();

    }

    @Override
    public void exit(Scanner scanner) {
        noteBookDao.exit(scanner);

    }
}
