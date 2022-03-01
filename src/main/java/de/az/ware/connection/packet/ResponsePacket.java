package de.az.ware.connection.packet;

import java.util.UUID;

public class ResponsePacket {

    private UUID responseID;

    public ResponsePacket() {
    }

    public ResponsePacket(UUID responseID) {
        this.responseID = responseID;
    }

    public UUID getResponseID() {
        return responseID;
    }

    public void setResponseID(UUID responseID) {
        this.responseID = responseID;
    }

}
