package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.UserDao;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Delete user command.
 *
 * @author Rustam Burmensky
 *
 */
public class DeleteUserCommand extends Command {

    private static final long serialVersionUID = -338483477473700994L;

    private static final Logger log = Logger.getLogger(DeleteUserCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Delete User Command starts");

        String errorMessage = null;
        String redirect = Path.PAGE__ERROR_PAGE;

        String userIdString = request.getParameter("id");
        log.trace("Request parameter: id --> " + userIdString);

        int id;

        try {
            id = Integer.parseInt(userIdString);
        }
        catch (Exception ex) {
            errorMessage = "Wrong user identifier";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return redirect;
        }

        try {
            new UserDao().deleteUser(id);
        }
        catch (Exception e) {
            errorMessage = "Failed to delete user";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return redirect;
        }

        redirect = Path.COMMAND__LIST_USERS;

        log.debug("Commands finished");
        return redirect;
    }
}
