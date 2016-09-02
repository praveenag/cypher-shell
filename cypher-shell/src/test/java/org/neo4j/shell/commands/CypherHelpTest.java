package org.neo4j.shell.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.neo4j.shell.exception.CommandException;
import org.neo4j.shell.log.Logger;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
public class CypherHelpTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private Logger logger = mock(Logger.class);
    private final CypherHelp cmd = new CypherHelp(logger);

    @Test
    public void usageShouldPrintAllCypherKeywords() throws Exception {
        assertThat(cmd.getUsage(), is(getUsageText()));
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
    public void helpListing() throws CommandException {
        // when
        cmd.execute("");

        // then
        verify(logger).printOut(getUsageText());
    }

    @Test
    public void helpForCommand() throws CommandException {
        // when
        cmd.execute("CREATE");

        // then
        verify(logger).printOut(createHelpText());
    }

    @Test
    public void helpForNonExistingCommandThrows() throws CommandException {
        // then
        thrown.expect(CommandException.class);
        thrown.expectMessage("No such cypher command: notacypher");

        // when
        cmd.execute("notacypher");
    }

    @Test
    public void helpIsCaseInsensitive() throws CommandException {
        // when
        cmd.execute("create");

        // then
        verify(logger).printOut(createHelpText());
    }

    @Test
    public void isCypherKeyword() throws CommandException {
        // when
        assertTrue(cmd.isCypherKeyword("create"));
        assertTrue(cmd.isCypherKeyword("CREATE"));
        assertTrue(cmd.isCypherKeyword("CYPHER"));
        assertFalse(cmd.isCypherKeyword("NotAValidCommand"));
    }

    private String createHelpText() {
        return "@|BOLD CREATE|@\n" +
                "\n" +
                "CREATE\n" +
                "\n" +
                "Insert graph data\n" +
                "\n" +
                "The CREATE clause is used to create data by specifying named\n" +
                "nodes and relationships with inline properties.\n" +
                "\n" +
                "Use MATCH clauses for reading data, and CREATE or MERGE for writing data.\n" +
                "\n" +
                "Reference\n" +
                "\n" +
                "[CREATE](https://neo4j.com/docs/developer-manual/3.0/cypher/#query-create) manual page\n" +
                "\n" +
                "Related\n" +
                "\n" +
                ":help SET\n" +
                ":help CREATE UNIQUE\n" +
                ":help MERGE\n" +
                ":help Cypher\n" +
                "\n" +
                "Example\n" +
                "\n" +
                "Create two related people, returning them.\n" +
                "\n" +
                "    CREATE (le:Person {name:\"Euler\"}),\n" +
                "      (db:Person {name:\"Bernoulli\"}),\n" +
                "      (le)-[:KNOWS {since:1768}]->(db)\n" +
                "    RETURN le, db\n" +
                "\n";
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
}