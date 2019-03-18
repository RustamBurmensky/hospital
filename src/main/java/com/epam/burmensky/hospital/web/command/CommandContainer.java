package com.epam.burmensky.hospital.web.command;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Holder for all commands.<br/>
 *
 * @author Rustam Burmensky
 *
 */
public class CommandContainer {

    private static final Logger log = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> getCommands = new TreeMap<>();
    private static Map<String, Command> postCommands = new TreeMap<>();

    static {
        // GET
        // common commands
        getCommands.put("logout", new LogoutCommand());
        getCommands.put("showSettings", new ShowSettingsCommand());
        getCommands.put("noCommand", new NoCommand());

        // admin commands
        getCommands.put("listUsers", new ListUsersCommand());
        getCommands.put("listSpecializations", new ListSpecializationsCommand());
        getCommands.put("addEditUserGet", new AddEditUserGetCommand());
        getCommands.put("addEditPatientGet", new AddEditPatientGetCommand());
        getCommands.put("addEditSpecializationGet", new AddEditSpecializationGetCommand());
        getCommands.put("listAppointments", new ListAppointmentsCommand());
        getCommands.put("showDischarge", new ShowDischargeCommand());
        getCommands.put("addEditDischargeGet", new AddEditDischargeGetCommand());

        // physician commands
        getCommands.put("listPatients", new ListPatientsCommand());
        getCommands.put("listCardRecords", new ListCardRecordsCommand());
        getCommands.put("addEditCardRecordGet", new AddEditCardRecordGetCommand());

        // POST
        // common commands
        postCommands.put("login", new LoginCommand());
        postCommands.put("updateSettings", new UpdateSettingsCommand());
        postCommands.put("noCommand", new NoCommand());

        // admin commands
        postCommands.put("addEditUserPost", new AddEditUserPostCommand());
        postCommands.put("deleteUser", new DeleteUserCommand());
        postCommands.put("addEditPatientPost", new AddEditPatientPostCommand());
        postCommands.put("deletePatient", new DeletePatientCommand());
        postCommands.put("addEditSpecializationPost", new AddEditSpecializationPostCommand());
        postCommands.put("deleteSpecialization", new DeleteSpecializationCommand());
        postCommands.put("addAppointmentPost", new AddAppointmentPostCommand());
        postCommands.put("deleteAppointment", new DeleteAppointmentCommand());
        postCommands.put("addEditDischargePost", new AddEditDischargePostCommand());
        postCommands.put("deleteDischarge", new DeleteDischargeCommand());

        // physician commands
        postCommands.put("addEditCardRecordPost", new AddEditCardRecordPostCommand());
        postCommands.put("deleteCardRecord", new DeleteCardRecordCommand());

        log.debug("Command container was successfully initialized");
        log.trace("Number of GET commands --> " + getCommands.size());
        log.trace("Number of POST commands --> " + postCommands.size());
    }

    /**
     * Returns GET command by name.
     *
     * @param commandName
     *          GET command name.
     * @return GET command.
     */
    public static Command getGet(String commandName) {
        return getCommand(commandName, getCommands);
    }

    /**
     * Returns POST command by name.
     *
     * @param commandName
     *          POST command name.
     * @return POST command.
     */
    public static Command getPost(String commandName) {
        return getCommand(commandName, postCommands);
    }

    /**
     * Returns command from the map by name.
     *
     * @param commandName
     *          command name.
     * @param commands
     *          commands map.
     * @return command.
     */
    private static Command getCommand(String commandName, Map<String, Command> commands) {
        if (commandName == null || !commands.containsKey(commandName)) {
            log.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }

}
