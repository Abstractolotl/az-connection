package de.az.ware.connection.packet;

import de.az.ware.connection.Connection;

public class ConnectionPacketListener extends DelegatedPacketListener<Connection> {

    /**
     * @param parser if provided registers packets classes to parser
     * @param handler
     */
    public ConnectionPacketListener(PacketParser parser, PacketHandler handler) {
        super(Connection.class, parser, handler);
    }

    public ConnectionPacketListener(PacketHandler handler) {
        super(Connection.class, handler);
    }

}
