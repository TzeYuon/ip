package command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Tests access to command execution outcomes. */
public class CommandResultTest {
    @Test
    public void accessors_valuesProvided_sameValuesReturned() {
        CommandResult result = new CommandResult("saved", true, false);

        assertEquals("saved", result.message());
        assertTrue(result.taskListChanged());
        assertFalse(result.exit());
    }
}
