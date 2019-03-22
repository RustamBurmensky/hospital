package com.epam.burmensky.hospital.web.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Main interface for the Command pattern implementation.
 *
 * @author Rustam Burmensky
 *
 */
public abstract class Command implements Serializable {

    private static final long serialVersionUID = 2601735044533103682L;

    /**
     * Execution method for command.
     * @return Address to go once the command is executed.
     */
    public abstract CommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException;

    @Override
    public final String toString() {
        return getClass().getSimpleName();
    }

}
