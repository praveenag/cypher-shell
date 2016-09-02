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
        assertThat(enhancedHelp.getAllKeyWordsUsage(), is(getUsageText()));
    }

    @Test
    public void helpForCommand() throws CommandException {
        // when
        enhancedHelp.printHelpFor("create");

        // then
        verify(logger).printOut(createHelpText());
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