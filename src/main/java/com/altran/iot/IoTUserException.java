package com.altran.iot;

import org.valuereporter.helper.StatusType;

/**
 * Created with IntelliJ IDEA.
 * User: bardl
 * Date: 20.03.13
 * Time: 10:54
 * To change this template use File | Settings | File Templates.
 */
public class IoTUserException extends IoTException {

    public IoTUserException(String msg, StatusType statusType) {
        super(msg, statusType);
    }

    public IoTUserException(String msg, Exception e, StatusType statusType) {
        super(msg, e, statusType);
    }
}
