import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class GeekMessenger extends JFrame {

    interface OnInputListener extends EventListener {
        void onInputMessage(String message);
    }
    private static String SETTINGS_ME = "Я";
    private static final int MARGIN_SIZE = 3;
    private static GeekMessenger geekMessenger = null;
    final private InputPanel inputPanel;
    final private ChatPanel chatPanel;
    private static final GeekMessageController messageController = new GeekMessageController();

    public static GeekMessenger createInstance() {
        if (geekMessenger==null) {
            geekMessenger = new GeekMessenger();
        }
        return geekMessenger;
    }

    private GeekMessenger(){
        setTitle("Geek Messenger");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(300, 300, 400, 400);
        setLayout(new BorderLayout()); // выбор компоновщика элементов
        inputPanel = new InputPanel();
        chatPanel = new ChatPanel();
        add(inputPanel, BorderLayout.SOUTH);
        add(chatPanel, BorderLayout.CENTER);
        inputPanel.addOnInputListener(new OnInputListener() {
            @Override
            public void onInputMessage(String message) {
                addMessage(SETTINGS_ME,message);
            }
        });
        messageController.addOnGeekMessageControllerEventListener(chatPanel);

    }

    private void addMessage(String author, String message) {
        messageController.addMessage(author, message, new Date());
    }

    class InputPanel extends JPanel {
        final private JTextField inputField;
        final private JButton inputButton;
        final Set<OnInputListener> onInputListeners = new CopyOnWriteArraySet<>();
        InputPanel(){
            super();
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(MARGIN_SIZE,MARGIN_SIZE,MARGIN_SIZE,MARGIN_SIZE));
            inputField = new JTextField();
            inputField.setEditable(true);
            inputField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar()=='\n') {
                        onCommitInput();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
            add(inputField, BorderLayout.CENTER);

            inputButton = new JButton();
            inputButton.setText("Send");
            inputButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onCommitInput();
                }
            });
            add(inputButton, BorderLayout.EAST);

        }
        void addOnInputListener(OnInputListener listener) {
            onInputListeners.add(listener);
        }
        void removeOnInputListener(OnInputListener listener) {
            onInputListeners.remove(listener);
        }
        private void onCommitInput() {
            String inputText = inputField.getText();
            if (inputText==null || inputText.isEmpty()) return;
            for (OnInputListener listener: onInputListeners) if (listener!=null) listener.onInputMessage(inputText);
            inputField.setText("");
        }
    }

    class ChatPanel extends JPanel implements GeekMessageController.OnGeekMessageControllerEventListener {
        private static final String STYLE_NORMAL = "normal";
        private static final String STYLE_HEADER = "header";
        private static final String STYLE_COMMENT = "comment";
        private static final String FONT_STYLE = "Arial";
        final private JTextPane textArea;



        ChatPanel(){
            super();
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(MARGIN_SIZE,MARGIN_SIZE,MARGIN_SIZE,MARGIN_SIZE));
            textArea = new JTextPane();
            textArea.setEditable(false);
            createStyles(textArea);
            JScrollPane scroller = new JScrollPane();
            scroller.setViewportView(textArea);
            add(scroller, BorderLayout.CENTER);
        }

        @Override
        public void onMessageReceived(GeekMessageController.GeekMessage message) {
            DateFormat sdf = new SimpleDateFormat("dd.MM kk:mm",Locale.getDefault());
            insertText(String.format("%s:\n",message.getAuthor()),textArea.getStyle(STYLE_HEADER),false);
            insertText(message.getMessage()+"\n",textArea.getStyle(STYLE_NORMAL),false);
            insertText(String.format("%s\n",sdf.format(message.getReceiveDate())),textArea.getStyle(STYLE_COMMENT),true);


        }
        private void createStyles(JTextPane textPane)
        {
            Style style;
            style = textPane.addStyle(STYLE_NORMAL, null);
            StyleConstants.setFontFamily(style, FONT_STYLE);
            StyleConstants.setFontSize(style, 12);

            style = textPane.addStyle(STYLE_HEADER, textPane.getStyle(STYLE_NORMAL));
            StyleConstants.setBold(style, true);
            StyleConstants.setForeground(style,Color.blue);

            style = textPane.addStyle(STYLE_COMMENT, textPane.getStyle(STYLE_NORMAL));
            StyleConstants.setFontSize(style, 8);
            StyleConstants.setForeground(style,Color.lightGray);
            StyleConstants.setAlignment(style,StyleConstants.ALIGN_RIGHT);




        }
        private void insertText(String string,Style style,boolean justifyRight)
        {
            try {

                StyledDocument doc = textArea.getStyledDocument();
                int offset = doc.getLength();
                doc.insertString(offset, string, style);
                SimpleAttributeSet alignment = new SimpleAttributeSet();
                StyleConstants.setAlignment(alignment, ((justifyRight)?StyleConstants.ALIGN_RIGHT:StyleConstants.ALIGN_LEFT));
                doc.setParagraphAttributes(offset, doc.getLength(), alignment, false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
