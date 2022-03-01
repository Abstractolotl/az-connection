package de.az.ware.connection.packet;

import de.az.ware.connection.Connection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class RequestMapper {

    private Map<UUID, Map.Entry<Connection, Consumer<ResponsePacket>>> callbacks;

    public RequestMapper() {
        callbacks = new HashMap<>();
    }

    public void sendAndRegisterCallback(Connection connection, RequestPacket req, Consumer<ResponsePacket> callback) {
        callbacks.put(req.getRequestID(), Map.entry(connection, callback));
        connection.sendMessage(PacketParser.SerializePacket(req));
    }

    public void onResponsePacket(Connection connection, ResponsePacket res) {
        var entry = callbacks.get(res.getResponseID());
        if(entry == null) return;

        Connection con = entry.getKey();
        if(con != connection) return;

        Consumer<ResponsePacket> callback = entry.getValue();
        callback.accept(res);
    }

}
