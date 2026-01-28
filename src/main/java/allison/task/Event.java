package allison.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Event extends Task {
    protected LocalDateTime start;
    protected LocalDateTime end;

    public Event(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        this.start = start;
        this.end = end;
    }

    public String toDateString(LocalDateTime d) {
        return d.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + toDateString(start) + " to: " + toDateString(end) + ")";
    }

    @Override
    public String toFileString() {
        return "D|" + (isDone ? 1 : 0) + "|" + description
                + "|" + toDateString(start) + "|" + toDateString(end);
    }
}
