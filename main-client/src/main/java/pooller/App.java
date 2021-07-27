package pooller;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import pooller.dto.pool.PoolMetaInfoDto;
import pooller.repository.PoolingRepositoryService;
import pooller.repository.PoolingRepositoryServiceAsync;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App implements EntryPoint {
    static Logger LOG = Logger.getLogger("pooller");
    public static PoolingRepositoryServiceAsync poolingRepository = GWT.create(PoolingRepositoryService.class);
    public void onModuleLoad() {
        LoadingPage w = new LoadingPage();
        RootPanel.get().add(w);
        w.center();
        w.show();
        String[] parts = Window.Location.getPath().split("/");
        LOG.info(Arrays.toString(parts));
        String pool = "";
        if (parts != null && parts.length > 1) {
            if ("p".equals(parts[1])) {
                pool = parts[2];
                try {
                    Long id = Long.parseLong(pool);
                    poolingRepository.getPool(id, new AsyncCallback<PoolMetaInfoDto>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            w.hide();
                            LOG.log(Level.ALL, "loading pool by " + id, caught);
                            RootPanel.get().clear();
                            RootPanel.get().add(new ErrorPage(caught).getWidget());
                        }

                        @Override
                        public void onSuccess(PoolMetaInfoDto result) {
                            w.hide();
                            Window.setTitle(result.getTitle());
                            RootPanel.get().clear();
                            RootLayoutPanel.get().clear();
                            RootLayoutPanel.get().add(new QuestionsLayout(result));
                        }
                    });
                } catch (NumberFormatException e) {
                    w.hide();
                    LOG.log(Level.ALL, "main entry by " + pool, e);
                    RootPanel.get().clear();
                    RootPanel.get().add(new ErrorPage(e).getWidget());
                }
            } else if ("pi".equals(parts[1])) {
                pool = parts[2];
                try {
                    Long id = Long.parseLong(pool);
                    poolingRepository.getPool(id, new AsyncCallback<PoolMetaInfoDto>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            w.hide();
                            LOG.log(Level.ALL, "loading pool by " + id, caught);
                            RootPanel.get().clear();
                            RootPanel.get().add(new ErrorPage(caught).getWidget());
                        }

                        @Override
                        public void onSuccess(PoolMetaInfoDto result) {
                            w.hide();
                            Window.setTitle(result.getTitle());
                            RootPanel.get().clear();
                            RootLayoutPanel.get().clear();
                            RootLayoutPanel.get().add(new WelcomePage(result));
                        }
                    });
                } catch (NumberFormatException e) {
                    w.hide();
                    LOG.log(Level.ALL, "main entry by " + pool, e);
                    RootPanel.get().clear();
                    RootPanel.get().add(new ErrorPage(e).getWidget());
                }
            }
        }

    }
}
