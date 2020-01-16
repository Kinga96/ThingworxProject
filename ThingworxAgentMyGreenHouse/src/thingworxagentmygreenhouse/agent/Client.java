package thingworxagentmygreenhouse.agent;

import com.thingworx.communications.client.ClientConfigurator;
import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.things.VirtualThing;
import com.thingworx.relationships.RelationshipTypes.ThingworxEntityTypes;
import com.thingworx.types.collections.ValueCollection;
import thingworxagentmygreenhouse.thing.MyGreenHouse;
import thingworxagentmygreenhouse.thing.GreenHouseTemplate;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kinga
 */
public class Client extends ConnectedThingClient {

    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    public Client(ClientConfigurator config) throws Exception {
        super(config);
    }

    public static void startConnection() {
        ClientConfigurator config = new ClientConfigurator();
        LOG.info("START");
        config.setUri("ws://127.0.0.1:8443/Thingworx/WS");
        
        config.setAppKey("6ac63f1c-209f-4e2b-be6c-bca85d7d1e28");

        
        config.ignoreSSLErrors(true);

        try {
            Client client = new Client(config);
            client.start();

            while (!client.getEndpoint().isConnected()) {
                Thread.sleep(1000L);
                LOG.info("WAIT");
            }

            MyGreenHouse[] var5;
            int var4 = (var5 = MyGreenHouse.values()).length;

            MyGreenHouse greenhouse;
            int var3;
            for (var3 = 0; var3 < var4; ++var3) {
                greenhouse = var5[var3];
                ValueCollection params = new ValueCollection();
                params.SetStringValue("ThingName", greenhouse.name);
                client.invokeService(ThingworxEntityTypes.Things, "GreenHouseHelper", "CreateThing", params, 3128);
            }

            var4 = (var5 = MyGreenHouse.values()).length;

            for (var3 = 0; var3 < var4; ++var3) {
                greenhouse = var5[var3];
                GreenHouseTemplate thing1 = new GreenHouseTemplate(greenhouse.name, "", client);
                client.bindThing(thing1);
            }

            while (true) {
                do {
                    if (client.isShutdown()) {
                        LOG.info("END");
                        return;
                    }
                } while (!client.isConnected());

                LOG.info("SEND");
                Iterator var10 = client.getThings().values().iterator();

                while (var10.hasNext()) {
                    VirtualThing thing = (VirtualThing) var10.next();

                    try {
                        thing.processScanRequest();
                    } catch (Exception var7) {
                        System.out.println("Error Processing Scan Request for [" + thing.getName() + "] : " + var7.getMessage());
                    }
                }

                LOG.info("SLEEP");
                Thread.sleep(60000L);
            }
        } catch (Exception var8) {
            LOG.info("ERROR");
            var8.printStackTrace();
        }
    }
}
