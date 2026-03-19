package org.freedesktop.dbus.connections.impl;

import org.freedesktop.dbus.connections.BusAddress;
import org.freedesktop.dbus.connections.config.ReceivingServiceConfig;
import org.freedesktop.dbus.connections.config.TransportConfig;
import org.freedesktop.dbus.exceptions.DBusException;

public class AndroidDBusBuilder extends BaseConnectionBuilder<AndroidDBusBuilder, DBusConnection> {

    private final String manualMachineId;

    private AndroidDBusBuilder(BusAddress _address, String _machineId) {
        super(AndroidDBusBuilder.class, _address);
        this.manualMachineId = _machineId;
    }

    // Статический метод для создания билдера с фейковым ID
    public static AndroidDBusBuilder forAddress(String _address) {
        String fakeId = "1234567890abcdef1234567890abcdef";
        return new AndroidDBusBuilder(BusAddress.of(_address), fakeId);
    }

    @Override
    public DBusConnection build() throws DBusException {
        ReceivingServiceConfig rcvSvcCfg = buildThreadConfig();
        TransportConfig transportCfg = buildTransportConfig();
        ConnectionConfig connectionConfig = getConnectionConfig();

        DBusConnection c = new DBusConnection(
                false,  // boolean _shared
                manualMachineId,    // String _machineId
                connectionConfig,             // ConnectionConfig _conCfg
                transportCfg,       // TransportConfig _transportCfg
                rcvSvcCfg                 // ReceivingServiceConfig _rsCfg
        );

        c.connectImpl();

        return c;
    }


}
