package dao;

import lombok.Data;
import lombok.extern.java.Log;
import model.NoteBook;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Log
public class NoteBookDaoImpl implements NoteBookDao {
    @Override
    public void getCommands() {

        log.fine("Вызвана команда HELP");
        log.fine("Это Ваша записная книжка. " +
                "Список доступных комманд: \n help - все доступные команды \n note-new - создать новую заметку \n note-list - вывести весь список заметок \n note-remove - удалить заметку \n note-export - поместить все заметки в текстовый файл \n exit - завершить работу приложения");


    }

    @Override
    public NoteBook createNote() {
        log.fine("Вызвана команда note-new");
        log.fine("Введите заметку");
        NoteBook newNote = null;

        List<String> labelsList;
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        try {
            if (text.length() < 3) {
                log.info("Поле ввода не должен быть пустым, длина не менее 3 символов! Введено: " + text);
                throw new IllegalArgumentException("Поле ввода не должен быть пустым, длина не менее 3 символов! Введено: " + text);
            }
            log.fine("Добавить метки? Метки состоят из одного слова и могу содержать только буквы. Для добавления нескольких меток разделяйте слова пробелом.");
            String label = scanner.nextLine();
            labelsList = List.of(label.toUpperCase().split(" "));
            List<String> filteredLabels = new ArrayList<>();
            for (String n : labelsList) {
                if (!n.matches("^[a-zA-Z]+$")) {
                    log.info("Метка должна состоят только из букв. Введено: " + label);
                    throw new IllegalArgumentException("Данные введены некорректно. Метки должны  состоят из одного слова и могу содержать только буквы ");
                } else {
                    filteredLabels.add(n);
                }

            }


            newNote = new NoteBook(text, filteredLabels);
            NoteBook.getNoteList().add(newNote);
            log.fine("Заметка добавлена");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }


        return newNote;
    }

    @Override
    public List<NoteBook> getAllNotes() {

        log.fine("Вызвана команда note-list");
        Scanner scanner = new Scanner(System.in);
        List<String> labels;
        List<NoteBook> filteredNotes = new ArrayList<>();
        String readLabelsFromConsole;
        log.fine("Введите метки, чтобы отобразить определенные заметки или оставьте пустым для отображения всех заметок");
        try {
            readLabelsFromConsole = scanner.nextLine();
            if (readLabelsFromConsole.isEmpty()) {
                labels = List.of(readLabelsFromConsole.toUpperCase().split(" "));
                for (String n : labels) {
                    if (!n.matches("^[a-zA-Z]+$")) {
                        log.info("Метка должна состоят только из букв. Введено: " + n);
                        throw new IllegalArgumentException("Данные введены некорректно. Метки должны  состоят из одного слова и могу содержать только буквы ");
                    } else {
                        for (NoteBook i : NoteBook.getNoteList()) {
                            if (i.getLabelsList().contains(n)) {
                                filteredNotes.add(i);
                            }
                        }
                    }
                }
                if (filteredNotes.isEmpty()) {
                    log.info(" Список заметок пуст");
                    throw new IllegalArgumentException("Список заметок пуст ");
                }
                filteredNotes = NoteBook.getNoteList();
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        for (NoteBook n : filteredNotes) {
            System.out.println(n);
        }

        return filteredNotes;
    }

    @Override
    public void removeNote() {
        log.fine("Вызвана команда note-remove ");
        log.fine("Введите id заметки для ее удаления.");
        Scanner scanner = new Scanner(System.in);
        int id;
        String readIdFromConsole = scanner.nextLine();
        String filter = "^\\d*";

        try {
            if (!readIdFromConsole.matches(filter)) {
                log.info("Неверный формат ввода, должно быть одно число. Введено " + readIdFromConsole);
                throw new IllegalArgumentException("Неверный формат ввода, должно быть одно число. Введено " + readIdFromConsole);
            } else if (NoteBook.getNoteList().isEmpty()) {
                log.warning("Список заметок пуст");
                throw new IllegalArgumentException("Список заметок пуст");
            }


        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        id = Integer.parseInt(readIdFromConsole);
        List<Integer> notesID = new ArrayList<>();
        for (NoteBook n : NoteBook.getNoteList()) {
            notesID.add(n.getId());

        }
        if (!notesID.contains(id)) {
            log.info("Заметка не найдена");
        }

        Iterator<NoteBook> iterator = NoteBook.getNoteList().iterator();
        while (iterator.hasNext()) {
            NoteBook noteBook = iterator.next();
            if (noteBook.getId() == id) {
                iterator.remove();
                log.fine("Заметка удалена ");
            }
        }

    }

    @Override
    public void saveNoteAsFile() {
        log.fine("Вызвана команда note-export");
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy_HH-mm-ss");
        String fileName = "notes" + dateFormat.format(new Date()) + ".txt";
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            if (NoteBook.getNoteList().isEmpty()) {
                log.info("Список Заметок пуст");
                throw new IOException("Список заметок пуст");
            }
            for (NoteBook n : NoteBook.getNoteList()) {
                fileWriter.write(n.toString());
            }
            log.fine("Заметка сохранена в файл: " + fileName);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void exit(Scanner scanner) {
        log.fine("Вызвана команда exit ");
        log.fine("Приложение завершило работу");
        System.exit(0);
    }
}
