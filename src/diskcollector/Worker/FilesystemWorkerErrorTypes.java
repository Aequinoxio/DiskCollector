/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diskcollector.Worker;

/**
 *
 * @author utente
 */
public enum FilesystemWorkerErrorTypes {
    OK(0,"Ok"),
    WRONG_PASSWORD (1,"Wrong password"),
    ERROR_ENCRYPTING_FILE (11,"Error encrypting file"),
    ERROR_READING_FILE (2,"Error reading file"),
    ERROR_WRITING_FILE (3,"Error writing file"),
    IO_ERROR (4,"IO error"),
    ERROR_LOADING_CLASS (5,"Error loading class");
    
    private final int code;
    private final String  message;
    
    private FilesystemWorkerErrorTypes(int code, String message){
        this.code=code;
        this.message=message;
    }

    /**
     *
     * @return
     */
    public int getCode(){
        return code;
    }
    
    /**
     *
     * @return
     */
    public String getMessage(){
        return message;
    }
}
