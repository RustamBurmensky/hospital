package com.epam.burmensky.hospital.web;

import com.epam.burmensky.hospital.web.command.Command;
import com.epam.burmensky.hospital.web.command.CommandContainer;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main servlet controller.
 *
 * @author Rustam Burmensky
 *
 */
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 5821043251707586157L;

    private static final Logger log = Logger.getLogger(Controller.class);

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processGet(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processPost(request, response);
    }

    /**
     * Main method of this controller.
     */
    private void processGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {

        log.debug("Controller starts");

        // extract command name from the request
        String commandName = request.getParameter("command");
        log.trace("Request parameter: command --> " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.getGet(commandName);
        log.trace("Obtained command --> " + command);

        // execute command and get forward address
        String forward = command.execute(request, response);
        log.trace("Forward address --> " + forward);

        log.debug("Controller finished, now go to forward address --> " + forward);

        // if the forward address is not null go to the address
        if (forward != null) {
            RequestDispatcher disp = request.getRequestDispatcher(forward);
            disp.forward(request, response);
        }
    }

    /**
     * Main method of this controller.
     */
    private void processPost(HttpServletRequest request,
                            HttpServletResponse response) throws IOException, ServletException {

        log.debug("Controller starts");

        // extract command name from the request
        String commandName = request.getParameter("command");
        log.trace("Request parameter: command --> " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.getPost(commandName);
        log.trace("Obtained command --> " + command);

        // execute command and get forward address
        String redirect = command.execute(request, response);
        log.trace("Redirect address --> " + redirect);

        log.debug("Controller finished, now go to redirect address --> " + redirect);

        // if the forward address is not null go to the address
        if (redirect != null) {
            response.sendRedirect(request.getContextPath() + redirect);
        }
    }

}
