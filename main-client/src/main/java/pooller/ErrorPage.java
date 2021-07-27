package pooller;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class ErrorPage {
    interface ErrorPageUiBinder extends UiBinder<HTMLPanel, ErrorPage> {}
    private static ErrorPageUiBinder uiBinder = GWT.create(ErrorPageUiBinder.class);
    HTMLPanel widget;
    @UiField
    InlineHTML message;

    public ErrorPage(Throwable e) {
        widget = uiBinder.createAndBindUi(this);
        message.setText(e.getMessage());
    }

    public HTMLPanel getWidget() {
        return widget;
    }
}
