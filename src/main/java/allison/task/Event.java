package allison.task;

import java.time.LocalDateTime;

/**
 * Represents a task that spans a time period, with a start and end date/time.
 */
public class Event extends Task {
    protected LocalDateTime start;
    protected LocalDateTime end;

    /**
     * Constructs an Event task with the given description, start time, and end time.
     *
     * @param description The description of the event.
     * @param start The start date/time of the event.
     * @param end The end date/time of the event.
     */
    public Event(String description, LocalDateTime start, LocalDateTime end) {
        super(description);
        assert start != null && end != null;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + start.format(DISPLAY_DATE_FORMAT)
                + " to: " + end.format(DISPLAY_DATE_FORMAT) + ")";
    }

    @Override
    public String toFileString() {
        return "E|" + (isDone ? 1 : 0) + "|" + description
                + "|" + start.format(FILE_DATE_FORMAT) + "|" + end.format(FILE_DATE_FORMAT);
    }
}
