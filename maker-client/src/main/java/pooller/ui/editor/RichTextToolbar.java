package pooller.ui.editor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

/**
 * A sample toolbar for use with {@link RichTextArea}. It provides a simple UI
 * for all rich text formatting, dynamically displayed only for the available
 * functionality.
 */
public class RichTextToolbar extends Composite {

    /**
     * This {@link ImageBundle} is used for all the button icons. Using an image
     * bundle allows all of these images to be packed into a single image, which
     * saves a lot of HTTP requests, drastically improving startup time.
     */
    public interface Images extends ClientBundle {

        /**
         * @gwt.resource bold.gif
         */
        ImageResource bold();

        /**
         * @gwt.resource createLink.gif
         */
        ImageResource createLink();

        /**
         * @gwt.resource hr.gif
         */
        ImageResource hr();

        /**
         * @gwt.resource indent.gif
         */
        ImageResource indent();

        /**
         * @gwt.resource insertImage.gif
         */
        ImageResource insertImage();

        /**
         * @gwt.resource italic.gif
         */
        ImageResource italic();

        /**
         * @gwt.resource justifyCenter.gif
         */
        ImageResource justifyCenter();

        /**
         * @gwt.resource justifyLeft.gif
         */
        ImageResource justifyLeft();

        /**
         * @gwt.resource justifyRight.gif
         */
        ImageResource justifyRight();

        /**
         * @gwt.resource ol.gif
         */
        ImageResource ol();

        /**
         * @gwt.resource outdent.gif
         */
        ImageResource outdent();

        /**
         * @gwt.resource removeFormat.gif
         */
        ImageResource removeFormat();

        /**
         * @gwt.resource removeLink.gif
         */
        ImageResource removeLink();

        /**
         * @gwt.resource strikeThrough.gif
         */
        ImageResource strikeThrough();

        /**
         * @gwt.resource subscript.gif
         */
        ImageResource subscript();

        /**
         * @gwt.resource superscript.gif
         */
        ImageResource superscript();

        /**
         * @gwt.resource ul.gif
         */
        ImageResource ul();

        /**
         * @gwt.resource underline.gif
         */
        ImageResource underline();
    }

    /**
     * This {@link Constants} interface is used to make the toolbar's strings
     * internationalizable.
     */
    public interface Strings extends Constants {

        String black();

        String blue();

        String bold();

        String color();

        String createLink();

        String font();

        String green();

        String hr();

        String indent();

        String insertImage();

        String italic();

        String justifyCenter();

        String justifyLeft();

        String justifyRight();

        String large();

        String medium();

        String normal();

        String ol();

        String outdent();

        String red();

        String removeFormat();

        String removeLink();

        String size();

        String small();

        String strikeThrough();

        String subscript();

        String superscript();

        String ul();

        String underline();

        String white();

        String xlarge();

        String xsmall();

        String xxlarge();

        String xxsmall();

        String yellow();
    }

