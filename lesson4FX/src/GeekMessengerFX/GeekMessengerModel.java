package GeekMessengerFX;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class GeekMessengerModel {
    interface OnGeekMessengerEventListener extends EventListener {
        void onMessageReceived(GeekMessage message);
    }

    private static List<GeekMessage> messages = Collections.synchronizedList(new ArrayList<GeekMessage>());
    private static Set<OnGeekMessengerEventListener> onGeekMessengerEventListeners = new CopyOnWriteArraySet<>();

    GeekMessengerModel(){}

    void addMessage(String author, String message, Date date) {
        GeekMessage geekMessage = new GeekMessage(author,message,date);
        messages.add(geekMessage);
        onMessageReceived(geekMessage);
    }

    void addOnGeekMessengerEventListener(OnGeekMessengerEventListener listener) {
        onGeekMessengerEventListeners.add(listener);
    }
    public void removeOnGeekMessengerEventListener(OnGeekMessengerEventListener listener) {
        onGeekMessengerEventListeners.remove(listener);
    }

    private void onMessageReceived(GeekMessage geekMessage) {
        for (OnGeekMessengerEventListener listener: onGeekMessengerEventListeners)
            if (listener!=null) listener.onMessageReceived(geekMessage);
    }


    public class GeekMessage {
        private final String author;
        private final String message;
        private final Date receiveDate;
        GeekMessage(String author, String message, Date receiveDate) {
            this.author = author;
            this.message = message;
            this.receiveDate = receiveDate;
        }

        String getAuthor() {
            return author;
        }

        String getMessage() {
            return message;
        }

        Date getReceiveDate() {
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


    }
}
