package pooller.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.LoadingStateChangeEvent;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SingleSelectionModel;
import pooller.PoolQuestionType;
import pooller.dto.PageDto;
import pooller.dto.pool.PoolMetaInfoDto;
import pooller.dto.pool.PoolQuestionDto;
import pooller.dto.pool.QuestionVariantDto;
import pooller.repository.PoolingRepositoryService;
import pooller.repository.PoolingRepositoryServiceAsync;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PoolSurvive extends Composite {
    interface PoolSurviveUiBinder extends UiBinder<DockLayoutPanel, PoolSurvive> {}
    private static PoolSurviveUiBinder uiBinder = GWT.create(PoolSurviveUiBinder.class);
    public static final PoolingRepositoryServiceAsync poolingRepository = GWT.create(PoolingRepositoryService.class);
    private static Logger LOG = Logger.getLogger("maker");
    @UiField
    DataGrid<PoolMetaInfoDto> poolGrid;
    @UiField
    DataGrid<PoolQuestionDto> questionsGrid;
    @UiField
    DataGrid<QuestionVariantDto> variantsGrid;
    @UiField
    Button addQuestionBtn;
    @UiField
    Button addQuestionVariantBtn;
    @UiField
    Button editQuestionBtn;
    @UiField
    Button removeQuestionBtn;
    @UiField
    Button editQuestionVariantBtn;
    @UiField
    Button deleteQuestionVariantBtn;
    @UiField
    SimplePager poolGridPager;
    @UiField
    MetaInfoEditor poolInfoPanel;

    public PoolSurvive() {
        initWidget(uiBinder.createAndBindUi(this));
        poolGrid.addColumn(new TextColumn<PoolMetaInfoDto>() {
            @Override
            public String getValue(PoolMetaInfoDto poolMetaInfoDto) {
                if (poolMetaInfoDto == null) {
                    return "";
                }
                return String.valueOf(poolMetaInfoDto.getId());
            }
        });
        poolGrid.setColumnWidth(poolGrid.getColumnCount() - 1, 4.0, Style.Unit.EM);
        poolGrid.addColumn(new TextColumn<PoolMetaInfoDto>() {
            @Override
            public String getValue(PoolMetaInfoDto poolMetaInfoDto) {
                if (poolMetaInfoDto == null) {
                    return "";
                }
                return poolMetaInfoDto.getTitle() + " (" + poolMetaInfoDto.getQuestionCount() + ")";
            }
        });
        poolGrid.setColumnWidth(poolGrid.getColumnCount() - 1, 20.0, Style.Unit.EM);
        poolGrid.addColumn(new TextColumn<PoolMetaInfoDto>() {
            @Override
            public String getValue(PoolMetaInfoDto poolMetaInfoDto) {
                if (poolMetaInfoDto == null || poolMetaInfoDto.getCreated() == null) {
                    return "";
                }
                return DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM).format(poolMetaInfoDto.getCreated(), TimeZone.createTimeZone(3));
            }
        });
        poolGrid.addRangeChangeHandler(rangeEvent -> {
            loadRange(poolGrid.getVisibleRange());
        });
        poolGrid.setSelectionModel(new SingleSelectionModel<PoolMetaInfoDto>());
        poolGrid.getSelectionModel().addSelectionChangeHandler(event -> {
            PoolMetaInfoDto selected = ((SingleSelectionModel<PoolMetaInfoDto>) poolGrid.getSelectionModel()).getSelectedObject();
            if (selected != null) {
                poolInfoPanel.initData(selected, null);
                loadQuestions(selected.getId());
                addQuestionBtn.setEnabled(true);
            } else {
                addQuestionBtn.setEnabled(false);
            }
        });
        poolGrid.setEmptyTableWidget(new HTML("No pools available"));
        questionsGrid.setEmptyTableWidget(new HTML("No questions available"));
        variantsGrid.setEmptyTableWidget(new HTML("No variants available"));
        questionsGrid.addColumn(new TextColumn<PoolQuestionDto>() {
            @Override
            public String getValue(PoolQuestionDto poolQuestionDto) {
                return poolQuestionDto.getQuestionText();
            }
        });
        questionsGrid.setRowData(0, new ArrayList<>());
        questionsGrid.setRowCount(0);
        questionsGrid.setSelectionModel(new SingleSelectionModel<PoolQuestionDto>());
        questionsGrid.getSelectionModel().addSelectionChangeHandler(event -> {
            PoolQuestionDto selected = ((SingleSelectionModel<PoolQuestionDto>) questionsGrid.getSelectionModel()).getSelectedObject();
            if (selected != null) {
                variantsGrid.setRowData(selected.getVariants());
                variantsGrid.redraw();
                addQuestionVariantBtn.setEnabled(true);
                editQuestionBtn.setEnabled(true);
            } else {
                addQuestionVariantBtn.setEnabled(false);
                editQuestionBtn.setEnabled(false);
            }
        });
        variantsGrid.addColumn(new TextColumn<QuestionVariantDto>() {
            @Override
            public String getValue(QuestionVariantDto questionVariantDto) {
                return questionVariantDto.getVariantText();
            }
        });
        variantsGrid.setRowData(0, new ArrayList<>());
        variantsGrid.setRowCount(0);
        loadRange(null);
    }

    private void loadRange(@Nullable Range range) {
        int start = range == null ? 0 : range.getStart();
        poolingRepository.getPage(start, poolGridPager.getPageSize(), new AsyncCallback<PageDto<PoolMetaInfoDto>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LOG.log(Level.ALL, throwable, () -> "Unable to load pools list");
            }

            @Override
            public void onSuccess(PageDto<PoolMetaInfoDto> poolMetaInfos) {
                poolGrid.setRowData((int) poolMetaInfos.getStart(), poolMetaInfos.getData());
                poolGrid.setRowCount((int) poolMetaInfos.getCount(), true);
                questionsGrid.setRowData(0, new ArrayList<>());
                questionsGrid.setRowCount(0);
                variantsGrid.setRowData(0, new ArrayList<>());
                variantsGrid.setRowCount(0);
            }
        });
    }

    private void loadQuestions(Long poolId) {
        questionsGrid.fireEvent(new LoadingStateChangeEvent(LoadingStateChangeEvent.LoadingState.LOADING));
        poolingRepository.getQuestions(poolId, new AsyncCallback<List<PoolQuestionDto>>() {
            @Override
            public void onFailure(Throwable throwable) {
                LOG.log(Level.ALL, throwable, () -> "Unable to load pools list");
            }

            @Override
            public void onSuccess(List<PoolQuestionDto> questions) {
                questionsGrid.fireEvent(new LoadingStateChangeEvent(LoadingStateChangeEvent.LoadingState.PARTIALLY_LOADED));
                questionsGrid.setRowData(questions);
                variantsGrid.setRowData(0, new ArrayList<>());
                variantsGrid.setRowCount(0);
                questionsGrid.fireEvent(new LoadingStateChangeEvent(LoadingStateChangeEvent.LoadingState.LOADED));
            }
        });
    }

    @UiHandler("addBtn")
    public void onClick(ClickEvent e) {
        PoolMetaInfoDto info = new PoolMetaInfoDto();
        info.setId(0L);
        info.setCreated(new Date());
        MetaInfoEditor newEditor = new MetaInfoEditor();
        newEditor.initData(info, () -> {
            if (poolGrid.getRowCount() < poolGridPager.getPageSize()) {
                loadRange(null);
            } else {
                poolGridPager.lastPage();
            }
        });
        newEditor.showDialog();
    }

    @UiHandler("editPoolBtn")
    public void onEditPoolClick(ClickEvent event) {
        poolInfoPanel.initData(getSelectedPool(), () -> {
            if (poolGrid.getRowCount() < poolGridPager.getPageSize()) {
                loadRange(null);
            } else {
                poolGridPager.lastPage();
            }
        });
    }

    PoolMetaInfoDto getSelectedPool() {
        return ((SingleSelectionModel<PoolMetaInfoDto>) poolGrid.getSelectionModel()).getSelectedObject();
    }

    PoolQuestionDto getSelectedQuestion() {
        return ((SingleSelectionModel<PoolQuestionDto>) questionsGrid.getSelectionModel()).getSelectedObject();
    }

    interface QuestionDialogEvent {
        void onOk(String text, String type);
    }

    void questionDialog(String title, String text, QuestionDialogEvent event) {
        questionDialog(title, text, null, event);
    }

    void questionDialog(String title, String text, String type, QuestionDialogEvent event) {
        DialogBox.CaptionImpl captionWidget = new DialogBox.CaptionImpl();
        captionWidget.setText(title);
        DialogBox dialog = new DialogBox(false, true, captionWidget);
        dialog.setWidth("30em");
        final TextBox questionText = new TextBox();
        questionText.setValue(text);
        questionText.setWidth("25em");
        VerticalPanel panel = new VerticalPanel();
        panel.add(questionText);
        HorizontalPanel typePanel = new HorizontalPanel();
        Label typeLabel = new Label("Type");
        ListBox selector = new ListBox();
        selector.setMultipleSelect(false);
        Arrays.stream(PoolQuestionType.values()).forEach(e -> selector.addItem(e.name()));
        if (type != null) {
            for (int i = 0; i < selector.getItemCount(); i++) {
                if (type.equals(selector.getItemText(i))) {
                    selector.setSelectedIndex(i);
                }
            }
        }
        typePanel.add(typeLabel);
        typePanel.add(selector);
        panel.add(typePanel);
        HorizontalPanel closePanel = new HorizontalPanel();
        closePanel.add(new Button("Add", (ClickHandler) event1 -> {
            event.onOk(questionText.getValue(), selector.getSelectedValue());
            dialog.hide(true);
        }));
        closePanel.add(new Button("Cancel", (ClickHandler) event1 -> {
            dialog.hide(true);
        }));
        panel.add(closePanel);
        dialog.add(panel);
        dialog.show();
        dialog.center();
    }

    @UiHandler("addQuestionBtn")
    public void onAddQuestionClick(ClickEvent event) {
        final PoolMetaInfoDto selectedPool = getSelectedPool();
        if (selectedPool == null) {
            return;
        }
        questionDialog("New question for pool " + selectedPool.getTitle(), "", (text, type) -> {
            PoolQuestionDto poolQuestionDto = new PoolQuestionDto();
            poolQuestionDto.setQuestionText(text);
            try {
                poolQuestionDto.setQuestionType(PoolQuestionType.valueOf(type));
            } catch (IllegalArgumentException e) {
                poolQuestionDto.setQuestionType(PoolQuestionType.MANY);
            }
            poolingRepository.addQuestion(selectedPool.getId(), poolQuestionDto, new AsyncCallback<Boolean>() {
                @Override
                public void onFailure(Throwable caught) {
                    LOG.log(Level.ALL, "add question", caught);
                }

                @Override
                public void onSuccess(Boolean result) {
                    loadQuestions(selectedPool.getId());
                }
            });
        });
    }

    @UiHandler("editQuestionBtn")
    public void onEditQuestionBtnClick(ClickEvent event) {
        final PoolMetaInfoDto selectedPool = getSelectedPool();
        final PoolQuestionDto questionDto = getSelectedQuestion();
        if (questionDto == null) {
            return;
        }
        questionDialog("Edit question for pool " + selectedPool.getTitle(),
                questionDto.getQuestionText(),
                questionDto.getQuestionType().name(),
                (text, type) -> {
            questionDto.setQuestionText(text);
            try {
                questionDto.setQuestionType(PoolQuestionType.valueOf(type));
            } catch (IllegalArgumentException e) {
                questionDto.setQuestionType(PoolQuestionType.MANY);
            }
            poolingRepository.updateQuestion(questionDto, new AsyncCallback<Boolean>() {
                @Override
                public void onFailure(Throwable caught) {
                    LOG.log(Level.ALL, "add question", caught);
                }

                @Override
                public void onSuccess(Boolean result) {
                    loadQuestions(selectedPool.getId());
                }
            });
        });
    }

    @UiHandler("addQuestionVariantBtn")
    public void onAddQuestionVariantClick(ClickEvent event) {
        final PoolMetaInfoDto selectedPool = getSelectedPool();
        final PoolQuestionDto selectedQuestion = getSelectedQuestion();
        if (selectedQuestion == null) {
            return;
        }
        questionDialog("New question variant for " + selectedQuestion.getId(), "", (text, type) -> {
            QuestionVariantDto questionVariantDto = new QuestionVariantDto();
            questionVariantDto.setVariantText(text);
            poolingRepository.addVariant(selectedQuestion.getId(), questionVariantDto, new AsyncCallback<Boolean>() {
                @Override
                public void onFailure(Throwable caught) {
                    LOG.log(Level.ALL, "add variant", caught);
                }

                @Override
                public void onSuccess(Boolean result) {
                    loadQuestions(selectedPool.getId());
                    questionsGrid.getSelectionModel().setSelected(selectedQuestion, true);
                }
            });
        });
    }
}
