package pooller;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import pooller.ui.PoolSurvive;

public class Maker implements EntryPoint {
    public void onModuleLoad() {
        PoolSurvive widget = new PoolSurvive();
        RootLayoutPanel.get().add(widget);
    }
}
