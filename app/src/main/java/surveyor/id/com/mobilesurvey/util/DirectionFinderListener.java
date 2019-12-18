package surveyor.id.com.mobilesurvey.util;

/**
 * Created by Vigaz on 9/17/2017.
 */

import java.util.List;

import surveyor.id.com.mobilesurvey.modal.Route;

/**
 * Created by Vigaz on 4/21/2017.
 */
public interface DirectionFinderListener {


    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}