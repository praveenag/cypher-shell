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
    private final List<String> keywords;

    public EnhancedHelp(Logger logger) {
        this.logger = logger;
        this.keywords = new ArrayList<>();
        initKeyWords();
    }

    public String getAllKeyWordsUsage() {
        return getHelp("CYPHER").formattedString();
    }

    public void printGeneralHelp() throws CommandException {
        logger.printOut(getHelp("CYPHER").formattedString());
    }

    public void printHelpFor(@Nonnull final String name) throws CommandException {
        Optional<String> matchedCypherCommand = keywords.stream()
                .filter(kw -> kw.equalsIgnoreCase(name)).findFirst();

        if (!matchedCypherCommand.isPresent()) {
            throw new CommandException(AnsiFormattedText.from("No such cypher command: ").bold().append(name));
        }

        String cmd = matchedCypherCommand.get();
        logger.printOut(getHelp(cmd).formattedString());
    }

    private AnsiFormattedText getHelp(String cmd) {
        String fileName = format("%s.txt", cmd.toLowerCase());
        InputStream resourceAsStream = EnhancedHelp.class.getClassLoader().getResourceAsStream(fileName);
        BufferedReader in = new BufferedReader(new InputStreamReader(resourceAsStream));

        AnsiFormattedText ansiFormattedText = AnsiFormattedText.from("").bold().append(cmd).boldOff()
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

    private void initKeyWords() {
        this.keywords.add("CYPHER");
        this.keywords.add("CREATE");
    }
}