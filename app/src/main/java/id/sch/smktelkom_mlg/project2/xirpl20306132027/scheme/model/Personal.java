package id.sch.smktelkom_mlg.project2.xirpl20306132027.scheme.model;

/**
 * Created by rehan on 08/04/17.
 */

public class Personal {
    String activity;
    String due;
    String category;
    String note;

    public Personal() {
    }

    public Personal(String activity, String due, String category, String note) {
        this.activity = activity;
        this.due = due;
        this.category = category;
        this.note = note;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
