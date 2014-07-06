package com.altran.iot;

import com.altran.iot.helper.StatusType;

/**
 *
 */
public class IoTUserException extends IoTException {

    public IoTUserException(String msg, StatusType statusType) {
        super(msg, statusType);
    }

    public IoTUserException(String msg, Exception e, StatusType statusType) {
        super(msg, e, statusType);
    }
}
