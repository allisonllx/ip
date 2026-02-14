package allison.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Represents a task with a description and completion status.
 * This is the base class for all task types (Todo, Deadline, Event).
 */
public class Task {
    protected static final DateTimeFormatter FILE_DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    protected static final DateTimeFormatter DISPLAY_DATE_FORMAT =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

    protected String description;
    protected boolean isDone;

    /**
     * Constructs a Task with the given description, initially not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        assert description != null;
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon for this task.
     *
     * @return "X" if the task is done, " " otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns the description of this task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Marks this task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Sets the done status of this task.
     *
     * @param isDone True if the task should be marked as done.
     */
    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Returns the file-storage representation of this task.
     *
     * @return A pipe-delimited string for saving to file.
     */
    public String toFileString() {
        return (isDone ? 1 : 0) + "|" + description;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task task)) {
            return false;
        }
        return isDone == task.isDone && description.equals(task.description);
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + Boolean.hashCode(isDone);
        return result;
    }
}