package com.epam.burmensky.hospital.web;

import com.epam.burmensky.hospital.web.command.Command;
import com.epam.burmensky.hospital.web.command.CommandContainer;
import com.epam.burmensky.hospital.web.command.CommandResult;
import org.apache.log4j.Logger;

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
     * Method to process GET requests controller.
     */
    private void processGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {

        log.debug("Controller starts");

        // extract command name from the request
        String commandName = request.getParameter("command");
        log.trace("Request parameter: command --> " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.getGet(commandName);
        executeCommand(request, response, command);
    }

    /**
     * Method to process POST requests.
     */
    private void processPost(HttpServletRequest request,
                            HttpServletResponse response) throws IOException, ServletException {

        log.debug("Controller starts");

        // extract command name from the request
        String commandName = request.getParameter("command");
        log.trace("Request parameter: command --> " + commandName);

        // obtain command object by its name
        Command command = CommandContainer.getPost(commandName);
        executeCommand(request, response, command);
    }

    /**
     * Method to execute obtained command.
     */
    private void executeCommand(HttpServletRequest request, HttpServletResponse response, Command command) throws IOException, ServletException {
        log.trace("Obtained command --> " + command);

        // execute command and get result
        CommandResult commandResult = command.execute(request, response);
        log.trace("Command result --> " + commandResult);

        log.debug("Controller finished, now go to address --> " + commandResult);

        // if the forward address is not null go to the address
        commandResult.proceed();
    }


}