    /**
     * We use an inner EventListener class to avoid exposing event methods on the
     * RichTextToolbar itself.
     */
    private class EventListener implements ClickListener, ChangeListener,
            KeyboardListener {

        public void onChange(Widget sender) {
            if (sender == backColors) {
                extended.setBackColor(backColors.getValue(backColors.getSelectedIndex()));
                backColors.setSelectedIndex(0);
            } else if (sender == foreColors) {
                extended.setForeColor(foreColors.getValue(foreColors.getSelectedIndex()));
                foreColors.setSelectedIndex(0);
            } else if (sender == fonts) {
                extended.setFontName(fonts.getValue(fonts.getSelectedIndex()));
                fonts.setSelectedIndex(0);
            } else if (sender == fontSizes) {
                extended.setFontSize(fontSizesConstants[fontSizes.getSelectedIndex() - 1]);
                fontSizes.setSelectedIndex(0);
            }
        }

        public void onClick(Widget sender) {
            if (sender == bold) {
                extended.toggleBold();
            } else if (sender == italic) {
                extended.toggleItalic();
            } else if (sender == underline) {
                extended.toggleUnderline();
            } else if (sender == subscript) {
                extended.toggleSubscript();
            } else if (sender == superscript) {
                extended.toggleSuperscript();
            } else if (sender == strikethrough) {
                extended.toggleStrikethrough();
            } else if (sender == indent) {
                extended.rightIndent();
            } else if (sender == outdent) {
                extended.leftIndent();
            } else if (sender == justifyLeft) {
                extended.setJustification(RichTextArea.Justification.LEFT);
            } else if (sender == justifyCenter) {
                extended.setJustification(RichTextArea.Justification.CENTER);
            } else if (sender == justifyRight) {
                extended.setJustification(RichTextArea.Justification.RIGHT);
            } else if (sender == insertImage) {
                String url = Window.prompt("Enter an image URL:", "http://");
                if (url != null) {
                    extended.insertImage(url);
                }
            } else if (sender == createLink) {
                String url = Window.prompt("Enter a link URL:", "http://");
                if (url != null) {
                    extended.createLink(url);
                }
            } else if (sender == removeLink) {
                extended.removeLink();
            } else if (sender == hr) {
                extended.insertHorizontalRule();
            } else if (sender == ol) {
                extended.insertOrderedList();
            } else if (sender == ul) {
                extended.insertUnorderedList();
            } else if (sender == removeFormat) {
                extended.removeFormat();
            } else if (sender == richText) {
                // We use the RichTextArea's onKeyUp event to update the toolbar status.
                // This will catch any cases where the user moves the cursur using the
                // keyboard, or uses one of the browser's built-in keyboard shortcuts.
                updateStatus();
            }
        }

        public void onKeyDown(Widget sender, char keyCode, int modifiers) {
        }

        public void onKeyPress(Widget sender, char keyCode, int modifiers) {
        }

        public void onKeyUp(Widget sender, char keyCode, int modifiers) {
            if (sender == richText) {
                // We use the RichTextArea's onKeyUp event to update the toolbar status.
                // This will catch any cases where the user moves the cursur using the
                // keyboard, or uses one of the browser's built-in keyboard shortcuts.
                updateStatus();
            }
        }
    }

    private static final RichTextArea.FontSize[] fontSizesConstants = new RichTextArea.FontSize[]{
            RichTextArea.FontSize.XX_SMALL, RichTextArea.FontSize.X_SMALL,
            RichTextArea.FontSize.SMALL, RichTextArea.FontSize.MEDIUM,
            RichTextArea.FontSize.LARGE, RichTextArea.FontSize.X_LARGE,
            RichTextArea.FontSize.XX_LARGE};

    private Images images = (Images) GWT.create(Images.class);
    private Strings strings = (Strings) GWT.create(Strings.class);
    private EventListener listener = new EventListener();

    private RichTextArea richText;
//    private RichTextArea.Formatter basic;
    private RichTextArea.Formatter extended;

    private VerticalPanel outer = new VerticalPanel();
    private HorizontalPanel topPanel = new HorizontalPanel();
    private HorizontalPanel bottomPanel = new HorizontalPanel();
    private ToggleButton bold;
    private ToggleButton italic;
    private ToggleButton underline;
    private ToggleButton subscript;
    private ToggleButton superscript;
    private ToggleButton strikethrough;
    private PushButton indent;
    private PushButton outdent;
    private PushButton justifyLeft;
    private PushButton justifyCenter;
    private PushButton justifyRight;
    private PushButton hr;
    private PushButton ol;
    private PushButton ul;
    private PushButton insertImage;
    private PushButton createLink;
    private PushButton removeLink;
    private PushButton removeFormat;

    private ListBox backColors;
    private ListBox foreColors;
    private ListBox fonts;
    private ListBox fontSizes;

