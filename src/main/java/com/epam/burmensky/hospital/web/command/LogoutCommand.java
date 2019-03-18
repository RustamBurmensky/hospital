package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Logout command.
 *
 * @author Rustam Burmensky
 *
 */
public class LogoutCommand extends Command {

    private static final long serialVersionUID = 827518230945256936L;

    private static final Logger log = Logger.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Logout Command starts");

        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();

        log.debug("Command finished");
        return Path.PAGE__LOGIN;
    }
}
