package id.sch.smktelkom_mlg.project2.xirpl20306132027.scheme.model;

import java.io.Serializable;

/**
 * Created by krisnapf on 28/03/17.
 */

public class Scheme implements Serializable {
    public String activity;
    public String due;
    public String category;
    public String priority;
    public String note;

    public Scheme(String activity, String due, String category, String priority, String note) {
        this.activity = activity;
        this.due = due;
        this.category = category;
        this.priority = priority;
        this.note = note;
    }
}
