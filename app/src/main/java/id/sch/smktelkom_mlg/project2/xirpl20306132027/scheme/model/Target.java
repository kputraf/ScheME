package id.sch.smktelkom_mlg.project2.xirpl20306132027.scheme.model;

/**
 * Created by rehan on 08/04/17.
 */

public class Target {
    String target;
    String priority;
    String due;
    String note;

    public Target() {
    }

    public Target(String target, String priority, String due, String note) {
        this.target = target;
        this.priority = priority;
        this.due = due;
        this.note = note;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
