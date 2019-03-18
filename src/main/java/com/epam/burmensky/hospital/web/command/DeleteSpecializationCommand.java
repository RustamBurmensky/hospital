package com.epam.burmensky.hospital.web.command;

import com.epam.burmensky.hospital.Path;
import com.epam.burmensky.hospital.db.SpecializationDao;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Delete specialization command.
 *
 * @author Rustam Burmensky
 *
 */
public class DeleteSpecializationCommand extends Command {

    private static final long serialVersionUID = 777907820350914695L;

    private static final Logger log = Logger.getLogger(DeleteSpecializationCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Delete Specialization Command starts");

        String errorMessage = null;
        String redirect = Path.PAGE__ERROR_PAGE;

        String speicalizationIdString = request.getParameter("id");
        log.trace("Request parameter: id --> " + speicalizationIdString);

        int id;

        try {
            id = Integer.parseInt(speicalizationIdString);
        }
        catch (Exception ex) {
            errorMessage = "Wrong specialization identifier";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return redirect;
        }

        try {
            new SpecializationDao().deleteSpecialization(id);
        }
        catch (Exception e) {
            errorMessage = "Failed to delete specialization";
            request.setAttribute("errorMessage", errorMessage);
            log.error("errorMessage --> " + errorMessage);
            return redirect;
        }

        redirect = Path.COMMAND__LIST_SPECIALIZATIONS;

        log.debug("Commands finished");
        return redirect;
    }
}
