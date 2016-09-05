package org.neo4j.shell.commands;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.neo4j.shell.exception.CommandException;
import org.neo4j.shell.exception.ExitException;
import org.neo4j.shell.log.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HelpTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private Logger logger = mock(Logger.class);
    private CommandHelper cmdHelper = mock(CommandHelper.class);

    private Command cmd;
    private EnhancedHelp enhancedHelp = new EnhancedHelp(logger);

    @Before
    public void setup() {
        this.cmd = new Help(logger, cmdHelper, enhancedHelp);
    }

    @Test
    public void shouldAcceptNoArgs() throws CommandException {
        cmd.execute("");
        // Should not throw
    }

    @Test
    public void shouldNotAcceptTooManyArgs() throws CommandException {
        thrown.expect(CommandException.class);
        thrown.expectMessage(containsString("Incorrect number of arguments"));

        cmd.execute("bob alice");
        fail("Should not accept too many args");
    }

    @Test
    public void helpListingShouldListCommandsAndCypherHelp() throws CommandException {
        // given
        List<Command> commandList = new ArrayList<>();

        commandList.add(new FakeCommand("bob"));
        commandList.add(new FakeCommand("bobby"));

        doReturn(commandList).when(cmdHelper).getAllCommands();

        // when
        cmd.execute("");

        // then
        verify(logger).printOut("\nAvailable commands:");
        verify(logger).printOut("  @|BOLD bob  |@ description for bob");
        verify(logger).printOut("  @|BOLD bobby|@ description for bobby");
        verify(logger).printOut("\nFor help on a specific command type:");
        verify(logger).printOut("    :help@|BOLD  command|@\n");
        verify(logger).printOut("\nFor help on cypher type:");
        verify(logger).printOut("    :help@|BOLD  CYPHER|@\n");
    }

    @Test
    public void helpForCommand() throws CommandException {
        // given
        doReturn(new FakeCommand("bob")).when(cmdHelper).getCommand(eq("bob"));

        // when
        cmd.execute("bob");

        // then
        verify(logger).printOut("\nusage: @|BOLD bob|@ usage for bob\n"
                               + "\nhelp for bob\n");
    }

    @Test
    public void helpForCypher() throws CommandException {
        // given
        doReturn(new FakeCommand("bob")).when(cmdHelper).getCommand(eq("bob"));

        // when
        cmd.execute("cypher");

        // then
        verify(logger).printOut(getUsageText());
    }

    @Test
    public void helpForNonExistingCommandThrows() throws CommandException {
        // then
        thrown.expect(CommandException.class);
        thrown.expectMessage("No such command: notacommandname");

        // when
        cmd.execute("notacommandname");
    }

    @Test
    public void helpForCommandHasOptionalColon() throws CommandException {
        // given
        doReturn(new FakeCommand(":bob")).when(cmdHelper).getCommand(eq(":bob"));

        // when
        cmd.execute("bob");

        // then
        verify(logger).printOut("\nusage: @|BOLD :bob|@ usage for :bob\n"
                + "\nhelp for :bob\n");
    }

    private String getUsageText() {
        return "@|BOLD CYPHER|@\n" +
                "\n" +
                "CYPHER\n" +
                "\n" +
                "A graph query language\n" +
                "\n" +
                "Cypher is Neo4j's graph query language. Working with a graph is all about understanding\n" +
                "patterns of data, which are central to Cypher queries.\n" +
                "\n" +
                "Use MATCH clauses for reading data, and CREATE or MERGE for writing data.\n" +
                "\n" +
                "Reference\n" +
                "\n" +
                "[CREATE](https://neo4j.com/docs/developer-manual/3.0/cypher/#cypher-intro) manual page\n" +
                "\n" +
                "Related\n" +
                "\n" +
                ":help MATCH\n" +
                ":help WHERE\n" +
                ":help RETURN\n" +
                ":help CREATE\n" +
                ":help MERGE\n" +
                ":help DELETE\n" +
                ":help DETACH DELETE\n" +
                ":help SET\n" +
                ":help FOREACH\n" +
                ":help HELP\n" +
                ":help WITH\n" +
                ":help LOAD CSV\n" +
                ":help UNWIND\n" +
                ":help START\n" +
                ":help CREATE UNIQUE\n" +
                ":help CREATE INDEX ON\n" +
                ":help STARTS WITH\n" +
                ":help ENDS WITH\n" +
                ":help CONTAINS\n" +
                "\n" +
                "Example\n" +
                "\n" +
                "Basic form of a Cypher read statement. (Not executable)\n" +
                "\n" +
                "    MATCH <pattern> WHERE <conditions> RETURN <expressions>\n" +
                "\n";
    }

    private class FakeCommand implements Command {
        private final String name;

        FakeCommand(String name) {
            this.name = name;
        }

        @Nonnull
        @Override
        public String getName() {
            return name;
        }

        @Nonnull
        @Override
        public String getDescription() {
            return "description for " + name;
        }

        @Nonnull
        @Override
        public String getUsage() {
            return "usage for " + name;
        }

        @Nonnull
        @Override
        public String getHelp() {
            return "help for " + name;
        }

        @Nonnull
        @Override
        public List<String> getAliases() {
            return new ArrayList<>();
        }

        @Override
        public void execute(@Nonnull String args) throws ExitException, CommandException {

        }
    }
}
