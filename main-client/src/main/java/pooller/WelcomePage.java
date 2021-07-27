package pooller;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import pooller.dto.pool.PoolMetaInfoDto;
import pooller.dto.uac.CabinetDto;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WelcomePage extends Composite {
    interface WelcomePageUiBinder extends UiBinder<HTMLPanel, WelcomePage> {}
    static WelcomePageUiBinder uiBinder = GWT.create(WelcomePageUiBinder.class);
    @UiField
    InlineHTML headerText;
    @UiField
    HTML description;
    @UiField
    InlineHTML questionsCount;
    @UiField
    TextBox email;

    private PoolingSurviveServiceAsync poolingSurviveService;

    PoolMetaInfoDto info;

    public WelcomePage(PoolMetaInfoDto info) {
        this.info = info;
        initWidget(uiBinder.createAndBindUi(this));
        Scheduler.get().scheduleDeferred(() -> {
            poolingSurviveService = GWT.create(PoolingSurviveService.class);
        });
        headerText.setText(info.getTitle());
        description.setHTML(info.getDescription());
        questionsCount.setText(String.valueOf(info.getQuestionCount()));
    }

    @UiHandler("startBtn")
    public void onStartBtnClick(ClickEvent event) {
        if (poolingSurviveService == null) {
            poolingSurviveService = GWT.create(PoolingSurviveService.class);
        }
        poolingSurviveService.invite(info, email.getValue(), new AsyncCallback<CabinetDto>() {
            @Override
            public void onFailure(Throwable caught) {
                Logger.getLogger("Start Page").log(Level.ALL, "Start btn error", caught);
            }

            @Override
            public void onSuccess(CabinetDto result) {
                Logger.getLogger("Start Page").log(Level.ALL, "Start btn " + result.getId());
            }
        });
    }
}
