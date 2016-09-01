package org.neo4j.shell.commands;

import org.junit.Test;
import org.neo4j.shell.exception.CommandException;
import org.neo4j.shell.log.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EnhancedHelpTest {

    private final Logger logger = mock(Logger.class);
    private final EnhancedHelp enhancedHelp = new EnhancedHelp(logger);

    @Test
    public void constructedShouldInitialiseResourcesBasedOnKeyWords() throws Exception {
        assertThat(enhancedHelp.getAllKeyWordsUsage(), is("[CYPHER, CREATE]"));
    }

    @Test
    public void helpForCommand() throws CommandException {
        // when
        enhancedHelp.printHelpFor("create");

        // then
        verify(logger).printOut(createHelpText());
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
}