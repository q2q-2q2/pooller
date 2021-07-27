package pooller.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Widget;


public class FormLabel extends Widget {
    @UiConstructor
    public FormLabel(String text, Widget widget) {
        setElement(Document.get().createLabelElement());
        String id = "id" + Random.nextInt();
        if (!widget.getElement().hasAttribute("id")) {
            widget.getElement().setAttribute("id", id);
        } else {
            id = widget.getElement().getAttribute(id);
        }
        getElement().setAttribute("for", id);
        getElement().setInnerText(text);
    }
}
