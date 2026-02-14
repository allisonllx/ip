package allison.task;

import java.time.LocalDateTime;

/**
 * Represents a task with a deadline (due date/time).
 */
public class Deadline extends Task {
    protected LocalDateTime dueDate;

    /**
     * Constructs a Deadline task with the given description and due date.
     *
     * @param description The description of the deadline task.
     * @param dueDate The date/time by which the task is due.
     */
    public Deadline(String description, LocalDateTime dueDate) {
        super(description);
        assert dueDate != null;
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dueDate.format(DISPLAY_DATE_FORMAT) + ")";
    }

    @Override
    public String toFileString() {
        return "D|" + (isDone ? 1 : 0)
                + "|" + description + "|" + dueDate.format(FILE_DATE_FORMAT);
    }
}
