package allison.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Event extends Task {
    protected LocalDateTime start;
    protected LocalDateTime end;

    public Event(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        assert start != null && end != null;
        this.start = start;
        this.end = end;
    }

    public String toLocalDateString(LocalDateTime d) {
        assert d != null;
        return d.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

    public String toDateString(LocalDateTime d) {
        assert d != null;
        return d.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + toLocalDateString(start)
                + " to: " + toLocalDateString(end) + ")";
    }

    @Override
    public String toFileString() {
        return "D|" + (isDone ? 1 : 0) + "|" + description
                + "|" + toDateString(start) + "|" + toDateString(end);
    }
}
