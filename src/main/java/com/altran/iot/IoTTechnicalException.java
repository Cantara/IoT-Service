package com.altran.iot;

import org.valuereporter.helper.StatusType;

/**
 * This class will identify that the root of these errors are tecnical, not user-created.
 */
public class IoTTechnicalException extends IoTException {

    public IoTTechnicalException(String msg, StatusType statusType) {
        super(msg, statusType);
    }

    public IoTTechnicalException(String msg, Exception e, StatusType statusType) {
        super(msg, e, statusType);
    }
}
