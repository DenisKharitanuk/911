package dao;

import lombok.extern.java.Log;
import model.NoteBook;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Log
public class NoteBookDaoImpl implements NoteBookDao {
    @Override
    public void getCommands() {

        log.info("Вызвана команда HELP");
        log.info("Это Ваша записная книжка. " +
                "\n Вот список доступных команд:\n help \n note-new \n note-list \n note-remove \n note-export \n exit ");


    }

    @Override
    public NoteBook createNote() {
        log.info("Вызвана команда note-new");
        NoteBook newNote = null;
        log.info("Введите заметку");
        List<String> labelsList;
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        try {
            if (text.length() < 3) {
                log.info("Поле ввода не должен быть пустым, длина не менее 3 символов!");
                throw new IllegalArgumentException("Поле ввода не должен быть пустым, длина не менее 3 символов!");
            }
            log.info("Добавить метки? Метки состоят из одного слова и могу содержать только буквы. Для добавления нескольких меток разделяйте слова пробелом.");
            String label = scanner.nextLine();
            labelsList = List.of(label.toUpperCase().split(" "));
            List<String> filteredLabels = new ArrayList<>();
            for (String n : labelsList) {
                if (!n.matches("^[a-zA-Z]+$")) {
                    log.info("Метка должна состоят только из букв");
                    throw new IllegalArgumentException("Данные введены некорректно. Метки должны  состоят из одного слова и могу содержать только буквы ");
                } else {
                    filteredLabels.add(n);
                }

            }


            newNote = new NoteBook(text, filteredLabels);
            NoteBook.getNoteList().add(newNote);
            log.info("Заметка добавлена");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }


        return newNote;
    }

    @Override
    public List<NoteBook> getAllNotes() {

        log.info("Вызвана команда note-list");
        Scanner scanner = new Scanner(System.in);
        List<String> labels;
        List<NoteBook> filteredNotes = new ArrayList<>();

        return null;
    }

    @Override
    public void removeNote() {
        log.info("Вызвана команда note-remove ");
    }

    @Override
    public void saveNoteAsFile() {
        log.info("Вызвана команда note-export");
    }

    @Override
    public void exit(Scanner scanner) {
        log.info("Вызвана команда exit ");
    }
}
