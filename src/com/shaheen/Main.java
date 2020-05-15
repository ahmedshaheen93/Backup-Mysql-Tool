package com.shaheen;

public class Main {

    public static void main(String[] args) {
        //test backup tool
//
        // dump tool founded on mysql path with name
        // mysqldump.exe on windows
        // or mysqldump on linux
        String mysqlDump = "mysqldump";
        // host to connect to
        String host = "localhost";
        // mysql port
        String port = "3306";
        //mysql username
        String user = "root";
        //mysql password
        String password = "root";
        // database to be backup
        String databaseName = "mydb";
        // directory to save backup file
        String backupPath ="/tmp/";
	    DataBaseBackup.backupDataWithDatabase(mysqlDump,host,port,
                user,password,databaseName,backupPath);

        // test restore tool
        // file to restore
        String sqlFilePath = "/tmp/backup(with_DB)-mydb-localhost-(16-05-2020).sql";
        // mysql path
        String mysql ="mysql";
        DataBaseBackup.restoreDataBaseFromSqlFile(sqlFilePath,mysql,user,password);


    }
}
