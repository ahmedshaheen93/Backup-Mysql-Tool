package com.shaheen;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

public class DataBaseBackup {
    static Logger log = Logger.getLogger(DataBaseBackup.class.getName());

    public static boolean backupDataWithDatabase(String dumpExePath, String host,
                                                 String port, String user, String password,
                                                 String database, String backupPath) {
        boolean status = false;
        try {

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            String filepath = "backup(with_DB)-" + database + "-" + host + "-(" + dateFormat.format(date) + ").sql";

            String batchCommand;
            if (!password.equals("")) {
                batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --password=" + password + " --add-drop-database -B " + database + " -r \"" + backupPath + "" + filepath + "\"";
            } else {
                batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --add-drop-database -B " + database + " -r \"" + backupPath + "" + filepath + "\"";
            }
            System.out.println("batchCommand " + batchCommand);
            Process runtimeProcess = Runtime.getRuntime().exec(batchCommand);

            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                status = true;
                log.info("Backup created successfully for with DB " + database + " in " + host + ":" + port);
            } else {
                status = false;
                log.info("Could not create the backup for with DB " + database + " in " + host + ":" + port);
            }

        } catch (Exception e) {
            log.info(e.getCause().toString());

        }
        return status;
    }

    public static boolean restoreDataBaseFromSqlFile(String restorePath, String mysqlPath, String dbUser, String dbPass) {
        try {
            String[] executeCmd = new String[]{mysqlPath, "--user=" + dbUser, "--password=" + dbPass, "-e", "source " + restorePath};
            System.out.println(Arrays.toString(executeCmd));

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();

            /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
            return processComplete == 0;

        } catch (Exception ex) {
            log.info(ex.getCause().toString());

        }

        return false;
    }
}
