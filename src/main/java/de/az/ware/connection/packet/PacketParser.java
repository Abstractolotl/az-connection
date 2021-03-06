package de.az.ware.connection.packet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.HashMap;

/**
 * Parses a String to a Packet. Packets need to be registered.
 */
public class PacketParser {

    private static final Gson gson = new Gson();
    public static String SerializePacket(Packet packet){
        var obj = gson.toJsonTree(packet).getAsJsonObject();
        if(obj.has("type")) {
            System.err.println("Tried Serializing Packet with property TYPE: " + obj.get("type").getAsString());
            obj.remove("type");
        }
        obj.addProperty("type", packet.getClass().getName());
        return gson.toJson(obj);
    }

    private final HashMap<String, Class<?>> packetClasses;

    public PacketParser(){
        packetClasses = new HashMap<>();
    }
    public void registerPacketClass(Class<? extends Packet> c){
        System.out.println("Calling Consumer with: " + c);
        String packetType = c.getName();

        if(packetClasses.containsKey(packetType)){
            System.err.println("PacketType already registered: " + packetType);
            return;
        }

        packetClasses.put(packetType, c);
    }

    public Packet createPacketFromJson(String jsonString){
        try{
            JsonElement json = gson.fromJson(jsonString, JsonElement.class);

            String packetType = json.getAsJsonObject().get("type").getAsString();
            json.getAsJsonObject().remove("type");

            Class<?> packetClass = packetClasses.get(packetType);
            if(packetClass == null) throw new RuntimeException("No Packet registered for Packet: " + packetType);
            return (Packet) gson.fromJson(json, packetClass);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}