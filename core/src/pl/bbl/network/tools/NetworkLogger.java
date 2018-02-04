package pl.bbl.network.tools;

public class NetworkLogger {
    private static LogType workingMode = LogType.INFO;

    public static void log(LogType logType, String message){
        System.out.println("[NETWORK_FRAMEWORK] " + message);
    }

    public static void changeWorkingMode(LogType logType){
        workingMode = logType;
    }
}
