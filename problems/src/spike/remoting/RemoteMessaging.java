package spike.remoting;

public interface RemoteMessaging {
    public void send(ApiMessage msg);
    public void subscribe(MessageListener listener);
}
