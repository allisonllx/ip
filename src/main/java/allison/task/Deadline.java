package allison.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Deadline extends Task {
    protected LocalDateTime dueDate;

    public Deadline(String description, LocalDateTime dueDate) {
        super(description);
        this.dueDate = dueDate;
    }

    public String toDateString(LocalDateTime d) {
        return d.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + toDateString(dueDate) + ")";
    }

    @Override
    public String toFileString() {
        return "D|" + (isDone ? 1 : 0)
                + "|" + description + "|" + toDateString(dueDate);
    }
}
