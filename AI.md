# AI Tool Usage Record

## Tools Used

- **Cursor AI (Claude)** -- used for the `branch-AiAssisted` increment.

## What Was Done

Used Cursor AI to improve overall code quality, documentation, and test coverage in a single session. Specific tasks:

1. **Bug fixes** -- AI identified and fixed 6 bugs I had missed, including:
   - `Event.toFileString()` writing `"D|"` instead of `"E|"` (events were saved as deadlines in the file).
   - `TaskList` boundary checks using `< 0` instead of `< 1` for 1-based indexing.
   - Mismatched error messages (e.g. `removeTask` error said "mark" instead of "delete").
   - Missing `hashCode()` override when `equals()` was overridden in `Task`.

2. **Code quality (SLAP, arrowhead reduction, duplication)** -- Refactored `Parser.parseCommand()` by extracting validation into private helpers (`validateTaskNumCommand`, `validateDeadlineCommand`, `validateEventCommand`). Eliminated duplicated date-formatting methods in `Deadline`/`Event` by moving them to the `Task` base class. Extracted `validateTaskNum()` in `TaskList`. Refactored `Allison.main()` to reuse `getResponse()` instead of duplicating the entire switch block.

3. **Cleanup** -- Removed unused fields (`scanner`, `SEPARATOR` in `Ui`), unused imports, commented-out code, and replaced magic numbers with named constants.

4. **Javadoc** -- Added Javadoc to all public classes and methods across all 15 source files.

5. **Tests** -- Expanded test suite from 5 tests to 87 tests. Created new `StorageTest` and `TaskTest` files, and significantly expanded `ParserTest` and `TaskListTest`.

## Observations

### What worked well
- AI was very effective at identifying bugs that are easy to overlook (e.g., the `"D|"` vs `"E|"` typo in `Event.toFileString()`).
- Boilerplate-heavy tasks like writing Javadoc and unit tests were completed much faster than doing them manually.
- The refactoring suggestions (extracting validation helpers, deduplicating code) were sensible and followed good OOP principles.

### What didn't work as well
- Had to review the generated tests to make sure they were actually testing meaningful behaviour and not just trivially passing.
- Some changes needed verification against the existing codebase to make sure nothing broke (e.g., the updated error messages in `TaskList` required updating the existing `TaskListTest` assertions too).

### Time saved
Overall, this saved roughly 1-2 hours of manual work. Writing 80+ unit tests alone would have taken a significant amount of time. The bug detection was also valuable -- the `Event.toFileString()` bug could have been difficult to catch during manual review.
