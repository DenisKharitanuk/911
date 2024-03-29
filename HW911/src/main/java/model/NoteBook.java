package model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class NoteBook {

    private static int idCounter = 0;
    private int id;
    @Getter
    private static List<NoteBook> noteList = new ArrayList<>();
    private String text;

    private List<String> labelsList;

    public NoteBook(String text, List<String> labelsList) {
        id = idGenerator();
        this.text = text;
        this.labelsList = labelsList;
    }

    public int idGenerator() {
        return ++idCounter;
    }

    @Override
    public String toString() {
        return String.format("{%d} # {%s}\n%s\n===================\n", this.getId(), this.getText(), this.getLabelsList());
    }
}
