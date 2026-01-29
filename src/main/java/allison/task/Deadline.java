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

    public String toLocalDateString(LocalDateTime d) {
        return d.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

    public String toDateString(LocalDateTime d) {
        return d.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + toLocalDateString(dueDate) + ")";
    }

    @Override
    public String toFileString() {
        return "D|" + (isDone ? 1 : 0)
                + "|" + description + "|" + toDateString(dueDate);
    }
}
