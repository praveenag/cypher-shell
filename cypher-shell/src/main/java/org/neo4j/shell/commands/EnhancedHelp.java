package org.neo4j.shell.commands;

import org.neo4j.shell.exception.CommandException;
import org.neo4j.shell.log.AnsiFormattedText;
import org.neo4j.shell.log.Logger;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

public class EnhancedHelp {

    private final Logger logger;

    public EnhancedHelp(Logger logger) {
        this.logger = logger;
    }

    public String getAllKeyWordsUsage() {
        return getHelp("CYPHER").formattedString();
    }

    public void printHelpFor(@Nonnull final String name) throws CommandException {
        if (!isCypherKeyword(name)) {
            throw new CommandException(AnsiFormattedText.from("No such cypher command: ").bold().append(name));
        }

        logger.printOut(getHelp(name.toUpperCase()).formattedString());
    }

    private AnsiFormattedText getHelp(String cypherKeyword) {
        BufferedReader in = new BufferedReader(new InputStreamReader(fileAsStream(cypherKeyword)));

        AnsiFormattedText ansiFormattedText = AnsiFormattedText.from("").bold().append(cypherKeyword).boldOff()
                .append("\n\n");
        String line;
        try {
            while ((line = in.readLine()) != null) {
                ansiFormattedText.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ansiFormattedText.append("\n");
    }

    private InputStream fileAsStream(String cmd) {
        String fileName = format("%s.txt", cmd.toLowerCase());
        return EnhancedHelp.class.getClassLoader().getResourceAsStream(fileName);
    }

    public boolean isCypherKeyword(String name) {
        return fileAsStream(name) != null;
    }
}