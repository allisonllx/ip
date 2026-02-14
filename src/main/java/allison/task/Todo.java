package allison.task;

/**
 * Represents a simple task with only a description and no date/time attached.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task with the given description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toFileString() {
        return "T|" + (isDone ? 1 : 0) + "|" + description;
    }
}
