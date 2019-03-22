package com.epam.burmensky.hospital.web.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Abstract holder for the URL, request and response received as command execution result.
 *
 * @author Rustam Burmensky
 *
 */
public abstract class CommandResult {

    protected String url;

    protected HttpServletRequest request;

    protected HttpServletResponse response;

    public CommandResult(String url, HttpServletRequest request, HttpServletResponse response) {
        this.url = url;
        this.request = request;
        this.response = response;
    }

    public abstract void proceed();

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
