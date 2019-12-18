package surveyor.id.com.mobilesurvey.modal;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.nearby.messages.Distance;

import java.util.List;

import javax.xml.datatype.Duration;

public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}
