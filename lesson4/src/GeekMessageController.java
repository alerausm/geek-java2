import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

public class GeekMessageController {
    interface OnGeekMessageControllerEventListener extends EventListener {
        void onMessageReceived(GeekMessage message);
    }

    private static List<GeekMessage> messages = Collections.synchronizedList(new ArrayList<GeekMessage>());
    private static Set<OnGeekMessageControllerEventListener> onGeekMessageControllerEventListeners = new CopyOnWriteArraySet<>();

    GeekMessageController(){}

    public void addMessage(String author, String message, Date date) {
        GeekMessage geekMessage = new GeekMessage(author,message,date);
        messages.add(geekMessage);
        onMessageReceived(geekMessage);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(geekMessage,null);

    }

    public void addOnGeekMessageControllerEventListener(OnGeekMessageControllerEventListener listener) {
        onGeekMessageControllerEventListeners.add(listener);
    }
    public void removeOnGeekMessageControllerEventListener(OnGeekMessageControllerEventListener listener) {
        onGeekMessageControllerEventListeners.remove(listener);
    }

    private void onMessageReceived(GeekMessage geekMessage) {
        for (OnGeekMessageControllerEventListener listener: onGeekMessageControllerEventListeners)
            if (listener!=null) listener.onMessageReceived(geekMessage);
    }


    public class GeekMessage implements Transferable {
        private final String author;
        private final String message;
        private final Date  receiveDate;
        public GeekMessage(String author,String message,Date receiveDate) {
            this.author = author;
            this.message = message;
            this.receiveDate = receiveDate;
        }

        public String getAuthor() {
            return author;
        }

        public String getMessage() {
            return message;
        }

        public Date getReceiveDate() {
            return receiveDate;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("geek-message:")
                    .append("[author=").append(getAuthor())
                    .append(",date=").append(getReceiveDate())
                    .append(",message=").append(getMessage()).append("]");
            return builder.toString();
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[2];
            try {
                flavors[0] = new DataFlavor("application/x-java-serialized-object;class="+getClass().getName());
                flavors[1] = DataFlavor.stringFlavor;
                return flavors;
            } catch (ClassNotFoundException e) {
                return new DataFlavor[0];
            }



        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {

            return DataFlavor.stringFlavor.equals(flavor)
                    || "application".equals(flavor.getPrimaryType())
                    &&"x-java-serialized-object".equals(flavor.getSubType())
                    &&flavor.getRepresentationClass().isAssignableFrom(getClass());

        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            if (!isDataFlavorSupported(flavor))
                throw  new UnsupportedFlavorException(flavor);
            if (DataFlavor.stringFlavor.equals(flavor)) return  toString();
            return  this;
        }
    }
}
