package org.neo4j.shell.commands;

import org.neo4j.shell.exception.CommandException;
import org.neo4j.shell.exception.ExitException;
import org.neo4j.shell.log.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.neo4j.shell.commands.CommandHelper.simpleArgParse;

public class CypherHelp implements Command {
    public static final String COMMAND_NAME = ":cypher-help";
    private final EnhancedHelp enhancedHelp;

    public CypherHelp(@Nonnull Logger logger) {
        this.enhancedHelp = new EnhancedHelp(logger);
    }

    @Nonnull
    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Nonnull
    @Override
    public String getDescription() {
        return "Show help text for different cypher keywords";
    }

    @Nonnull
    @Override
    public String getUsage() {
        return enhancedHelp.getAllKeyWordsUsage();
    }

    @Nonnull
    @Override
    public String getHelp() {
        return "Show the detailed help for a specific cypher keyword.";
    }

    @Nonnull
    @Override
    public List<String> getAliases() {
        return new ArrayList<>();
    }

    @Override
    public void execute(@Nonnull String argString) throws ExitException, CommandException {
        String[] args = simpleArgParse(argString, 0, 1, COMMAND_NAME, getUsage());
        if (args.length == 0) {
            enhancedHelp.printGeneralHelp();
        } else {
            enhancedHelp.printHelpFor(args[0]);
        }
    }
}
