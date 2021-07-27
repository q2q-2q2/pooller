package pooller;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import pooller.dto.pool.*;
import pooller.repository.SurviveRepositoryService;
import pooller.repository.SurviveRepositoryServiceAsync;

public class QuestionsLayout extends Composite {
    interface QuestionsUiBinder extends UiBinder<DockLayoutPanel, QuestionsLayout> {}
    static QuestionsUiBinder binder = GWT.create(QuestionsUiBinder.class);
    @UiField
    HeadingElement title;
    @UiField
    DockLayoutPanel questionPanel;
    PoolMetaInfoDto poolMetaInfo;
    int currentQuestionIdx = 0;
    @UiField
    Button nextBtn;
    @UiField
    Button prevBtn;
    @UiField
    Button finishBtn;
    @UiField
    Element questionHeader;
    @UiField
    VerticalPanel questionVariants;
    @UiField
    Element progress;

    SurviveRepositoryServiceAsync surviveRepositoryService = GWT.create(SurviveRepositoryService.class);
    private final Storage localStorageIfSupported = Storage.getLocalStorageIfSupported();

    SurviveDto surviveDto;

    public QuestionsLayout(PoolMetaInfoDto infoDto) {
        poolMetaInfo = infoDto;
        surviveDto = new SurviveDto();
        surviveDto.setPoolMainInfo(infoDto);
        surviveRepositoryService.startSurvive(surviveDto, new AsyncCallback<SurviveDto>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(SurviveDto result) {
                surviveDto = result;
                questionPanel.setVisible(true);
            }
        });
        initWidget(binder.createAndBindUi(this));
        title.setInnerText(infoDto.getTitle());
        prevBtn.setEnabled(false);
        questionPanel.setVisible(false);
        initQuestionPanel(poolMetaInfo.getQuestionIds().get(currentQuestionIdx));
    }

    private void initQuestionPanel(Long aLong) {
        progress.setInnerText("" + (currentQuestionIdx + 1) + "/" + poolMetaInfo.getQuestionIds().size());
        App.poolingRepository.getQuestion(aLong, new AsyncCallback<PoolQuestionDto>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(PoolQuestionDto result) {
                disableButtons();
                questionHeader.setInnerText(result.getQuestionText());
                questionVariants.clear();

                for (QuestionVariantDto variantDto : result.getVariants()) {
                    CheckBox w;
                    if (result.getQuestionType() == PoolQuestionType.MANY){
                        w = new CheckBox(variantDto.getVariantText());
                    } else {
                        w = new RadioButton(result.getId().toString(), variantDto.getVariantText());
                    }
                    w.setLayoutData(variantDto);
                    w.addValueChangeHandler(event -> {
                        Object data = ((CheckBox) event.getSource()).getLayoutData();
                        if (data instanceof QuestionVariantDto) {
                            QuestionVariantDto dto = (QuestionVariantDto) data;
                            localStorageIfSupported.setItem(dto.getId().toString(), event.getValue().toString());
                            SurviveVariantDto surviveVariantDto = new SurviveVariantDto();
                            surviveRepositoryService.setVariant(surviveVariantDto, new AsyncCallback<Long>() {
                                @Override
                                public void onFailure(Throwable caught) {

                                }

                                @Override
                                public void onSuccess(Long result) {
                                    if (result != null) {
                                        localStorageIfSupported.setItem(variantDto.getId().toString(), result.toString());
                                    }
                                    enableButtons();
                                }
                            });
                        }
                    });
                    w.setValue(Boolean.parseBoolean(localStorageIfSupported.getItem(variantDto.getId().toString())));
                    if (w.getValue()) {
                        enableButtons();
                        lastState = true;
                    }
                    questionVariants.add(w);
                }
            }
        });

    }

    boolean lastState = true;

    void disableButtons() {
        if (lastState) {
            lastState = nextBtn.isEnabled();
        }
        nextBtn.setEnabled(false);
    }

    void enableButtons() {
        nextBtn.setEnabled(lastState);
    }

    @UiHandler("nextBtn")
    public void onNextBtn(ClickEvent event) {
        currentQuestionIdx++;
        initQuestionPanel(poolMetaInfo.getQuestionIds().get(currentQuestionIdx));
        if (currentQuestionIdx >= poolMetaInfo.getQuestionIds().size() - 1) {
            nextBtn.setEnabled(false);
        }
        if (currentQuestionIdx > 0) {
            prevBtn.setEnabled(true);
        }
    }

    @UiHandler("prevBtn")
    public void onPrevBtn(ClickEvent event) {
        currentQuestionIdx--;
        initQuestionPanel(poolMetaInfo.getQuestionIds().get(currentQuestionIdx));
        if (currentQuestionIdx <= 0) {
            prevBtn.setEnabled(false);
        }
        if (currentQuestionIdx < poolMetaInfo.getQuestionIds().size() - 1) {
            nextBtn.setEnabled(true);
        }
    }

}
