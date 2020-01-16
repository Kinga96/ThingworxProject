package thingworxagentmygreenhouse.thing;

import com.thingworx.communications.client.ConnectedThingClient;
import com.thingworx.communications.client.things.VirtualThing;
import com.thingworx.metadata.annotations.ThingworxPropertyDefinition;
import com.thingworx.metadata.annotations.ThingworxPropertyDefinitions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Kinga
 */

@ThingworxPropertyDefinitions(
    properties = {@ThingworxPropertyDefinition(
    name = "open",
    description = "",
    baseType = "BOOLEAN",
    aspects = {"dataChangeType:ALWAYS", "dataChangeThreshold:0", "cacheTime:0", "isPersistent:FALSE", "isReadOnly:FALSE", "pushType:ALWAYS", "isFolded:FALSE", "defaultValue:0"}
), @ThingworxPropertyDefinition(
    name = "intensity",
    description = "",
    baseType = "NUMBER",
    aspects = {"dataChangeType:ALWAYS", "dataChangeThreshold:0", "cacheTime:0", "isPersistent:FALSE", "isReadOnly:FALSE", "pushType:ALWAYS", "isFolded:FALSE", "defaultValue:0"}
), @ThingworxPropertyDefinition(
    name = "temperature",
    description = "",
    baseType = "NUMBER",
    aspects = {"dataChangeType:ALWAYS", "dataChangeThreshold:0", "cacheTime:0", "isPersistent:FALSE", "isReadOnly:FALSE", "pushType:ALWAYS", "isFolded:FALSE", "defaultValue:0"}
), @ThingworxPropertyDefinition(
    name = "switch",
    description = "",
    baseType = "BOOLEAN",
    aspects = {"dataChangeType:ALWAYS", "dataChangeThreshold:0", "cacheTime:0", "isPersistent:FALSE", "isReadOnly:FALSE", "pushType:ALWAYS", "isFolded:FALSE", "defaultValue:0"}
), @ThingworxPropertyDefinition(
    name = "hydration",
    description = "",
    baseType = "NUMBER",
    aspects = {"dataChangeType:ALWAYS", "dataChangeThreshold:0", "cacheTime:0", "isPersistent:FALSE", "isReadOnly:FALSE", "pushType:ALWAYS", "isFolded:FALSE", "defaultValue:0"}
), @ThingworxPropertyDefinition(
    name = "watts",
    description = "",
    baseType = "NUMBER",
    aspects = {"dataChangeType:ALWAYS", "dataChangeThreshold:0", "cacheTime:0", "isPersistent:FALSE", "isReadOnly:FALSE", "pushType:ALWAYS", "isFolded:FALSE", "defaultValue:0"}
)}
)

public class GreenHouseTemplate extends VirtualThing implements Runnable{
    private static final Logger LOG = LoggerFactory.getLogger(GreenHouseTemplate.class);
    private static final String OPEN_FIELD = "open";
    private static final String INTENSITY_FIELD = "intensity";
    private static final String TEMPERATURE_FIELD = "temperature";
    private static final String SWITCH_FIELD = "switch";
    private static final String HYDRATION_FIELD = "hydration";
    private static final String WATTS_FIELD = "watts";
    private Boolean open;
    private Double intensity;
    private Double temperature;
    private Boolean switch_ventilator;
    private Double hydration;
    private Double watts;
    private String thingName = null;

    public GreenHouseTemplate(String name, String description, ConnectedThingClient client) {
        super(name, description, client);
        this.getThingShape();
        this.getBindingName();
        this.thingName = name;

        try {
            super.initializeFromAnnotations();
        } catch (Exception var5) {
            LOG.error("Not work", var5);
        }

        this.init();
    }

    public void synchronizeState() {
        super.synchronizeState();
        super.syncProperties();
    }

    private void init() {
        this.open = new Boolean(true);
        this.intensity = new Double(40.0D);
        this.temperature = new Double(32.8D);
        this.switch_ventilator = new Boolean(false);
        this.hydration = new Double(45.0D);
        this.watts = new Double(60.0D);
    }

    public void run() {
        try {
            Thread.sleep(1000L);
            this.getClient().shutdown();
        } catch (Exception var2) {
            LOG.error("Error " + this.thingName, var2);
        }

    }

    public void processScanRequest() throws Exception {
        this.intensity = this.intensity + (Math.random() * 20.0D - 10.0D);
        if (this.intensity < 0.0D) {
            this.intensity = new Double(0.0D);
        }

        if (this.intensity > 100.0D) {
            this.intensity = new Double(100.0D);
        }

        this.open = new Boolean(Math.random() < 0.5D);
       this.switch_ventilator = new Boolean(Math.random() < 0.5D);

          this.temperature = this.temperature + (Math.random() * 20.0D - 10.0D);
        if (this.temperature < 0.0D) {
            this.temperature = new Double(0.0D);
        }

        if (this.temperature > 100.0D) {
            this.temperature = new Double(100.0D);
        }
        
        this.hydration = this.hydration + (Math.random() * 20.0D - 10.0D);
        if (this.hydration < 0.0D) {
            this.hydration = new Double(0.0D);
        }

        if (this.hydration > 100.0D) {
            this.hydration = new Double(100.0D);
        }
        
        this.watts = this.watts + (Math.random() * 20.0D - 10.0D);
        if (this.watts < 0.0D) {
            this.watts = new Double(0.0D);
        }

        if (this.watts > 100.0D) {
            this.watts = new Double(100.0D);
        }
        
        this.setOpen();
        this.setIntensity();
        this.setTemperature();
        this.setSwitch();
        this.setHydration();
        this.setWatts();
        this.updateSubscribedProperties(5000);
    }

    public void setOpen() throws Exception {
        this.setProperty("Open", this.open);
    }

    public void setIntensity() throws Exception {
        this.setProperty("Intensity", this.intensity);
    }

    public void setTemperature() throws Exception {
        this.setProperty("Temperature", this.temperature);
    }
    
    public void setSwitch() throws Exception {
        this.setProperty("Switch", this.switch_ventilator);
    }
    
    public void setHydration() throws Exception {
        this.setProperty("Hydration", this.hydration);
    }
    
    public void setWatts() throws Exception {
        this.setProperty("Watts", this.watts);
    }
}
