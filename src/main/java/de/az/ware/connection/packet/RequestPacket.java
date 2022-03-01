package de.az.ware.connection.packet;

import java.util.UUID;

public class RequestPacket implements Packet {

    private UUID requestID;

    public RequestPacket() {
    }

    public RequestPacket(UUID requestID) {
        this.requestID = requestID;
    }

    public UUID getRequestID() {
        return requestID;
    }

    public void setRequestID(UUID requestID) {
        this.requestID = requestID;
    }

}
