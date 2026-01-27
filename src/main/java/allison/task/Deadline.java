package allison.task;

public class Deadline extends Task {
    protected String dueDate;

    public Deadline(String description, String dueDate) {
        super(description);
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.dueDate + ")";
    }

    @Override
    public String toFileString() {
        return "D|" + (isDone ? 1 : 0) + "|" + description + "|" + dueDate;
    }
}
