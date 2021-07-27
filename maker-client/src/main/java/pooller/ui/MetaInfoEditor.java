package pooller.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import pooller.dto.pool.PoolMetaInfoDto;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MetaInfoEditor extends Composite implements Editor<PoolMetaInfoDto> {

    private Scheduler.ScheduledCommand onClose;
    DialogBox dialogBox;
    public void showDialog() {
        DialogBox.CaptionImpl captionWidget = new DialogBox.CaptionImpl();
        captionWidget.setText("New pool");
        dialogBox = new DialogBox(captionWidget);
        dialogBox.add(this);
        dialogBox.show();
        dialogBox.center();

    }

    interface MetaInfoEditorUiBinder extends UiBinder<FormPanel, MetaInfoEditor> {}
    private static MetaInfoEditorUiBinder uiBinder = GWT.create(MetaInfoEditorUiBinder.class);
    private static Logger LOG = Logger.getLogger("new dialog");
    @UiField
    NumberLabel<Long> idEditor;
    @UiField
    RichTextArea descriptionEditor;
    @UiField
    DateLabel createdEditor;
    @UiField
    TextBox titleEditor;
    @UiField
    Button createBtn;

    interface Driver extends SimpleBeanEditorDriver<PoolMetaInfoDto, MetaInfoEditor> {}
    Driver driver = GWT.create(Driver.class);

    public MetaInfoEditor() {
        initWidget(uiBinder.createAndBindUi(this));
        driver.initialize(this);
    }

    public void initData(PoolMetaInfoDto info, Scheduler.ScheduledCommand onClose) {
        this.onClose = onClose;
        if (info != null) {
            driver.edit(info);
            descriptionEditor.setHTML(info.getDescription());
        }
    }

    @UiHandler("createBtn")
    public void onCreateBtn(ClickEvent event) {
        PoolMetaInfoDto flush = driver.flush();
        flush.setDescription(descriptionEditor.getHTML());
        flush.setCreated(null);
        PoolSurvive.poolingRepository.savePool(flush, new AsyncCallback<PoolMetaInfoDto>() {
            @Override
            public void onFailure(Throwable throwable) {
                LOG.log(Level.ALL, throwable, () -> "Unable to save pool info");
            }

            @Override
            public void onSuccess(PoolMetaInfoDto poolMetaInfoDto) {
                Scheduler.get().scheduleDeferred(onClose);
                dialogBox.hide(true);
            }
        });
    }

    @UiHandler("closeBtn")
    public void onCloseBtn(ClickEvent event) {
        dialogBox.hide(true);
    }

    @UiHandler("openLink")
    public void linkClickEvent(ClickEvent event) {
        Window.open("/p/" + idEditor.getValue(), "_blank", null);
    }

}