    /**
     * Creates a new toolbar that drives the given rich text area.
     *
     * @param richText the rich text area to be controlled
     */
    @UiConstructor
    public RichTextToolbar(RichTextArea richText) {
        this.richText = richText;
//        this.basic = richText.getBasicFormatter();
        this.extended = richText.getFormatter();

        outer.add(topPanel);
        outer.add(bottomPanel);
        topPanel.setWidth("100%");
        bottomPanel.setWidth("100%");

        initWidget(outer);
        setStyleName("gwt-RichTextToolbar");

//        if (basic != null) {
            topPanel.add(bold = createToggleButton(images.bold(), strings.bold()));
            topPanel.add(italic = createToggleButton(images.italic(), strings.italic()));
            topPanel.add(underline = createToggleButton(images.underline(),
                    strings.underline()));
            topPanel.add(subscript = createToggleButton(images.subscript(),
                    strings.subscript()));
            topPanel.add(superscript = createToggleButton(images.superscript(),
                    strings.superscript()));
            topPanel.add(justifyLeft = createPushButton(images.justifyLeft(),
                    strings.justifyLeft()));
            topPanel.add(justifyCenter = createPushButton(images.justifyCenter(),
                    strings.justifyCenter()));
            topPanel.add(justifyRight = createPushButton(images.justifyRight(),
                    strings.justifyRight()));
//        }

        if (extended != null) {
            topPanel.add(strikethrough = createToggleButton(images.strikeThrough(),
                    strings.strikeThrough()));
            topPanel.add(indent = createPushButton(images.indent(), strings.indent()));
            topPanel.add(outdent = createPushButton(images.outdent(), strings.outdent()));
            topPanel.add(hr = createPushButton(images.hr(), strings.hr()));
            topPanel.add(ol = createPushButton(images.ol(), strings.ol()));
            topPanel.add(ul = createPushButton(images.ul(), strings.ul()));
            topPanel.add(insertImage = createPushButton(images.insertImage(),
                    strings.insertImage()));
            topPanel.add(createLink = createPushButton(images.createLink(),
                    strings.createLink()));
            topPanel.add(removeLink = createPushButton(images.removeLink(),
                    strings.removeLink()));
            topPanel.add(removeFormat = createPushButton(images.removeFormat(),
                    strings.removeFormat()));
        }

//        if (basic != null) {
            bottomPanel.add(backColors = createColorList("Background"));
            bottomPanel.add(foreColors = createColorList("Foreground"));
            bottomPanel.add(fonts = createFontList());
            bottomPanel.add(fontSizes = createFontSizes());

            // We only use these listeners for updating status, so don't hook them up
            // unless at least basic editing is supported.
            richText.addKeyboardListener(listener);
            richText.addClickListener(listener);
//        }
    }

    private ListBox createColorList(String caption) {
        ListBox lb = new ListBox();
        lb.addChangeListener(listener);
        lb.setVisibleItemCount(1);

        lb.addItem(caption);
        lb.addItem(strings.white(), "white");
        lb.addItem(strings.black(), "black");
        lb.addItem(strings.red(), "red");
        lb.addItem(strings.green(), "green");
        lb.addItem(strings.yellow(), "yellow");
        lb.addItem(strings.blue(), "blue");
        return lb;
    }

    private ListBox createFontList() {
        ListBox lb = new ListBox();
        lb.addChangeListener(listener);
        lb.setVisibleItemCount(1);

        lb.addItem(strings.font(), "");
        lb.addItem(strings.normal(), "");
        lb.addItem("Times New Roman", "Times New Roman");
        lb.addItem("Arial", "Arial");
        lb.addItem("Courier New", "Courier New");
        lb.addItem("Georgia", "Georgia");
        lb.addItem("Trebuchet", "Trebuchet");
        lb.addItem("Verdana", "Verdana");
        return lb;
    }

    private ListBox createFontSizes() {
        ListBox lb = new ListBox();
        lb.addChangeListener(listener);
        lb.setVisibleItemCount(1);

        lb.addItem(strings.size());
        lb.addItem(strings.xxsmall());
        lb.addItem(strings.xsmall());
        lb.addItem(strings.small());
        lb.addItem(strings.medium());
        lb.addItem(strings.large());
        lb.addItem(strings.xlarge());
        lb.addItem(strings.xxlarge());
        return lb;
    }

    private PushButton createPushButton(ImageResource img, String tip) {
        PushButton pb = new PushButton(new Image(img));
        pb.addClickListener(listener);
        pb.setTitle(tip);
        return pb;
    }

    private ToggleButton createToggleButton(ImageResource img, String tip) {
        ToggleButton tb = new ToggleButton(new Image(img));
        tb.addClickListener(listener);
        tb.setTitle(tip);
        return tb;
    }

    /**
     * Updates the status of all the stateful buttons.
     */
    private void updateStatus() {
//        if (basic != null) {
            bold.setDown(extended.isBold());
            italic.setDown(extended.isItalic());
            underline.setDown(extended.isUnderlined());
            subscript.setDown(extended.isSubscript());
            superscript.setDown(extended.isSuperscript());
//        }

        if (extended != null) {
            strikethrough.setDown(extended.isStrikethrough());
        }
    }
}