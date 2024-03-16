package service;

import dao.NoteBookDaoImpl;
import lombok.extern.java.Log;
import model.NoteBook;

import java.util.List;
import java.util.Scanner;

@Log
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
    public void removeNote() {
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

    public void start() {

        log.fine("Это Ваша записная книжка." +
                "\n Вот список доступных команд:\n help \n note-new \n note-list \n note-remove \n note-export \n exit ");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String command = scanner.nextLine();
            switch (command) {
                case "help" -> getCommands();
                case "exit" -> exit(scanner);
                case "note-new" -> createNote();
                case "note-list" -> getAllNotes();
                case "note-remove" -> removeNote();
                case "note-export" -> saveNoteAsFile();
                default -> log.warning("Такой команды не существует ");
            }
        }
    }
}
